package org.tat.gginl.api.domains;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.IDInterceptor;
import org.tat.gginl.api.common.TableName;

@Entity
@Table(name = TableName.GROUPFARMERPROPOSAL_ATTACH_LINK)
@TableGenerator(name = "GROUPFARMERPROPOSALATTACHMENT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "GROUPFARMERPROPOSALATTACHMENT_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class GroupFarmerProposalAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPROPOSALATTACHMENT_GEN")
	private String id;
	private String name;
	private String filePath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUPFARMERPROPOSALID", referencedColumnName = "ID")
	private GroupFarmerProposal groupFarmerProposal;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	public GroupFarmerProposalAttachment() {

	}

	public GroupFarmerProposalAttachment(String name, String filePath, GroupFarmerProposal proposal) {
		this.name = name;
		this.filePath = filePath;
	}

	public GroupFarmerProposalAttachment(GroupFarmerProposalAttachment attachment) {
		this.name = attachment.getName();
		this.filePath = attachment.getFilePath();
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public GroupFarmerProposal getGroupFarmerProposal() {
		return groupFarmerProposal;
	}

	public void setGroupFarmerProposal(GroupFarmerProposal groupFarmerProposal) {
		this.groupFarmerProposal = groupFarmerProposal;
	}

	public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
		return commonCreateAndUpateMarks;
	}

	public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
		this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
