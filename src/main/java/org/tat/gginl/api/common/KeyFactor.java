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

import org.tat.gginl.api.common.emumdata.KeyFactorType;


@Entity
@Table(name=TableName.KEYFACTOR)
@TableGenerator(name = "KEYFACTOR_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "KEYFACTOR_GEN", allocationSize = 10)
@NamedQueries(value = {
		@NamedQuery(name = "KeyFactor.findAll", query = "SELECT k FROM KeyFactor k ORDER BY k.value ASC"),
		@NamedQuery(name = "KeyFactor.findById", query = "SELECT k FROM KeyFactor k WHERE k.id = :id") })
@Access(value = AccessType.FIELD)
public class KeyFactor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Transient
	private String id;
	@Transient
	private String prefix;
	private String value;
	@Enumerated(value = EnumType.STRING)
	private KeyFactorType keyFactorType;
	@Version
	private int version;

	public KeyFactor() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "KEYFACTOR_GEN")
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

	public KeyFactorType getKeyFactorType() {
		return this.keyFactorType;
	}

	public void setKeyFactorType(KeyFactorType keyFactorType) {
		this.keyFactorType = keyFactorType;
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
		result = prime * result + ((keyFactorType == null) ? 0 : keyFactorType.hashCode());
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
		KeyFactor other = (KeyFactor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keyFactorType != other.keyFactorType)
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