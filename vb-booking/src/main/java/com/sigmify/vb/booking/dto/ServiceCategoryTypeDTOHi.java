package com.sigmify.vb.booking.dto;

import java.io.Serializable;

public class ServiceCategoryTypeDTOHi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852169864438397467L;
    private Long categoryId;
    private String categoryname;
    private String descriptionHi;
    private String imagePath;
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDescriptionHi() {
		return descriptionHi;
	}
	public void setDescriptionHi(String descriptionHi) {
		this.descriptionHi = descriptionHi;
	}
}
