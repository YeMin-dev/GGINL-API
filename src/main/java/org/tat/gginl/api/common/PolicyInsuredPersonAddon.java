package org.tat.gginl.api.common;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.domains.InsuredPersonAddon;
import org.tat.gginl.api.domains.PolicyInsuredPersonAddonHistory;



@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONADDON)
@TableGenerator(name = "LPOLINSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSUREDPERSONADDON_GEN", allocationSize = 10)
@NamedQueries(value = {
		@NamedQuery(name = "PolicyInsuredPersonAddon.findAll", query = "SELECT p FROM PolicyInsuredPersonAddon p "),
		@NamedQuery(name = "PolicyInsuredPersonAddon.findById", query = "SELECT p FROM PolicyInsuredPersonAddon p WHERE p.id = :id") })
@Access(value = AccessType.FIELD)
public class PolicyInsuredPersonAddon {

	@Transient
	private String id;
	private String prefix;
	private double premium;
	private double sumInsured;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYINSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	@Version
	private int version;

	public PolicyInsuredPersonAddon() {
	}

	public PolicyInsuredPersonAddon(InsuredPersonAddon addOn) {
		this.premium = addOn.getProposedPremium();
		this.sumInsured = addOn.getProposedSumInsured();
		this.addOn = addOn.getAddOn();
	}

	public PolicyInsuredPersonAddon(PolicyInsuredPersonAddonHistory addOn) {
		this.premium = addOn.getPremium();
		this.sumInsured = addOn.getSumInsured();
		this.addOn = addOn.getAddOn();
	}

	public PolicyInsuredPersonAddon(AddOn addOn, double addOnSumInsured) {
		this.addOn = addOn;
		this.sumInsured = addOnSumInsured;
	}

	public PolicyInsuredPersonAddon(InsuredPersonAddOnDTO dto) {
		this.sumInsured = dto.getAddOnSumInsured();
		this.addOn = dto.getAddOn();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSUREDPERSONADDON_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}

	public void overwriteId(String id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public PolicyInsuredPerson getPolicyInsuredPersonInfo() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPersonInfo(
			PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sumInsured);
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
		PolicyInsuredPersonAddon other = (PolicyInsuredPersonAddon) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	
}
