package org.tat.gginl.api.dto.singlePremiumCreditLifeDTO;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SinglePremiumCreditLifeReponseDTO {

	private String bpmsInsuredPersonId;
	private String policyNo;
	private String proposalNo;
	private String customerId;

}
