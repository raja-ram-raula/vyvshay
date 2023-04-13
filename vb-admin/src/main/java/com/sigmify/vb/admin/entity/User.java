package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sigmify.vb.admin.entity.metadata.UserType;

//entity class

@Entity
@Table(schema = "admin",name="user")
@JsonInclude(value = Include.NON_NULL)
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -652859095263750353L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_seq")
	  @SequenceGenerator(schema = "admin", name = "user_id_seq",
	      sequenceName = "user_id_seq", allocationSize = 1)
	  private Long id;
	  
	  @Column(name = "username",length = 100,unique = true,nullable = false)
	  private String userName;

	  @Column(name="fname",length = 100)
	  private String fName;
	  
	  @Column(name="lname",length = 100)
	  private String lName;
	  
	  @Column(name="profile_photo",length = 200) 
	  private String photo;
	  
	  @ManyToOne(targetEntity =UserType.class,cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	  @JoinColumn(name = "user_type",referencedColumnName = "id")
	  private UserType usertype;
	 
	  @Column(name="create_date",nullable = false)
	  private LocalDateTime createDate;
	 
	 // @Temporal(TemporalType.TIMESTAMP)
	  @Column(name="last_update_date",nullable = false)
	  private LocalDateTime lastUpdateDate;
	  
	  @Column(name="is_active")
	  private boolean active;
	  
	  @Column(name="last_update_by")
	  private Long lastUpdateBy;
	  
	  @Column(name="user_agent")
	  private String agent;
	  
	  @OneToMany(targetEntity = Address.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	  @JoinColumn(name = "user_id",referencedColumnName = "id")
	  private List<Address> listAddress;
	  
	  @OneToOne(targetEntity = UserContactDetails.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	  private UserContactDetails ucDetails;
	  
	  @OneToOne(targetEntity = UserPassword.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	  private UserPassword userPassword;
	  
	  @OneToOne(targetEntity = AccountDetails.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	  private AccountDetails accDetails;
	  
	  @OneToOne(targetEntity = UserIdProof.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
	  private UserIdProof userIdProof;
	  
	  @OneToOne(mappedBy = "generatedfor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	  private Referral referral;
	//-----getter&setter method
	  
	public UserIdProof getUserIdProof() {
		return userIdProof;
	}
	public void setUserIdProof(UserIdProof userIdProof) {
		this.userIdProof = userIdProof;
	}
	public AccountDetails getAccDetails() {
		return accDetails;
	}
	public void setAccDetails(AccountDetails accDetails) {
		this.accDetails = accDetails;
	}
	public UserType getUsertype() {
		return usertype;
	}
	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}
	public UserPassword getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(UserPassword userPassword) {
		this.userPassword = userPassword;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public UserType getUserType() {
		return usertype;
	}
	public void setUserType(UserType userType) {
		this.usertype = userType;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Referral getReferral() {
		return referral;
	}
	public void setReferral(Referral referral) {
		this.referral = referral;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<Address> getListAddress() {
		return listAddress;
	}
	public void setListAddress(List<Address> listAddress) {
		this.listAddress = listAddress;
	}
	public UserContactDetails getUcDetails() {
		return ucDetails;
	}
	public void setUcDetails(UserContactDetails ucDetails) {
		this.ucDetails = ucDetails;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", fName=" + fName + ", lName=" + lName + ", photo="
				+ /*photo +*/ ", userType=" + usertype + ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate
				+ ", active=" + active + ", lastUpdateBy=" + lastUpdateBy + "]";
	}
}
