package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import com.sigmify.vb.admin.entity.User;
import com.sigmify.vb.admin.entity.metadata.ProofType;

public class UserIdProofDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4689645908960601239L;
	private Long id;
	  
	  private Long userId;
	  private String uan;
	  private String imagePath;
      private ProofTypeDTO proofType;
	  private Long createdBy;
	  private LocalDateTime creationDate;
	  private LocalDateTime lastUpdateDate;
	  private Long lastUpdateBy;
	  
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

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ProofTypeDTO getProofType() {
		return proofType;
	}

	public void setProofType(ProofTypeDTO proofType) {
		this.proofType = proofType;
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
