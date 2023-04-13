package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.User;
import com.sigmify.vb.admin.entity.UserPassword;

public interface PasswordRepository extends JpaRepository<UserPassword, Long> {
	
	public UserPassword getUserPasswordByUser(User id);

}
