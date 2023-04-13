package com.sigmify.vb.booking.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.booking.entity.Price;

public interface PriceRepository extends JpaRepository<Price,Long>{
	
}
