package org.tat.gginl.api.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.TableName;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.ProposalType;
import lombok.Data;

@Entity
@Data
@Table(name = TableName.GROUPFARMERPROPOSAL)
@TableGenerator(name = "GROUPFARMERPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "GROUPFARMERPROPOSAL_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class GroupFarmerProposal implements Serializable {

  private static final long serialVersionUID = 1L;

  @Transient
  private String id;

  @Transient
  private String prefix;

  private String proposalNo;

  @Temporal(TemporalType.DATE)
  private Date submittedDate;

  @Enumerated(EnumType.STRING)
  private ProposalType proposalType;

  @Temporal(TemporalType.DATE)
  private Date endDate;

  private int noOfInsuredPerson;

  private double totalSI;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
  private Branch branch;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
  private PaymentType paymentType;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "AGENTID", referencedColumnName = "ID")
  private Agent agent;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "SALEMANID", referencedColumnName = "ID")
  private SaleMan saleMan;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REFERRALID", referencedColumnName = "ID")
  private Customer referral;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
  private Organization organization;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "groupFarmerProposal",
      orphanRemoval = true)
  private List<GroupFarmerProposalAttachment> attachmentList;

  private double premium;

  private boolean isPaymentComplete;

  private boolean isProcessComplete;

  @Transient
  private boolean isShowIssue;

  @OneToOne
  @JoinColumn(name = "SALEPOINTID")
  private SalePoint salePoint;

  @Embedded
  private CommonCreateAndUpateMarks commonCreateAndUpateMarks;


  @Transient
  private String bpmsInsuredPersonId;
  
  
  private String bpmsProposalNo;


  @Version
  private int version;

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "GROUPFARMERPROPOSAL_GEN")
  @Access(value = AccessType.PROPERTY)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    if (id != null) {
      this.id = FormatID.formatId(id, getPrefix(), 10);
    }
  }

  @Transient
  public double getAgentCommission() {
    double totalCommission = 0.0;
    if (agent != null) {
      double commissionPercent = 10;
      if (commissionPercent > 0) {
        double totalPremium = premium;
        double commission = Utils.getPercentOf(commissionPercent, totalPremium);
        totalCommission = totalCommission + commission;
      }
    }

    return Utils.getTwoDecimalPoint(totalCommission);
  }

}
