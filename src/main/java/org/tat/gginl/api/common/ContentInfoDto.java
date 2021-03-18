package org.tat.gginl.api.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 0, required = false)
	private String phone;

	@ApiModelProperty(position = 1, required = false)
	private String fax;

	@ApiModelProperty(position = 2, required = false)
	private String mobile;

	@ApiModelProperty(position = 3, required = false)
	private String email;
	
	public String getPhoneOrMoblieNo() {
		if (phone == null && mobile == null) {
			return "-";
		} else {
			if (phone.isEmpty() && phone == "") {
				if (mobile != null) {
					return mobile;
				}
				return "-";
			} else
				return phone;
		}
	}
	
}