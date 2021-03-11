/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.common.emumdata;

public enum InsuranceType {

	LIFE("Life"),

	MEDICAL("Medical"),

	TRAVEL_INSURANCE("Travel Insurance"),

	FIDELITY("Fidelity"),

	PERSON_TRAVEL("Person Travel"),

	PERSONAL_ACCIDENT("Personal Accident"),

	FARMER("Farmer"),

	SHORT_ENDOWMENT_LIFE("Short Term Endowment"),

	HEALTH("Health"),

	MICRO_HEALTH("Micro health"),

	CRITICAL_ILLNESS("Critical Illness"),

	GROUP_MICRO_HEALTH("Group Micro Health"),

	GROUP_FARMER("Group Farmer"),

	STUDENT_LIFE("Student Life");

	private String label;

	private InsuranceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}