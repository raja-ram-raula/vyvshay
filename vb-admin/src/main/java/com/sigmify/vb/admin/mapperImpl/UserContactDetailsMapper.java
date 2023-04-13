package com.sigmify.vb.admin.mapperImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.UserContactDetailsDTO;
import com.sigmify.vb.admin.entity.UserContactDetails;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;
import com.sigmify.vb.admin.repositories.UserContactDetailsRepository;

@Component
public class UserContactDetailsMapper implements Mapper<UserContactDetails, UserContactDetailsDTO> {
    @Autowired
	private UserContactDetailsRepository userContactRepo;
    
    private static final Logger LOGGER=LoggerFactory.getLogger(UserContactDetailsMapper.class);
	
	  @Override
	  public UserContactDetailsDTO convert(UserContactDetails ucdetails) {
		//converting UserocntactDetails entity into UserContactDetailsDTO
	    UserContactDetailsDTO ucDto=new UserContactDetailsDTO();
	    ucDto.setId(ucdetails.getId());
	    ucDto.seteMail(ucdetails.geteMail());
	    ucDto.setPhoneNo(ucdetails.getPhoneNo());
	    ucDto.setCreateDate(ucdetails.getCreateDate());
	    ucDto.setLastUpdateDate(ucdetails.getLastUpdateDate());
	    ucDto.setUserId(ucdetails.getUser().getId());
	    ucDto.setLastUpdateBy(ucdetails.getLastUpdateBy().getId());
	    ucDto.setFbToken(ucdetails.getFbToken());
	    ucDto.setDeviceId(ucdetails.getDeviceId());
	    return ucDto;
	  }

	  @Override
	  public UserContactDetails toEntity(UserContactDetailsDTO userContactDto) throws AdminException {
		 //converting UserContactDetailsDTO into UserContactDetails entity
		  if(userContactDto==null) {
			  LOGGER.error("User contact details information is empty for-->{}",userContactDto);
			  throw new AdminException(ErrorCode.REQUEST_ERROR,"Insufficient user contact details information");
		  }
		  UserContactDetails contactDetails=null;
		  if(userContactDto.getId()!=null) {
			  //getting UserContactDetails entity by id from repository
			  Optional<UserContactDetails> opt=userContactRepo.findById(userContactDto.getId());
				if(opt.isPresent()) {
					contactDetails=opt.get();
				}
				else {
					LOGGER.error("No user contact details found for given id -->{} ",userContactDto.getId());
					throw new AdminException(ErrorCode.ENTITY_NOT_FOUND,"A valid user contact id is mandatory");
				}
		  }
		  else {
			  contactDetails= new UserContactDetails();
			  contactDetails.setCreateDate(LocalDateTime.now());
		  }
			contactDetails.seteMail(userContactDto.geteMail());
			contactDetails.setPhoneNo(userContactDto.getPhoneNo());
			contactDetails.setLastUpdateDate(LocalDateTime.now());
			contactDetails.setFbToken(userContactDto.getFbToken());
			contactDetails.setDeviceId(userContactDto.getDeviceId());
			return contactDetails;

	                
	  }

}
