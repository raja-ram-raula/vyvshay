package com.sigmify.vb.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sigmify.vb.admin.util.PathUtil;



/**
 * <p><b>Executive summary:</b> Registers an {@link ExceptionHandler} to
 * handle all the exceptions occurred in end points.</p>
 * <p>
 * The {@link AdminExceptionHandler} get the exception and returns
 * as response a {@link ErrorResponse} object and HTTP status
 * as specified in the ErrorResponse object
 *  @author Srikanta
 */
@ControllerAdvice
public class AdminExceptionHandler {
	private static final Logger Logger = LoggerFactory.getLogger(AdminExceptionHandler.class);

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ErrorResponse> handleException(Exception exception,
			HttpServletRequest httpServletRequest) {
		Logger.debug("Global error handler..", exception);
		String path = httpServletRequest != null
				? PathUtil.getPathBeforeProxy(httpServletRequest) : null;
		ErrorResponse errorResponse = new ErrorResponse(exception, path);
		
		 
		return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
		
		
	}
}
