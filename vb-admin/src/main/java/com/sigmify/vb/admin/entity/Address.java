package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sigmify.vb.admin.entity.metadata.AddressType;
import com.sigmify.vb.admin.entity.metadata.State;

@Entity
@Table(schema = "admin",name = "address")
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8573950742028190L;
	
	
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "address_id_seq")
	  @SequenceGenerator(schema = "admin", name = "address_id_seq",
	      sequenceName = "address_id_seq", allocationSize = 1)
	  private Long id;
	
	@ManyToOne(targetEntity = User.class,cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private User user;
	
	@Column(name = "city_locality",length = 50)
	private String cityLocality;
	
	/*@ManyToOne(targetEntity = District.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id",referencedColumnName = "id")
	private District districtId;*/
	
	@Column(name = "district_Id",length = 400)
    private Integer districtId;
	
	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	@ManyToOne(targetEntity = State.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id",referencedColumnName = "id")
	private State stateId;
	
    @Column(name = "zip_code",length = 6)
	private Integer zipCode;
    
    @Column(name = "lattitude")
    private String lattitude;
    
    @Column(name= "longitude")
    private String longitude;
    
    @ManyToOne(targetEntity = AddressType.class,cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "address_type_id",referencedColumnName = "id")
    private AddressType addressType;
    
    @Column(name = "address",length = 400)
    private String address;
    
    @Column(name = "zeo_location",length = 400)
    private String zeoLocation;
    
    @OneToOne(targetEntity = User.class,cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by",referencedColumnName = "id")
    private User createdBy;
    
    @Column(name = "creation_date",nullable = false)
    private LocalDateTime creationDate;
    
    @Column(name = "last_update_date",nullable = false)
    private LocalDateTime lastUpdateDate;
    
    @OneToOne(targetEntity = User.class,cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "last_update_by",referencedColumnName = "id")
    private User lastUpdateBy;
    
	/*@ManyToOne(targetEntity = District.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "district_name",referencedColumnName = "description")
	private District districtName;*/
    
    @Column(name = "district_name",length = 400)
    private String districtName;
    
    @ManyToOne(targetEntity = State.class,cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "state_name",referencedColumnName = "description")
	private State stateName;

    
  //---getter setter method

	

    public State getStateName() {
		return stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}


	public void setStateName(State stateName) {
		this.stateName = stateName;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user=user;
	}

	public String getCityLocality() {
		return cityLocality;
	}

	public void setCityLocality(String cityLocality) {
		this.cityLocality = cityLocality;
	}



	public State getStateId() {
		return stateId;
	}

	public void setStateId(State stateId) {
		this.stateId = stateId;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZeoLocation() {
		return zeoLocation;
	}

	public void setZeoLocation(String zeoLocation) {
		this.zeoLocation = zeoLocation;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public User getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(User lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	//----toString Method
	@Override
	public String toString() {
		return "Address [id=" + id + ", userId=" + user + ", cityLocality=" + cityLocality + ", districtId="
				+ districtId + ", stateId=" + stateId + ", zipCode=" + zipCode + ", addressType=" + addressType
				+ ", address=" + address + ", zeoLocation=" + zeoLocation + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdateBy="
				+ lastUpdateBy + "]";
	}

}    
	

