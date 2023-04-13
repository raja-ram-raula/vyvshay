package com.sigmify.vb.booking.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author jagannath computers9
 *
 */
@Entity
@Table (schema = "booking" , name ="order_details")
public class OrderDetails extends Auditable<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680662545352789736L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_details_id_seq")
	@SequenceGenerator(schema = "booking" , name = "order_details_id_seq", sequenceName = "order_details_id_seq", allocationSize =1 )
	private Long id;
	@ManyToOne(targetEntity =Order.class,cascade = CascadeType.ALL,fetch= FetchType.EAGER)
	@JoinColumn(name="order_id",referencedColumnName = "id") 
	private Order order;
	@ManyToOne(targetEntity =ServiceDetails.class,cascade = CascadeType.ALL,fetch= FetchType.EAGER)
	@JoinColumn(name="service_details_id",referencedColumnName = "id")
	private	ServiceDetails serviceDetailsId;
	@Column(name = "order_start_time")
	private Date orderExecutionTime;
	@Column(name = "order_status")
	private String orderStatus;
	@Column(name =  "order_payment_status")
	private String paymentStatus;
	@Column(name =  "service_id")
	private Long serviceID;
	@Column(name =  "is_active",columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
	private Boolean isActive;
	@Column(name =  "orderNumber")
	private String orderNumber;
	@Column(name =  "service_executor_userId")
	private String serviceExecutorUserId;
	@Column(name =  "service_executor_name")
	private String serviceExecutorName;
	@Column(name= "service_executor_lattitude")
	private String serviceExLattitude;
	@Column(name= "service_executor_longitude")
	private String serviceExLongitude;
	@Column(name= "zip_code")
	private String zipCode;
		
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public ServiceDetails getServiceDetailsId() {
		return serviceDetailsId;
	}
	public void setServiceDetailsId(ServiceDetails serviceDetailsId) {
		this.serviceDetailsId = serviceDetailsId;
	}
	public Date getOrderExecutionTime() {
		return orderExecutionTime;
	}
	public void setOrderExecutionTime(Date orderExecutionTime) {
		this.orderExecutionTime = orderExecutionTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getServiceID() {
		return serviceID;
	}
	public void setServiceID(Long serviceID) {
		this.serviceID = serviceID;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrdeNumber(String ordeNumber) {
		this.orderNumber = ordeNumber;
	}
	public String getServiceExecutorUserId() {
		return serviceExecutorUserId;
	}
	public void setServiceExecutorUserId(String serviceExecutorUserId) {
		this.serviceExecutorUserId = serviceExecutorUserId;
	}
	public String getServiceExLattitude() {
		return serviceExLattitude;
	}
	public void setServiceExLattitude(String serviceExLattitude) {
		this.serviceExLattitude = serviceExLattitude;
	}
	public String getServiceExLongitude() {
		return serviceExLongitude;
	}
	public void setServiceExLongitude(String serviceExLongitude) {
		this.serviceExLongitude = serviceExLongitude;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getServiceExecutorName() {
		return serviceExecutorName;
	}
	public void setServiceExecutorName(String serviceExecutorName) {
		this.serviceExecutorName = serviceExecutorName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
