package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class OrderDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680662545352789736L;
	private Long orderID;
	private String orderName;
	private LocalDateTime orderPlacesTimeStamp;
	private LocalDateTime orderExecutionTimeStamp;
	private String orderStatus;
	private String serviceProviderName;
	private String serviceExecuterName;
	private Double serviceIndicativeRate;
	private String geoLocation;
	private String paymentStatus;
	private Long serviceID;
	private String serviceExLattitude;
	private String serviceExLongitude;
	
	public Long getOrderID() {
		return orderID;
	}
	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public LocalDateTime getOrderPlacesTimeStamp() {
		return orderPlacesTimeStamp;
	}
	public void setOrderPlacesTimeStamp(LocalDateTime orderPlacesTimeStamp) {
		this.orderPlacesTimeStamp = orderPlacesTimeStamp;
	}
	public LocalDateTime getOrderExecutionTimeStamp() {
		return orderExecutionTimeStamp;
	}
	public void setOrderExecutionTimeStamp(LocalDateTime orderExecutionTimeStamp) {
		this.orderExecutionTimeStamp = orderExecutionTimeStamp;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
	public String getServiceExecuterName() {
		return serviceExecuterName;
	}
	public void setServiceExecuterName(String serviceExecuterName) {
		this.serviceExecuterName = serviceExecuterName;
	}
	public Double getServiceIndicativeRate() {
		return serviceIndicativeRate;
	}
	public void setServiceIndicativeRate(Double serviceIndicativeRate) {
		this.serviceIndicativeRate = serviceIndicativeRate;
	}
	public String getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getServiceID() {
		return serviceID;
	}
	public void setServiceID(Long serviceID) {
		this.serviceID = serviceID;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
