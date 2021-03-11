package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.tat.gginl.api.domains.Township;

@Embeddable
public class ResidentAddress implements Serializable {
	private static final long serialVersionUID = -2074848703209463245L;
	private String residentAddress;
	
	@Access(AccessType.FIELD)
	@OneToOne
	@JoinColumn(name = "RESIDENTTOWNSHIPID", referencedColumnName = "ID")
	private Township residentTownship;

	public ResidentAddress() {
	}

	public ResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress.getResidentAddress();
		this.residentTownship = residentAddress.getResidentTownship();
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}



	public Township getResidentTownship() {
		return residentTownship;
	}

	public void setResidentTownship(Township residentTownship) {
		this.residentTownship = residentTownship;
	}

	@Transient
	public String getFullResidentAddress() {
		if (residentAddress == null || residentTownship == null) {
			return "";
		}

		return residentAddress + ", " + residentTownship.getFullTownShip();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((residentTownship == null) ? 0 : residentTownship.hashCode());
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
		ResidentAddress other = (ResidentAddress) obj;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (residentTownship == null) {
			if (other.residentTownship != null)
				return false;
		} else if (!residentTownship.equals(other.residentTownship))
			return false;
		return true;
	}

}
