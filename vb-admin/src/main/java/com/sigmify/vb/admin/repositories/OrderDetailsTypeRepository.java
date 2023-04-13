package com.sigmify.vb.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.admin.entity.OrderDetails;



public interface OrderDetailsTypeRepository extends JpaRepository<OrderDetails, Long>{

	@Query("from OrderDetails where  orderNumber =:orderId and (serviceExecutorUserId =:userId or createdBy =: userId)") 
	OrderDetails findByOrderId(@Param("orderId")String	orderId,@Param("userId")String userId);
	
	@Query("from OrderDetails where orderNumber =:orderId and orderStatus =:status and isActive = true") 
	List<OrderDetails> updateOtherOrders(@Param("orderId")String orderId,@Param("status")String status);
	
	
	@Query("select createdBy from OrderDetails where  orderNumber =:orderId and isActive = true") 
	 String findByOrderId(@Param("orderId")String orderId);
	
	@Query("from OrderDetails where serviceExecutorUserId =:userName and orderStatus = 'ORDER_ACCEPTED' and orderNumber =:orderId")
	OrderDetails findByAcceptedList(@Param("userName")String userName, @Param("orderId")String orderId);
	 
	 
	/*
	 * @Query("from OrderDetails where (serviceExecutorUserId =:userId or serviceExecutorName =:userId) and orderStatus =:status and isActive = true "
	 * ) List<OrderDetails> findSEOrders(@Param("userId")String
	 * userId, @Param("status")String status);
	 * 
	 * @Query("from OrderDetails where  orderNumber =:orderId and (serviceExecutorUserId =:userId or createdBy =: userId) and isActive = true"
	 * ) OrderDetails findByOrderId(@Param("orderId")String
	 * orderId,@Param("userId")String userId);
	 * 
	 * List<OrderDetails> findByCreatedBy(String userId);
	 * 
	 * @Query("from OrderDetails where orderNumber =:orderId and orderStatus =:status and isActive = true"
	 * ) List<OrderDetails> updateOtherOrders(@Param("orderId")String
	 * orderId,@Param("status")String status);
	 * 
	 * @Query("from OrderDetails where orderNumber =:orderId and orderStatus =:status and isActive = true"
	 * ) OrderDetails findByOrderIdAndStatus(@Param("orderId")String
	 * orderId, @Param("status")String status);
	 * 
	 * @Query("from OrderDetails where orderNumber =:orderId and paymentStatus =:status and isActive = true"
	 * ) List<OrderDetails>
	 * findOrdersByOrderIdandcompletedStatus(@Param("orderId")String
	 * userId, @Param("status")String status);
	 * 
	 * @Query("from OrderDetails where serviceExecutorUserId=:userId and orderNumber=:orderNum"
	 * ) OrderDetails findByServiceExecutorUserId(String userId,String orderNum);
	 * 
	 * List<OrderDetails> findByServiceExecutorUserId(String userId);
	 * 
	 * @Query("from OrderDetails where (serviceExecutorUserId =:userName or createdBy =: userName) and orderStatus = 'ORDER_ACCEPTED' "
	 * ) List<OrderDetails> trackOrdersByUserId(@Param("userName")String userName);
	 * 
	 * @Query("select serviceExecutorUserId from OrderDetails where  orderNumber =:orderId and isActive = true"
	 * ) String findByOrderId(@Param("orderId")String orderId);
	 * 
	 * @Query("from OrderDetails where serviceDetailsId=:id") OrderDetails
	 * findByServiceDetailsId(ServiceDetails id);
	 */
	
}
