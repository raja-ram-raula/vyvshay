package com.sigmify.vb.booking.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "booking",name = "price" )
public class Price implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7982228641612213317L;
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "booking_price_id_seq")
	  @SequenceGenerator(schema = "booking", name = "booking_price_id_seq",
	      sequenceName = "booking_price_id_seq", allocationSize = 1)
	private Long id;
	private Double price;
	@Column(name = "geo_location")
	private String geoLocation;
	@Column(name = "active")
	private boolean isActive;
	@Column(name = "unit_of_measurement_type_id ")
	private Long measurementUnitId;
	@Column(name = "service_type_id")
	private Long serviceTypeID;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
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
