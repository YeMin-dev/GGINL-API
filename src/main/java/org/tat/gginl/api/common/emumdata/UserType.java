package org.tat.gginl.api.common.emumdata;

public enum UserType {
	AGENT("Agent"), SALEMAN("Saleman"), REFERRAL("Referral"), WALKIN("Walkin");
	private String label;

	private UserType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
