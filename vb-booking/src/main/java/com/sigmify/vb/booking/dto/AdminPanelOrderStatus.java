package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.util.Date;

public class AdminPanelOrderStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7683176022264721441L;
	
	private String phoneNo;
	private String OrderId;
	private Date orderDate;
	private String serviceOrdered;
	private Integer costOfService;
	private Boolean closerStatus;
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
	public Integer getCostOfService() {
		return costOfService;
	}
	public void setCostOfService(Integer costOfService) {
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
	
	


}
