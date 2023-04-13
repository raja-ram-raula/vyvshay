package com.sigmify.vb.booking.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table (schema = "booking" , name ="service_details")
@JsonInclude(Include.NON_NULL)
public class ServiceDetails extends Auditable<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680662545352789736L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "service_details_id_seq")
	@SequenceGenerator(schema = "booking" , name = "service_details_id_seq", sequenceName = "service_details_id_seq", allocationSize =1 )
	private Long id;
	
	//@ManyToOne(targetEntity =ServiceType.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@Column(name ="service_type_name")
	private String serviceType;
	
	@Column(name = "service_owner_name",nullable=false)
	private String serviceOwnerName;
	
	@Column(name = "service_executor_name",nullable = false)
	private String serviceExecutorName;
	
	@Column(name = "uan_no")
	private String uanNo;
	
	@Column(name = "off_season_price",nullable = false)
	private Integer offSeasonPrice;
	
	@Column(name = "on_season_price",nullable = false)
	private Integer onSeasonPrice;
	
	@Column(name =  "unit_measurement_type_name")
	private Long unitMeasurementType;
	
	@Column(name =  "is_active",nullable = false)
	private boolean isActive;
	
	@Column(name="lattitude",nullable = false)
	private String lattitude;
	
	@Column(name="longitude",nullable = false)
	private String longitude;
	
	@Column(name="availability",nullable = false)
	private String availability;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="phone_number")
	private String phoneNo;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="zip_code")
	private String zipCode;
	
	@Column(name="vehicle_number")
	private String vehicleNumber;
	
	@Column(name="idproof_photo")
	private String idproofPhoto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceOwnerName() {
		return serviceOwnerName;
	}
	public void setServiceOwnerName(String serviceOwnerName) {
		this.serviceOwnerName = serviceOwnerName;
	}
	public String getServiceExecutorName() {
		return serviceExecutorName;
	}
	public void setServiceExecutorName(String serviceExecutorName) {
		this.serviceExecutorName = serviceExecutorName;
	}
	public Integer getOffSeasonPrice() {
		return offSeasonPrice;
	}
	public void setOffSeasonPrice(Integer offSeasonPrice) {
		this.offSeasonPrice = offSeasonPrice;
	}
	public Integer getOnSeasonPrice() {
		return onSeasonPrice;
	}
	public void setOnSeasonPrice(Integer onSeasonPrice) {
		this.onSeasonPrice = onSeasonPrice;
	}
	public Long getUnitMeasurementType() {
		return unitMeasurementType;
	}
	public void setUnitMeasurementType(Long unitMeasurementType) {
		this.unitMeasurementType = unitMeasurementType;
	}

	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getUanNo() {
		return uanNo;
	}
	public void setUanNo(String uanNo) {
		this.uanNo = uanNo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdproofPhoto() {
		return idproofPhoto;
	}
	public void setIdproofPhoto(String idproofPhoto) {
		this.idproofPhoto = idproofPhoto;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public boolean getActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
