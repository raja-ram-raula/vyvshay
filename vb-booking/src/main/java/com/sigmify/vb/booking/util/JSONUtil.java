package com.sigmify.vb.booking.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigmify.vb.booking.enums.ErrorCode;
import com.sigmify.vb.booking.exception.BookingException;

/**
 * <p><b>Executive summary:</b>Utility class, used to handle serialization/deserialization of objects to/from JSON,
 * respectively.</p>
 *
 * @author srikanta
 */
@Component
public class JSONUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class);

	@Autowired
	private ObjectMapper objectMapper;

	/**
     * <p><b>Executive Summary:</b>Serialize the provided object to a JSON string..</p>
	 * @param sourceObject the object to serialize to JSON
	 * @return the provided object in a JSON {@link String}
	 * @throws AdminException in case of an error while trying to serialize the provided object
	 */
	public   String serialize(Object sourceObject) throws BookingException {
		String result;
		try {
			result = objectMapper.writeValueAsString(sourceObject);
		}
		catch (JsonProcessingException jpe) {
			LOGGER.error("Error while trying to serialize to JSON {}", jpe);
			throw new BookingException(ErrorCode.SERIALIZATION_ERROR, "Serialization error");
		}
		return result;
	}

	/**
     * <p><b>Executive Summary:</b>Deserialize the provided byte array (representing a JSON string) to the designated
     * target class.</p>
     
	 * @param bytes {@code byte[]} to deserialize
	 * @param targetClass {@link Class} the class to deserialize the provided byte array to
	 * @param <T> the generic indicating the return type of the deserialization
	 * @return an object representation of the provided byte array
	 * @throws AdminException in case of an error while trying to deserialize the provided
	 */
	public <T> T deserialize(byte[] bytes, Class<T> targetClass) throws BookingException {
		T result;
		try {
			result = objectMapper.readValue(bytes, targetClass);
		}
		catch (IOException ioe) {
			LOGGER.error("Error while trying to deserialize from JSON {}", ioe);
			throw new BookingException(ErrorCode.DESERIALIZATION_ERROR,"Deserialization error");
		}
		return result;
	}

	/**
     * <p><b>Executive Summary:</b>Deserialize the provided JSON string to the designated target class.</p>
	 * @param jsonString the JSON {@code String} to deserialize
	 * @param targetClass {@link Class} the class to deserialize the provided JSON string to
	 * @param <T> the generic indicating the return type of the deserialization
	 * @return an object representation of the provided JSON {@link String}
	 * @throws AdminException in case of an error while trying to deserialize the provided
	 */
	public <T> T deserialize(String jsonString, Class<T> targetClass)
			throws BookingException {
		if (jsonString == null) {
			throw new BookingException(ErrorCode.DESERIALIZATION_ERROR,
					"Trying to deserialize a null string");
		}
		return deserialize(jsonString.getBytes(), targetClass);
	}

	/**
	 * <p><b>Executive Summary:</b>Read the bytes from {@code jsonFilePath} and pass it to the method to deserialize.</p>
	 * <p><b>Cause of Exceptions:</b> Throws AdminException if any.</p>
	 * <p><b>Security Constraints:</b> N/A</p>
	 * @param <T> the generic type
	 * @param jsonFilePath {@link File}
	 * @param targetClass {@link Class}
	 * @return the generic indicating the return type of the deserialization
	 * @throws AdminException if any
	 */
	public <T> T deserialize(File jsonFilePath, Class<T> targetClass)
			throws BookingException {
		if (jsonFilePath == null || !jsonFilePath.exists()) {
			throw new BookingException(ErrorCode.DESERIALIZATION_ERROR,
					"Trying to deserialize a invalid JSON file");
		}
		byte[] jsonFileBytes = null;
		try {
			jsonFileBytes = FileUtils.readFileToByteArray(jsonFilePath);
		}
		catch (IOException e) {
			LOGGER.error("Error while reading file bytes of JSON {}", e);
			throw new BookingException(ErrorCode.FILE_SYSTEM_ERROR,
					"Error reading JSON File bytes");
		}
		return deserialize(jsonFileBytes, targetClass);
	}
	
	/**
     * <p><b>Executive Summary:</b>used to deserialize invalid fields.</p>
	 * @param inValidFieldsJson {@link Map}
	 * @return {@link Map} 
	 * @throws AdminException if any
	 */
	public Map<String, Boolean> deserializeInValidFields(String inValidFieldsJson) throws BookingException {
		
		if (inValidFieldsJson == null) {
			throw new BookingException(ErrorCode.DESERIALIZATION_ERROR,"Trying to deserialize a null string");
		}
		ObjectMapper objectMapperForMap = new ObjectMapper();
		try {

			TypeReference<Map<String, Boolean>>	typeReference =new TypeReference<Map<String, Boolean>>(){};
			return objectMapperForMap.readValue(inValidFieldsJson, typeReference);

		}
		catch (IOException ioe) {
			LOGGER.error("Error while trying to deserialize from JSON {}", ioe);
			throw new BookingException(ErrorCode.DESERIALIZATION_ERROR,"Deserialization error");
		}
	}


	
}
