package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.User;
import com.sigmify.vb.admin.entity.UserIdProof;

public interface UserIdProofRepository extends JpaRepository<UserIdProof, Long> {
	public UserIdProof findByUan(String uan);
	public UserIdProof findByUser(User user);

}
