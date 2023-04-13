package com.sigmify.vb.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigmify.vb.booking.entity.UnitMeasurementType;

public interface UnitMeasurementTypeRepository extends JpaRepository<UnitMeasurementType,Long>{
	
	@Query("from UnitMeasurementType where isActive = true")
	List<UnitMeasurementType> getAll();

}
