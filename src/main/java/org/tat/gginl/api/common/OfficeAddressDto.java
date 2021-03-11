package org.tat.gginl.api.common;


	import java.io.Serializable;


	public class OfficeAddressDto implements Serializable {
		private static final long serialVersionUID = 1L;

		private String officeAddress;

		private String townshipId;

		public OfficeAddressDto() {
		}

		public String getOfficeAddress() {
			return officeAddress;
		}

		public void setOfficeAddress(String officeAddress) {
			this.officeAddress = officeAddress;
		}

		public String getTownshipId() {
			return townshipId;
		}

		public void setTownshipId(String townshipId) {
			this.townshipId = townshipId;
		}

		public void getFullTownShip() {

		}

	
	

}
