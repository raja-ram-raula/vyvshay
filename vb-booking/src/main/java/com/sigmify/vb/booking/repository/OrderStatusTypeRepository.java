package com.sigmify.vb.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sigmify.vb.booking.entity.OrderStatusType;


public interface OrderStatusTypeRepository extends JpaRepository<OrderStatusType, Long>{

}
