package com.sigmify.vb.admin.mapperImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.AddressDTO;
import com.sigmify.vb.admin.entity.Address;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;
import com.sigmify.vb.admin.repositories.AddressRepository;

@Component
public class AddressMapper implements Mapper<Address, AddressDTO> {
	@Autowired
	 private AddressRepository addressrepo;
	
	 private static final Logger LOGGER=LoggerFactory.getLogger(AddressMapper.class);

	@Override
	public AddressDTO convert(Address adrs) {
		//converting Address entity into AddressDTO
		AddressDTO adrsDto=new AddressDTO();
		adrsDto.setId(adrs.getId());
		adrsDto.setAddress(adrs.getAddress());
		adrsDto.setAddressType(adrs.getAddressType());
		adrsDto.setCityLocality(adrs.getCityLocality());
		adrsDto.setDistrictName(adrs.getDistrictName());
		adrsDto.setDistrictId(adrs.getDistrictId());
		adrsDto.setZipCode(adrs.getZipCode());
		adrsDto.setZeoLocation(adrs.getZeoLocation());
		adrsDto.setLattitude(adrs.getLattitude());
		adrsDto.setLongitude(adrs.getLongitude());
		adrsDto.setStateId(adrs.getStateId().getId());
		adrsDto.setStateName(adrs.getStateName().getDescription());
		adrsDto.setUserId(adrs.getUser().getId());
		adrsDto.setCreatedBy(adrs.getCreatedBy().getId());
		adrsDto.setLastUpdateBy(adrs.getLastUpdateBy().getId());
		adrsDto.setCreationDate(adrs.getCreationDate());
		adrsDto.setLastUpdateDate(adrs.getLastUpdateDate());
		return adrsDto;
	}

	@Override
	public Address toEntity(AddressDTO addressDto) throws AdminException {
		//converting AddressDTO into Address entity
		if(addressDto==null) {
			 LOGGER.error("Address information is empty for-->{}",addressDto);
			  throw new AdminException(ErrorCode.REQUEST_ERROR,"Insufficient Address information");
		}
		Address adrs=null;
		if(addressDto.getId()!=null) {
			//getting Address entity by id from repository
			Optional<Address> opt = addressrepo.findById(addressDto.getId());
			if(opt.isPresent()) {
				adrs=opt.get();
			}
			else {
				LOGGER.error("No Address found for given id -->{} ",addressDto.getId());
				throw new AdminException(ErrorCode.ENTITY_NOT_FOUND,"A valid Address id is mandatory");
			}
		}
		else {
			 adrs=new Address();
			 adrs.setCreationDate(LocalDateTime.now());
		}
		adrs.setAddress(addressDto.getAddress());
		adrs.setCityLocality(addressDto.getCityLocality());
		adrs.setZipCode(addressDto.getZipCode());
		adrs.setZeoLocation(addressDto.getZeoLocation());
		adrs.setLattitude(addressDto.getLattitude());
		adrs.setLongitude(addressDto.getLongitude());
		adrs.setLastUpdateDate(LocalDateTime.now());
		return adrs;
	}
	
}
