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
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.FormatID;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.PermanentAddress;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.ViberContent;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdConditionType;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.MaritalStatus;
import org.tat.gginl.api.common.emumdata.ProductGroupType;
import lombok.Data;

@Entity
@Data
@TableGenerator(name = "AGENT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "AGENT_GEN", allocationSize = 10)
@Access(value = AccessType.FIELD)
public class Agent implements Serializable {
  private static final long serialVersionUID = 1L;

  @Transient
  private String id;
  @Transient
  private String prefix;
  private String codeNo;
  private String liscenseNo;
  private String initialId;
  private String idNo;
  private String fatherName;
  private String birthMark;
  private String accountNo;
  private String remark;
  private String training;
  private String outstandingEvent;

  @Embedded
  private Name name;

  @Enumerated(value = EnumType.STRING)
  private Gender gender;

  @Enumerated(value = EnumType.STRING)
  private MaritalStatus maritalStatus;

  @Temporal(TemporalType.DATE)
  private Date appointedDate;

  @Access(AccessType.FIELD)
  @ElementCollection
  @CollectionTable(name = "AGENT_FAMILY_LINK",
      joinColumns = @JoinColumn(referencedColumnName = "ID", name = "AGENTID", updatable = false,
          insertable = false))
  private List<FamilyInfo> familyInfo;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "QUALIFICATIONID", referencedColumnName = "ID")
  private Qualification qualification;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RELIGIONID", referencedColumnName = "ID")
  private Religion religion;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "NATIONALITYID", referencedColumnName = "ID", insertable = false,
      updatable = false)
  private Country country;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BANKBRANCHID", referencedColumnName = "ID", insertable = false,
      updatable = false)
  private BankBranch bankBranch;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID", insertable = false,
      updatable = false)
  private Organization organization;

  @Embedded
  private PermanentAddress permanentAddress;


  @Embedded
  private ResidentAddress residentAddress;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BRANCHID", referencedColumnName = "ID", updatable = false, insertable = false)
  private Branch branch;


  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agent",
      orphanRemoval = true)
  private AgentAttachment attachment;

  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  @Enumerated(value = EnumType.STRING)
  private IdType idType;

  @Column(name = "GROUPTYPE")
  @Enumerated(value = EnumType.STRING)
  private ProductGroupType groupType;

  @AttributeOverrides({@AttributeOverride(name = "phone", column = @Column(name = "VoicePhoneNo")),
      @AttributeOverride(name = "mobile", column = @Column(name = "SMSPhoneNo"))})
  @Embedded
  private ContentInfo contentInfo;



  @Version
  private int version;

  @Transient
  private StateCode stateCode;

  @Transient
  private TownshipCode townshipCode;

  @Transient
  private IdConditionType idConditionType;

  @Transient
  private String fullIdNo;



  @AttributeOverrides({
      @AttributeOverride(name = "viberPhone", column = @Column(name = "ViberPhoneNo"))})
  @Embedded
  private ViberContent viberContent;


  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SALEPOINTID", referencedColumnName = "ID")
  private SalePoint salePoint;


  public Agent() {}

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "AGENT_GEN")
  @Access(value = AccessType.PROPERTY)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    if (id != null) {
      this.id = FormatID.formatId(id, getPrefix(), 10);
    }
  }

  public String getCodeNo() {
    return this.codeNo;
  }

  public void setCodeNo(String codeNo) {
    this.codeNo = codeNo;
  }

  public String getLiscenseNo() {
    return this.liscenseNo;
  }

  public void setLiscenseNo(String liscenseNo) {
    this.liscenseNo = liscenseNo;
  }

  public ContentInfo getContentInfo() {
    if (this.contentInfo == null) {
      this.contentInfo = new ContentInfo();
    }
    return this.contentInfo;
  }

  public void setContentInfo(ContentInfo contentInfo) {
    this.contentInfo = contentInfo;
  }


  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getInitialId() {
    return initialId;
  }

  public void setInitialId(String initialId) {
    this.initialId = initialId;
  }

  public Name getName() {
    return name;
  }

  public void setName(Name name) {
    this.name = name;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public IdType getIdType() {
    return idType;
  }

  public void setIdType(IdType idType) {
    this.idType = idType;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  @Transient
  public String getFullName() {
    return initialId + name.getFullName();
  }

  // @Transient
  // public String getFullAddress() {
  // String result = "";
  // if (residentAddress.getResidentAddress() != null &&
  // !residentAddress.getResidentAddress().isEmpty()) {
  // result = result + residentAddress.getResidentAddress();
  // }
  // if (residentAddress.getTownship() != null &&
  // !residentAddress.getTownship().getFullTownShip().isEmpty()) {
  // result = result + ", " + residentAddress.getTownship().getFullTownShip();
  // }
  // return result;
  // }

  public String getFatherName() {
    return fatherName;
  }

  public void setFatherName(String fatherName) {
    this.fatherName = fatherName;
  }

  public String getBirthMark() {
    return birthMark;
  }

  public void setBirthMark(String birthMark) {
    this.birthMark = birthMark;
  }

  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getTraining() {
    return training;
  }

  public void setTraining(String training) {
    this.training = training;
  }

  public String getOutstandingEvent() {
    return outstandingEvent;
  }

  public void setOutstandingEvent(String outstandingEvent) {
    this.outstandingEvent = outstandingEvent;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public MaritalStatus getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(MaritalStatus maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public Date getAppointedDate() {
    return appointedDate;
  }

  public void setAppointedDate(Date appointedDate) {
    this.appointedDate = appointedDate;
  }

  public Qualification getQualification() {
    return qualification;
  }

  public void setQualification(Qualification qualification) {
    this.qualification = qualification;
  }


  public Religion getReligion() {
    return religion;
  }

  public void setReligion(Religion religion) {
    this.religion = religion;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public ProductGroupType getGroupType() {
    return groupType;
  }

  public void setGroupType(ProductGroupType groupType) {
    this.groupType = groupType;
  }

  public AgentAttachment getAttachment() {
    return attachment;
  }

  public void setAttachment(AgentAttachment attachment) {
    this.attachment = attachment;
    attachment.setAgent(this);
  }

  // public PermanentAddress getPermanentAddress() {
  // if (this.permanentAddress == null) {
  // this.permanentAddress = new PermanentAddress();
  // }
  // return permanentAddress;
  // }


  // public ResidentAddress getResidentAddress() {
  // if (this.residentAddress == null) {
  // this.residentAddress = new ResidentAddress();
  // }
  // return this.residentAddress;
  // }


  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }


  public StateCode getStateCode() {
    return stateCode;
  }

  public void setStateCode(StateCode stateCode) {
    this.stateCode = stateCode;
  }

  public TownshipCode getTownshipCode() {
    return townshipCode;
  }

  public void setTownshipCode(TownshipCode townshipCode) {
    this.townshipCode = townshipCode;
  }

  public IdConditionType getIdConditionType() {
    return idConditionType;
  }

  public void setIdConditionType(IdConditionType idConditionType) {
    this.idConditionType = idConditionType;
  }

  public String getFullIdNo() {
    return fullIdNo;
  }

  public void setFullIdNo(String fullIdNo) {
    this.fullIdNo = fullIdNo;
  }



}
