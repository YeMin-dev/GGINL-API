package org.tat.gginl.api.domains;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.gginl.api.common.AddOn;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.IDInterceptor;
import org.tat.gginl.api.common.TableName;



@Entity
@Table(name = TableName.MEDICALPOLICYINSUREDPERSONADDON)
@TableGenerator(name = "MPOLINSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MPOLINSUREDPERSONADDON_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyInsuredPersonAddOn {

	@Version
	private int version;
	private double premium;
	private int unit;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLINSUREDPERSONADDON_GEN")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	public MedicalPolicyInsuredPersonAddOn() {

	}

	public MedicalPolicyInsuredPersonAddOn(MedicalProposalInsuredPersonAddOn insuredPersonAddOn) {
		this.premium = insuredPersonAddOn.getApprovedPremium();
		this.addOn = insuredPersonAddOn.getAddOn();
		this.unit = insuredPersonAddOn.getUnit();

	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
		return commonCreateAndUpateMarks;
	}

	public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
		this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
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
		MedicalPolicyInsuredPersonAddOn other = (MedicalPolicyInsuredPersonAddOn) obj;
		if (addOn == null) {
			if (other.addOn != null)
				return false;
		} else if (!addOn.equals(other.addOn))
			return false;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;

		if (version != other.version)
			return false;
		return true;
	}

}
