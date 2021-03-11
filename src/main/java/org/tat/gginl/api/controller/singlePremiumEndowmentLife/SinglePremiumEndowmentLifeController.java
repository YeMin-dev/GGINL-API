package org.tat.gginl.api.controller.singlePremiumEndowmentLife;

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
import org.tat.gginl.api.domains.services.LifeProposalService;
import org.tat.gginl.api.domains.services.productServices.SinglePremiumEndowmentLifeService;
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.singlePremiumCreditLifeDTO.SinglePremiumCreditLifeReponseDTO;
import org.tat.gginl.api.dto.singlePremiumEndowmentLifeDTO.SinglePremiumEndowmentLifeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

	@RestController
	@RequestMapping("/singlepremiumendowmentlife")
	@Api(tags = "Single Premium Endowment Life Proposal")
	public class SinglePremiumEndowmentLifeController {

		@Autowired
		private SinglePremiumEndowmentLifeService singlePremiumEndowmentService;

		@Autowired
		private ModelMapper mapper;

		@PostMapping("/submitproposal")
		@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
				@ApiResponse(code = 403, message = "Access denied"),
				@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
		@ApiOperation(value = "${SinglePremiumEndowmentLifeController.submitProposal}")
		public ResponseDTO<Object> submitproposal(
				@ApiParam("Submit SinglePremiumEndowmentLife Proposal") @Valid @RequestBody SinglePremiumEndowmentLifeDTO singlePremiumEndowmentLifeProposalDTO) {
			List<LifePolicy> policyList = new ArrayList<>();
			SinglePremiumEndowmentLifeDTO singlePremiumEndowmentDTO = mapper.map(singlePremiumEndowmentLifeProposalDTO, SinglePremiumEndowmentLifeDTO.class);

			// create single premium Endowment Life  proposal
			policyList = singlePremiumEndowmentService.createSinglePremiumEndowmentLifePolicy(singlePremiumEndowmentDTO);

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

