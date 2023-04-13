package com.sigmify.vb.booking.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ServiceResponse{
	List<ServiceSubCategoryDto> serviceSubCategoryDto;
	List<SelfServiceDTO> selfServiceDTO;

	public List<SelfServiceDTO> getSelfServiceDTO() {
		return selfServiceDTO;
	}

	public void setSelfServiceDTO(List<SelfServiceDTO> selfServiceDTO) {
		this.selfServiceDTO = selfServiceDTO;
	}

	public List<ServiceSubCategoryDto> getServiceSubCategoryDto() {
		return serviceSubCategoryDto;
	}

	public void setServiceSubCategoryDto(List<ServiceSubCategoryDto> serviceSubCategoryDto) {
		this.serviceSubCategoryDto = serviceSubCategoryDto;
	}	
}