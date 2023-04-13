package com.sigmify.vb.admin.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;

import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.util.JSONUtil;


/**
 * Adapter for exceptions .
 * 
 * @author Srikanta
 * 
 */
@Component
public class ExceptionAdapter {
  @Autowired
  private JSONUtil jSONUtil;
  private static final String EXCEPTION_FROM_BOOKING = "Exception from Booking : ";
  
	
  
 /**
  * Convert to Admin exception and throw .
  *
  * @param rce the  instance of {@link RestClientResponseException}
  * @param customizedErrorMessage the customized error message
  * @throws AdminException the admin exception
  */
 public void convertToAdminExceptionAndThrow(RestClientResponseException rce,String customizedErrorMessage) throws AdminException {
		  ErrorResponse errorResponse = jSONUtil.deserialize(rce.getResponseBodyAsByteArray(), ErrorResponse.class);
		  //checking error code  for admin specific ,generic error message  
		  if(rce.getRawStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR.value())
			  throw  new AdminException(ErrorCode.BOOKING_INTEGERATION_ERROR);
		  String message=EXCEPTION_FROM_BOOKING+errorResponse.getMessage();
		  if(!StringUtils.hasText(customizedErrorMessage)) {
			  message= customizedErrorMessage;
		  }
	      throw new  AdminException(errorResponse.getErrorCode(),message); 	  
	  }

}
