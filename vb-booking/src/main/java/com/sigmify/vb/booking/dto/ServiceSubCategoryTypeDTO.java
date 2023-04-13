package com.sigmify.vb.booking.dto;

import java.io.Serializable;


public class ServiceSubCategoryTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1102413612981355398L;
	
    private Long id;
    private String name;
    private String description;
   // private boolean isActive;
	private Long serviceCategoryTypeID;
	private String serviceCategoryTypeDesc;
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

	/*
	 * public boolean isActive() { return isActive; } public void setActive(boolean
	 * isActive) { this.isActive = isActive; }
	 */
	public Long getServiceCategoryTypeID() {
		return serviceCategoryTypeID;
	}
	public void setServiceCategoryTypeID(Long serviceCategoryTypeID) {
		this.serviceCategoryTypeID = serviceCategoryTypeID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getServiceCategoryTypeDesc() {
		return serviceCategoryTypeDesc;
	}
	public void setServiceCategoryTypeDesc(String serviceCategoryTypeDesc) {
		this.serviceCategoryTypeDesc = serviceCategoryTypeDesc;
	}
	
}
