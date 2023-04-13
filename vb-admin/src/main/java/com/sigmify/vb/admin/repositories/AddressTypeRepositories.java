package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.metadata.AddressType;

public interface AddressTypeRepositories extends JpaRepository<AddressType, Integer> {
	public AddressType findByName(String name);

}
