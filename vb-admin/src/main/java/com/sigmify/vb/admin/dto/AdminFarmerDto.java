package com.sigmify.vb.admin.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AdminFarmerDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5473085100047982740L;
	
	private String name;
	private String address;
	private String email;
	private String phone;
	private boolean status;
	private String executorContactType;
	private String idProof;
	private String serviceAllocated;
	
	
	public String getExecutorContactType() {
		return executorContactType;
	}
	public void setExecutorContactType(String executorContactType) {
		this.executorContactType = executorContactType;
	}
	public String getIdProof() {
		return idProof;
	}
	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}
	public String getServiceAllocated() {
		return serviceAllocated;
	}
	public void setServiceAllocated(String serviceAllocated) {
		this.serviceAllocated = serviceAllocated;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	

}
