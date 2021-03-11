package org.tat.gginl.api.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@Entity
@Table(name = TableName.INTERFACE_FILE)
@EntityListeners(IDInterceptor.class)
public class InterfaceFile implements Serializable {
	private static final long serialVersionUID = -4084926942570551050L;

	@Id
	private int id;

	@Embedded
	private UniqueKey uniqueKey;

	private String rdocpfx;
	private int rdoccoy;

	private String rldgpfx;
	private int rldgcoy;
	private String chdrpfx;
	private int chdrcoy;
	private String chdrnum;
	private String origcurr;
	private String acctcurr;
	private double crate;
	private String cnttype;
	private String cntbranch;
	private String chdrstcda;
	private String chdrstcdb;
	private String chdrstcdc;
	private String chdrstcdd;
	private String chdrstcde;
	private int postmonth;
	private int postyear;

	@Temporal(TemporalType.DATE)
	private Date trandate;

	private int trantime;

	@Temporal(TemporalType.DATE)
	private Date effdate;

	private double tranamt;
	private String genlcur;
	private String genlpfx;
	private int genlcoy;
	private String glcode;
	private String accpfx;
	private int acccoy;
	private String accnum;

	@Temporal(TemporalType.DATE)
	private Date ccdate;
	@Temporal(TemporalType.DATE)
	private Date exdate;

	private String statreasn;
	private String ritype;

	@Temporal(TemporalType.TIMESTAMP)
	private Date importedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date convertedDate;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	@Column(name = "IS_CONVERTED")
	private boolean isConverted;

	public InterfaceFile() {
	}

	// public InterfaceFile(String batcpfx, String batccoy, String batcbrn,
	// String batcactyr, String batcactmn, String batctrcde, String batcbatch,
	// String rdocpfx, String rdoccoy,
	// String rdocnum, String rldgpfx, String rldgcoy, String rldgacct, String
	// chdrpfx, String chdrcoy, String chdrnum, String origcurr, String
	// acctcurr, String crate,
	// String cnttype, String tranno, String cntbranch, String chdrstcda, String
	// chdrstcdb, String chdrstcdc, String chdrstcdd, String chdrstcde, String
	// postmonth,
	// String postyear, int trandate, String trantime, String effdate, String
	// sacscode, String sacstype, String tranamt, String genlcur, String
	// genlpfx, String genlcoy,
	// String glcode, String accpfx, int acccoy, String accnum, String ccdate,
	// String exdate, String statreasn, String ritype) {
	// this.uniqueKey = new UniqueKey(batcpfx, Integer.parseInt(batccoy),
	// Integer.parseInt(batcbrn), Integer.parseInt(batcactyr),
	// Integer.parseInt(batcactmn), batctrcde,
	// Integer.parseInt(batcbatch), rldgacct, Integer.parseInt(tranno),
	// sacscode, sacstype, rdocnum);
	// this.rdocpfx = rdocpfx;
	// this.rdoccoy = Integer.parseInt(rdoccoy);
	// this.rldgpfx = rldgpfx;
	// this.rldgcoy = Integer.parseInt(rldgcoy);
	// this.chdrpfx = chdrpfx;
	// this.chdrcoy = Integer.parseInt(chdrcoy);
	// this.chdrnum = chdrnum;
	// this.origcurr = origcurr;
	// this.acctcurr = acctcurr;
	// this.crate = Double.parseDouble(crate);
	// this.cnttype = cnttype;
	// this.cntbranch = cntbranch;
	// this.chdrstcda = chdrstcda;
	// this.chdrstcdb = chdrstcdb;
	// this.chdrstcdc = chdrstcdc;
	// this.chdrstcdd = chdrstcdd;
	// this.chdrstcde = chdrstcde;
	// this.postmonth = Integer.parseInt(postmonth);
	// this.postyear = Integer.parseInt(postyear);
	// this.trandate = trandate;
	// this.trantime = Integer.parseInt(trantime);
	// this.effdate = Integer.parseInt(effdate);
	//
	// this.tranamt = Double.parseDouble(tranamt);
	// this.genlcur = genlcur;
	// this.genlpfx = genlpfx;
	// this.genlcoy = Integer.parseInt(genlcoy);
	// this.glcode = glcode;
	// this.accpfx = accpfx;
	// this.acccoy = acccoy;
	// this.accnum = accnum;
	// this.ccdate = Integer.parseInt(ccdate);
	// this.exdate = Integer.parseInt(exdate);
	// this.statreasn = statreasn;
	// this.ritype = ritype;
	// }

	// V Getter setters--------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UniqueKey getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(UniqueKey uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getRdocpfx() {
		return rdocpfx;
	}

	public void setRdocpfx(String rdocpfx) {
		this.rdocpfx = rdocpfx;
	}

	public int getRdoccoy() {
		return rdoccoy;
	}

	public void setRdoccoy(int rdoccoy) {
		this.rdoccoy = rdoccoy;
	}

	public String getRldgpfx() {
		return rldgpfx;
	}

	public void setRldgpfx(String rldgpfx) {
		this.rldgpfx = rldgpfx;
	}

	public int getRldgcoy() {
		return rldgcoy;
	}

	public void setRldgcoy(int rldgcoy) {
		this.rldgcoy = rldgcoy;
	}

	public String getChdrpfx() {
		return chdrpfx;
	}

	public void setChdrpfx(String chdrpfx) {
		this.chdrpfx = chdrpfx;
	}

	public int getChdrcoy() {
		return chdrcoy;
	}

	public void setChdrcoy(int chdrcoy) {
		this.chdrcoy = chdrcoy;
	}

	public String getChdrnum() {
		return chdrnum;
	}

	public void setChdrnum(String chdrnum) {
		this.chdrnum = chdrnum;
	}

	public String getOrigcurr() {
		return origcurr;
	}

	public void setOrigcurr(String origcurr) {
		this.origcurr = origcurr;
	}

	public String getAcctcurr() {
		return acctcurr;
	}

	public void setAcctcurr(String acctcurr) {
		this.acctcurr = acctcurr;
	}

	public double getCrate() {
		return crate;
	}

	public void setCrate(double crate) {
		this.crate = crate;
	}

	public String getCnttype() {
		return cnttype;
	}

	public void setCnttype(String cnttype) {
		this.cnttype = cnttype;
	}

	public String getCntbranch() {
		return cntbranch;
	}

	public void setCntbranch(String cntbranch) {
		this.cntbranch = cntbranch;
	}

	public String getChdrstcda() {
		return chdrstcda;
	}

	public void setChdrstcda(String chdrstcda) {
		this.chdrstcda = chdrstcda;
	}

	public String getChdrstcdb() {
		return chdrstcdb;
	}

	public void setChdrstcdb(String chdrstcdb) {
		this.chdrstcdb = chdrstcdb;
	}

	public String getChdrstcdc() {
		return chdrstcdc;
	}

	public void setChdrstcdc(String chdrstcdc) {
		this.chdrstcdc = chdrstcdc;
	}

	public String getChdrstcdd() {
		return chdrstcdd;
	}

	public void setChdrstcdd(String chdrstcdd) {
		this.chdrstcdd = chdrstcdd;
	}

	public String getChdrstcde() {
		return chdrstcde;
	}

	public void setChdrstcde(String chdrstcde) {
		this.chdrstcde = chdrstcde;
	}

	public int getPostmonth() {
		return postmonth;
	}

	public void setPostmonth(int postmonth) {
		this.postmonth = postmonth;
	}

	public int getPostyear() {
		return postyear;
	}

	public void setPostyear(int postyear) {
		this.postyear = postyear;
	}

	public int getTrantime() {
		return trantime;
	}

	public void setTrantime(int trantime) {
		this.trantime = trantime;
	}

	public double getTranamt() {
		return tranamt;
	}

	public void setTranamt(double tranamt) {
		this.tranamt = tranamt;
	}

	public String getGenlcur() {
		return genlcur;
	}

	public void setGenlcur(String genlcur) {
		this.genlcur = genlcur;
	}

	public String getGenlpfx() {
		return genlpfx;
	}

	public void setGenlpfx(String genlpfx) {
		this.genlpfx = genlpfx;
	}

	public int getGenlcoy() {
		return genlcoy;
	}

	public void setGenlcoy(int genlcoy) {
		this.genlcoy = genlcoy;
	}

	public String getGlcode() {
		return glcode;
	}

	public void setGlcode(String glcode) {
		this.glcode = glcode;
	}

	public String getAccpfx() {
		return accpfx;
	}

	public void setAccpfx(String accpfx) {
		this.accpfx = accpfx;
	}

	public int getAcccoy() {
		return acccoy;
	}

	public void setAcccoy(int acccoy) {
		this.acccoy = acccoy;
	}

	public String getAccnum() {
		return accnum;
	}

	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}

	public Date getEffdate() {
		return effdate;
	}

	public void setEffdate(Date effdate) {
		this.effdate = effdate;
	}

	public Date getCcdate() {
		return ccdate;
	}

	public void setCcdate(Date ccdate) {
		this.ccdate = ccdate;
	}

	public Date getExdate() {
		return exdate;
	}

	public void setExdate(Date exdate) {
		this.exdate = exdate;
	}

	public void setTrandate(Date trandate) {
		this.trandate = trandate;
	}

	public String getStatreasn() {
		return statreasn;
	}

	public void setStatreasn(String statreasn) {
		this.statreasn = statreasn;
	}

	public String getRitype() {
		return ritype;
	}

	public void setRitype(String ritype) {
		this.ritype = ritype;
	}

	public Date getImportedDate() {
		return importedDate;
	}

	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	public boolean isConverted() {
		return isConverted;
	}

	public void setConverted(boolean isConverted) {
		this.isConverted = isConverted;
	}

	public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
		return commonCreateAndUpateMarks;
	}

	public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
		this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getConvertedDate() {
		return convertedDate;
	}

	public void setConvertedDate(Date convertedDate) {
		this.convertedDate = convertedDate;
	}

	public Date getTrandate() {
		return trandate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acccoy;
		result = prime * result + ((accnum == null) ? 0 : accnum.hashCode());
		result = prime * result + ((accpfx == null) ? 0 : accpfx.hashCode());
		result = prime * result + ((acctcurr == null) ? 0 : acctcurr.hashCode());
		result = prime * result + ((ccdate == null) ? 0 : ccdate.hashCode());
		result = prime * result + chdrcoy;
		result = prime * result + ((chdrnum == null) ? 0 : chdrnum.hashCode());
		result = prime * result + ((chdrpfx == null) ? 0 : chdrpfx.hashCode());
		result = prime * result + ((chdrstcda == null) ? 0 : chdrstcda.hashCode());
		result = prime * result + ((chdrstcdb == null) ? 0 : chdrstcdb.hashCode());
		result = prime * result + ((chdrstcdc == null) ? 0 : chdrstcdc.hashCode());
		result = prime * result + ((chdrstcdd == null) ? 0 : chdrstcdd.hashCode());
		result = prime * result + ((chdrstcde == null) ? 0 : chdrstcde.hashCode());
		result = prime * result + ((cntbranch == null) ? 0 : cntbranch.hashCode());
		result = prime * result + ((cnttype == null) ? 0 : cnttype.hashCode());
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((convertedDate == null) ? 0 : convertedDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(crate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((effdate == null) ? 0 : effdate.hashCode());
		result = prime * result + ((exdate == null) ? 0 : exdate.hashCode());
		result = prime * result + genlcoy;
		result = prime * result + ((genlcur == null) ? 0 : genlcur.hashCode());
		result = prime * result + ((genlpfx == null) ? 0 : genlpfx.hashCode());
		result = prime * result + ((glcode == null) ? 0 : glcode.hashCode());
		result = prime * result + id;
		result = prime * result + ((importedDate == null) ? 0 : importedDate.hashCode());
		result = prime * result + (isConverted ? 1231 : 1237);
		result = prime * result + ((origcurr == null) ? 0 : origcurr.hashCode());
		result = prime * result + postmonth;
		result = prime * result + postyear;
		result = prime * result + rdoccoy;
		result = prime * result + ((rdocpfx == null) ? 0 : rdocpfx.hashCode());
		result = prime * result + ((ritype == null) ? 0 : ritype.hashCode());
		result = prime * result + rldgcoy;
		result = prime * result + ((rldgpfx == null) ? 0 : rldgpfx.hashCode());
		result = prime * result + ((statreasn == null) ? 0 : statreasn.hashCode());
		temp = Double.doubleToLongBits(tranamt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((trandate == null) ? 0 : trandate.hashCode());
		result = prime * result + trantime;
		result = prime * result + ((uniqueKey == null) ? 0 : uniqueKey.hashCode());
		result = prime * result + version;
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
		InterfaceFile other = (InterfaceFile) obj;
		if (acccoy != other.acccoy)
			return false;
		if (accnum == null) {
			if (other.accnum != null)
				return false;
		} else if (!accnum.equals(other.accnum))
			return false;
		if (accpfx == null) {
			if (other.accpfx != null)
				return false;
		} else if (!accpfx.equals(other.accpfx))
			return false;
		if (acctcurr == null) {
			if (other.acctcurr != null)
				return false;
		} else if (!acctcurr.equals(other.acctcurr))
			return false;
		if (ccdate == null) {
			if (other.ccdate != null)
				return false;
		} else if (!ccdate.equals(other.ccdate))
			return false;
		if (chdrcoy != other.chdrcoy)
			return false;
		if (chdrnum == null) {
			if (other.chdrnum != null)
				return false;
		} else if (!chdrnum.equals(other.chdrnum))
			return false;
		if (chdrpfx == null) {
			if (other.chdrpfx != null)
				return false;
		} else if (!chdrpfx.equals(other.chdrpfx))
			return false;
		if (chdrstcda == null) {
			if (other.chdrstcda != null)
				return false;
		} else if (!chdrstcda.equals(other.chdrstcda))
			return false;
		if (chdrstcdb == null) {
			if (other.chdrstcdb != null)
				return false;
		} else if (!chdrstcdb.equals(other.chdrstcdb))
			return false;
		if (chdrstcdc == null) {
			if (other.chdrstcdc != null)
				return false;
		} else if (!chdrstcdc.equals(other.chdrstcdc))
			return false;
		if (chdrstcdd == null) {
			if (other.chdrstcdd != null)
				return false;
		} else if (!chdrstcdd.equals(other.chdrstcdd))
			return false;
		if (chdrstcde == null) {
			if (other.chdrstcde != null)
				return false;
		} else if (!chdrstcde.equals(other.chdrstcde))
			return false;
		if (cntbranch == null) {
			if (other.cntbranch != null)
				return false;
		} else if (!cntbranch.equals(other.cntbranch))
			return false;
		if (cnttype == null) {
			if (other.cnttype != null)
				return false;
		} else if (!cnttype.equals(other.cnttype))
			return false;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (convertedDate == null) {
			if (other.convertedDate != null)
				return false;
		} else if (!convertedDate.equals(other.convertedDate))
			return false;
		if (Double.doubleToLongBits(crate) != Double.doubleToLongBits(other.crate))
			return false;
		if (effdate == null) {
			if (other.effdate != null)
				return false;
		} else if (!effdate.equals(other.effdate))
			return false;
		if (exdate == null) {
			if (other.exdate != null)
				return false;
		} else if (!exdate.equals(other.exdate))
			return false;
		if (genlcoy != other.genlcoy)
			return false;
		if (genlcur == null) {
			if (other.genlcur != null)
				return false;
		} else if (!genlcur.equals(other.genlcur))
			return false;
		if (genlpfx == null) {
			if (other.genlpfx != null)
				return false;
		} else if (!genlpfx.equals(other.genlpfx))
			return false;
		if (glcode == null) {
			if (other.glcode != null)
				return false;
		} else if (!glcode.equals(other.glcode))
			return false;
		if (id != other.id)
			return false;
		if (importedDate == null) {
			if (other.importedDate != null)
				return false;
		} else if (!importedDate.equals(other.importedDate))
			return false;
		if (isConverted != other.isConverted)
			return false;
		if (origcurr == null) {
			if (other.origcurr != null)
				return false;
		} else if (!origcurr.equals(other.origcurr))
			return false;
		if (postmonth != other.postmonth)
			return false;
		if (postyear != other.postyear)
			return false;
		if (rdoccoy != other.rdoccoy)
			return false;
		if (rdocpfx == null) {
			if (other.rdocpfx != null)
				return false;
		} else if (!rdocpfx.equals(other.rdocpfx))
			return false;
		if (ritype == null) {
			if (other.ritype != null)
				return false;
		} else if (!ritype.equals(other.ritype))
			return false;
		if (rldgcoy != other.rldgcoy)
			return false;
		if (rldgpfx == null) {
			if (other.rldgpfx != null)
				return false;
		} else if (!rldgpfx.equals(other.rldgpfx))
			return false;
		if (statreasn == null) {
			if (other.statreasn != null)
				return false;
		} else if (!statreasn.equals(other.statreasn))
			return false;
		if (Double.doubleToLongBits(tranamt) != Double.doubleToLongBits(other.tranamt))
			return false;
		if (trandate == null) {
			if (other.trandate != null)
				return false;
		} else if (!trandate.equals(other.trandate))
			return false;
		if (trantime != other.trantime)
			return false;
		if (uniqueKey == null) {
			if (other.uniqueKey != null)
				return false;
		} else if (!uniqueKey.equals(other.uniqueKey))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
