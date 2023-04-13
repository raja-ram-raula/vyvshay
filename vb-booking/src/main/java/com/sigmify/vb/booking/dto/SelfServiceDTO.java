package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.util.List;

public class SelfServiceDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1894471140079646321L;
	private long parentServiceID;
	private String parentServiceName;
	private boolean isParentServiceActive;
	private List<ServiceSubCategoryDto> serviceSubCategoryDto;
	public long getParentServiceID() {
		return parentServiceID;
	}
	public void setParentServiceID(long parentServiceID) {
		this.parentServiceID = parentServiceID;
	}
	public String getParentServiceName() {
		return parentServiceName;
	}
	public void setParentServiceName(String parentServiceName) {
		this.parentServiceName = parentServiceName;
	}
	public boolean isParentServiceActive() {
		return isParentServiceActive;
	}
	public void setParentServiceActive(boolean isParentServiceActive) {
		this.isParentServiceActive = isParentServiceActive;
	}
	public List<ServiceSubCategoryDto> getServiceSubCategoryDto() {
		return serviceSubCategoryDto;
	}
	public void setServiceSubCategoryDto(List<ServiceSubCategoryDto> serviceSubCategoryDto) {
		this.serviceSubCategoryDto = serviceSubCategoryDto;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
