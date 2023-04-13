package com.sigmify.vb.booking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "booking" , name = "service_status_user")
public class ServiceStatusUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 832795377890723957L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "service_status_user_id_seq")
	@SequenceGenerator(schema = "booking" , name = "service_status_user_id_seq", sequenceName = "service_status_user_id_seq", allocationSize =1 )
	private Long orderid;
	@Column(name = "serviceid")
	private Long serviceId;
	
	@Column(name="booking_date")
	private LocalDateTime bookingDate;

	public Long getId() {
		return orderid;
	}

	public void setId(Long id) {
		this.orderid = id;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
