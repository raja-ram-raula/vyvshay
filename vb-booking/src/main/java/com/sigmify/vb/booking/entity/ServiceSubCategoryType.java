package com.sigmify.vb.booking.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(schema = "booking" , name = "service_sub_category_type")
public class ServiceSubCategoryType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7427237309784323891L;
	
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "service_sub_category_type_id_seq")
	  @SequenceGenerator(schema = "booking", name = "service_sub_category_type_id_seq",
	      sequenceName = "service_sub_category_type_id_seq", allocationSize = 1)
	 private Long id;
	 
	 @Column(name="name",nullable = false,length = 50)
	 private String name;
		
	 @Column(name="description",nullable = false,length = 80)
	 private String description;
	
	 @Column(name="lang",length = 200)
	 private String language;
		
	 @Column(name = "active")
	 private boolean isActive=true;
	 
	 @ManyToOne(targetEntity =ServiceCategoryType.class,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	 @JoinColumn(name = "service_category_type_id",referencedColumnName = "id")
	 private ServiceCategoryType serviceCategoryType;
	 
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
	 public String getDescription() {
		return description;
	 }
	 public void setDescription(String description) {
		this.description = description;
	 }
	 public boolean isActive() {
		return isActive;
	 }
	 public void setActive(boolean isActive) {
		this.isActive = isActive;
	 }
	public ServiceCategoryType getServiceCategoryType() {
		return serviceCategoryType;
	}
	public void setServiceCategoryType(ServiceCategoryType serviceCategoryType) {
		this.serviceCategoryType = serviceCategoryType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
