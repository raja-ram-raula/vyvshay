package com.sigmify.vb.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigmify.vb.booking.entity.ServiceExecutorContactType;


public interface ServiceExecutorContactTypeRepository extends JpaRepository<ServiceExecutorContactType, Long>{
	
	@Query("from ServiceExecutorContactType where isActive = true")
	List<ServiceExecutorContactType> getAll();
	
	public ServiceExecutorContactType findByDescription(String desc);
	
	public ServiceExecutorContactType findByName(String name);

	
	

}
