package org.tat.gginl.api.common;

	import java.io.Serializable;
import java.util.Date;

import org.tat.gginl.api.common.emumdata.IdType;

	

	public class FamilyInfoDto implements Serializable {
		private static final long serialVersionUID = 1L;

		private String initialId;

		private String idNo;

		private Date dateOfBirth;

		private String refCustomerId;

		private NameDto name;

		private IdType idType;

		private String relationShipId;

		private String industryId;

		private String occupationId;

		public FamilyInfoDto() {
		}

		public FamilyInfoDto(String initialId, String idNo, IdType idType, Date dateOfBirth, String refCustomerId, NameDto name, String relationShipId, String industryId,
				String occupationId) {
			this.initialId = initialId;
			this.idNo = idNo;
			this.idType = idType;
			this.dateOfBirth = dateOfBirth;
			this.refCustomerId = refCustomerId;
			this.name = name;
			this.idType = idType;
			this.relationShipId = relationShipId;
			this.industryId = industryId;
			this.occupationId = occupationId;
		}

		public IdType getIdType() {
			return idType;
		}

		public void setIdType(IdType idType) {
			this.idType = idType;
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

		public Date getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getRefCustomerId() {
			return refCustomerId;
		}

		public void setRefCustomerId(String refCustomerId) {
			this.refCustomerId = refCustomerId;
		}

		public String getRelationShipId() {
			return relationShipId;
		}

		public void setRelationShipId(String relationShipId) {
			this.relationShipId = relationShipId;
		}

		public String getIndustryId() {
			return industryId;
		}

		public void setIndustryId(String industryId) {
			this.industryId = industryId;
		}

		public String getOccupationId() {
			return occupationId;
		}

		public void setOccupationId(String occupationId) {
			this.occupationId = occupationId;
		}

		public NameDto getName() {
			return name;
		}

		public void setName(NameDto name) {
			this.name = name;
		}

	

}
