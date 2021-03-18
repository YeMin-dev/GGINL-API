package org.tat.gginl.api.common;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.emumdata.ClaimStatus;
import org.tat.gginl.api.common.emumdata.ClassificationOfHealth;
import org.tat.gginl.api.common.emumdata.EndorsementStatus;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.PeriodType;
import org.tat.gginl.api.common.emumdata.SumInsuredType;
import org.tat.gginl.api.common.emumdata.SurveyAnswerOne;
import org.tat.gginl.api.common.emumdata.SurveyAnswerTwo;
import org.tat.gginl.api.domains.Attachment;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.GradeInfo;
import org.tat.gginl.api.domains.InsuredPersonAddon;
import org.tat.gginl.api.domains.InsuredPersonAttachment;
import org.tat.gginl.api.domains.InsuredPersonBeneficiaries;
import org.tat.gginl.api.domains.InsuredPersonKeyFactorValue;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.PolicyInsuredPersonAddonHistory;
import org.tat.gginl.api.domains.PolicyInsuredPersonAttachment;
import org.tat.gginl.api.domains.PolicyInsuredPersonAttachmentHistory;
import org.tat.gginl.api.domains.PolicyInsuredPersonBeneficiariesHistory;
import org.tat.gginl.api.domains.PolicyInsuredPersonHistory;
import org.tat.gginl.api.domains.PolicyInsuredPersonKeyFactorValue;
import org.tat.gginl.api.domains.PolicyInsuredPersonKeyFactorValueHistory;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.ProposalInsuredPerson;
import org.tat.gginl.api.domains.RelationShip;
import org.tat.gginl.api.domains.School;
import org.tat.gginl.api.domains.TypesOfSport;

@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSON)
@TableGenerator(name = "LPOLINSURPERSON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSURPERSON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPerson.findAll", query = "SELECT s FROM PolicyInsuredPerson s "),
		@NamedQuery(name = "PolicyInsuredPerson.findById", query = "SELECT s FROM PolicyInsuredPerson s WHERE s.id = :id"),
		@NamedQuery(name = "PolicyInsuredPerson.updateClaimStatus", query = "UPDATE PolicyInsuredPerson p SET p.claimStatus = :claimStatus WHERE p.id = :id") })
@Access(value = AccessType.FIELD)
public class PolicyInsuredPerson implements IInsuredItem, Serializable {
	private static final long serialVersionUID = -1810680158208016018L;
	@Transient
	private String id;
	private String prefix;
	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	@Column(name = "AGE")
	private int age;
	private int paymentTerm;
	private double sumInsured;
	private double premium;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double endorsementNetBasicPremium;
	private double endorsementNetAddonPremium;
	private double interest;
	@Column(name = "INPERSONCODENO")
	private String insPersonCodeNo;
	@Column(name = "INPERSONGROUPCODENO")
	private String inPersonGroupCodeNo;
	private String initialId;
	private String idNo;
	private String fatherName;
	private String parentName;
	private String parentIdNo;
	@Enumerated(value = EnumType.STRING)
	private IdType parentIdType;
	private Date parentDOB;
	private int unit;
	private int weight;
	private int height;
	private double bmi;
	private SurveyAnswerOne surveyquestionOne;
	private SurveyAnswerTwo surveyquestionTwo;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(EnumType.STRING)
	private EndorsementStatus endorsementStatus;

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

	@Enumerated(value = EnumType.STRING)
	private ClassificationOfHealth clsOfHealth;
	
	@Enumerated(value = EnumType.STRING)
	private PeriodType periodType;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPESOFSPORTID", referencedColumnName = "ID")
	private TypesOfSport typesOfSport;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationShip;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOLID", referencedColumnName = "ID")
	private School school;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRATEINFOID", referencedColumnName = "ID")
	private GradeInfo gradeInfo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> birthCertificateAttachment;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAddon> policyInsuredPersonAddOnList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList;

	@Transient
	private String bpmsInsuredPersonId;

	@Version
	private int version;

	@Transient
	private boolean newCustomer;

	@Embedded
	private CommonCreateAndUpateMarks recorder;
	
	@Enumerated(value = EnumType.STRING)
	private SumInsuredType sumInsuredType;

	public boolean isNewCustomer() {
		return newCustomer;
	}

	public void setNewCustomer(boolean newCustomer) {
		this.newCustomer = newCustomer;
	}

	public PolicyInsuredPerson() {
	}

	public PolicyInsuredPerson(ProposalInsuredPerson insuredPerson) {

		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.recorder = insuredPerson.getRecorder();
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.periodType = insuredPerson.getPeriodType();
		this.sumInsured = insuredPerson.getProposedSumInsured();
		this.periodMonth = insuredPerson.getPeriodMonth();
		this.startDate = insuredPerson.getStartDate();
		this.endDate = insuredPerson.getEndDate();
		this.product = insuredPerson.getProduct();
		this.premium = insuredPerson.getApprovedPremium();
		this.paymentTerm = insuredPerson.getPaymentTerm();
		this.basicTermPremium = insuredPerson.getBasicTermPremium();
		this.addOnTermPremium = insuredPerson.getAddOnTermPremium();
		this.endorsementNetBasicPremium = insuredPerson.getEndorsementNetBasicPremium();
		this.endorsementNetAddonPremium = insuredPerson.getEndorsementNetAddonPremium();
		this.interest = insuredPerson.getInterest();
		this.insPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.endorsementStatus = insuredPerson.getEndorsementStatus();
		this.initialId = insuredPerson.getInitialId();
		this.idNo = insuredPerson.getIdNo();
		this.idType = insuredPerson.getIdType();
		this.name = insuredPerson.getName();
		this.gender = insuredPerson.getGender();
		this.residentAddress = insuredPerson.getResidentAddress();
		this.occupation = insuredPerson.getOccupation();
		this.fatherName = insuredPerson.getFatherName();
		this.parentName = insuredPerson.getParentName();
		this.parentDOB = insuredPerson.getParentDOB();
		this.parentIdType = insuredPerson.getParentIdType();
		this.parentIdNo = insuredPerson.getParentIdNo();
		this.customer = insuredPerson.getCustomer();
		this.age = insuredPerson.getAge();
		this.inPersonGroupCodeNo = insuredPerson.getInPersonGroupCodeNo();
		this.typesOfSport = insuredPerson.getTypesOfSport();
		this.unit = insuredPerson.getUnit();
		this.relationShip = insuredPerson.getRelationship();
		this.school = insuredPerson.getSchool();
		this.gradeInfo = insuredPerson.getGradeInfo();
		this.bpmsInsuredPersonId = insuredPerson.getBpmsInsuredPersonId();
		this.newCustomer = insuredPerson.isNewCustomer();
		this.sumInsuredType = insuredPerson.getSumInsuredType();
		this.weight = insuredPerson.getWeight();
		this.height = insuredPerson.getHeight();
		this.bmi = insuredPerson.getBmi();
		this.surveyquestionOne = insuredPerson.getSurveyquestionOne();
		this.surveyquestionTwo = insuredPerson.getSurveyquestionTwo();
		for (InsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachment(attachment));
		}
		for (Attachment attachment : insuredPerson.getBirthCertificateAttachment()) {
			addBirthCertificateAttachment(new Attachment(attachment));
		}
		for (InsuredPersonAddon addOn : insuredPerson.getInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddon(addOn));
		}
		for (InsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getKeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValue(keyFactorValue));
		}
		for (InsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
		}

	}

	public PolicyInsuredPerson(PolicyInsuredPersonHistory history) {
		this.periodMonth = history.getPeriodMonth();
		this.age = history.getAge();
		this.paymentTerm = history.getPaymentTerm();
		this.sumInsured = history.getSumInsured();
		this.premium = history.getPremium();
		this.basicTermPremium = history.getBasicTermPremium();
		this.addOnTermPremium = history.getAddOnTermPremium();
		this.endorsementNetBasicPremium = history.getEndorsementNetBasicPremium();
		this.endorsementNetAddonPremium = history.getEndorsementNetAddonPremium();
		this.interest = history.getInterest();
		this.insPersonCodeNo = history.getInPersonCodeNo();
		this.inPersonGroupCodeNo = history.getInPersonGroupCodeNo();
		this.initialId = history.getInitialId();
		this.idNo = history.getIdNo();
		this.fatherName = history.getFatherName();
		this.dateOfBirth = history.getDateOfBirth();
		this.startDate = history.getStartDate();
		this.endDate = history.getEndDate();
		this.gender = history.getGender();
		this.idType = history.getIdType();
		this.endorsementStatus = history.getEndorsementStatus();
		this.claimStatus = history.getClaimStatus();
		this.clsOfHealth = history.getClsOfHealth();
		this.residentAddress = history.getResidentAddress();
		this.name = history.getName();
		this.product = history.getProduct();
		this.occupation = history.getOccupation();
		this.customer = history.getCustomer();
		this.typesOfSport = history.getTypesOfSport();
		this.relationShip = history.getRelationShip();
		this.school = history.getSchool();
		this.gradeInfo = history.getGradeInfo();
		this.sumInsuredType =  history.getSumInsuredType();
		this.weight = history.getWeight();
		this.height = history.getHeight();
		this.bmi = history.getBmi();
		for (PolicyInsuredPersonAttachmentHistory attachment : history.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachment(attachment));
		}
		for (Attachment attachment : history.getBirthCertificateAttachment()) {
			addBirthCertificateAttachment(new Attachment(attachment));
		}
		for (PolicyInsuredPersonKeyFactorValueHistory keyFactorValue : history.getPolicyInsuredPersonkeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValue(keyFactorValue));
		}

		for (PolicyInsuredPersonBeneficiariesHistory insuredPersonBeneficiaries : history.getPolicyInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
		}

		for (PolicyInsuredPersonAddonHistory addOn : history.getPolicyInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddon(addOn));
		}

	}

	public PolicyInsuredPerson(InsuredPersonInfoDTO dto) {
		this.periodMonth = dto.getPeriodMonth();
		this.age = dto.getAge();
		this.paymentTerm = dto.getPaymentTerm();
		this.sumInsured = dto.getSumInsuredInfo();
		this.premium = dto.getPremium();
		this.basicTermPremium = dto.getBasicTermPremium();
		this.addOnTermPremium = dto.getAddOnTermPremium();
		this.endorsementNetBasicPremium = dto.getEndorsementBasicPremium();
		this.endorsementNetAddonPremium = dto.getEndorsementAddonPremium();
		this.interest = dto.getInterest();
		this.insPersonCodeNo = dto.getInsPersonCodeNo();
		this.inPersonGroupCodeNo = dto.getInPersonGroupCodeNo();
		this.initialId = dto.getInitialId();
		this.idNo = dto.getIdNo();
		this.fatherName = dto.getFatherName();
		this.dateOfBirth = dto.getDateOfBirth();
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.gender = dto.getGender();
		this.idType = dto.getIdType();
		this.endorsementStatus = dto.getEndorsementStatus();
		this.claimStatus = dto.getClaimStatus();
		this.clsOfHealth = dto.getClassificationOfHealth();
		this.residentAddress = dto.getResidentAddress();
		this.name = dto.getName();
		this.product = dto.getProduct();
		this.occupation = dto.getOccupation();
		this.customer = dto.getCustomer();
		this.typesOfSport = dto.getTypesOfSport();
		this.relationShip = dto.getRelationShip();
		this.school = dto.getSchool();
		this.gradeInfo = dto.getGradeInfo();
		this.sumInsuredType = dto.getSumInsuredType();
		this.weight = dto.getWeight();
		this.height = dto.getHeight();
		this.bmi = dto.getBmi();
		for (PolicyInsuredPersonAttachment attach : dto.getPrePolicyAttachmentList()) {
			addAttachment(attach);
		}
		for (Attachment attach : dto.getBirthCertificateAttachments()) {
			addBirthCertificateAttachment(attach);
		}
		for (PolicyInsuredPersonKeyFactorValue kfv : dto.getPolicyKeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(kfv);
		}
		for (BeneficiariesInfoDTO beneficiary : dto.getBeneficiariesInfoDTOList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiaries(beneficiary));
		}
		for (InsuredPersonAddOnDTO addOn : dto.getInsuredPersonAddOnDTOMap().values()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddon(addOn));
		}

		if (dto.isExistsEntity()) {
			this.id = dto.getTempId();
			this.version = dto.getVersion();
		}
	}

	public PolicyInsuredPerson(Date dateOfBirth, double sumInsured, Product product, LifePolicy lifePolicy, int periodMonth, Date startDate, Date endDate, double premium,
			double endorsementNetBasicPremium, double endorsementNetAddonPremium, double interest, String insPersonCodeNo, EndorsementStatus endorsementStatus,
			String inPersonGroupCodeNo) {
		this.endorsementStatus = endorsementStatus;
		this.dateOfBirth = dateOfBirth;
		this.sumInsured = sumInsured;
		this.product = product;
		this.lifePolicy = lifePolicy;
		this.periodMonth = periodMonth;
		this.startDate = startDate;
		this.endDate = endDate;
		this.premium = premium;
		this.endorsementNetBasicPremium = endorsementNetAddonPremium;
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
		this.insPersonCodeNo = insPersonCodeNo;
		this.interest = interest;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public PolicyInsuredPerson(Date dateOfBirth, double sumInsured, Product product, LifePolicy lifePolicy, int periodMonth, Date startDate, Date endDate, double premium,
			String initialId, String idNo, IdType idType, Name name, Gender gender, ResidentAddress residentAddress, Occupation occupation, String fatherName,
			double endorsementNetBasicPremium, double endorsementNetAddonPremium, double interest, EndorsementStatus status, String insPersonCodeNo, Customer customer, int age,
			String inPersonGroupCodeNo, int paymentTerm, double basicTermPremium, double addOnTermPremium, ClaimStatus claimStatus) {
		this.dateOfBirth = dateOfBirth;
		this.sumInsured = sumInsured;
		this.product = product;
		this.lifePolicy = lifePolicy;
		this.periodMonth = periodMonth;
		this.startDate = startDate;
		this.endDate = endDate;
		this.premium = premium;
		this.initialId = initialId;
		this.idNo = idNo;
		this.idType = idType;
		this.name = name;
		this.residentAddress = residentAddress;
		this.gender = gender;
		this.occupation = occupation;
		this.fatherName = fatherName;
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
		this.interest = interest;
		this.endorsementStatus = status;
		this.insPersonCodeNo = insPersonCodeNo;
		this.customer = customer;
		this.age = age;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
		this.paymentTerm = paymentTerm;
		this.basicTermPremium = basicTermPremium;
		this.addOnTermPremium = addOnTermPremium;
		this.claimStatus = claimStatus;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSURPERSON_GEN")
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public int getPeriodYears() {
		return periodMonth / 12;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getBmi() {
		return bmi;
	}

	public void setBmi(double bmi) {
		this.bmi = bmi;
	}
	
	public SurveyAnswerOne getSurveyquestionOne() {
		return surveyquestionOne;
	}

	public void setSurveyquestionOne(SurveyAnswerOne surveyquestionOne) {
		this.surveyquestionOne = surveyquestionOne;
	}

	public SurveyAnswerTwo getSurveyquestionTwo() {
		return surveyquestionTwo;
	}

	public void setSurveyquestionTwo(SurveyAnswerTwo surveyquestionTwo) {
		this.surveyquestionTwo = surveyquestionTwo;
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

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
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

	public EndorsementStatus getEndorsementStatus() {
		return endorsementStatus;
	}

	public void setEndorsementStatus(EndorsementStatus endorsementStatus) {
		this.endorsementStatus = endorsementStatus;
	}

	public double getEndorsementNetBasicPremium() {
		return endorsementNetBasicPremium;
	}

	public void setEndorsementNetBasicPremium(double endorsementNetBasicPremium) {
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
	}

	public double getEndorsementNetAddonPremium() {
		return endorsementNetAddonPremium;
	}

	public void setEndorsementNetAddonPremium(double endorsementNetAddonPremium) {
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
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

	public List<PolicyInsuredPersonAttachment> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<PolicyInsuredPersonAttachment>();
		}
		return this.attachmentList;
	}

	public void setAttachmentList(List<PolicyInsuredPersonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<Attachment> getBirthCertificateAttachment() {
		if (this.birthCertificateAttachment == null) {
			this.birthCertificateAttachment = new ArrayList<Attachment>();
		}
		return birthCertificateAttachment;
	}

	public void setBirthCertificateAttachment(List<Attachment> birthCertificateAttachment) {
		this.birthCertificateAttachment = birthCertificateAttachment;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public PeriodType getPeriodType() {
		return periodType;
	}

	public void setPeriodType(PeriodType periodType) {
		this.periodType = periodType;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public List<PolicyInsuredPersonAddon> getPolicyInsuredPersonAddOnList() {
		if (policyInsuredPersonAddOnList == null) {
			policyInsuredPersonAddOnList = new ArrayList<PolicyInsuredPersonAddon>();
		}
		return policyInsuredPersonAddOnList;
	}

	public void setPolicyInsuredPersonAddOnList(List<PolicyInsuredPersonAddon> policyInsuredPersonAddOnList) {
		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
	}

	public List<PolicyInsuredPersonKeyFactorValue> getPolicyInsuredPersonkeyFactorValueList() {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValue>();
		}
		return policyInsuredPersonkeyFactorValueList;
	}

	public void setPolicyInsuredPersonkeyFactorValueList(List<PolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList) {
		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<PolicyInsuredPersonBeneficiaries> getPolicyInsuredPersonBeneficiariesList() {
		if (this.policyInsuredPersonBeneficiariesList == null) {
			this.policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		}
		return this.policyInsuredPersonBeneficiariesList;
	}

	public void setPolicyInsuredPersonBeneficiariesList(List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList) {
		this.policyInsuredPersonBeneficiariesList = policyInsuredPersonBeneficiariesList;
	}

	public void addAttachment(PolicyInsuredPersonAttachment attachment) {
		attachment.setPolicyInsuredPerson(this);
		getAttachmentList().add(attachment);
	}

	public void addBirthCertificateAttachment(Attachment attachment) {
		getBirthCertificateAttachment().add(attachment);
	}

	public void addInsuredPersonAddOn(PolicyInsuredPersonAddon policyInsuredPersonAddOn) {
		policyInsuredPersonAddOn.setPolicyInsuredPersonInfo(this);
		getPolicyInsuredPersonAddOnList().add(policyInsuredPersonAddOn);
	}

	public void addPolicyInsuredPersonKeyFactorValue(PolicyInsuredPersonKeyFactorValue keyFactorValue) {
		keyFactorValue.setPolicyInsuredPersonInfo(this);
		getPolicyInsuredPersonkeyFactorValueList().add(keyFactorValue);
	}

	public void addInsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
		policyInsuredPersonBeneficiaries.setPolicyInsuredPerson(this);
		getPolicyInsuredPersonBeneficiariesList().add(policyInsuredPersonBeneficiaries);
	}

	public int getPremiumTerm() {
		return getPeriodYears() - 3;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public ClassificationOfHealth getClsOfHealth() {
		return clsOfHealth;
	}

	public void setClsOfHealth(ClassificationOfHealth clsOfHealth) {
		this.clsOfHealth = clsOfHealth;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public ResidentAddress getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public String getParentIdNo() {
		return parentIdNo;
	}

	public void setParentIdNo(String parentIdNo) {
		this.parentIdNo = parentIdNo;
	}

	public IdType getParentIdType() {
		return parentIdType;
	}

	public void setParentIdType(IdType parentIdType) {
		this.parentIdType = parentIdType;
	}

	public Date getParentDOB() {
		return parentDOB;
	}

	public void setParentDOB(Date parentDOB) {
		this.parentDOB = parentDOB;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TypesOfSport getTypesOfSport() {
		return typesOfSport;
	}

	public void setTypesOfSport(TypesOfSport typesOfSport) {
		this.typesOfSport = typesOfSport;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public CommonCreateAndUpateMarks getRecorder() {
		return recorder;
	}

	public void setRecorder(CommonCreateAndUpateMarks recorder) {
		this.recorder = recorder;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
			for (PolicyInsuredPersonAddon pia : policyInsuredPersonAddOnList) {
				premium = Utils.getTwoDecimalPoint(premium + pia.getPremium());
			}
		}
		return premium;
	}

	public double getTotalPermium() {
		return Utils.getTwoDecimalPoint(premium + getAddOnPremium());
	}

	public double getTotalTermPermium() {
		return Utils.getTwoDecimalPoint(getBasicTermPremium() + getAddOnTermPremium());
	}

	public double getAddOnSumInsure() {
		double premium = 0.0;
		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
			for (PolicyInsuredPersonAddon pia : policyInsuredPersonAddOnList) {
				premium = premium + pia.getSumInsured();
			}
		}
		return premium;
	}

	public String getFullAddress() {
		String result = "";
		if (residentAddress != null) {
			if (residentAddress.getResidentAddress() != null && !residentAddress.getResidentAddress().isEmpty()) {
				result = result + residentAddress.getResidentAddress();
			}
			if (residentAddress.getResidentTownship() != null && !residentAddress.getResidentTownship().getFullTownShip().isEmpty()) {
				result = result + ", " + residentAddress.getResidentTownship().getFullTownShip();
			}
		}
		return result;
	}

	public List<Date> getTimeSlotList() {
		List<Date> result = new ArrayList<Date>();
		result.add(startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int months = lifePolicy.getPaymentType().getMonth();
		if (months > 0) {
			int a = 12 / months;
			for (int i = 1; i < a; i++) {
				cal.add(Calendar.MONTH, months);
				result.add(cal.getTime());
			}
		}
		return result;
	}

	public String getTimeSlotListStr() {
		List<String> result = new ArrayList<String>();
		String resultStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int months = lifePolicy.getPaymentType().getMonth();
		if (months == 1) {
			resultStr = "Every " + DateUtils.getHtmlDayString(cal.getTime()) + " day of every month Until " + DateUtils.formatDateToString(lifePolicy.getPaymentEndDate());
			return resultStr;
		} else if (months > 0 && months != 12) {
			int a = 12 / months;
			for (int i = 1; i <= a; i++) {
				result.add(org.tat.gginl.api.common.emumdata.Utils.formattedDate(cal.getTime(), "dd-MMM"));
				cal.add(Calendar.MONTH, months);
			}
		} else if (months == 12) {
			result.add(org.tat.gginl.api.common.emumdata.Utils.formattedDate(cal.getTime(), "dd-MMM"));
		}
		return result != null ? result.toString().substring(1, result.toString().length() - 1) : null;
	}

	public Date getLastPaymentDate() {
		List<Date> dateList = getTimeSlotList();
		dateList.add(startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int months = lifePolicy.getPaymentType().getMonth();
		if (months > 0) {
			for (int i = 1; i < paymentTerm; i++) {
				cal.add(Calendar.MONTH, months);
				dateList.add(cal.getTime());
			}
		}
		if (!dateList.isEmpty()) {
			int lastIndex = dateList.size() - 1;
			return dateList.get(lastIndex);
		}
		return null;
	}

	public int getPaymentTimes() {
		int monthTimes = lifePolicy.getPaymentType().getMonth();
		if (monthTimes > 0) {
			return periodMonth / monthTimes;
		} else {
			return 1;
		}
	}

	public void setPaymentTimes(int paymentTimes) {
		// do nothing
	}

	public int getAgeForNextYear() {
		Calendar cal_1 = Calendar.getInstance();
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);

		if (new Date().after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	/**
	 * @see org.ace.insurance.common.interfaces.IPolicy#getTotalPremium()
	 */
	public double getTotalPremium() {
		// TODO Auto-generated method stub
		return Utils.getTwoDecimalPoint(premium + getAddOnPremium());
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId.trim();
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName().trim();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName().trim();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName().trim();
			}
		}
		return result;
	}

	public String getPeriod() {
		if (periodMonth / 12 < 1) {
			return periodMonth + " - Months";
		} else {
			return periodMonth / 12 + " - Year";
		}
	}

	public String getSchoolName() {
		if (school != null)
			return school.getName();
		return "-";
	}

	public String getSchoolAddress() {
		if (school != null && school.getAddress() != null)
			return school.getFullAddress();
		return "-";
	}

	public String getGradeInfoName() {
		if (gradeInfo != null)
			return gradeInfo.getName();
		return "-";
	}

	public String getBpmsInsuredPersonId() {
		return bpmsInsuredPersonId;
	}

	public void setBpmsInsuredPersonId(String bpmsInsuredPersonId) {
		this.bpmsInsuredPersonId = bpmsInsuredPersonId;
	}
	
	public SumInsuredType getSumInsuredType() {
		return sumInsuredType;
	}

	public void setSumInsuredType(SumInsuredType sumInsuredType) {
		this.sumInsuredType = sumInsuredType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(addOnTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((clsOfHealth == null) ? 0 : clsOfHealth.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		temp = Double.doubleToLongBits(endorsementNetAddonPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(endorsementNetBasicPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endorsementStatus == null) ? 0 : endorsementStatus.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + paymentTerm;
		result = prime * result + periodMonth;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + unit;
		result = prime * result + weight;
		result = prime * result + height;
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
		PolicyInsuredPerson other = (PolicyInsuredPerson) obj;
		if (Double.doubleToLongBits(addOnTermPremium) != Double.doubleToLongBits(other.addOnTermPremium))
			return false;
		if (age != other.age)
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (clsOfHealth != other.clsOfHealth)
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (Double.doubleToLongBits(endorsementNetAddonPremium) != Double.doubleToLongBits(other.endorsementNetAddonPremium))
			return false;
		if (Double.doubleToLongBits(endorsementNetBasicPremium) != Double.doubleToLongBits(other.endorsementNetBasicPremium))
			return false;
		if (endorsementStatus != other.endorsementStatus)
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (inPersonGroupCodeNo == null) {
			if (other.inPersonGroupCodeNo != null)
				return false;
		} else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (insPersonCodeNo == null) {
			if (other.insPersonCodeNo != null)
				return false;
		} else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
			return false;
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (paymentTerm != other.paymentTerm)
			return false;
		if (periodMonth != other.periodMonth)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (unit != other.unit)
			return false;
		if (weight != other.weight)
			return false;
		if (height != other.height)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
