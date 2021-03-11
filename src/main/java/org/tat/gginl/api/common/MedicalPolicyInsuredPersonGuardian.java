package org.tat.gginl.api.common;

import javax.persistence.Column;
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

import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.MedicalProposalInsuredPersonGuardian;
import org.tat.gginl.api.domains.RelationShip;


@Entity
@Table(name = TableName.MPOLICYGUARDIAN)
@TableGenerator(name = "MPOLICYGUARDIAN_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MPOLICYGUARDIAN_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyInsuredPersonGuardian {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLICYGUARDIAN_GEN")
	private String id;
	private String guardianNo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;
	@Version
	private int version;
	@Column(name = "DEATH")
	private boolean death;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	public MedicalPolicyInsuredPersonGuardian() {
	}

	public MedicalPolicyInsuredPersonGuardian(MedicalProposalInsuredPersonGuardian proposalGuardian) {
		this.guardianNo = proposalGuardian.getGuardianNo();
		this.customer = proposalGuardian.getCustomer();
		this.relationship = proposalGuardian.getRelationship();
	}

	public MedicalPolicyInsuredPersonGuardian(MedicalPolicyInsuredPersonGuardian policyGuardian) {
		this.guardianNo = policyGuardian.getGuardianNo();
		this.customer = policyGuardian.getCustomer();
		this.relationship = policyGuardian.getRelationship();
	}

	public String getId() {
		return id;
	}

	public String getGuardianNo() {
		return guardianNo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public int getVersion() {
		return version;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setGuardianNo(String guardianNo) {
		this.guardianNo = guardianNo;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
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
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		result = prime * result + ((guardianNo == null) ? 0 : guardianNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
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
		MedicalPolicyInsuredPersonGuardian other = (MedicalPolicyInsuredPersonGuardian) obj;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (death != other.death)
			return false;
		if (guardianNo == null) {
			if (other.guardianNo != null)
				return false;
		} else if (!guardianNo.equals(other.guardianNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
