package org.tat.gginl.api.common;

import java.util.StringTokenizer;

import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.InsuredPersonBeneficiaries;
import org.tat.gginl.api.domains.Organization;
import org.tat.gginl.api.domains.RelationShip;

public class BeneficiariesInfoDTO {
	private boolean existEntity;
	private int age;
	private float percentage;
	private String tempId;
	private String beneficiaryNo;

	private RelationShip relationship;
	private String initialId;
	private Gender gender;
	private IdType idType;
	private ResidentAddress residentAddress;
	private Name name;
	private String fullIdNo;
	private String idNo;
	private String provinceCode;
	private String townshipCode;
	private String idConditionType;
	private int version;
	
	private ContentInfo contentInfo;
	private String phoneNo;
	private Customer customer;
	private Organization organization;

	public BeneficiariesInfoDTO() {
		tempId = System.nanoTime() + "";
	}

	public BeneficiariesInfoDTO(InsuredPersonBeneficiaries beneficiary) {
		if (beneficiary.getId() == null) {
			this.tempId = System.nanoTime() + "";
		} else {
			existEntity = true;
			this.tempId = beneficiary.getId();
			this.version = beneficiary.getVersion();
		}
		this.age = beneficiary.getAge();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.initialId = beneficiary.getInitialId();
		this.fullIdNo = beneficiary.getIdNo();
		this.gender = beneficiary.getGender();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.name = beneficiary.getName();
		this.relationship = beneficiary.getRelationship();
		this.contentInfo = beneficiary.getContentInfo();
		this.customer = beneficiary.getCustomer();
		this.organization = beneficiary.getOrganization();
	}

	public BeneficiariesInfoDTO(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
		if (policyInsuredPersonBeneficiaries.getId() == null) {
			this.tempId = System.nanoTime() + "";
		} else {
			this.tempId = policyInsuredPersonBeneficiaries.getId();
			this.version = policyInsuredPersonBeneficiaries.getVersion();
		}
		this.age = policyInsuredPersonBeneficiaries.getAge();
		this.percentage = policyInsuredPersonBeneficiaries.getPercentage();
		this.beneficiaryNo = policyInsuredPersonBeneficiaries.getBeneficiaryNo();
		this.initialId = policyInsuredPersonBeneficiaries.getInitialId();
		this.fullIdNo = policyInsuredPersonBeneficiaries.getIdNo();
		this.gender = policyInsuredPersonBeneficiaries.getGender();
		this.idType = policyInsuredPersonBeneficiaries.getIdType();
		this.residentAddress = policyInsuredPersonBeneficiaries.getResidentAddress();
		this.name = policyInsuredPersonBeneficiaries.getName();
		this.relationship = policyInsuredPersonBeneficiaries.getRelationship();
		this.contentInfo = policyInsuredPersonBeneficiaries.getContentInfo();
		this.customer = policyInsuredPersonBeneficiaries.getCustomer();
		this.organization = policyInsuredPersonBeneficiaries.getOrganization();
	}

	public BeneficiariesInfoDTO(BeneficiariesInfoDTO beneficiary) {
		if (beneficiary.getTempId() != null) {
			this.tempId = beneficiary.getTempId();
		} else {
			this.tempId = System.nanoTime() + "";
		}
		existEntity = true;
		this.version = beneficiary.getVersion();
		this.age = beneficiary.getAge();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.initialId = beneficiary.getInitialId();
		this.fullIdNo = beneficiary.getFullIdNo();
		this.idNo = beneficiary.getIdNo();
		this.idConditionType = beneficiary.getIdConditionType();
		this.townshipCode = beneficiary.getTownshipCode();
		this.provinceCode = beneficiary.getProvinceCode();
		this.gender = beneficiary.getGender();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.name = beneficiary.getName();
		this.relationship = beneficiary.getRelationship();
		this.contentInfo = beneficiary.getContentInfo();
		this.phoneNo = beneficiary.getContentInfo()!= null ? beneficiary.getContentInfo().getPhoneOrMoblieNo() : null;
		this.customer = beneficiary.getCustomer();
		this.organization = beneficiary.getOrganization();
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
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

	/*
	 * public void setCustomer(Customer customer) { this.customer = customer;
	 * if(customer != null) { Date dateOfBirth = customer.getDateOfBirth();
	 * Calendar cal_1 = Calendar.getInstance(); int currentYear =
	 * cal_1.get(Calendar.YEAR); Calendar cal_2 = Calendar.getInstance();
	 * cal_2.setTime(dateOfBirth); cal_2.set(Calendar.YEAR, currentYear); if(new
	 * Date().after(cal_2.getTime())) { Calendar cal_3 = Calendar.getInstance();
	 * cal_3.setTime(dateOfBirth); int year_1 = cal_3.get(Calendar.YEAR); int
	 * year_2 = cal_1.get(Calendar.YEAR) + 1; age = year_2 - year_1; } else {
	 * Calendar cal_3 = Calendar.getInstance(); cal_3.setTime(dateOfBirth); int
	 * year_1 = cal_3.get(Calendar.YEAR); int year_2 = cal_1.get(Calendar.YEAR);
	 * age = year_2 - year_1; } } }
	 */
	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
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

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isExistEntity() {
		return existEntity;
	}

	public void setExistEntity(boolean existEntity) {
		this.existEntity = existEntity;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public String getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(String idConditionType) {
		this.idConditionType = idConditionType;
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

	public void loadFullIdNo() {
		if (idType.equals(IdType.NRCNO) && fullIdNo != null) {
			StringTokenizer token = new StringTokenizer(fullIdNo, "/()");
			provinceCode = token.nextToken();
			townshipCode = token.nextToken();
			idConditionType = token.nextToken();
			idNo = token.nextToken();
			fullIdNo = provinceCode.equals("null") ? "" : fullIdNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			idNo = fullIdNo == null ? "" : fullIdNo;
		}
	}

	public String setFullIdNo() {
		if (idType.equals(IdType.NRCNO)) {
			fullIdNo = provinceCode + "/" + townshipCode + "(" + idConditionType + ")" + idNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			fullIdNo = idNo;
		}
		return fullIdNo;
	}
	
	

	public ContentInfo getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + (existEntity ? 1231 : 1237);
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((tempId == null) ? 0 : tempId.hashCode());
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
		BeneficiariesInfoDTO other = (BeneficiariesInfoDTO) obj;
		if (age != other.age)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (existEntity != other.existEntity)
			return false;
		if (gender != other.gender)
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
		if (tempId == null) {
			if (other.tempId != null)
				return false;
		} else if (!tempId.equals(other.tempId))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
