package org.tat.gginl.api.common.emumdata;

public enum EndorsementStatus {
	REPLACE("REPLACE"), TERMINATE_BY_CUSTOMER("TERMINATE BY CUSTOMER"), TERMINATE_BY_GGLI("TERMINATE BY GGLI"), TERMINATE("TERMINATE"), TERMINATE_BY_RENEWAL(
			"TERMINATE BY RENEWAL"), EDIT("EDIT"), NEW("NEW");

	private String label;

	private EndorsementStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
