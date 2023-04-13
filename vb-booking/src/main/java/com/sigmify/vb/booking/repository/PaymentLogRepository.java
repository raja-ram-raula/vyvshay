package com.sigmify.vb.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigmify.vb.booking.entity.PaymentLog;
import com.sigmify.vb.booking.entity.UnitMeasurementType;

public interface PaymentLogRepository extends JpaRepository<PaymentLog,Long>{
	
	PaymentLog findByUserName(String userName);

}
