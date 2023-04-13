package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(value = Include.NON_NULL)
public class UserContactDetailsDTO implements Serializable {

	/**
	 * 
	 */
	  private static final long serialVersionUID = 7717385888390913486L;
	  
	  private Long id;
	  private Long userId;
	  private String eMail;
	  private String phoneNo;
	  private String userType;
	  private LocalDateTime createDate;
	  private LocalDateTime lastUpdateDate;
	  private Long lastUpdateBy;
	  private String fbToken;
	  private String deviceId;
	  
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
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
	
	public String getFbToken() {
		return fbToken;
	}
	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	  
}
