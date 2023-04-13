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
@Table (schema = "booking" , name = "order_status_type")
public class OrderStatusType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1155224046838453595L;
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_status_type_id_seq")
	  @SequenceGenerator(schema = "booking", name = "order_status_type_id_seq",
	      sequenceName = "order_status_type_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="name",length = 50,nullable = false)
	private String name;
	
	@Column(name="description",length = 80,nullable = false)
	private String description;	
	
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

}
