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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(schema = "booking" , name="service_type")
@JsonInclude(Include.NON_NULL)
public class ServiceType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7957044363094948814L;
	
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "service_type_id_seq")
	  @SequenceGenerator(schema = "booking", name = "service_type_id_seq",
	      sequenceName = "service_type_id_seq", allocationSize = 1)
	 private Long id;
	 
	 @Column(name="name",length = 50,nullable = false)
	 private String name;
	 
	 @Column(name="description",length = 80,nullable = false)
	 private String description;
	 
	 @Column(name = "active")
	 private boolean isActive;
	
	 @ManyToOne(targetEntity =ServiceCategoryType.class,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	 @JoinColumn(name = "service_category_type_id",referencedColumnName = "id")
	 private ServiceCategoryType serviceCategoryType;
	 
	 @ManyToOne(targetEntity =ServiceSubCategoryType.class,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	 @JoinColumn(name = "service_sub_category_type_id",referencedColumnName = "id")
	 private ServiceSubCategoryType serviceSubCategoryType;
	 
	 @ManyToOne(targetEntity =ServiceExecutorContactType.class,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	 @JoinColumn(name = "service_executor_contact_type_id",referencedColumnName = "id")
	 private ServiceExecutorContactType serviceExContactType;
	 
	 @Column(name="lang",length = 200)
	 private String language;
	 
	 @Column(name="image_path",length = 255)
	 private String imagePath;

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

	public ServiceSubCategoryType getServiceSubCategoryType() {
		return serviceSubCategoryType;
	}

	public void setServiceSubCategoryType(ServiceSubCategoryType serviceSubCategoryType) {
		this.serviceSubCategoryType = serviceSubCategoryType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ServiceExecutorContactType getServiceExContactType() {
		return serviceExContactType;
	}

	public void setServiceExContactType(ServiceExecutorContactType serviceExContactType) {
		this.serviceExContactType = serviceExContactType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
