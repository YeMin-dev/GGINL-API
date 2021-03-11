package org.tat.gginl.api.common;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.PolicyStatus;
import org.tat.gginl.api.domains.Bank;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.PaymentType;

public class PaymentDTO implements ISorter {
	private static final long serialVersionUID = -2728650365142658238L;
	private String id;
	private String tempId;
	private String receiptNo;
	private String bankAccountNo;
	private String chequeNo;
	private String referenceNo;
	private PolicyReferenceType referenceType;
	private double basicPremium;
	private double addOnPremium;
	private double discountPercent;
	private Double servicesCharges;
	private double stampFees;
	private double administrationFees;
	private Double ncbPremium = 0.0;
	private Double penaltyPremium = 0.0;
	private String receivedDeno;
	private String refundDeno;
	private double claimAmount;
	private boolean complete;
	private PaymentChannel paymentChannel;
	private Bank bank;
	private PolicyStatus policyStatus;
	private double reinstatementPremium;
	private Date confirmDate;
	private Date paymentDate;
	private int fromTerm;
	private int toTerm;
	private PaymentType paymentType;
	private double refund;
	private double loanInterest;
	private double renewalInterest;
	private String poNo;
	private Bank accountBank;
	private boolean isOutstanding;
	private boolean isAlreadyGenerated;

	public PaymentDTO() {
	}

	public PaymentDTO(String receiptNo, boolean complete) {
		super();
		this.receiptNo = receiptNo;
		this.complete = complete;
	}

	public PaymentDTO(List<Payment> paymentList) {
		if (paymentList != null && !paymentList.isEmpty()) {
			Payment tempPayment = paymentList.get(0);
			this.id = tempPayment.getId();
			this.receiptNo = tempPayment.getReceiptNo();
			this.bankAccountNo = tempPayment.getBankAccountNo();
			this.referenceNo = tempPayment.getReferenceNo();
			this.referenceType = tempPayment.getReferenceType();
			this.discountPercent = tempPayment.getDiscountPercent();
			this.servicesCharges = tempPayment.getServicesCharges();
			this.stampFees = tempPayment.getStampFees();
			this.receivedDeno = tempPayment.getReceivedDeno();
			this.refundDeno = tempPayment.getRefundDeno();
			this.complete = tempPayment.isComplete();
			this.paymentChannel = tempPayment.getPaymentChannel();
			this.chequeNo = tempPayment.getChequeNo();
			this.bank = tempPayment.getBank();
			this.reinstatementPremium = tempPayment.getReinstatementPremium();
			this.administrationFees = tempPayment.getAdministrationFees();
			this.ncbPremium = tempPayment.getNcbPremium();
			this.penaltyPremium = tempPayment.getPenaltyPremium();
			double basicPremium = 0.0;
			double addOnPremium = 0.0;
			double claimAmount = 0.0;
			this.confirmDate = tempPayment.getConfirmDate();
			this.paymentDate = tempPayment.getPaymentDate();
			for (Payment pay : paymentList) {
				basicPremium = basicPremium + pay.getBasicPremium();
				addOnPremium = addOnPremium + pay.getAddOnPremium();
				claimAmount = claimAmount + pay.getClaimAmount();
			}
			this.claimAmount = claimAmount;
			this.basicPremium = basicPremium;
			this.addOnPremium = addOnPremium;
			this.fromTerm = tempPayment.getFromTerm();
			this.toTerm = tempPayment.getToTerm();
			this.paymentType = tempPayment.getPaymentType();
			this.loanInterest = tempPayment.getLoanInterest();
			this.renewalInterest = tempPayment.getRenewalInterest();
			this.refund = tempPayment.getRefund();
			this.poNo = tempPayment.getPoNo();
			this.accountBank = tempPayment.getAccountBank();
			this.isOutstanding = tempPayment.isOutstanding();
			this.isAlreadyGenerated = tempPayment.isAlreadyGenerated();
		}
	}

	public PaymentDTO(Payment tempPayment) {
		this.id = tempPayment.getId();
		this.receiptNo = tempPayment.getReceiptNo();
		this.bankAccountNo = tempPayment.getBankAccountNo();
		this.referenceNo = tempPayment.getReferenceNo();
		this.referenceType = tempPayment.getReferenceType();
		this.discountPercent = tempPayment.getDiscountPercent();
		this.servicesCharges = tempPayment.getServicesCharges();
		this.stampFees = tempPayment.getStampFees();
		this.receivedDeno = tempPayment.getReceivedDeno();
		this.refundDeno = tempPayment.getRefundDeno();
		this.claimAmount = tempPayment.getClaimAmount();
		this.complete = tempPayment.isComplete();
		this.paymentChannel = tempPayment.getPaymentChannel();
		this.chequeNo = tempPayment.getChequeNo();
		this.bank = tempPayment.getBank();
		this.reinstatementPremium = tempPayment.getReinstatementPremium();
		this.administrationFees = tempPayment.getAdministrationFees();
		this.ncbPremium = tempPayment.getNcbPremium();
		this.penaltyPremium = tempPayment.getPenaltyPremium();
		double basicPremium = 0.0;
		double addOnPremium = 0.0;
		this.confirmDate = tempPayment.getConfirmDate();
		this.paymentDate = tempPayment.getPaymentDate();
		basicPremium = basicPremium + tempPayment.getBasicPremium();
		addOnPremium = addOnPremium + tempPayment.getAddOnPremium();
		this.basicPremium = basicPremium;
		this.addOnPremium = addOnPremium;
		this.fromTerm = tempPayment.getFromTerm();
		this.toTerm = tempPayment.getToTerm();
		this.paymentType = tempPayment.getPaymentType();
		this.loanInterest = tempPayment.getLoanInterest();
		this.renewalInterest = tempPayment.getRenewalInterest();
		this.refund = tempPayment.getRefund();
		this.poNo = tempPayment.getPoNo();
		this.accountBank = tempPayment.getAccountBank();
		this.isOutstanding = tempPayment.isOutstanding();
		this.isAlreadyGenerated = tempPayment.isAlreadyGenerated();
	}

	public boolean isOutstanding() {
		return isOutstanding;
	}

	public void setOutstanding(boolean isOutstanding) {
		this.isOutstanding = isOutstanding;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public double getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(double basicPremium) {
		this.basicPremium = basicPremium;
	}

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getServicesCharges() {
		if (servicesCharges == null) {
			servicesCharges = Double.valueOf(0.0);
		}
		return servicesCharges;
	}

	public void setServicesCharges(Double servicesCharges) {
		this.servicesCharges = servicesCharges;
	}

	public Number getServicesChargesNum() {
		if (servicesCharges == null) {
			servicesCharges = Double.valueOf(0.0);
		}
		return servicesCharges;
	}

	public void setServicesChargesNum(Number servicesCharges) {
		if (servicesCharges != null) {
			this.servicesCharges = servicesCharges.doubleValue();
		}
	}

	public double getStampFees() {
		return stampFees;
	}

	public void setStampFees(double stampFees) {
		this.stampFees = stampFees;
	}

	public String getReceivedDeno() {
		return receivedDeno;
	}

	public void setReceivedDeno(String receivedDeno) {
		this.receivedDeno = receivedDeno;
	}

	public String getRefundDeno() {
		return refundDeno;
	}

	public void setRefundDeno(String refundDeno) {
		this.refundDeno = refundDeno;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public double getTotalPremium() {
		return basicPremium + addOnPremium + penaltyPremium;
	}

	public double getRenewalTotalPremium() {
		return (basicPremium - ncbPremium) + addOnPremium + penaltyPremium;
	}

	public double getBillCollectionTotalPremium() {
		return (basicPremium + addOnPremium);
	}

	public double getDiscountAmount() {
		double discountAmount = 0.0;
		if (discountPercent > 0.0) {
			if (referenceType == PolicyReferenceType.MEDICAL_POLICY || referenceType == PolicyReferenceType.MEDICAL_BILL_COLLECTION) {
				discountAmount = (basicPremium + addOnPremium) * discountPercent / 100;
			} else
				discountAmount = basicPremium * discountPercent / 100;
		}
		return discountAmount;
	}

	public double getNetPremium() {
		return getTotalPremium() - getDiscountAmount() - getNcbPremium();
	}

	public double getPersonTravelNetPremium() {
		double result = 0.0;
		result = basicPremium - getDiscountAmount();
		return result;
	}

	public double getRenewalNetPremium() {
		return getRenewalTotalPremium() - getDiscountAmount();
	}

	public double getBillCollectionNetPremium() {
		return getBillCollectionTotalPremium() - getDiscountAmount() - ncbPremium;
	}

	public double getTotalAmount() {
		return getNetPremium() + getServicesCharges().doubleValue() + stampFees + administrationFees;
	}

	public double getPersonTravelTotalAmount() {
		return getPersonTravelNetPremium() + getServicesCharges().doubleValue() + stampFees + administrationFees;
	}

	public double getRenewalTotalAmount() {
		return getRenewalNetPremium() + servicesCharges.doubleValue() + stampFees + administrationFees;
	}

	public double getBillCollectionTotalAmount() {
		return (getBillCollectionNetPremium() + servicesCharges.doubleValue() + renewalInterest + loanInterest) - refund;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getTotalClaimAmount() {
		return getClaimAmount() - (servicesCharges.doubleValue() + reinstatementPremium);
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public double getReinstatementPremium() {
		return reinstatementPremium;
	}

	public void setReinstatementPremium(double reinstatementPremium) {
		this.reinstatementPremium = reinstatementPremium;
	}

	public double getAdministrationFees() {
		return administrationFees;
	}

	public void setAdministrationFees(double administrationFees) {
		this.administrationFees = administrationFees;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getFromTerm() {
		return fromTerm;
	}

	public void setFromTerm(int fromTerm) {
		this.fromTerm = fromTerm;
	}

	public int getToTerm() {
		return toTerm;
	}

	public void setToTerm(int toTerm) {
		this.toTerm = toTerm;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public double getRenewalInterest() {
		return renewalInterest;
	}

	public void setRenewalInterest(double renewalInterest) {
		this.renewalInterest = renewalInterest;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public Bank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(Bank accountBank) {
		this.accountBank = accountBank;
	}

	public double getNcbPremium() {
		return ncbPremium;
	}

	public void setNcbPremium(double ncbPremium) {
		this.ncbPremium = ncbPremium;
	}

	public Number getNcbPremiumNum() {
		if (ncbPremium == null) {
			ncbPremium = Double.valueOf(0.0);
		}
		return ncbPremium;
	}

	public void setNcbPremiumNum(Number ncbPremium) {
		if (ncbPremium != null) {
			this.ncbPremium = ncbPremium.doubleValue();
		}
	}

	public double getPenaltyPremium() {
		if (penaltyPremium == null) {
			penaltyPremium = Double.valueOf(0.0);
		}
		return penaltyPremium;
	}

	public void setPenaltyPremium(double penaltyPremium) {
		this.penaltyPremium = penaltyPremium;
	}

	public Number getPenaltyPremiumNum() {
		if (penaltyPremium == null) {
			penaltyPremium = Double.valueOf(0.0);
		}
		return penaltyPremium;
	}

	public void setPenaltyPremiumNum(Number penaltyPremium) {
		if (ncbPremium != null) {
			this.penaltyPremium = penaltyPremium.doubleValue();
		}
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String getRegistrationNo() {
		return receiptNo;
	}

	public boolean isAlreadyGenerated() {
		return isAlreadyGenerated;
	}

	public void setAlreadyGenerated(boolean isAlreadyGenerated) {
		this.isAlreadyGenerated = isAlreadyGenerated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
