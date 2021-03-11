package org.tat.gginl.api.domains;

import java.util.Date;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.IDInterceptor;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.ClaimStatus;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdConditionType;
import org.tat.gginl.api.common.emumdata.IdType;


@Entity
@Table(name = TableName.MEDICALPOLICYINSUREDPERSONBENEFICIARIES)
@TableGenerator(name = "MPOLINSUREDPERSONBENEFICIARIES_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MPOLINSUREDPERSONBENEFICIARIES_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MedicalPolicyInsuredPersonBeneficiaries.findAll", query = "SELECT p FROM MedicalPolicyInsuredPersonBeneficiaries p "),
		@NamedQuery(name = "MedicalPolicyInsuredPersonBeneficiaries.findById", query = "SELECT p FROM MedicalPolicyInsuredPersonBeneficiaries p WHERE p.id = :id"),
		@NamedQuery(name = "MedicalPolicyInsuredPersonBeneficiaries.updateClaimStatus", query = "UPDATE MedicalPolicyInsuredPersonBeneficiaries p SET p.claimStatus = :claimStatus WHERE p.beneficiaryNo = :beneficiaryNo") })
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyInsuredPersonBeneficiaries {
	@Version
	private int version;
	private int age;
	private float percentage;
	private String beneficiaryNo;
	private String initialId;
	private String fatherName;
	private String idNo;
	@Transient
	private boolean claimBeneficiary;
	@Transient
	private boolean exit;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLINSUREDPERSONBENEFICIARIES_GEN")
	private String id;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deathDate;
	private String deathReason;
	private boolean death;
	@Transient
	private String fullIdNo;

	@Enumerated(value = EnumType.STRING)
	private IdConditionType idConditionType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATECODEID", referencedColumnName = "ID")
	private StateCode stateCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPCODEID", referencedColumnName = "ID")
	private TownshipCode townshipCode;

	@Embedded
	private ContentInfo contentInfo;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	public MedicalPolicyInsuredPersonBeneficiaries() {
	}

	public MedicalPolicyInsuredPersonBeneficiaries(MedicalProposalInsuredPersonBeneficiaries insuredPersonBeneficiaries) {
		this.beneficiaryNo = insuredPersonBeneficiaries.getBeneficiaryNo();
		this.age = insuredPersonBeneficiaries.getAge();
		this.percentage = insuredPersonBeneficiaries.getPercentage();
		this.relationship = insuredPersonBeneficiaries.getRelationship();
		this.initialId = insuredPersonBeneficiaries.getInitialId();
		this.fatherName = insuredPersonBeneficiaries.getFatherName();
		this.idNo = insuredPersonBeneficiaries.getIdNo();
		this.gender = insuredPersonBeneficiaries.getGender();
		this.idType = insuredPersonBeneficiaries.getIdType();
		this.residentAddress = insuredPersonBeneficiaries.getResidentAddress();
		this.name = insuredPersonBeneficiaries.getName();
		this.relationship = insuredPersonBeneficiaries.getRelationship();
		this.contentInfo = insuredPersonBeneficiaries.getContentInfo();
		this.stateCode = insuredPersonBeneficiaries.getStateCode();
		this.townshipCode = insuredPersonBeneficiaries.getTownshipCode();
		this.idConditionType = insuredPersonBeneficiaries.getIdConditionType();
		this.fullIdNo = insuredPersonBeneficiaries.getFullIdNo();
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId + " ";
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + name.getFirstName() + " ";
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + name.getMiddleName() + " ";
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + name.getLastName();
			}
		}
		return result;
	}

	@Access(value = AccessType.PROPERTY)
	public String getFullIdNo() {
		String result = "";
		if (!IdType.STILL_APPLYING.equals(idType)) {
			if (stateCode != null) {
				result = result + stateCode.getCodeNo() + "/";
			}
			if (townshipCode != null) {
				result = result + townshipCode.getTownshipcodeno();
			}
			if (idConditionType != null) {
				result = result + "(" + idConditionType.getLabel() + ")";
			}

			return result + idNo;
		} else
			return null;
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

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getDeathReason() {
		return deathReason;
	}

	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = id;
		}
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

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
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

	public boolean isClaimBeneficiary() {
		return claimBeneficiary;
	}

	public void setClaimBeneficiary(boolean claimBeneficiary) {
		this.claimBeneficiary = claimBeneficiary;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((deathReason == null) ? 0 : deathReason.hashCode());
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
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((townshipCode == null) ? 0 : townshipCode.hashCode());
		result = prime * result + ((idConditionType == null) ? 0 : idConditionType.hashCode());
		result = prime * result + ((fullIdNo == null) ? 0 : fullIdNo.hashCode());
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
		MedicalPolicyInsuredPersonBeneficiaries other = (MedicalPolicyInsuredPersonBeneficiaries) obj;
		if (age != other.age)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (claimStatus != other.claimStatus)
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

		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;

		if (townshipCode == null) {
			if (other.townshipCode != null)
				return false;
		} else if (!townshipCode.equals(other.townshipCode))
			return false;

		if (idConditionType == null) {
			if (other.idConditionType != null)
				return false;
		} else if (!idConditionType.equals(other.idConditionType))
			return false;

		if (fullIdNo == null) {
			if (other.fullIdNo != null)
				return false;
		} else if (!fullIdNo.equals(other.fullIdNo))
			return false;
		if (death != other.death)
			return false;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (deathReason == null) {
			if (other.deathReason != null)
				return false;
		} else if (!deathReason.equals(other.deathReason))
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
