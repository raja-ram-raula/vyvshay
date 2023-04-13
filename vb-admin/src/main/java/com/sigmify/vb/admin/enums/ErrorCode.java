package com.sigmify.vb.admin.enums;

import org.springframework.http.HttpStatus;



/**
 * <p><b>Executive summary:</b> Enumeration of different HTTP status codes mapped with admin application error message . </p>
 *  @author srikanta
 */
public enum ErrorCode {
    GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Generic server error"),
    REQUEST_ERROR(HttpStatus.BAD_REQUEST, "Error in provided request"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Entity not found"),
    SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Serialization error"),
    DESERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Deserialization error"),
    STALE_DATA_ERROR(HttpStatus.CONFLICT, "Stale data - client has an older version of data compared to the server's"),
    INVALID_DURATION_FORMAT(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid duration format"),
	XML_PARSING_ERROR(HttpStatus.BAD_REQUEST, "Xml parsing failed"),
	PDF_PROCESSING_ERROR(HttpStatus.BAD_REQUEST, "Error Processing PDF"),
	ENTITY_ALREADY_PERSIST(HttpStatus.CREATED,"Entity already created"),
	FILE_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error in file I/O operation"),
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "File not found in the location"),
	MAPPING_ENTITY_DTO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Entity to DTO Mapping Error"),
	MAPPING_DTO_TO_ENTITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"DTO to Entity Mapping Error"),
    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "Error while trying to upload a file"),
    BOOKING_INTEGERATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error while trying to contact Booking"),
    PAY_INTEGRATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error while trying to contact PAY"),
    NOT_IMPLEMENTED_ERROR(HttpStatus.NOT_IMPLEMENTED, "Requested functionality not implemented yet"),
	REPRESENTATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Representation was not found");
    

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
