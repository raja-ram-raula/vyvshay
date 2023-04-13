package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(value = Include.NON_NULL)
public class ServiceExecutorListDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2641451171960352551L;
	
	private Long id;
	private String serviceName;
	private String serviceDesc;
	private String serviceTypePhoto;
	private String executorName;
	private String executorPhone;
	private String uan;
	private Integer offSeasonPrice;
	private Integer onSeasonPrice;
	private Long unitOfMeasurement;
	private String vehicleNumber;
	private String idproofPhoto;
	private String userType;
	//setters and getters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getExecutorName() {
		return executorName;
	}
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	public String getExecutorPhone() {
		return executorPhone;
	}
	public void setExecutorPhone(String executorPhone) {
		this.executorPhone = executorPhone;
	}
	public String getUan() {
		return uan;
	}
	public void setUan(String uan) {
		this.uan = uan;
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
	public Long getUnitOfMeasurement() {
		return unitOfMeasurement;
	}
	public void setUnitOfMeasurement(Long unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getServiceTypePhoto() {
		return serviceTypePhoto;
	}
	public void setServiceTypePhoto(String serviceTypePhoto) {
		this.serviceTypePhoto = serviceTypePhoto;
	}
	public String getIdproofPhoto() {
		return idproofPhoto;
	}
	public void setIdproofPhoto(String idproofPhoto) {
		this.idproofPhoto = idproofPhoto;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	

}
