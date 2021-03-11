package org.tat.gginl.api.common;

import java.util.Date;

import org.tat.gginl.api.common.emumdata.HealthType;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Organization;


public class OrganizationDTO {

	private String id;

	private String prefix;

	private String codeNo;

	private String name;

	private String regNo;

	private String OwnerName;

	private int activePolicy;

	private Date activedDate;

	private PermanentAddress address;

	private ContentInfo contentInfo;

	private Branch branch;

	private HealthType healthType;

	public OrganizationDTO(String id, String prefix, String codeNo, String name, String regNo, String ownerName, int activePolicy, Date activedDate, PermanentAddress address,
			ContentInfo contentInfo, Branch branch, HealthType healthType) {
		super();
		this.id = id;
		this.prefix = prefix;
		this.codeNo = codeNo;
		this.name = name;
		this.regNo = regNo;
		OwnerName = ownerName;
		this.activePolicy = activePolicy;
		this.activedDate = activedDate;
		this.address = address;
		this.contentInfo = contentInfo;
		this.branch = branch;
		this.healthType = healthType;
	}

	public OrganizationDTO(Organization organization) {
		this.id = organization.getId();
		this.prefix = organization.getPrefix();
		this.codeNo = organization.getCodeNo();
		this.name = organization.getName();
		this.regNo = organization.getRegNo();
		this.activePolicy = organization.getActivePolicy();
		this.activedDate = organization.getActivedDate();
		this.address = organization.getAddress();
		this.contentInfo = organization.getContentInfo();
		this.branch = organization.getBranch();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getOwnerName() {
		return OwnerName;
	}

	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}

	public int getActivePolicy() {
		return activePolicy;
	}

	public void setActivePolicy(int activePolicy) {
		this.activePolicy = activePolicy;
	}

	public Date getActivedDate() {
		return activedDate;
	}

	public void setActivedDate(Date activedDate) {
		this.activedDate = activedDate;
	}

	public PermanentAddress getAddress() {
		return address;
	}

	public void setAddress(PermanentAddress address) {
		this.address = address;
	}

	public ContentInfo getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}

}
