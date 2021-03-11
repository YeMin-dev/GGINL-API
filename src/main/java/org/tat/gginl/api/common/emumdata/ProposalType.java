package org.tat.gginl.api.common.emumdata;

public enum ProposalType {

	UNDERWRITING("UNDERWRITING"), ENDORSEMENT("ENDORSEMENT"), RENEWAL("RENEWAL"), TERMINATE("TERMINATE");

	private String label;

	private ProposalType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
