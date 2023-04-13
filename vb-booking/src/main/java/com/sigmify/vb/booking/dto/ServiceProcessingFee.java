package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class ServiceProcessingFee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 32031260594055627L;
	
	private String serviceOwnerName;
	private String orderId;
	private Boolean amountPaid;
	private Double dueAmount;
	
	
	public String getServiceOwnerName() {
		return serviceOwnerName;
	}
	public void setServiceOwnerName(String serviceOwnerName) {
		this.serviceOwnerName = serviceOwnerName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	public Boolean getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Boolean amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
