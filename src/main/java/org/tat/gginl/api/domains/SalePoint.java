package org.tat.gginl.api.domains;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.IDInterceptor;
import org.tat.gginl.api.common.TableName;


@Entity
@Table(name = TableName.SALEPOINT)
@TableGenerator(name = "SALEPOINT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SALEPOINT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "SalePoint.findAll", query = "SELECT b FROM SalePoint b ORDER BY b.name ASC"),
		@NamedQuery(name = "SalePoint.findByCode", query = "SELECT b FROM SalePoint b WHERE b.salePointCode = :salePointCode"),
		@NamedQuery(name = "SalePoint.findById", query = "SELECT b FROM SalePoint b WHERE b.id = :id") })
@Access(value = AccessType.FIELD)
@EntityListeners(IDInterceptor.class)
public class SalePoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SALEPOINT_GEN")
	private String id;
	private String name;
	private String salePointCode;
	private String Address;
	private String email;
	private String phone;
	private String description;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPID", referencedColumnName = "ID")
	private Township township;
	
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="BRANCHID",referencedColumnName="ID")
//	private Branch branch;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalePointCode() {
		return salePointCode;
	}

	public void setSalePointCode(String salePointCode) {
		this.salePointCode = salePointCode;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Township getTownship() {
		return township;
	}

	public void setTownship(Township township) {
		this.township = township;
	}

//	public Branch getBranch() {
//		return branch;
//	}
//
//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Address == null) ? 0 : Address.hashCode());
//		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((salePointCode == null) ? 0 : salePointCode.hashCode());
		result = prime * result + ((township == null) ? 0 : township.hashCode());
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
		SalePoint other = (SalePoint) obj;
		if (Address == null) {
			if (other.Address != null)
				return false;
		} else if (!Address.equals(other.Address))
			return false;
//		if (branch == null) {
//			if (other.branch != null)
//				return false;
//		} else if (!branch.equals(other.branch))
//			return false;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (salePointCode == null) {
			if (other.salePointCode != null)
				return false;
		} else if (!salePointCode.equals(other.salePointCode))
			return false;
		if (township == null) {
			if (other.township != null)
				return false;
		} else if (!township.equals(other.township))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
