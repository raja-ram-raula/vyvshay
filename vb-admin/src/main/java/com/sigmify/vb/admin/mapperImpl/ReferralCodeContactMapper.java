package com.sigmify.vb.admin.mapperImpl;

import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.ReferralCodeContactDTO;
import com.sigmify.vb.admin.entity.ReferralCodeContact;
import com.sigmify.vb.admin.mapper.Mapper;

@Component
public class ReferralCodeContactMapper implements Mapper<ReferralCodeContact, ReferralCodeContactDTO> {
	
	
	@Override
	public ReferralCodeContactDTO convert(ReferralCodeContact refercodeContact) {
		ReferralCodeContactDTO referCodeContactDto=new ReferralCodeContactDTO();
		referCodeContactDto.setId(refercodeContact.getId());
		referCodeContactDto.setMobileNumber(refercodeContact.getMobileNumber());
		referCodeContactDto.setMailId(refercodeContact.getMailId());
		referCodeContactDto.setMessageSend(refercodeContact.isMessageSend());
		referCodeContactDto.setMailSend(refercodeContact.isMailSend());
		referCodeContactDto.setAppInstalled(refercodeContact.isAppInstalled());
		referCodeContactDto.setMessageSentTime(refercodeContact.getMessageSentTime());
		referCodeContactDto.setLastUpdateDate(refercodeContact.getLastUpdateDate());
		return referCodeContactDto;
	}

	@Override
	public ReferralCodeContact toEntity(ReferralCodeContactDTO dto) throws Exception {
		ReferralCodeContact referCodeContact=new ReferralCodeContact();
		referCodeContact.setId(dto.getId());
		referCodeContact.setMobileNumber(dto.getMobileNumber());
		referCodeContact.setMailId(dto.getMailId());
		referCodeContact.setMessageSend(dto.isMessageSend());
		referCodeContact.setMailSend(dto.isMailSend());
		referCodeContact.setAppInstalled(dto.isAppInstalled());
		referCodeContact.setMessageSentTime(dto.getMessageSentTime());
		referCodeContact.setLastUpdateDate(dto.getLastUpdateDate());
		return referCodeContact;
	}

}
