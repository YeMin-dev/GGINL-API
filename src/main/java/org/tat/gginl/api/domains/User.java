/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.WorkFlowType;
import org.tat.gginl.api.common.emumdata.WorkflowTask;

@Entity
@Table(name = TableName.USER)
@TableGenerator(name = "USER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "USER_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u ORDER BY u.name ASC"),
		@NamedQuery(name = "User.findByUsercode", query = "SELECT u FROM User u WHERE u.usercode = :usercode"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
		@NamedQuery(name = "User.changePassword", query = "UPDATE User u SET u.password = :newPassword WHERE u.usercode = :usercode"),
		@NamedQuery(name = "User.resetPassword", query = "UPDATE User u SET u.password = :defaultPassowrd WHERE u.usercode = :usercode") })
@Access(value = AccessType.FIELD)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_PASSWORD = "password";
	@Transient
	private String id;
	@Transient
	private String prefix;
	@Convert(disableConversion = true)
	private String usercode;
	@Convert(disableConversion = true)
	private String password;
	private String name;
	private String counterId;
	@Convert(disableConversion = true)
	private boolean disabled;
	@Convert(disableConversion = true)
	private double authority;
	private boolean accessAllBranch;
	@Convert(disableConversion = true)
	private boolean superUser;
	@Convert(disableConversion = true)
	private double claimAuthority;

	@Temporal(TemporalType.TIMESTAMP)
	@Convert(disableConversion = true)
	private Date disabledDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_ROLE_LINK", joinColumns = { @JoinColumn(name = "USERID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLEID", referencedColumnName = "ID") })
	private List<Role> roleList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	private List<Authority> authorityList;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEPOINTID", referencedColumnName = "ID")
	private SalePoint salePoint;

	@Version
	private int version;

	public User() {
		// this.password = DEFAULT_PASSWORD;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCounterId() {
		return counterId;
	}

	public void setCounterId(String counterId) {
		this.counterId = counterId;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getDisabledDate() {
		return disabledDate;
	}

	public void setDisabledDate(Date disabledDate) {
		this.disabledDate = disabledDate;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public double getAuthority() {
		return authority;
	}

	public void setAuthority(double authority) {
		this.authority = authority;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	public List<WorkflowTask> getPermissions(WorkFlowType workFlowType) {
		for (Authority auth : authorityList) {
			if (auth.getWorkFlowType().equals(workFlowType)) {
				return auth.getPermissionList();
			}
		}
		return null;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public static String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}

	public boolean needChangePassword() {
		if ("admin".equals(usercode)) {
			return false;
		}
		if ("cGFzc3dvcmQ=".equals(password)) {
			return true;
		}
		return false;
	}

	public boolean isAccessAllBranch() {
		return accessAllBranch;
	}

	public void setAccessAllBranch(boolean accessAllBranch) {
		this.accessAllBranch = accessAllBranch;
	}

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}

	public SalePoint getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalePoint salePoint) {
		this.salePoint = salePoint;
	}

	public double getClaimAuthority() {
		return claimAuthority;
	}

	public void setClaimAuthority(double claimAuthority) {
		this.claimAuthority = claimAuthority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accessAllBranch ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(authority);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		temp = Double.doubleToLongBits(claimAuthority);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((counterId == null) ? 0 : counterId.hashCode());
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((disabledDate == null) ? 0 : disabledDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((salePoint == null) ? 0 : salePoint.hashCode());
		result = prime * result + (superUser ? 1231 : 1237);
		result = prime * result + ((usercode == null) ? 0 : usercode.hashCode());
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
		User other = (User) obj;
		if (accessAllBranch != other.accessAllBranch)
			return false;
		if (Double.doubleToLongBits(authority) != Double.doubleToLongBits(other.authority))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (Double.doubleToLongBits(claimAuthority) != Double.doubleToLongBits(other.claimAuthority))
			return false;
		if (counterId == null) {
			if (other.counterId != null)
				return false;
		} else if (!counterId.equals(other.counterId))
			return false;
		if (disabled != other.disabled)
			return false;
		if (disabledDate == null) {
			if (other.disabledDate != null)
				return false;
		} else if (!disabledDate.equals(other.disabledDate))
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (salePoint == null) {
			if (other.salePoint != null)
				return false;
		} else if (!salePoint.equals(other.salePoint))
			return false;
		if (superUser != other.superUser)
			return false;
		if (usercode == null) {
			if (other.usercode != null)
				return false;
		} else if (!usercode.equals(other.usercode))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
