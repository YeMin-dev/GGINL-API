/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.common;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.emumdata.AddOnType;


@Entity
@Table(name = TableName.ADDON)
@TableGenerator(name = "ADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "ADDON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "AddOn.findAll", query = "SELECT a FROM AddOn a ORDER BY a.name ASC"),
		@NamedQuery(name = "AddOn.findById", query = "SELECT a FROM AddOn a WHERE a.id = :id") })
@Access(value = AccessType.FIELD)
public class AddOn implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Transient
	private String id;
	@Transient
	private String prefix;
	private String name;
	private String description;

	@Enumerated(value = EnumType.STRING)
	private AddOnType addOnType;
	/* If AddOnType is BASED_ON_ADDON_SUMINSU */
	private double maxAmount;
	private double minAmount;

	private double value;

	@Version
	private int version;

	public AddOn() {
	}

	public AddOn(String id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ADDON_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddOnType getAddOnType() {
		return this.addOnType;
	}

	public void setAddOnType(AddOnType addOnType) {
		this.addOnType = addOnType;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public boolean isBasicAddOnSumInsured() {
		if (AddOnType.BASED_ON_ADDON_SUMINSU.equals(addOnType)) {
			return true;
		}
		return false;
	}

	public boolean isBasicSumInsured() {
		if (AddOnType.BASED_ON_SUMINSU.equals(addOnType)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addOnType == null) ? 0 : addOnType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maxAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AddOn other = (AddOn) obj;
		if (addOnType != other.addOnType)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(maxAmount) != Double.doubleToLongBits(other.maxAmount))
			return false;
		if (Double.doubleToLongBits(minAmount) != Double.doubleToLongBits(other.minAmount))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}