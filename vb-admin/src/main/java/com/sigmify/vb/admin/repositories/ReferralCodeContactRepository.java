package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.Referral;
import com.sigmify.vb.admin.entity.ReferralCodeContact;

public interface ReferralCodeContactRepository extends JpaRepository<ReferralCodeContact, Long> {
	public ReferralCodeContact findByMobileNumber(String phone);
	//@Query("from ReferralCodeContact where")
	public ReferralCodeContact findByMobileNumberAndReferralCode(String phone,Referral code);
}
