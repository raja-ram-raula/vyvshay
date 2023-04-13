package com.sigmify.vb.admin.mapperImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.AccountDetailsDTO;
import com.sigmify.vb.admin.entity.AccountDetails;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;
import com.sigmify.vb.admin.repositories.AccDetailsRepository;

@Component
public class AccountDetailsMapper implements Mapper<AccountDetails, AccountDetailsDTO> {
	@Autowired
	private AccDetailsRepository accDetailsRepo;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AccountDetailsMapper.class);

  @Override
  public AccountDetailsDTO convert(AccountDetails acdetails) {
	  //converting AccountDetails entity into AccountDetailsDTO
    AccountDetailsDTO acdetailsDto=new AccountDetailsDTO();
    acdetailsDto.setId(acdetails.getId());
    acdetailsDto.setUserId(acdetails.getUser().getId());
    acdetailsDto.setAccountNumber(acdetails.getAccountNumber());
    acdetailsDto.setIfscCode(acdetails.getIfscCode());
    acdetailsDto.setAccountHolderName(acdetails.getAccountHolderName());
    acdetailsDto.setTermsConditonAgreed(acdetails.isTermsConditonAgreed());
    acdetailsDto.setCreationDate(acdetails.getCreationDate());
    acdetailsDto.setLastUpdateDate(acdetails.getLastUpdateDate());
    acdetailsDto.setCreatedBy(acdetails.getCreatedBy().getId());
    acdetailsDto.setLastUpdateBy(acdetails.getLastUpdateBy().getId());
   // acdetailsDto.setTermsAndConditionsId(acdetails.getTermsAndConditionsId().getId());
    return acdetailsDto;
  }

  @Override
  public AccountDetails toEntity(AccountDetailsDTO accDetailsDto) throws AdminException {
	  //converting AccountDetailsDTO into AccountDetails entity
	  if(accDetailsDto==null) {
		  LOGGER.error("Account details information is empty for-->{}",accDetailsDto);
		  throw new AdminException(ErrorCode.REQUEST_ERROR,"Insufficient Account details information");
	  }
	  AccountDetails accDetails=null;
	  if(accDetailsDto.getId()!=null) {
		  //getting AccountDetails entity by id from repository
		  Optional<AccountDetails> opt = accDetailsRepo.findById(accDetailsDto.getId());
		  if(opt.isPresent()) {
			  accDetails=opt.get();
		  }
		  else {
			  LOGGER.error("No user account details found for given id -->{} ",accDetailsDto.getId());
				throw new AdminException(ErrorCode.ENTITY_NOT_FOUND,"A valid Account details id is mandatory");
		  }
	  }
	  else {
		  accDetails=new AccountDetails();
		  accDetails.setCreationDate(LocalDateTime.now());
	  }
      accDetails.setAccountNumber(accDetailsDto.getAccountNumber());
      accDetails.setIfscCode(accDetailsDto.getIfscCode());
      accDetails.setAccountHolderName(accDetailsDto.getAccountHolderName());
      accDetails.setLastUpdateDate(LocalDateTime.now());
      accDetails.setTermsConditonAgreed(accDetailsDto.isTermsConditonAgreed());
      accDetails.setTermsConditonAgreed(accDetailsDto.isTermsConditonAgreed());
      //acdetails.setTermsAndConditionsId(dto.getTermsAndConditionsId());
      return accDetails;
  }

}