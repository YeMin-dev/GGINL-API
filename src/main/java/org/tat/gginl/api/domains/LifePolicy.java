package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.IInsuredItem;
import org.tat.gginl.api.common.IPolicy;
import org.tat.gginl.api.common.ISorter;
import org.tat.gginl.api.common.PolicyInsuredPerson;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.EndorsementStatus;
import org.tat.gginl.api.common.emumdata.InsuranceType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyStatus;

@Entity
@Table(name = TableName.LIFEPOLICY)
@TableGenerator(name = "LIFEPOLICY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICY_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class LifePolicy implements IPolicy, Serializable, ISorter {
	private static final long serialVersionUID = 2379164707215020929L;

	@Transient
	private String id;
	@Transient
	private String prefix;
	@Transient
	private boolean nilExcess;
	private boolean delFlag;
	private boolean isCoinsuranceApplied;
	private boolean isEndorsementApplied;
	private boolean isSkipPaymentTLF;
	private int lastPaymentTerm;
	private int printCount;
	private double totalDiscountAmount;
	private double standardExcess;
	private String policyNo;
	private String endorsementNo;
	private boolean isMigrated;
	private boolean hasExtraValue;

	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYSTARTDATE")
	private Date activedPolicyStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYENDDATE")
	private Date activedPolicyEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentEndDate;

	@Column(name = "RENEWALCONFIRMDATE")
	@Temporal(TemporalType.DATE)
	private Date renewalConfirmDate;

	@Enumerated(EnumType.STRING)
	private PolicyStatus policyStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFERRALID", referencedColumnName = "ID")
	private Customer referral;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPROVERID", referencedColumnName = "ID")
	private User approvedBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEMANID", referencedColumnName = "ID")
	private SaleMan saleMan;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PROPOSALID")
	private LifeProposal lifeProposal;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifePolicy", orphanRemoval = true)
	private List<PolicyInsuredPerson> policyInsuredPersonList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifePolicy", orphanRemoval = true)
	private List<LifePolicyAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> customerMedicalCheckUpAttachmentList;

	@OneToOne
	@JoinColumn(name = "SALEPOINTID")
	private SalePoint salePoint;

	@Transient
	private PaymentChannel paymentChannel;

	@Transient
	private String fromBank;

	@Transient
	private String toBank;

	@Version
	private int version;

	@Transient
	private String chequeNo;

	@Transient
	private String bpmsInsuredPersonId;

	@Transient
	private String bpmsReceiptNo;

	@Embedded
	private CommonCreateAndUpateMarks recorder;

	public LifePolicy() {
	}

	public LifePolicy(LifeProposal lifeProposal) {
		this.customer = lifeProposal.getCustomer();
		this.referral = lifeProposal.getReferral();
		this.organization = lifeProposal.getOrganization();
		this.saleMan = lifeProposal.getSaleMan();
		this.branch = lifeProposal.getBranch();
		this.paymentType = lifeProposal.getPaymentType();
		this.agent = lifeProposal.getAgent();
		this.lifeProposal = lifeProposal;
		this.isSkipPaymentTLF = lifeProposal.isSkipPaymentTLF();
		this.isEndorsementApplied = false;

		if (null != lifeProposal.getSalePoint()) {
			this.salePoint = lifeProposal.getSalePoint();
		}

		for (ProposalInsuredPerson person : lifeProposal.getProposalInsuredPersonList()) {
			addInsuredPerson(new PolicyInsuredPerson(person));
		}

		for (LifeProposalAttachment attach : lifeProposal.getAttachmentList()) {
			addLifePolicyAttachment(new LifePolicyAttachment(attach));
		}
		for (Attachment medicalAttach : lifeProposal.getCustomerMedicalCheckUpAttachmentList()) {
			addCustomerMedicalChkUpAttachment(new Attachment(medicalAttach));
		}
	}

	public LifePolicy(LifePolicyHistory history) {
		this.id = history.getPolicyReferenceNo();
		this.nilExcess = history.isNilExcess();
		this.isCoinsuranceApplied = history.isCoinsuranceApplied();
		this.isEndorsementApplied = history.isActiveEndorsement();
		this.lastPaymentTerm = history.getLastPaymentTerm();
		this.printCount = history.getPrintCount();
		this.totalDiscountAmount = history.getTotalDiscountAmount();
		this.standardExcess = history.getStandardExcess();
		this.policyNo = history.getPolicyNo();
		this.endorsementNo = history.getEndorsementNo();
		this.commenmanceDate = history.getCommenmanceDate();
		this.activedPolicyStartDate = history.getActivedPolicyStartDate();
		this.activedPolicyEndDate = history.getActivedPolicyEndDate();
		this.policyStatus = history.getPolicyStatus();
		this.customer = history.getCustomer();
		this.referral = history.getReferral();
		this.organization = history.getOrganization();
		this.approvedBy = history.getApprovedBy();
		this.branch = history.getBranch();
		this.paymentType = history.getPaymentType();
		this.agent = history.getAgent();
		this.saleMan = history.getSaleMan();
		this.lifeProposal = history.getLifeProposal();

		for (PolicyInsuredPersonHistory iPerson : history.getPolicyInsuredPersonList()) {
			addInsuredPerson(new PolicyInsuredPerson(iPerson));
		}

		for (LifePolicyAttachmentHistory attachment : history.getAttachmentList()) {
			addLifePolicyAttachment(new LifePolicyAttachment(attachment));
		}

		for (Attachment medicalAttach : lifeProposal.getCustomerMedicalCheckUpAttachmentList()) {
			addCustomerMedicalChkUpAttachment(medicalAttach);
		}

	}

	public String getBpmsInsuredPersonId() {
		return bpmsInsuredPersonId;
	}

	public void setBpmsInsuredPersonId(String bpmsInsuredPersonId) {
		this.bpmsInsuredPersonId = bpmsInsuredPersonId;
	}

	public CommonCreateAndUpateMarks getRecorder() {
		return recorder;
	}

	public void setRecorder(CommonCreateAndUpateMarks recorder) {
		this.recorder = recorder;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public void addInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		policyInsuredPerson.setLifePolicy(this);
		getPolicyInsuredPersonList().add(policyInsuredPerson);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICY_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	public void overwriteId(String id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public List<PolicyInsuredPerson> getInsuredPersonInfo() {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		} else {
			for (PolicyInsuredPerson policyInsuredPerson : policyInsuredPersonList) {
				if (EndorsementStatus.TERMINATE.equals(policyInsuredPerson.getEndorsementStatus())) {
					policyInsuredPersonList.remove(policyInsuredPerson);
				}
			}
		}
		return policyInsuredPersonList;
	}

	public void setVehicleInfo(List<PolicyInsuredPerson> insuredPersonInfo) {
		this.policyInsuredPersonList = insuredPersonInfo;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public List<LifePolicyAttachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<LifePolicyAttachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<LifePolicyAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public SaleMan getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(SaleMan saleMan) {
		this.saleMan = saleMan;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Customer getReferral() {
		return referral;
	}

	public void setReferral(Customer referral) {
		this.referral = referral;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public boolean isMigrated() {
		return isMigrated;
	}

	public void setMigrated(boolean isMigrated) {
		this.isMigrated = isMigrated;
	}

	public boolean isHasExtraValue() {
		return hasExtraValue;
	}

	public void setHasExtraValue(boolean hasExtraValue) {
		this.hasExtraValue = hasExtraValue;
	}

	public boolean isNilExcess() {
		return nilExcess;
	}

	public void setNilExcess(boolean nilExcess) {
		this.nilExcess = nilExcess;
	}

	public double getStandardExcess() {
		return standardExcess;
	}

	public void setStandardExcess(double standardExcess) {
		this.standardExcess = standardExcess;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public List<PolicyInsuredPerson> getPolicyInsuredPersonList() {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		}
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<PolicyInsuredPerson> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public void addPolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPersonInfo) {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		}
		policyInsuredPersonInfo.setLifePolicy(this);
		policyInsuredPersonList.add(policyInsuredPersonInfo);
	}

	public void addLifePolicyAttachment(LifePolicyAttachment attachment) {
		attachment.setLifePolicy(this);
		getAttachmentList().add(attachment);
	}

	public void addCustomerMedicalChkUpAttachment(Attachment attachment) {
		if (customerMedicalCheckUpAttachmentList == null) {
			customerMedicalCheckUpAttachmentList = new ArrayList<Attachment>();
		}
		getCustomerMedicalCheckUpAttachmentList().add(attachment);

	}

	public String getProposalNo() {
		return lifeProposal.getProposalNo();
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isCoinsuranceApplied() {
		return isCoinsuranceApplied;
	}

	public void setCoinsuranceApplied(boolean isCoinsuranceApplied) {
		this.isCoinsuranceApplied = isCoinsuranceApplied;
	}

	public boolean isEndorsementApplied() {
		return isEndorsementApplied;
	}

	public void setEndorsementApplied(boolean isEndorsementApplied) {
		this.isEndorsementApplied = isEndorsementApplied;
	}

	public String getEndorsementNo() {
		return endorsementNo;
	}

	public void setEndorsementNo(String endorsementNo) {
		this.endorsementNo = endorsementNo;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public int getLastPaymentTerm() {
		return lastPaymentTerm;
	}

	public void setLastPaymentTerm(int lastPaymentTerm) {
		this.lastPaymentTerm = lastPaymentTerm;
	}

	public ProductGroup getProductGroup() {
		if (this.policyInsuredPersonList != null && !this.policyInsuredPersonList.isEmpty()) {
			return this.policyInsuredPersonList.get(0).getProduct().getProductGroup();
		}
		return null;
	}

	/**
	 * @see org.ace.insurance.common.interfaces.IPolicy#getInsuredItems()
	 */
	public List<IInsuredItem> getInsuredItems() {
		List<IInsuredItem> insuredItems = Collections.emptyList();
		List<PolicyInsuredPerson> personList = getPolicyInsuredPersonList();
		if (personList != null) {
			insuredItems = new ArrayList<IInsuredItem>();
			for (PolicyInsuredPerson person : personList) {
				insuredItems.add(person);
			}
		}
		return insuredItems;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public InsuranceType getInsuranceType() {
		return InsuranceType.LIFE;
	}

	public Date getRenewalConfirmDate() {
		return renewalConfirmDate;
	}

	public void setRenewalConfirmDate(Date renewalConfirmDate) {
		this.renewalConfirmDate = renewalConfirmDate;
	}

	public boolean isSkipPaymentTLF() {
		return isSkipPaymentTLF;
	}

	public void setSkipPaymentTLF(boolean isSkipPaymentTLF) {
		this.isSkipPaymentTLF = isSkipPaymentTLF;
	}

	public SalePoint getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalePoint salePoint) {
		this.salePoint = salePoint;
	}

	public Date getPaymentEndDate() {
		return paymentEndDate;
	}

	public void setPaymentEndDate(Date paymentEndDate) {
		this.paymentEndDate = paymentEndDate;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

	/* System Auto Calculate Process */
	public double getPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			if (!EndorsementStatus.TERMINATE.equals(pi.getEndorsementStatus())) {
				premium = Utils.getTwoDecimalPoint(premium + (pi.getPremium()));
			}
		}
		return premium;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
		}
		return premium;
	}

	public double getSumInsured() {
		double sumInsured = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			sumInsured = sumInsured + pi.getSumInsured();
		}
		return sumInsured;
	}

	public double getAddOnSumInsured() {
		double sumInsured = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			sumInsured = sumInsured + pi.getAddOnSumInsure();
		}
		return sumInsured;
	}

	public double getTotalPremium() {
		return getPremium() + getAddOnPremium();
	}

	public double getTotalSumInsured() {
		return getSumInsured() + getAddOnSumInsured();
	}

	public String getTotalSumInsuredString() {
		return Utils.getCurrencyFormatString(getSumInsured() + getAddOnSumInsured());
	}

	public double getTotalTermPremium() {
		return Utils.getTwoDecimalPoint(getTotalBasicTermPremium() + getTotalAddOnTermPremium());
	}

	public double getTotalBasicTermPremium() {
		double termPermium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getBasicTermPremium());
		}
		return termPermium;
	}

	public String getTotalBasicTermPremiumString() {
		double termPermium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getBasicTermPremium());
		}
		return Utils.getCurrencyFormatString(termPermium);
	}

	public double getTotalAddOnTermPremium() {
		double termPermium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getAddOnTermPremium());
		}
		return termPermium;
	}

	public double getEndorsementBasicPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			premium = Utils.getTwoDecimalPoint(premium + pi.getEndorsementNetBasicPremium());
		}
		return premium;
	}

	public double getEndorsementAddOnPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			premium = Utils.getTwoDecimalPoint(premium + pi.getEndorsementNetAddonPremium());
		}
		return premium;
	}

	public double getTotalEndorsementPremium() {
		return Utils.getTwoDecimalPoint(getEndorsementBasicPremium() + getEndorsementAddOnPremium());
	}

	public double getAgentCommission() {
		double totalCommission = 0.0;
		if (agent != null) {
			for (PolicyInsuredPerson pip : policyInsuredPersonList) {
				double commissionPercent = pip.getProduct().getFirstCommission();
				if (commissionPercent > 0) {
					double totalPremium = pip.getTotalTermPermium();
					double commission = Utils.getPercentOf(commissionPercent, totalPremium);
					totalCommission = totalCommission + commission;
				}
			}
		}
		return Utils.getTwoDecimalPoint(totalCommission);
	}

	public double getRenewalAgentCommission() {
		double totalCommission = 0.0;
		if (agent != null) {
			for (PolicyInsuredPerson pip : policyInsuredPersonList) {
				double commissionPercent = pip.getProduct().getRenewalCommission();
				if (commissionPercent > 0) {
					double totalPremium = pip.getTotalTermPermium();
					double commission = Utils.getPercentOf(commissionPercent, totalPremium);
					totalCommission = totalCommission + commission;
				}
			}
		}
		return Utils.getTwoDecimalPoint(totalCommission);
	}

	public String getCustomerId() {
		if (customer != null) {
			return customer.getId();
		}
		if (organization != null) {
			return organization.getId();
		}
		return null;
	}

	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getName();
		}
		return null;
	}

	public String getCustomerNRC() {
		if (customer != null) {
			return customer.getFullIdNo();
		}
		return null;
	}

	public String getOrganizationName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getName();
		}
		return null;
	}

	public String getOwnerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getOwnerName();
		}
		return null;
	}

	public String getCustomerAddress() {
		if (customer != null) {
			return customer.getFullAddress();
		}
		if (organization != null) {
			return organization.getFullAddress();
		}
		return null;
	}

	public String getCustomerPhoneNo() {
		if (customer != null) {
			return customer.getContentInfo().getPhone();
		}
		if (organization != null) {
			return organization.getContentInfo().getPhone();
		}
		return null;
	}

	public String getCustomerEmail() {
		if (customer != null) {
			return customer.getContentInfo().getEmail();
		}
		if (organization != null) {
			return organization.getContentInfo().getEmail();
		}
		return null;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		} else if (saleMan != null) {
			return saleMan.getFullName();
		} else if (referral != null) {
			return referral.getFullName();
		}
		return null;
	}

	public String agentNameWithLiscenceNo() {
		if (agent != null) {
			return agent.getFullName() + "( " + agent.getLiscenseNo() + " )";
		}
		return "";
	}

	public int getTotalPaymentTimes() {
		if (paymentType.getMonth() == 0) {
			return 1;
		} else {
			int totalPaymentTimes = 0;
			int paymentMonths = 0;
			for (PolicyInsuredPerson i : policyInsuredPersonList) {
				if (i.getPeriodMonth() > paymentMonths) {
					paymentMonths = i.getPeriodMonth();
				}
			}
			totalPaymentTimes = paymentMonths / paymentType.getMonth();
			return totalPaymentTimes;
		}
	}

	public List<Attachment> getCustomerMedicalCheckUpAttachmentList() {
		return customerMedicalCheckUpAttachmentList;
	}

	public void setCustomerMedicalCheckUpAttachmentList(List<Attachment> customerMedicalCheckUpAttachmentList) {
		this.customerMedicalCheckUpAttachmentList = customerMedicalCheckUpAttachmentList;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getGroupFarmerProposalNo() {
		return lifeProposal.getGroupFarmerProposal().getProposalNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((approvedBy == null) ? 0 : approvedBy.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + (delFlag ? 1231 : 1237);
		result = prime * result + ((endorsementNo == null) ? 0 : endorsementNo.hashCode());
		result = prime * result + (hasExtraValue ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isCoinsuranceApplied ? 1231 : 1237);
		result = prime * result + (isEndorsementApplied ? 1231 : 1237);
		result = prime * result + (isMigrated ? 1231 : 1237);
		result = prime * result + (isSkipPaymentTLF ? 1231 : 1237);
		result = prime * result + lastPaymentTerm;
		result = prime * result + ((lifeProposal == null) ? 0 : lifeProposal.hashCode());
		result = prime * result + (nilExcess ? 1231 : 1237);
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + printCount;
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((renewalConfirmDate == null) ? 0 : renewalConfirmDate.hashCode());
		result = prime * result + ((saleMan == null) ? 0 : saleMan.hashCode());
		long temp;
		temp = Double.doubleToLongBits(standardExcess);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalDiscountAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + version;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LifePolicy other = (LifePolicy) obj;
		if (activedPolicyEndDate == null) {
			if (other.activedPolicyEndDate != null)
				return false;
		} else if (!activedPolicyEndDate.equals(other.activedPolicyEndDate))
			return false;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (approvedBy == null) {
			if (other.approvedBy != null)
				return false;
		} else if (!approvedBy.equals(other.approvedBy))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (commenmanceDate == null) {
			if (other.commenmanceDate != null)
				return false;
		} else if (!commenmanceDate.equals(other.commenmanceDate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (delFlag != other.delFlag)
			return false;
		if (endorsementNo == null) {
			if (other.endorsementNo != null)
				return false;
		} else if (!endorsementNo.equals(other.endorsementNo))
			return false;
		if (hasExtraValue != other.hasExtraValue)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCoinsuranceApplied != other.isCoinsuranceApplied)
			return false;
		if (isEndorsementApplied != other.isEndorsementApplied)
			return false;
		if (isMigrated != other.isMigrated)
			return false;
		if (isSkipPaymentTLF != other.isSkipPaymentTLF)
			return false;
		if (lastPaymentTerm != other.lastPaymentTerm)
			return false;
		if (lifeProposal == null) {
			if (other.lifeProposal != null)
				return false;
		} else if (!lifeProposal.equals(other.lifeProposal))
			return false;
		if (nilExcess != other.nilExcess)
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (policyStatus != other.policyStatus)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (printCount != other.printCount)
			return false;
		if (referral == null) {
			if (other.referral != null)
				return false;
		} else if (!referral.equals(other.referral))
			return false;
		if (renewalConfirmDate == null) {
			if (other.renewalConfirmDate != null)
				return false;
		} else if (!renewalConfirmDate.equals(other.renewalConfirmDate))
			return false;
		if (saleMan == null) {
			if (other.saleMan != null)
				return false;
		} else if (!saleMan.equals(other.saleMan))
			return false;
		if (Double.doubleToLongBits(standardExcess) != Double.doubleToLongBits(other.standardExcess))
			return false;
		if (Double.doubleToLongBits(totalDiscountAmount) != Double.doubleToLongBits(other.totalDiscountAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public String getFromBank() {
		return fromBank;
	}

	public void setFromBank(String fromBank) {
		this.fromBank = fromBank;
	}

	public String getToBank() {
		return toBank;
	}

	public void setToBank(String toBank) {
		this.toBank = toBank;
	}

	public String getBpmsReceiptNo() {
		return bpmsReceiptNo;
	}

	public void setBpmsReceiptNo(String bpmsReceiptNo) {
		this.bpmsReceiptNo = bpmsReceiptNo;
	}

}
