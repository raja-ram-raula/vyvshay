package com.sigmify.vb.admin.mapperImpl;

import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.UserTypeDTO;
import com.sigmify.vb.admin.entity.metadata.UserType;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;

@Component
public class UserTypeMapper implements Mapper<UserType, UserTypeDTO>{

	@Override
	public UserTypeDTO convert(UserType source) {
		UserTypeDTO typeDTO = new UserTypeDTO();
		typeDTO.setId(source.getId());
		typeDTO.setName(source.getName());
		typeDTO.setDescription(source.getDescription());
		
		return typeDTO;
	}

	@Override
	public UserType toEntity(UserTypeDTO dto) throws AdminException {
		
		UserType userType = new UserType();
		userType.setId(dto.getId());
		userType.setName(dto.getName());
		userType.setDescription(dto.getDescription());
		
		return userType;
	}

}
