package org.tat.gginl.api.controller.groupFarmer;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.services.LifeProposalService;
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.FarmerProposalDTO;
import org.tat.gginl.api.dto.groupFarmerDTO.GroupFarmerResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/groupfarmer")
@Api(tags = "Group-Farmer")
public class GroupFarmerController {

	@Autowired
	private LifeProposalService lifeProposalService;

	@PostMapping("/submitproposal")

	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${GroupFarmerController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@Valid @RequestBody FarmerProposalDTO groupFarmerProposalDTO) {
//		try {
		List<LifePolicy> policyList = new ArrayList<>();
		// create farmer proposal
		policyList = lifeProposalService.createGroupFarmerProposalToPolicy(groupFarmerProposalDTO);
		// create response object
		List<GroupFarmerResponseDTO> responseList = new ArrayList<GroupFarmerResponseDTO>();

		policyList.forEach(policy -> {
			GroupFarmerResponseDTO dto = GroupFarmerResponseDTO.builder()
					.bpmsInsuredPersonId(policy.getPolicyInsuredPersonList().get(0).getBpmsInsuredPersonId())
					.proposalNo(policy.getProposalNo()).policyNo(policy.getPolicyNo())
					.groupProposalNo(policy.getGroupFarmerProposalNo())
					.customerId(policy.getPolicyInsuredPersonList().get(0).isNewCustomer()
							? policy.getPolicyInsuredPersonList().get(0).getCustomer().getId()
							: null)
					.build();

			responseList.add(dto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}
//		catch(Exception e)
//		{
//			e.getMessage();
//		}
//		return null;
//	}

}
