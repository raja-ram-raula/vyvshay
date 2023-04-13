package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class PaymentRequest implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5948973893545470725L;
	
	private String orderId;
	private String paymentStatus;
	private Double totalAmount;
	private Double advanceAmount;
	private Double balanceAmount;
	private String userId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(Double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
