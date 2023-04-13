package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;





@Entity
@Table(schema="booking",name ="service_executor_contact_type")
public class ServiceExecutorContactType implements Serializable {
	
	/**
	 
	 */
	private static final long serialVersionUID = -5010234194104353171L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "service_executor_contact_type_id_seq")
	@SequenceGenerator(schema = "booking", name = "service_executor_contact_type_id_seq",
	      sequenceName = "service_executor_contact_type_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="name",nullable = false,length = 50)
	private String name;
	
	@Column(name="description",nullable = false,length = 80)
	private String description;
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	
}
