package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.InterfaceFile;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;

@Entity
@Table(name = TableName.TLF)
@TableGenerator(name = "TLF_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "TLF_GEN", allocationSize = 10)
@NamedQueries(
    value = {@NamedQuery(name = "findByEno", query = "SELECT t FROM TLF t WHERE t.enoNo = :enoNo"),
        @NamedQuery(name = "TLF.reverseByTLFNo",
            query = "UPDATE TLF t SET t.reverse=true WHERE t.tlfNo = :tlfNo ")})
@Access(value = AccessType.FIELD)
public class TLF implements Serializable {
  private static final long serialVersionUID = -299125992570974772L;
  @Transient
  private String id;
  @Transient
  private String prefix;

  private String customerId;
  private String coaId;
  private double homeAmount;
  private double localAmount;
  private String currencyId;
  private String chequeNo;
  @Column(name = "ENO")
  private String enoNo;
  private String bankId;
  private String purchaseOrderId;
  private String loanId;
  private String narration;
  private String status;
  private String tranCode;
  private String orgnTLFid;
  private String branchId;
  private double rate;
  @Temporal(TemporalType.DATE)
  private Date settlementDate;
  private boolean reverse;
  private String referenceNo;
  @Column(name = "REFERENCETYPE")
  @Enumerated(value = EnumType.STRING)
  private PolicyReferenceType referenceType;
  @Column(name = "PAID")
  private boolean paid;

  @Enumerated(value = EnumType.STRING)
  private PaymentChannel paymentChannel;

  /**
   * TLF Number (tlfNo) is cash receipt number.
   */
  @Column(name = "TLFNO")
  private String tlfNo;

  @Column(name = "CLEARING")
  private boolean clearing;

  @Column(name = "ISRENEWAL")
  private boolean isRenewal;

  @Embedded
  private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

  @Column(name = "BPMSRECEIPTNO")
  private String bpmsReceiptNo;

  @Version
  private int version;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = TableName.INTERFACE_FILE_TLF_REF,
      joinColumns = {@JoinColumn(name = "TLFID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "INTERFACE_FILE_ID", referencedColumnName = "ID")})
  private InterfaceFile interfaceFile;

  @OneToOne
  @JoinColumn(name = "SALEPOINTID")
  private SalePoint salePoint;

  private String policyNo;

  private boolean agentTransaction;

  @Override
  public String toString() {
    return coaId + " | " + status + " | " + homeAmount;
  }

  public TLF() {

  }

  public TLF(String customerId, String coaId, double homeAmount, double localAmount,
      String currencyId, String chequeNo, String enoNo, String bankId, String purchaseOrderId,
      String loanId, String narration, String status, String tranCode, String orgnTLFid,
      String branchId, double rate, Date settlementDate, boolean reverse, String referenceNo,
      PolicyReferenceType referenceType) {
    this.customerId = customerId;
    this.coaId = coaId;
    this.homeAmount = homeAmount;
    this.localAmount = localAmount;
    this.currencyId = currencyId;
    this.chequeNo = chequeNo;
    this.enoNo = enoNo;
    this.bankId = bankId;
    this.purchaseOrderId = purchaseOrderId;
    this.loanId = loanId;
    this.narration = narration;
    this.status = status;
    this.tranCode = tranCode;
    this.orgnTLFid = orgnTLFid;
    this.branchId = branchId;
    this.rate = rate;
    this.settlementDate = settlementDate;
    this.reverse = reverse;
    this.referenceNo = referenceNo;
    this.referenceType = referenceType;
  }

  public TLF(double homeAmount, double localAmount, double rate, String currency, String chequeNo,
      String status, String trancode, String enoNo, String referenceNo, String bankId,
      String customerId, String branchId, String coaId, String narration, Date settlementDate,
      PolicyReferenceType referenceType, boolean isRenewal, String tlfNo) {
    this.homeAmount = homeAmount;
    this.localAmount = localAmount;
    this.rate = rate;
    this.currencyId = currency;
    this.chequeNo = chequeNo;
    this.status = status;
    this.tranCode = trancode;
    this.enoNo = enoNo;
    this.referenceNo = referenceNo;
    this.bankId = bankId;
    this.customerId = customerId;
    this.branchId = branchId;
    this.coaId = coaId;
    this.narration = narration;
    this.settlementDate = settlementDate;
    this.referenceType = referenceType;
    this.isRenewal = isRenewal;
    this.tlfNo = tlfNo;
  }

  public TLF(String id, String prefix, String customerId, String coaId, double homeAmount,
      double localAmount, String currencyId, String chequeNo, String enoNo, String bankId,
      String purchaseOrderId, String loanId, String narration, String status, String tranCode,
      String orgnTLFid, String branchId, double rate, Date settlementDate, boolean reverse,
      String referenceNo, PolicyReferenceType referenceType, boolean paid, String tlfNo,
      boolean clearing, boolean isRenewal, int version, Date createdDate, Date updatedDate,
      String createdUserId, String updatedUserId) {
    this.id = id;
    this.prefix = prefix;
    this.customerId = customerId;
    this.coaId = coaId;
    this.homeAmount = homeAmount;
    this.localAmount = localAmount;
    this.currencyId = currencyId;
    this.chequeNo = chequeNo;
    this.enoNo = enoNo;
    this.bankId = bankId;
    this.purchaseOrderId = purchaseOrderId;
    this.loanId = loanId;
    this.narration = narration;
    this.status = status;
    this.tranCode = tranCode;
    this.orgnTLFid = orgnTLFid;
    this.branchId = branchId;
    this.rate = rate;
    this.settlementDate = settlementDate;
    this.reverse = reverse;
    this.referenceNo = referenceNo;
    this.referenceType = referenceType;
    this.paid = paid;
    this.tlfNo = tlfNo;
    this.clearing = clearing;
    this.isRenewal = isRenewal;
    this.version = version;
    this.commonCreateAndUpateMarks = new CommonCreateAndUpateMarks();
    this.commonCreateAndUpateMarks.setCreatedDate(createdDate);
    this.commonCreateAndUpateMarks.setUpdatedDate(updatedDate);
    this.commonCreateAndUpateMarks.setCreatedUserId(createdUserId);
    this.commonCreateAndUpateMarks.setUpdatedUserId(updatedUserId);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TLF_GEN")
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

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCoaId() {
    return coaId;
  }

  public void setCoaId(String coaId) {
    this.coaId = coaId;
  }

  public double getHomeAmount() {
    return homeAmount;
  }


  public void setHomeAmount(double homeAmount) {
    this.homeAmount = homeAmount;
  }

  public double getLocalAmount() {
    return localAmount;
  }

  public void setLocalAmount(double localAmount) {
    this.localAmount = localAmount;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public String getChequeNo() {
    return chequeNo;
  }

  public void setChequeNo(String chequeNo) {
    this.chequeNo = chequeNo;
  }

  public String getEnoNo() {
    return enoNo;
  }

  public void setEnoNo(String enoNo) {
    this.enoNo = enoNo;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  public String getPurchaseOrderId() {
    return purchaseOrderId;
  }

  public void setPurchaseOrderId(String purchaseOrderId) {
    this.purchaseOrderId = purchaseOrderId;
  }

  public String getLoanId() {
    return loanId;
  }

  public void setLoanId(String loanId) {
    this.loanId = loanId;
  }

  public String getNarration() {
    return narration;
  }

  public void setNarration(String narration) {
    this.narration = narration;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTranCode() {
    return tranCode;
  }

  public void setTranCode(String tranCode) {
    this.tranCode = tranCode;
  }

  public String getOrgnTLFid() {
    return orgnTLFid;
  }

  public void setOrgnTLFid(String orgnTLFid) {
    this.orgnTLFid = orgnTLFid;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public Date getSettlementDate() {
    return settlementDate;
  }

  public void setSettlementDate(Date settlementDate) {
    this.settlementDate = settlementDate;
  }

  public boolean isReverse() {
    return reverse;
  }

  public void setReverse(boolean reverse) {
    this.reverse = reverse;
  }

  public String getReferenceNo() {
    return referenceNo;
  }

  public void setReferenceNo(String referenceNo) {
    this.referenceNo = referenceNo;
  }

  public PolicyReferenceType getReferenceType() {
    return referenceType;
  }

  public void setReferenceType(PolicyReferenceType referenceType) {
    this.referenceType = referenceType;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public boolean isPaid() {
    return paid;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  public String getTlfNo() {
    return tlfNo;
  }

  public void setTlfNo(String tlfNo) {
    this.tlfNo = tlfNo;
  }

  public boolean isClearing() {
    return clearing;
  }

  public void setClearing(boolean clearing) {
    this.clearing = clearing;
  }

  public boolean isRenewal() {
    return isRenewal;
  }

  public void setRenewal(boolean isRenewal) {
    this.isRenewal = isRenewal;
  }

  public InterfaceFile getInterfaceFile() {
    return interfaceFile;
  }

  public void setInterfaceFile(InterfaceFile interfaceFile) {
    this.interfaceFile = interfaceFile;
  }

  public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
    if (commonCreateAndUpateMarks == null) {
      commonCreateAndUpateMarks = new CommonCreateAndUpateMarks();
    }
    return commonCreateAndUpateMarks;
  }

  public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
    this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
  }

  public PaymentChannel getPaymentChannel() {
    return paymentChannel;
  }

  public void setPaymentChannel(PaymentChannel paymentChannel) {
    this.paymentChannel = paymentChannel;
  }

  public SalePoint getSalePoint() {
    return salePoint;
  }

  public void setSalePoint(SalePoint salePoint) {
    this.salePoint = salePoint;
  }

  public String getPolicyNo() {
    return policyNo;
  }

  public void setPolicyNo(String policyNo) {
    this.policyNo = policyNo;
  }

  public boolean isAgentTransaction() {
    return agentTransaction;
  }

  public void setAgentTransaction(boolean agentTransaction) {
    this.agentTransaction = agentTransaction;
  }

  public String getBpmsReceiptNo() {
	return bpmsReceiptNo;
  }

  public void setBpmsReceiptNo(String bpmsReceiptNo) {
	this.bpmsReceiptNo = bpmsReceiptNo;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (agentTransaction ? 1231 : 1237);
	result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
	result = prime * result + ((bpmsReceiptNo == null) ? 0 : bpmsReceiptNo.hashCode());
	result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
	result = prime * result + ((chequeNo == null) ? 0 : chequeNo.hashCode());
	result = prime * result + (clearing ? 1231 : 1237);
	result = prime * result + ((coaId == null) ? 0 : coaId.hashCode());
	result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
	result = prime * result + ((currencyId == null) ? 0 : currencyId.hashCode());
	result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
	result = prime * result + ((enoNo == null) ? 0 : enoNo.hashCode());
	long temp;
	temp = Double.doubleToLongBits(homeAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((interfaceFile == null) ? 0 : interfaceFile.hashCode());
	result = prime * result + (isRenewal ? 1231 : 1237);
	result = prime * result + ((loanId == null) ? 0 : loanId.hashCode());
	temp = Double.doubleToLongBits(localAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((narration == null) ? 0 : narration.hashCode());
	result = prime * result + ((orgnTLFid == null) ? 0 : orgnTLFid.hashCode());
	result = prime * result + (paid ? 1231 : 1237);
	result = prime * result + ((paymentChannel == null) ? 0 : paymentChannel.hashCode());
	result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
	result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
	result = prime * result + ((purchaseOrderId == null) ? 0 : purchaseOrderId.hashCode());
	temp = Double.doubleToLongBits(rate);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
	result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
	result = prime * result + (reverse ? 1231 : 1237);
	result = prime * result + ((salePoint == null) ? 0 : salePoint.hashCode());
	result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	result = prime * result + ((tlfNo == null) ? 0 : tlfNo.hashCode());
	result = prime * result + ((tranCode == null) ? 0 : tranCode.hashCode());
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
	TLF other = (TLF) obj;
	if (agentTransaction != other.agentTransaction)
		return false;
	if (bankId == null) {
		if (other.bankId != null)
			return false;
	} else if (!bankId.equals(other.bankId))
		return false;
	if (bpmsReceiptNo == null) {
		if (other.bpmsReceiptNo != null)
			return false;
	} else if (!bpmsReceiptNo.equals(other.bpmsReceiptNo))
		return false;
	if (branchId == null) {
		if (other.branchId != null)
			return false;
	} else if (!branchId.equals(other.branchId))
		return false;
	if (chequeNo == null) {
		if (other.chequeNo != null)
			return false;
	} else if (!chequeNo.equals(other.chequeNo))
		return false;
	if (clearing != other.clearing)
		return false;
	if (coaId == null) {
		if (other.coaId != null)
			return false;
	} else if (!coaId.equals(other.coaId))
		return false;
	if (commonCreateAndUpateMarks == null) {
		if (other.commonCreateAndUpateMarks != null)
			return false;
	} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
		return false;
	if (currencyId == null) {
		if (other.currencyId != null)
			return false;
	} else if (!currencyId.equals(other.currencyId))
		return false;
	if (customerId == null) {
		if (other.customerId != null)
			return false;
	} else if (!customerId.equals(other.customerId))
		return false;
	if (enoNo == null) {
		if (other.enoNo != null)
			return false;
	} else if (!enoNo.equals(other.enoNo))
		return false;
	if (Double.doubleToLongBits(homeAmount) != Double.doubleToLongBits(other.homeAmount))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (interfaceFile == null) {
		if (other.interfaceFile != null)
			return false;
	} else if (!interfaceFile.equals(other.interfaceFile))
		return false;
	if (isRenewal != other.isRenewal)
		return false;
	if (loanId == null) {
		if (other.loanId != null)
			return false;
	} else if (!loanId.equals(other.loanId))
		return false;
	if (Double.doubleToLongBits(localAmount) != Double.doubleToLongBits(other.localAmount))
		return false;
	if (narration == null) {
		if (other.narration != null)
			return false;
	} else if (!narration.equals(other.narration))
		return false;
	if (orgnTLFid == null) {
		if (other.orgnTLFid != null)
			return false;
	} else if (!orgnTLFid.equals(other.orgnTLFid))
		return false;
	if (paid != other.paid)
		return false;
	if (paymentChannel != other.paymentChannel)
		return false;
	if (policyNo == null) {
		if (other.policyNo != null)
			return false;
	} else if (!policyNo.equals(other.policyNo))
		return false;
	if (prefix == null) {
		if (other.prefix != null)
			return false;
	} else if (!prefix.equals(other.prefix))
		return false;
	if (purchaseOrderId == null) {
		if (other.purchaseOrderId != null)
			return false;
	} else if (!purchaseOrderId.equals(other.purchaseOrderId))
		return false;
	if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
		return false;
	if (referenceNo == null) {
		if (other.referenceNo != null)
			return false;
	} else if (!referenceNo.equals(other.referenceNo))
		return false;
	if (referenceType != other.referenceType)
		return false;
	if (reverse != other.reverse)
		return false;
	if (salePoint == null) {
		if (other.salePoint != null)
			return false;
	} else if (!salePoint.equals(other.salePoint))
		return false;
	if (settlementDate == null) {
		if (other.settlementDate != null)
			return false;
	} else if (!settlementDate.equals(other.settlementDate))
		return false;
	if (status == null) {
		if (other.status != null)
			return false;
	} else if (!status.equals(other.status))
		return false;
	if (tlfNo == null) {
		if (other.tlfNo != null)
			return false;
	} else if (!tlfNo.equals(other.tlfNo))
		return false;
	if (tranCode == null) {
		if (other.tranCode != null)
			return false;
	} else if (!tranCode.equals(other.tranCode))
		return false;
	if (version != other.version)
		return false;
	return true;
}

  
}
