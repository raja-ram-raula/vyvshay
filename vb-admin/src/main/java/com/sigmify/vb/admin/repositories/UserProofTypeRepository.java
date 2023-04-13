package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.metadata.ProofType;

public interface UserProofTypeRepository extends JpaRepository<ProofType, Integer> {
	public ProofType findByName(String name);

}
