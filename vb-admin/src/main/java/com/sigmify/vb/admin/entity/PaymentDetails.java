package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table (schema = "booking" , name ="payment")
@JsonInclude(value = Include.NON_NULL)
public class PaymentDetails extends Auditable<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680662545352789736L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "payment_id_seq")
	@SequenceGenerator(schema = "booking" , name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize =1 )
	private Long id;
	@Column(name =  "payment_reference_id")
	private String paymentReferenceId;
	@Column(name="order_id") 
	private String order;
	@Column(name =  "payment_status")
	private String paymentStatus;
	@Column(name =  "total_amount")
	private Double totalAmount;
	@Column(name =  "advance_amount")
	private Double advanceAmount;
	@Column(name =  "balance_amount")
	private Double balanceAmount;
	@Column(name =  "is_completed",nullable = false)
	private Boolean isCompleted;
	@Column(name =  "due_amount")
	private Double dueAmount;
	@Column(name= "due_payment_status")
	private String duePaymentStatus;
	@Column(name= "payment_accept_reject")
	private String paymentAcceptReject;
	
	
	public String getDuePaymentStatus() {
		return duePaymentStatus;
	}
	public void setDuePaymentStatus(String duePaymentStatus) {
		this.duePaymentStatus = duePaymentStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPaymentReferenceId() {
		return paymentReferenceId;
	}
	public void setPaymentReferenceId(String paymentReferenceId) {
		this.paymentReferenceId = paymentReferenceId;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
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
	public Boolean getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public Double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public String getPaymentAcceptReject() {
		return paymentAcceptReject;
	}
	public void setPaymentAcceptReject(String paymentAcceptReject) {
		this.paymentAcceptReject = paymentAcceptReject;
	}
	
}
