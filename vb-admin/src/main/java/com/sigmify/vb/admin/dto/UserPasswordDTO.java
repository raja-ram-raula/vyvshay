package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserPasswordDTO implements Serializable {

	/**
	 * 
	 */
	  private static final long serialVersionUID = -2519331372578914463L;
	  private Long id;  
	  private String password;
	  private Long userId;
	  private LocalDateTime creationDate;
	  private LocalDateTime lastUpdateDate;
	  private Long lastUpdateBy;
	//getterSetter method
	  public Long getId() {
		return id;
	  }
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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
