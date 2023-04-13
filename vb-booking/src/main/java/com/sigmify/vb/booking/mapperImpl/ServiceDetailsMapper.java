package com.sigmify.vb.booking.mapperImpl;

import org.springframework.stereotype.Component;

import com.sigmify.vb.booking.dto.ServiceDetailsDTO;
import com.sigmify.vb.booking.entity.ServiceDetails;
import com.sigmify.vb.booking.mapper.Mapper;
@Component
public class ServiceDetailsMapper implements Mapper<ServiceDetails, ServiceDetailsDTO>{

	@Override
	public ServiceDetailsDTO convert(ServiceDetails serviceDetails) {
		// TODO Auto-generated method stub
		ServiceDetailsDTO serviceDetailsDto=new ServiceDetailsDTO();
		serviceDetailsDto.setServiceType(serviceDetails.getServiceType());
		serviceDetailsDto.setServiceExecutorName(serviceDetails.getServiceExecutorName());
		serviceDetailsDto.setServiceOwnerName(serviceDetails.getServiceOwnerName());
		serviceDetailsDto.setUanNo(serviceDetails.getUanNo());
		serviceDetailsDto.setOffSeasonPrice(serviceDetails.getOffSeasonPrice());
		serviceDetailsDto.setOnSeasonPrice(serviceDetails.getOnSeasonPrice());
		serviceDetailsDto.setUnitMeasurementType(serviceDetails.getUnitMeasurementType());
		serviceDetailsDto.setLattitude(serviceDetails.getLattitude());
		serviceDetailsDto.setLongitude(serviceDetails.getLongitude());
		//serviceDetailsDto.setAvailability(serviceDetails.getAvailability());
		serviceDetailsDto.setUserType(serviceDetails.getUserType());
		serviceDetailsDto.setPhoneNo(serviceDetails.getPhoneNo());
		serviceDetailsDto.setUserName(serviceDetails.getUserName());
		serviceDetailsDto.setZipCode(serviceDetails.getZipCode());
		return serviceDetailsDto;
	}

	@Override
	public ServiceDetails toEntity(ServiceDetailsDTO serviceDetailsDto) throws Exception {
		// TODO Auto-generated method stub
		ServiceDetails serviceDetails=new ServiceDetails();
		serviceDetails.setServiceType(serviceDetailsDto.getServiceType());
		serviceDetails.setServiceExecutorName(serviceDetailsDto.getServiceExecutorName());
		serviceDetails.setServiceOwnerName(serviceDetailsDto.getServiceOwnerName());
		serviceDetails.setUanNo(serviceDetailsDto.getUanNo());
		serviceDetails.setOffSeasonPrice(serviceDetailsDto.getOffSeasonPrice());
		serviceDetails.setOnSeasonPrice(serviceDetailsDto.getOnSeasonPrice());
		serviceDetails.setUnitMeasurementType(serviceDetailsDto.getUnitMeasurementType());
		serviceDetails.setLattitude(serviceDetailsDto.getLattitude());
		serviceDetails.setLongitude(serviceDetailsDto.getLongitude());
		//serviceDetails.setAvailability(serviceDetailsDto.getAvailability());
		serviceDetails.setUserType(serviceDetailsDto.getUserType());
		serviceDetails.setPhoneNo(serviceDetailsDto.getPhoneNo());
		serviceDetails.setUserName(serviceDetailsDto.getUserName());
		serviceDetails.setZipCode(serviceDetailsDto.getZipCode());
		return serviceDetails;
	}

}
