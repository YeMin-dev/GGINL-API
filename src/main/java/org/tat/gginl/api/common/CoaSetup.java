package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;



@Entity
@Table(name = TableName.COASETUP)
@TableGenerator(name = "COASETUP_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "COASETUP_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class CoaSetup implements Serializable {
	private static final long serialVersionUID = 1518034010161889917L;
	@Id
	@Column(name = "ACNAME")
	private String acccountName;
	@Column(name = "ACODE")
	private String accountCode;
	@Column(name = "BRANCHCODE")
	private String branchCode;
	@Column(name = "DCODE")
	private String dCode;
	@Column(name = "CUR")
	private String currency;

	public String getAcccountName() {
		return acccountName;
	}

	public void setAcccountName(String acccountName) {
		this.acccountName = acccountName;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getdCode() {
		return dCode;
	}

	public void setdCode(String dCode) {
		this.dCode = dCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acccountName == null) ? 0 : acccountName.hashCode());
		result = prime * result + ((accountCode == null) ? 0 : accountCode.hashCode());
		result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((dCode == null) ? 0 : dCode.hashCode());
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
		CoaSetup other = (CoaSetup) obj;
		if (acccountName == null) {
			if (other.acccountName != null)
				return false;
		} else if (!acccountName.equals(other.acccountName))
			return false;
		if (accountCode == null) {
			if (other.accountCode != null)
				return false;
		} else if (!accountCode.equals(other.accountCode))
			return false;
		if (branchCode == null) {
			if (other.branchCode != null)
				return false;
		} else if (!branchCode.equals(other.branchCode))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (dCode == null) {
			if (other.dCode != null)
				return false;
		} else if (!dCode.equals(other.dCode))
			return false;
		return true;
	}

}
