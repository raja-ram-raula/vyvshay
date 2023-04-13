package com.sigmify.vb.booking.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;





@Entity
@Table(schema="booking",name ="service_category_type")
//@Where(clause = "active='true'")
public class ServiceCategoryType implements Serializable {
	
	/**
	 
	 */
	private static final long serialVersionUID = -5010234194104353171L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "service_category_type_id_seq")
	@SequenceGenerator(schema = "booking", name = "service_category_type_id_seq",
	      sequenceName = "service_category_type_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="name",nullable = false,length = 50)
	private String name;
	
	@Column(name="lang",length = 200)
	private String language;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(name="description",nullable = false,length = 80)
	private String description;
	
	@Column(name = "active")
	private boolean isActive=true;
	
	@OneToMany(targetEntity = ServiceSubCategoryType.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "id")
	private List<ServiceSubCategoryType> listSubCatType;
	
	@Column(name="image_path",length = 255)
	private String imagePath;
	
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
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
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public List<ServiceSubCategoryType> getListSubCatType() {
		return listSubCatType;
	}
	public void setListSubCatType(List<ServiceSubCategoryType> listSubCatType) {
		this.listSubCatType = listSubCatType;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
