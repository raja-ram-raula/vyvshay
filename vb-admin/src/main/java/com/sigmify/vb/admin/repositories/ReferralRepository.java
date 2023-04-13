package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.Referral;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
	public Referral findByReferralCode(String code);
}
