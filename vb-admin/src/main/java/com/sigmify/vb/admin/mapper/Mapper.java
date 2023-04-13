package com.sigmify.vb.admin.mapper;

import org.springframework.core.convert.converter.Converter;

// TODO: Auto-generated Javadoc
/**
 * The Interface Mapper for converting one to another type instance .
 *
 * @param <E> the element type
 * @param <DTO> the generic type
 */
public interface Mapper<E, DTO> extends Converter<E, DTO>{
	
 /**
  * To entity.
  *
  * @param dto the dto
  * @return the e
 * @throws Exception 
  */
 E	toEntity(DTO dto) throws Exception;
	
 
 

}
