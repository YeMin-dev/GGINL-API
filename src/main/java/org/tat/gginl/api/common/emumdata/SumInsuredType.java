package org.tat.gginl.api.common.emumdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "sumInsuredType")
@XmlEnum
public enum SumInsuredType {
	Decreasing_SI("Decreasing SI"), Fixed_SI("Fixed SI");

	private String label;

	private SumInsuredType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
