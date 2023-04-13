package com.sigmify.vb.admin.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.admin.entity.Order;



public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("from Order where orderId =:orderId") 
	Order findByOrderId(@Param("orderId")String orderId);
	@Query("select count(*) from Order")
	int getAllReq();
	
	/*
	 * @Query("from Order") List<Order> getAll();
	 * 
	 * @Query("SELECT coalesce(MAX(id),0)FROM Order") Long selectMaxId();
	 * 
	 * @Query("from Order where createdBy =:userId and isActive = true ")
	 * List<Order> findByUserId(@Param("userId")String userId);
	 * 
	 * @Query("from Order where orderId =:orderId") Order
	 * findByOrderId(@Param("orderId")String orderId);
	 * 
	 * @Query("from Order where orderId =:orderId and orderStatus =:status and isActive = true"
	 * ) List<Order> ordersByOrderIdStatus(@Param("orderId")String
	 * orderId, @Param("status")String status);
	 * 
	 * @Query("from Order where createdBy =:userId and orderStatus =:status and isActive = true"
	 * ) List<Order> findOrdersByUserIdandStatus(@Param("userId")String
	 * userId, @Param("status")String status);
	 * 
	 * @Query("from Order where createdBy =:userName and isActive = true order by id desc"
	 * ) List<Order> findByUserName(@Param("userName")String userName, Pageable
	 * pageable);
	 * 
	 * @Query("from Order where requesterContact =:phone and orderId=:orderId")
	 * Order getByNumberAndOrderId(String phone,String orderId);
	 */
}
