package com.sigmify.vb.booking.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.booking.entity.ServiceStatusUser;

public interface ServiceStatusRepository extends JpaRepository<ServiceStatusUser,Long>{
	
}
