package org.tat.gginl.api.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
import org.tat.gginl.api.common.KeyFactor;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;


@Entity
@Table(name = TableName.MEDICALPROPOSALINSUREDPERSON)
@TableGenerator(name = "MEDICALPROPOSALINSUREDPERSON_GEN", table = "ID_GEN",
    pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",
    pkColumnValue = "MEDICALPROPOSALINSUREDPERSON_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class MedicalProposalInsuredPerson {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPROPOSALINSUREDPERSON_GEN")
  private String id;
  private boolean isPaidPremiumForPaidup;
  private boolean approved;
  private boolean needMedicalCheckup;
  private boolean sameCustomer;
  private int paymentTerm;
  private int age;
  private double proposedPremium;
  private double approvedPremium;
  @Column(name = "BASICTERMPREMIUM")
  private double basicTermPremium;
  private double proposedAddOnPremium;
  @Column(name = "ADDONTERMPREMIUM")
  private double addOnTermPremium;
  private double approvedUnit;
  private double totalNcbPremium;
  private String rejectReason;
  @Column(name = "PERIODOFMONTH")
  private Integer periodMonth;
  @Temporal(TemporalType.DATE)
  private Date startDate;
  @Temporal(TemporalType.DATE)
  private Date endDate;
  private int unit;
  private int basicPlusUnit;
  private double basicPlusPremium;
  private boolean finishedApproved;
  @Column(name = "INSPERSONCODENO")
  private String insPersonCodeNo;
  @Column(name = "INPERSONGROUPCODENO")
  private String inPersonGroupCodeNo;

  @Column(name = "BASICPLUSTERMPREMIUM")
  private double basicPlusTermPremium;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
  private RelationShip relationship;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
  private Product product;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
  private Customer customer;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "GUARDIANID", referencedColumnName = "ID")
  private MedicalProposalInsuredPersonGuardian guardian;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
  private List<MedicalProposalInsuredPersonAttachment> attachmentList;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "MEDIPROPOSALINSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalProposalInsuredPersonAddOn> insuredPersonAddOnList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "MEDIPROPOSALINSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalProposalInsuredPersonKeyFactorValue> keyFactorValueList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalProposalInsuredPersonBeneficiaries> insuredPersonBeneficiariesList;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
  private List<SurveyQuestionAnswer> surveyQuestionAnswerList;

  @Embedded
  private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

  @Version
  private int version;

  @Transient
  private boolean newCustomer;

  @Transient
  private String bpmsInsuredPersonId;

  public boolean isNewCustomer() {
    return newCustomer;
  }

  public void setNewCustomer(boolean newCustomer) {
    this.newCustomer = newCustomer;
  }

  public MedicalProposalInsuredPerson() {}

  public MedicalProposalInsuredPerson(String id, boolean isPaidPremiumForPaidup, String prefix,
      Integer periodMonth, Date startDate, Date endDate, Date dateOfBirth, Gender gender, int unit,
      IdType idType, ResidentAddress residentAddress, Name name, String fatherName,
      double proposedPremium, double approvedPremium, int paymentTerm, double totalNcbPremium,
      boolean approved, boolean needMedicalCheckup, String rejectReason, String initialId,
      String idNo, int age, RelationShip relationship, Product product, Occupation occupation,
      Customer customer, MedicalProposalInsuredPersonGuardian guardian,
      List<MedicalProposalInsuredPersonAttachment> attachmentList,
      List<MedicalProposalInsuredPersonAddOn> insuredPersonAddOnList,
      List<MedicalProposalInsuredPersonKeyFactorValue> keyFactorValueList,
      List<MedicalProposalInsuredPersonBeneficiaries> insuredPersonBeneficiariesList, int version,
      String tempId, double basicTermPremium, double addOnTermPremium,
      double basicPlusTermPremium) {
    this.id = id;
    this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
    this.periodMonth = periodMonth;
    this.startDate = startDate;
    this.endDate = endDate;
    this.unit = unit;
    this.proposedPremium = proposedPremium;
    this.approvedPremium = approvedPremium;
    this.totalNcbPremium = totalNcbPremium;
    this.basicTermPremium = basicTermPremium;
    this.paymentTerm = paymentTerm;
    this.approved = approved;
    this.needMedicalCheckup = needMedicalCheckup;
    this.rejectReason = rejectReason;
    this.age = age;
    this.relationship = relationship;
    this.product = product;
    this.customer = customer;
    this.guardian = guardian;
    this.attachmentList = attachmentList;
    this.addOnTermPremium = addOnTermPremium;
    this.basicPlusTermPremium = basicPlusTermPremium;
    this.version = version;
    for (MedicalProposalInsuredPersonAttachment attach : attachmentList) {
      addAttachment(attach);
    }
    for (MedicalProposalInsuredPersonKeyFactorValue kfv : keyFactorValueList) {
      addMedicalKeyFactorValue(
          new MedicalProposalInsuredPersonKeyFactorValue(kfv.getValue(), kfv.getKeyFactor()));
    }
    for (MedicalProposalInsuredPersonBeneficiaries beneficiary : insuredPersonBeneficiariesList) {
      addBeneficiaries(new MedicalProposalInsuredPersonBeneficiaries(beneficiary));
    }
    for (MedicalProposalInsuredPersonAddOn addon : insuredPersonAddOnList) {
      addInsuredPersonAddon(new MedicalProposalInsuredPersonAddOn(addon));
    }

    for (SurveyQuestionAnswer surveyQuestion : surveyQuestionAnswerList) {
      addSurveyQuestion(surveyQuestion);
    }

  }

  public MedicalProposalInsuredPerson(MedicalPolicyInsuredPerson policyInsuredPerson) {
    this.customer = policyInsuredPerson.getCustomer();
    this.periodMonth = policyInsuredPerson.getPeriodMonth();
    this.startDate = policyInsuredPerson.getStartDate();
    this.endDate = policyInsuredPerson.getEndDate();
    this.product = policyInsuredPerson.getProduct();
    this.relationship = policyInsuredPerson.getRelationship();
    this.insPersonCodeNo = policyInsuredPerson.getInsPersonCodeNo();
    this.inPersonGroupCodeNo = policyInsuredPerson.getInPersonGroupCodeNo();
    this.basicPlusUnit = policyInsuredPerson.getBasicPlusUnit();
    this.basicPlusPremium = policyInsuredPerson.getBasicPlusPremium();
    this.age = Utils.getAgeForNextYear(policyInsuredPerson.getCustomer().getDateOfBirth());
    this.unit = policyInsuredPerson.getUnit();
    this.proposedPremium = policyInsuredPerson.getPremium();
    this.basicTermPremium = policyInsuredPerson.getBasicTermPremium();
    this.addOnTermPremium = policyInsuredPerson.getAddOnTermPremium();
    this.basicPlusTermPremium = policyInsuredPerson.getBasicPlusTermPremium();

    if (policyInsuredPerson.getGuardian() != null) {
      this.guardian = new MedicalProposalInsuredPersonGuardian(policyInsuredPerson.getGuardian());
    }

    for (MedicalPolicyInsuredPersonAttachment attachment : policyInsuredPerson
        .getAttachmentList()) {
      addAttachment(new MedicalProposalInsuredPersonAttachment(attachment));
    }

    for (MedicalPolicyInsuredPersonKeyFactorValue keyFactorValue : policyInsuredPerson
        .getPolicyInsuredPersonkeyFactorValueList()) {
      addMedicalKeyFactorValue(new MedicalProposalInsuredPersonKeyFactorValue(keyFactorValue));
    }

    for (MedicalPolicyInsuredPersonBeneficiaries insuredPersonBeneficiaries : policyInsuredPerson
        .getPolicyInsuredPersonBeneficiariesList()) {
      addBeneficiaries(new MedicalProposalInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
    }

    for (MedicalPersonHistoryRecord record : policyInsuredPerson
        .getMedicalPersonHistoryRecordList()) {
      addHistoryRecord(new MedicalPersonHistoryRecord(record));
    }

    for (MedicalPolicyInsuredPersonAddOn addOn : policyInsuredPerson
        .getPolicyInsuredPersonAddOnList()) {
      addInsuredPersonAddon(new MedicalProposalInsuredPersonAddOn(addOn));
    }
  }

  public boolean isSameCustomer() {
    return sameCustomer;
  }

  public void setSameCustomer(boolean sameCustomer) {
    this.sameCustomer = sameCustomer;
  }

  public double getApprovedUnit() {
    return approvedUnit;
  }

  public void setApprovedUnit(double approvedUnit) {
    this.approvedUnit = approvedUnit;
  }

  public String getInsPersonCodeNo() {
    return insPersonCodeNo;
  }

  public void setInsPersonCodeNo(String insPersonCodeNo) {
    this.insPersonCodeNo = insPersonCodeNo;
  }

  public String getInPersonGroupCodeNo() {
    return inPersonGroupCodeNo;
  }

  public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
    this.inPersonGroupCodeNo = inPersonGroupCodeNo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean getIsPaidPremiumForPaidup() {
    return isPaidPremiumForPaidup;
  }

  public void setIsPaidPremiumForPaidup(boolean isPaidPremiumForPaidup) {
    this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
  }

  public Integer getPeriodMonth() {
    return periodMonth;
  }

  public void setPeriodMonth(Integer periodMonth) {
    this.periodMonth = periodMonth;
  }

  public double getProposedPremium() {
    return proposedPremium;
  }

  public void setProposedPremium(double proposedPremium) {
    this.proposedPremium = proposedPremium;
  }

  public double getApprovedPremium() {
    return approvedPremium;
  }

  public void setApprovedPremium(double approvedPremium) {
    this.approvedPremium = approvedPremium;
  }

  public int getPaymentTerm() {
    return paymentTerm;
  }

  public void setPaymentTerm(int paymentTerm) {
    this.paymentTerm = paymentTerm;
  }

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public boolean isNeedMedicalCheckup() {
    return needMedicalCheckup;
  }

  public void setNeedMedicalCheckup(boolean needMedicalCheckup) {
    this.needMedicalCheckup = needMedicalCheckup;
  }

  public String getRejectReason() {
    return rejectReason;
  }

  public void setRejectReason(String rejectReason) {
    this.rejectReason = rejectReason;
  }

  public double getTotalNcbPremium() {
    return totalNcbPremium;
  }

  public void setTotalNcbPremium(double totalNcbPremium) {
    this.totalNcbPremium = totalNcbPremium;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public RelationShip getRelationship() {
    return relationship;
  }

  public MedicalProposalInsuredPersonGuardian getGuardian() {
    return guardian;
  }

  public void setGuardian(MedicalProposalInsuredPersonGuardian guardian) {
    this.guardian = guardian;
  }

  public void setRelationship(RelationShip relationship) {
    this.relationship = relationship;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
    loadKeyFactor(product);
  }

  public void loadKeyFactor(Product product) {
    keyFactorValueList = new ArrayList<MedicalProposalInsuredPersonKeyFactorValue>();
    for (KeyFactor kf : product.getKeyFactorList()) {
      MedicalProposalInsuredPersonKeyFactorValue insKf =
          new MedicalProposalInsuredPersonKeyFactorValue(kf);
      insKf.setKeyFactor(kf);
      keyFactorValueList.add(insKf);
    }
  }

  public List<MedicalPersonHistoryRecord> getMedicalPersonHistoryRecordList() {
    if (medicalPersonHistoryRecordList == null) {
      medicalPersonHistoryRecordList = new ArrayList<MedicalPersonHistoryRecord>();
    }
    return medicalPersonHistoryRecordList;
  }

  public void setMedicalPersonHistoryRecordList(
      List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList) {
    this.medicalPersonHistoryRecordList = medicalPersonHistoryRecordList;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public int getUnit() {
    return unit;
  }

  public void setUnit(int unit) {
    this.unit = unit;
  }

  public List<MedicalProposalInsuredPersonAttachment> getAttachmentList() {
    if (attachmentList == null) {
      attachmentList = new ArrayList<MedicalProposalInsuredPersonAttachment>();
    }
    return attachmentList;
  }

  public void setAttachmentList(List<MedicalProposalInsuredPersonAttachment> attachmentList) {
    this.attachmentList = attachmentList;
  }

  public List<MedicalProposalInsuredPersonAddOn> getInsuredPersonAddOnList() {
    if (insuredPersonAddOnList == null) {
      insuredPersonAddOnList = new ArrayList<MedicalProposalInsuredPersonAddOn>();
    }
    return insuredPersonAddOnList;
  }

  public void setInsuredPersonAddOnList(
      List<MedicalProposalInsuredPersonAddOn> insuredPersonAddOnList) {
    this.insuredPersonAddOnList = insuredPersonAddOnList;
  }

  public List<MedicalProposalInsuredPersonKeyFactorValue> getKeyFactorValueList() {
    if (keyFactorValueList == null) {
      keyFactorValueList = new ArrayList<MedicalProposalInsuredPersonKeyFactorValue>();
    }
    return keyFactorValueList;
  }

  public List<MedicalProposalInsuredPersonKeyFactorValue> getKeyFactorValueList(
      MedicalProposalInsuredPerson proposalInsuredPerson) {
    return keyFactorValueList;
  }

  public void setKeyFactorValueList(
      List<MedicalProposalInsuredPersonKeyFactorValue> keyFactorValueList) {
    this.keyFactorValueList = keyFactorValueList;
  }

  public List<MedicalProposalInsuredPersonBeneficiaries> getInsuredPersonBeneficiariesList() {
    if (insuredPersonBeneficiariesList == null) {
      insuredPersonBeneficiariesList = new ArrayList<MedicalProposalInsuredPersonBeneficiaries>();
    }
    return insuredPersonBeneficiariesList;
  }

  public void setInsuredPersonBeneficiariesList(
      List<MedicalProposalInsuredPersonBeneficiaries> insuredPersonBeneficiariesList) {
    this.insuredPersonBeneficiariesList = insuredPersonBeneficiariesList;
  }

  public int getPeriodYears() {
    return periodMonth / 12;
  }

  public void setPaidPremiumForPaidup(boolean isPaidPremiumForPaidup) {
    this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
  }

  public double getTotalPremium() {
    double result = 0;
    result = approvedPremium + basicPlusPremium + getTotalAddOnPremium();
    return result;
  }

  public double getTermPremium() {
    return basicTermPremium + addOnTermPremium + basicPlusTermPremium;
  }

  public double getTotalAddOnPremium() {
    return proposedAddOnPremium;
  }

  public String getFullName() {
    return customer.getFullName();
  }

  public String getGuardianName() {
    if (guardian != null) {
      return guardian.getCustomer().getFullName();
    } else {
      return "";
    }
  }

  public double getTotalAddOnUnit() {
    double unit = 0.0;
    for (MedicalProposalInsuredPersonAddOn addOn : insuredPersonAddOnList) {
      unit += addOn.getUnit();
    }
    return unit;
  }

  public void addHistoryRecord(MedicalPersonHistoryRecord record) {
    getMedicalPersonHistoryRecordList().add(record);
  }

  public void addInsuredPersonAddon(MedicalProposalInsuredPersonAddOn insuredPersonAddon) {
    getInsuredPersonAddOnList().add(insuredPersonAddon);
  }

  public void addSurveyQuestion(SurveyQuestionAnswer surveyQuestion) {
    getSurveyQuestionAnswerList().add(surveyQuestion);
  }

  public void addBeneficiaries(
      MedicalProposalInsuredPersonBeneficiaries insuredPersonBeneficiaries) {
    getInsuredPersonBeneficiariesList().add(insuredPersonBeneficiaries);
  }

  public void addMedicalKeyFactorValue(
      MedicalProposalInsuredPersonKeyFactorValue insuredPersonKeyFactorValue) {
    getKeyFactorValueList().add(insuredPersonKeyFactorValue);
  }

  public void addAttachment(MedicalProposalInsuredPersonAttachment attachment) {
    getAttachmentList().add(attachment);
  }

  public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
    return commonCreateAndUpateMarks;
  }

  public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
    this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
  }

  public boolean isFinishedApproved() {
    return finishedApproved;
  }

  public void setFinishedApproved(boolean finishedApproved) {
    this.finishedApproved = finishedApproved;
  }

  public double getAddOnPremium() {
    return proposedAddOnPremium;
  }

  public double getProposedAddOnPremium() {
    return proposedAddOnPremium;
  }

  public void setProposedAddOnPremium(double proposedAddOnPremium) {
    this.proposedAddOnPremium = proposedAddOnPremium;
  }

  public int getBasicPlusUnit() {
    return basicPlusUnit;
  }

  public void setBasicPlusUnit(int basicPlusUnit) {
    this.basicPlusUnit = basicPlusUnit;
  }

  public double getBasicPlusPremium() {
    return basicPlusPremium;
  }

  public void setBasicPlusPremium(double basicPlusPremium) {
    this.basicPlusPremium = basicPlusPremium;
  }

  public List<SurveyQuestionAnswer> getSurveyQuestionAnswerList() {
    if (surveyQuestionAnswerList == null) {
      surveyQuestionAnswerList = new ArrayList<SurveyQuestionAnswer>();
    }
    return surveyQuestionAnswerList;
  }

  public void setSurveyQuestionAnswerList(List<SurveyQuestionAnswer> surveyQuestionAnswerList) {
    this.surveyQuestionAnswerList = surveyQuestionAnswerList;
  }

  public double getBasicTermPremium() {
    return basicTermPremium;
  }

  public void setBasicTermPremium(double basicTermPremium) {
    this.basicTermPremium = basicTermPremium;
  }

  public double getAddOnTermPremium() {
    return addOnTermPremium;
  }

  public void setAddOnTermPremium(double addOnTermPremium) {
    this.addOnTermPremium = addOnTermPremium;
  }

  public double getBasicPlusTermPremium() {
    return basicPlusTermPremium;
  }

  public void setBasicPlusTermPremium(double basicPlusTermPremium) {
    this.basicPlusTermPremium = basicPlusTermPremium;
  }

  public String getBpmsInsuredPersonId() {
    return bpmsInsuredPersonId;
  }

  public void setBpmsInsuredPersonId(String bpmsInsuredPersonId) {
    this.bpmsInsuredPersonId = bpmsInsuredPersonId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(addOnTermPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + age;
    result = prime * result + (approved ? 1231 : 1237);
    temp = Double.doubleToLongBits(approvedPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(approvedUnit);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(basicPlusPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(basicPlusTermPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + basicPlusUnit;
    temp = Double.doubleToLongBits(basicTermPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result
        + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + (finishedApproved ? 1231 : 1237);
    result = prime * result + ((guardian == null) ? 0 : guardian.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
    result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
    result = prime * result + (isPaidPremiumForPaidup ? 1231 : 1237);
    result = prime * result + (needMedicalCheckup ? 1231 : 1237);
    result = prime * result + paymentTerm;
    result = prime * result + ((periodMonth == null) ? 0 : periodMonth.hashCode());
    result = prime * result + ((product == null) ? 0 : product.hashCode());
    temp = Double.doubleToLongBits(proposedAddOnPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(proposedPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((rejectReason == null) ? 0 : rejectReason.hashCode());
    result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
    result = prime * result + (sameCustomer ? 1231 : 1237);
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    temp = Double.doubleToLongBits(totalNcbPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + unit;
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
    MedicalProposalInsuredPerson other = (MedicalProposalInsuredPerson) obj;
    if (Double.doubleToLongBits(addOnTermPremium) != Double
        .doubleToLongBits(other.addOnTermPremium))
      return false;
    if (age != other.age)
      return false;
    if (approved != other.approved)
      return false;
    if (Double.doubleToLongBits(approvedPremium) != Double.doubleToLongBits(other.approvedPremium))
      return false;
    if (Double.doubleToLongBits(approvedUnit) != Double.doubleToLongBits(other.approvedUnit))
      return false;
    if (Double.doubleToLongBits(basicPlusPremium) != Double
        .doubleToLongBits(other.basicPlusPremium))
      return false;
    if (Double.doubleToLongBits(basicPlusTermPremium) != Double
        .doubleToLongBits(other.basicPlusTermPremium))
      return false;
    if (basicPlusUnit != other.basicPlusUnit)
      return false;
    if (Double.doubleToLongBits(basicTermPremium) != Double
        .doubleToLongBits(other.basicTermPremium))
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
    if (endDate == null) {
      if (other.endDate != null)
        return false;
    } else if (!endDate.equals(other.endDate))
      return false;
    if (finishedApproved != other.finishedApproved)
      return false;
    if (guardian == null) {
      if (other.guardian != null)
        return false;
    } else if (!guardian.equals(other.guardian))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (inPersonGroupCodeNo == null) {
      if (other.inPersonGroupCodeNo != null)
        return false;
    } else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
      return false;
    if (insPersonCodeNo == null) {
      if (other.insPersonCodeNo != null)
        return false;
    } else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
      return false;
    if (isPaidPremiumForPaidup != other.isPaidPremiumForPaidup)
      return false;
    if (needMedicalCheckup != other.needMedicalCheckup)
      return false;
    if (paymentTerm != other.paymentTerm)
      return false;
    if (periodMonth == null) {
      if (other.periodMonth != null)
        return false;
    } else if (!periodMonth.equals(other.periodMonth))
      return false;
    if (product == null) {
      if (other.product != null)
        return false;
    } else if (!product.equals(other.product))
      return false;
    if (Double.doubleToLongBits(proposedAddOnPremium) != Double
        .doubleToLongBits(other.proposedAddOnPremium))
      return false;
    if (Double.doubleToLongBits(proposedPremium) != Double.doubleToLongBits(other.proposedPremium))
      return false;
    if (rejectReason == null) {
      if (other.rejectReason != null)
        return false;
    } else if (!rejectReason.equals(other.rejectReason))
      return false;
    if (relationship == null) {
      if (other.relationship != null)
        return false;
    } else if (!relationship.equals(other.relationship))
      return false;
    if (sameCustomer != other.sameCustomer)
      return false;
    if (startDate == null) {
      if (other.startDate != null)
        return false;
    } else if (!startDate.equals(other.startDate))
      return false;
    if (Double.doubleToLongBits(totalNcbPremium) != Double.doubleToLongBits(other.totalNcbPremium))
      return false;
    if (unit != other.unit)
      return false;
    if (version != other.version)
      return false;
    return true;
  }

}
