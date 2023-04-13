package com.sigmify.vb.admin.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigmify.vb.admin.entity.Address;
import com.sigmify.vb.admin.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
	public List<Address> getAddressByUser(User user);

}
