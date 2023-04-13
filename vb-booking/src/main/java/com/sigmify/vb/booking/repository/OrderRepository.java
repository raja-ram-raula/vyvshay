package com.sigmify.vb.booking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.booking.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("from Order")
	List<Order> getAll();

	@Query("SELECT coalesce(MAX(id),0)FROM Order")
	Long selectMaxId();

	@Query("from Order where createdBy =:userId order by id desc")
	List<Order> findByUserId(@Param("userId")String userId);
	
	@Query("from Order where orderId =:orderId")
	Order findByOrderId(@Param("orderId")String orderId);
	
	@Query("from Order where orderId =:orderId and orderStatus =:status and isActive = true")
	List<Order> ordersByOrderIdStatus(@Param("orderId")String orderId, @Param("status")String status);
	
	@Query("from Order where createdBy =:userId and orderStatus =:status order by id desc")
	List<Order> findOrdersByUserIdandStatus(@Param("userId")String userId, @Param("status")String status);
	
	@Query("from Order where createdBy =:userName and isActive = true order by id desc")
	List<Order> findByUserName(@Param("userName")String userName,Pageable pageable);
	
	@Query("from Order where requesterContact =:phone and orderId=:orderId")
	Order getByNumberAndOrderId(String phone,String orderId);
	
	List<Order> findByServiceOwnerMobile(String phone);
	
	@Query("from Order where orderStatus =:status and createdAt >:createdAt order by id desc")
	List<Order> findAllByStatus(@Param("status")String status, @Param("createdAt")Date createdAt);
	
	@Query("select serviceExecutorUserId from OrderDetails where  orderNumber =:orderId")
	String findByOrderNumber(@Param("orderId")String orderId);
	
//	@Query("from Order where serviceId.id =:serviceId AND createdBy =:createdBy AND orderExecutionTime =:orderExecutionTime")
//	Order findByOrderDetails(@Param("serviceId")String serviceId, @Param("createdBy")String createdBy, @Param("orderExecutionTime")Date orderExecutionTime);
}
