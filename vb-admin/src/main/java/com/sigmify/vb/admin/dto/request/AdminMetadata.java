package com.sigmify.vb.admin.dto.request;

import java.io.Serializable;

public class AdminMetadata implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4553748102188487984L;
	
	private Integer id;
	private String name;
    private String description;
    
    //setters and getters
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
    

	
	

}
