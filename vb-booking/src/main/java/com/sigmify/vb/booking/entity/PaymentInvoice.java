package com.sigmify.vb.booking.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table (schema = "booking" , name ="payment_invoice")
public class PaymentInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4912160215048805056L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "payment_invoice_id_seq")
	@SequenceGenerator(schema = "booking" , name = "payment_invoice_id_seq", sequenceName = "payment_invoice_id_seq", allocationSize =1 )
	private Long id;
	
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "so_id")
	private String serviceOwnerId;
	
	@Column(name = "due_amount")
	private Double dueAmount;
	
	@Column(name = "due_amount_status")
	private String dueAmountStatus;
	
	@Column(name = "invoice_date")
	private  String invoiceDate;
	
	@LastModifiedDate
	@Column(name = "order_execution_date")
	@Temporal(TemporalType.TIMESTAMP)
	private  Date orderExecutionDate;
	
	@LastModifiedDate
	@UpdateTimestamp
	@Column(name = "amount_received_date")
	@Temporal(TemporalType.TIMESTAMP)
	private  Date amountReceivedDate;

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

	public Date getOrderExecutionDate() {
		return orderExecutionDate;
	}

	public void setOrderExecutionDate(Date orderExecutionDate) {
		this.orderExecutionDate = orderExecutionDate;
	}

	public Date getAmountReceivedDate() {
		return amountReceivedDate;
	}

	public void setAmountReceivedDate(Date amountReceivedDate) {
		this.amountReceivedDate = amountReceivedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
