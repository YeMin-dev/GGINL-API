package org.tat.gginl.api.controller.shortTermSinglePremiumCreditLife;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.services.productServices.ShortTermSinglePremiumCreditLifeService;
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.shortTermSinglePremiumCreditLifeDTO.ShortTermSinglePremiumCreditLifeDTO;
import org.tat.gginl.api.dto.singlePremiumCreditLifeDTO.SinglePremiumCreditLifeReponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Created by Sai Nyan Htay
 */

@RestController
@RequestMapping("/shortTermSinglepremiumcreditlife")
@Api(tags = "Short Term Single Premium Credit Life Proposal")
public class ShortTermSinglePremiumCreditLifeController {

	@Autowired
	private ShortTermSinglePremiumCreditLifeService shortTermSinglePremiumService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
		@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
				@ApiResponse(code = 403, message = "Access denied"),
				@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
		@ApiOperation(value = "${ShortTermSinglePremiumCreditLifeController.submitProposal}")
		public ResponseDTO<Object> submitproposal(
				@ApiParam("Submit ShortTermSinglePremiumCreditLife Proposal") @Valid @RequestBody ShortTermSinglePremiumCreditLifeDTO shortTermSinglePremiumCreditLifeProposalDTO) {
			List<LifePolicy> policyList = new ArrayList<>();
			ShortTermSinglePremiumCreditLifeDTO shortTermSinglePremiumCreditDTO = mapper.map(shortTermSinglePremiumCreditLifeProposalDTO, ShortTermSinglePremiumCreditLifeDTO.class);

			// create short Term single premium credit Life  proposal
			policyList = shortTermSinglePremiumService.createShortTermSinglePremiumCreditLifePolicy(shortTermSinglePremiumCreditDTO);

			// create response object
			List<SinglePremiumCreditLifeReponseDTO> responseList = new ArrayList<>();

			policyList.forEach(policy -> {
				SinglePremiumCreditLifeReponseDTO dto = SinglePremiumCreditLifeReponseDTO.builder()
						.bpmsInsuredPersonId(policy.getPolicyInsuredPersonList().get(0).getBpmsInsuredPersonId())
						.proposalNo(policy.getLifeProposal().getProposalNo()).policyNo(policy.getPolicyNo())
						.customerId(policy.getPolicyInsuredPersonList().get(0).isNewCustomer()
								? policy.getPolicyInsuredPersonList().get(0).getCustomer().getId()
								: null)
						.build();
				responseList.add(dto);
			});

			ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

			return responseDTO;
		}
}
