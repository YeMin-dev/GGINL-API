package org.tat.gginl.api.common.emumdata;

public enum PolicyStatus {
	DELETE("Delete"), UPDATE("Update"), TERMINATE("Terminate"), PREPAID("Prepaid"), PAID("Paid"), DEACTIVATE("Deactivate"), REINSTATE("Reinstate"), SURRENDER("Surrender"), PAIDUP(
			"Paidup"), LOAN("Loan"), INFORCE("Inforce");

	private String label;

	private PolicyStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
