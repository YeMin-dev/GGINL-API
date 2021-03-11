package org.tat.gginl.api.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
import org.tat.gginl.api.common.MedicalProposalAttachment;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.CustomerType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.ProposalType;

@Entity
@Table(name = org.tat.gginl.api.common.TableName.MEDICALPROPOSAL)
@TableGenerator(name = "MEDICALPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPROPOSAL_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class MedicalProposal {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPROPOSAL_GEN")
  private String id;
  private String proposalNo;
  private boolean isChannel;

  @Enumerated(EnumType.STRING)
  private ProposalType proposalType;

  @Temporal(TemporalType.TIMESTAMP)
  private Date submittedDate;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
  private Branch branch;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
  private Customer customer;

  @Enumerated(EnumType.STRING)
  private CustomerType customerType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REFERRALID", referencedColumnName = "ID")
  private Customer referral;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
  private PaymentType paymentType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AGENTID", referencedColumnName = "ID")
  private Agent agent;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SALEMANID", referencedColumnName = "ID")
  private SaleMan saleMan;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OLDMEDICALPOLICYID", referencedColumnName = "ID")
  private MedicalPolicy oldMedicalPolicy;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICALPROPOSALID", referencedColumnName = "ID")
  private List<MedicalProposalInsuredPerson> medicalProposalInsuredPersonList;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
  private List<MedicalProposalAttachment> attachmentList;

  private boolean isSkipPaymentTLF;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
  private Organization organization;

  @OneToOne
  @JoinColumn(name = "SALEPOINTID")
  private SalePoint salePoint;

  @Transient
  private PaymentChannel paymentChannel;


  private String groupMicroHealthID;

  @Embedded
  private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

  @Transient
  private String fromBank;

  @Transient
  private String toBank;

  @Transient
  private String chequeNo;


  private String bpmsProposalNo;

  @Transient
  private String bpmsReceiptNo;

  @Version
  private int version;

  public MedicalProposal() {}

  public MedicalProposal(MedicalPolicy policy) {
    this.branch = policy.getBranch();
    this.customer = policy.getCustomer();
    this.referral = policy.getReferral();
    this.paymentType = policy.getPaymentType();
    this.agent = policy.getAgent();
    this.saleMan = policy.getSaleMan();
    this.oldMedicalPolicy = policy;
    this.proposalType = policy.getMedicalProposal().getProposalType();
    this.customerType = policy.getCustomerType();
    this.organization = policy.getOrganization();
    this.salePoint = policy.getSalePoint();
    for (MedicalPolicyInsuredPerson policyInsuredPerson : policy.getPolicyInsuredPersonList()) {
      addInsuredPerson(new MedicalProposalInsuredPerson(policyInsuredPerson));
    }
    for (MedicalPolicyAttachment policyAttachment : policy.getAttachmentList()) {
      addAttachment(new MedicalProposalAttachment(policyAttachment));
    }
  }

  public void addInsuredPerson(MedicalProposalInsuredPerson proposalInsuredPerson) {
    if (!getMedicalProposalInsuredPersonList().contains(proposalInsuredPerson)) {
      getMedicalProposalInsuredPersonList().add(proposalInsuredPerson);
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProposalNo() {
    return proposalNo;
  }

  public void setProposalNo(String proposalNo) {
    this.proposalNo = proposalNo;
  }

  public ProposalType getProposalType() {
    return proposalType;
  }

  public void setProposalType(ProposalType proposalType) {
    this.proposalType = proposalType;
  }

  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Customer getReferral() {
    return referral;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public Agent getAgent() {
    return agent;
  }

  public SaleMan getSaleMan() {
    return saleMan;
  }

  public int getVersion() {
    return version;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public CustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(CustomerType customerType) {
    this.customerType = customerType;
  }

  public void setReferral(Customer referral) {
    this.referral = referral;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public void setAgent(Agent agent) {
    this.agent = agent;
  }

  public void setSaleMan(SaleMan saleMan) {
    this.saleMan = saleMan;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Date getSubmittedDate() {
    return submittedDate;
  }

  public List<MedicalProposalAttachment> getAttachmentList() {
    if (attachmentList == null) {
      attachmentList = new ArrayList<MedicalProposalAttachment>();
    }
    return attachmentList;
  }

  public void setSubmittedDate(Date submittedDate) {
    this.submittedDate = submittedDate;
  }

  public void setAttachmentList(List<MedicalProposalAttachment> attachmentList) {
    this.attachmentList = attachmentList;
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

  public String getInsuredPersonName() {
    return medicalProposalInsuredPersonList.get(0).getFullName();
  }

  public double getTotalBasicUnit() {
    double unit = 0.0;
    for (MedicalProposalInsuredPerson person : getMedicalProposalInsuredPersonList()) {
      unit += person.getUnit() + person.getBasicPlusUnit();
    }
    return unit;
  }

  public double getTotalBasicPlusUnit() {
    double unit = 0.0;
    for (MedicalProposalInsuredPerson person : getMedicalProposalInsuredPersonList()) {
      unit += person.getBasicPlusUnit();
    }
    return unit;
  }

  public double getTotalBasicAndBasicPlusUnit() {
    return getTotalBasicUnit() + getTotalBasicPlusUnit();
  }

  public double getTotalApprovedPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson mp : medicalProposalInsuredPersonList) {
      premium += mp.getApprovedPremium();
    }
    return premium;
  }

  public double getTotalBasicPlusPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson mp : medicalProposalInsuredPersonList) {
      premium = premium + mp.getBasicPlusPremium();
    }
    return premium;
  }

  public double getTotalNcbPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson mp : medicalProposalInsuredPersonList) {
      premium = premium + mp.getTotalNcbPremium();
    }
    return premium;
  }

  public double getTotalAddOnPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson mp : medicalProposalInsuredPersonList) {
      premium += mp.getTotalAddOnPremium();
    }
    return premium;
  }

  public double getTotalPremium() {
    return getTotalApprovedPremium() + getTotalAddOnPremium() + getTotalBasicPlusPremium();

  }

  public double getTermPremium() {
    double termPremium = 0.0;
    for (MedicalProposalInsuredPerson person : medicalProposalInsuredPersonList) {
      termPremium += person.getBasicTermPremium() + person.getAddOnTermPremium()
          + person.getBasicPlusTermPremium();
    }
    return termPremium;

  }

  public double getTotalBasicTermPremium() {
    double totalBasicTermPremium = 0.0;
    for (MedicalProposalInsuredPerson person : medicalProposalInsuredPersonList) {
      totalBasicTermPremium += person.getBasicTermPremium();
    }
    return totalBasicTermPremium;
  }

  public List<MedicalProposalInsuredPerson> getMedicalProposalInsuredPersonList() {
    if (this.medicalProposalInsuredPersonList == null) {
      this.medicalProposalInsuredPersonList = new ArrayList<MedicalProposalInsuredPerson>();
    }
    return medicalProposalInsuredPersonList;
  }

  public void setMedicalProposalInsuredPersonList(
      List<MedicalProposalInsuredPerson> medicalProposalInsuredPersonList) {
    this.medicalProposalInsuredPersonList = medicalProposalInsuredPersonList;
  }

  public void addAttachment(MedicalProposalAttachment attachment) {
    if (attachmentList == null) {
      attachmentList = new ArrayList<MedicalProposalAttachment>();
    }
    attachmentList.add(attachment);
  }

  public void addMedicalProposalInsuredPerson(
      MedicalProposalInsuredPerson medicalProposalInsuredPerson) {
    if (medicalProposalInsuredPersonList == null) {
      medicalProposalInsuredPersonList = new ArrayList<MedicalProposalInsuredPerson>();
    }
    medicalProposalInsuredPersonList.add(medicalProposalInsuredPerson);
  }

  public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
    return commonCreateAndUpateMarks;
  }

  public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
    this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
  }

  public double getAddOnPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson pi : medicalProposalInsuredPersonList) {
      premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
    }
    return premium;
  }

  public MedicalPolicy getOldMedicalPolicy() {
    return oldMedicalPolicy;
  }

  public void setOldMedicalPolicy(MedicalPolicy oldMedicalPolicy) {
    this.oldMedicalPolicy = oldMedicalPolicy;
  }

  public boolean isSkipPaymentTLF() {
    return isSkipPaymentTLF;
  }

  public boolean isChannel() {
    return isChannel;
  }

  public void setChannel(boolean isChannel) {
    this.isChannel = isChannel;
  }

  public void setSkipPaymentTLF(boolean isSkipPaymentTLF) {
    this.isSkipPaymentTLF = isSkipPaymentTLF;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public SalePoint getSalePoint() {
    return salePoint;
  }

  public void setSalePoint(SalePoint salePoint) {
    this.salePoint = salePoint;
  }

  public String getGroupMicroHealthID() {
    return groupMicroHealthID;
  }

  public void setGroupMicroHealthID(String groupMicroHealthID) {
    this.groupMicroHealthID = groupMicroHealthID;
  }

  public String getCustomerName() {
    if (customer != null) {
      return customer.getFullName();
    } else if (organization != null) {
      return organization.getName();
    }
    return null;
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

  public String getChequeNo() {
    return chequeNo;
  }

  public void setChequeNo(String chequeNo) {
    this.chequeNo = chequeNo;
  }

  public String getBpmsProposalNo() {
    return bpmsProposalNo;
  }

  public void setBpmsProposalNo(String bpmsProposalNo) {
    this.bpmsProposalNo = bpmsProposalNo;
  }

  public String getBpmsReceiptNo() {
    return bpmsReceiptNo;
  }

  public void setBpmsReceiptNo(String bpmsReceiptNo) {
    this.bpmsReceiptNo = bpmsReceiptNo;
  }

  public PaymentChannel getPaymentChannel() {
    return paymentChannel;
  }

  public void setPaymentChannel(PaymentChannel paymentChannel) {
    this.paymentChannel = paymentChannel;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agent == null) ? 0 : agent.hashCode());
    result = prime * result + ((attachmentList == null) ? 0 : attachmentList.hashCode());
    result = prime * result + ((branch == null) ? 0 : branch.hashCode());
    result = prime * result
        + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
    result = prime * result + ((groupMicroHealthID == null) ? 0 : groupMicroHealthID.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + (isChannel ? 1231 : 1237);
    result = prime * result + (isSkipPaymentTLF ? 1231 : 1237);
    result = prime * result + ((medicalProposalInsuredPersonList == null) ? 0
        : medicalProposalInsuredPersonList.hashCode());
    result = prime * result + ((oldMedicalPolicy == null) ? 0 : oldMedicalPolicy.hashCode());
    result = prime * result + ((organization == null) ? 0 : organization.hashCode());
    result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
    result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
    result = prime * result + ((proposalType == null) ? 0 : proposalType.hashCode());
    result = prime * result + ((referral == null) ? 0 : referral.hashCode());
    result = prime * result + ((saleMan == null) ? 0 : saleMan.hashCode());
    result = prime * result + ((salePoint == null) ? 0 : salePoint.hashCode());
    result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
    result = prime * result + version;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MedicalProposal other = (MedicalProposal) obj;
    if (agent == null) {
      if (other.agent != null)
        return false;
    } else if (!agent.equals(other.agent))
      return false;
    if (attachmentList == null) {
      if (other.attachmentList != null)
        return false;
    } else if (!attachmentList.equals(other.attachmentList))
      return false;
    if (branch == null) {
      if (other.branch != null)
        return false;
    } else if (!branch.equals(other.branch))
      return false;
    if (commonCreateAndUpateMarks == null) {
      if (other.commonCreateAndUpateMarks != null)
        return false;
    } else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
      return false;
    if (customer == null) {
      if (other.customer != null)
        return false;
    } else if (!customer.equals(other.customer))
      return false;
    if (customerType != other.customerType)
      return false;
    if (groupMicroHealthID == null) {
      if (other.groupMicroHealthID != null)
        return false;
    } else if (!groupMicroHealthID.equals(other.groupMicroHealthID))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (isChannel != other.isChannel)
      return false;
    if (isSkipPaymentTLF != other.isSkipPaymentTLF)
      return false;
    if (medicalProposalInsuredPersonList == null) {
      if (other.medicalProposalInsuredPersonList != null)
        return false;
    } else if (!medicalProposalInsuredPersonList.equals(other.medicalProposalInsuredPersonList))
      return false;
    if (oldMedicalPolicy == null) {
      if (other.oldMedicalPolicy != null)
        return false;
    } else if (!oldMedicalPolicy.equals(other.oldMedicalPolicy))
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
    if (proposalNo == null) {
      if (other.proposalNo != null)
        return false;
    } else if (!proposalNo.equals(other.proposalNo))
      return false;
    if (proposalType != other.proposalType)
      return false;
    if (referral == null) {
      if (other.referral != null)
        return false;
    } else if (!referral.equals(other.referral))
      return false;
    if (saleMan == null) {
      if (other.saleMan != null)
        return false;
    } else if (!saleMan.equals(other.saleMan))
      return false;
    if (salePoint == null) {
      if (other.salePoint != null)
        return false;
    } else if (!salePoint.equals(other.salePoint))
      return false;
    if (submittedDate == null) {
      if (other.submittedDate != null)
        return false;
    } else if (!submittedDate.equals(other.submittedDate))
      return false;
    if (version != other.version)
      return false;
    return true;
  }

}
