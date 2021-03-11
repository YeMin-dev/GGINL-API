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
@Table(name = TableName.MEDICALPROPOSALINSUREDPERSONADDON)
@TableGenerator(name = "MEDINSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDINSUREDPERSONADDON_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalProposalInsuredPersonAddOn {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDINSUREDPERSONADDON_GEN")
	private String id;

	private double proposedPremium;
	private double approvedPremium;

	private int unit;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICALPRODUCTADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	public MedicalProposalInsuredPersonAddOn() {
	}

	public MedicalProposalInsuredPersonAddOn(MedicalProposalInsuredPersonAddOn addOn) {
		this.id = addOn.getId();
		this.proposedPremium = addOn.getProposedPremium();
		this.approvedPremium = addOn.getApprovedPremium();
		this.addOn = addOn.getAddOn();
		this.version = addOn.getVersion();
	}

	public MedicalProposalInsuredPersonAddOn(MedicalPolicyInsuredPersonAddOn policyAddOn) {
		this.proposedPremium = policyAddOn.getPremium();
		this.approvedPremium = policyAddOn.getPremium();
		this.addOn = policyAddOn.getAddOn();
		this.unit = policyAddOn.getUnit();
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

	public double getProposedPremium() {
		return proposedPremium;
	}

	public void setProposedPremium(double proposedPremium) {
		this.proposedPremium = proposedPremium;
	}

	public double getApprovedPremium() {
		return approvedPremium;
	}

	public void setApprovedPremium(double approvedPremium) {
		this.approvedPremium = approvedPremium;
	}

	public AddOn getAddOn() {
		if (addOn == null) {
			addOn = new AddOn();
		}
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
		result = prime * result + unit;
		result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
		long temp;
		temp = Double.doubleToLongBits(approvedPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(proposedPremium);
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
		MedicalProposalInsuredPersonAddOn other = (MedicalProposalInsuredPersonAddOn) obj;
		if (addOn == null) {
			if (other.addOn != null)
				return false;
		} else if (!addOn.equals(other.addOn))
			return false;
		if (unit != other.unit)
			return false;
		if (Double.doubleToLongBits(approvedPremium) != Double.doubleToLongBits(other.approvedPremium))
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
		if (Double.doubleToLongBits(proposedPremium) != Double.doubleToLongBits(other.proposedPremium))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
