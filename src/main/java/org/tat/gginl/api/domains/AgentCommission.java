package org.tat.gginl.api.domains;

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
import org.tat.gginl.api.common.IDataModel;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.AgentCommissionEntryType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;


@Entity
@Table(name = TableName.AGENTCOMMISSION)
@TableGenerator(name = "AGENTCOMMISSION_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "AGENTCOMMISSION_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "AgentCommission.findAll", query = "SELECT a FROM AgentCommission a "),
		@NamedQuery(name = "AgentCommission.findById", query = "SELECT a FROM AgentCommission a WHERE a.id = :id"),
		@NamedQuery(name = "AgentCommission.findByReferenceNo", query = "SELECT a FROM AgentCommission a WHERE a.referenceNo = :referenceNo"),
		@NamedQuery(name = "AgentCommission.deleteByReceiptNo",query = "DELETE FROM AgentCommission a WHERE a.receiptNo =:receiptNo")})
@Access(value = AccessType.FIELD)
public class AgentCommission implements IDataModel {
	@Transient
	private String id;
	@Transient
	private String prefix;
	private String invoiceNo;
	@Column(name = "REFERENCENO")
	private String referenceNo;
	@Column(name = "REFERENCETYPE")
	@Enumerated(value = EnumType.STRING)
	private PolicyReferenceType referenceType;
	@Column(name = "COMMISSION")
	private double commission;
	@Temporal(TemporalType.DATE)
	private Date commissionStartDate;
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	private Boolean isPaid;
	private Boolean status;
	@Column(name = "CHEQUENO")
	private String chequeNo;
	@Column(name = "BANKACCOUNTNO")
	private String bankaccountno;
	@Enumerated(value = EnumType.STRING)
	private PaymentChannel paymentChannel;
	@Temporal(TemporalType.DATE)
	private Date sanctionDate;
	private String sanctionNo;
	private String receiptNo;
	@Column(name = "ENTRY_TYPE")
	@Enumerated(EnumType.STRING)
	private AgentCommissionEntryType entryType;
	private double premium;
	private double percentage;
	
	@Column(name = "BPMSRECEIPTNO")
	private String bpmsReceiptNo;


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANKID", referencedColumnName = "ID")
	private Bank bank;

	private double homeCommission;
	private double rate;
	private String CUR;
	private double homePremium;
	private double withHoldingTax;
	private double homeWithHoldingTax;

	@Version
	private int version;
	
	@Embedded
	private CommonCreateAndUpateMarks recorder;

	public AgentCommission() {
	}

	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, Agent agent, double commission, Date commissionStartDate) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = commissionStartDate;
		this.isPaid = false;
		this.status = false;
	}

	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, Agent agent, double commission, Date commissionStartDate, String receiptNo) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = commissionStartDate;
		this.isPaid = false;
		this.status = false;
		this.receiptNo = receiptNo;
	}

	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, Agent agent, double commission, Date commissionStartDate, String receiptNo, double premium,
			double percentage, AgentCommissionEntryType entryType, double rate, double homeCommission, String cur, double homePremium,String bpmsReceiptNo) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = commissionStartDate;
		this.isPaid = false;
		this.status = false;
		this.receiptNo = receiptNo;
		this.premium = premium;
		this.percentage = percentage;
		this.entryType = entryType;
		this.rate = rate;
		this.homeCommission = homeCommission;
		this.CUR = cur;
		this.homePremium = homePremium;
		this.bpmsReceiptNo=bpmsReceiptNo;
	}

	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, Agent agent, double commission) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.invoiceNo = referenceNo;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = new Date();
		this.invoiceDate = new Date();
		this.paymentDate = new Date();
		this.isPaid = true;
		this.status = true;
	}

	public AgentCommission(String referenceNo, PolicyReferenceType referenceType, Agent agent, double commission, Date commissionStartDate, PaymentChannel paymentChannel) {
		this.referenceNo = referenceNo;
		this.referenceType = referenceType;
		this.agent = agent;
		this.commission = commission;
		this.commissionStartDate = commissionStartDate;
		this.isPaid = false;
		this.status = false;
		this.paymentChannel = paymentChannel;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AGENTCOMMISSION_GEN")
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public Date getCommissionStartDate() {
		return commissionStartDate;
	}

	public void setCommissionStartDate(Date commissionStartDate) {
		this.commissionStartDate = commissionStartDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getBankaccountno() {
		return bankaccountno;
	}

	public void setBankaccountno(String bankaccountno) {
		this.bankaccountno = bankaccountno;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public AgentCommissionEntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(AgentCommissionEntryType entryType) {
		this.entryType = entryType;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getHomeCommission() {
		return homeCommission;
	}

	public void setHomeCommission(double homeCommission) {
		this.homeCommission = homeCommission;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getCUR() {
		return CUR;
	}

	public void setCUR(String cUR) {
		CUR = cUR;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getHomePremium() {
		return homePremium;
	}

	public void setHomePremium(double homePremium) {
		this.homePremium = homePremium;
	}

	public double getWithHoldingTax() {
		return withHoldingTax;
	}

	public void setWithHoldingTax(double withHoldingTax) {
		this.withHoldingTax = withHoldingTax;
	}

	public double getHomeWithHoldingTax() {
		return homeWithHoldingTax;
	}

	public void setHomeWithHoldingTax(double homeWithHoldingTax) {
		this.homeWithHoldingTax = homeWithHoldingTax;
	}
	

	public CommonCreateAndUpateMarks getRecorder() {
		return recorder;
	}

	public void setRecorder(CommonCreateAndUpateMarks recorder) {
		this.recorder = recorder;
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
		result = prime * result + ((CUR == null) ? 0 : CUR.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		result = prime * result + ((bankaccountno == null) ? 0 : bankaccountno.hashCode());
		result = prime * result + ((bpmsReceiptNo == null) ? 0 : bpmsReceiptNo.hashCode());
		result = prime * result + ((chequeNo == null) ? 0 : chequeNo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(commission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((commissionStartDate == null) ? 0 : commissionStartDate.hashCode());
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		temp = Double.doubleToLongBits(homeCommission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(homePremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(homeWithHoldingTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((invoiceNo == null) ? 0 : invoiceNo.hashCode());
		result = prime * result + ((isPaid == null) ? 0 : isPaid.hashCode());
		result = prime * result + ((paymentChannel == null) ? 0 : paymentChannel.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		temp = Double.doubleToLongBits(percentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		result = prime * result + ((sanctionDate == null) ? 0 : sanctionDate.hashCode());
		result = prime * result + ((sanctionNo == null) ? 0 : sanctionNo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + version;
		temp = Double.doubleToLongBits(withHoldingTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AgentCommission other = (AgentCommission) obj;
		if (CUR == null) {
			if (other.CUR != null)
				return false;
		} else if (!CUR.equals(other.CUR))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (bank == null) {
			if (other.bank != null)
				return false;
		} else if (!bank.equals(other.bank))
			return false;
		if (bankaccountno == null) {
			if (other.bankaccountno != null)
				return false;
		} else if (!bankaccountno.equals(other.bankaccountno))
			return false;
		if (bpmsReceiptNo == null) {
			if (other.bpmsReceiptNo != null)
				return false;
		} else if (!bpmsReceiptNo.equals(other.bpmsReceiptNo))
			return false;
		if (chequeNo == null) {
			if (other.chequeNo != null)
				return false;
		} else if (!chequeNo.equals(other.chequeNo))
			return false;
		if (Double.doubleToLongBits(commission) != Double.doubleToLongBits(other.commission))
			return false;
		if (commissionStartDate == null) {
			if (other.commissionStartDate != null)
				return false;
		} else if (!commissionStartDate.equals(other.commissionStartDate))
			return false;
		if (entryType != other.entryType)
			return false;
		if (Double.doubleToLongBits(homeCommission) != Double.doubleToLongBits(other.homeCommission))
			return false;
		if (Double.doubleToLongBits(homePremium) != Double.doubleToLongBits(other.homePremium))
			return false;
		if (Double.doubleToLongBits(homeWithHoldingTax) != Double.doubleToLongBits(other.homeWithHoldingTax))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNo == null) {
			if (other.invoiceNo != null)
				return false;
		} else if (!invoiceNo.equals(other.invoiceNo))
			return false;
		if (isPaid == null) {
			if (other.isPaid != null)
				return false;
		} else if (!isPaid.equals(other.isPaid))
			return false;
		if (paymentChannel != other.paymentChannel)
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (Double.doubleToLongBits(percentage) != Double.doubleToLongBits(other.percentage))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		} else if (!referenceNo.equals(other.referenceNo))
			return false;
		if (referenceType != other.referenceType)
			return false;
		if (sanctionDate == null) {
			if (other.sanctionDate != null)
				return false;
		} else if (!sanctionDate.equals(other.sanctionDate))
			return false;
		if (sanctionNo == null) {
			if (other.sanctionNo != null)
				return false;
		} else if (!sanctionNo.equals(other.sanctionNo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (version != other.version)
			return false;
		if (Double.doubleToLongBits(withHoldingTax) != Double.doubleToLongBits(other.withHoldingTax))
			return false;
		return true;
	}

	

}
