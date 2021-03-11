package org.tat.gginl.api.common;


	import java.io.Serializable;

import lombok.Data;


	@Data
	public class ResidentAddressDto implements Serializable {
		private static final long serialVersionUID = -2074848703209463245L;

		private String residentAddress;

		private String townshipId;

		public ResidentAddressDto() {
		}

		public String getResidentAddress() {
			return residentAddress;
		}

		public void setResidentAddress(String residentAddress) {
			this.residentAddress = residentAddress;
		}

		public String getTownshipId() {
			return townshipId;
		}

		public void setTownshipId(String townshipId) {
			this.townshipId = townshipId;
		}

		
}
