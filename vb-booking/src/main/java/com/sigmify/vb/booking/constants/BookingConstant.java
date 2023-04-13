package com.sigmify.vb.booking.constants;

import java.io.File;

public class BookingConstant {

	//For DEV Env
	public static final String ADMIN_APPLICATION_BASEURL = "http://164.52.212.42:8087/vb-admin/";
	public static String FILE_PATH_BOOKING=File.separator+"root"+File.separator+"sigmify";
	//For Test Env
	//public static final String ADMIN_APPLICATION_BASEURL = "http://164.52.208.41:8084/vb-admin/";
	//public static String FILE_PATH_BOOKING=File.separator+"root"+File.separator+"VB-BookingTest";
	//public static final String ADMIN_APPLICATION_BASEURL = "http://127.0.0.1:8080/vb-admin/";
	public static final String SAVE_SERVICE_EXECUTOR_URL = "user/save/executor";
	public static final String UPDATE_SERVICE_EXECUTOR_URL = "user/update/executor";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String EMAIL_ID = "emailID";
	public static final String EMPTY_STRING = "";
	public static final String SUCCESS = "success";
	public static final String USER_VERIFIED = "User is verified";
	public static final String USER_NOT_VERIFIED = "User is not authentic";
	public static final boolean TRUE = Boolean.TRUE;
	public static final boolean FALSE = Boolean.FALSE;
	public static final String HEADER_PARAMETERS = "parameters";
	public static final String COMMA = ",";
	public static final String SEMICOLON = ";";
	public static final String DISTANCEMATRIX_BASE_API = "https://apis.mapmyindia.com/advancedmaps/v1/";
	public static final String DEPT_TIME = "?dep_time=1531543500";
	public static final String DISTANCEMATRIX_PROFILE = "driving/";
	public static final String DISTANCEMATRIX_RESOURCES = "distance_matrix/";
	public static final String DISTANCEMATRIX_API_KEY = "qg2gbmtlk3g6jwla8uui8vf9ol9sxyhn/";
	public static final double RADIOUS1= 3000;
	public static final double RADIOUS2 = 5000;
	
	public static final String SEND_OTP_BASE_API = "https://api.msg91.com/api/v5/otp/";
	public static final String SEND_OTP_BASE_TEMPLATE="?template_id=";
	public static final String MOBILE_NO="&mobile=";
	public static final String SEND_OTP_AUTHKEY="&authkey=";
	public static final String IND_CODE="91";
	public static final String SEND_OTP_AUTH_KEY = "362351Aw39Lf0Zt60c43e55P1";
	public static final String SERVICE_START_SEND_OTP_TEMPLATE_ID = "60f12a02200dfd4b341bbcb2";
	public static final String SERVICE_COMPLETE_SEND_OTP_TEMPLATE_ID = "60f173e5dba252575b43c7d5";
	public static final String OTP_EXPIRY_TIME="15";
	public static final String OTP_EXPIRY="&otp_expiry=";
	public static final String VERIFY_OTP="https://api.msg91.com/api/v5/otp/verify?authkey=";
	public static final String SE_OTP="&otp=";
	
}
