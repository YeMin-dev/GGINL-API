package org.tat.gginl.api.common.emumdata;

import java.util.Arrays;
import java.util.List;

public enum PolicyReferenceType {
	LIFE_POLICY("Life Policy"),

	LIFE_CLAIM("Life Claim"),

	LIFE_DIS_CLAIM("Life Disablity Claim"),

	LIFE_SURRENDER_CLAIM("Life Surrender Claim"),

	SHORTTERMLIFE_SURRENDER_CLAIM("ShortTermLife Surrender Claim"),

	SHORTTERMLIFE_PAIDUP_CLAIM("ShortTermLife PaidUp Claim"),

	LIFE_PAIDUP_CLAIM("Life PaidUp Claim"),

	LIFE_BILL_COLLECTION("Life Bill Collection"),

	SNAKE_BITE_POLICY("Snake Bite Policy"),

	TRAVEL_PROPOSAL("Travel Proposal"),

	MEDICAL_POLICY("Medical Policy"),

	MEDICAL_CLAIM("Medical Claim"),

	MEDICAL_DEATH_CLAIM("Medical Death Claim"),

	MEDICAL_BILL_COLLECTION("Medical Bill Collection"),

	PA_POLICY("PA Policy"),

	PERSON_TRAVEL_POLICY("Person Travel Policy"),

	FARMER_POLICY("Farmer Policy"),

	SHORT_ENDOWMENT_LIFE_POLICY("Short Term Endowment Life Policy"),

	SHORT_ENDOWMENT_LIFE_BILL_COLLECTION("Short Term Endowment Life Bill Collection"),

	CSC_IMPORT("CSC_IMPORT"),

	HEALTH_POLICY("Health Policy"),

	MICRO_HEALTH_POLICY("Micro Health Policy"),

	CRITICAL_ILLNESS_POLICY("Critical Illness Policy"),

	HEALTH_POLICY_BILL_COLLECTION("Health Policy Bill Collection"),

	MICRO_HEALTH_POLICY_BILL_COLLECTION("Micro Health Policy Bill Collection"),

	CRITICAL_ILLNESS_POLICY_BILL_COLLECTION("Critical Illness Policy Bill Collection"),

	GROUP_MICRO_HEALTH("Group Micro Health"),

	GROUP_FARMER_PROPOSAL("Group Farmer Proposal"),

	GROUP_MICRO_HEALTH_BILL_COLLECTION("Group Micro Health Bill Collection"),

	STUDENT_LIFE_POLICY("Student Life Policy"),

	PUBLIC_TERM_LIFE_POLICY("Public Term Life Policy"),

	STUDENT_LIFE_POLICY_BILL_COLLECTION("Student Life Policy Bill Collection"),

	SINGLE_PREMIUM_CREDIT_LIFE_POLICY("Single Premium Credit Life Policy"),

	SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_POLICY("Short Term Single Premium Credit Life Policy"),

	SINGLE_PREMIUM_ENDOWMENT_LIFE_POLICY("Single Premium Endowment Life Policy"),

	SIMPLE_LIFE_POLICY("Simple Life");

	private String label;

	private PolicyReferenceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static List<PolicyReferenceType> getMedicalPolicyReference() {
		return Arrays.asList(MEDICAL_POLICY, HEALTH_POLICY, MICRO_HEALTH_POLICY, CRITICAL_ILLNESS_POLICY);
	}

	public static List<PolicyReferenceType> getMedicalPolicyReferenceForReport() {
		return Arrays.asList(MEDICAL_POLICY, HEALTH_POLICY, MICRO_HEALTH_POLICY, CRITICAL_ILLNESS_POLICY, HEALTH_POLICY_BILL_COLLECTION, CRITICAL_ILLNESS_POLICY_BILL_COLLECTION);
	}

}
