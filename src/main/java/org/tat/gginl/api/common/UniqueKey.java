package org.tat.gginl.api.common;

import javax.persistence.Embeddable;

@Embeddable
public class UniqueKey {
	private String batcpfx;
	private int batccoy;
	private String batcbrn;
	private int batcactyr;
	private int batcactmn;
	private String batctrcde;
	private Integer batcbatch = null;
	private String rldgacct;
	private Integer tranno = null;
	private String sacscode;
	private String sacstype;
	private String rdocnum;

	public UniqueKey() {
	}

	public UniqueKey(String batcpfx, int batccoy, String batcbrn, int batcactyr, int batcactmn, String batctrcde, int batcbatch, String rldgacct, int tranno, String sacscode,
			String sacstype, String rdocnum) {
		super();
		this.batcpfx = batcpfx;
		this.batccoy = batccoy;
		this.batcbrn = batcbrn;
		this.batcactyr = batcactyr;
		this.batcactmn = batcactmn;
		this.batctrcde = batctrcde;
		this.batcbatch = batcbatch;
		this.rldgacct = rldgacct;
		this.tranno = tranno;
		this.sacscode = sacscode;
		this.sacstype = sacstype;
		this.rdocnum = rdocnum;
	}

	public UniqueKey(UniqueKey uniqueKey) {
		this.batcpfx = uniqueKey.getBatcpfx();
		this.batccoy = uniqueKey.getBatccoy();
		this.batcbrn = uniqueKey.getBatcbrn();
		this.batcactyr = uniqueKey.getBatcactyr();
		this.batcactmn = uniqueKey.getBatcactmn();
		this.batctrcde = uniqueKey.getBatctrcde();
		this.batcbatch = uniqueKey.getBatcbatch();
		this.rldgacct = uniqueKey.getRldgacct();
		this.tranno = uniqueKey.getTranno();
		this.sacscode = uniqueKey.getSacscode();
		this.sacstype = uniqueKey.getSacstype();
		this.rdocnum = uniqueKey.getRdocnum();
	}

	public String asTLFNO() {
		return batcpfx + "-" + batccoy + "-" + batcbrn + "-" + batcactyr + "-" + batcactmn + "-" + batctrcde + "-" + batcbatch + "-"
				+ (rdocnum == null ? rldgacct + "-" + tranno : rdocnum);
	}

	public void setBatchKey(BatchKey batchKey) {
		this.batcpfx = batchKey.getBatcpfx();
		this.batccoy = batchKey.getBatccoy();
		this.batcbrn = batchKey.getBatcbrn();
		this.batcactyr = batchKey.getBatcactyr();
		this.batcactmn = batchKey.getBatcactmn();
		this.batctrcde = batchKey.getBatctrcde();
		this.batcbatch = batchKey.getBatcbatch();
	}

	public BatchKey getBatchKey() {
		BatchKey batchKey = new BatchKey(batcpfx, batccoy, batcbrn, batcactyr, batcactmn, batctrcde, batcbatch);
		return batchKey;
	}

	// public UniqueKey(String batcpfx, int batccoy, int batcbrn, int batcactyr,
	// int batcactmn) {
	// super();
	// this.batcpfx = batcpfx;
	// this.batccoy = batccoy;
	// this.batcbrn = batcbrn;
	// this.batcactyr = batcactyr;
	// this.batcactmn = batcactmn;
	// }

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

	public void setBatcbatch(int batcbatch) {
		this.batcbatch = batcbatch;
	}

	public String getRldgacct() {
		return rldgacct;
	}

	public void setRldgacct(String rldgacct) {
		this.rldgacct = rldgacct;
	}

	public Integer getTranno() {
		return tranno;
	}

	public void setTranno(int tranno) {
		this.tranno = tranno;
	}

	public void setTranno(Integer tranno) {
		this.tranno = tranno;
	}

	public String getSacscode() {
		return sacscode;
	}

	public void setSacscode(String sacscode) {
		this.sacscode = sacscode;
	}

	public String getSacstype() {
		return sacstype;
	}

	public void setSacstype(String sacstype) {
		this.sacstype = sacstype;
	}

	public String getRdocnum() {
		return rdocnum;
	}

	public void setRdocnum(String rdocnum) {
		this.rdocnum = rdocnum;
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
		result = prime * result + ((rdocnum == null) ? 0 : rdocnum.hashCode());
		result = prime * result + ((rldgacct == null) ? 0 : rldgacct.hashCode());
		result = prime * result + ((sacscode == null) ? 0 : sacscode.hashCode());
		result = prime * result + ((sacstype == null) ? 0 : sacstype.hashCode());
		result = prime * result + ((tranno == null) ? 0 : tranno.hashCode());
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
		UniqueKey other = (UniqueKey) obj;
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
		if (rdocnum == null) {
			if (other.rdocnum != null)
				return false;
		} else if (!rdocnum.equals(other.rdocnum))
			return false;
		if (rldgacct == null) {
			if (other.rldgacct != null)
				return false;
		} else if (!rldgacct.equals(other.rldgacct))
			return false;
		if (sacscode == null) {
			if (other.sacscode != null)
				return false;
		} else if (!sacscode.equals(other.sacscode))
			return false;
		if (sacstype == null) {
			if (other.sacstype != null)
				return false;
		} else if (!sacstype.equals(other.sacstype))
			return false;
		if (tranno == null) {
			if (other.tranno != null)
				return false;
		} else if (!tranno.equals(other.tranno))
			return false;
		return true;
	}

}
