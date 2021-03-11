package org.tat.gginl.api.controller.billCollection;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.services.PaymentService;
import org.tat.gginl.api.dto.ResponseDTO;
import org.tat.gginl.api.dto.billcollectionDTO.BillCollectionDTO;
import org.tat.gginl.api.dto.billcollectionDTO.BillCollectionPaymentResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/billCollection")
@Api(tags = "Bill Collection")
public class BillCollectionController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/payment")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${BillCollectionController.payment}")
	public ResponseDTO<Object> submitproposal(@Valid @RequestBody BillCollectionDTO billCollectionDTO) {
//		try {
		List<Payment> paymentList = new ArrayList<>();
		paymentList = paymentService.paymentBillCollection(billCollectionDTO);
		// create response object
		List<BillCollectionPaymentResponseDTO> responseList = new ArrayList<BillCollectionPaymentResponseDTO>();

		paymentList.forEach(payment -> {
			BillCollectionPaymentResponseDTO dto = BillCollectionPaymentResponseDTO.builder()
					.policyNo(billCollectionDTO.getPolicyNo()).receiptNo(payment.getReceiptNo()).build();

			responseList.add(dto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
