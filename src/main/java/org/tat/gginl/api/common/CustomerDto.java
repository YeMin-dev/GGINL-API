package org.tat.gginl.api.common;
	import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.MaritalStatus;

	public class CustomerDto implements Serializable {
		private static final long serialVersionUID = 1L;

		private String id;

		private String prefix;

		private String initialId;

		private String fatherName;

		private String idNo;

		private Date dateOfBirth;

		private String labourNo;

		private String birthMark;

		private String salary;

		private int closedPolicy;

		private int activePolicy;

		private String bankAccountNo;

		private Gender gender;

		private IdType idType;

		private MaritalStatus maritalStatus;

		private OfficeAddressDto officeAddress;

		private PermanentAddressDto permanentAddress;

		private ResidentAddressDto residentAddress;

		private ContentInfoDto contentInfo;

		private NameDto name;

		private List<FamilyInfoDto> familyInfo;

		private String branchId;

		private String qualificationId;

		private String bankBranchId;

		private String religionId;

		private String occupationId;

		private String industryId;

		private String countryId;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getInitialId() {
			return initialId;
		}

		public void setInitialId(String initialId) {
			this.initialId = initialId;
		}

		public String getFatherName() {
			return fatherName;
		}

		public void setFatherName(String fatherName) {
			this.fatherName = fatherName;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}

		public Date getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getBirthMark() {
			return birthMark;
		}

		public String getSalary() {
			return salary;
		}

		public void setBirthMark(String birthMark) {
			this.birthMark = birthMark;
		}

		public void setSalary(String salary) {
			this.salary = salary;
		}

		public int getClosedPolicy() {
			return closedPolicy;
		}

		public void setClosedPolicy(int closedPolicy) {
			this.closedPolicy = closedPolicy;
		}

		public int getActivePolicy() {
			return activePolicy;
		}

		public void setActivePolicy(int activePolicy) {
			this.activePolicy = activePolicy;
		}

		public String getBankAccountNo() {
			return bankAccountNo;
		}

		public void setBankAccountNo(String bankAccountNo) {
			this.bankAccountNo = bankAccountNo;
		}

		public Gender getGender() {
			return this.gender;
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

		public MaritalStatus getMaritalStatus() {
			return maritalStatus;
		}

		public void setMaritalStatus(MaritalStatus maritalStatus) {
			this.maritalStatus = maritalStatus;
		}

		public String getLabourNo() {
			return labourNo;
		}

		public void setLabourNo(String labourNo) {
			this.labourNo = labourNo;
		}

		public OfficeAddressDto getOfficeAddress() {
			return officeAddress;
		}

		public void setOfficeAddress(OfficeAddressDto officeAddress) {
			this.officeAddress = officeAddress;
		}

		public PermanentAddressDto getPermanentAddress() {
			return permanentAddress;
		}

		public void setPermanentAddress(PermanentAddressDto permanentAddress) {
			this.permanentAddress = permanentAddress;
		}

		public ResidentAddressDto getResidentAddress() {
			return residentAddress;
		}

		public void setResidentAddress(ResidentAddressDto residentAddress) {
			this.residentAddress = residentAddress;
		}

		public ContentInfoDto getContentInfo() {
			return contentInfo;
		}

		public void setContentInfo(ContentInfoDto contentInfo) {
			this.contentInfo = contentInfo;
		}

		public NameDto getName() {
			return name;
		}

		public void setName(NameDto name) {
			this.name = name;
		}

		public List<FamilyInfoDto> getFamilyInfo() {
			if (familyInfo == null)
				familyInfo = new ArrayList<FamilyInfoDto>();
			return familyInfo;
		}

		public void setFamilyInfo(List<FamilyInfoDto> familyInfo) {
			this.familyInfo = familyInfo;
		}

		public String getBranchId() {
			return branchId;
		}

		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}

		public String getQualificationId() {
			return qualificationId;
		}

		public void setQualificationId(String qualificationId) {
			this.qualificationId = qualificationId;
		}

		public String getBankBranchId() {
			return bankBranchId;
		}

		public void setBankBranchId(String bankBranchId) {
			this.bankBranchId = bankBranchId;
		}

		public String getReligionId() {
			return religionId;
		}

		public void setReligionId(String religionId) {
			this.religionId = religionId;
		}

		public String getOccupationId() {
			return occupationId;
		}

		public void setOccupationId(String occupationId) {
			this.occupationId = occupationId;
		}

		public String getIndustryId() {
			return industryId;
		}

		public void setIndustryId(String industryId) {
			this.industryId = industryId;
		}

		public String getCountryId() {
			return countryId;
		}

		public void setCountryId(String countryId) {
			this.countryId = countryId;
		}
	

}
