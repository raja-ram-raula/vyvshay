package com.sigmify.vb.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigmify.vb.booking.entity.ServiceCategoryType;

public interface CategoryTypeRepository extends JpaRepository<ServiceCategoryType, Long>{
	
	@Query("from ServiceCategoryType where isActive = true")
	List<ServiceCategoryType> getAll();
	
	@Query("from ServiceCategoryType where language =:lang and isActive = true")
	List<ServiceCategoryType> getAlll(String lang);
	
	public ServiceCategoryType findByDescription(String desc);
	

}
