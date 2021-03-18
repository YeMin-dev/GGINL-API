package org.tat.gginl.api.dto.customerDTO;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.gginl.api.common.ContentInfoDto;
import org.tat.gginl.api.common.NameDto;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.MaritalStatus;
import org.tat.gginl.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 0, required = false)
	private String initialId;

	@ApiModelProperty(position = 1, required = false)
	private String fatherName;
	
	@ApiModelProperty(position = 2,example = "NRCNO", required = true)
	@NotNull(message = "Id Type is mandatory")
	private IdType idType;

	@ApiModelProperty(position = 3, example="009354", required = false)
	private String idNo;

	@ApiModelProperty(position = 4, example = "2020-12-16", required = true)
	@NotNull(message = "DateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 5,example = "MALE", required = true)
	@NotNull(message = "Gender is mandatory")
	private Gender gender;

	@ApiModelProperty(position = 6, example = "SINGLE", required = true)
	@NotNull(message = "Marital status is mandatory")
	private MaritalStatus maritalStatus;

//	@Valid
//	@ApiModelProperty(position = 7, required = true)
//	@NotNull(message = "ResidentAddress is mandatory")
//	private ResidentAddressDto residentAddress;
	
	@ApiModelProperty(position = 7, example = "Yangon", required = true)
	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;

	@Valid
	@ApiModelProperty(position = 8, required = true)
	@NotNull(message = "Name is mandatory")
	private NameDto name;
	
	@Valid
	@ApiModelProperty(position = 9, required = true)
	private ContentInfoDto contactInfo;
	
	@ApiModelProperty(position = 28, example = "ISSYS004000009724620062019", required = true)
	@NotBlank(message = "residentTownshipId is mandatory")
	@NotEmpty
	private String residentTownshipId;

	@ApiModelProperty(position = 10, example="ISSYS011000009823001042019")
	private String occupationId;

}
