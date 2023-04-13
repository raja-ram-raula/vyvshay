package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class PaymentInvoiceDTO implements Serializable {
	
	private Long id;
    private String invoiceNo;
    private String orderId;
    private String serviceOwnerId;
    private Double dueAmount;
    private String dueAmountStatus;
    private String invoiceDate;
    private String dueDate;
    private String orderDate;
    private String amountReceivedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getServiceOwnerId() {
		return serviceOwnerId;
	}
	public void setServiceOwnerId(String serviceOwnerId) {
		this.serviceOwnerId = serviceOwnerId;
	}
	public Double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public String getDueAmountStatus() {
		return dueAmountStatus;
	}
	public void setDueAmountStatus(String dueAmountStatus) {
		this.dueAmountStatus = dueAmountStatus;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getAmountReceivedDate() {
		return amountReceivedDate;
	}
	public void setAmountReceivedDate(String amountReceivedDate) {
		this.amountReceivedDate = amountReceivedDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
    
}
