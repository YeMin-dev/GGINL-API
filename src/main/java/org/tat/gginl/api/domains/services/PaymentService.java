package org.tat.gginl.api.domains.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.common.AccountPayment;
import org.tat.gginl.api.common.COACode;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.CurrencyUtils;
import org.tat.gginl.api.common.IPolicy;
import org.tat.gginl.api.common.ProductIDConfig;
import org.tat.gginl.api.common.TLFBuilder;
import org.tat.gginl.api.common.TranCode;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.AgentCommissionEntryType;
import org.tat.gginl.api.common.emumdata.DoubleEntry;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.Status;
import org.tat.gginl.api.domains.AgentCommission;
import org.tat.gginl.api.domains.Bank;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.LifeProposal;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.ShortEndowmentExtraValue;
import org.tat.gginl.api.domains.TLF;
import org.tat.gginl.api.domains.repository.AgentCommissionRepository;
import org.tat.gginl.api.domains.repository.LifePolicyRepository;
import org.tat.gginl.api.domains.repository.PaymentRepository;
import org.tat.gginl.api.domains.repository.ShortEndowmentExtraValueRepository;
import org.tat.gginl.api.domains.repository.TLFRepository;
import org.tat.gginl.api.dto.billcollectionDTO.BillCollectionDTO;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.ErrorCode;
import org.tat.gginl.api.exception.SystemException;

@Service
public class PaymentService {

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private LifePolicyRepository lifePolicyRepository;

	@Autowired
	private LifepolicyService lifepolicyService;

	@Autowired
	private ShortEndowmentExtraValueRepository shortEndowmentExtraValueRepository;

	@Autowired
	private TLFRepository tlfRepository;

	@Autowired
	private BankService bankService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private AgentCommissionRepository agentCommissionRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> paymentBillCollection(BillCollectionDTO billCollectionDTO) {

		List<Payment> payments = new ArrayList<Payment>();
		try {
			Optional<LifePolicy> lifePolicy = lifepolicyService.findByPolicyNo(billCollectionDTO.getPolicyNo());
			Optional<Payment> paymentNotComplete = paymentRepository.findPaymentNotComplete(lifePolicy.get().getId());
			Optional<Bank> fromBank = bankService.findById(billCollectionDTO.getFromBank());
			Optional<Bank> toBank = bankService.findById(billCollectionDTO.getFromBank());
			Optional<Branch> paymentBranch = branchService.findById(billCollectionDTO.getPaymentBranch());
			// PolicyInsuredPerson insuredPerson =
			// lifePolicy.get().getInsuredPersonInfo().get(0);
			if (paymentNotComplete.isPresent()) {
				throw new SystemException(ErrorCode.PAYMENT_ALREADY_CONFIRMED, "This policy is not completed for previous payment");
			} else {

				PaymentChannel channel = null;
				Payment payment = new Payment();
				if (fromBank.isPresent()) {
					payment.setBank(fromBank.get());
				}
				if (toBank.isPresent()) {
					payment.setAccountBank(toBank.get());
				}

				payment.setPoNo(billCollectionDTO.getChequeNo());
				if (billCollectionDTO.getPaymentChannel().equalsIgnoreCase("CSH")) {
					channel = PaymentChannel.CASHED;
				} else if (billCollectionDTO.getPaymentChannel().equalsIgnoreCase("TRF")) {
					channel = PaymentChannel.TRANSFER;
					payment.setPoNo(billCollectionDTO.getChequeNo());
				} else if (billCollectionDTO.getPaymentChannel().equalsIgnoreCase("CHQ")) {
					channel = PaymentChannel.CHEQUE;
					payment.setChequeNo(billCollectionDTO.getChequeNo());
				} else if (billCollectionDTO.getPaymentChannel().equalsIgnoreCase("RCV")) {
					channel = PaymentChannel.SUNDRY;
				}

				payment.setPaymentChannel(channel);
				payment.setReferenceNo(lifePolicy.get().getId());

				/*
				 * if (KeyFactorChecker.isStudentLife(product.getId())) {
				 * payment.setReferenceType(PolicyReferenceType.
				 * STUDENT_LIFE_POLICY_BILL_COLLECTION); }
				 */
				payment.setReferenceType(PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION);

				// TODO FIXME multiply by payment times
				payment.setBasicPremium((billCollectionDTO.getAmount() * billCollectionDTO.getPaymentTimes()));
				payment.setAmount(payment.getNetPremium());
				payment.setComplete(true);
				payment.setFromTerm(lifePolicy.get().getLastPaymentTerm() + 1);
				payment.setToTerm(lifePolicy.get().getLastPaymentTerm() + billCollectionDTO.getPaymentTimes());
				payment.setPaymentType(lifePolicy.get().getPaymentType());
				payment.setConfirmDate(billCollectionDTO.getPaymentConfirmDate());
				payment.setPaymentDate(billCollectionDTO.getPaymentConfirmDate());
				payment.setBpmsReceiptNo(billCollectionDTO.getBpmNo());

				payments.add(payment);

				payments = extendPaymentTimes(payments, paymentBranch.get(), CurrencyUtils.getCurrencyCode(null));

			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return payments;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> extendPaymentTimes(List<Payment> paymentList, Branch paymentBranch, String currencyCode) {
		List<Payment> payments = new ArrayList<Payment>();

		try {
			String receiptNo = null;
			LifePolicy lifePolicy = null;
			SalePoint salePoint = null;
			List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
			for (Payment payment : paymentList) {

				if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE) || payment.getPaymentChannel().equals(PaymentChannel.SUNDRY)) {
					receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
				} else if (payment.getPaymentChannel().equals(PaymentChannel.CASHED)) {
					receiptNo = customIdRepo.getNextId("CASH_RECEIPT_ID_GEN", null);
				} else {
					receiptNo = customIdRepo.getNextId("TRANSFER_RECEIPT_ID_GEN", null);
				}

				payment.setReceiptNo(receiptNo);
				payment = paymentRepository.save(payment);

				lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
				salePoint = lifePolicy.getSalePoint();

				int paymentTimes = (payment.getToTerm() - payment.getFromTerm()) + 1;
				Date calculatedEndDate = getCalculatedEndDate(paymentTimes, lifePolicy.getActivedPolicyEndDate(), lifePolicy.getPaymentType());
				lifePolicy.setActivedPolicyEndDate(calculatedEndDate);
				lifePolicy.setLastPaymentTerm(payment.getToTerm());

				/** Add Agent Commission TLF **/
				if (lifePolicy.getAgent() != null) {
					agentCommissionList = getAgentCommissionsForLifeBillCollection(lifePolicy, paymentList.get(0), null);

					/** Add AgentCommission **/
					if (agentCommissionList != null && !agentCommissionList.isEmpty()) {
						addAgentCommission(agentCommissionList);
					}
					/** Add AgentCommission TLF **/
					addAgentcommissionLifeProposalBillCollection(lifePolicy, payment);
				}

				if (lifePolicy.getBranch().getId().equals(paymentBranch.getId())) {
					/** Add Payment TLF **/
					preActivateBillCollection(payment);
				} else {
					/** Add Payment TLF for interBranch **/
					preActivateBillCollectionForInterBranch(payment, paymentBranch);
				}

				if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE) || payment.getPaymentChannel().equals(PaymentChannel.SUNDRY)) {
					List<Payment> tempList = new ArrayList<Payment>();
					tempList.add(payment);
					if (payment.getReferenceType().equals(PolicyReferenceType.LIFE_BILL_COLLECTION)
							|| PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION.equals(payment.getReferenceType())
							|| PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION.equals(payment.getReferenceType())) {
						prePaymentTransfer(lifePolicy.getLifeProposal(), tempList, lifePolicy.getBranch(), currencyCode, salePoint);
					}
				}
				/** update life Policy **/
				lifePolicyRepository.saveAndFlush(lifePolicy);
				payments.add(payment);
			}

			return payments;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insert payment : " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void preActivateBillCollection(Payment payment) {
		try {

			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			// MedicalPolicy medicalPolicy =
			// medicalPolicyService.findMedicalPolicyById(payment.getReferenceNo());
			Branch policyBranch = lifePolicy.getBranch();
			String customerId = "";
			if (lifePolicy != null) {
				customerId = lifePolicy.getCustomer() != null ? lifePolicy.getCustomer().getId() : lifePolicy.getOrganization().getId();
			}
			String accountCode = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getProductGroup().getAccountCode();
			String currencyCode = CurrencyUtils.getCurrencyCode(null);
			List<AccountPayment> accountPaymentList = new ArrayList<AccountPayment>();
			String receiptNo = payment.getReceiptNo();
			accountPaymentList.add(new AccountPayment(accountCode, payment));
			SalePoint salePoint = lifePolicy.getSalePoint();

			addNewTLF_For_CashDebitForPremium(accountPaymentList, customerId, policyBranch, receiptNo, false, currencyCode, salePoint);
			addNewTLF_For_PremiumCredit(payment, customerId, policyBranch, accountCode, receiptNo, false, currencyCode, salePoint);

			addNewTLF_For_CashDebitForSCSTFees(payment, customerId, policyBranch, receiptNo, false, currencyCode, salePoint);
			addNewTLF_For_SCSTFees(payment, customerId, policyBranch, receiptNo, false, currencyCode, salePoint);
		} catch (DAOException e) {
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void preActivateBillCollectionForInterBranch(Payment payment, Branch paymentBranch) {
		try {

			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			// MedicalPolicy medicalPolicy =
			// medicalPolicyService.findMedicalPolicyById(payment.getReferenceNo());
			Branch policyBranch = lifePolicy.getBranch();
			String customerId = "";
			if (lifePolicy != null) {
				customerId = lifePolicy.getCustomer() != null ? lifePolicy.getCustomer().getId() : lifePolicy.getOrganization().getId();
			}
			String accountCode = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getProductGroup().getAccountCode();
			String currencyCode = CurrencyUtils.getCurrencyCode(null);
			List<AccountPayment> accountPaymentList = new ArrayList<AccountPayment>();
			String receiptNo = payment.getReceiptNo();
			accountPaymentList.add(new AccountPayment(accountCode, payment));
			SalePoint salePoint = lifePolicy.getSalePoint();

			preActivatePaymentForInterBranch(accountPaymentList, lifePolicy.getCustomerId(), paymentBranch, null, false, currencyCode, salePoint, policyBranch);

		} catch (DAOException e) {
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void preActivatePaymentForInterBranch(List<AccountPayment> accountPaymentList, String customerId, Branch userBranch, List<AgentCommission> agentCommissionList,
			boolean isRenewal, String currencyCode, SalePoint salePoint, Branch proposalBranch) {
		String tlfNo = accountPaymentList.get(0).getPayment().getReceiptNo();
		// Premium receivable Debit
		addNewTLF_For_CashDebitForPremiumForInterBranch(accountPaymentList, customerId, userBranch, tlfNo, isRenewal, currencyCode, salePoint, proposalBranch);

		// Premium Debit
		addNewTLF_For_CashDebitForPremium(accountPaymentList, customerId, userBranch, tlfNo, isRenewal, currencyCode, salePoint);

		for (AccountPayment accountPayment : accountPaymentList) {
			Payment payment = accountPayment.getPayment();

			// 9. Premium Payable Credit
			addNewTLF_For_PremiumCreditPayableForInterBranch(payment, customerId, userBranch, proposalBranch.getPayableACName(), tlfNo, isRenewal, currencyCode, salePoint,
					proposalBranch);

			// premium Credit
			addNewTLF_For_PremiumCredit(payment, customerId, proposalBranch, accountPayment.getAcccountName(), tlfNo, isRenewal, currencyCode, salePoint);

		}

		// if (agentCommissionList != null && !agentCommissionList.isEmpty()) {
		// // 10. Narration: Agent Commission Payable for . . . .
		// String eNo = accountPaymentList.get(0).getPayment().getId();
		// Payment paymentByIndex = accountPaymentList.get(0).getPayment();
		// addAgentCommissionTLF(agentCommissionList, proposalBranch,
		// paymentByIndex, eNo, isRenewal, currencyCode, salePoint);
		// }

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_PremiumCreditPayableForInterBranch(Payment payment, String customerId, Branch branch, String accountName, String tlfNo, boolean isRenewal,
			String currenyCode, SalePoint salePoint, Branch proposalBranch) {
		try {

			double homeAmount = payment.getNetPremium();
			if (isRenewal) {
				homeAmount = payment.getRenewalNetPremium();
			}
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			String coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName, proposalBranch.getBranchCode(), currenyCode);
			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId, branch.getBranchCode(), coaCode, tlfNo,
					getNarrationPremiumPayableForInterBranch(payment, proposalBranch.getName()), payment, isRenewal);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setSalePoint(salePoint);
			tlf.setSettlementDate(payment.getConfirmDate());
			tlf.setPaid(true);
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
			// setIDPrefixForInsert(tlf);
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlfRepository.save(tlf);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_SCSTFees(Payment payment, String customerId, Branch branch, String tlfNo, boolean isRenewal, String currencyCode, SalePoint salePoint) {
		try {
			String narration = null;
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			if (payment.getServicesCharges() > 0) {
				String coaCode = null;
				String accountName = null;
				switch (payment.getReferenceType()) {

					case GROUP_FARMER_PROPOSAL:
					case TRAVEL_PROPOSAL:
					case PERSON_TRAVEL_POLICY:
					case SNAKE_BITE_POLICY:
					case LIFE_POLICY:
					case MEDICAL_POLICY:
					case HEALTH_POLICY:
					case HEALTH_POLICY_BILL_COLLECTION:
					case MICRO_HEALTH_POLICY:
					case CRITICAL_ILLNESS_POLICY:
					case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
					case LIFE_BILL_COLLECTION:
						coaCode = COACode.LIFE_SERVICE_CHARGES;
						break;
					case SHORT_ENDOWMENT_LIFE_POLICY:
					case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
						coaCode = COACode.SHORT_ENDOWMENT_SERVICE_CHARGES;
						break;
					case STUDENT_LIFE_POLICY:
					case STUDENT_LIFE_POLICY_BILL_COLLECTION:
						coaCode = COACode.STUDENT_LIFE_SERVICE_CHARGES;
						break;
					default:
						break;
				}

				accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(), currencyCode);
				double serviceCharges = Utils.getTwoDecimalPoint(payment.getServicesCharges());
				narration = getNarrationSCST(payment, isRenewal);
				TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, serviceCharges, customerId, branch.getBranchCode(), accountName, tlfNo, narration, payment, isRenewal);
				TLF tlf = tlfBuilder.getTLFInstance();
				// setIDPrefixForInsert(tlf);
				tlf.setPaid(true);
				tlf.setSettlementDate(payment.getConfirmDate());
				tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
				tlf.setPolicyNo(lifePolicy.getPolicyNo());
				tlf.setPaymentChannel(payment.getPaymentChannel());
				tlf.setSalePoint(salePoint);
				tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
				tlfRepository.save(tlf);
			}

			if (payment.getStampFees() > 0) {
				String coaCode = "";
				switch (payment.getReferenceType()) {

					default:
						coaCode = COACode.STAMP_FEES;
						break;
				}
				String accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(), currencyCode);
				double stampFees = Utils.getTwoDecimalPoint(payment.getStampFees());
				narration = "Stamp fees for " + payment.getReceiptNo();
				TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, stampFees, customerId, branch.getBranchCode(), accountName, tlfNo, narration, payment, isRenewal);
				TLF tlf = tlfBuilder.getTLFInstance();
				tlf.setPaid(true);
				tlf.setSettlementDate(payment.getConfirmDate());
				tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
				tlf.setPolicyNo(lifePolicy.getPolicyNo());
				tlf.setPaymentChannel(payment.getPaymentChannel());
				tlf.setSalePoint(salePoint);
				tlfRepository.save(tlf);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	private String getNarrationSCST(Payment payment, boolean isRenewal) {
		String premiumName = "";
		StringBuffer nrBf = new StringBuffer();
		// Being amount of Service charges for (Product name) Received No
		// (Receive No)

		nrBf.append("Being amount of service charges for ");
		switch (payment.getReferenceType()) {

			case FARMER_POLICY:
			case LIFE_POLICY:
				premiumName = " Life Premium ";
				break;
			case LIFE_BILL_COLLECTION:
				premiumName = " Life Bill Collection ";
				break;
			case SHORT_ENDOWMENT_LIFE_POLICY:
				premiumName = " Short Term Life Premium ";
				break;
			case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
				premiumName = " Short Term Life Bill Collection ";
			case PA_POLICY:
				premiumName = (isRenewal) ? "Personal Accident Renewal Premium " : "Personal Accident Premium ";
				break;

			case MEDICAL_POLICY:
				premiumName = "Medical Insurance ";
				break;
			case HEALTH_POLICY:
				premiumName = "Health Insurance ";
				break;
			case HEALTH_POLICY_BILL_COLLECTION:
				premiumName = "Health Bill collection ";
				break;
			case MICRO_HEALTH_POLICY:
				premiumName = "Micro Health Insurance ";
				break;
			case CRITICAL_ILLNESS_POLICY:
				premiumName = "Critical Illness Insurance ";
				break;
			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
				premiumName = "Critical Illness Bill collection ";
				break;
			case STUDENT_LIFE_POLICY:
				premiumName = " Student Life Premium ";
				break;
			case STUDENT_LIFE_POLICY_BILL_COLLECTION:
				premiumName = " Student Life Bill Collection ";
				break;
			default:
				break;
		}
		nrBf.append(premiumName);
		nrBf.append(" for Receipt No ");
		nrBf.append(payment.getReceiptNo());
		nrBf.append(". ");

		return nrBf.toString();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_CashDebitForSCSTFees(Payment payment, String customerId, Branch branch, String tlfNo, boolean isRenewal, String currencyCode, SalePoint salePoint) {
		try {
			String narration = null;
			String coaCode = null;
			Product product = null;
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			if (payment.getServicesCharges() > 0 || payment.getStampFees() > 0) {
				if (payment.getPaymentChannel().equals(PaymentChannel.TRANSFER)) {
					coaCode = payment.getAccountBank() == null ? paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE, branch.getBranchCode(), currencyCode)
							: payment.getAccountBank().getAcode();
				} else if (payment.getPaymentChannel().equals(PaymentChannel.CASHED)) {
					coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH, branch.getBranchCode(), currencyCode);
				} else if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
					String coaCodeType = "";
					switch (payment.getReferenceType()) {
						case LIFE_POLICY:
						case LIFE_BILL_COLLECTION:

							product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
							if (ProductIDConfig.isGroupLife(product)) {
								coaCodeType = COACode.GROUP_LIFE_PAYMENT_ORDER;
							} else if (ProductIDConfig.isPublicLife(product)) {
								coaCodeType = COACode.ENDOWMENT_LIFE_PAYMENT_ORDER;
							} else if (ProductIDConfig.isSnakeBite(product)) {
								coaCodeType = COACode.SNAKE_BITE_PAYMENT_ORDER;
							} else if (ProductIDConfig.isSportMan(product)) {
								coaCodeType = COACode.SPORTMAN_PAYMENT_ORDER;
							} else {
								coaCodeType = COACode.LIFE_PAYMENT_ORDER;
							}
							break;
						case MEDICAL_POLICY:
						case MEDICAL_BILL_COLLECTION:
						case HEALTH_POLICY:
						case HEALTH_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.HEALTH_PAYMENT_ORDER;
							break;
						case CRITICAL_ILLNESS_POLICY:
						case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.CRITICAL_ILLNESS_PAYMENT_ORDER;
							break;
						case MICRO_HEALTH_POLICY:
							coaCodeType = COACode.MICRO_HEALTH_PAYMENT_ORDER;
							break;
						case PA_POLICY:
							coaCodeType = COACode.PA_PAYMENT_ORDER;
							break;
						case TRAVEL_PROPOSAL:
						case PERSON_TRAVEL_POLICY:
							coaCodeType = COACode.PERSON_TRAVEL_PAYMENT_ORDER;
							break;
						case SHORT_ENDOWMENT_LIFE_POLICY:
						case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
							coaCodeType = COACode.SHORT_ENDOWMENT_PAYMENT_ORDER;
							break;
						case SNAKE_BITE_POLICY:
							coaCodeType = COACode.SNAKE_BITE_PAYMENT_ORDER;
							break;
						case GROUP_FARMER_PROPOSAL:
							coaCodeType = COACode.FARMER_PAYMENT_ORDER;
							break;
						case STUDENT_LIFE_POLICY:
						case STUDENT_LIFE_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.STUDENT_LIFE_PAYMENT_ORDER;
							break;
						default:
							break;
					}
					coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType, branch.getBranchCode(), currencyCode);
				}
			}

			if (payment.getServicesCharges() > 0) {
				narration = getNarrationSCST(payment, isRenewal);
				double sericeCharges = Utils.getTwoDecimalPoint(payment.getServicesCharges());
				TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, sericeCharges, customerId, branch.getBranchCode(), coaCode, tlfNo, narration, payment, isRenewal);
				TLF tlf = tlfBuilder.getTLFInstance();
				tlf.setPaymentChannel(payment.getPaymentChannel());
				tlf.setSettlementDate(payment.getConfirmDate());
				tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
				tlf.setSalePoint(salePoint);
				tlf.setPaid(true);
				tlf.setPolicyNo(lifePolicy.getPolicyNo());
				tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
				// setIDPrefixForInsert(tlf);
				tlfRepository.save(tlf);
			}

			if (payment.getStampFees() > 0) {
				narration = "Stamp fees for " + payment.getReceiptNo();
				double stampFees = Utils.getTwoDecimalPoint(payment.getStampFees());

				TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, stampFees, customerId, branch.getBranchCode(), coaCode, tlfNo, narration, payment, isRenewal);
				TLF tlf = tlfBuilder.getTLFInstance();
				// setIDPrefixForInsert(tlf);
				tlf.setPaymentChannel(payment.getPaymentChannel());
				tlf.setPaid(true);
				tlf.setSettlementDate(payment.getConfirmDate());
				tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
				tlf.setPolicyNo(lifePolicy.getPolicyNo());
				tlf.setSalePoint(salePoint);
				tlfRepository.save(tlf);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_PremiumCredit(Payment payment, String customerId, Branch branch, String accountName, String tlfNo, boolean isRenewal, String currenyCode,
			SalePoint salePoint) {
		try {
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			double homeAmount = payment.getNetPremium();
			if (isRenewal) {
				homeAmount = payment.getRenewalNetPremium();
			}
			String coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName, branch.getBranchCode(), currenyCode);
			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId, branch.getBranchCode(), coaCode, tlfNo, getNarrationPremium(payment, isRenewal),
					payment, isRenewal);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setPaid(true);
			tlf.setSettlementDate(payment.getConfirmDate());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			// setIDPrefixForInsert(tlf);
			tlfRepository.save(tlf);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_CashDebitForPremium(List<AccountPayment> accountPaymentList, String customerId, Branch branch, String tlfNo, boolean isRenewal, String currencyCode,
			SalePoint salePoint) {
		try {

			double totalNetPremium = 0;
			double homeAmount = 0;
			String coaCode = null;
			Product product;
			Payment payment = accountPaymentList.get(0).getPayment();
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			// TLF Home Amount
			if (isRenewal) {
				for (AccountPayment accountPayment : accountPaymentList) {
					totalNetPremium = totalNetPremium + accountPayment.getPayment().getRenewalNetPremium();
				}
			} else {
				for (AccountPayment accountPayment : accountPaymentList) {
					totalNetPremium = totalNetPremium + accountPayment.getPayment().getNetPremium();
				}
			}

			// TODO FIXME TOK don't set total amount as homeamount , wrong if
			// not
			// home currency
			homeAmount = totalNetPremium;
			// TLF COAID
			if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
				coaCode = payment.getAccountBank() == null ? paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE, branch.getBranchCode(), currencyCode)
						: payment.getAccountBank().getAcode();
			} else if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH, branch.getBranchCode(), currencyCode);
			} else if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
				String coaCodeType = "";
				switch (payment.getReferenceType()) {
					case LIFE_POLICY:
					case LIFE_BILL_COLLECTION:
						product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
						if (ProductIDConfig.isGroupLife(product)) {
							coaCodeType = COACode.GROUP_LIFE_PAYMENT_ORDER;
						} else if (ProductIDConfig.isPublicLife(product)) {
							coaCodeType = COACode.ENDOWMENT_LIFE_PAYMENT_ORDER;
						} else if (ProductIDConfig.isSnakeBite(product)) {
							coaCodeType = COACode.SNAKE_BITE_PAYMENT_ORDER;
						} else if (ProductIDConfig.isSportMan(product)) {
							coaCodeType = COACode.SPORTMAN_PAYMENT_ORDER;
						} else {
							coaCodeType = COACode.LIFE_PAYMENT_ORDER;
						}
						break;
					case MEDICAL_POLICY:
					case HEALTH_POLICY:
					case HEALTH_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.HEALTH_PAYMENT_ORDER;
						break;
					case GROUP_MICRO_HEALTH:
					case MICRO_HEALTH_POLICY:
						coaCodeType = COACode.MICRO_HEALTH_PAYMENT_ORDER;
						break;
					case CRITICAL_ILLNESS_POLICY:
					case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.CRITICAL_ILLNESS_PAYMENT_ORDER;
						break;
					case PA_POLICY:
						coaCodeType = COACode.PA_PAYMENT_ORDER;
						break;
					case TRAVEL_PROPOSAL:
					case PERSON_TRAVEL_POLICY:
						coaCodeType = COACode.PERSON_TRAVEL_PAYMENT_ORDER;
						break;
					case GROUP_FARMER_PROPOSAL:
					case FARMER_POLICY:
						coaCodeType = COACode.FARMER_PAYMENT_ORDER;
						break;
					case SHORT_ENDOWMENT_LIFE_POLICY:
					case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
						coaCodeType = COACode.SHORT_ENDOWMENT_PAYMENT_ORDER;
						break;
					case STUDENT_LIFE_POLICY:
					case STUDENT_LIFE_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.STUDENT_LIFE_PAYMENT_ORDER;
						break;
					case PUBLIC_TERM_LIFE_POLICY:
						coaCodeType = COACode.PUBLICTERMLIFE_PAYMENT_ORDER;
						break;
					default:
						break;
				}
				coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType, branch.getBranchCode(), currencyCode);
			} else if (PaymentChannel.SUNDRY.equals(payment.getPaymentChannel())) {
				String coaCodeType = "";
				switch (payment.getReferenceType()) {
					case LIFE_POLICY:
					case LIFE_BILL_COLLECTION:
						coaCodeType = COACode.LIFE_SUNDRY;
						break;
					case MEDICAL_POLICY:
					case MEDICAL_BILL_COLLECTION:
						coaCodeType = COACode.MEDICAL_SUNDRY;
						break;
					case HEALTH_POLICY:
					case HEALTH_POLICY_BILL_COLLECTION:
					case GROUP_MICRO_HEALTH:
						coaCodeType = COACode.HEALTH_SUNDRY;
						break;
					case MICRO_HEALTH_POLICY:
						coaCodeType = COACode.MICRO_HEALTH_SUNDRY;
						break;
					case CRITICAL_ILLNESS_POLICY:
					case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.CRITICAL_ILLNESS_SUNDRY;
						break;
					case PA_POLICY:
						coaCodeType = COACode.PA_SUNDRY;
						break;
					case PERSON_TRAVEL_POLICY:
						coaCodeType = COACode.PERSON_TRAVEL_SUNDRY;
						break;
					case GROUP_FARMER_PROPOSAL:
					case FARMER_POLICY:
						coaCodeType = COACode.FARMER_SUNDRY;
						break;
					case TRAVEL_PROPOSAL:
						coaCodeType = COACode.TRAVEL_SUNDRY;
						break;
					case SHORT_ENDOWMENT_LIFE_POLICY:
					case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
						coaCodeType = COACode.SHORT_ENDOWMENT_SUNDRY;
						break;
					case STUDENT_LIFE_POLICY:
					case STUDENT_LIFE_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.STUDENT_LIFE_SUNDRY;
						break;
					case PUBLIC_TERM_LIFE_POLICY:
						coaCodeType = COACode.PUBLICTERMLIFE_SUNDRY;
						break;
					default:
						break;
				}
				coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType, branch.getBranchCode(), currencyCode);
			}

			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, branch.getBranchCode(), coaCode, tlfNo, getNarrationPremium(payment, isRenewal),
					payment, isRenewal);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setSalePoint(salePoint);
			tlf.setSettlementDate(payment.getConfirmDate());
			tlf.setPaid(true);
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
			// setIDPrefixForInsert(tlf);
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlfRepository.save(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void prePaymentTransfer(LifeProposal lifeProposal, List<Payment> paymentList, Branch branch, String currencyCode, SalePoint salePoint) {
		try {
			List<AccountPayment> accountPaymentList = new ArrayList<AccountPayment>();
			AccountPayment accountPayment;
			for (Payment payment : paymentList) {
				accountPayment = new AccountPayment(payment.getAccountBank().getAcode(), payment);
				accountPaymentList.add(accountPayment);
			}
			activatePaymentTransfer(accountPaymentList, lifeProposal.getCustomerId(), branch, false, currencyCode, salePoint);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to pre-payment a LifeProposal ID : " + lifeProposal.getId(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void activatePaymentTransfer(List<AccountPayment> accountPaymentList, String customerId, Branch branch, boolean isRenewal, String currencyCode, SalePoint salePoint) {
		try {
			String tlfNo = accountPaymentList.get(0).getPayment().getReceiptNo();
			addNewTLF_For_CashCreditForPremium(accountPaymentList, customerId, branch, false, tlfNo, true, isRenewal, currencyCode, salePoint);
			for (AccountPayment accountPayment : accountPaymentList) {
				Payment payment = accountPayment.getPayment();
				if (payment.getPaymentChannel().equals(PaymentChannel.TRANSFER)) {
					payment.setPO(false);
					payment = paymentRepository.saveAndFlush(payment);
				}
				addNewTLF_For_PremiumDebit(payment, customerId, branch, accountPayment.getAcccountName(), false, tlfNo, true, isRenewal, currencyCode, salePoint);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update the actual Payment", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_PremiumDebit(Payment payment, String customerId, Branch branch, String accountName, boolean isEndorse, String tlfNo, boolean isClearing,
			boolean isRenewal, String currencyCode, SalePoint salePoint) {
		try {
			double netPremium = 0.0;
			if (isRenewal) {
				netPremium = payment.getRenewalNetPremium();
			} else {
				netPremium = payment.getNetPremium();
			}
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			netPremium = netPremium * (isEndorse ? -1 : 1);
			double homeAmount = 0;
			// TLF Home Amount
			homeAmount = netPremium;

			// TLF COAID
			String coaCode = "";
			if (payment.getAccountBank() == null) {
				coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName, branch.getBranchCode(), currencyCode);
			} else {
				coaCode = payment.getAccountBank().getAcode();
			}

			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, branch.getBranchCode(), coaCode, tlfNo, getNarrationPremium(payment, isRenewal),
					payment, isRenewal);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setClearing(isClearing);
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.setSettlementDate(payment.getConfirmDate());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
			// setIDPrefixForInsert(tlf);
			tlfRepository.save(tlf);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	private String getNarrationPremium(Payment payment, boolean isRenewal) {
		StringBuffer nrBf = new StringBuffer();
		String customerName = "";
		String premiumString = "";
		int totalInsuredPerson = 0;
		double si = 0.0;
		String unit = "";
		double premium = 0.0;

		if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
			nrBf.append("Accrued");
			switch (payment.getReferenceType()) {
				case FARMER_POLICY:
				case LIFE_POLICY:
					premiumString = " Life Premium ";
					LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = lifePolicy.getTotalSumInsured();
					premium = (lifePolicy.isEndorsementApplied()) ? lifePolicy.getTotalEndorsementPremium() : lifePolicy.getTotalPremium();
					customerName = lifePolicy.getCustomerName();
					totalInsuredPerson = lifePolicy.getInsuredPersonInfo().size() > 1 ? lifePolicy.getInsuredPersonInfo().size() : 0;
					break;
				case LIFE_BILL_COLLECTION:
					premiumString = " Life Bill Collection ";
					LifePolicy lifeBillPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = lifeBillPolicy.getTotalSumInsured();
					premium = lifeBillPolicy.getTotalPremium();
					customerName = lifeBillPolicy.getCustomerName();
					break;
				case PA_POLICY:
					premiumString = " Personal Accident Premium ";
					LifePolicy paPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = paPolicy.getTotalSumInsured();
					premium = (paPolicy.isEndorsementApplied()) ? paPolicy.getTotalEndorsementPremium() : paPolicy.getTotalPremium();
					customerName = paPolicy.getCustomerName();
					break;

				// case MICRO_HEALTH_POLICY_BILL_COLLECTION:
				// premiumString = "Micro Health Bill Collection Premium ";
				// MedicalPolicy microHealthBillPolicy =
				// medicalPolicyService.findMedicalPolicyById(payment.getReferenceNo());
				// unit = microHealthBillPolicy.getTotalUnit() + " Unit(s) ";
				// premium = microHealthBillPolicy.getPremium();
				// customerName = microHealthBillPolicy.getCustomerName();
				// break;

				case SHORT_ENDOWMENT_LIFE_POLICY:
					premiumString = " Short Term Endowment Life Premium ";
					LifePolicy seLifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = seLifePolicy.getTotalSumInsured();
					premium = seLifePolicy.getTotalPremium();
					customerName = seLifePolicy.getCustomerName();
					break;
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					premiumString = " Short Term Endowment Life Bill Collection ";
					LifePolicy seLifeBillPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = seLifeBillPolicy.getTotalSumInsured();
					premium = seLifeBillPolicy.getTotalPremium();
					customerName = seLifeBillPolicy.getCustomerName();
					break;
				case STUDENT_LIFE_POLICY:
					premiumString = "Student Life Premium ";
					LifePolicy studentPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = studentPolicy.getTotalSumInsured();
					premium = (studentPolicy.isEndorsementApplied()) ? studentPolicy.getTotalEndorsementPremium() : studentPolicy.getTotalPremium();
					customerName = studentPolicy.getCustomerName();
					break;
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					premiumString = " Student Life Bill Collection ";
					LifePolicy studentBillPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = studentBillPolicy.getTotalSumInsured();
					premium = studentBillPolicy.getTotalPremium();
					customerName = studentBillPolicy.getCustomerName();
					break;
				default:
					break;
			}
			nrBf.append(premiumString);
			nrBf.append("to be paid by ");
			nrBf.append(payment.getReceiptNo());
			nrBf.append(" from ");
			nrBf.append(customerName);
			if (payment.getReferenceType().equals(PolicyReferenceType.MEDICAL_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)
					|| payment.getReferenceType().equals(PolicyReferenceType.MICRO_HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)
					|| PolicyReferenceType.PERSON_TRAVEL_POLICY.equals(payment.getReferenceType())) {
				nrBf.append(" for ");
				nrBf.append(unit);
			} else {
				nrBf.append(" for Sum Insured ");
				nrBf.append(Utils.getCurrencyFormatString(si));
			}

			if (totalInsuredPerson > 0
					&& (PolicyReferenceType.GROUP_FARMER_PROPOSAL.equals(payment.getReferenceType()) || PolicyReferenceType.LIFE_POLICY.equals(payment.getReferenceType()))) {
				nrBf.append(" and for total number of insured person ");
				nrBf.append(Integer.toString(totalInsuredPerson));
			}

			nrBf.append(" and the premium amount of ");
			nrBf.append(Utils.getCurrencyFormatString(premium));
			nrBf.append(". ");

		} else {
			nrBf.append("Being amount of ");
			switch (payment.getReferenceType()) {
				case FARMER_POLICY:
				case LIFE_POLICY:
					nrBf.append(" Life Premium ");
					LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = lifePolicy.getTotalSumInsured();
					premium = (lifePolicy.isEndorsementApplied()) ? lifePolicy.getTotalEndorsementPremium() : lifePolicy.getTotalPremium();
					customerName = lifePolicy.getCustomerName();
					totalInsuredPerson = lifePolicy.getInsuredPersonInfo().size() > 1 ? lifePolicy.getInsuredPersonInfo().size() : 0;
					break;
				case LIFE_BILL_COLLECTION:
					premiumString = " Life Bill Collection ";
					LifePolicy lifeBillPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = lifeBillPolicy.getTotalSumInsured();
					premium = (lifeBillPolicy.isEndorsementApplied()) ? lifeBillPolicy.getTotalEndorsementPremium() : lifeBillPolicy.getTotalPremium();
					break;
				case SHORT_ENDOWMENT_LIFE_POLICY:
					nrBf.append(" Short Term Endowment Life Premium ");
					LifePolicy selifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = selifePolicy.getTotalSumInsured();
					premium = selifePolicy.getTotalPremium();
					customerName = selifePolicy.getCustomerName();
					break;
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					nrBf.append(" Short Term Endowment Life Bill Collection ");
					LifePolicy selifeBillPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = selifeBillPolicy.getTotalSumInsured();
					premium = selifeBillPolicy.getTotalPremium();
					customerName = selifeBillPolicy.getCustomerName();
					break;

				case STUDENT_LIFE_POLICY:
					nrBf.append(" Life Premium ");
					LifePolicy studentPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = studentPolicy.getTotalSumInsured();
					premium = (studentPolicy.isEndorsementApplied()) ? studentPolicy.getTotalEndorsementPremium() : studentPolicy.getTotalPremium();
					customerName = studentPolicy.getCustomerName();
					totalInsuredPerson = studentPolicy.getInsuredPersonInfo().size() > 1 ? studentPolicy.getInsuredPersonInfo().size() : 0;
					break;
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					premiumString = "Student Life Bill Collection ";
					LifePolicy studentBillPolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
					si = studentBillPolicy.getTotalSumInsured();
					premium = (studentBillPolicy.isEndorsementApplied()) ? studentBillPolicy.getTotalEndorsementPremium() : studentBillPolicy.getTotalPremium();
					break;
				default:
					break;
			}
			nrBf.append(premiumString);
			nrBf.append(" received by ");
			nrBf.append(payment.getReceiptNo());
			nrBf.append(" from ");
			nrBf.append(customerName);
			if (payment.getReferenceType().equals(PolicyReferenceType.MEDICAL_POLICY) || PolicyReferenceType.PERSON_TRAVEL_POLICY.equals(payment.getReferenceType())
					|| payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)
					|| payment.getReferenceType().equals(PolicyReferenceType.MICRO_HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)) {
				nrBf.append(" for ");
				nrBf.append(unit);
			} else {
				nrBf.append(" for Sum Insured ");
				nrBf.append(Utils.getCurrencyFormatString(si));
			}
			if (totalInsuredPerson > 0
					&& (PolicyReferenceType.GROUP_FARMER_PROPOSAL.equals(payment.getReferenceType()) || PolicyReferenceType.LIFE_POLICY.equals(payment.getReferenceType()))) {
				nrBf.append(" and for total number of insured person ");
				nrBf.append(Integer.toString(totalInsuredPerson));
			}
			nrBf.append(" and the premium amount of ");
			nrBf.append(Utils.getCurrencyFormatString(premium));
			nrBf.append(". ");
		}

		return nrBf.toString();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_CashCreditForPremium(List<AccountPayment> accountPaymentList, String customerId, Branch branch, boolean isEndorse, String tlfNo, boolean isClearing,
			boolean isRenewal, String currencyCode, SalePoint salePoint) {
		try {

			double totalNetPremium = 0;
			double homeAmount = 0;
			String narration = null;
			String coaCode = null;
			Payment payment = accountPaymentList.get(0).getPayment();
			String enoNo = payment.getReceiptNo();
			Product product = null;
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			if (isRenewal) {
				for (AccountPayment accountPayment : accountPaymentList) {
					totalNetPremium = totalNetPremium + accountPayment.getPayment().getRenewalNetPremium();
				}
			} else {
				for (AccountPayment accountPayment : accountPaymentList) {
					totalNetPremium = totalNetPremium + accountPayment.getPayment().getNetPremium();
				}
			}
			totalNetPremium = totalNetPremium * (isEndorse ? -1 : 1);

			// TLF Home Amount
			homeAmount = totalNetPremium;

			// TLF COAID
			switch (payment.getPaymentChannel()) {
				case TRANSFER: {
					if (payment.getAccountBank() == null) {
						coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE, branch.getBranchCode(), currencyCode);
					} else {
						coaCode = payment.getAccountBank().getAcode();
					}
				}
					break;
				case CASHED:
					coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH, branch.getBranchCode(), currencyCode);
					break;
				case CHEQUE: {
					String coaCodeType = "";
					switch (payment.getReferenceType()) {

						case MEDICAL_POLICY:
						case HEALTH_POLICY:
						case HEALTH_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.HEALTH_PAYMENT_ORDER;
							break;
						case LIFE_POLICY:
						case LIFE_BILL_COLLECTION:
							product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
							if (ProductIDConfig.isGroupLife(product)) {
								coaCodeType = COACode.GROUP_LIFE_PAYMENT_ORDER;
							} else if (ProductIDConfig.isPublicLife(product)) {
								coaCodeType = COACode.ENDOWMENT_LIFE_PAYMENT_ORDER;
							} else if (ProductIDConfig.isSnakeBite(product)) {
								coaCodeType = COACode.SNAKE_BITE_PAYMENT_ORDER;
							} else if (ProductIDConfig.isSportMan(product)) {
								coaCodeType = COACode.SPORTMAN_PAYMENT_ORDER;
							} else {
								coaCodeType = COACode.LIFE_PAYMENT_ORDER;
							}
							break;
						case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
						case SHORT_ENDOWMENT_LIFE_POLICY:
							coaCodeType = COACode.SHORT_ENDOWMENT_PAYMENT_ORDER;
							break;
						case CRITICAL_ILLNESS_POLICY:
						case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.CRITICAL_ILLNESS_PAYMENT_ORDER;
							break;
						case MICRO_HEALTH_POLICY:
						case GROUP_MICRO_HEALTH:
							coaCodeType = COACode.MICRO_HEALTH_PAYMENT_ORDER;
							break;
						case PA_POLICY:
							coaCodeType = COACode.PA_PAYMENT_ORDER;
							break;
						case PERSON_TRAVEL_POLICY:
							coaCodeType = COACode.PERSON_TRAVEL_PAYMENT_ORDER;
							break;
						case PUBLIC_TERM_LIFE_POLICY:
							coaCodeType = COACode.PUBLICTERMLIFE_PAYMENT_ORDER;
							break;
						case GROUP_FARMER_PROPOSAL:
						case FARMER_POLICY:
							coaCodeType = COACode.FARMER_PAYMENT_ORDER;
							break;
						case STUDENT_LIFE_POLICY:
						case STUDENT_LIFE_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.STUDENT_LIFE_PAYMENT_ORDER;
							break;
						default:
							break;
					}
					coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType, branch.getBranchCode(), currencyCode);
				}
					break;
				case SUNDRY: {
					String coaCodeType = "";
					switch (payment.getReferenceType()) {

						case LIFE_POLICY:
						case LIFE_BILL_COLLECTION:
							coaCodeType = COACode.LIFE_SUNDRY;
							break;

						case SHORT_ENDOWMENT_LIFE_POLICY:
						case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
							coaCodeType = COACode.SHORT_ENDOWMENT_SUNDRY;
							break;
						case MEDICAL_BILL_COLLECTION:
						case MEDICAL_POLICY:
							coaCodeType = COACode.MEDICAL_SUNDRY;
							break;
						case HEALTH_POLICY:
						case HEALTH_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.HEALTH_SUNDRY;
							break;
						case MICRO_HEALTH_POLICY:
						case GROUP_MICRO_HEALTH:
							coaCodeType = COACode.MICRO_HEALTH_SUNDRY;
							break;
						case CRITICAL_ILLNESS_POLICY:
						case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.CRITICAL_ILLNESS_SUNDRY;
							break;
						case PA_POLICY:
							coaCodeType = COACode.PA_SUNDRY;
							break;
						case PERSON_TRAVEL_POLICY:
							coaCodeType = COACode.PERSON_TRAVEL_SUNDRY;
							break;
						case TRAVEL_PROPOSAL:
							coaCodeType = COACode.TRAVEL_SUNDRY;
							break;
						case GROUP_FARMER_PROPOSAL:
						case FARMER_POLICY:
							coaCodeType = COACode.FARMER_SUNDRY;
							break;
						case STUDENT_LIFE_POLICY:
						case STUDENT_LIFE_POLICY_BILL_COLLECTION:
							coaCodeType = COACode.STUDENT_LIFE_SUNDRY;
							break;
						case PUBLIC_TERM_LIFE_POLICY:
							coaCodeType = COACode.PUBLICTERMLIFE_SUNDRY;
							break;
						default:
							break;
					}
					coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType, branch.getBranchCode(), currencyCode);
				}
					break;
			}
			// TLF Narration
			narration = "Cash refund for " + enoNo;
			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId, branch.getBranchCode(), coaCode, tlfNo, narration, payment, isRenewal);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setClearing(isClearing);
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlf.setSettlementDate(new Date());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(new Date());
			tlfRepository.save(tlf);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void addAgentcommissionLifeProposalBillCollection(LifePolicy policy, Payment payment) {
		try {
			String currencyCode = CurrencyUtils.getCurrencyCode(null);
			int paymentType = policy.getPaymentType().getMonth();
			double commissionPercent = 0.00;
			int toterm = payment.getToTerm();
			int fromterm = payment.getFromTerm();
			List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
			double totalCommission = 0.0;
			int i = 0;
			int j = 0;
			Product product = policy.getInsuredPersonInfo().get(0).getProduct();
			int check = 12 / paymentType;
			j = toterm;

			double extraAmount = 0.0;
			if (PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION.equals(payment.getReferenceType())) {
				ShortEndowmentExtraValue shortEndowmentExtraValue = shortEndowmentExtraValueRepository.findShortEndowmentExtraValue(policy.getId());
				if (shortEndowmentExtraValue != null) {
					extraAmount = shortEndowmentExtraValue.getExtraAmount();
				}
			}

			for (i = fromterm; i <= j; i++) {
				if (i <= check) {
					commissionPercent = product.getFirstCommission();
				} else
					commissionPercent = product.getRenewalCommission();

				if (commissionPercent > 0) {
					double totalPremium = policy.getTotalBasicTermPremium() + policy.getTotalAddOnTermPremium();

					if (extraAmount > 0) {
						totalPremium -= extraAmount;
						extraAmount = 0;
					}

					double commission = (totalPremium / 100) * commissionPercent;
					totalCommission = totalCommission + commission;
				}
			}

			agentCommissionList.add(new AgentCommission(policy.getId(), payment.getReferenceType(), policy.getAgent(), totalCommission, payment.getConfirmDate()));
			addAgentCommissionTLF(agentCommissionList, policy.getBranch(), payment, policy.getLifeProposal().getId(), false, currencyCode, policy.getSalePoint());

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Add Agentcommission TLF for LifeBillCollection ( LifePolicy Id :  " + policy.getId() + " )", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addAgentCommissionTLF(List<AgentCommission> agentCommissionList, Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
			SalePoint salePoint) {
		try {
			for (AgentCommission agentCommission : agentCommissionList) {
				boolean coInsureance = isCoInsurance(agentCommission.getReferenceNo(), agentCommission.getReferenceType());
				addNewTLF_For_AgentCommissionDr(agentCommission, coInsureance, branch, payment, eno, isRenewal, currencyCode, salePoint);
				addNewTLF_For_AgentCommissionCredit(agentCommission, coInsureance, branch, payment, eno, isRenewal, currencyCode, salePoint);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add agent Commission into TLF.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private boolean isCoInsurance(String referenceNo, PolicyReferenceType referenceType) {
		boolean isCoInsurance = false;
		double maxSumInsured = 0.0;
		double totalSumInsured = 0.0;
		IPolicy policy = null;
		// TODO FIX PSH
		switch (referenceType) {

			default:
				break;
		}

		if (policy != null) {
			maxSumInsured = policy.getProductGroup().getMaxSumInsured();
			totalSumInsured = policy.getTotalSumInsured();
			isCoInsurance = (totalSumInsured > maxSumInsured) ? true : false;
		}

		return isCoInsurance;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_AgentCommissionCredit(AgentCommission ac, boolean coInsureance, Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
			SalePoint salePoint) {
		try {
			String receiptNo = payment.getReceiptNo();
			String coaCode = null;
			String accountName = null;
			double commission = 0.0;
			LifePolicy policy = lifePolicyRepository.getOne(payment.getReferenceNo());
			switch (ac.getReferenceType()) {
				case LIFE_BILL_COLLECTION:
				case LIFE_POLICY:
					Product product = policy.getPolicyInsuredPersonList().get(0).getProduct();
					// coaCode = ProductIDConfig.isFarmer(product) ?
					// COACode.FARMER_AGENT_PAYABLE : coInsureance ?
					// COACode.LIFE_CO_AGENT_PAYABLE :
					// COACode.LIFE_AGENT_PAYABLE;
					if (coInsureance) {
						coaCode = COACode.LIFE_CO_AGENT_PAYABLE;
					} else if (ProductIDConfig.isFarmer(product)) {
						coaCode = COACode.FARMER_AGENT_PAYABLE;
					} else if (ProductIDConfig.isGroupLife(product)) {
						coaCode = COACode.GROUP_LIFE_AGENT_PAYABLE;
					} else if (ProductIDConfig.isPublicLife(product)) {
						coaCode = COACode.ENDOWMENT_LIFE_AGENT_PAYABLE;
					} else if (ProductIDConfig.isSportMan(product)) {
						coaCode = COACode.SPORTMAN_AGENT_PAYABLE;
					} else if (ProductIDConfig.isSnakeBite(product)) {
						coaCode = COACode.SNAKE_BITE_AGENT_PAYABLE;
					} else {
						coaCode = COACode.LIFE_AGENT_PAYABLE;
					}
					break;
				case SNAKE_BITE_POLICY:
					coaCode = COACode.SNAKE_BITE_AGENT_PAYABLE;
					break;
				case MEDICAL_POLICY:
				case HEALTH_POLICY:
				case HEALTH_POLICY_BILL_COLLECTION:
					coaCode = COACode.HEALTH_AGENT_PAYABLE;
					break;
				case MEDICAL_BILL_COLLECTION:
					coaCode = COACode.LIFE_AGENT_PAYABLE;
					break;

				case CRITICAL_ILLNESS_POLICY:
				case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
					coaCode = COACode.CRITICAL_ILLNESS_AGENT_PAYABLE;
					break;
				case MICRO_HEALTH_POLICY:
					coaCode = COACode.MICRO_HEALTH_AGENT_PAYABLE;
					break;
				case PA_POLICY:
					coaCode = COACode.PA_AGENT_PAYABLE;
					break;
				case PERSON_TRAVEL_POLICY:
					coaCode = COACode.PERSON_TRAVEL_AGENT_PAYABLE;
					break;
				case GROUP_FARMER_PROPOSAL:
				case FARMER_POLICY:
					coaCode = COACode.FARMER_AGENT_PAYABLE;
					break;
				case SHORT_ENDOWMENT_LIFE_POLICY:
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					coaCode = COACode.SHORT_ENDOWMENT_AGENT_PAYABLE;
					break;
				case GROUP_MICRO_HEALTH:
					coaCode = COACode.MICROHEALTH_AGENT_COMMISSION;
					break;
				case STUDENT_LIFE_POLICY:
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					coaCode = COACode.STUDENT_LIFE_AGENT_PAYABLE;
					break;
				case PUBLIC_TERM_LIFE_POLICY:
					coaCode = COACode.PUBLICTERMLIFE_AGENT_PAYABLE;
					break;
				default:
					break;
			}

			accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(), currencyCode);
			commission = Utils.getTwoDecimalPoint(ac.getCommission());
			String narration = getNarrationAgent(payment, ac, isRenewal);
			String cur = payment.getCur();
			double rate = payment.getRate();
			TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRCREDIT, Status.TCV, commission, ac.getAgent().getId(), branch.getBranchCode(), accountName, receiptNo, narration, eno,
					ac.getReferenceNo(), ac.getReferenceType(), isRenewal, cur, rate);
			TLF tlf = tlfBuilder.getTLFInstance();
			if (ac.getReferenceType().equals(PolicyReferenceType.SNAKE_BITE_POLICY)) {
				tlf.setPaid(true);
			}

			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setAgentTransaction(true);
			tlf.setSettlementDate(new Date());
			tlf.setPolicyNo(policy.getPolicyNo());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(new Date());
			tlfRepository.save(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	private String getNarrationAgent(Payment payment, AgentCommission agentCommission, boolean isRenewal) {
		StringBuffer nrBf = new StringBuffer();
		double commission = 0.0;
		String agentName = "";
		String insuranceName = "";
		// Agent Commission payable for Fire Insurance(Product Name), Received
		// No to Agent Name for commission amount of Amount
		nrBf.append("Agent Commission payable for ");
		switch (payment.getReferenceType()) {

			case LIFE_POLICY:
				insuranceName = "Life Insurance, ";
				break;
			case LIFE_BILL_COLLECTION:
				insuranceName = "Life Bill collection, ";
				break;

			case MEDICAL_POLICY:
				insuranceName = "Medical Insurance, ";
				break;
			case HEALTH_POLICY:
				insuranceName = "Health Insurance, ";
				break;
			case HEALTH_POLICY_BILL_COLLECTION:
				insuranceName = "Health Bill collection, ";
				break;
			case MICRO_HEALTH_POLICY:
				insuranceName = "Micro Health Insurance, ";
				break;
			case CRITICAL_ILLNESS_POLICY:
				insuranceName = "Critical Illness Insurance, ";
				break;
			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
				insuranceName = "Critical Illness Bill collection, ";
				break;

			case PA_POLICY:
				insuranceName = "Personal Accident Insurance, ";
				break;

			case STUDENT_LIFE_POLICY:
				insuranceName = "Student Life Insurance, ";
				break;
			case STUDENT_LIFE_POLICY_BILL_COLLECTION:
				insuranceName = "Student Life Bill collection, ";
				break;
			default:
				break;
		}

		agentName = agentCommission.getAgent() == null ? "" : agentCommission.getAgent().getFullName();
		commission = agentCommission.getCommission();
		nrBf.append(insuranceName);
		nrBf.append(payment.getReceiptNo());
		nrBf.append(" to ");
		nrBf.append(agentName);
		nrBf.append(" for commission amount of ");
		nrBf.append(Utils.getCurrencyFormatString(commission));
		nrBf.append(".");

		return nrBf.toString();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_AgentCommissionDr(AgentCommission ac, boolean coInsureance, Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
			SalePoint salePoint) {
		try {
			String receiptNo = payment.getReceiptNo();
			String coaCode = null;
			String coaCodeMI = null;
			String accountName = null;
			double ownCommission = 0.0;
			Product product;
			TLF tlf = new TLF();
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			switch (ac.getReferenceType()) {

				case LIFE_POLICY:
				case LIFE_BILL_COLLECTION:
					product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
					// coaCode = ProductIDConfig.isFarmer(product) ?
					// COACode.FARMER_AGENT_COMMISSION : coInsureance ?
					// COACode.LIFE_SUNDRY : COACode.LIFE_AGENT_COMMISSION;
					if (coInsureance) {
						coaCode = COACode.LIFE_SUNDRY;
					} else if (ProductIDConfig.isFarmer(product)) {
						coaCode = COACode.FARMER_AGENT_COMMISSION;
					} else if (ProductIDConfig.isGroupLife(product)) {
						coaCode = COACode.GROUP_LIFE_AGENT_COMMISSION;
					} else if (ProductIDConfig.isPublicLife(product)) {
						coaCode = COACode.ENDOWMENT_LIFE_AGENT_COMMISSION;
					} else if (ProductIDConfig.isSportMan(product)) {
						coaCode = COACode.SPORTMAN_AGENT_COMMISSION;
					} else if (ProductIDConfig.isSnakeBite(product)) {
						coaCode = COACode.SNAKE_BITE_AGENT_COMMISSION;
					} else {
						coaCode = COACode.LIFE_AGENT_COMMISSION;
					}
					break;
				case SNAKE_BITE_POLICY:
					coaCode = COACode.SNAKE_BITE_AGENT_COMMISSION;
					break;
				case MEDICAL_POLICY:
				case HEALTH_POLICY:
				case MEDICAL_BILL_COLLECTION:
				case HEALTH_POLICY_BILL_COLLECTION:
					coaCode = COACode.HEALTH_AGENT_COMMISSION;
					break;
				case MICRO_HEALTH_POLICY:
					coaCode = COACode.MICROHEALTH_AGENT_COMMISSION;
					break;
				case CRITICAL_ILLNESS_POLICY:
				case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
					coaCode = COACode.CRITICAL_ILLNESS_AGENT_COMMISSION;
					break;
				// CARGO_POLICY is handled explicitly below

				case PA_POLICY:
					coaCode = COACode.PA_AGENT_COMMISSION;
					break;

				case PUBLIC_TERM_LIFE_POLICY:
					coaCode = COACode.PUBLICTERMLIFE_AGENT_COMMISSION;
					break;
				case GROUP_FARMER_PROPOSAL:
				case FARMER_POLICY:
					coaCode = COACode.FARMER_AGENT_COMMISSION;
					break;

				case SHORT_ENDOWMENT_LIFE_POLICY:
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					coaCode = COACode.SHORT_ENDOWMENT_AGENT_COMMISSION;
					break;
				case GROUP_MICRO_HEALTH:
					coaCode = COACode.MICROHEALTH_AGENT_COMMISSION;
					break;
				case STUDENT_LIFE_POLICY:
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					coaCode = COACode.STUDENT_LIFE_AGENT_COMMISSION;
					break;
				default:
					break;
			}
			String cur = payment.getCur();
			double rate = payment.getRate();
			String narration = getNarrationAgent(payment, ac, isRenewal);

			accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(), currencyCode);
			ownCommission = Utils.getTwoDecimalPoint(ac.getCommission());
			if (ac.getReferenceType().equals(PolicyReferenceType.SNAKE_BITE_POLICY)) {
				tlf.setPaid(true);
			}

			TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRDEBIT, Status.TDV, ownCommission, ac.getAgent().getId(), branch.getBranchCode(), accountName, receiptNo, narration,
					eno, ac.getReferenceNo(), ac.getReferenceType(), isRenewal, cur, rate);
			tlf = tlfBuilder.getTLFInstance();
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setSalePoint(salePoint);
			tlf.setSettlementDate(new Date());
			tlf.setAgentTransaction(true);
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(new Date());
			// setIDPrefixForInsert(tlf);
			tlfRepository.save(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	private List<AgentCommission> getAgentCommissionsForLifeBillCollection(LifePolicy lifePolicy, Payment payment, ShortEndowmentExtraValue extraValue) {
		List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
		String currencyCode = CurrencyUtils.getCurrencyCode(null);
		Product product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
		int paymentType = 0;
		double commissionPercent = 0.00;
		paymentType = lifePolicy.getPaymentType().getMonth();
		int toterm = payment.getToTerm();
		int fromterm = payment.getFromTerm();
		AgentCommissionEntryType entryType = null;
		double commission = 0.0;
		double rate = payment.getRate();
		double totalPremium = 0.0;
		int i = 0;
		int j = 0;
		int check = 12 / paymentType;
		j = toterm;
		boolean isExtraValueUsed = false;

		for (i = fromterm; i <= j; i++) {
			if (i <= check) {
				commissionPercent = product.getFirstCommission();
				entryType = AgentCommissionEntryType.UNDERWRITING;
			} else {
				commissionPercent = product.getRenewalCommission();
				entryType = AgentCommissionEntryType.RENEWAL;
			}

			if (commissionPercent > 0) {
				totalPremium = lifePolicy.getTotalBasicTermPremium() + lifePolicy.getTotalAddOnTermPremium();
				if (!isExtraValueUsed && extraValue != null && !extraValue.isPaid()) {
					totalPremium -= extraValue.getExtraAmount();
					isExtraValueUsed = true;
				}
				commission = Utils.getPercentOf(commissionPercent, totalPremium);
			}
			// agent commission is inserted according to each
			// payment term, not payment time
			// payment time can be many (eg; 3 to 5), but agent
			// commission will insert for 3, 4 and 5
			agentCommissionList.add(new AgentCommission(lifePolicy.getId(), payment.getReferenceType(), lifePolicy.getAgent(), commission, payment.getConfirmDate(),
					payment.getReceiptNo(), totalPremium, commissionPercent, entryType, rate, rate * commission, currencyCode, totalPremium, payment.getBpmsReceiptNo()));
		}

		return agentCommissionList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addAgentCommission(List<AgentCommission> agentCommissionList) {
		try {
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			for (AgentCommission agentCommission : agentCommissionList) {
				agentCommission.setRecorder(recorder);
			}
			agentCommissionRepository.saveAll(agentCommissionList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add agent commission No into TLF.", e);
		}
	}

	private Date getCalculatedEndDate(int paymentTimes, Date activePolicyEndDate, PaymentType paymentType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(activePolicyEndDate);
		int month = paymentType == null ? 0 : paymentType.getMonth();
		calendar.add(Calendar.MONTH, month * paymentTimes);
		return calendar.getTime();
	}

	// For interbranch

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewTLF_For_CashDebitForPremiumForInterBranch(List<AccountPayment> accountPaymentList, String customerId, Branch userBranch, String tlfNo, boolean isRenewal,
			String currencyCode, SalePoint salePoint, Branch proposalBranch) {
		try {
			double totalNetPremium = 0;
			double homeAmount = 0;
			String coaCode = null;
			Product product;
			Payment payment = accountPaymentList.get(0).getPayment();
			LifePolicy lifePolicy = lifePolicyRepository.getOne(payment.getReferenceNo());
			// TLF Home Amount
			if (isRenewal) {
				for (AccountPayment accountPayment : accountPaymentList) {
					totalNetPremium = totalNetPremium + accountPayment.getPayment().getRenewalNetPremium();
				}
			} else {
				for (AccountPayment accountPayment : accountPaymentList) {
					totalNetPremium = totalNetPremium + accountPayment.getPayment().getNetPremium();
				}
			}

			// TODO FIXME TOK don't set total amount as homeamount , wrong if
			// not
			// home currency
			homeAmount = totalNetPremium;
			// TLF COAID
			if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
				coaCode = paymentRepository.findCheckOfAccountNameByCode(userBranch.getReceivableACName(), userBranch.getBranchCode(), currencyCode);
			} else if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
				// coaCode =
				// paymentDAO.findCheckOfAccountNameByCode(COACode.CASH,
				// branch.getBranchCode(), currencyCode);
				coaCode = paymentRepository.findCheckOfAccountNameByCode(userBranch.getReceivableACName(), userBranch.getBranchCode(), currencyCode);
			} else if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
				String coaCodeType = "";
				switch (payment.getReferenceType()) {
					case LIFE_POLICY:
					case LIFE_BILL_COLLECTION:
						product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
						if (ProductIDConfig.isGroupLife(product)) {
							coaCodeType = COACode.GROUP_LIFE_PAYMENT_ORDER;
						} else if (ProductIDConfig.isPublicLife(product)) {
							coaCodeType = COACode.ENDOWMENT_LIFE_PAYMENT_ORDER;
						} else if (ProductIDConfig.isSnakeBite(product)) {
							coaCodeType = COACode.SNAKE_BITE_PAYMENT_ORDER;
						} else if (ProductIDConfig.isSportMan(product)) {
							coaCodeType = COACode.SPORTMAN_PAYMENT_ORDER;
						} else {
							coaCodeType = COACode.LIFE_PAYMENT_ORDER;
						}
						break;
					case MEDICAL_POLICY:
					case HEALTH_POLICY:
					case HEALTH_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.HEALTH_PAYMENT_ORDER;
						break;
					case GROUP_MICRO_HEALTH:
					case MICRO_HEALTH_POLICY:
						coaCodeType = COACode.MICRO_HEALTH_PAYMENT_ORDER;
						break;
					case CRITICAL_ILLNESS_POLICY:
					case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.CRITICAL_ILLNESS_PAYMENT_ORDER;
						break;
					case PA_POLICY:
						coaCodeType = COACode.PA_PAYMENT_ORDER;
						break;
					case TRAVEL_PROPOSAL:
					case PERSON_TRAVEL_POLICY:
						coaCodeType = COACode.PERSON_TRAVEL_PAYMENT_ORDER;
						break;
					case GROUP_FARMER_PROPOSAL:
					case FARMER_POLICY:
						coaCodeType = COACode.FARMER_PAYMENT_ORDER;
						break;
					case PUBLIC_TERM_LIFE_POLICY:
						coaCodeType = COACode.PUBLICTERMLIFE_PAYMENT_ORDER;
						break;
					case SHORT_ENDOWMENT_LIFE_POLICY:
					case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
						coaCodeType = COACode.SHORT_ENDOWMENT_PAYMENT_ORDER;
						break;
					case STUDENT_LIFE_POLICY:
					case STUDENT_LIFE_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.STUDENT_LIFE_PAYMENT_ORDER;
						break;
					default:
						break;
				}
				coaCode = paymentRepository.findCheckOfAccountNameByCode(userBranch.getReceivableACName(), userBranch.getBranchCode(), currencyCode);
			} else if (PaymentChannel.SUNDRY.equals(payment.getPaymentChannel())) {
				String coaCodeType = "";
				switch (payment.getReferenceType()) {
					case LIFE_POLICY:
					case LIFE_BILL_COLLECTION:
						coaCodeType = COACode.LIFE_SUNDRY;
						break;
					case MEDICAL_POLICY:
					case MEDICAL_BILL_COLLECTION:
						coaCodeType = COACode.MEDICAL_SUNDRY;
						break;
					case HEALTH_POLICY:
					case HEALTH_POLICY_BILL_COLLECTION:
					case GROUP_MICRO_HEALTH:
						coaCodeType = COACode.HEALTH_SUNDRY;
						break;
					case MICRO_HEALTH_POLICY:
						coaCodeType = COACode.MICRO_HEALTH_SUNDRY;
						break;
					case CRITICAL_ILLNESS_POLICY:
					case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.CRITICAL_ILLNESS_SUNDRY;
						break;
					case PA_POLICY:
						coaCodeType = COACode.PA_SUNDRY;
						break;
					case PERSON_TRAVEL_POLICY:
						coaCodeType = COACode.PERSON_TRAVEL_SUNDRY;
						break;
					case GROUP_FARMER_PROPOSAL:
					case FARMER_POLICY:
						coaCodeType = COACode.FARMER_SUNDRY;
						break;
					case PUBLIC_TERM_LIFE_POLICY:
						coaCodeType = COACode.PUBLICTERMLIFE_SUNDRY;
						break;

					case TRAVEL_PROPOSAL:
						coaCodeType = COACode.TRAVEL_SUNDRY;
						break;
					case SHORT_ENDOWMENT_LIFE_POLICY:
					case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
						coaCodeType = COACode.SHORT_ENDOWMENT_SUNDRY;
						break;
					case STUDENT_LIFE_POLICY:
					case STUDENT_LIFE_POLICY_BILL_COLLECTION:
						coaCodeType = COACode.STUDENT_LIFE_SUNDRY;
						break;
					default:
						break;
				}
				coaCode = paymentRepository.findCheckOfAccountNameByCode(userBranch.getReceivableACName(), userBranch.getBranchCode(), currencyCode);
			}

			TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, proposalBranch.getBranchCode(), coaCode, tlfNo,
					getNarrationReceiablePremiumForInterBranch(payment, isRenewal, userBranch.getName()), payment, isRenewal);
			TLF tlf = tlfBuilder.getTLFInstance();
			tlf.setSalePoint(salePoint);
			tlf.setSettlementDate(payment.getConfirmDate());
			tlf.setPaid(true);
			tlf.setPolicyNo(lifePolicy.getPolicyNo());
			tlf.getCommonCreateAndUpateMarks().setCreatedDate(payment.getConfirmDate());
			// setIDPrefixForInsert(tlf);
			tlf.setPaymentChannel(payment.getPaymentChannel());
			tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
			tlfRepository.save(tlf);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new TLF", e);
		}
	}

	private String getNarrationReceiablePremiumForInterBranch(Payment payment, boolean isRenewal, String branchName) {
		StringBuffer nrBf = new StringBuffer();
		String customerName = "";
		String premiumString = "";
		int totalInsuredPerson = 0;
		double si = 0.0;
		String unit = "";
		double premium = 0.0;

		if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
			nrBf.append(" Receivable from " + "(" + branchName + ")");
			nrBf.append(" Accrued");
			switch (payment.getReferenceType()) {
				case FARMER_POLICY:
				case LIFE_POLICY:
					premiumString = " Life Premium ";
					Optional<LifePolicy> lifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifePolicy.get().getTotalSumInsured();
					premium = (lifePolicy.get().isEndorsementApplied()) ? lifePolicy.get().getTotalEndorsementPremium() : lifePolicy.get().getTotalPremium();
					customerName = lifePolicy.get().getCustomerName();
					totalInsuredPerson = lifePolicy.get().getInsuredPersonInfo().size() > 1 ? lifePolicy.get().getInsuredPersonInfo().size() : 0;
					break;
				case LIFE_BILL_COLLECTION:
					premiumString = " Life Bill Collection ";
					Optional<LifePolicy> lifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifeBillPolicy.get().getTotalSumInsured();
					premium = lifeBillPolicy.get().getTotalPremium();
					customerName = lifeBillPolicy.get().getCustomerName();
					break;

				case SHORT_ENDOWMENT_LIFE_POLICY:
					premiumString = " Short Term Endowment Life Premium ";
					Optional<LifePolicy> seLifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = seLifePolicy.get().getTotalSumInsured();
					premium = seLifePolicy.get().getTotalPremium();
					customerName = seLifePolicy.get().getCustomerName();
					break;
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					premiumString = " Short Term Endowment Life Bill Collection ";
					Optional<LifePolicy> seLifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = seLifeBillPolicy.get().getTotalSumInsured();
					premium = seLifeBillPolicy.get().getTotalPremium();
					customerName = seLifeBillPolicy.get().getCustomerName();
					break;
				case STUDENT_LIFE_POLICY:
					premiumString = "Student Life Premium ";
					Optional<LifePolicy> studentPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentPolicy.get().getTotalSumInsured();
					premium = (studentPolicy.get().isEndorsementApplied()) ? studentPolicy.get().getTotalEndorsementPremium() : studentPolicy.get().getTotalPremium();
					customerName = studentPolicy.get().getCustomerName();
					break;
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					premiumString = " Student Life Bill Collection ";
					Optional<LifePolicy> studentBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentBillPolicy.get().getTotalSumInsured();
					premium = studentBillPolicy.get().getTotalPremium();
					customerName = studentBillPolicy.get().getCustomerName();
					break;
				default:
					break;
			}
			nrBf.append(premiumString);
			nrBf.append("to be paid by ");
			nrBf.append(payment.getReceiptNo());
			nrBf.append(" from ");
			nrBf.append(customerName);
			if (payment.getReferenceType().equals(PolicyReferenceType.MEDICAL_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)
					|| payment.getReferenceType().equals(PolicyReferenceType.MICRO_HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)
					|| PolicyReferenceType.PERSON_TRAVEL_POLICY.equals(payment.getReferenceType())) {
				nrBf.append(" for ");
				nrBf.append(unit);
			} else {
				nrBf.append(" for Sum Insured ");
				nrBf.append(Utils.getCurrencyFormatString(si));
			}

			if (totalInsuredPerson > 0
					&& (PolicyReferenceType.GROUP_FARMER_PROPOSAL.equals(payment.getReferenceType()) || PolicyReferenceType.LIFE_POLICY.equals(payment.getReferenceType()))) {
				nrBf.append(" and for total number of insured person ");
				nrBf.append(Integer.toString(totalInsuredPerson));
			}

			nrBf.append(" and the premium amount of ");
			nrBf.append(Utils.getCurrencyFormatString(premium));
			nrBf.append(". ");

		} else {
			nrBf.append(" Receivable from " + "(" + branchName + ")");
			nrBf.append(" Being amount of ");
			switch (payment.getReferenceType()) {
				case FARMER_POLICY:
				case LIFE_POLICY:
					nrBf.append(" Life Premium ");
					Optional<LifePolicy> lifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifePolicy.get().getTotalSumInsured();
					premium = (lifePolicy.get().isEndorsementApplied()) ? lifePolicy.get().getTotalEndorsementPremium() : lifePolicy.get().getTotalPremium();
					customerName = lifePolicy.get().getCustomerName();
					totalInsuredPerson = lifePolicy.get().getInsuredPersonInfo().size() > 1 ? lifePolicy.get().getInsuredPersonInfo().size() : 0;
					break;
				case LIFE_BILL_COLLECTION:
					premiumString = " Life Bill Collection ";
					Optional<LifePolicy> lifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifeBillPolicy.get().getTotalSumInsured();
					premium = (lifeBillPolicy.get().isEndorsementApplied()) ? lifeBillPolicy.get().getTotalEndorsementPremium() : lifeBillPolicy.get().getTotalPremium();
					break;
				case SHORT_ENDOWMENT_LIFE_POLICY:
					nrBf.append(" Short Term Endowment Life Premium ");
					Optional<LifePolicy> selifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = selifePolicy.get().getTotalSumInsured();
					premium = selifePolicy.get().getTotalPremium();
					customerName = selifePolicy.get().getCustomerName();
					break;
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					nrBf.append(" Short Term Endowment Life Bill Collection ");
					Optional<LifePolicy> selifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = selifeBillPolicy.get().getTotalSumInsured();
					premium = selifeBillPolicy.get().getTotalPremium();
					customerName = selifeBillPolicy.get().getCustomerName();
					break;

				case STUDENT_LIFE_POLICY:
					nrBf.append(" Life Premium ");
					Optional<LifePolicy> studentPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentPolicy.get().getTotalSumInsured();
					premium = (studentPolicy.get().isEndorsementApplied()) ? studentPolicy.get().getTotalEndorsementPremium() : studentPolicy.get().getTotalPremium();
					customerName = studentPolicy.get().getCustomerName();
					totalInsuredPerson = studentPolicy.get().getInsuredPersonInfo().size() > 1 ? studentPolicy.get().getInsuredPersonInfo().size() : 0;
					break;
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					premiumString = "Student Life Bill Collection ";
					Optional<LifePolicy> studentBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentBillPolicy.get().getTotalSumInsured();
					premium = (studentBillPolicy.get().isEndorsementApplied()) ? studentBillPolicy.get().getTotalEndorsementPremium() : studentBillPolicy.get().getTotalPremium();
					break;
				default:
					break;
			}
			nrBf.append(premiumString);
			nrBf.append(" received by ");
			nrBf.append(payment.getReceiptNo());
			nrBf.append(" from ");
			nrBf.append(customerName);
			if (payment.getReferenceType().equals(PolicyReferenceType.MEDICAL_POLICY) || PolicyReferenceType.PERSON_TRAVEL_POLICY.equals(payment.getReferenceType())
					|| payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)
					|| payment.getReferenceType().equals(PolicyReferenceType.MICRO_HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)) {
				nrBf.append(" for ");
				nrBf.append(unit);
			} else {
				nrBf.append(" for Sum Insured ");
				nrBf.append(Utils.getCurrencyFormatString(si));
			}
			if (totalInsuredPerson > 0
					&& (PolicyReferenceType.GROUP_FARMER_PROPOSAL.equals(payment.getReferenceType()) || PolicyReferenceType.LIFE_POLICY.equals(payment.getReferenceType()))) {
				nrBf.append(" and for total number of insured person ");
				nrBf.append(Integer.toString(totalInsuredPerson));
			}
			nrBf.append(" and the premium amount of ");
			nrBf.append(Utils.getCurrencyFormatString(premium));
			nrBf.append(". ");
		}

		return nrBf.toString();
	}

	private String getNarrationPremiumPayableForInterBranch(Payment payment, String branchName) {
		StringBuffer nrBf = new StringBuffer();
		String customerName = "";
		String premiumString = "";
		int totalInsuredPerson = 0;
		double si = 0.0;
		String unit = "";
		double premium = 0.0;

		if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
			nrBf.append(" Payable to " + "(" + branchName + ") ");
			nrBf.append(" Accrued");
			switch (payment.getReferenceType()) {
				case FARMER_POLICY:
				case LIFE_POLICY:
					premiumString = " Life Premium ";
					Optional<LifePolicy> lifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifePolicy.get().getTotalSumInsured();
					premium = (lifePolicy.get().isEndorsementApplied()) ? lifePolicy.get().getTotalEndorsementPremium() : lifePolicy.get().getTotalPremium();
					customerName = lifePolicy.get().getCustomerName();
					totalInsuredPerson = lifePolicy.get().getInsuredPersonInfo().size() > 1 ? lifePolicy.get().getInsuredPersonInfo().size() : 0;
					break;
				case LIFE_BILL_COLLECTION:
					premiumString = " Life Bill Collection ";
					Optional<LifePolicy> lifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifeBillPolicy.get().getTotalSumInsured();
					premium = lifeBillPolicy.get().getTotalPremium();
					customerName = lifeBillPolicy.get().getCustomerName();
					break;
				case STUDENT_LIFE_POLICY:
					premiumString = "Student Life Premium ";
					Optional<LifePolicy> studentPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentPolicy.get().getTotalSumInsured();
					premium = (studentPolicy.get().isEndorsementApplied()) ? studentPolicy.get().getTotalEndorsementPremium() : studentPolicy.get().getTotalPremium();
					customerName = studentPolicy.get().getCustomerName();
					break;
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					premiumString = " Student Life Bill Collection ";
					Optional<LifePolicy> studentBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentBillPolicy.get().getTotalSumInsured();
					premium = studentBillPolicy.get().getTotalPremium();
					customerName = studentBillPolicy.get().getCustomerName();
					break;
				default:
					break;
			}
			nrBf.append(premiumString);
			nrBf.append("to be paid by ");
			nrBf.append(payment.getReceiptNo());
			nrBf.append(" from ");
			nrBf.append(customerName);
			if (payment.getReferenceType().equals(PolicyReferenceType.MEDICAL_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)
					|| payment.getReferenceType().equals(PolicyReferenceType.MICRO_HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)
					|| PolicyReferenceType.PERSON_TRAVEL_POLICY.equals(payment.getReferenceType())) {
				nrBf.append(" for ");
				nrBf.append(unit);
			} else {
				nrBf.append(" for Sum Insured ");
				nrBf.append(Utils.getCurrencyFormatString(si));
			}

			if (totalInsuredPerson > 0
					&& (PolicyReferenceType.GROUP_FARMER_PROPOSAL.equals(payment.getReferenceType()) || PolicyReferenceType.LIFE_POLICY.equals(payment.getReferenceType()))) {
				nrBf.append(" and for total number of insured person ");
				nrBf.append(Integer.toString(totalInsuredPerson));
			}

			nrBf.append(" and the premium amount of ");
			nrBf.append(Utils.getCurrencyFormatString(premium));
			nrBf.append(". ");

		} else {

			nrBf.append(" Payable to " + "(" + branchName + ")");
			nrBf.append(" Being amount of ");
			switch (payment.getReferenceType()) {

				case FARMER_POLICY:
				case LIFE_POLICY:
					nrBf.append(" Life Premium ");
					Optional<LifePolicy> lifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifePolicy.get().getTotalSumInsured();
					premium = (lifePolicy.get().isEndorsementApplied()) ? lifePolicy.get().getTotalEndorsementPremium() : lifePolicy.get().getTotalPremium();
					customerName = lifePolicy.get().getCustomerName();
					totalInsuredPerson = lifePolicy.get().getInsuredPersonInfo().size() > 1 ? lifePolicy.get().getInsuredPersonInfo().size() : 0;
					break;
				case LIFE_BILL_COLLECTION:
					premiumString = " Life Bill Collection ";
					Optional<LifePolicy> lifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = lifeBillPolicy.get().getTotalSumInsured();
					premium = (lifeBillPolicy.get().isEndorsementApplied()) ? lifeBillPolicy.get().getTotalEndorsementPremium() : lifeBillPolicy.get().getTotalPremium();
					break;
				case SHORT_ENDOWMENT_LIFE_POLICY:
					nrBf.append(" Short Term Endowment Life Premium ");
					Optional<LifePolicy> selifePolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = selifePolicy.get().getTotalSumInsured();
					premium = selifePolicy.get().getTotalPremium();
					customerName = selifePolicy.get().getCustomerName();
					break;
				case SHORT_ENDOWMENT_LIFE_BILL_COLLECTION:
					nrBf.append(" Short Term Endowment Life Bill Collection ");
					Optional<LifePolicy> selifeBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = selifeBillPolicy.get().getTotalSumInsured();
					premium = selifeBillPolicy.get().getTotalPremium();
					customerName = selifeBillPolicy.get().getCustomerName();
					break;

				case STUDENT_LIFE_POLICY:
					nrBf.append(" Life Premium ");
					Optional<LifePolicy> studentPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentPolicy.get().getTotalSumInsured();
					premium = (studentPolicy.get().isEndorsementApplied()) ? studentPolicy.get().getTotalEndorsementPremium() : studentPolicy.get().getTotalPremium();
					customerName = studentPolicy.get().getCustomerName();
					totalInsuredPerson = studentPolicy.get().getInsuredPersonInfo().size() > 1 ? studentPolicy.get().getInsuredPersonInfo().size() : 0;
					break;
				case STUDENT_LIFE_POLICY_BILL_COLLECTION:
					premiumString = "Student Life Bill Collection ";
					Optional<LifePolicy> studentBillPolicy = lifePolicyRepository.findById(payment.getReferenceNo());
					si = studentBillPolicy.get().getTotalSumInsured();
					premium = (studentBillPolicy.get().isEndorsementApplied()) ? studentBillPolicy.get().getTotalEndorsementPremium() : studentBillPolicy.get().getTotalPremium();
					break;
				default:
					break;
			}
			nrBf.append(premiumString);
			nrBf.append(" received by ");
			nrBf.append(payment.getReceiptNo());
			nrBf.append(" from ");
			nrBf.append(customerName);
			if (payment.getReferenceType().equals(PolicyReferenceType.MEDICAL_POLICY) || PolicyReferenceType.PERSON_TRAVEL_POLICY.equals(payment.getReferenceType())
					|| payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION)
					|| payment.getReferenceType().equals(PolicyReferenceType.MICRO_HEALTH_POLICY) || payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY)
					|| payment.getReferenceType().equals(PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION)) {
				nrBf.append(" for ");
				nrBf.append(unit);
			} else {
				nrBf.append(" for Sum Insured ");
				nrBf.append(Utils.getCurrencyFormatString(si));
			}
			if (totalInsuredPerson > 0
					&& (PolicyReferenceType.GROUP_FARMER_PROPOSAL.equals(payment.getReferenceType()) || PolicyReferenceType.LIFE_POLICY.equals(payment.getReferenceType()))) {
				nrBf.append(" and for total number of insured person ");
				nrBf.append(Integer.toString(totalInsuredPerson));
			}
			nrBf.append(" and the premium amount of ");
			nrBf.append(Utils.getCurrencyFormatString(premium));
			nrBf.append(". ");
		}

		return nrBf.toString();
	}

}
