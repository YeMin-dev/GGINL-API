package org.tat.gginl.api.dto.healthdto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class HealthReponseDTO {

  private String bpmsInsuredPersonId;
  private String policyNo;
  private String proposalNo;
  private String customerId;



}
