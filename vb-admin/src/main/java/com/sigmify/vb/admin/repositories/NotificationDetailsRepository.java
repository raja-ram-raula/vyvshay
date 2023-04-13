package com.sigmify.vb.admin.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.admin.entity.NotificationDetails;

public interface NotificationDetailsRepository extends JpaRepository<NotificationDetails, Integer>{
	
	@Query("from NotificationDetails where receiverId =:receiverId") 
	NotificationDetails findByReceiverId(@Param("receiverId")String receiverId);
	
	@Transactional
	@Modifying
	@Query("Update NotificationDetails set notificationStatus = 1 where notificationId =:notificationId") 
	int updateNotificationStatus(@Param("notificationId")int notificationId);
	
	@Query("from NotificationDetails where receiverId =:receiverId order by notificationId desc") 
	List<NotificationDetails> findAllByRId(@Param("receiverId")String receiverId);
	
	


}
