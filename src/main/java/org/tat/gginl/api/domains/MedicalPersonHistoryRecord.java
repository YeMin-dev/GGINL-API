package org.tat.gginl.api.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.IDInterceptor;
import org.tat.gginl.api.common.TableName;


@Entity
@Table(name = TableName.MEDICALPERSONHISTORYRECORD)
@TableGenerator(name = "MEDICALPERSONHISTORYRECORD_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPERSONHISTORYRECORD_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalPersonHistoryRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPERSONHISTORYRECORD_GEN")
	private String id;
	@Transient
	private String tempId;
	private String policyNo;
	@Temporal(TemporalType.DATE)
	private Date fromDate;
	@Temporal(TemporalType.DATE)
	private Date toDate;
	private int unit;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANYID", referencedColumnName = "ID")
	private CoinsuranceCompany company;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "historyRecord", orphanRemoval = true)
	private List<PersonProductHistory> productList;

	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	@Version
	private int version;

	public MedicalPersonHistoryRecord() {
		tempId = System.nanoTime() + "";
	}

	public MedicalPersonHistoryRecord(MedicalPersonHistoryRecord record) {
		this.policyNo = record.getPolicyNo();
		this.fromDate = record.getFromDate();
		this.toDate = record.getToDate();
		this.unit = record.getUnit();
		this.company = record.getCompany();
		this.commonCreateAndUpateMarks = record.getCommonCreateAndUpateMarks();
		this.productList = getNewProductList();
	}

	public List<PersonProductHistory> getNewProductList() {
		List<PersonProductHistory> list = new ArrayList<>();
		for (PersonProductHistory product : getProductList()) {
			product.setHistoryRecord(this);
			product.setId(null);
			list.add(product);
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public CoinsuranceCompany getCompany() {
		return company;
	}

	public void setCompany(CoinsuranceCompany company) {
		this.company = company;
	}

	public List<PersonProductHistory> getProductList() {
		if (productList == null) {
			productList = new ArrayList<PersonProductHistory>();
		}
		return productList;
	}

	public void setProductList(List<PersonProductHistory> productList) {
		this.productList = productList;
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

	public void addPersonProductHistory(PersonProductHistory product) {
		product.setHistoryRecord(this);
		getProductList().add(product);
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		result = prime * result + unit;
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
		MedicalPersonHistoryRecord other = (MedicalPersonHistoryRecord) obj;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;

		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
