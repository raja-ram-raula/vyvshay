package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class UpdateLatLongDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789764054197574738L;
	
	private String serviceExecutorUserName;
	private String lattitude;
	private String longitude;
	private String orderNumber;
	
	
	public String getServiceExecutorUserName() {
		return serviceExecutorUserName;
	}
	public void setServiceExecutorUserName(String serviceExecutorUserName) {
		this.serviceExecutorUserName = serviceExecutorUserName;
	}
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	

}
