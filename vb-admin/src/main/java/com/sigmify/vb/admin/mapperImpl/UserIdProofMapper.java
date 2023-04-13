package com.sigmify.vb.admin.mapperImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.ProofTypeDTO;
import com.sigmify.vb.admin.dto.UserIdProofDTO;
import com.sigmify.vb.admin.entity.UserIdProof;
import com.sigmify.vb.admin.entity.metadata.ProofType;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.mapper.Mapper;
import com.sigmify.vb.admin.repositories.UserIdProofRepository;
import com.sigmify.vb.admin.repositories.UserProofTypeRepository;

@Component
public class UserIdProofMapper implements Mapper<UserIdProof,UserIdProofDTO> {
	@Autowired
	private UserIdProofRepository userIdRepo;
	
	@Autowired
	private UserProofTypeRepository proofTypeRepo;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserIdProofMapper.class);

	  @Override
	  public UserIdProofDTO convert(UserIdProof userProof) {
		  //converting UserIdProof entity to UserIdProofDTO
		    UserIdProofDTO uidproofDto=new UserIdProofDTO();
		    uidproofDto.setId(userProof.getId());
		    uidproofDto.setUserId(userProof.getUser().getId());
		    uidproofDto.setCreatedBy(userProof.getCreatedBy().getId());
		    uidproofDto.setLastUpdateBy(userProof.getLastUpdateBy().getId());
		    uidproofDto.setUan(userProof.getUan());
		    uidproofDto.setCreationDate(userProof.getCreationDate());
		    uidproofDto.setLastUpdateDate(userProof.getLastUpdateDate());

		   // uidproofDto.setImagePath(Base64.getEncoder().encodeToString(userProof.getImagePath()));
		    ProofTypeDTO proofTypeDTO = new ProofTypeDTO();
		    proofTypeDTO.setId(userProof.getProofType().getId());
		    proofTypeDTO.setName(userProof.getProofType().getName());
		    proofTypeDTO.setDescription(userProof.getProofType().getDescription());
	        
		    uidproofDto.setProofType(proofTypeDTO);
		    return uidproofDto;
	  }

	  @Override
	  public UserIdProof toEntity(UserIdProofDTO userIdProofDto) throws AdminException {
		  //converting UserIdProofDTO to UserIdProof entity
		  if(userIdProofDto==null) {
			  LOGGER.error("user id proof information is empty for-->{}",userIdProofDto);
			  throw new AdminException(ErrorCode.REQUEST_ERROR,"Insufficient user id proof  information");
		  }
		  UserIdProof userIdProof=null;
		  if(userIdProofDto.getId()!=null) {
			  //getting UserIdProof entity by id from repository
			  Optional<UserIdProof> opt = userIdRepo.findById(userIdProofDto.getId());
			  if(opt.isPresent()) {
				  userIdProof=opt.get();
			  }
			  else {
				  LOGGER.error("No user id proof found for given id -->{} ",userIdProofDto.getId());
					throw new AdminException(ErrorCode.ENTITY_NOT_FOUND,"A valid user id proof id is mandatory");
			  }
		  }
		  else {
			  userIdProof=new UserIdProof();
			  userIdProof.setCreationDate(LocalDateTime.now());
		  }
		  //InputStream fis=new FileInputStream(dto.getImagePath());
		  //byte[] photoContent=new byte[fis.available()];
		  //fis.read(photoContent);
	      userIdProof.setUan(userIdProofDto.getUan());
	      userIdProof.setImagePath(userIdProofDto.getImagePath());
	      ProofTypeDTO proofTypeDTO =  userIdProofDto.getProofType();
	      ProofType proofType = null;
	      if(proofTypeDTO!=null) {
	    	  proofType = proofTypeRepo.findByName(proofTypeDTO.getName());
	      }
	      userIdProof.setProofType(proofType);
	      userIdProof.setLastUpdateDate(LocalDateTime.now());
	      return userIdProof;
	  }

}