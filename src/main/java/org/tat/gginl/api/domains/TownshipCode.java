package org.tat.gginl.api.domains;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.IDInterceptor;

import lombok.Data;

@Entity
@Data
@TableGenerator(name = "TOWNSHIPCODE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "TOWNSHIPCODE_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class TownshipCode implements Serializable {
	private static final long serialVersionUID = 7393371719430453210L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TOWNSHIPCODE_GEN")
	private String id;
	private String name;
	private String townshipcodeno;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATECODEID", referencedColumnName = "ID")
	private StateCode stateCode;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	public TownshipCode() {

	}

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

	public String getTownshipcodeno() {
		return townshipcodeno;
	}

	public void setTownshipcodeno(String townshipcodeno) {
		this.townshipcodeno = townshipcodeno;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public StateCode getStateCode() {
		return stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((townshipcodeno == null) ? 0 : townshipcodeno.hashCode());
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
		TownshipCode other = (TownshipCode) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (townshipcodeno == null) {
			if (other.townshipcodeno != null)
				return false;
		} else if (!townshipcodeno.equals(other.townshipcodeno))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
