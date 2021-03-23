package org.tat.gginl.api.dto.simpleLifeDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.gginl.api.configuration.DateHandler;
import org.tat.gginl.api.dto.customerDTO.CustomerDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleLifeDTO {

  @ApiModelProperty(position = 0, example = "2019-12-16", required = true)
  @NotNull(message = "SubmittedDate is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date submittedDate;

  @ApiModelProperty(position = 1, example = "BANCH00000000000000129032018", required = true)
  @NotBlank(message = "branchId is mandatory")
  private String branchId;

  @ApiModelProperty(position = 2, example = "ISSYS001000005575112092016")
  private String referralID;
  
  @Valid
  @ApiModelProperty(position = 3)
  private CustomerDto customer;
  
  @ApiModelProperty(position = 4, example = "ISSYS033000009784102042019")
  private String organizationId;

  @ApiModelProperty(position = 5, example = "ISSYS0090001000000000429032013", required = true)
  @NotBlank(message = "paymentTypeId is mandatory")
  private String paymentTypeId;

  @ApiModelProperty(position = 6, example = "ISSYS002000009756217052019", required = true)
  @NotBlank(message = "agentID is mandatory")
  private String agentID;

  @ApiModelProperty(position = 7, example = "ISSYS022000009634116052019")
  private String saleManId;
  
  @ApiModelProperty(position = 8, example = "11", required = true)
  @NotBlank(message = "salePointId is mandatory")
  private String salePointId;
  
  @ApiModelProperty(position = 9, example = "TRF", required = true)
  private String paymentChannel;

  @ApiModelProperty(position = 10, example = "ISSYS010005000000021118072016", required = true)
  private String toBank;

  @ApiModelProperty(position = 11, example = "ISSYS0100001000000000713032013", required = true)
  private String fromBank;
  
  @ApiModelProperty(position = 12, example = "CH34345345", required = true)
  private String chequeNo;

//  @ApiModelProperty(position = 13, example = "1234", required = true)
//  @NotBlank(message = "userId is mandatory")
//  private String userId;
  
  @ApiModelProperty(position = 13, example = "BPMS/P/0001", required = true)
  private String gginlProposalNo;

  @ApiModelProperty(position = 14, example = "BPMS/CSH/0001", required = true)
  private String gginlReceiptNo;
  
  @ApiModelProperty(position = 15, example = "0001", required = true)
  @NotBlank(message = "gginlAppID is mandatory")
  private String gginlAppID;
  
  @ApiModelProperty(position = 16, example = "name", required = true)
  @NotBlank(message = "gginlAppID is mandatory")
  private String gginlAppName;
  
  @ApiModelProperty(position = 16, example = "2019-12-16", required = true)
  @NotNull(message = "Payment Confirm Date is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date paymentConfirmDate;

  @ApiModelProperty(position = 17, required = true)
  @NotNull(message = "proposalInsuredPersonList is mandatory")
  private List<SimpleLifeProposalInsuredPersonDTO> proposalInsuredPersonList;
  

}
