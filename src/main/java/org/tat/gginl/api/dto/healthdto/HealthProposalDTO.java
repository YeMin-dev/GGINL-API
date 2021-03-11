package org.tat.gginl.api.dto.healthdto;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.tat.gginl.api.configuration.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HealthProposalDTO {

  @ApiModelProperty(position = 0, example = "2019-12-16", required = true)
  @NotNull(message = "SubmittedDate is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date submittedDate;

  @ApiModelProperty(position = 1, example = "BANCH00000000000000129032018", required = true)
  @NotBlank(message = "branchId is mandatory")
  private String branchId;

  @ApiModelProperty(position = 2, example = "ISSYS001000005575112092016")
  private String referralID;

  @ApiModelProperty(position = 3, example = "ISSYS001000018029701042019", required = true)
  private String customerID;

  @ApiModelProperty(position = 4, example = "ISSYS0090001000000000429032013", required = true)
  @NotBlank(message = "paymentTypeId is mandatory")
  private String paymentTypeId;

  @ApiModelProperty(position = 5, example = "ISSYS002000009756217052019", required = true)
  @NotBlank(message = "agentID is mandatory")
  private String agentID;

  @ApiModelProperty(position = 6, example = "ISSYS022000009634116052019")
  private String saleManId;

  @ApiModelProperty(position = 7, required = true)
  @NotNull(message = "proposalInsuredPersonList is mandatory")
  private List<HealthProposalInsuredPersonDTO> proposalInsuredPersonList;

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

  @ApiModelProperty(position = 13, example = "1234", required = true)
  @NotBlank(message = "userId is mandatory")
  private String userId;

  @ApiModelProperty(position = 14, example = "BPMS/P/0001", required = true)
  private String bpmsProposalNo;

  @ApiModelProperty(position = 15, example = "BPMS/CSH/0001", required = true)
  private String bpmsReceiptNo;

  @ApiModelProperty(position = 16, example = "2019-12-16", required = true)
  @NotNull(message = "Payment Confirm Date is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date paymentConfirmDate;

  @ApiModelProperty(position = 17, example = "FIRSTCLASS")
  @NotBlank(message = "ClassificationOfHealth is mandatory")
  private String classificationOfHealth;



  @ApiModelProperty(position = 18, example = "ISSYS012000009552804092019", required = true)
  @NotBlank(message = "relationshipId is mandatory")
  private String relationshipId;

  @ApiModelProperty(position = 19, example = "ISSYS007001000000251219122015", required = true)
  @NotBlank(message = "nationalityId is mandatory")
  private String nationalityId;

  @ApiModelProperty(position = 20, example = "ISSYS004000009724620062019", required = true)
  @NotBlank(message = "residentTownshipId is mandatory")
  private String residentTownshipId;

  @ApiModelProperty(position = 21, example = "Yangon", required = true)
  @NotBlank(message = "residentAddress is mandatory")
  private String residentAddress;

  @ApiModelProperty(position = 22, example = "ISSYS004000009724620062019")
  private String officeTownshipId;

  @ApiModelProperty(position = 23, example = "Yangon", required = true)
  private String officeAddress;


}
