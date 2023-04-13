package com.sigmify.vb.admin.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.admin.entity.ServiceCategoryType;
import com.sigmify.vb.admin.entity.ServiceType;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>{
	
    @Query("from ServiceType where serviceSubCategoryType.id =:subCategoryId and isActive = true order by id ASC")
    List<ServiceType> findBySubCategoryId(@Param("subCategoryId")Long subCategoryId);
    
    @Query("from ServiceType where serviceCategoryType.id =:categoryId and isActive = true order by id ASC")
	List<ServiceType> getServiceDetailsByServiceCategoryType(@Param("categoryId")Long categoryId);
    
    @Query("from ServiceType where serviceCategoryType.id =:categoryId and serviceExContactType.id =:contactId and isActive = true order by id ASC")
   	List<ServiceType> getServiceDetailsByServiceCategoryType(@Param("categoryId")Long categoryId,@Param("contactId")Long contactId);
    
    ServiceType findByName(String name);
    
    @Query("from ServiceType where name =:name and serviceCategoryType=:id")
    ServiceType getByNameAndCategoryId(String name,ServiceCategoryType id);
    
    

}
