package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = TableName.INSURANCE_HISTORY_RECORD)
@TableGenerator(name = "INSURANCEHISTORYRECORD_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "INSURANCEHISTORYRECORD_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class InsuranceHistoryRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "INSURANCEHISTORYRECORD_GEN")
	private String id;
	private String policyNo;
	@Transient
	private String tempId;
	private Double sIAmount;
	@Temporal(TemporalType.DATE)
	private Date policyDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANYID", referencedColumnName = "ID")
	private CoinsuranceCompany company;
	private boolean isActive;
	private Integer periodOfInsurance;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;
	@Embedded
	private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

	public InsuranceHistoryRecord() {
		tempId = System.nanoTime() + "";
	}

	public InsuranceHistoryRecord(InsuranceHistoryRecord record) {
		policyNo = record.getPolicyNo();
		sIAmount = record.getsIAmount();
		policyDate = record.getPolicyDate();
		company = record.getCompany();
		isActive = record.isActive();
		periodOfInsurance = record.getPeriodOfInsurance();
		product = record.getProduct();
	}

	@Version
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getPolicyDate() {
		return policyDate;
	}

	public Double getsIAmount() {
		return sIAmount;
	}

	public void setsIAmount(Double sIAmount) {
		this.sIAmount = sIAmount;
	}

	public Integer getPeriodOfInsurance() {
		return periodOfInsurance;
	}

	public void setPeriodOfInsurance(Integer periodOfInsurance) {
		this.periodOfInsurance = periodOfInsurance;
	}

	public void setPolicyDate(Date policyDate) {
		this.policyDate = policyDate;
	}

	public CoinsuranceCompany getCompany() {
		if (company == null) {
			company = new CoinsuranceCompany();
		}
		return company;
	}

	public void setCompany(CoinsuranceCompany company) {
		this.company = company;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((periodOfInsurance == null) ? 0 : periodOfInsurance.hashCode());
		result = prime * result + ((policyDate == null) ? 0 : policyDate.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((sIAmount == null) ? 0 : sIAmount.hashCode());
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
		InsuranceHistoryRecord other = (InsuranceHistoryRecord) obj;
		if (commonCreateAndUpateMarks == null) {
			if (other.commonCreateAndUpateMarks != null)
				return false;
		} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive != other.isActive)
			return false;
		if (periodOfInsurance == null) {
			if (other.periodOfInsurance != null)
				return false;
		} else if (!periodOfInsurance.equals(other.periodOfInsurance))
			return false;
		if (policyDate == null) {
			if (other.policyDate != null)
				return false;
		} else if (!policyDate.equals(other.policyDate))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (sIAmount == null) {
			if (other.sIAmount != null)
				return false;
		} else if (!sIAmount.equals(other.sIAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
