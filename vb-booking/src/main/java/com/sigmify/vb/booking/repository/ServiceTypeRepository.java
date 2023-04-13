package com.sigmify.vb.booking.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.booking.entity.ServiceCategoryType;
import com.sigmify.vb.booking.entity.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>{
	
    @Query("from ServiceType where serviceSubCategoryType.id =:subCategoryId and isActive = true order by id ASC")
    List<ServiceType> findBySubCategoryId(@Param("subCategoryId")Long subCategoryId);
    
    @Query("from ServiceType where serviceSubCategoryType.id =:subCategoryId and language=:lang and  isActive = true order by id ASC")
    List<ServiceType> findBySubCategoryId(@Param("subCategoryId")Long subCategoryId,@Param("lang")String lang);
    
    @Query("from ServiceType where serviceCategoryType.id =:categoryId and isActive = true order by id ASC")
	List<ServiceType> getServiceDetailsByServiceCategoryType(@Param("categoryId")Long categoryId);
    
    @Query("from ServiceType where serviceCategoryType.id =:categoryId and language=:lang and isActive = true order by id ASC")
	List<ServiceType> getServiceDetailsByServiceCategoryType(@Param("categoryId")Long categoryId,@Param("lang")String lang);
    
    @Query("from ServiceType where serviceCategoryType.id =:categoryId and serviceExContactType.id =:contactId and isActive = true order by id ASC")
   	List<ServiceType> getServiceDetailsByServiceCategoryType(@Param("categoryId")Long categoryId,@Param("contactId")Long contactId);
    
    @Query("from ServiceType where name =:name and isActive = true order by id ASC")
    ServiceType findByName(String name);
    
    @Query("from ServiceType where name =:name and serviceCategoryType=:id")
    ServiceType getByNameAndCategoryId(String name,ServiceCategoryType id);
    
    

}
