package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ServiceTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7957044363094948814L;

	 private Long id;
	 private String name;
	 private String description;
	 private Long serviceCategoryTypeID;
	 private Long serviceSubCategoryTypeId;
	 private Long serviceExecutorContactTypeId;
	 private String serviceExContactType;
	 
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
	public Long getServiceCategoryTypeID() {
		return serviceCategoryTypeID;
	}
	public void setServiceCategoryTypeID(Long serviceCategoryTypeID) {
		this.serviceCategoryTypeID = serviceCategoryTypeID;
	}
	public Long getServiceSubCategoryTypeId() {
		return serviceSubCategoryTypeId;
	}
	public void setServiceSubCategoryTypeId(Long serviceSubCategoryTypeId) {
		this.serviceSubCategoryTypeId = serviceSubCategoryTypeId;
	}
	public Long getServiceExecutorContactTypeId() {
		return serviceExecutorContactTypeId;
	}
	public void setServiceExecutorContactTypeId(Long serviceExecutorContactTypeId) {
		this.serviceExecutorContactTypeId = serviceExecutorContactTypeId;
	}
	public String getServiceExContactType() {
		return serviceExContactType;
	}
	public void setServiceExContactType(String serviceExContactType) {
		this.serviceExContactType = serviceExContactType;
	}
	
	

}
