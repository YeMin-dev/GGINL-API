package org.tat.gginl.api.common;

public class BatchKey {
	private String batcpfx;
	private int batccoy;
	private String batcbrn;
	private int batcactyr;
	private int batcactmn;
	private String batctrcde;
	private Integer batcbatch = null;

	public BatchKey() {
	}

	public BatchKey(String batcpfx, int batccoy, String batcbrn, int batcactyr, int batcactmn, String batctrcde, int batcbatch) {
		super();
		this.batcpfx = batcpfx;
		this.batccoy = batccoy;
		this.batcbrn = batcbrn;
		this.batcactyr = batcactyr;
		this.batcactmn = batcactmn;
		this.batctrcde = batctrcde;
		this.batcbatch = batcbatch;
	}

	@Override
	public String toString() {
		return this.batcpfx + this.batccoy + this.batcbrn + this.batcactyr + this.batcactmn + this.batctrcde + this.batcbatch;
	}

	public String getBatcpfx() {
		return batcpfx;
	}

	public void setBatcpfx(String batcpfx) {
		this.batcpfx = batcpfx;
	}

	public int getBatccoy() {
		return batccoy;
	}

	public void setBatccoy(int batccoy) {
		this.batccoy = batccoy;
	}

	public String getBatcbrn() {
		return batcbrn;
	}

	public void setBatcbrn(String batcbrn) {
		this.batcbrn = batcbrn;
	}

	public int getBatcactyr() {
		return batcactyr;
	}

	public void setBatcactyr(int batcactyr) {
		this.batcactyr = batcactyr;
	}

	public int getBatcactmn() {
		return batcactmn;
	}

	public void setBatcactmn(int batcactmn) {
		this.batcactmn = batcactmn;
	}

	public String getBatctrcde() {
		return batctrcde;
	}

	public void setBatctrcde(String batctrcde) {
		this.batctrcde = batctrcde;
	}

	public Integer getBatcbatch() {
		return batcbatch;
	}

	public void setBatcbatch(Integer batcbatch) {
		this.batcbatch = batcbatch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + batcactmn;
		result = prime * result + batcactyr;
		result = prime * result + ((batcbatch == null) ? 0 : batcbatch.hashCode());
		result = prime * result + ((batcbrn == null) ? 0 : batcbrn.hashCode());
		result = prime * result + batccoy;
		result = prime * result + ((batcpfx == null) ? 0 : batcpfx.hashCode());
		result = prime * result + ((batctrcde == null) ? 0 : batctrcde.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchKey other = (BatchKey) obj;
		if (batcactmn != other.batcactmn)
			return false;
		if (batcactyr != other.batcactyr)
			return false;
		if (batcbatch == null) {
			if (other.batcbatch != null)
				return false;
		} else if (!batcbatch.equals(other.batcbatch))
			return false;
		if (batcbrn == null) {
			if (other.batcbrn != null)
				return false;
		} else if (!batcbrn.equals(other.batcbrn))
			return false;
		if (batccoy != other.batccoy)
			return false;
		if (batcpfx == null) {
			if (other.batcpfx != null)
				return false;
		} else if (!batcpfx.equals(other.batcpfx))
			return false;
		if (batctrcde == null) {
			if (other.batctrcde != null)
				return false;
		} else if (!batctrcde.equals(other.batctrcde))
			return false;
		return true;
	}

}
