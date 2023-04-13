package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

	
	@Entity
	@Table(schema = "admin",name="user_activity")
	@JsonInclude(value = Include.NON_NULL)
	public class UserActivity implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -652859095263750359L;
		
		  @Id
		  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_seq")
		  @SequenceGenerator(schema = "admin", name = "id_seq",
		      sequenceName = "id_seq", allocationSize = 1)
		  private Long id;
		  
		  //@Column(name = "username",length = 100,unique = true, nullable = true)
		  //private String userName;

		  @Column(name="fname",length = 100)
		  private String fName;
		  
		  @Column(name="lname",length = 100)
		  private String lName;
		  
		  @Column(name="mobile_no",length = 13, nullable = false)
		  private String mobileNo;
		  
		  		  
		  @Column(name = "user_type")
		  private String usertype;
		  
		  @Column(name = "api_type",nullable = false)
		  private String apiType;
		 
		  @Column(name="time_stamp",nullable = false)
		  private LocalDateTime timeStamp;

		 @Column(name="api_type_details")
		 private String apiTypeDetails;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

//		public String getUserName() {
//			return userName;
//		}
//
//		public void setUserName(String userName) {
//			this.userName = userName;
//		}

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

		public String getMobileNo() {
			return mobileNo;
		}

		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}

		public String getUsertype() {
			return usertype;
		}

		public void setUsertype(String usertype) {
			this.usertype = usertype;
		}

		public String getApiType() {
			return apiType;
		}

		public void setApiType(String apiType) {
			this.apiType = apiType;
		}

		public LocalDateTime getTimeStamp() {
			return timeStamp;
		}

		public void setTimeStamp(LocalDateTime timeStamp) {
			this.timeStamp = timeStamp;
		}

		

		public String getApiTypeDetails() {
			return apiTypeDetails;
		}

		public void setApiTypeDetails(String apiTypeDetails) {
			this.apiTypeDetails = apiTypeDetails;
		}

		@Override
		public String toString() {
			return "UserActivity [id=" + id + ", fName=" + fName + ", lName=" + lName + ", mobileNo=" + mobileNo
					+ ", usertype=" + usertype + ", apiType=" + apiType + ", timeStamp=" + timeStamp
					+ ", apiTypeDetails=" + apiTypeDetails + "]";
		}
		  
		  
		 
	

}
