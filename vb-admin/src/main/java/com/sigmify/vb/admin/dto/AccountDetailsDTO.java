/**
 * 
 */
package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccountDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9057181824290111346L;
	  private Long id;
	  private String accountNumber;
	  private String ifscCode;
	  private Long userId;
	  private String accountHolderName;
	  private Integer termsAndConditionsId;
	  private boolean isTermsConditonAgreed;
	  private Long createdBy;
	  private LocalDateTime creationDate;
	  private LocalDateTime lastUpdateDate;
	  private Long lastUpdateBy;
	//--gettersetter method
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public Integer getTermsAndConditionsId() {
		return termsAndConditionsId;
	}
	public void setTermsAndConditionsId(Integer termsAndConditionsId) {
		this.termsAndConditionsId = termsAndConditionsId;
	}
	public boolean isTermsConditonAgreed() {
		return isTermsConditonAgreed;
	}
	public void setTermsConditonAgreed(boolean isTermsConditonAgreed) {
		this.isTermsConditonAgreed = isTermsConditonAgreed;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
