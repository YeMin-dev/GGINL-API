package org.tat.gginl.api.common;
	import java.io.Serializable;

import lombok.Data;

	
	@Data
	public class ContentInfoDto implements Serializable {
		private static final long serialVersionUID = 1L;

		private String phone;

		private String fax;

		private String mobile;

		private String email;

		public ContentInfoDto() {
		}

		public String getPhone() {
			return this.phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getFax() {
			return this.fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getMobile() {
			return this.mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getEmail() {
			return this.email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
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
