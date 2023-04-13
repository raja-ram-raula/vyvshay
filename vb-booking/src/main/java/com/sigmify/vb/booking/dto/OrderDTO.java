package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class OrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3032598413500043539L;
	
	private String orderId;
	private String orderStatus;
	private Long serviceId;
	private String serviceSubCatName;
	private String farmerName;
	private String farmerMobileNumber;
	private String startDate;
	private String startTime;
	private String serviceName;
	private String serviceDesc;
	private String imagePath;
	private String serviceOwnerName;
	private String serviceExecutorName;
	private String uanNo;
	private String offSeasonPrice;
	private String onSeasonPrice;
	private Double totalAmount;
	private String uom;
	private Double fieldArea;
	private String paymentStatus;
	private String lattitude;
	private String longitude;
	private String address;
	private String landMark;
	private String executorMobileNumber;
	private Integer indicativeRate;
	private String servExLattitude;
	private String servExLongitude;
	private String pinCode;
	private Boolean isRunning;
	
	
	
	
	public Boolean getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}
	public String getServExLattitude() {
		return servExLattitude;
	}
	public void setServExLattitude(String servExLattitude) {
		this.servExLattitude = servExLattitude;
	}
	public String getServExLongitude() {
		return servExLongitude;
	}
	public void setServExLongitude(String servExLongitude) {
		this.servExLongitude = servExLongitude;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceOwnerName() {
		return serviceOwnerName;
	}
	public void setServiceOwnerName(String serviceOwnerName) {
		this.serviceOwnerName = serviceOwnerName;
	}
	public String getServiceExecutorName() {
		return serviceExecutorName;
	}
	public void setServiceExecutorName(String serviceExecutorName) {
		this.serviceExecutorName = serviceExecutorName;
	}
	public String getUanNo() {
		return uanNo;
	}
	public void setUanNo(String uanNo) {
		this.uanNo = uanNo;
	}
	public String getOffSeasonPrice() {
		return offSeasonPrice;
	}
	public void setOffSeasonPrice(String offSeasonPrice) {
		this.offSeasonPrice = offSeasonPrice;
	}
	public String getOnSeasonPrice() {
		return onSeasonPrice;
	}
	public void setOnSeasonPrice(String onSeasonPrice) {
		this.onSeasonPrice = onSeasonPrice;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getFieldArea() {
		return fieldArea;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public void setFieldArea(Double fieldArea) {
		this.fieldArea = fieldArea;
	}
	public String getExecutorMobileNumber() {
		return executorMobileNumber;
	}
	public void setExecutorMobileNumber(String executorMobileNumber) {
		this.executorMobileNumber = executorMobileNumber;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getServiceSubCatName() {
		return serviceSubCatName;
	}
	public void setServiceSubCatName(String serviceSubCatName) {
		this.serviceSubCatName = serviceSubCatName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public String getFarmerMobileNumber() {
		return farmerMobileNumber;
	}
	public void setFarmerMobileNumber(String farmerMobileNumber) {
		this.farmerMobileNumber = farmerMobileNumber;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Integer getIndicativeRate() {
		return indicativeRate;
	}
	public void setIndicativeRate(Integer indicativeRate) {
		this.indicativeRate = indicativeRate;
	}	
}
