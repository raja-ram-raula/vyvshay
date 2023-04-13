package com.sigmify.vb.admin.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
/**
 * @date 13th Jan 2020
 * @author Sukanta
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
    @CreatedBy
    @Column(name = "created_by")
    private U createdBy;

    @CreatedDate
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private U updatedBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private  Date updatedAt;

	public U getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public U getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedBy(U updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

   
}