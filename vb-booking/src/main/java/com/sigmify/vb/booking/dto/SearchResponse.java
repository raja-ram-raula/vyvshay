package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class SearchResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3032598413500043539L;
	
	private String availability;
	private Integer indicativePrice;
	private String serviceType;
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Integer getIndicativePrice() {
		return indicativePrice;
	}
	public void setIndicativePrice(Integer indicativePrice) {
		this.indicativePrice = indicativePrice;
	}
	

	
}
