package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class MyServiceDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1389709671586581255L;
	
	private String serviceCategory;
	private String serviceSubCategory;
	private String serviceTypeImage;
	private String vehicleNumber;
	private String serviceName;
	private String createdDate;
	private String createdTime;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getServiceSubCategory() {
		return serviceSubCategory;
	}
	public void setServiceSubCategory(String serviceSubCategory) {
		this.serviceSubCategory = serviceSubCategory;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getServiceTypeImage() {
		return serviceTypeImage;
	}
	public void setServiceTypeImage(String serviceTypeImage) {
		this.serviceTypeImage = serviceTypeImage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
