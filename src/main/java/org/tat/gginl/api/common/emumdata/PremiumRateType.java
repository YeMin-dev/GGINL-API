package org.tat.gginl.api.common.emumdata;

public enum PremiumRateType {
	USER_DEFINED_RATE("User Definied Premium Rate"), 
	BASED_ON_BASE_SUMINSURED("Based on Basic Sum Insured"),
	PERCENT_OF_SUMINSURED("Precentage of Sum Insured");

	private String label;

	private PremiumRateType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
