package org.tat.gginl.api.domains;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.FormatID;

@Entity
@TableGenerator(name = "SHORTENDOWMENTEXTRAVALUE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SHORTENDOWMENTEXTRAVALUE_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class ShortEndowmentExtraValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private String id;

	@Transient
	private String prefix;

	private String referenceNo;

	private String endowmentPolicyNo;

	private String shortTermPolicyNo;

	private double extraAmount;

	private boolean isPaid;

	@Version
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SHORTENDOWMENTEXTRAVALUE_GEN")
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getEndowmentPolicyNo() {
		return endowmentPolicyNo;
	}

	public void setEndowmentPolicyNo(String endowmentPolicyNo) {
		this.endowmentPolicyNo = endowmentPolicyNo;
	}

	public String getShortTermPolicyNo() {
		return shortTermPolicyNo;
	}

	public void setShortTermPolicyNo(String shortTermPolicyNo) {
		this.shortTermPolicyNo = shortTermPolicyNo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getExtraAmount() {
		return extraAmount;
	}

	public void setExtraAmount(double extraAmount) {
		this.extraAmount = extraAmount;
	}

	/**
	 * @return the isPaid
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * @param isPaid the isPaid to set
	 */
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endowmentPolicyNo == null) ? 0 : endowmentPolicyNo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(extraAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isPaid ? 1231 : 1237);
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((shortTermPolicyNo == null) ? 0 : shortTermPolicyNo.hashCode());
		result = prime * result + version;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShortEndowmentExtraValue other = (ShortEndowmentExtraValue) obj;
		if (endowmentPolicyNo == null) {
			if (other.endowmentPolicyNo != null)
				return false;
		}
		else if (!endowmentPolicyNo.equals(other.endowmentPolicyNo))
			return false;
		if (Double.doubleToLongBits(extraAmount) != Double.doubleToLongBits(other.extraAmount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (isPaid != other.isPaid)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		}
		else if (!prefix.equals(other.prefix))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		}
		else if (!referenceNo.equals(other.referenceNo))
			return false;
		if (shortTermPolicyNo == null) {
			if (other.shortTermPolicyNo != null)
				return false;
		}
		else if (!shortTermPolicyNo.equals(other.shortTermPolicyNo))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
