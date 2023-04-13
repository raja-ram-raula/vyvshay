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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(schema = "booking" , name = "order")
@JsonInclude(Include.NON_NULL)
public class Order extends Auditable<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 832795377890723957L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_id_seq")
	@SequenceGenerator(schema = "booking" , name = "order_id_seq", sequenceName = "order_id_seq", allocationSize =1 )
	private Long id;
	@Column(name = "order_id",length = 50,nullable = false)
	private String orderId;

	@Column(name="order_status",length = 80,nullable = false)
	private String orderStatus;
	
	@ManyToOne(targetEntity =ServiceType.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "service_id",referencedColumnName = "id")
	private ServiceType serviceId;
	
	
	//@OneToOne(targetEntity =ServiceDetails.class,cascade = CascadeType.ALL,mappedBy = "servide_details_id" , fetch=FetchType.EAGER)
	//@JoinColumn(name="servide_details_id",referencedColumnName = "id") 
	//private ServiceDetails serviceDetailsId;
	 
	
	@Column(name="amount",length = 80,nullable = false)
	private Double totalAmount;
	
	@ManyToOne(targetEntity =UnitMeasurementType.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="uom",referencedColumnName = "id")
	private UnitMeasurementType uom;
	
	@Column(name="field_area",length = 80,nullable = false)
	private Double fieldArea;
	
	@Column(name =  "order_payment_status")
	private String paymentStatus;
	
	@Column(name="lattitude")
	private String lattitude;
	
	@Column(name="longitude")
	private String longitude;
	
	@Column(name="zip_code")
	private String pinCode;
	
	@Column(name="address")
	private String address;
	
	@Column(name="landMark")
	private String landMark;
	
	@Column(name="requester_user_id")
	private String requester;
	
	@Column(name="requester_contact")
	private String requesterContact;
	
	@Column(name = "order_start_time")
	private Date orderExecutionTime;
	
	@Column(name="requester_name")
	private String requesterName;
	
	@Column(name="indicative_rate")
	private Integer indicativeRate;
	
	@Column(name="is_active",columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
	private Boolean isActive;
	
	@Column(name = "service_owner_name",nullable=true)
	private String serviceOwnerName;
	
	@Column(name = "service_executor_name",nullable = true)
	private String serviceExecutorName;
	
	@Column(name = "service_executor_mobile",nullable=true)
	private String serviceOwnerMobile;
	
	@Column(name = "is_running")
	private Boolean isRunning;
	
	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public ServiceType getServiceId() {
		return serviceId;
	}

	public void setServiceId(ServiceType serviceId) {
		this.serviceId = serviceId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public UnitMeasurementType getUom() {
		return uom;
	}

	public void setUom(UnitMeasurementType uom) {
		this.uom = uom;
	}

	public Double getFieldArea() {
		return fieldArea;
	}

	public void setFieldArea(Double fieldArea) {
		this.fieldArea = fieldArea;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getOrderExecutionTime() {
		return orderExecutionTime;
	}

	public void setOrderExecutionTime(Date orderExecutionTime) {
		this.orderExecutionTime = orderExecutionTime;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getRequesterContact() {
		return requesterContact;
	}

	public void setRequesterContact(String requesterContact) {
		this.requesterContact = requesterContact;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public Integer getIndicativeRate() {
		return indicativeRate;
	}

	public void setIndicativeRate(Integer indicativeRate) {
		this.indicativeRate = indicativeRate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getServiceOwnerName() {
		return serviceOwnerName;
	}

	public void setServiceOwnerName(String serviceOwnerName) {
		this.serviceOwnerName = serviceOwnerName;
	}

	public String getServiceExecutorName() {
		return serviceExecutorName;
	}

	public void setServiceExecutorName(String serviceExecutorName) {
		this.serviceExecutorName = serviceExecutorName;
	}
	
	
	public String getServiceOwnerMobile() {
		return serviceOwnerMobile;
	}

	public void setServiceOwnerMobile(String serviceOwnerMobile) {
		this.serviceOwnerMobile = serviceOwnerMobile;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderId=" + orderId + ", orderStatus=" + orderStatus + ", serviceId=" + serviceId
				+ ", totalAmount=" + totalAmount + ", uom=" + uom + ", fieldArea=" + fieldArea + ", paymentStatus="
				+ paymentStatus + ", lattitude=" + lattitude + ", longitude=" + longitude + ", pinCode=" + pinCode
				+ ", address=" + address + ", landMark=" + landMark + ", requester=" + requester + ", requesterContact="
				+ requesterContact + ", requesterName=" + requesterName + ", indicativeRate=" + indicativeRate
				+ ", isActive=" + isActive + ", serviceOwnerName=" + serviceOwnerName + ", serviceExecutorName="
				+ serviceExecutorName + ", serviceOwnerMobile=" + serviceOwnerMobile + ", isRunning=" + isRunning + "]";
	}
}
