package org.tat.gginl.api.domains;

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

import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.KeyFactor;
import org.tat.gginl.api.common.TableName;

@Entity
@Table(name = TableName.INSUREDPERSONKEYFACTORVALUE)
@TableGenerator(name = "INSUREDPERSONKEYFACTORVALUE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "INSUREDPERSONKEYFACTORVALUE_GEN", allocationSize = 10)
@NamedQueries(value = {
		@NamedQuery(name = "InsuredPersonKeyFactorValue.findAll", query = "SELECT v FROM InsuredPersonKeyFactorValue v "),
		@NamedQuery(name = "InsuredPersonKeyFactorValue.findById", query = "SELECT v FROM InsuredPersonKeyFactorValue v WHERE v.id = :id") })
@Access(value = AccessType.FIELD)
public class InsuredPersonKeyFactorValue {
	@Transient
	private String id;
	private String prefix;

	private String value;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KEYFACTORID", referencedColumnName = "ID")
	private KeyFactor keyFactor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPROPOSALINSUREDPERSONID", referencedColumnName = "ID")
	private ProposalInsuredPerson proposalInsuredPerson;

	@Version
	private int version;

	public InsuredPersonKeyFactorValue() {
	}

	public InsuredPersonKeyFactorValue(KeyFactor keyFactor) {
		this.keyFactor = keyFactor;
	}

	public InsuredPersonKeyFactorValue(String value, KeyFactor keyFactor) {
		this.value = value;
		this.keyFactor = keyFactor;
	}

	public InsuredPersonKeyFactorValue(
			PolicyInsuredPersonKeyFactorValue pinsuredPersonKeyFactorValue) {
		this.keyFactor = pinsuredPersonKeyFactorValue.getKeyFactor();
		this.value = pinsuredPersonKeyFactorValue.getValue();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "INSUREDPERSONKEYFACTORVALUE_GEN")
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public KeyFactor getKeyFactor() {
		return this.keyFactor;
	}

	public void setKeyFactor(KeyFactor keyFactor) {
		this.keyFactor = keyFactor;
	}

	public ProposalInsuredPerson getProposalInsuredPerson() {
		return proposalInsuredPerson;
	}

	public void setProposalInsuredPerson(
			ProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		InsuredPersonKeyFactorValue other = (InsuredPersonKeyFactorValue) obj;
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
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
