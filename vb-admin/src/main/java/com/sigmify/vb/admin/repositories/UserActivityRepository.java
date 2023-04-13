package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.UserActivity;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long>{

}
