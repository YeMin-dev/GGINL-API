package org.tat.gginl.api.common.emumdata;

public enum ProposalStatus {
	DENIED_BY_GGLI("Denied by GGINL"), NOT_TAKEN_BY_CUS("Not taken by customer"), PROPOSAL_EXPIRED("Proposal Expired");
	private String label;

	private ProposalStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
