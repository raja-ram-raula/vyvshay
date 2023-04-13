package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class UnitMeasurementTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8430531440320676162L;
	
	private Long id;
	private String name;
	private String description;
	private Double indicativePrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Double getIndicativePrice() {
		return indicativePrice;
	}
	public void setIndicativePrice(Double indicativePrice) {
		this.indicativePrice = indicativePrice;
	}
}
