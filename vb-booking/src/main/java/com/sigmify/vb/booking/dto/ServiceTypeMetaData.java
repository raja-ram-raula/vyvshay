package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class ServiceTypeMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8456561179750136072L;
	
	private Long id;
	private String name;
	private String image;
	//private String description;
	private String categoryDesc;
	private String subCatDescription;
	private String serviceExeContDesc;
	private Double processingFee;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public Double getProcessingFee() {
		return processingFee;
	}
	public void setProcessingFee(Double processingFee) {
		this.processingFee = processingFee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/*public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}*/
	
	public String getSubCatDescription() {
		return subCatDescription;
	}
	
	public void setSubCatDescription(String subCatDescription) {
		this.subCatDescription = subCatDescription;
	}
	public String getServiceExeContDesc() {
		return serviceExeContDesc;
	}
	public void setServiceExeContDesc(String serviceExeContDesc) {
		this.serviceExeContDesc = serviceExeContDesc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
