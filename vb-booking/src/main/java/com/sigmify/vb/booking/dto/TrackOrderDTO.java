package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class TrackOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;
	private String serviceExecuterName;
	private String serviceExecuterMobileNumber;
	private String serviceExLattitude;
	private String serviceExLongitude;
	private String farmerLattitude;
	private String farmerLongitude;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getServiceExecuterName() {
		return serviceExecuterName;
	}
	public void setServiceExecuterName(String serviceExecuterName) {
		this.serviceExecuterName = serviceExecuterName;
	}
	public String getServiceExecuterMobileNumber() {
		return serviceExecuterMobileNumber;
	}
	public void setServiceExecuterMobileNumber(String serviceExecuterMobileNumber) {
		this.serviceExecuterMobileNumber = serviceExecuterMobileNumber;
	}
	public String getServiceExLattitude() {
		return serviceExLattitude;
	}
	public void setServiceExLattitude(String serviceExLattitude) {
		this.serviceExLattitude = serviceExLattitude;
	}
	public String getServiceExLongitude() {
		return serviceExLongitude;
	}
	public void setServiceExLongitude(String serviceExLongitude) {
		this.serviceExLongitude = serviceExLongitude;
	}
	public String getFarmerLattitude() {
		return farmerLattitude;
	}
	public void setFarmerLattitude(String farmerLattitude) {
		this.farmerLattitude = farmerLattitude;
	}
	public String getFarmerLongitude() {
		return farmerLongitude;
	}
	public void setFarmerLongitude(String farmerLongitude) {
		this.farmerLongitude = farmerLongitude;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
