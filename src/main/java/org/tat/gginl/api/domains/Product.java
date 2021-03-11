/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.gginl.api.common.AddOn;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.KeyFactor;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.InsuranceType;
import org.tat.gginl.api.common.emumdata.PremiumRateType;

@Entity
@Table(name = TableName.PRODUCT)
@TableGenerator(name = "PRODUCT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCT_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Transient
	private String id;
	@Transient
	private String prefix;
	private String name;
	private boolean autoCalculate;
	private float fixedValue;
	private int standardExcess;
	private float firstCommission;
	private float renewalCommission;
	private double maxSumInsured;
	private double minSumInsured;
	private int maxTerm;
	private int minTerm;
	private String multiCurPrefix;
	private int maxAge;
	private int minAge;
	private int maxHospDays;
	private int maxUnit;

	@Enumerated(EnumType.STRING)
	private PremiumRateType premiumRateType;
	/*
	 * If PremiumRateType is "BASED_ON_BASE_SUMINSURED", premium calculated by
	 * baseSumInsured
	 */
	private double baseSumInsured;

	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCTGROUPID", referencedColumnName = "ID")
	private ProductGroup productGroup;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PRODUCT_PAYMENTTYPE_LINK", joinColumns = {
			@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID") })
	private List<PaymentType> paymentTypeList;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PRODUCT_ADDON_LINK", joinColumns = {
			@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ADDONID", referencedColumnName = "ID") })
	private List<AddOn> addOnList;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PRODUCT_KEYFACTOR_LINK", joinColumns = {
			@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "KEYFACTORID", referencedColumnName = "ID") })
	private List<KeyFactor> keyFactorList;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCYID", referencedColumnName = "ID")
	private Currency currency;

	@Version
	private int version;

	public Product() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCT_GEN")
	@Access(value = AccessType.PROPERTY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = FormatID.formatId(id, getPrefix(), 10);
		}
	}


	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getAutoCalculate() {
		return this.autoCalculate;
	}

	public void setAutoCalculate(boolean autoCaluculate) {
		this.autoCalculate = autoCaluculate;
	}

	public float getFixedValue() {
		return this.fixedValue;
	}

	public void setFixedValue(float fixedValue) {
		this.fixedValue = fixedValue;
	}

	public double getBaseSumInsured() {
		return baseSumInsured;
	}

	public void setBaseSumInsured(double baseSumInsured) {
		this.baseSumInsured = baseSumInsured;
	}

	public int getStandardExcess() {
		return this.standardExcess;
	}

	public void setStandardExcess(int standardExcess) {
		this.standardExcess = standardExcess;
	}

	public float getFirstCommission() {
		return firstCommission;
	}

	public void setFirstCommission(float firstCommission) {
		this.firstCommission = firstCommission;
	}

	public void setRenewalCommission(float renewalCommission) {
		this.renewalCommission = renewalCommission;
	}

	public float getRenewalCommission() {
		return renewalCommission;
	}

	public void setMaxSumInsured(double maxSumInsured) {
		this.maxSumInsured = maxSumInsured;
	}

	public double getMaxSumInsured() {
		return maxSumInsured;
	}

	public double getMinSumInsured() {
		return minSumInsured;
	}

	public void setMinSumInsured(double minSumInsured) {
		this.minSumInsured = minSumInsured;
	}

	public int getMaxTerm() {
		return maxTerm;
	}

	public void setMaxTerm(int maxTerm) {
		this.maxTerm = maxTerm;
	}

	public int getMinTerm() {
		return minTerm;
	}

	public void setMinTerm(int minTerm) {
		this.minTerm = minTerm;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public PremiumRateType getPremiumRateType() {
		return premiumRateType;
	}

	public void setPremiumRateType(PremiumRateType premiumRateType) {
		this.premiumRateType = premiumRateType;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public List<PaymentType> getPaymentTypeList() {
		if (paymentTypeList == null) {
			paymentTypeList = new ArrayList<PaymentType>();
		}
		return paymentTypeList;
	}

	public void setPaymentTypeList(List<PaymentType> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}

	public List<AddOn> getAddOnList() {
		return this.addOnList;
	}

	public void setAddOnList(List<AddOn> addOnList) {
		this.addOnList = addOnList;
	}

	public List<KeyFactor> getKeyFactorList() {
		return keyFactorList;
	}

	public void setKeyFactorList(List<KeyFactor> keyFactorList) {
		this.keyFactorList = keyFactorList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public boolean isFireBuildingProduct(String id) {
		if (id.equals(this.id)) {
			return true;
		}
		return false;
	}

	public boolean isBasicSumInsured() {
		if (PremiumRateType.BASED_ON_BASE_SUMINSURED.equals(premiumRateType) && autoCalculate == true) {
			return true;
		}
		return false;
	}

	public String getMultiCurPrefix() {
		return multiCurPrefix;
	}

	public void setMultiCurPrefix(String multiCurPrefix) {
		this.multiCurPrefix = multiCurPrefix;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxHospDays() {
		return maxHospDays;
	}

	public void setMaxHospDays(int maxHospDays) {
		this.maxHospDays = maxHospDays;
	}

	public int getMaxUnit() {
		return maxUnit;
	}

	public void setMaxUnit(int maxUnit) {
		this.maxUnit = maxUnit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (autoCalculate ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(baseSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + Float.floatToIntBits(firstCommission);
		result = prime * result + Float.floatToIntBits(fixedValue);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuranceType == null) ? 0 : insuranceType.hashCode());
		result = prime * result + maxAge;
		result = prime * result + maxHospDays;
		temp = Double.doubleToLongBits(maxSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + maxTerm;
		result = prime * result + maxUnit;
		result = prime * result + minAge;
		temp = Double.doubleToLongBits(minSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + minTerm;
		result = prime * result + ((multiCurPrefix == null) ? 0 : multiCurPrefix.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((premiumRateType == null) ? 0 : premiumRateType.hashCode());
		result = prime * result + ((productGroup == null) ? 0 : productGroup.hashCode());
		result = prime * result + Float.floatToIntBits(renewalCommission);
		result = prime * result + standardExcess;
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
		Product other = (Product) obj;
		if (autoCalculate != other.autoCalculate)
			return false;
		if (Double.doubleToLongBits(baseSumInsured) != Double.doubleToLongBits(other.baseSumInsured))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (Float.floatToIntBits(firstCommission) != Float.floatToIntBits(other.firstCommission))
			return false;
		if (Float.floatToIntBits(fixedValue) != Float.floatToIntBits(other.fixedValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuranceType != other.insuranceType)
			return false;
		if (maxAge != other.maxAge)
			return false;
		if (maxHospDays != other.maxHospDays)
			return false;
		if (Double.doubleToLongBits(maxSumInsured) != Double.doubleToLongBits(other.maxSumInsured))
			return false;
		if (maxTerm != other.maxTerm)
			return false;
		if (maxUnit != other.maxUnit)
			return false;
		if (minAge != other.minAge)
			return false;
		if (Double.doubleToLongBits(minSumInsured) != Double.doubleToLongBits(other.minSumInsured))
			return false;
		if (minTerm != other.minTerm)
			return false;
		if (multiCurPrefix == null) {
			if (other.multiCurPrefix != null)
				return false;
		} else if (!multiCurPrefix.equals(other.multiCurPrefix))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (premiumRateType != other.premiumRateType)
			return false;
		if (productGroup == null) {
			if (other.productGroup != null)
				return false;
		} else if (!productGroup.equals(other.productGroup))
			return false;
		if (Float.floatToIntBits(renewalCommission) != Float.floatToIntBits(other.renewalCommission))
			return false;
		if (standardExcess != other.standardExcess)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}