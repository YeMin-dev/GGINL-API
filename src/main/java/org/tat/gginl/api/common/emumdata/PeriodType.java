package org.tat.gginl.api.common.emumdata;

import java.util.ArrayList;
import java.util.List;

public enum PeriodType {
	WEEK("Week"), DAY("Day"), HOUR("Hour"), MINUTE("Minute"), MONTH("Month"), YEAR("Year");

	private String label;

	private PeriodType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static List<PeriodType> getAvailableTypes(PeriodType min, PeriodType max) {
		List<PeriodType> result = new ArrayList<>();
		for (PeriodType period : PeriodType.values()) {
			if (period.ordinal() >= min.ordinal() && period.ordinal() <= max.ordinal()) {
				result.add(period);
			}
		}
		return result;
	}
}
