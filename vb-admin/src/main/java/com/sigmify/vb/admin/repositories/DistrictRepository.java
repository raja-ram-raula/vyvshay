package com.sigmify.vb.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigmify.vb.admin.entity.metadata.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {
	public District findByName(String name);
	@Query("select name from District where description=?1")
	public String findNameByDescription(String decsription);
	public District findByDescription(String name);

}
