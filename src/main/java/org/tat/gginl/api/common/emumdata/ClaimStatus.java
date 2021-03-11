package org.tat.gginl.api.common.emumdata;

public enum ClaimStatus {
	DEATH_CLAIM("DEATH_CLAIM"), DISABILITY_CLAIM("DISABILITY_CLAIM"), PAID("PAID"), WAITING("WAITING"), INITIAL_INFORM("INITIAL_INFORM"), CLAIM_APPLIED("CLAIM_APPLIED");

	private String label;

	private ClaimStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
