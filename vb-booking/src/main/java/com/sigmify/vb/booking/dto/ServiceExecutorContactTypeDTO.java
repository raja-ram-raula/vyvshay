package com.sigmify.vb.booking.dto;

import java.io.Serializable;





public class ServiceExecutorContactTypeDTO implements Serializable {
	
	/**
	 
	 */
	private static final long serialVersionUID = -5010234194104353171L;
	private Long id;
	private String name;
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
