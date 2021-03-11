package org.tat.gginl.api.domains;

import java.util.Date;
import java.util.List;

import org.tat.gginl.api.common.emumdata.ProposalType;


public class GroupFarmerProposalDTO {
	private String id;
	private String proposalNo;
	private Date submittedDate;
	private Date endDate;
	private int noOfInsuredPerson;
	private ProposalType proposalType;
	private PaymentType paymentType;

	private Branch branch;

	private double totalSI;

	private Agent agent;

	private SaleMan saleMan;

	private Customer referral;

	private double premium;

	private boolean isPaymentComplete;

	private SalePoint salePoint;

	private List<LifeProposal> lifeProposalList;

	private Organization organization;

	private boolean showIssueButton;

	private int version;

	private List<GroupFarmerProposalAttachment> attachMentList;

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		} else if (saleMan != null) {
			return saleMan.getFullName();
		} else if (referral != null) {
			return referral.getFullName();
		}
		return null;
	}

	public GroupFarmerProposalDTO(GroupFarmerProposal groupFarmerProposal) {
		this.id = groupFarmerProposal.getId();
		this.proposalNo = groupFarmerProposal.getProposalNo();
		this.submittedDate = groupFarmerProposal.getSubmittedDate();
		this.endDate = groupFarmerProposal.getEndDate();
		this.noOfInsuredPerson = groupFarmerProposal.getNoOfInsuredPerson();
		this.totalSI = groupFarmerProposal.getTotalSI();
		this.agent = groupFarmerProposal.getAgent();
		this.premium = groupFarmerProposal.getPremium();
		this.saleMan = groupFarmerProposal.getSaleMan();
		this.referral = groupFarmerProposal.getReferral();
		this.isPaymentComplete = groupFarmerProposal.isPaymentComplete();
		this.salePoint = groupFarmerProposal.getSalePoint();
		this.paymentType = groupFarmerProposal.getPaymentType();
		this.branch = groupFarmerProposal.getBranch();
		this.proposalType = groupFarmerProposal.getProposalType();
		this.organization = groupFarmerProposal.getOrganization();
		this.version = groupFarmerProposal.getVersion();
		this.attachMentList = groupFarmerProposal.getAttachmentList();

	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNoOfInsuredPerson() {
		return noOfInsuredPerson;
	}

	public void setNoOfInsuredPerson(int noOfInsuredPerson) {
		this.noOfInsuredPerson = noOfInsuredPerson;
	}

	public double getTotalSI() {
		return totalSI;
	}

	public void setTotalSI(double totalSI) {
		this.totalSI = totalSI;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public SaleMan getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(SaleMan saleMan) {
		this.saleMan = saleMan;
	}

	public Customer getReferral() {
		return referral;
	}

	public void setReferral(Customer referral) {
		this.referral = referral;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public boolean isPaymentComplete() {
		return isPaymentComplete;
	}

	public void setPaymentComplete(boolean isPaymentComplete) {
		this.isPaymentComplete = isPaymentComplete;
	}

	public List<LifeProposal> getLifeProposalList() {
		return lifeProposalList;
	}

	public void setLifeProposalList(List<LifeProposal> lifeProposalList) {
		this.lifeProposalList = lifeProposalList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SalePoint getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalePoint salePoint) {
		this.salePoint = salePoint;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public boolean isShowIssueButton() {
		return showIssueButton;
	}

	public void setShowIssueButton(boolean showIssueButton) {
		this.showIssueButton = showIssueButton;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public int getVersion() {
		return version;
	}

	public List<GroupFarmerProposalAttachment> getAttachMentList() {
		return attachMentList;
	}

	public void setAttachMentList(List<GroupFarmerProposalAttachment> attachMentList) {
		this.attachMentList = attachMentList;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
