package org.tat.gginl.api.common.emumdata;

	public enum MaritalStatus {
		MARRIED("Married"), SINGLE("Single"), DIVORCED("Divorced"), WIDOWED("Widowed");

		private String label;

		private MaritalStatus(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}


