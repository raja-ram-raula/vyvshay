package com.sigmify.vb.admin.mapperImpl;

import org.springframework.stereotype.Component;

import com.sigmify.vb.admin.dto.ReferralDTO;
import com.sigmify.vb.admin.entity.Referral;
import com.sigmify.vb.admin.mapper.Mapper;

@Component
public class ReferralMapper implements Mapper<Referral, ReferralDTO> {

	@Override
	public ReferralDTO convert(Referral referral) {
		ReferralDTO referralDto=new ReferralDTO();
		referralDto.setId(referral.getId());
		referralDto.setReferralCode(referral.getReferralCode());
		referralDto.setGeneratedFor(referral.getGeneratedfor().getId());
		referralDto.setReferralPoint(referral.getReferralPoint());
		referralDto.setTotalInstalled(referral.getTotalInstalled());
		referralDto.setTotalReferred(referral.getTotalReferred());
		
		return referralDto;
	}

	@Override
	public Referral toEntity(ReferralDTO referralDto) throws Exception {
		Referral referral=new Referral();
		referral.setId(referralDto.getId());
		referral.setReferralCode(referralDto.getReferralCode());
		referral.setReferralPoint(referralDto.getReferralPoint());
		referral.setTotalInstalled(referralDto.getTotalInstalled());
		referral.setTotalReferred(referralDto.getTotalReferred());
		return referral;
	}

}
