package com.sigmify.vb.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ServiceDTo{
	private String serviceName;
	private String serviceDesc;
	private Long serviceId;
	private String servicImagePath;
	private Long serviceExeCutorContType;
	private String serviceExeCutorContTypeName;
	private String serviceExeCutorContTypeDesc;
	private String userType;
	
	private Long serviceDetailsId;
	private String executorName;
	private String executorPhone;
	private String uan;
	private Integer offSeasonPrice;
	private Integer onSeasonPrice;
	private Long unitOfMeasurement;
	private String vehicleNumber;
	private String idproofPhoto;
	
	public Long getServiceDetailsId() {
		return serviceDetailsId;
	}
	public void setServiceDetailsId(Long ServiceDetailsId) {
		this.serviceDetailsId = ServiceDetailsId;
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
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getIdproofPhoto() {
		return idproofPhoto;
	}
	public void setIdproofPhoto(String idproofPhoto) {
		this.idproofPhoto = idproofPhoto;
	}
	public String getServiceName() {
		return serviceName;
	}
	public Long getServiceExeCutorContType() {
		return serviceExeCutorContType;
	}
	public void setServiceExeCutorContType(Long serviceExeCutorContType) {
		this.serviceExeCutorContType = serviceExeCutorContType;
	}
	public String getServiceExeCutorContTypeName() {
		return serviceExeCutorContTypeName;
	}
	public void setServiceExeCutorContTypeName(String serviceExeCutorContTypeName) {
		this.serviceExeCutorContTypeName = serviceExeCutorContTypeName;
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
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long integer) {
		this.serviceId = integer;
	}

	public String getServiceExeCutorContTypeDesc() {
		return serviceExeCutorContTypeDesc;
	}
	public void setServiceExeCutorContTypeDesc(String serviceExeCutorContTypeDesc) {
		this.serviceExeCutorContTypeDesc = serviceExeCutorContTypeDesc;
	}
	public String getServicImagePath() {
		return servicImagePath;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setServicImagePath(String servicImagePath) {
		this.servicImagePath = servicImagePath;
	}	
}