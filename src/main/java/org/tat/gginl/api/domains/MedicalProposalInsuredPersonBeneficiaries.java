package org.tat.gginl.api.domains;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.IDInterceptor;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdConditionType;
import org.tat.gginl.api.common.emumdata.IdType;


@Entity
@Table(name = TableName.MEDICALPROPOSALINSUREDPERSONBENEFICIARIES)
@TableGenerator(name = "MPINSUREDPERSONBENEFICIARIES_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MPINSUREDPERSONBENEFICIARIES_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalProposalInsuredPersonBeneficiaries {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MPINSUREDPERSONBENEFICIARIES_GEN")
	private String id;

	private int age;
	private float percentage;
	private String beneficiaryNo;
	private String initialId;
	private String fatherName;
	private String idNo;
	@Transient
	private String fullIdNo;

	@Enumerated(value = EnumType.STRING)
	private IdConditionType idConditionType;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private ContentInfo contentInfo;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATECODEID", referencedColumnName = "ID")
	private StateCode stateCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPCODEID", referencedColumnName = "ID")
	private TownshipCode townshipCode;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	public MedicalProposalInsuredPersonBeneficiaries() {
	}

	public MedicalProposalInsuredPersonBeneficiaries(MedicalProposalInsuredPersonBeneficiaries beneficiary) {
		this.id = beneficiary.getId();
		this.age = beneficiary.getAge();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.initialId = beneficiary.getInitialId();
		this.fatherName = beneficiary.getFatherName();
		this.idNo = beneficiary.getIdNo();
		this.gender = beneficiary.getGender();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.contentInfo = beneficiary.getContentInfo();
		this.name = beneficiary.getName();
		this.relationship = beneficiary.getRelationship();
		this.version = beneficiary.getVersion();
	}

	public MedicalProposalInsuredPersonBeneficiaries(MedicalPolicyInsuredPersonBeneficiaries policyBeneficiary) {
		this.id = policyBeneficiary.getId();
		this.age = policyBeneficiary.getAge();
		this.percentage = policyBeneficiary.getPercentage();
		this.beneficiaryNo = policyBeneficiary.getBeneficiaryNo();
		this.initialId = policyBeneficiary.getInitialId();
		this.fatherName = policyBeneficiary.getFatherName();
		this.idNo = policyBeneficiary.getIdNo();
		this.gender = policyBeneficiary.getGender();
		this.idType = policyBeneficiary.getIdType();
		this.residentAddress = policyBeneficiary.getResidentAddress();
		this.contentInfo = policyBeneficiary.getContentInfo();
		this.name = policyBeneficiary.getName();
		this.relationship = policyBeneficiary.getRelationship();
		this.version = policyBeneficiary.getVersion();
	}

	@Access(value = AccessType.PROPERTY)
	public String getFullIdNo() {
		String result = "";
		if (fullIdNo == null || fullIdNo.isEmpty()) {
			if (stateCode != null) {
				result = result + stateCode.getCodeNo() + "/";
			} else if (townshipCode != null) {
				result = result + townshipCode.getTownshipcodeno();
			} else if (idConditionType != null) {
				result = result + "(" + idConditionType.getLabel() + ")";
			} else {
				result = result + idNo;
			}
		} else {
			result = result + idNo;
		}
		return result;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public IdConditionType getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(IdConditionType idConditionType) {
		this.idConditionType = idConditionType;
	}

	public StateCode getStateCode() {
		return stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	public TownshipCode getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(TownshipCode townshipCode) {
		this.townshipCode = townshipCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
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
		if (residentAddress == null) {
			residentAddress = new ResidentAddress();
		}
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public Name getName() {
		if (name == null) {
			name = new Name();
		}
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isValidBeneficiaries() {
		/*
		 * if(customer == null) { return false; }
		 */
		if (percentage <= 0) {
			return false;
		}
		if (age < 0) {
			return false;
		}
		return true;
	}

	public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
		return commonCreateAndUpateMarks;
	}

	public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
		this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public ContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new ContentInfo();
		}
		return this.contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
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
		MedicalProposalInsuredPersonBeneficiaries other = (MedicalProposalInsuredPersonBeneficiaries) obj;
		if (age != other.age)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
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
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
