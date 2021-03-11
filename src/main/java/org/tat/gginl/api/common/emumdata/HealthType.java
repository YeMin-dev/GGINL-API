package org.tat.gginl.api.common.emumdata;

public enum HealthType {

	MICROHEALTH("Micro Health"), CRITICALILLNESS("Critical Illness"), HEALTH("Health"), MEDICAL_INSURANCE("MEDICAL_INSURANCE");

	private String label;

	private HealthType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
