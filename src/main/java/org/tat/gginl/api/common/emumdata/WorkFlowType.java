package org.tat.gginl.api.common.emumdata;

public enum WorkFlowType {
	LIFE("Life"),

	AGENT_COMMISSION("Agent Commission"),

	COINSURANCE("Co-insurance"),

	FEDILITY("Fidelity"),

	SNAKE_BITE_POLICY("Snake Bite Policy"),

	MEDICAL_INSURANCE("Medical Insurance"),

	TRAVEL("Travel Insurance"),

	LIFESURRENDER("Life Surrender"),

	LIFE_PAIDUP("Life PaidUp"),

	SHORTTERM_LIFE_PAIDUP("ShortTerm Endowment Life PaidUp"),

	PERSONAL_ACCIDENT("Personal Accident"),

	PERSON_TRAVEL("Person Travel"),

	FARMER("Farmer"),

	SHORT_ENDOWMENT("Short Term Endowment Life"),

	STUDENT_LIFE("Student Life");

	private String label;

	private WorkFlowType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
