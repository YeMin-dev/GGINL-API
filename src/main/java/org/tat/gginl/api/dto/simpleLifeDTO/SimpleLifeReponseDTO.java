package org.tat.gginl.api.dto.simpleLifeDTO;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleLifeReponseDTO {

  private String bpmsInsuredPersonId;
  private String policyNo;
  private String proposalNo;
  private String customerId;

}
