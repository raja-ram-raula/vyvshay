package com.sigmify.vb.admin.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sigmify.vb.admin.enums.ErrorCode;



/**
 * <p><b>Executive summary:</b> ErrorResponse used to send response for any incoming request to admin in case of any error while request processing . </p>
 * <p><b>OS/Hardware Dependencies:</b> N/A</p>
 * <p><b>References to any External Specifications:</b> N/A.</p>
 *  @author Srikanta 
 *  
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = -7979820204581071309L;

	private LocalDateTime timestamp;
	private Integer status;
	private ErrorCode errorCode;
	private String error;
	@JsonIgnore
	private Class exception;
	private String message;
	private String path;
	@JsonIgnore
	@XmlTransient
	private HttpStatus httpStatus;
	private String data;

	public ErrorResponse() {
	}

	public ErrorResponse(Exception exception, String path) {
		String message = exception.getMessage() == null ? "No message available"
				: exception.getMessage();
		ErrorCode errorCode;
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		if (exception instanceof AdminException) {
			final AdminException adminException = (AdminException) exception;
			errorCode = adminException.getErrorCode();
			httpStatus = errorCode.getHttpStatus();
			this.setData(adminException.getData());
			this.setErrorCode(adminException.getErrorCode());
		}
		this.setTimestamp(LocalDateTime.now());
		this.setStatus(httpStatus.value());
		this.setException(exception.getClass());
		this.setError(httpStatus.getReasonPhrase());
		this.setMessage(message);
		this.setPath(path);
		this.setHttpStatus(httpStatus);
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Class getException() {
		return exception;
	}

	public void setException(Class exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ErrorResponse{" + "timestamp=" + timestamp + ", status=" + status
				+ ", errorCode=" + errorCode + ", error='" + error + '\'' + ", exception="
				+ exception + ", message='" + message + '\'' + ", path='" + path + '\''
				+ ", httpStatus=" + httpStatus + ", data='" + data + '\'' + '}';
	}
}
