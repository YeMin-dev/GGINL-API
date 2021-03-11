package org.tat.gginl.api.common.emumdata;

public enum WorkflowTask {
	UNDERWRITING("Underwriting"), SURVEY("Survey"), APPROVAL("Approval"), INFORM("Inform"), CONFIRMATION("Confirmation"), PAYMENT("Payment"), PROPOSAL_REJECT("Reject"), ISSUING(
			"Issuing"),

	ENDORSEMENT_UNDERWRITING("Endorsement Underwriting"), ENDORSEMENT_SURVEY("Endorsement Survey"), ENDORSEMENT_APPROVAL("Endorsement Approval"), ENDORSEMENT_INFORM(
			"Endorsement Inform"), ENDORSEMENT_CONFIRMATION("Endorsement Confirmation"), ENDORSEMENT_PAYMENT(
					"Endorsement Payment"), ENDORSEMENT_PROPOSAL_REJECT("Endorsement Proposal Reject"), ENDORSEMENT_ISSUING("Endorsement Issuing"),

	CLAIM_SURVEY("Claim Survey"), CLAIM_APPROVAL("Claim Approval"), CLAIM_REQUEST("Claim Request"), CLAIM_INFORM("Claim Inform"), CLAIM_CONFIRMATION(
			"Claim Confirmation"), CLAIM_PAYMENT("Claim Payment"), CLAIM_REJECT("Claim Reject"),

	AGENT_COMMISSION_PAYMENT("Agent Commission Payment"),

	COINSURANCE_CONFIRMATION("Coinsurance Confirmation"), COINSURANCE_PAYMENT("Coinsurance Payment"),

	BILL_COLLECT_PAYMENT("Bill Collect Payment"),

	RENEWAL_UNDERWRITING("Renewal Underwriting"), RENEWAL_SURVEY("Renewal Survey"), RENEWAL_APPROVAL("Renewal Approval"), RENEWAL_INFORM("Renewal Inform"), RENEWAL_CONFIRMATION(
			"Renewal Confirmation"), RENEWAL_PAYMENT("Renewal Payment"), RENEWAL_PROPOSAL_REJECT("Renewal Reject"), RENEWAL_ISSUING("Renewal Issuing"),

	TERMINATE_APPROVAL("Terminate Approval"), TERMINATE_INFORM("Terminate Inform"), TERMINATE_CONFIRMATION("Terminate Confirmation"), TERMINATE_PROPOSAL_REJECT(
			"terminate Proposal Reject"), TERMINATE_PAYMENT("Terminate Payment"), TERMINATE_ISSUING("Terminate Issuing"),TRAVEL_CONFIRMATION("Travel Confirmation"),TRAVEL_PAYMENT("Travel Payment"),MEDICAL_APPROVAL("Medical Approval"),
	MEDICAL_INFORM("Medical Inform"),MEDICAL_CLAIM_INFORM("Medical Claim Inform"),MEDICAL_CLAIM_APPROVAL("Medical Claim Approval"),MEDICAL_CONFIRMATION("Medical Confirmation"),MEDICAL_CLAIM_PAYMENT("Medical Claim Payment"),
	MEDICAL_PAYMENT("Medical Payment"),MEDICAL_CLAIM_SURVEY("Medical Claim Survey"),MEDICAL_SURVEY("Medical Survey"),MEDICAL_CLAIM_COMFIRMATION("Medical Claim Confirmation"),MEDICAL_UNDERWRITING("Medical Underwriting"),
	MEDICAL_CLAIM_REQUEST("Medical Claim Request"),MEDICAL_ISSUE("Medical Issue");
	
	private String label;

	private WorkflowTask(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static WorkflowTask getPaymentBy(WorkflowReferenceType referenceType) {
		switch (referenceType) {
			case AGENT_COMMISSION:
				return AGENT_COMMISSION_PAYMENT;

			case COINSURANCE:
				return COINSURANCE_PAYMENT;

			case CRITICAL_ILLNESS_POLICY:
			case CRITICAL_ILLNESS_PROPOSAL:
			case HEALTH_POLICY:
			case HEALTH_PROPOSAL:
			case MEDICAL_POLICY:
			case MEDICAL_PROPOSAL:
			case MICRO_HEALTH_POLICY:
			case MICRO_HEALTH_PROPOSAL:
			case FARMER_PROPOSAL:
			case LIFESURRENDER_PROPOSAL:
			case LIFE_PAIDUP_PROPOSAL:
			case LIFE_PROPOSAL:
			case PA_PROPOSAL:
			case PERSON_TRAVEL_PROPOSAL:
			case SHORTENDOWMENTLIFESURRENDER_PROPOSAL:
			case SHORTTERM_LIFE_PAIDUP_PROPOSAL:
			case SHORT_ENDOWMENT_LIFE_PROPOSAL:
			case SNAKEBITEPOLICY:
			case TRAVEL_PROPOSAL:
				return PAYMENT;

			case LIFE_DIS_CLAIM:
			case LIFE_CLAIM:
			case MEDICAL_CLAIM:
				return CLAIM_PAYMENT;

			case LIFE_COLLECTION:
				return BILL_COLLECT_PAYMENT;

			case MEDICAL_RENEWAL_PROPOSAL:
				return RENEWAL_PAYMENT;
			default:
				return PAYMENT;

		}
	}

	public static WorkflowTask getConfirmationBy(WorkflowReferenceType referenceType) {
		switch (referenceType) {
			case COINSURANCE:
				return COINSURANCE_CONFIRMATION;

			case CRITICAL_ILLNESS_POLICY:
			case CRITICAL_ILLNESS_PROPOSAL:
			case HEALTH_POLICY:
			case HEALTH_PROPOSAL:
			case MEDICAL_POLICY:
			case MEDICAL_PROPOSAL:
			case MICRO_HEALTH_POLICY:
			case MICRO_HEALTH_PROPOSAL:
			case FARMER_PROPOSAL:
			case LIFESURRENDER_PROPOSAL:
			case LIFE_PAIDUP_PROPOSAL:
			case LIFE_PROPOSAL:
			case PA_PROPOSAL:
			case PERSON_TRAVEL_PROPOSAL:
			case SHORTENDOWMENTLIFESURRENDER_PROPOSAL:
			case SHORTTERM_LIFE_PAIDUP_PROPOSAL:
			case SHORT_ENDOWMENT_LIFE_PROPOSAL:
			case SNAKEBITEPOLICY:
			case TRAVEL_PROPOSAL:
				return CONFIRMATION;

			case LIFE_DIS_CLAIM:
			case LIFE_CLAIM:
			case MEDICAL_CLAIM:
				return CLAIM_CONFIRMATION;

			case MEDICAL_RENEWAL_PROPOSAL:
				return RENEWAL_CONFIRMATION;
			default:
				return CONFIRMATION;

		}
	}
}
