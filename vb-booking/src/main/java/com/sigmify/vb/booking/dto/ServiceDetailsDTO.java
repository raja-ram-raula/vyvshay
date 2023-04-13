package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ServiceDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680662545352789736L;
	
	private Long id;
	private String serviceType;
	private String serviceOwnerName;
	private String serviceExecutorName;
	private String uanNo;
	private Integer offSeasonPrice;
	private Integer onSeasonPrice;
	private Long unitMeasurementType;
	private String lattitude;
	private String longitude;
	//private String availability;
	private String userType;
	private String phoneNo;
	private String userName;
	private String idproofPhoto;
	private String zipCode;
	private String vehicleNumber;
	//private String deviceToken;
	
//	public String getDeviceToken() {
//		return deviceToken;
//	}
//	public void setDeviceToken(String deviceToken) {
//		this.deviceToken = deviceToken;
//	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	public Integer getOffSeasonPrice() {
		return offSeasonPrice;
	}
	public void setOffSeasonPrice(Integer offSeasonPrice) {
		this.offSeasonPrice = offSeasonPrice;
	}
	public Integer getOnSeasonPrice() {
		return onSeasonPrice;
	}
	public void setOnSeasonPrice(Integer onSeasonPrice) {
		this.onSeasonPrice = onSeasonPrice;
	}
	public Long getUnitMeasurementType() {
		return unitMeasurementType;
	}
	public void setUnitMeasurementType(Long unitMeasurementType) {
		this.unitMeasurementType = unitMeasurementType;
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
//	public String getAvailability() {
//		return availability;
//	}
//	public void setAvailability(String availability) {
//		this.availability = availability;
//	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Long getId() {
		return id;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdproofPhoto() {
		return idproofPhoto;
	}
	public void setIdproofPhoto(String idproofPhoto) {
		this.idproofPhoto = idproofPhoto;
	}
	
	
	

}
