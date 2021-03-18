package org.tat.gginl.api.common.emumdata;

public enum SurveyAnswerOne {
	YES("Yes"), NO("No");

	private String label;

	private SurveyAnswerOne(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
