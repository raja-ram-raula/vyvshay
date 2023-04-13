package com.sigmify.vb.admin.mapperImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.UserPasswordDTO;
import com.sigmify.vb.admin.entity.UserPassword;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;
import com.sigmify.vb.admin.repositories.PasswordRepository;

@Component
public class UserPasswordMapper implements Mapper<UserPassword, UserPasswordDTO> {

	@Autowired
	private PasswordRepository passrepo;
	
	  private static final Logger LOGGER=LoggerFactory.getLogger(UserPasswordMapper.class);
	
	  @Override
	  public UserPasswordDTO convert(UserPassword userpwd) {
		  //converting UserPassword entity into UserPasswordDTO
	    UserPasswordDTO userpwdDto=new UserPasswordDTO();
	    userpwdDto.setId(userpwd.getId());
	    userpwdDto.setUserId(userpwd.getUser().getId());
	    userpwdDto.setPassword(userpwd.getPassword());
	    userpwdDto.setCreationDate(userpwd.getCreationDate());
	    userpwdDto.setLastUpdateDate(userpwd.getLastUpdateDate());
	    userpwdDto.setLastUpdateBy(userpwd.getLastUpdateBy().getId());
	    return userpwdDto;
	  }

	  @Override
	  public UserPassword toEntity(UserPasswordDTO userPasswordDto) throws AdminException {
		  //converting UserPasswordDTO into UserPassword entity
		  if(userPasswordDto==null) {
			  LOGGER.error("User password information is empty for-->{}",userPasswordDto);
			  throw new AdminException(ErrorCode.REQUEST_ERROR,"Insufficient user password information");
		  }
		  UserPassword ucPwd=null;
		  if(userPasswordDto.getId()!=null) {
			  //getting UserPassword entity by id from repository
			  Optional<UserPassword> opt = passrepo.findById(userPasswordDto.getId());
			  if(opt.isPresent()) {
				  ucPwd=opt.get();
			  }
			  else {
				  LOGGER.error("No user password found for given id -->{} ",userPasswordDto.getId());
					throw new AdminException(ErrorCode.ENTITY_NOT_FOUND,"A valid user password id is mandatory");
			  }
		  }
		  else {
			  ucPwd=new UserPassword();
			  ucPwd.setCreationDate(LocalDateTime.now());
		  }
			  ucPwd.setPassword(userPasswordDto.getPassword());
			  ucPwd.setLastUpdateDate(LocalDateTime.now());
			  ucPwd.setExpired(true);
			  return ucPwd;
	    
	  }

}