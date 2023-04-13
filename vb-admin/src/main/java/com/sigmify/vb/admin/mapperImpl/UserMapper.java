package com.sigmify.vb.admin.mapperImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.UserDTO;
import com.sigmify.vb.admin.entity.User;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;



@Component
public class UserMapper implements Mapper<User, UserDTO> {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserMapper.class);
	
	UserTypeMapper typeMapper = new UserTypeMapper();

	@Override
	public UserDTO convert(User user) {
		//converting User entity to UserDTO
		UserDTO userDto=new UserDTO();
		userDto.setId(user.getId());
		userDto.setUserName(user.getUserName());
		userDto.setfName(user.getfName());
		userDto.setlName(user.getlName());
		userDto.setPhoto(user.getPhoto());
		userDto.setIsAgent(user.getAgent());
		userDto.setActive(true);
		userDto.setUsertype(typeMapper.convert(user.getUsertype()));
		userDto.setCreateDate(user.getCreateDate());
		userDto.setLastUpdateDate(user.getLastUpdateDate());
		
		return userDto;
	}
	
	@Override
	public User toEntity(UserDTO userDto) throws AdminException {
		//converting UserDTO to user entity
		if(userDto!=null) {
				//InputStream fis=new FileInputStream(dto.getPhoto());
				//byte[] photoContent=new byte[fis.available()];
				//fis.read(photoContent);
				User user=new User();
			    user.setId(userDto.getId());
			    user.setUserName(userDto.getUserName());
			    user.setfName(userDto.getfName());
			    user.setlName(userDto.getlName());
			    user.setPhoto(userDto.getPhoto());
			    user.setAgent(userDto.getIsAgent());
			    user.setUserType(typeMapper.toEntity(userDto.getUsertype()));
			    user.setCreateDate(LocalDateTime.now());
			    user.setLastUpdateDate(LocalDateTime.now());
			    user.setActive(true);
			    user.setLastUpdateBy(userDto.getLastUpdateBy());
	
				return user;
		}
		else {
			LOGGER.error("user information is empty for-->{}",userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR,"Insufficient user information");
		}
	}

}
