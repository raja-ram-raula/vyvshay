package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ServiceStatusUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 832795377890723957L;
	private Long orderid;
	private Long serviceId;
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
