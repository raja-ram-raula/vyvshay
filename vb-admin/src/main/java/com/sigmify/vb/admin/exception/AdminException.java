package com.sigmify.vb.admin.exception;

import com.sigmify.vb.admin.enums.ErrorCode;


/**
 * <p><b>Executive summary:</b> AdminException is generic exception class for admin application   . </p>
 *  @author Srikanta
 */
public class AdminException extends Exception {
	private static final long serialVersionUID = -5356979569234744412L;

	private final ErrorCode errorCode;
	private  String data;

	public AdminException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public AdminException(ErrorCode errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public AdminException(ErrorCode errorCode, String errorMessage, String data) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.data = data;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getData() {
		return data;
	}
}
