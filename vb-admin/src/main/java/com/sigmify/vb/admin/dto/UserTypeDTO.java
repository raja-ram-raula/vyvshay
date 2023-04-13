package com.sigmify.vb.admin.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class UserTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3305421937796557507L;
	
	private int id;
	private String name;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
