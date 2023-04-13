package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.sigmify.vb.admin.entity.Referral;

public class ReferralCodeContactDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8991902689281843630L;
    private Long id;
	private String mobileNumber;
	private String mailId;
	private boolean isMessageSend;
	private boolean isMailSend;
	private boolean isAppInstalled; 
	private LocalDateTime messageSentTime;
	private LocalDateTime lastUpdateDate;
	private Long lastUpdateBy;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
