package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class OrderResponseDTO implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2152221609287924864L;
	
	private String orderId;
	private String orderStatus;
	private String message;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
