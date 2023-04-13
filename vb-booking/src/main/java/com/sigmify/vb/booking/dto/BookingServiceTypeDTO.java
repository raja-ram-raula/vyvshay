package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class BookingServiceTypeDTO implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -7963112131680736274L;
	private Long id;
	 private String name;
	 private String description;
	 private boolean isActive;
	 private Long serviceSubCategoryTypeID;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Long getServiceSubCategoryTypeID() {
		return serviceSubCategoryTypeID;
	}
	public void setServiceSubCategoryTypeID(Long serviceSubCategoryTypeID) {
		this.serviceSubCategoryTypeID = serviceSubCategoryTypeID;
	}
}
