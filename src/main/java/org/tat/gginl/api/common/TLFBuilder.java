package org.tat.gginl.api.common;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.tat.gginl.api.common.emumdata.DoubleEntry;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.Status;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.TLF;

@Service("TLFBuilder")
public class TLFBuilder {

	private boolean isRenewal;
	private double homeAmount;
	private double localAmount;
	private double rate;
	private String currency;
	private String chequeNo;
	private String status;
	private String tranCode;
	private String enoNo;
	private String referenceNo;
	private String bankId;
	private String customerId;
	private String branchId;
	private String coaId;
	private String narration;
	private String tlfNo;
	private Date settlementDate;
	private PolicyReferenceType referenceType;

	public TLFBuilder() {
	}

	private TLFBuilder(Payment payment, boolean isRenewal) {
		this.enoNo = payment.getReceiptNo();
		this.referenceNo = payment.getReferenceNo();
		this.referenceType = payment.getReferenceType();
		this.isRenewal = isRenewal;
	}

	private TLFBuilder(DoubleEntry doubleEntry, PaymentChannel paymentChannel, String chequeNo, String bankId, boolean isRenewal) {
		this.isRenewal = isRenewal;

		// credit
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
				this.chequeNo = chequeNo;
				this.bankId = bankId;

				this.status = Status.TCV;
				this.tranCode = TranCode.TRCREDIT;
			} else if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.status = Status.CCV;
				this.tranCode = TranCode.CSCREDIT;
			} else if (PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.status = Status.TCV;
				this.tranCode = TranCode.TRCREDIT;
			} else if (PaymentChannel.SUNDRY.equals(paymentChannel)) {
				this.bankId = bankId;
				this.status = Status.TCV;
				this.tranCode = TranCode.TRCREDIT;
			}

			// debit
		} else if (DoubleEntry.DEBIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(paymentChannel)) {
				this.chequeNo = chequeNo;
				this.bankId = bankId;

				this.status = Status.TDV;
				this.tranCode = TranCode.TRDEBIT;
			} else if (PaymentChannel.CASHED.equals(paymentChannel)) {
				this.status = Status.CDV;
				this.tranCode = TranCode.CSDEBIT;
			} else if (PaymentChannel.TRANSFER.equals(paymentChannel)) {
				this.status = Status.TDV;
				this.tranCode = TranCode.TRDEBIT;
			} else if (PaymentChannel.SUNDRY.equals(paymentChannel)) {
				this.bankId = bankId;
				this.status = Status.TDV;
				this.tranCode = TranCode.TRDEBIT;
			}

		}
	}

	private TLFBuilder(DoubleEntry doubleEntry, Payment payment, boolean isRenewal) {
		this.enoNo = payment.getReceiptNo();
		this.referenceNo = payment.getReferenceNo();
		this.referenceType = payment.getReferenceType();
		this.isRenewal = isRenewal;

		// credit
		if (DoubleEntry.CREDIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
				this.chequeNo = payment.getChequeNo();
				this.bankId = payment.getBank().getId();

				this.status = Status.TCV;
				this.tranCode = TranCode.TRCREDIT;
			} else if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				this.status = Status.CCV;
				this.tranCode = TranCode.CSCREDIT;
			} else if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
				this.status = Status.TCV;
				this.tranCode = TranCode.TRCREDIT;
			} else if (PaymentChannel.SUNDRY.equals(payment.getPaymentChannel())) {
				this.bankId = payment.getAccountBank().getId();
				this.status = Status.TCV;
				this.tranCode = TranCode.TRCREDIT;
			}

			// debit
		} else if (DoubleEntry.DEBIT.equals(doubleEntry)) {
			if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
				chequeNo = payment.getChequeNo();
				bankId = payment.getBank().getId();
				this.status = Status.TDV;
				this.tranCode = TranCode.TRDEBIT;
			} else if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				this.status = Status.CDV;
				this.tranCode = TranCode.CSDEBIT;
			} else if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
				this.status = Status.TDV;
				this.tranCode = TranCode.TRDEBIT;
			} else if (PaymentChannel.SUNDRY.equals(payment.getPaymentChannel())) {
				bankId = payment.getAccountBank().getId();
				this.status = Status.TDV;
				this.tranCode = TranCode.TRDEBIT;
			}
		}
	}

	/* With Double Entry, Payment */
	public TLFBuilder(DoubleEntry doubleEntry, double homeAmount, String customerId, String branchId, String coaId, String tlfNo, String narration, Payment payment,
			boolean isRenewal) {
		this(doubleEntry, payment, isRenewal);
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.coaId = coaId;
		this.narration = narration;
		this.isRenewal = isRenewal;
		this.tlfNo = tlfNo;

		this.currency = payment.getCur() == null ? "KYT" : payment.getCur();
		this.rate = payment.getRate();
		this.settlementDate = new Date();
		if (rate > 1) {
			this.homeAmount = homeAmount * payment.getRate();
		}
	}

	/* With Double Entry, PaymentChannel */
	public TLFBuilder(DoubleEntry doubleEntry, PaymentChannel channel, double homeAmount, String customerId, String branchId, String coaId, String chequeNo, String bankId,
			String tlfNo, String narration, String enoNo, String referenceNo, PolicyReferenceType refType, boolean isRenewal) {
		this(doubleEntry, channel, chequeNo, bankId, isRenewal);
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.tlfNo = tlfNo;
		this.narration = narration;
		this.coaId = coaId;
		this.enoNo = enoNo;
		this.referenceNo = referenceNo;
		this.referenceType = refType;
		this.isRenewal = isRenewal;
		this.currency = "KYT";
		this.rate = 1.0;
		this.settlementDate = new Date();
	}

	/* With Double Entry, PaymentChannel */
	public TLFBuilder(DoubleEntry doubleEntry, PaymentChannel channel, double localAmount, double rate, String currencyCode, double homeAmount, String customerId, String branchId,
			String coaId, String chequeNo, String bankId, String tlfNo, String narration, String enoNo, String referenceNo, PolicyReferenceType refType, boolean isRenewal) {
		this(doubleEntry, channel, chequeNo, bankId, isRenewal);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.rate = rate;
		this.currency = currencyCode;
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.tlfNo = tlfNo;
		this.narration = narration;
		this.coaId = coaId;
		this.enoNo = enoNo;
		this.referenceNo = referenceNo;
		this.referenceType = refType;
		this.isRenewal = isRenewal;
		this.settlementDate = new Date();
	}

	/* With TranCode and Status, Payment */
	public TLFBuilder(String tranCode, String status, double homeAmount, String customerId, String branchId, String coaId, String tlfNo, String narration, Payment payment,
			boolean isRenewal) {
		this(payment, isRenewal);
		this.tranCode = tranCode;
		this.status = status;
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.coaId = coaId;
		this.narration = narration;
		this.isRenewal = isRenewal;
		this.tlfNo = tlfNo;
		this.currency = "KYT";
		this.rate = 1.0;
		this.settlementDate = new Date();
	}

	/* With TranCode and Status, Without Payment */
	public TLFBuilder(String tranCode, String status, double homeAmount, String customerId, String branchId, String coaId, String tlfNo, String narration, String enoNo,
			String referenceNo, PolicyReferenceType refType, boolean isRenewal, String cur, double rate) {
		this.tranCode = tranCode;
		this.status = status;
		this.homeAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.localAmount = Utils.getTwoDecimalPoint(homeAmount);
		this.customerId = customerId;
		this.branchId = branchId;
		this.coaId = coaId;
		this.narration = narration;
		this.enoNo = enoNo;
		this.referenceNo = referenceNo;
		this.referenceType = refType;
		this.tlfNo = tlfNo;
		this.currency = cur;
		this.rate = rate;
		this.settlementDate = new Date();
		if (rate > 1) {
			this.homeAmount = homeAmount * this.rate;
		}
		this.isRenewal = isRenewal;
	}

	public TLF getTLFInstance() {
		TLF tlf = new TLF(homeAmount, localAmount, rate, currency, chequeNo, status, tranCode, enoNo, referenceNo, bankId, customerId, branchId, coaId, narration, settlementDate,
				referenceType, isRenewal, tlfNo);
		tlf.setPaid(false);
		tlf.setClearing(false);
		return tlf;
	}

}
