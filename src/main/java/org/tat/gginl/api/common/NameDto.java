package org.tat.gginl.api.common;


	import java.io.Serializable;

import lombok.Data;

	
	@Data
	public class NameDto implements Serializable {
		private static final long serialVersionUID = -8381245681586350716L;

		private String firstName;

		private String middleName;

		private String lastName;

		public NameDto() {
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

	
}
