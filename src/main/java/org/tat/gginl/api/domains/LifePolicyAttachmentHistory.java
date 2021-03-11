package org.tat.gginl.api.domains;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.TableName;

@Entity
@Table(name=TableName.LIFEPOLICYATTACHMENTHISTORY)
@TableGenerator(name = "LPOLATTHIS_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLATTHIS_GEN", allocationSize = 10)
@NamedQueries(value = {
		//@NamedQuery(name = "LifePolicyAttachmentHistory.findAll", query = "SELECT s FROM LifePolicyAttachmentHistory s "),
		@NamedQuery(name = "LifePolicyAttachmentHistory.findById", query = "SELECT s FROM LifePolicyAttachmentHistory s WHERE s.id = :id") })
@Access(value = AccessType.FIELD)
public class LifePolicyAttachmentHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private  String  id;
	@Transient
	private String prefix;
	private String name;
	private String filePath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicyHistory lifePolicy;
	
	@Version
	private int version;
	
	public LifePolicyAttachmentHistory(){
		
	}
	
	public LifePolicyAttachmentHistory(LifeProposalAttachment attachment) {
		this.name = attachment.getName();
		this.filePath = attachment.getFilePath();
	}
	
	public LifePolicyAttachmentHistory(LifePolicyAttachmentHistory attachment) {
		this.name = attachment.getName();
		this.filePath = attachment.getFilePath();
	}
	
	public LifePolicyAttachmentHistory(LifePolicyAttachment attachment) {
		this.name = attachment.getName();
		this.filePath = attachment.getFilePath();
	}
	
	public LifePolicyAttachmentHistory(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLATTHIS_GEN")
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}	

	public LifePolicyHistory getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicyHistory lifePolicy) {
		this.lifePolicy = lifePolicy;
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
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
		LifePolicyAttachmentHistory other = (LifePolicyAttachmentHistory) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
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
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
