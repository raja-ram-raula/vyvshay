package com.sigmify.vb.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.booking.entity.ServiceSubCategoryType;

public interface SubCategoryRepository extends JpaRepository<ServiceSubCategoryType, Long> {

	@Query("from ServiceSubCategoryType where serviceCategoryType.id =:categoryId and isActive = true")
    List<ServiceSubCategoryType> findByServiceCategoryType(@Param("categoryId")Long categoryId);
	 
	@Query("from ServiceSubCategoryType where serviceCategoryType.id =:categoryId and isActive = true and language=:lang")
    List<ServiceSubCategoryType> findByServiceCategoryTypeAllLang(@Param("categoryId")Long categoryId,@Param("lang")String lang);

	 public ServiceSubCategoryType findByDescription(String desc);
	 public ServiceSubCategoryType findByName(String name);
}
