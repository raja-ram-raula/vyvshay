package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "admin",name="user_contact_details")
public class UserContactDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -652859095263750353L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_seq")
	  @SequenceGenerator(schema = "admin", name = "user_contact_id_seq",
	      sequenceName = "user_contact_id_seq", allocationSize = 1)
	  private Long id;
	  
	  @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	  @JoinColumn(name = "user_id",referencedColumnName = "id")
	  private User user;

	  @Column(name="email",length = 100)
	  private String eMail;
	  
	  @Column(name="phone",length = 12)
	  private String phoneNo;
	  
	  @Column(name="creation_date",nullable = false)
	  private LocalDateTime createDate;
	  
	  @Column(name="last_update_date",nullable = false)
	  private LocalDateTime lastUpdateDate;
	  
	  @Column(name="fb_token",unique = true)
	  private String fbToken;
	  
	  @Column(name = "device_id",unique = true,length = 100)
	  private String deviceId;
	  
	  @Column(name = "user_type", length = 100)
	  private String userType;
	 
	@OneToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	  @JoinColumn(name = "last_update_by",referencedColumnName = "id")
	  private User lastUpdateBy;
	//-----getter&setter method
	  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public User getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(User lastUpdateBy) {
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

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	//------to string method
	@Override
	public String toString() {
		return "UserContactDetails [id=" + id + ", user=" + user + ", eMail=" + eMail + ", phoneNo=" + phoneNo
				+ ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate + ", fbToken=" + fbToken
				+ ", deviceId=" + deviceId + ", userType=" + userType + ", lastUpdateBy=" + lastUpdateBy + "]";
	}
	
	
	

	
		
	
	
}
