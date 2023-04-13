package com.sigmify.vb.admin.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AdminPanelOrderStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7683176022264721441L;
	
	private String phoneNo;
	private String OrderId;
	private Date orderDate;
	private String serviceOrdered;
	private Double costOfService;
	private Boolean closerStatus;
	private Boolean orderDeclined;
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getServiceOrdered() {
		return serviceOrdered;
	}
	public void setServiceOrdered(String serviceOrdered) {
		this.serviceOrdered = serviceOrdered;
	}
	public Double getCostOfService() {
		return costOfService;
	}
	public void setCostOfService(Double costOfService) {
		this.costOfService = costOfService;
	}
	public Boolean getCloserStatus() {
		return closerStatus;
	}
	public void setCloserStatus(Boolean closerStatus) {
		this.closerStatus = closerStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Boolean getOrderDeclined() {
		return orderDeclined;
	}
	public void setOrderDeclined(Boolean orderDeclined) {
		this.orderDeclined = orderDeclined;
	}
	
	


}
