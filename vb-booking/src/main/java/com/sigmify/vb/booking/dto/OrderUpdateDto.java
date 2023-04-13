package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.util.Date;

public class OrderUpdateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3032598413500043539L;
	
	private Date startTime;
	private Double fieldArea;
	private Long uom;
	private String address;
	private String landMark;
	private Long serviceId;
	private String userId;
	private Double unitPrice;
	private String longitude;
	private String lattitude;
	private String zipcode;
	private String mobileNumber;
	private String requesterName;
	private String orderNumber;

	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Double getFieldArea() {
		return fieldArea;
	}
	public void setFieldArea(Double fieldArea) {
		this.fieldArea = fieldArea;
	}
	public Long getUom() {
		return uom;
	}
	public void setUom(Long uom) {
		this.uom = uom;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	
	
	

}
