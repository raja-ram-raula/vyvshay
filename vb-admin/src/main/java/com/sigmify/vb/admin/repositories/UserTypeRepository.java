package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.metadata.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
	public UserType findByName(String name);
}
