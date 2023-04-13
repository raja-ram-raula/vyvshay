package com.sigmify.vb.booking.dto;

import java.io.Serializable;


public class PriceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 159617161062176533L;

	private Long id;
	private Double price;
	private String geoLocation;
	private boolean isActive;
	private Long measurementUnitId;
	private Long serviceTypeID;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getZipLocation() {
		return geoLocation;
	}
	public void setZipLocation(String zipLocation) {
		this.geoLocation = zipLocation;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Long getMeasurementUnitId() {
		return measurementUnitId;
	}
	public void setMeasurementUnitId(Long measurementUnitId) {
		this.measurementUnitId = measurementUnitId;
	}
	public Long getServiceTypeID() {
		return serviceTypeID;
	}
	public void setServiceTypeID(Long serviceTypeID) {
		this.serviceTypeID = serviceTypeID;
	}
}
