package org.tat.gginl.api.controller.healthController;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.gginl.api.domains.MedicalPolicy;
import org.tat.gginl.api.domains.services.MedicalProposalService;
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.healthdto.HealthProposalDTO;
import org.tat.gginl.api.dto.healthdto.HealthReponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping("/health")
@Api(tags = "Health")
public class HealthController {

  @Autowired
  private MedicalProposalService medicalProposalService;

  @PostMapping("/submitproposal")

  @ApiResponses(value = {@ApiResponse(code = 400, message = "Something went wrong"),
      @ApiResponse(code = 403, message = "Access denied"),
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  @ApiOperation(value = "${HealthController.submitproposal}")
  public ResponseDTO<Object> submitproposal(
      @Valid @RequestBody HealthProposalDTO healthProposalDTO) {
    // try {
    List<MedicalPolicy> policyList = new ArrayList<>();
    // create farmer proposal
    policyList = medicalProposalService.createHealthProposalToPolicy(healthProposalDTO);
    // create response object
    List<HealthReponseDTO> responseList = new ArrayList<HealthReponseDTO>();

    policyList.forEach(policy -> {
      HealthReponseDTO dto = HealthReponseDTO.builder()
          .bpmsInsuredPersonId(policy.getPolicyInsuredPersonList().get(0).getBpmsInsuredPersonId())
          .proposalNo(policy.getMedicalProposal().getProposalNo()).policyNo(policy.getPolicyNo())
          .customerId(policy.getPolicyInsuredPersonList().get(0).isNewCustomer()
              ? policy.getPolicyInsuredPersonList().get(0).getCustomer().getId()
              : null)
          .build();
      responseList.add(dto);
    });

    ResponseDTO<Object> responseDTO =
        ResponseDTO.builder().status("Success!").responseBody(responseList).build();
    return responseDTO;
  }

}
