package com.sigmify.vb.booking.dto;

import java.util.List;

public class ServiceSubCategoryDto {
	
	private Long subCategoryId;
	private String subCategoryName;
	private String subCategoryDesc;
	public List<ServiceDTo> serviceDetails;
	
	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long integer) {
		this.subCategoryId = integer;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}
	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}
	public List<ServiceDTo> getServiceDetails() {
		return serviceDetails;
	}
	public void setServiceDetails(List<ServiceDTo> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}
		
	}

