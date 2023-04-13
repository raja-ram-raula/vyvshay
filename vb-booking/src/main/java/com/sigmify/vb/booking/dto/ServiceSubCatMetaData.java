package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class ServiceSubCatMetaData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7498100497083984893L;
	
	private Long id;
	private String name;
	private String description;
	private String categoryDescription;
	
	
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
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
