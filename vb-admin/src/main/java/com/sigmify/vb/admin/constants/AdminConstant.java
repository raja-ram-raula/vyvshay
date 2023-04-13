package com.sigmify.vb.admin.constants;

import java.io.File;

public class AdminConstant {
 
	//public static final String BOOKING_APPLICATION_BASEURL = "http://127.0.0.1:8089/vb-booking/";
	//For UAT url and file path
	//public static final String BOOKING_APPLICATION_BASEURL = "http://164.52.208.41:8085/vb-booking/";
	//public static String FILE_PATH_ADMIN=File.separator+"root"+File.separator+"VB-AdminTest";
	
	//For DEV url and file path
    public static final String BOOKING_APPLICATION_BASEURL = "http://164.52.212.42:8088/vb-booking/";
	public static String FILE_PATH_ADMIN=File.separator+"root"+File.separator+"sigmify";
	public static final String RECREATE_ORDER="order/_recreateOrder";
	public static final String LOGIN_OTP="OTP";
	public static final String LOGIN_PWD="PWD";
	public static final String REGISTRAION_TYPE="REGISTRATION_DETAILS";
	public static final Integer REFERRAL_POINT=10;
	public static final String USER = "username";
	public static final String PASSWORD = "password";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String EMAIL_ID = "emailID";
	public static final String EMPTY_STRING = "";
	public static final String SUCCESS = "success";
	public static final String USER_VERIFIED = "User is verified";
	public static final String USER_NOT_VERIFIED = "User is not authentic";
	public static final String PHONE = "phone";
	public static final String OTP = "otp";
	public static final boolean TRUE = Boolean.TRUE;
	public static final boolean FALSE = Boolean.FALSE;
	public static final String SEND_OTP_BASE_API = "https://api.msg91.com/api/v5/otp/";
	public static final String SEND_OTP_BASE_TEMPLATE="?template_id=";
	public static final String MOBILE_NO="&mobile=";
	public static final String SEND_OTP_AUTHKEY="&authkey=";
	public static final String IND_CODE="91";
	public static final String SE_SEND_OTP_TEMPLATE_ID = "60ee70e96f7ee02c1e690c72";
	public static final String USER_REGISTRATION_OTP_TEMPLATE_ID = "60ee9dab9ca51943ff524109";
	public static final String SO_REGISTRATION_OTP_TEMPLATE_ID = "60ef06cc8b79760db659d339";
	public static final String USER_LOGIN_OTP_TEMPLATE_ID ="60eeff4d4f24470047208a47";
	public static final String SO_LOGIN_OTP_TEMPLATE_ID ="60ef0dc863a15f6bca0298c3";
	public static final String USER_RESETPWD_SEND_OTP_TEMPLATE_ID ="60f0f75870d947388e382c57"; 
	public static final String SO_RESETPWD_SEND_OTP_TEMPLATE_ID ="60f120302f23870af92ae93a";
	public static final String SEND_OTP_AUTH_KEY = "362351Aw39Lf0Zt60c43e55P1";
	public static final String OTP_EXPIRY_TIME="15";
	public static final String OTP_EXPIRY="&otp_expiry=";
	public static final String VERIFY_OTP="https://api.msg91.com/api/v5/otp/verify?authkey=";
	public static final String SE_OTP="&otp=";	
}
