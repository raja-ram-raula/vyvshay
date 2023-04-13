package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigmify.vb.admin.entity.User;
import com.sigmify.vb.admin.entity.UserContactDetails;

public interface UserContactDetailsRepository extends JpaRepository<UserContactDetails,Long > {
	//public  boolean isMobileNumberExist(String mobileNumber, String password);
	//public boolean isEmailExist(String emailId, String password);
	
	@Query("from UserContactDetails where phoneNo =:phoneNumber and userType =:userType")
	public UserContactDetails getUserContactsByPhoneNo(String phoneNumber, String userType);
	public UserContactDetails findByUser(User user);
	
}
