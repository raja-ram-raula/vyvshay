package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.util.Date;

public class BookingOrderDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680662545352789736L;
	private Long orderID;
	private String orderName;
	private Date orderPlacesTimeStamp;
	private Date orderExecutionTimeStamp;
	private String orderStatus;
	private String serviceProviderName;
	private String serviceExecuterName;
	private Double serviceIndicativeRate;
	private String geoLocation;
	private String paymentStatus;
	private Long serviceID;
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
	public Date getOrderPlacesTimeStamp() {
		return orderPlacesTimeStamp;
	}
	public void setOrderPlacesTimeStamp(Date orderPlacesTimeStamp) {
		this.orderPlacesTimeStamp = orderPlacesTimeStamp;
	}
	public Date getOrderExecutionTimeStamp() {
		return orderExecutionTimeStamp;
	}
	public void setOrderExecutionTimeStamp(Date orderExecutionTimeStamp) {
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

}
