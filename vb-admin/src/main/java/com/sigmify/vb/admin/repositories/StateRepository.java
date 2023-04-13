package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.metadata.State;

public interface StateRepository extends JpaRepository<State, Integer> {
	public State findByName(String name);
	public State findByDescription(String name);

}
