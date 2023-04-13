package com.sigmify.vb.admin.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.admin.entity.PaymentDetails;


public interface PaymentRepository extends JpaRepository<PaymentDetails, Long>{

	@Query("from PaymentDetails where order =:orderId")
	PaymentDetails getPaymentDetails(@Param("orderId")String orderId);
	
	@Query("from PaymentDetails where order =:orderId")
	PaymentDetails updatePaymentStatus(@Param("orderId")String orderId);

	@Query("from PaymentDetails where createdBy =:userId and paymentStatus =:status")
	List<PaymentDetails> findOrdersByUserIdandcompletedStatus(@Param("userId")String userId, @Param("status")String status);
	
}
