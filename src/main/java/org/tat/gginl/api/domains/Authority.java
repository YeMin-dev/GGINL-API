package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.WorkFlowType;
import org.tat.gginl.api.common.emumdata.WorkflowTask;

@Entity
@Table(name = TableName.USER_AUTHORITY)
@TableGenerator(name = "AUTHORITY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "AUTHORITY_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class Authority implements Serializable{

	private static final long serialVersionUID = 1L;
	@Transient
	private String id;
	@Transient
	private String prefix;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private User user;
	
	@Column(name = "INSURANCETYPE")
	@Enumerated(EnumType.STRING)
	private WorkFlowType workFlowType;
	
	@ElementCollection(targetClass = WorkflowTask.class,fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CascadeOnDelete
	@Column(name = "PERMISSION")
	@CollectionTable(name = "USER_PERMISSION", joinColumns = @JoinColumn(name = "AUTHORITYID", referencedColumnName = "ID" ))	
	private List<WorkflowTask> permissionList;
	
	@Version
	private int version;
	public Authority(){}
	
	
	public Authority(User user, WorkFlowType workFlowType, List<WorkflowTask> permissionList) {
		this.user = user;
		this.workFlowType = workFlowType;
		this.permissionList = permissionList;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AUTHORITY_GEN")
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
	
	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(WorkFlowType workFlowType) {
		this.workFlowType = workFlowType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<WorkflowTask> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<WorkflowTask> permissionList) {
		this.permissionList = permissionList;
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
		result = prime * result + version;
		result = prime * result + ((workFlowType == null) ? 0 : workFlowType.hashCode());
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
		Authority other = (Authority) obj;
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
		if (version != other.version)
			return false;
		if (workFlowType != other.workFlowType)
			return false;
		return true;
	}

}
