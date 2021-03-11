package org.tat.gginl.api.controller.publicTermLife;

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
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.publicTermLife.PublicTermLifeDTO;
import org.tat.gginl.api.dto.publicTermLife.PublicTermLifeReponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

	@RestController
	@RequestMapping("/publicTermLife")
	@Api(tags = "Public Term Life Proposal")
	public class PublicTermLifeController {

		@Autowired
		private LifeProposalService lifeProposalService;

		@Autowired
		private ModelMapper mapper;

		@PostMapping("/submitproposal")
		@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
				@ApiResponse(code = 403, message = "Access denied"),
				@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
		@ApiOperation(value = "${PublicTermLifeController.submitProposal}")
		public ResponseDTO<Object> submitproposal(
				@ApiParam("Submit PublicTermLife Proposal") @Valid @RequestBody PublicTermLifeDTO publicTermLifeProposalDTO) {
			List<LifePolicy> policyList = new ArrayList<>();
			PublicTermLifeDTO a = mapper.map(publicTermLifeProposalDTO, PublicTermLifeDTO.class);

			// create public Term Life  proposal
			policyList = lifeProposalService.createPublicTermLifePolicy(a);

			// create response object
			List<PublicTermLifeReponseDTO> responseList = new ArrayList<>();

			policyList.forEach(policy -> {
				PublicTermLifeReponseDTO dto = PublicTermLifeReponseDTO.builder()
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

