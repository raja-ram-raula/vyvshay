package com.sigmify.vb.admin.dto;

import java.io.Serializable;

public class AdminPanelDashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632275223280114064L;
	
	private Integer totalUsersRegister;
	private Integer totalServiceOwnerRegister;
	private Integer totalServiceRequestRaised;
	private Integer totalServiceRequestExecuted;
	
	
	public Integer getTotalUsersRegister() {
		return totalUsersRegister;
	}
	public void setTotalUsersRegister(Integer totalUsersRegister) {
		this.totalUsersRegister = totalUsersRegister;
	}
	public Integer getTotalServiceOwnerRegister() {
		return totalServiceOwnerRegister;
	}
	public void setTotalServiceOwnerRegister(Integer totalServiceOwnerRegister) {
		this.totalServiceOwnerRegister = totalServiceOwnerRegister;
	}
	public Integer getTotalServiceRequestRaised() {
		return totalServiceRequestRaised;
	}
	public void setTotalServiceRequestRaised(Integer totalServiceRequestRaised) {
		this.totalServiceRequestRaised = totalServiceRequestRaised;
	}
	public Integer getTotalServiceRequestExecuted() {
		return totalServiceRequestExecuted;
	}
	public void setTotalServiceRequestExecuted(Integer totalServiceRequestExecuted) {
		this.totalServiceRequestExecuted = totalServiceRequestExecuted;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
