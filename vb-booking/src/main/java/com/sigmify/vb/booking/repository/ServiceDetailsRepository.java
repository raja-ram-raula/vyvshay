package com.sigmify.vb.booking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigmify.vb.booking.entity.ServiceDetails;

public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails,Long>{
	
	@Query("from ServiceDetails where isActive = true")
	List<ServiceDetails> getAll();
	
	@Query("select count(*) from ServiceDetails where isActive = true and vehicleNumber =:vehicleNumber and serviceType =:serviceType")
	int validate(@Param("vehicleNumber") String vehicleNumber, @Param("serviceType")String serviceType);
	
	@Query("select count(*) from ServiceDetails where phoneNo =:phoneNo and userType =:userType")
	int count(@Param("phoneNo") String phoneNo, @Param("userType")String userType);
	  
	@Query("from ServiceDetails where phoneNo =:phone and vehicleNumber =:vehicleNumber")
	List<ServiceDetails> findByMobileNoAndVehicleNo(String phone,String vehicleNumber);
	
	@Query("from ServiceDetails where phoneNo =:phone and userType='U_TYPE_SERVICE_EXECUTOR'")
	List<ServiceDetails> findByMobileNo(String phone);
	
	@Query("from ServiceDetails where uanNo =:uan and userType='U_TYPE_SERVICE_EXECUTOR' ")
	List<ServiceDetails> findByUanNo(String uan);
	
	@Query("from ServiceDetails where zipCode =:zipCode and serviceType =:serviceType and availability ='AVAILABLE' ")
	List<ServiceDetails> findByServiceandZipcode(@Param("zipCode")String zipcode,@Param("serviceType")String serviceType);

	//@Query("from ServiceDetails where userName=:userName and userType = 'U_TYPE_SERVICE_EXECUTOR' and availability ='AVAILABLE' ")
	List<ServiceDetails> findByUserName(String userName);
	/*-----------
	@Query("from ServiceDetails where userName=:userName and serviceType=:serviceType")
	ServiceDetails getByUserNameAndServiceType(String userName,String serviceType);
	*/
	@Query("from ServiceDetails where userName=:userName and serviceType=:serviceType")
	List<ServiceDetails> getByUserNameAndServiceType(String userName,String serviceType);
	
	@Query("from ServiceDetails where phoneNo =:mobile and serviceType =:serviceName and userType='U_TYPE_SERVICE_EXECUTOR' ")
	ServiceDetails getByPhoneNo(String mobile,String serviceName);
	
	@Query("from ServiceDetails where serviceOwnerName =:ownerName and serviceExecutorName =:executorName and serviceType =:description and vehicleNumber =:vehicleNo")
	ServiceDetails getByServiceOwnerNameAndServiceExecutorNameDesc(String ownerName,String executorName,String description,String vehicleNo);
	
	//@Query("from ServiceDetails where serviceExecutorName =:executorName and serviceType =:description")
	//ServiceDetails getByServiceOwnerNameAndServiceExecutorName(String executorName,String description);
	
	@Query("from ServiceDetails where serviceType =:description")
	ServiceDetails getByServiceOwnerNameAndServiceExecutorName(String description);
	
	//@Query("from ServiceDetails where serviceType =:description and userName=:userName and serviceExecutorName =:exeName and id =:serviceDetailsId")
	//ServiceDetails getByDescriptionAndServiceOwnerUserName(String description,String userName,String exeName,Long serviceDetailsId);
	
	@Query("from ServiceDetails where serviceType =:description and userName=:userName  and id =:serviceDetailsId")
	ServiceDetails getByDescriptionAndServiceOwnerUserName(String description,String userName,Long serviceDetailsId);
	
	@Query("from ServiceDetails where serviceType =:description")
	List<ServiceDetails> getByServiceDesc(String description);
	
	@Query("from ServiceDetails where serviceExecutorName=:name and phoneNo=:phone and uanNo=:uan")
	ServiceDetails getByExeNameAndPhoneAndUan(String name,String phone,String uan);
	
	@Query("from ServiceDetails where userName=:userName and userType = 'U_TYPE_SERVICE_EXECUTOR' and availability ='AVAILABLE' ")
	List<ServiceDetails> findBySeviceExecutorName(String userName);
	
	@Query("from ServiceDetails where phoneNo=:phone and userType = 'U_TYPE_SERVICE_EXECUTOR' and availability ='AVAILABLE' ")
	List<ServiceDetails> findBySeviceExecutorMobile(String phone);
	
	@Query("from ServiceDetails where serviceExecutorName=:name and serviceType=:serviceType")
	ServiceDetails getByExecutorNameAndServiceType(String name,String serviceType);
	
	@Query("from ServiceDetails where userName =:userName and userType = 'U_TYPE_SERVICE_PROVIDER' and availability ='AVAILABLE' ")
	List<ServiceDetails> findBySelfService(String userName);
	
	//@Query("from ServiceDetails where onSeasonPrice=(select max(onSeasonPrice) from ServiceDetails where (serviceType =:serviceName and zipCode =:pincode)) order by desc limit 1")
	//from OrderDetails where createdBy =:userName and isActive = true order by id desc
	@Query("from ServiceDetails where serviceType =:serviceName and zipCode =:pincode order by onSeasonPrice desc")
	List<ServiceDetails> findByPincodeAndServiceName(String serviceName,String pincode,Pageable pageable);
	
	@Query("from ServiceDetails where id =:id")
	ServiceDetails findByServiceExeId(Long id);
	
	@Query("from ServiceDetails where zipCode =:pincode and serviceType =:serviceName and userName =:ownerId")
	List<ServiceDetails> findByServiceOwnerAndServiceName(String pincode,String serviceName,String ownerId,Pageable pageable);
}
