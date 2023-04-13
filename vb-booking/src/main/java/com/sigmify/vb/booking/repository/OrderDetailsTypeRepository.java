package com.sigmify.vb.booking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.booking.entity.OrderDetails;
import com.sigmify.vb.booking.entity.ServiceDetails;


public interface OrderDetailsTypeRepository extends JpaRepository<OrderDetails, Long>{

	
	@Query("from OrderDetails where (serviceExecutorUserId =:userId or serviceExecutorName =:userId or createdBy =:userId) and orderStatus =:status order by id desc")
	List<OrderDetails> findSEOrders(@Param("userId")String userId, @Param("status")String status);
	
	@Query("from OrderDetails where createdBy =:userId and orderStatus =:status order by id desc")
	List<OrderDetails> findUserOrders(@Param("userId")String userId, @Param("status")String status,Pageable pageable);
    
	@Query("from OrderDetails where  orderNumber =:orderId and (serviceExecutorUserId =:userId or createdBy =: userId)")
	OrderDetails findByOrderId(@Param("orderId")String orderId,@Param("userId")String userId);
    
	@Query("from OrderDetails where  orderNumber =:orderId and (serviceExecutorUserId =:userId or createdBy =: userId)")
	List<OrderDetails> findByOrderIdAndUsername(@Param("orderId")String orderId,@Param("userId")String userId);
	
	@Query("from OrderDetails where createdBy =:userId and orderStatus != 'ORDER_ACCEPTED_BY_OTHERS' order by id desc")
	List<OrderDetails> findByCreatedBy(String userId);
	
	@Query("from OrderDetails where createdBy =:userId and orderStatus != 'ORDER_ACCEPTED_BY_OTHERS' order by id desc")
	List<OrderDetails> findByCreatedByNew(String userId,Pageable pageable);
	
	@Query("from OrderDetails where orderNumber =:orderId and orderStatus != 'ORDER_ACCEPTED_BY_OTHERS' order by id desc")
	List<OrderDetails> findByOrderByNew(String orderId,Pageable pageable);
	
	@Query("from OrderDetails where orderNumber =:orderId and orderStatus =:status")
	List<OrderDetails> updateOtherOrders(@Param("orderId")String orderId,@Param("status")String status);
	
	@Query("from OrderDetails where orderNumber =:orderId and orderStatus =:status")
	OrderDetails findByOrderIdAndStatus(@Param("orderId")String orderId, @Param("status")String status);
	
	@Query("from OrderDetails where orderNumber =:orderId and paymentStatus =:status and isActive = true")
	List<OrderDetails> findOrdersByOrderIdandcompletedStatus(@Param("orderId")String userId, @Param("status")String status);
	@Query("from OrderDetails where serviceExecutorUserId=:userId and orderNumber=:orderNum")
	OrderDetails findByServiceExecutorUserId(String userId,String orderNum);
	
	List<OrderDetails> findByServiceExecutorUserId(String userId);
	
	@Query("from OrderDetails where (serviceExecutorUserId =:userName or createdBy =: userName) and orderStatus = 'ORDER_ACCEPTED' ")
	List<OrderDetails> trackOrdersByUserId(@Param("userName")String userName);
	
	@Query("select serviceExecutorUserId from OrderDetails where  orderNumber =:orderId")
	List<String> findByOrderId(@Param("orderId")String orderId);
	
	@Query("from OrderDetails where  orderNumber =:orderNumber")
	List<OrderDetails> findByOrderNumber(String orderNumber);
	
	@Query("from OrderDetails where  createdBy =:userName and orderNumber =:orderId")
	OrderDetails findByOrderUserName(String userName,String orderId);
	
	@Query("from OrderDetails where  orderNumber =:orderNumber and (orderStatus = 'ORDER_ACCEPTED' or orderStatus = 'ORDER_COMPLETED' or orderStatus = 'ORDER_CANCELLED')") 
	OrderDetails findByOrderNum(String orderNumber);
	
	@Query("from OrderDetails where  orderNumber =:orderId and isActive = true")
	List<OrderDetails> findProviderListByOrderId(String orderId);
	
	@Query("from OrderDetails where serviceDetailsId=:id")
	List<OrderDetails> findByServiceDetailsId(ServiceDetails id);
	
	@Query("select count(*) from OrderDetails where orderStatus='ORDER_ACCEPTED' and serviceExecutorUserId =:userId and serviceID =:serviceId and orderExecutionTime between :min and :max")
	int findPendingSRCount(@Param("userId")String userId, @Param("serviceId")long serviceId, @Param("min")Date min, @Param("max")Date max);
	
	@Query("from OrderDetails where createdAt between :min and :max and orderStatus = 'ORDER_ACCEPTED' order by createdAt")
	List<OrderDetails> getOrderDetails(@Param("min")Date min, @Param("max")Date max);
	
	@Query("from OrderDetails where serviceDetailsId.id =:id")
	List<OrderDetails> getOrdersByServiceDetailsId(Long id);
	
	@Query("from OrderDetails where createdBy =:userName and isActive = true order by id desc")
	List<OrderDetails> findByUserName(@Param("userName")String userName, Pageable pageable);
	
	@Query("from OrderDetails where orderNumber =:orderId and orderStatus='ORDER_ACCEPTED'")
	OrderDetails findByOrder(@Param("orderId")String orderId);
	
	@Query("from OrderDetails where orderNumber =:orderId and orderStatus =:status order by id desc")
	List<OrderDetails> ordersByOrderIdStatus(@Param("orderId")String orderId, @Param("status")String status);
	
	@Query("from OrderDetails where serviceExecutorUserId =:userId and serviceID =:id and zipCode =:pincode")
	OrderDetails findByOrderSave(String userId,Long id,String pincode);
}
