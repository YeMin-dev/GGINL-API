package org.tat.gginl.api.common;


	import java.io.Serializable;

	import javax.persistence.Embeddable;
	import javax.persistence.FetchType;
	import javax.persistence.JoinColumn;
	import javax.persistence.OneToOne;

import org.tat.gginl.api.domains.Township;




	@Embeddable
	public class OfficeAddress implements Serializable {
		
		private static final long serialVersionUID = 1L;

		private String officeAddress;

		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "OFFICETOWNSHIPID", referencedColumnName = "ID")
		private Township township;

		public OfficeAddress() {
		}

		public OfficeAddress(String officeAddress, Township township) {
			this.officeAddress = officeAddress;
			this.township = township;
		}

		public String getOfficeAddress() {
			return officeAddress;
		}

		public void setOfficeAddress(String officeAddress) {
			this.officeAddress = officeAddress;
		}

		public Township getTownship() {
			return this.township;
		}

		public void setTownship(Township township) {
			this.township = township;
		}

		public void getFullTownShip() {

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((officeAddress == null) ? 0 : officeAddress.hashCode());
			result = prime * result + ((township == null) ? 0 : township.hashCode());
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
			OfficeAddress other = (OfficeAddress) obj;
			if (officeAddress == null) {
				if (other.officeAddress != null)
					return false;
			} else if (!officeAddress.equals(other.officeAddress))
				return false;
			if (township == null) {
				if (other.township != null)
					return false;
			} else if (!township.equals(other.township))
				return false;
			return true;
		}

	}
