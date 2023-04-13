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
@Table (schema = "booking" , name = "unit_of_measurement_type")
public class UnitMeasurementType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155224046838453595L;
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "unit_of_measurement_type_id_seq")
	  @SequenceGenerator(schema = "booking", name = "unit_of_measurement_type_id_seq",
	      sequenceName = "unit_of_measurement_type_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="name",length = 50,nullable = false)
	private String name;
	
	@Column(name="description",length = 80,nullable = false)
	private String description;
	
	@Column(name="indicative_price",length = 80,nullable = false)
	private Double indicativePrice;
	
	@Column(name = "active")
	private boolean isActive;
	
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Double getIndicativePrice() {
		return indicativePrice;
	}
	public void setIndicativePrice(Double indicativePrice) {
		this.indicativePrice = indicativePrice;
	}

}
