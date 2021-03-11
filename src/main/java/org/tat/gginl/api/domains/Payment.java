package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
import org.tat.gginl.api.common.IDataModel;
import org.tat.gginl.api.common.PaymentDTO;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.PolicyStatus;


@Entity
@Table(name = TableName.PAYMENT)
@TableGenerator(name = "PAYMENT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "PAYMENT_GEN", allocationSize = 10)
@NamedQueries(value = {@NamedQuery(name = "Payment.findAll", query = "SELECT m FROM Payment m "),
    @NamedQuery(name = "Payment.findById", query = "SELECT m FROM Payment m WHERE m.id = :id"),
    @NamedQuery(name = "Payment.findByReferenceNo",
        query = "SELECT m FROM Payment m WHERE m.referenceNo = :referenceNo"),
    @NamedQuery(name = "Payment.findByReferenceNoAndReferenceType",
        query = "SELECT m FROM Payment m WHERE m.referenceNo = :referenceNo and m.referenceType = :referenceType"),
    @NamedQuery(name = "Payment.findByReferenceNoAndReferenceTypeComplete",
        query = "SELECT m FROM Payment m WHERE m.complete = TRUE and m.referenceNo = :referenceNo and m.referenceType = :referenceType"),
    @NamedQuery(name = "Payment.findPaymentByReferenceNoAndMaxDate",
        query = "select p from Payment p where p.paymentDate=(select MIN(p1.paymentDate) from Payment p1 where  p1.referenceNo = :referenceNo) "),
    @NamedQuery(name = "Payment.findPaymentByReferenceNoAndTerm1",
        query = "SELECT m FROM Payment m WHERE m.referenceNo = :referenceNo AND m.toTerm='1'"),
    @NamedQuery(name = "Payment.reversePaymentByReceiptNo",
        query = "UPDATE Payment p SET p.reverse = true WHERE p.receiptNo = :receiptNo")})
@Access(value = AccessType.FIELD)
public class Payment implements Serializable, IDataModel {
  private static final long serialVersionUID = 6880993048581143599L;

  @Transient
  private String id;
  @Transient
  private String prefix;

  @Column(name = "RECEIPTNO")
  private String receiptNo;

  @Column(name = "BANKACCOUNTNO")
  private String bankAccountNo;

  @Column(name = "REFERENCENO")
  private String referenceNo;

  @Column(name = "CHEQUENO")
  private String chequeNo;

  @Column(name = "REFERENCETYPE")
  @Enumerated(value = EnumType.STRING)
  private PolicyReferenceType referenceType;

  @Column(name = "DISCOUNTPERCENTAGE")
  private double discountPercent;

  @Column(name = "RECEIVED_DENO")
  private String receivedDeno;

  @Column(name = "REFUND_DENO")
  private String refundDeno;

  @Column(name = "RATE")
  private double rate;

  @Column(name = "CUR")
  private String cur;

  @Column(name = "BASICPREMIUM")
  private double basicPremium;

  @Column(name = "HOMEPREMIUM")
  private double homePremium;

  @Column(name = "ADDONPREMIUM")
  private double addOnPremium;

  @Column(name = "HOMEADDONPREMIUM")
  private double homeAddOnPremium;

  @Column(name = "AMOUNT")
  private double amount;

  @Column(name = "HOMEAMOUNT")
  private double homeAmount;

  @Column(name = "CLAIMAMOUNT")
  private double claimAmount;

  @Column(name = "HOMECLAIMAMOUNT")
  private double homeClaimAmount;

  @Column(name = "ADMINISTRATIONFEES")
  private double administrationFees;

  @Column(name = "HOMEADMINISTRATIONFEES")
  private double homeAdministrationFees;

  @Column(name = "NCBPREMIUM")
  private double ncbPremium;

  @Column(name = "HOMENCBPREMIUM")
  private double homeNcbPremium;

  @Column(name = "PENALTYPREMIUM")
  private double penaltyPremium;

  @Column(name = "HOMEPENALTYPREMIUM")
  private double homePenaltyPremium;

  @Column(name = "SERVICECHARGES")
  private double servicesCharges;

  @Column(name = "HOMESERVICECHARGES")
  private double homeServicesCharges;

  @Column(name = "STAMPFEES")
  private double stampFees;

  @Column(name = "HOMESTAMPFEES")
  private double homeStampFees;

  @Column(name = "REINSTATEMENTPREMIUM")
  private double reinstatementPremium;

  @Column(name = "HOMEREINSTATEMENTPREMIUM")
  private double homeReinstatementPremium;

  private boolean complete;

  private boolean isOutstanding;

  private boolean isAlreadyGenerated;

  @Enumerated(value = EnumType.STRING)
  private PaymentChannel paymentChannel;

  /* Cheque Received Bank */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BANKID", referencedColumnName = "ID")
  private Bank bank;

  @Column(name = "CONFIRMDATE")
  @Temporal(TemporalType.DATE)
  private Date confirmDate;

  @Column(name = "PAYMENTDATE")
  @Temporal(TemporalType.DATE)
  private Date paymentDate;

  @Column(name = "ENDORSEMENTCONFIRMDATE")
  @Temporal(TemporalType.DATE)
  private Date endorsementConfirmDate;

  @Column(name = "ENDORSEMENTPAYMENTDATE")
  @Temporal(TemporalType.DATE)
  private Date endorsementPaymentDate;

  @Column(name = "POLICYSTATUS")
  @Enumerated(value = EnumType.STRING)
  private PolicyStatus policyStatus;

  @Column(name = "FROMTERM")
  private int fromTerm;

  @Column(name = "TOTERM")
  private int toTerm;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
  private PaymentType paymentType;

  @Column(name = "LOANINTEREST")
  private double loanInterest;

  @Column(name = "RENEWALINTEREST")
  private double renewalInterest;

  @Column(name = "REFUND")
  private double refund;

  @Column(name = "PONO")
  private String poNo;

  @Column(name = "ISPO")
  private boolean isPO;

  /* Account Clearing Bank */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ACCOUNTBANKID", referencedColumnName = "ID")
  private Bank accountBank;

  private boolean isReinstate;

  private boolean reverse;

  @Embedded
  private CommonCreateAndUpateMarks commonCreateAndUpateMarks;

  
  @Column(name = "BPMSRECEIPTNO")
  private String bpmsReceiptNo;

  @Version
  private int version;

  public Payment() {
    this.rate = 1;
    this.cur = "KYT";
  }

  public Payment(Payment payment) {
    this.id = payment.getId();
    this.receiptNo = payment.getReceiptNo();
    this.bankAccountNo = payment.getBankAccountNo();
    this.accountBank = payment.getAccountBank();
    this.referenceNo = payment.getReferenceNo();
    this.chequeNo = payment.getChequeNo();
    this.poNo = payment.getPoNo();
    this.referenceType = payment.getReferenceType();
    this.basicPremium = payment.getBasicPremium();
    this.addOnPremium = payment.getAddOnPremium();
    this.claimAmount = payment.getClaimAmount();
    this.discountPercent = payment.getDiscountPercent();
    this.servicesCharges = payment.getServicesCharges();
    this.stampFees = payment.getStampFees();
    this.complete = payment.isComplete();
    this.receivedDeno = payment.getReceivedDeno();
    this.refundDeno = payment.getRefundDeno();
    this.paymentChannel = payment.getPaymentChannel();
    this.bank = payment.getBank();
    this.confirmDate = payment.getConfirmDate();
    this.paymentDate = payment.getPaymentDate();
    this.rate = payment.getRate();
    this.cur = payment.getCur();
    this.isAlreadyGenerated = payment.isAlreadyGenerated();

  }

  public Payment(String id, String receiptNo, String bankAccountNo, String referenceNo,
      PolicyReferenceType referenceType, double basicPremium, double addOnPremium,
      double discountPercent, double servicesCharges, double stampFees, boolean complete,
      String receivedDeno, String refundDeno, PaymentChannel paymentChannel, Bank bank) {
    this.id = id;
    this.receiptNo = receiptNo;
    this.bankAccountNo = bankAccountNo;
    this.referenceNo = referenceNo;
    this.referenceType = referenceType;
    this.basicPremium = basicPremium;
    this.addOnPremium = addOnPremium;
    this.discountPercent = discountPercent;
    this.servicesCharges = servicesCharges;
    this.stampFees = stampFees;
    this.complete = complete;
    this.receivedDeno = receivedDeno;
    this.refundDeno = refundDeno;
    this.paymentChannel = paymentChannel;
    this.bank = bank;
  }

  public Payment(PaymentDTO tempPayment) {
    this.id = tempPayment.getId();
    this.receiptNo = tempPayment.getReceiptNo();
    this.bankAccountNo = tempPayment.getBankAccountNo();
    this.referenceNo = tempPayment.getReferenceNo();
    this.referenceType = tempPayment.getReferenceType();
    this.discountPercent = tempPayment.getDiscountPercent();
    this.servicesCharges = tempPayment.getServicesCharges();
    this.stampFees = tempPayment.getStampFees();
    this.receivedDeno = tempPayment.getReceivedDeno();
    this.refundDeno = tempPayment.getRefundDeno();
    this.claimAmount = tempPayment.getClaimAmount();
    this.complete = tempPayment.isComplete();
    this.paymentChannel = tempPayment.getPaymentChannel();
    this.chequeNo = tempPayment.getChequeNo();
    this.bank = tempPayment.getBank();
    this.reinstatementPremium = tempPayment.getReinstatementPremium();
    this.administrationFees = tempPayment.getAdministrationFees();
    this.ncbPremium = tempPayment.getNcbPremium();
    this.penaltyPremium = tempPayment.getPenaltyPremium();
    double basicPremium = 0.0;
    double addOnPremium = 0.0;
    this.confirmDate = tempPayment.getConfirmDate();
    this.paymentDate = tempPayment.getPaymentDate();
    basicPremium = basicPremium + tempPayment.getBasicPremium();
    addOnPremium = addOnPremium + tempPayment.getAddOnPremium();
    this.basicPremium = basicPremium;
    this.addOnPremium = addOnPremium;
    this.fromTerm = tempPayment.getFromTerm();
    this.toTerm = tempPayment.getToTerm();
    this.paymentType = tempPayment.getPaymentType();
    this.loanInterest = tempPayment.getLoanInterest();
    this.renewalInterest = tempPayment.getRenewalInterest();
    this.refund = tempPayment.getRefund();
    this.poNo = tempPayment.getPoNo();
    this.accountBank = tempPayment.getAccountBank();
    this.isOutstanding = tempPayment.isOutstanding();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PAYMENT_GEN")
  @Access(value = AccessType.PROPERTY)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    if (id != null) {
      this.id = FormatID.formatId(id, getPrefix(), 10);
    }
  }

  public boolean isOutstanding() {
    return isOutstanding;
  }

  public void setOutstanding(boolean isOutstanding) {
    this.isOutstanding = isOutstanding;
  }

  public String getBpmsReceiptNo() {
	return bpmsReceiptNo;
  }

  public void setBpmsReceiptNo(String bpmsReceiptNo) {
	this.bpmsReceiptNo = bpmsReceiptNo;
	}

public CommonCreateAndUpateMarks getCommonCreateAndUpateMarks() {
    return commonCreateAndUpateMarks;
  }

  public void setCommonCreateAndUpateMarks(CommonCreateAndUpateMarks commonCreateAndUpateMarks) {
    this.commonCreateAndUpateMarks = commonCreateAndUpateMarks;
  }

  public boolean isAlreadyGenerated() {
    return isAlreadyGenerated;
  }

  public void setAlreadyGenerated(boolean isAlreadyGenerated) {
    this.isAlreadyGenerated = isAlreadyGenerated;
  }

  public double getHomeClaimAmount() {
    return homeClaimAmount;
  }

  public void setHomeClaimAmount(double homeClaimAmount) {
    this.homeClaimAmount = homeClaimAmount;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getReceiptNo() {
    return receiptNo;
  }

  public void setReceiptNo(String receiptNo) {
    this.receiptNo = receiptNo;
  }

  public String getChequeNo() {
    return chequeNo;
  }

  public void setChequeNo(String chequeNo) {
    this.chequeNo = chequeNo;
  }

  public String getBankAccountNo() {
    return bankAccountNo;
  }

  public void setBankAccountNo(String bankAccountNo) {
    this.bankAccountNo = bankAccountNo;
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

  public double getBasicPremium() {
    return basicPremium;
  }

  public void setBasicPremium(double basicPremium) {
    this.basicPremium = basicPremium;
  }

  public double getAddOnPremium() {
    return addOnPremium;
  }

  public void setAddOnPremium(double addOnPremium) {
    this.addOnPremium = addOnPremium;
  }

  public double getDiscountPercent() {
    return discountPercent;
  }

  public void setDiscountPercent(double discountPercent) {
    this.discountPercent = discountPercent;
  }

  public double getServicesCharges() {
    return servicesCharges;
  }

  public void setServicesCharges(double servicesCharges) {
    this.servicesCharges = servicesCharges;
  }

  public double getStampFees() {
    return stampFees;
  }

  public void setStampFees(double stampFees) {
    this.stampFees = stampFees;
  }

  public boolean isComplete() {
    return complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  public String getReceivedDeno() {
    return receivedDeno;
  }

  public void setReceivedDeno(String receivedDeno) {
    this.receivedDeno = receivedDeno;
  }

  public String getRefundDeno() {
    return refundDeno;
  }

  public void setRefundDeno(String refundDeno) {
    this.refundDeno = refundDeno;
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

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public double getAdministrationFees() {
    return administrationFees;
  }

  public void setAdministrationFees(double administrationFees) {
    this.administrationFees = administrationFees;
  }

  public double getClaimAmount() {
    return claimAmount;
  }

  public void setClaimAmount(double claimAmount) {
    this.claimAmount = claimAmount;
  }

  public double getNetClaimAmount() {
    return claimAmount - servicesCharges;
  }

  public PolicyStatus getPolicyStatus() {
    return policyStatus;
  }

  public void setPolicyStatus(PolicyStatus policyStatus) {
    this.policyStatus = policyStatus;
  }

  public double getReinstatementPremium() {
    return reinstatementPremium;
  }

  public void setReinstatementPremium(double reinstatementPremium) {
    this.reinstatementPremium = reinstatementPremium;
  }

  public Date getConfirmDate() {
    return confirmDate;
  }

  public void setConfirmDate(Date confirmDate) {
    this.confirmDate = confirmDate;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public Date getEndorsementConfirmDate() {
    return endorsementConfirmDate;
  }

  public void setEndorsementConfirmDate(Date endorsementConfirmDate) {
    this.endorsementConfirmDate = endorsementConfirmDate;
  }

  public Date getEndorsementPaymentDate() {
    return endorsementPaymentDate;
  }

  public void setEndorsementPaymentDate(Date endorsementPaymentDate) {
    this.endorsementPaymentDate = endorsementPaymentDate;
  }

  public int getFromTerm() {
    return fromTerm;
  }

  public void setFromTerm(int fromTerm) {
    this.fromTerm = fromTerm;
  }

  public int getToTerm() {
    return toTerm;
  }

  public void setToTerm(int toTerm) {
    this.toTerm = toTerm;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public double getLoanInterest() {
    return loanInterest;
  }

  public void setLoanInterest(double loanInterest) {
    this.loanInterest = loanInterest;
  }

  public double getRenewalInterest() {
    return renewalInterest;
  }

  public void setRenewalInterest(double renewalInterest) {
    this.renewalInterest = renewalInterest;
  }

  public double getRefund() {
    return refund;
  }

  public void setRefund(double refund) {
    this.refund = refund;
  }

  public double getNcbPremium() {
    return ncbPremium;
  }

  public void setNcbPremium(double ncbPremium) {
    this.ncbPremium = ncbPremium;
  }

  public double getPenaltyPremium() {
    return penaltyPremium;
  }

  public void setPenaltyPremium(double penaltyPremium) {
    this.penaltyPremium = penaltyPremium;
  }

  public String getPoNo() {
    return poNo;
  }

  public void setPoNo(String poNo) {
    this.poNo = poNo;
  }

  public boolean isPO() {
    return isPO;
  }

  public void setPO(boolean isPO) {
    this.isPO = isPO;
  }

  public Bank getAccountBank() {
    return accountBank;
  }

  public void setAccountBank(Bank accountBank) {
    this.accountBank = accountBank;
  }

  public boolean getIsReinstate() {
    return isReinstate;
  }

  public void setIsReinstate(boolean isReinstate) {
    this.isReinstate = isReinstate;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getHomePremium() {
    return homePremium;
  }

  public void setHomePremium(double homePremium) {
    this.homePremium = homePremium;
  }

  public double getHomeAddOnPremium() {
    return homeAddOnPremium;
  }

  public void setHomeAddOnPremium(double homeAddOnPremium) {
    this.homeAddOnPremium = homeAddOnPremium;
  }

  public String getCur() {
    return cur;
  }

  public void setCur(String cur) {
    this.cur = cur;
  }

  public double getHomeServicesCharges() {
    return homeServicesCharges;
  }

  public void setHomeServicesCharges(double homeServicesCharges) {
    this.homeServicesCharges = homeServicesCharges;
  }

  public double getHomeStampFees() {
    return homeStampFees;
  }

  public void setHomeStampFees(double homeStampFees) {
    this.homeStampFees = homeStampFees;
  }

  public double getHomeAdministrationFees() {
    return homeAdministrationFees;
  }

  public void setHomeAdministrationFees(double homeAdministrationFees) {
    this.homeAdministrationFees = homeAdministrationFees;
  }

  public double getHomeNcbPremium() {
    return homeNcbPremium;
  }

  public void setHomeNcbPremium(double homeNcbPremium) {
    this.homeNcbPremium = homeNcbPremium;
  }

  public double getHomePenaltyPremium() {
    return homePenaltyPremium;
  }

  public void setHomePenaltyPremium(double homePenaltyPremium) {
    this.homePenaltyPremium = homePenaltyPremium;
  }

  public double getHomeAmount() {
    return homeAmount;
  }

  public void setHomeAmount(double homeAmount) {
    this.homeAmount = homeAmount;
  }

  public double getHomeReinstatementPremium() {
    return homeReinstatementPremium;
  }

  public void setHomeReinstatementPremium(double homeReinstatementPremium) {
    this.homeReinstatementPremium = homeReinstatementPremium;
  }

  /*******************************************
   * generated getter
   *******************************************/

  public double getDiscountAmount() {
    double discountAmount = 0.0;
    if (discountPercent > 0.0) {
      if (referenceType == PolicyReferenceType.MEDICAL_POLICY
          || referenceType == PolicyReferenceType.MEDICAL_BILL_COLLECTION) {
        discountAmount = (basicPremium + addOnPremium) * discountPercent / 100;
      } else
        discountAmount = basicPremium * discountPercent / 100;
    }
    return discountAmount;
  }

  public double getRenewalTotalPremium() {
    return (basicPremium - ncbPremium) + addOnPremium + penaltyPremium;
  }

  public double getRenewalNetPremium() {
    return getRenewalTotalPremium() - getDiscountAmount();
  }

  public double getTotalPremium() {
    return basicPremium + addOnPremium + penaltyPremium;
  }

  public double getNetPremium() {
    return getTotalPremium() - ncbPremium - getDiscountAmount() + getReinstatementPremium()
        + loanInterest - refund + renewalInterest;
  }

  public double getTotalAmount() {
    return getNetPremium() + servicesCharges + stampFees;
  }

  public double getTotalClaimAmount() {
    return getClaimAmount() - (servicesCharges + reinstatementPremium);
  }

  public boolean isReverse() {
    return reverse;
  }

  public void setReverse(boolean reverse) {
    this.reverse = reverse;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((accountBank == null) ? 0 : accountBank.hashCode());
	long temp;
	temp = Double.doubleToLongBits(addOnPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(administrationFees);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(amount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((bank == null) ? 0 : bank.hashCode());
	result = prime * result + ((bankAccountNo == null) ? 0 : bankAccountNo.hashCode());
	temp = Double.doubleToLongBits(basicPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((bpmsReceiptNo == null) ? 0 : bpmsReceiptNo.hashCode());
	result = prime * result + ((chequeNo == null) ? 0 : chequeNo.hashCode());
	temp = Double.doubleToLongBits(claimAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((commonCreateAndUpateMarks == null) ? 0 : commonCreateAndUpateMarks.hashCode());
	result = prime * result + (complete ? 1231 : 1237);
	result = prime * result + ((confirmDate == null) ? 0 : confirmDate.hashCode());
	result = prime * result + ((cur == null) ? 0 : cur.hashCode());
	temp = Double.doubleToLongBits(discountPercent);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((endorsementConfirmDate == null) ? 0 : endorsementConfirmDate.hashCode());
	result = prime * result + ((endorsementPaymentDate == null) ? 0 : endorsementPaymentDate.hashCode());
	result = prime * result + fromTerm;
	temp = Double.doubleToLongBits(homeAddOnPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeAdministrationFees);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeClaimAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeNcbPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homePenaltyPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homePremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeReinstatementPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeServicesCharges);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(homeStampFees);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + (isAlreadyGenerated ? 1231 : 1237);
	result = prime * result + (isOutstanding ? 1231 : 1237);
	result = prime * result + (isPO ? 1231 : 1237);
	result = prime * result + (isReinstate ? 1231 : 1237);
	temp = Double.doubleToLongBits(loanInterest);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(ncbPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((paymentChannel == null) ? 0 : paymentChannel.hashCode());
	result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
	result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
	temp = Double.doubleToLongBits(penaltyPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((poNo == null) ? 0 : poNo.hashCode());
	result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
	result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
	temp = Double.doubleToLongBits(rate);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
	result = prime * result + ((receivedDeno == null) ? 0 : receivedDeno.hashCode());
	result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
	result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
	temp = Double.doubleToLongBits(refund);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((refundDeno == null) ? 0 : refundDeno.hashCode());
	temp = Double.doubleToLongBits(reinstatementPremium);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(renewalInterest);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + (reverse ? 1231 : 1237);
	temp = Double.doubleToLongBits(servicesCharges);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(stampFees);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + toTerm;
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
	Payment other = (Payment) obj;
	if (accountBank == null) {
		if (other.accountBank != null)
			return false;
	} else if (!accountBank.equals(other.accountBank))
		return false;
	if (Double.doubleToLongBits(addOnPremium) != Double.doubleToLongBits(other.addOnPremium))
		return false;
	if (Double.doubleToLongBits(administrationFees) != Double.doubleToLongBits(other.administrationFees))
		return false;
	if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
		return false;
	if (bank == null) {
		if (other.bank != null)
			return false;
	} else if (!bank.equals(other.bank))
		return false;
	if (bankAccountNo == null) {
		if (other.bankAccountNo != null)
			return false;
	} else if (!bankAccountNo.equals(other.bankAccountNo))
		return false;
	if (Double.doubleToLongBits(basicPremium) != Double.doubleToLongBits(other.basicPremium))
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
	if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))
		return false;
	if (commonCreateAndUpateMarks == null) {
		if (other.commonCreateAndUpateMarks != null)
			return false;
	} else if (!commonCreateAndUpateMarks.equals(other.commonCreateAndUpateMarks))
		return false;
	if (complete != other.complete)
		return false;
	if (confirmDate == null) {
		if (other.confirmDate != null)
			return false;
	} else if (!confirmDate.equals(other.confirmDate))
		return false;
	if (cur == null) {
		if (other.cur != null)
			return false;
	} else if (!cur.equals(other.cur))
		return false;
	if (Double.doubleToLongBits(discountPercent) != Double.doubleToLongBits(other.discountPercent))
		return false;
	if (endorsementConfirmDate == null) {
		if (other.endorsementConfirmDate != null)
			return false;
	} else if (!endorsementConfirmDate.equals(other.endorsementConfirmDate))
		return false;
	if (endorsementPaymentDate == null) {
		if (other.endorsementPaymentDate != null)
			return false;
	} else if (!endorsementPaymentDate.equals(other.endorsementPaymentDate))
		return false;
	if (fromTerm != other.fromTerm)
		return false;
	if (Double.doubleToLongBits(homeAddOnPremium) != Double.doubleToLongBits(other.homeAddOnPremium))
		return false;
	if (Double.doubleToLongBits(homeAdministrationFees) != Double.doubleToLongBits(other.homeAdministrationFees))
		return false;
	if (Double.doubleToLongBits(homeAmount) != Double.doubleToLongBits(other.homeAmount))
		return false;
	if (Double.doubleToLongBits(homeClaimAmount) != Double.doubleToLongBits(other.homeClaimAmount))
		return false;
	if (Double.doubleToLongBits(homeNcbPremium) != Double.doubleToLongBits(other.homeNcbPremium))
		return false;
	if (Double.doubleToLongBits(homePenaltyPremium) != Double.doubleToLongBits(other.homePenaltyPremium))
		return false;
	if (Double.doubleToLongBits(homePremium) != Double.doubleToLongBits(other.homePremium))
		return false;
	if (Double.doubleToLongBits(homeReinstatementPremium) != Double.doubleToLongBits(other.homeReinstatementPremium))
		return false;
	if (Double.doubleToLongBits(homeServicesCharges) != Double.doubleToLongBits(other.homeServicesCharges))
		return false;
	if (Double.doubleToLongBits(homeStampFees) != Double.doubleToLongBits(other.homeStampFees))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (isAlreadyGenerated != other.isAlreadyGenerated)
		return false;
	if (isOutstanding != other.isOutstanding)
		return false;
	if (isPO != other.isPO)
		return false;
	if (isReinstate != other.isReinstate)
		return false;
	if (Double.doubleToLongBits(loanInterest) != Double.doubleToLongBits(other.loanInterest))
		return false;
	if (Double.doubleToLongBits(ncbPremium) != Double.doubleToLongBits(other.ncbPremium))
		return false;
	if (paymentChannel != other.paymentChannel)
		return false;
	if (paymentDate == null) {
		if (other.paymentDate != null)
			return false;
	} else if (!paymentDate.equals(other.paymentDate))
		return false;
	if (paymentType == null) {
		if (other.paymentType != null)
			return false;
	} else if (!paymentType.equals(other.paymentType))
		return false;
	if (Double.doubleToLongBits(penaltyPremium) != Double.doubleToLongBits(other.penaltyPremium))
		return false;
	if (poNo == null) {
		if (other.poNo != null)
			return false;
	} else if (!poNo.equals(other.poNo))
		return false;
	if (policyStatus != other.policyStatus)
		return false;
	if (prefix == null) {
		if (other.prefix != null)
			return false;
	} else if (!prefix.equals(other.prefix))
		return false;
	if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
		return false;
	if (receiptNo == null) {
		if (other.receiptNo != null)
			return false;
	} else if (!receiptNo.equals(other.receiptNo))
		return false;
	if (receivedDeno == null) {
		if (other.receivedDeno != null)
			return false;
	} else if (!receivedDeno.equals(other.receivedDeno))
		return false;
	if (referenceNo == null) {
		if (other.referenceNo != null)
			return false;
	} else if (!referenceNo.equals(other.referenceNo))
		return false;
	if (referenceType != other.referenceType)
		return false;
	if (Double.doubleToLongBits(refund) != Double.doubleToLongBits(other.refund))
		return false;
	if (refundDeno == null) {
		if (other.refundDeno != null)
			return false;
	} else if (!refundDeno.equals(other.refundDeno))
		return false;
	if (Double.doubleToLongBits(reinstatementPremium) != Double.doubleToLongBits(other.reinstatementPremium))
		return false;
	if (Double.doubleToLongBits(renewalInterest) != Double.doubleToLongBits(other.renewalInterest))
		return false;
	if (reverse != other.reverse)
		return false;
	if (Double.doubleToLongBits(servicesCharges) != Double.doubleToLongBits(other.servicesCharges))
		return false;
	if (Double.doubleToLongBits(stampFees) != Double.doubleToLongBits(other.stampFees))
		return false;
	if (toTerm != other.toTerm)
		return false;
	if (version != other.version)
		return false;
	return true;
}


}
