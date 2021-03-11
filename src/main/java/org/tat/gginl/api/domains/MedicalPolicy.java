package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.tat.gginl.api.common.IInsuredItem;
import org.tat.gginl.api.common.IPolicy;
import org.tat.gginl.api.common.MedicalProposalAttachment;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.CustomerType;
import org.tat.gginl.api.common.emumdata.InsuranceType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyStatus;


@Entity
@Table(name = TableName.MEDICALPOLICY)
@TableGenerator(name = "MEDICALPOLICY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPOLICY_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class MedicalPolicy implements IPolicy, Serializable {
  private static final long serialVersionUID = 7444548034181108750L;
  @Transient
  private boolean nilExcess;
  private boolean delFlag;
  private int lastPaymentTerm;
  private int printCount;
  private boolean isSkipPaymentTLF;

  @Version
  private int version;
  private double totalDiscountAmount;
  private double standardExcess;

  private String policyNo;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPOLICY_GEN")
  private String id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date commenmanceDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "ACTIVEDPOLICYSTARTDATE")
  private Date activedPolicyStartDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "ACTIVEDPOLICYENDDATE")
  private Date activedPolicyEndDate;

  @Enumerated(EnumType.STRING)
  private PolicyStatus policyStatus;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
  private Customer customer;

  @Enumerated(EnumType.STRING)
  private CustomerType customerType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REFERRALID", referencedColumnName = "ID")
  private Customer referral;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
  private Organization organization;

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

  @OneToOne
  @JoinColumn(name = "PROPOSALID")
  private MedicalProposal medicalProposal;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICALPOLICYID", referencedColumnName = "ID")
  private List<MedicalPolicyInsuredPerson> policyInsuredPersonList;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
  private List<MedicalPolicyAttachment> attachmentList;

  private String referencePolicyNo;

  @OneToOne
  @JoinColumn(name = "SALEPOINTID")
  private SalePoint salePoint;


  @Transient
  private PaymentChannel paymentChannel;

  @Transient
  private String fromBank;

  @Transient
  private String toBank;



  @Transient
  private String chequeNo;

  @Transient
  private String bpmsInsuredPersonId;

  @Transient
  private String bpmsReceiptNo;

  @Embedded
  private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

  public MedicalPolicy() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isNilExcess() {
    return nilExcess;
  }

  public void setNilExcess(boolean nilExcess) {
    this.nilExcess = nilExcess;
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

  public int getPrintCount() {
    return printCount;
  }

  public void setPrintCount(int printCount) {
    this.printCount = printCount;
  }

  public double getTotalDiscountAmount() {
    return totalDiscountAmount;
  }

  public void setTotalDiscountAmount(double totalDiscountAmount) {
    this.totalDiscountAmount = totalDiscountAmount;
  }

  public double getStandardExcess() {
    return standardExcess;
  }

  public void setStandardExcess(double standardExcess) {
    this.standardExcess = standardExcess;
  }

  public String getPolicyNo() {
    return policyNo;
  }

  public void setPolicyNo(String policyNo) {
    this.policyNo = policyNo;
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

  public PolicyStatus getPolicyStatus() {
    return policyStatus;
  }

  public void setPolicyStatus(PolicyStatus policyStatus) {
    this.policyStatus = policyStatus;
  }

  public Customer getCustomer() {
    return customer;
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

  public Customer getReferral() {
    return referral;
  }

  public void setReferral(Customer referral) {
    this.referral = referral;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
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

  public Agent getAgent() {
    return agent;
  }

  public void setAgent(Agent agent) {
    this.agent = agent;
  }

  public SaleMan getSaleMan() {
    return saleMan;
  }

  public void setSaleMan(SaleMan saleMan) {
    this.saleMan = saleMan;
  }

  public MedicalProposal getMedicalProposal() {
    return medicalProposal;
  }

  public void setMedicalProposal(MedicalProposal medicalProposal) {
    this.medicalProposal = medicalProposal;
  }

  public List<MedicalPolicyAttachment> getAttachmentList() {
    if (attachmentList == null) {
      attachmentList = new ArrayList<MedicalPolicyAttachment>();
    }
    return attachmentList;
  }

  public void setAttachmentList(List<MedicalPolicyAttachment> attachmentList) {
    this.attachmentList = attachmentList;
  }

  public List<MedicalPolicyInsuredPerson> getPolicyInsuredPersonList() {
    if (policyInsuredPersonList == null) {
      policyInsuredPersonList = new ArrayList<MedicalPolicyInsuredPerson>();
    }
    return policyInsuredPersonList;
  }

  public void setPolicyInsuredPersonList(List<MedicalPolicyInsuredPerson> policyInsuredPersonList) {
    this.policyInsuredPersonList = policyInsuredPersonList;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public boolean isSkipPaymentTLF() {
    return isSkipPaymentTLF;
  }

  public void setSkipPaymentTLF(boolean isSkipPaymentTLF) {
    this.isSkipPaymentTLF = isSkipPaymentTLF;
  }

  public MedicalPolicy(MedicalProposal medicalProposal) {
    this.customer = medicalProposal.getCustomer();
    this.organization = medicalProposal.getOrganization();
    this.referral = medicalProposal.getReferral();
    this.saleMan = medicalProposal.getSaleMan();
    this.branch = medicalProposal.getBranch();
    this.paymentType = medicalProposal.getPaymentType();
    this.agent = medicalProposal.getAgent();
    this.medicalProposal = medicalProposal;
    this.activedPolicyStartDate =
        medicalProposal.getMedicalProposalInsuredPersonList().get(0).getStartDate();
    this.activedPolicyEndDate =
        medicalProposal.getMedicalProposalInsuredPersonList().get(0).getEndDate();
    this.customerType = medicalProposal.getCustomerType();
    this.isSkipPaymentTLF = medicalProposal.isSkipPaymentTLF();
    if (null != medicalProposal.getSalePoint()) {
      this.salePoint = medicalProposal.getSalePoint();
    }

    for (MedicalProposalInsuredPerson person : medicalProposal
        .getMedicalProposalInsuredPersonList()) {
      addPolicyInsuredPerson(new MedicalPolicyInsuredPerson(person));
    }
    for (MedicalProposalAttachment attach : medicalProposal.getAttachmentList()) {
      addMedicalPolicyAttachment(new MedicalPolicyAttachment(attach));
    }
  }

  public void addMedicalPolicyAttachment(MedicalPolicyAttachment attachment) {
    getAttachmentList().add(attachment);
  }

  public void addPolicyInsuredPerson(MedicalPolicyInsuredPerson policyInsuredPerson) {
    getPolicyInsuredPersonList().add(policyInsuredPerson);
  }

  public PaymentChannel getPaymentChannel() {
    return paymentChannel;
  }

  public void setPaymentChannel(PaymentChannel paymentChannel) {
    this.paymentChannel = paymentChannel;
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

  public String getBpmsInsuredPersonId() {
    return bpmsInsuredPersonId;
  }

  public void setBpmsInsuredPersonId(String bpmsInsuredPersonId) {
    this.bpmsInsuredPersonId = bpmsInsuredPersonId;
  }

  public String getBpmsReceiptNo() {
    return bpmsReceiptNo;
  }

  public void setBpmsReceiptNo(String bpmsReceiptNo) {
    this.bpmsReceiptNo = bpmsReceiptNo;
  }

  public String getCustomerName() {
    if (customer != null) {
      return customer.getFullName();
    }
    if (organization != null) {
      return organization.getOwnerName();
    }
    return null;
  }

  public String getInsuredPersonName() {
    return policyInsuredPersonList.get(0).getCustomer().getFullName();
  }

  public double getTotalUnit() {
    double unit = 0.0;
    for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
      unit += person.getUnit();
    }
    return unit;
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

  public double getTotalAddOnPremium() {
    double premium = 0.0;
    for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
      premium += person.getAddOnPremium();
    }
    return premium;
  }

  // public double getTotalBasicPremium() {
  // double premium = 0.0;
  // for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
  // premium += person.getPremium();
  // }
  // return premium;
  // }

  // public double getTotalBasicPlusPremium() {
  // double premium = 0.0;
  // for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
  // premium += person.getBasicPlusPremium();
  // }
  // return premium;
  // }

  public double getTotalPremium() {
    double premium = 0.0;
    for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
      premium += person.getPremium() + person.getAddOnPremium() + person.getBasicPlusPremium()
          - person.getTotalNcbPremium();
    }
    return premium;
  }

  public double getTotalNcbPremium() {
    double premium = 0.0;
    for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
      premium += person.getTotalNcbPremium();
    }
    return premium;
  }

  public double getAgentCommission() {
    double totalCommission = 0.0;
    if (agent != null) {
      double commissionPercent = policyInsuredPersonList.get(0).getProduct().getFirstCommission();
      if (commissionPercent > 0) {
        totalCommission = (getTotalTermPremium() * commissionPercent) / 100;
      }
    }
    return totalCommission;
  }

  public double getRenewalAgentCommission() {
    double totalCommission = 0.0;
    if (agent != null) {
      double commissionPercent = policyInsuredPersonList.get(0).getProduct().getRenewalCommission();
      if (commissionPercent > 0) {
        // double totalPremium =
        // policyInsuredPersonList.get(0).getBasicTermPremium();// +
        // policyInsuredPerson.getAddOnTermPremium();
        // double commission = (totalPremium * commissionPercent) / 100;
        // totalCommission = totalCommission + commission;
      }
    }
    return totalCommission;
  }

  public MedicalPolicyInsuredPerson getMedicalPolicyInsuredPerson(Customer customer) {
    MedicalPolicyInsuredPerson result = null;
    for (MedicalPolicyInsuredPerson person : policyInsuredPersonList) {
      if (customer != null && customer.getId().equals(person.getCustomer().getId())) {
        result = person;
        break;
      }
    }
    return result;
  }

  public String getTotalBasicTermPremiumString() {
    double termPermium = 0.0;
    for (MedicalPolicyInsuredPerson pi : policyInsuredPersonList) {
      double ipTermPremium = paymentType.getMonth() > 0
          ? (pi.getPremium() + pi.getBasicPlusPremium()) * paymentType.getMonth() / 12
          : pi.getPremium() + pi.getBasicPlusPremium();
      termPermium += ipTermPremium;
      for (MedicalPolicyInsuredPersonAddOn pia : pi.getPolicyInsuredPersonAddOnList()) {
        double addonTermPremium =
            paymentType.getMonth() > 0 ? pia.getPremium() * paymentType.getMonth() / 12
                : pia.getPremium();
        termPermium += addonTermPremium;
      }
    }
    return Utils.getCurrencyFormatString(Utils.getTwoDecimalPoint(termPermium));
  }

  public double getTotalBasicTermPremiumDouble() {
    double termPermium = 0.0;
    for (MedicalPolicyInsuredPerson pi : policyInsuredPersonList) {
      double ipTermPremium = paymentType.getMonth() > 0
          ? (pi.getPremium() + pi.getBasicPlusPremium()) * paymentType.getMonth() / 12
          : pi.getPremium() + pi.getBasicPlusPremium();
      termPermium += ipTermPremium;
      for (MedicalPolicyInsuredPersonAddOn pia : pi.getPolicyInsuredPersonAddOnList()) {
        double addonTermPremium =
            paymentType.getMonth() > 0 ? pia.getPremium() * paymentType.getMonth() / 12
                : pia.getPremium();
        termPermium += addonTermPremium;
      }
    }
    return Utils.getTwoDecimalPoint(termPermium);

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

  public String getCustomerPhoneNo() {
    if (customer != null) {
      return customer.getContentInfo().getPhone();
    }
    if (organization != null) {
      return organization.getContentInfo().getPhone();
    }
    return null;
  }

  public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
    return commonCreateAndUpateMarks;
  }

  public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
    this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
  }

  public String getReferencePolicyNo() {
    return referencePolicyNo;
  }

  public void setReferencePolicyNo(String referencePolicyNo) {
    this.referencePolicyNo = referencePolicyNo;
  }

  @Override
  public String getPrefix() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getPremium() {
    double premium = 0.0;
    for (MedicalPolicyInsuredPerson pi : policyInsuredPersonList) {
      premium += pi.getBasicTermPremium() + pi.getBasicPlusPremium();
    }
    System.out.println("===============" + premium);
    return Utils.getTwoDecimalPoint(premium);

  }

  @Override
  public double getAddOnPremium() {
    double addonPremium = 0.0;
    for (MedicalPolicyInsuredPerson pi : policyInsuredPersonList) {
      for (MedicalPolicyInsuredPersonAddOn pia : pi.getPolicyInsuredPersonAddOnList()) {
        addonPremium += pia.getPremium();
      }
    }
    return Utils.getTwoDecimalPoint(addonPremium);

  }

  public double getBasicTermPremium() {

    return Utils.getTwoDecimalPoint(getPremium());
  }

  public double getAddonTermPremium() {
    double termPermium =
        paymentType.getMonth() > 0 ? getAddOnPremium() * paymentType.getMonth() / 12
            : getAddOnPremium();
    termPermium = Utils.getTwoDecimalPoint(termPermium);
    return termPermium;
  }

  @Override
  public double getTotalTermPremium() {
    return getBasicTermPremium() + getAddonTermPremium();
  }

  @Override
  public double getTotalSumInsured() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isCoinsuranceApplied() {
    return false;
  }

  @Override
  public ProductGroup getProductGroup() {
    return policyInsuredPersonList.get(0).getProduct().getProductGroup();
  }

  @Override
  public List<IInsuredItem> getInsuredItems() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public InsuranceType getInsuranceType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getApprovedBy() {
    // TODO Auto-generated method stub
    return null;
  }

  public SalePoint getSalePoint() {
    return salePoint;
  }

  public void setSalePoint(SalePoint salePoint) {
    this.salePoint = salePoint;
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
    result =
        prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
    result =
        prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
    result = prime * result + ((agent == null) ? 0 : agent.hashCode());
    result = prime * result + ((branch == null) ? 0 : branch.hashCode());
    result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
    result = prime * result
        + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + (delFlag ? 1231 : 1237);
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + lastPaymentTerm;
    result = prime * result + ((medicalProposal == null) ? 0 : medicalProposal.hashCode());
    result = prime * result + (nilExcess ? 1231 : 1237);
    result = prime * result + ((organization == null) ? 0 : organization.hashCode());
    result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
    result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
    result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
    result = prime * result + printCount;
    result = prime * result + ((referencePolicyNo == null) ? 0 : referencePolicyNo.hashCode());
    result = prime * result + ((referral == null) ? 0 : referral.hashCode());
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
    MedicalPolicy other = (MedicalPolicy) obj;
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
    if (delFlag != other.delFlag)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastPaymentTerm != other.lastPaymentTerm)
      return false;
    if (medicalProposal == null) {
      if (other.medicalProposal != null)
        return false;
    } else if (!medicalProposal.equals(other.medicalProposal))
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
    if (printCount != other.printCount)
      return false;
    if (referencePolicyNo == null) {
      if (other.referencePolicyNo != null)
        return false;
    } else if (!referencePolicyNo.equals(other.referencePolicyNo))
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
    if (Double.doubleToLongBits(standardExcess) != Double.doubleToLongBits(other.standardExcess))
      return false;
    if (Double.doubleToLongBits(totalDiscountAmount) != Double
        .doubleToLongBits(other.totalDiscountAmount))
      return false;
    if (version != other.version)
      return false;
    return true;
  }

  public int getTotalPaymentTimes() {
    if (paymentType.getMonth() == 0) {
      return 1;
    } else {
      int totalPaymentTimes = 0;
      int paymentMonths = 0;
      for (MedicalPolicyInsuredPerson i : policyInsuredPersonList) {
        if (i.getPeriodMonth() > paymentMonths) {
          paymentMonths = i.getPeriodMonth();
        }
      }
      totalPaymentTimes = paymentMonths / paymentType.getMonth();
      return totalPaymentTimes;
    }
  }

  public String getTimeSlotList() {
    List<String> result = null;
    Calendar cal = Calendar.getInstance();
    MedicalPolicyInsuredPerson insredPerson = this.policyInsuredPersonList.get(0);
    cal.setTime(this.activedPolicyStartDate);
    int months = this.paymentType.getMonth();
    if (months > 0 && months != 12) {
      result = new ArrayList<String>();
      int a = 12 / months;
      for (int i = 1; i < a; i++) {
        cal.add(Calendar.MONTH, months);
        if (insredPerson.getEndDate().after(cal.getTime()))
          result.add(org.tat.gginl.api.common.emumdata.Utils.formattedDate(cal.getTime()));
      }
    }
    return result != null ? result.toString().substring(1, result.toString().length() - 1) : null;
  }
}
