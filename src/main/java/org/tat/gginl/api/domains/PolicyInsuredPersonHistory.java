package org.tat.gginl.api.domains;

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

import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.PolicyInsuredPerson;
import org.tat.gginl.api.common.PolicyInsuredPersonAddon;
import org.tat.gginl.api.common.PolicyInsuredPersonBeneficiaries;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.ClaimStatus;
import org.tat.gginl.api.common.emumdata.ClassificationOfHealth;
import org.tat.gginl.api.common.emumdata.EndorsementStatus;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.SumInsuredType;

@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONHISTORY)
@TableGenerator(name = "LPOLINSURPERSONHIS_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSURPERSONHIS_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPersonHistory.findAll", query = "SELECT s FROM PolicyInsuredPersonHistory s "),
		@NamedQuery(name = "PolicyInsuredPersonHistory.findById", query = "SELECT s FROM PolicyInsuredPersonHistory s WHERE s.id = :id") })
@Access(value = AccessType.FIELD)
public class PolicyInsuredPersonHistory {
	@Transient
	private String id;
	@Transient
	private String prefix;
	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	private int paymentTerm;
	@Column(name = "AGE")
	private int age;
	private double sumInsured;
	private double premium;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double endorsementNetBasicPremium;
	private double endorsementNetAddonPremium;
	private double interest;
	@Column(name = "INPERSONCODENO")
	private String inPersonCodeNo;
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

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Enumerated(EnumType.STRING)
	private EndorsementStatus endorsementStatus;

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(value = EnumType.STRING)
	private ClassificationOfHealth clsOfHealth;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;
	
	@Enumerated(value = EnumType.STRING)
	private SumInsuredType sumInsuredType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationShip;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOLID", referencedColumnName = "ID")
	private School school;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRATEINFOID", referencedColumnName = "ID")
	private GradeInfo gradeInfo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPESOFSPORTID", referencedColumnName = "ID")
	private TypesOfSport typesOfSport;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFERENCENUMBER", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicyHistory lifePolicy;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAttachmentHistory> attachmentList;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> birthCertificateAttachment;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAddonHistory> policyInsuredPersonAddOnList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonKeyFactorValueHistory> policyInsuredPersonkeyFactorValueList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonBeneficiariesHistory> policyInsuredPersonBeneficiariesList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
	private List<InsuranceHistoryRecord> insuranceHistoryRecord;
	@Version
	private int version;

	public PolicyInsuredPersonHistory() {
	}

	public PolicyInsuredPersonHistory(ProposalInsuredPerson insuredPerson) {
		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.premium = insuredPerson.getProposedPremium();
		this.sumInsured = insuredPerson.getProposedSumInsured();
		this.periodMonth = insuredPerson.getPeriodMonth();
		this.startDate = insuredPerson.getStartDate();
		this.endDate = insuredPerson.getEndDate();
		this.product = insuredPerson.getProduct();
		this.premium = insuredPerson.getProposedPremium();
		this.endorsementNetAddonPremium = insuredPerson.getEndorsementNetAddonPremium();
		this.endorsementNetBasicPremium = insuredPerson.getEndorsementNetBasicPremium();
		this.interest = insuredPerson.getInterest();
		this.endorsementStatus = insuredPerson.getEndorsementStatus();
		this.inPersonCodeNo = insuredPerson.getInsPersonCodeNo();
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
		this.age = insuredPerson.getAgeForNextYear();
		this.relationShip = insuredPerson.getRelationship();
		this.school = insuredPerson.getSchool();
		this.gradeInfo = insuredPerson.getGradeInfo();
		this.sumInsuredType = insuredPerson.getSumInsuredType();

		for (InsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachmentHistory(attachment));
		}
		for (Attachment attachment : insuredPerson.getBirthCertificateAttachment()) {
			addBirthCertificateAttachment(new Attachment(attachment));
		}
		for (InsuredPersonAddon addOn : insuredPerson.getInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddonHistory(addOn));
		}
		for (InsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getKeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValueHistory(keyFactorValue));
		}
		for (InsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiariesHistory(insuredPersonBeneficiaries));
		}
		for (InsuranceHistoryRecord record : insuredPerson.getInsuranceHistoryRecord()) {
			addInsuranceHistoryRecord(new InsuranceHistoryRecord(record));
		}
	}

	public PolicyInsuredPersonHistory(PolicyInsuredPerson insuredPerson) {
		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.premium = insuredPerson.getPremium();
		this.sumInsured = insuredPerson.getSumInsured();
		this.periodMonth = insuredPerson.getPeriodMonth();
		this.startDate = insuredPerson.getStartDate();
		this.endDate = insuredPerson.getEndDate();
		this.product = insuredPerson.getProduct();
		this.paymentTerm = insuredPerson.getPaymentTerm();
		this.basicTermPremium = insuredPerson.getBasicTermPremium();
		this.endorsementNetAddonPremium = insuredPerson.getEndorsementNetAddonPremium();
		this.endorsementNetBasicPremium = insuredPerson.getEndorsementNetBasicPremium();
		this.interest = insuredPerson.getInterest();
		this.endorsementStatus = insuredPerson.getEndorsementStatus();
		this.inPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.policyInsuredPerson = insuredPerson;
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
		this.relationShip = insuredPerson.getRelationShip();
		this.school = insuredPerson.getSchool();
		this.gradeInfo = insuredPerson.getGradeInfo();
		this.sumInsuredType = insuredPerson.getSumInsuredType();
		for (PolicyInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachmentHistory(attachment));
		}
		for (Attachment attachment : insuredPerson.getBirthCertificateAttachment()) {
			addBirthCertificateAttachment(new Attachment(attachment));
		}
		for (PolicyInsuredPersonAddon addOn : insuredPerson.getPolicyInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddonHistory(addOn));
		}
		for (PolicyInsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getPolicyInsuredPersonkeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValueHistory(keyFactorValue));
		}
		for (PolicyInsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiariesHistory(insuredPersonBeneficiaries));
		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSURPERSONHIS_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
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

	public String getInPersonCodeNo() {
		return inPersonCodeNo;
	}

	public void setInPersonCodeNo(String inPersonCodeNo) {
		this.inPersonCodeNo = inPersonCodeNo;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getEndorsementNetAddonPremium() {
		return endorsementNetAddonPremium;
	}

	public void setEndorsementNetAddonPremium(double endorsementNetAddonPremium) {
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
	}

	public double getEndorsementNetBasicPremium() {
		return endorsementNetBasicPremium;
	}

	public void setEndorsementNetBasicPremium(double endorsementNetBasicPremium) {
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
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

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId;
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName();
			}
		}
		return result;
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

	public List<PolicyInsuredPersonAttachmentHistory> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<PolicyInsuredPersonAttachmentHistory>();
		}
		return this.attachmentList;
	}

	public void setAttachmentList(List<PolicyInsuredPersonAttachmentHistory> attachmentList) {
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

	public LifePolicyHistory getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicyHistory lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public List<PolicyInsuredPersonAddonHistory> getPolicyInsuredPersonAddOnList() {
		return policyInsuredPersonAddOnList;
	}

	public void setPolicyInsuredPersonAddOnList(List<PolicyInsuredPersonAddonHistory> policyInsuredPersonAddOnList) {
		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
	}

	public List<PolicyInsuredPersonKeyFactorValueHistory> getPolicyInsuredPersonkeyFactorValueList() {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValueHistory>();
		}
		return policyInsuredPersonkeyFactorValueList;
	}

	public void setPolicyInsuredPersonkeyFactorValueList(List<PolicyInsuredPersonKeyFactorValueHistory> policyInsuredPersonkeyFactorValueList) {
		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
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

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
			for (PolicyInsuredPersonAddonHistory pia : policyInsuredPersonAddOnList) {
				premium = premium + pia.getPremium();
			}
		}
		return premium;
	}

	public double getTotalPermium() {
		return premium + getAddOnPremium();
	}

	public double getAddOnSumInsure() {
		double premium = 0.0;
		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
			for (PolicyInsuredPersonAddonHistory pia : policyInsuredPersonAddOnList) {
				premium = premium + pia.getSumInsured();
			}
		}
		return premium;
	}

	public List<PolicyInsuredPersonBeneficiariesHistory> getPolicyInsuredPersonBeneficiariesList() {
		if (this.policyInsuredPersonBeneficiariesList == null) {
			this.policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiariesHistory>();
		}
		return this.policyInsuredPersonBeneficiariesList;
	}

	public void setPolicyInsuredPersonBeneficiariesList(List<PolicyInsuredPersonBeneficiariesHistory> policyInsuredPersonBeneficiariesList) {
		this.policyInsuredPersonBeneficiariesList = policyInsuredPersonBeneficiariesList;
	}

	public List<InsuranceHistoryRecord> getInsuranceHistoryRecord() {
		return insuranceHistoryRecord;
	}

	public void setInsuranceHistoryRecord(List<InsuranceHistoryRecord> insuranceHistoryRecord) {
		this.insuranceHistoryRecord = insuranceHistoryRecord;
	}

	public void addAttachment(PolicyInsuredPersonAttachmentHistory attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<PolicyInsuredPersonAttachmentHistory>();
		}
		attachment.setPolicyInsuredPerson(this);
		attachmentList.add(attachment);
	}

	public void addBirthCertificateAttachment(Attachment attachment) {
		if (birthCertificateAttachment == null) {
			birthCertificateAttachment = new ArrayList<Attachment>();
		}
		birthCertificateAttachment.add(attachment);
	}

	public void addInsuredPersonAddOn(PolicyInsuredPersonAddonHistory policyInsuredPersonAddOn) {
		if (policyInsuredPersonAddOnList == null) {
			policyInsuredPersonAddOnList = new ArrayList<PolicyInsuredPersonAddonHistory>();
		}
		policyInsuredPersonAddOn.setPolicyInsuredPersonInfo(this);
		policyInsuredPersonAddOnList.add(policyInsuredPersonAddOn);
	}

	public void addPolicyInsuredPersonKeyFactorValue(PolicyInsuredPersonKeyFactorValueHistory keyFactorValue) {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValueHistory>();
		}
		keyFactorValue.setPolicyInsuredPersonInfo(this);
		policyInsuredPersonkeyFactorValueList.add(keyFactorValue);

	}

	public void addInsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiariesHistory policyInsuredPersonBeneficiaries) {
		if (policyInsuredPersonBeneficiariesList == null) {
			policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiariesHistory>();
		}
		policyInsuredPersonBeneficiaries.setPolicyInsuredPerson(this);
		policyInsuredPersonBeneficiariesList.add(policyInsuredPersonBeneficiaries);
	}

	public void addInsuranceHistoryRecord(InsuranceHistoryRecord record) {
		if (insuranceHistoryRecord == null) {
			insuranceHistoryRecord = new ArrayList<InsuranceHistoryRecord>();
		}
		insuranceHistoryRecord.add(record);
	}

	public ClassificationOfHealth getClsOfHealth() {
		return clsOfHealth;
	}

	public void setClsOfHealth(ClassificationOfHealth clsOfHealth) {
		this.clsOfHealth = clsOfHealth;
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

	public Date getLastPaymentDate() {
		List<Date> dateList = getTimeSlotList();
		dateList.add(startDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int months = lifePolicy.getPaymentType().getMonth();
		if (months > 0) {
			int a = getPeriodMonth() / months;
			for (int i = 1; i < a; i++) {
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
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
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
		result = prime * result + ((inPersonCodeNo == null) ? 0 : inPersonCodeNo.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((parentDOB == null) ? 0 : parentDOB.hashCode());
		result = prime * result + ((parentIdNo == null) ? 0 : parentIdNo.hashCode());
		result = prime * result + ((parentIdType == null) ? 0 : parentIdType.hashCode());
		result = prime * result + ((parentName == null) ? 0 : parentName.hashCode());
		result = prime * result + paymentTerm;
		result = prime * result + periodMonth;
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((typesOfSport == null) ? 0 : typesOfSport.hashCode());
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
		PolicyInsuredPersonHistory other = (PolicyInsuredPersonHistory) obj;
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
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
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
		if (inPersonCodeNo == null) {
			if (other.inPersonCodeNo != null)
				return false;
		} else if (!inPersonCodeNo.equals(other.inPersonCodeNo))
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
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (parentDOB == null) {
			if (other.parentDOB != null)
				return false;
		} else if (!parentDOB.equals(other.parentDOB))
			return false;
		if (parentIdNo == null) {
			if (other.parentIdNo != null)
				return false;
		} else if (!parentIdNo.equals(other.parentIdNo))
			return false;
		if (parentIdType != other.parentIdType)
			return false;
		if (parentName == null) {
			if (other.parentName != null)
				return false;
		} else if (!parentName.equals(other.parentName))
			return false;
		if (paymentTerm != other.paymentTerm)
			return false;
		if (periodMonth != other.periodMonth)
			return false;
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
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
		if (typesOfSport == null) {
			if (other.typesOfSport != null)
				return false;
		} else if (!typesOfSport.equals(other.typesOfSport))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
