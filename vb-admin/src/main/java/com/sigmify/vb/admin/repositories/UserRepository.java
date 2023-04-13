package com.sigmify.vb.admin.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.admin.entity.Referral;
import com.sigmify.vb.admin.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User,Long>{
	   //for log in validation for verify username
       public  User findByUserName(String name);
       
       public User findByfName(String name);
       
       @Query("from User where userName =:userName")
       User getByUserName(@Param("userName")String userName);
       
   	@Modifying
   	@Query("UPDATE User set active=false  where userName =:seid")
   	int updateExecutorStatus(@Param("seid")String seid);
   	
    @Query("select count(*) from User where userName =:seid")
    int getUserStatus(@Param("seid")String seid);
    
    @Query("select count(*) from User where usertype =1")
    int getTotalFarmers();
    
    @Query("select count(*) from User where usertype =2")
    int getTotalServiceOwners();
    
    @Query("from User where referral =:referralCode")
    public User findByReferral(Referral referralCode);
   	
}
