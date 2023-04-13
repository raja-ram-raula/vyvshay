package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(schema = "admin",name = "referral_code_contact")
@XmlRootElement
public class ReferralCodeContact implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 162429849507505694L;

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "referral_code_contact_id_seq")
    @SequenceGenerator(schema = "admin", name = "referral_code_contact_id_seq",
        sequenceName = "referral_code_contact_id_seq", allocationSize = 1)
    private Long id;
  
  @ManyToOne(targetEntity = Referral.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "referral_code",referencedColumnName = "referral_code")
  private Referral referralCode;
  
  @Column(name="mobile_number",length = 10,nullable = false)
  private String mobileNumber;
  
  @Column(name = "mail_id",length = 100)
  private String mailId;
  
  @Column(name = "is_message_send")
  private boolean isMessageSend;
  
  @Column(name = "is_mail_send")
  private boolean isMailSend;
  
  @Column(name = "is_app_installed ")
  private boolean isAppInstalled; 
  
  @Column(name = "message_sent_time")
  private LocalDateTime messageSentTime;
  
  @Column(name = "last_update_date")
  private LocalDateTime lastUpdateDate;
  
  @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "last_update_by",referencedColumnName = "id")
  private User lastUpdateBy;

  //----getterSetter method
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Referral getReferralCode() {
    return referralCode;
  }

  public void setReferralCode(Referral referralCode) {
    this.referralCode = referralCode;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getMailId() {
    return mailId;
  }

  public void setMailId(String mailId) {
    this.mailId = mailId;
  }

  public boolean isMessageSend() {
    return isMessageSend;
  }

  public void setMessageSend(boolean isMessageSend) {
    this.isMessageSend = isMessageSend;
  }

  public boolean isMailSend() {
    return isMailSend;
  }

  public void setMailSend(boolean isMailSend) {
    this.isMailSend = isMailSend;
  }

  public boolean isAppInstalled() {
    return isAppInstalled;
  }

  public void setAppInstalled(boolean isAppInstalled) {
    this.isAppInstalled = isAppInstalled;
  }

  public LocalDateTime getMessageSentTime() {
    return messageSentTime;
  }

  public void setMessageSentTime(LocalDateTime messageSentTime) {
    this.messageSentTime = messageSentTime;
  }

  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public User getLastUpdateBy() {
    return lastUpdateBy;
  }

  public void setLastUpdateBy(User lastUpdateBy) {
    this.lastUpdateBy = lastUpdateBy;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  //--toString method
  @Override
  public String toString() {
    return "ReferrralCodeContact [id=" + id + ", referralCode=" + referralCode + ", mobileNumber=" + mobileNumber
        + ", mailId=" + mailId + ", isMessageSend=" + isMessageSend + ", isMailSend=" + isMailSend
        + ", isAppInstalled=" + isAppInstalled + ", messageSentTime=" + messageSentTime + ", lastUpdateDate="
        + lastUpdateDate + ", lastUpdateBy=" + lastUpdateBy + "]";
  }
  
  
}
