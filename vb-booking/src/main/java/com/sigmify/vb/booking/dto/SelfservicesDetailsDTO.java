package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class SelfservicesDetailsDTO implements Serializable {

	/**
	 * 
	 */ 
	 
	private static final long serialVersionUID = 8890899054554452016L;
	private String categoryType;
	private String categoryImage;
	private String subCategory;
	private String serviceType;
	private String serviceTpeImage;
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryImage() {
		return categoryImage;
	}
	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceTpeImage() {
		return serviceTpeImage;
	}
	public void setServiceTpeImage(String serviceTpeImage) {
		this.serviceTpeImage = serviceTpeImage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
	