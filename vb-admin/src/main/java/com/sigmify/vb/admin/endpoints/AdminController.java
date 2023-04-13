package com.sigmify.vb.admin.endpoints;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sigmify.vb.admin.constants.AdminConstant;
import com.sigmify.vb.admin.dto.AccountDetailsDTO;
import com.sigmify.vb.admin.dto.AddressDTO;
import com.sigmify.vb.admin.dto.AdminFarmerDto;
import com.sigmify.vb.admin.dto.AdminPanelDashboard;
import com.sigmify.vb.admin.dto.AdminPanelOrderStatus;
import com.sigmify.vb.admin.dto.OrderResponseDTO;
import com.sigmify.vb.admin.dto.ReferralDTO;
import com.sigmify.vb.admin.dto.UserContactDetailsDTO;
import com.sigmify.vb.admin.dto.UserDTO;
import com.sigmify.vb.admin.dto.UserIdProofDTO;
import com.sigmify.vb.admin.dto.request.AdminMetadata;
import com.sigmify.vb.admin.dto.request.ServiceExecutorSaveRequestDTO;
import com.sigmify.vb.admin.entity.NotificationDetails;
import com.sigmify.vb.admin.entity.UserActivity;
import com.sigmify.vb.admin.entity.metadata.AddressType;
import com.sigmify.vb.admin.entity.metadata.ProofType;
import com.sigmify.vb.admin.entity.metadata.UserType;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.firebase.NotificationMessage;
import com.sigmify.vb.admin.firebase.model.PushNotificationRequest;
import com.sigmify.vb.admin.firebase.model.PushNotificationResponse;
import com.sigmify.vb.admin.firebase.service.PushNotificationService;
import com.sigmify.vb.admin.serviceImpl.PhoneverificationService;
import com.sigmify.vb.admin.serviceImpl.UserServiceImpl;
import com.sigmify.vb.admin.serviceImpl.VerificationResult;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/user")
public class AdminController {

	private PushNotificationService pushNotificationService;

	public AdminController(PushNotificationService pushNotificationService) {
		this.pushNotificationService = pushNotificationService;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	PhoneverificationService phonesmsservice;
	
	RestTemplate restTemplate;
	private static Long expiryTime;
	private static final String NOTIFICATION_URL=AdminConstant.BOOKING_APPLICATION_BASEURL + "booking/notification/token";

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
    	pushNotificationService.sendPushNotificationToToken(request);
    	return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    
    @PostMapping("/notification/get")
    public ResponseEntity getNotification(@RequestBody PushNotificationRequest request) {
    	new RestTemplate().postForObject(NOTIFICATION_URL, request, String.class);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    
    @GetMapping("/notification/_get_all/{receiverId}")
    public ResponseEntity <List<NotificationDetails>> getAllReceiveNotification(@PathVariable String receiverId) {
    	List<NotificationDetails> notificationList = userService.fetchAllNotification(receiverId);
    	return ResponseEntity.status(HttpStatus.OK).body(notificationList);
    }
    
    @PostMapping("/notification/_update_status")
    public ResponseEntity getAllNotification(@RequestParam int notificationId) {
    	int status = 0;
    	String statusMessage = "";
    	int Status = userService.updateNotificationStatus(notificationId);
    	if (status==0)
    		statusMessage="Please Try again";
    		statusMessage="Notification has been read";
        return new ResponseEntity<>(statusMessage, HttpStatus.OK);
    }

	@PostMapping("/save")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDto) throws AdminException {
		UserDTO dto = userService.saveUser(userDto);
		String deviceToken = dto.getUcDto().getFbToken();
		NotificationDetails notificationRequest = new NotificationDetails();
		notificationRequest.setNotificationTitle("User Successful Registration");
		notificationRequest.setNotificationMessage(NotificationMessage.REGISTRATION_SUCCESS.replace("{NME}", dto.getlName()));
		notificationRequest.setNotificationStatus(0);
		notificationRequest.setSenderId(dto.getUserName());
		notificationRequest.setReceiverId(dto.getUserName());
		NotificationDetails bean = userService.saveNotificationDetails(notificationRequest);
		
		PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
		pushNotificationRequest.setTitle("User Successful Registration");
		pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.REGISTRATION_SUCCESS.replace("{NME}", dto.getlName()));
		pushNotificationRequest.setToken(deviceToken);
		pushNotificationRequest.setNotificationId(bean.getNotificationId());
//		RestTemplate restTemplate = new RestTemplate();
		if (dto.getUsertype().getName().equals("U__TYPE_FARMER")) {
			pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
		} else {
			new RestTemplate().postForObject(NOTIFICATION_URL, pushNotificationRequest, String.class);
			
		}
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@PostMapping("/save_user_activity")
	public ResponseEntity<UserActivity> saveUserActivity(@RequestBody UserActivity userActivity) throws AdminException {
		UserActivity activity = userService.saveUserActivity(userActivity);
		return ResponseEntity.status(HttpStatus.OK).body(activity);
	}

	
	@PutMapping("/modify/addrs")
	public ResponseEntity<List<AddressDTO>> updateNewAddress(@RequestBody UserDTO userDto) throws AdminException {

		List<AddressDTO> updateAddress = userService.updateAddress(userDto);
		return ResponseEntity.status(HttpStatus.OK).body(updateAddress);

	}

	@PutMapping("/modify/contact")
	public ResponseEntity<UserContactDetailsDTO> updateContactDetails(@RequestBody UserDTO userDto)
			throws AdminException {

		UserContactDetailsDTO contact = userService.updateContact(userDto);
		return ResponseEntity.status(HttpStatus.OK).body(contact);

	}

	@PutMapping("/modify/pwd")
	public ResponseEntity<String> updatePassword(@RequestBody UserDTO userDto) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.updatePassword(userDto));

	}
    
	@PutMapping("/modify/profile")
	public ResponseEntity<String> updateUser(@RequestBody UserDTO userDto) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto));

	}

	@PutMapping("/modify/acc")
	public ResponseEntity<AccountDetailsDTO> updateAccDetails(@RequestBody UserDTO userDto) throws AdminException {

		AccountDetailsDTO updateAccDetails = userService.updateAccDetails(userDto);
		return ResponseEntity.status(HttpStatus.OK).body(updateAccDetails);

	}

	@PutMapping("/modify/idproof")
	public ResponseEntity<UserIdProofDTO> updateIdProof(@RequestBody UserDTO userDto) throws AdminException {

		UserIdProofDTO IdProof = userService.saveIdProof(userDto);
		return ResponseEntity.status(HttpStatus.OK).body(IdProof);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		String deleteUser = userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(deleteUser);
	}

	@GetMapping("/getall")
	public ResponseEntity<Iterable<UserDTO>> getUsers() {
		Iterable<UserDTO> usersList = userService.fetchAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(usersList);

	}
	
	@ApiOperation(value = "Get Registered Farmer Screen of Admin Panel")
	@GetMapping("/getAllFarmers")
	public ResponseEntity<Iterable<AdminFarmerDto>> getFarmers() {
		Iterable<AdminFarmerDto> usersList = userService.fetchAllFarmer();
		return ResponseEntity.status(HttpStatus.OK).body(usersList);

	}
	
	@ApiOperation(value = "Get Registered Service Owner Screen of Admin Panel")
	@GetMapping("/getAllServiceOwners")
	public ResponseEntity<Iterable<AdminFarmerDto>> getServiceOwners() {
		Iterable<AdminFarmerDto> usersList = userService.fetchAllServiceOwner();
		return ResponseEntity.status(HttpStatus.OK).body(usersList);

	}
	
	@ApiOperation(value = "Get Registered Admin Users Screen of Admin Panel")
	@GetMapping("/getAllAdminUser")
	public ResponseEntity<Iterable<AdminFarmerDto>> getAdminUsers() {
		Iterable<AdminFarmerDto> usersList = userService.fetchAllAdminUser();
		return ResponseEntity.status(HttpStatus.OK).body(usersList);

	}
	
	@ApiOperation(value = "Get Service Executor Details Screen of Admin Panel")
	@GetMapping("/getAllExecutors")
	public ResponseEntity<Iterable<AdminFarmerDto>> getAllExecutor() {
		Iterable<AdminFarmerDto> usersList = userService.fetchAllExecutor();
		return ResponseEntity.status(HttpStatus.OK).body(usersList);

	}
	
	@ApiOperation(value = "Get Dashboard Screen of Admin Panel")
	@GetMapping("/getAdminDashboard")
	public ResponseEntity<AdminPanelDashboard> getAdminDashboard() {
		AdminPanelDashboard adminDashboard = userService.getAdminDashboard();
		return ResponseEntity.status(HttpStatus.OK).body(adminDashboard);

	}
	
	@ApiOperation(value = "view order status Screen for farmer of Admin Panel")
	@GetMapping("/getFarmerOrderStatus")
	public ResponseEntity<List<AdminPanelOrderStatus>> fetchFarmerOrderStatus(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getFarmerOrderStatus());
	}
	
	@ApiOperation(value = "view order status Screen for Owner of Admin Panel")
	@GetMapping("/getOwnerOrderStatus")
	public ResponseEntity<List<AdminPanelOrderStatus>> fetchOwnerOrderStatus(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getOwnerOrderStatus());
	}
	
	@ApiOperation(value = "view Referral Screen for farmer of Admin Panel")
	@GetMapping("/getAllReferralDetails")
	public ResponseEntity<List<ReferralDTO>> fetchReferralDetails(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllReferralDetails());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Long id) {
		UserDTO dto = userService.fetchSingleUser(id);
		return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);

	}

	@GetMapping("/get/adrs/{id}")
	public ResponseEntity<UserDTO> getAddress(@PathVariable Long id) {
		UserDTO userDto = userService.fetchAddress(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@GetMapping("/get/adrss/{id}")
	public ResponseEntity<List<AddressDTO>> getAddres(@PathVariable Long id) {
		UserDTO userDto = userService.fetchSingleUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto.getAddressdto());

	}

	@GetMapping("/get/contact/{id}")
	public ResponseEntity<UserDTO> getContactDetails(@PathVariable Long id) {
		UserDTO userDto = userService.fetchContactDetails(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);

	}

	@GetMapping("/get/cont/{id}")
	public ResponseEntity<UserContactDetailsDTO> getContactDetail(@PathVariable Long id) {
		UserDTO userDto = userService.fetchSingleUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto.getUcDto());

	}

	@GetMapping("/get/acc/{id}")
	public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable Long id) {
		UserDTO userDto = userService.fetchSingleUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto.getAccDetailsDto());

	}

	@GetMapping("/get/idproof/{id}")
	public ResponseEntity<UserIdProofDTO> getIdProof(@PathVariable Long id) {
		UserDTO userDto = userService.fetchSingleUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto.getUserIdProofDto());
	}

	@PostMapping(value = "/app")
	public ResponseEntity<String> applyReferralCode(@RequestParam(defaultValue = "empty") Long id,
			@RequestParam(defaultValue = "empty") String code) throws Exception {
		String status = userService.applyReferralcode(id, code);
		return ResponseEntity.status(HttpStatus.OK).body(status);
		
	}

	@PostMapping("/verify")
	public ResponseEntity<UserDTO> checkUserDetails(@RequestBody Map<String, String> inputs, @RequestParam String userType) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService
				.isAuthenticMobileNumber(inputs.get(AdminConstant.MOBILE_NUMBER), inputs.get(AdminConstant.PASSWORD), userType));

	}
   /*
	@PostMapping("/sendotp")
	public ResponseEntity<String> sendotp(@RequestBody String phone) {
		expiryTime = System.currentTimeMillis() + 60000;
		VerificationResult result = phonesmsservice.startVerification(phone);
		if (result.isValid()) {
			return ResponseEntity.status(HttpStatus.OK).body("Your OTP has been sent to your registered mobilenumber");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP failed to sent....");
	}
*/
	@GetMapping("/SEsendotp")
	public ResponseEntity<String> sesendotp(@RequestParam String phone, @RequestParam String userType) throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.serviceExeSendOtp(phone, userType));
	}

	@GetMapping("/verifySEotp")
	public ResponseEntity<UserDTO> verifySEotp(@RequestParam String phone, @RequestParam String otp, @RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.serviceExeVerifyOtp(phone, otp, userType));
	}
	
	@GetMapping("/UserRegistrationOtp")
	public ResponseEntity<String> userRegistrationOtp(@RequestParam String phone, @RequestParam String userType)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userRegistrationOtp(phone, userType));
	}
	
	@GetMapping("/verifyUserRegistrationOtp")
	public ResponseEntity<String> verifyUserRegistrationOtp(@RequestParam String phone, @RequestParam String otp, @RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.userRegistrationVerifyOtp(phone, otp, userType));
	}
	
	@GetMapping("/UserRegistrationSuccessMsg")
	public ResponseEntity<String> userRegistrationSuccessMsg(@RequestParam String phone, @RequestParam String userType)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userRegistrationSuccessMsg(phone, userType));
	}
	
	@GetMapping("/userLoginSendOtp")
	public ResponseEntity<String> userLoginSendOtp(@RequestParam String phone, @RequestParam String userType) throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userLoginSendOtp(phone, userType));
	}

	@GetMapping("/verifyUserLoginOtp")
	public ResponseEntity<UserDTO> verifyUserLoginOtp(@RequestParam String phone, @RequestParam String otp, @RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.userLoginVerifyOtp(phone, otp, userType));
	}
	
	@GetMapping("/userResetPwdSendOtp")
	public ResponseEntity<String> userResetPwdSendOtp(@RequestParam String phone)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userSendResetPasswordOtp(phone));
	}
	
	@GetMapping("/verifyUserResetPwdOtp")
	public ResponseEntity<String> verifyUserResetPwdotp(@RequestParam String phone, @RequestParam String otp, @RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.userResetPwdVerifyOtp(phone, otp, userType));
	}
	
	@GetMapping("/userBookingPendingMsg")
	public ResponseEntity<String> userBookingPendingMsg(@RequestParam String phone)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userBookingPendingMsg(phone));
	}
	
	@GetMapping("/userBookingCancelMsg")
	public ResponseEntity<String> userBookingCancelMsg(@RequestParam String phone)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userBookingCancelMsg(phone));
	}
	
	@GetMapping("/userBookingAcceptMsg")
	public ResponseEntity<String> userBookingAcceptMsg(@RequestParam String phone)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.userBookingAcceptanceMsg(phone));
	}
	
	@GetMapping("/serviceOwnerRegistrationOtp")
	public ResponseEntity<String> serviceOwnerRegistrationOtp(@RequestParam String phone, @RequestParam String userType)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.ServiceOwnerRegistrationOtp(phone, userType));
	}
	
	@GetMapping("/verifyServiceOwnerRegistrationOtp")
	public ResponseEntity<String> verifyServiceOwnerotp(@RequestParam String phone, @RequestParam String otp,@RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.serviceOwnerRegistrationVerifyOtp(phone, otp,userType));
	}
	
	@GetMapping("/serviceOwnerLoginSendOtp")
	public ResponseEntity<String> serviceOwnerLoginSendOtp(@RequestParam String phone, @RequestParam String userType)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.serviceOwnerLoginSendOtp(phone, userType));
	}

	@GetMapping("/verifyServiceOwnerLoginOtp")
	public ResponseEntity<UserDTO> verifyServiceOwnerLoginOtp(@RequestParam String phone, @RequestParam String otp, @RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.serviceOwnerLoginVerifyOtp(phone, otp, userType));
	}

	@GetMapping("/ServiceOwnerResetPwdSendOtp")
	public ResponseEntity<String> ServiceOwnerResetPwdSendOtp(@RequestParam String phone)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.ServiceOwnerSendResetPasswordOtp(phone));
	}
	
	@GetMapping("/verifyServiceOwnerResetPwdOtp")
	public ResponseEntity<String> verifySOResetPwdotp(@RequestParam String phone, @RequestParam String otp, @RequestParam String userType)
			throws AdminException, UnirestException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.ServiceOwnerResetPwdVerifyOtp(phone, otp, userType));
	}
	
	@GetMapping("/serviceOwnerRegistrationSuccessMsg")
	public ResponseEntity<String> ServiceOwnerRegistrationSuccessMsg(@RequestParam String phone, @RequestParam String userType)
			throws AdminException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.serviceOwnerRegistrationSuccessMsg(phone, userType));
	}

	@GetMapping("/referralMsg")
	public ResponseEntity<String> referralMsg(@RequestParam List<String> phone, @RequestParam String userName, @RequestParam String userType)
			throws AdminException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(userService.referralMsg(phone, userName, userType));
	}

	@PostMapping("/verifyotp")
	public ResponseEntity<UserDTO> sendotp(@RequestParam String phone, @RequestParam String code, @RequestParam String userType)
			throws AdminException {
		if (expiryTime >= System.currentTimeMillis()) {
			String number = "+91" + phone;
			VerificationResult result = phonesmsservice.checkverification(number, code);
			if (result.isValid()) {
				return ResponseEntity.status(HttpStatus.OK).body(userService.isVerify(phone, userType));
			} else {
				LOGGER.error("Verification failed due to invalid phone number or otp");
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"A valid phone number or otp is mandatory for verify");
			}
		} else {
			LOGGER.error("Verification failed due to verified time exceeded");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "otp verification time is expired");
		}
	}

	@PostMapping("/registration/verifyotp")
	public ResponseEntity<String> sendotp(@RequestBody Map<String, String> inputs) {
		if (expiryTime >= System.currentTimeMillis()) {
			VerificationResult result = phonesmsservice.checkverification(inputs.get(AdminConstant.PHONE),
					inputs.get(AdminConstant.OTP));
			if (result.isValid()) {
				return ResponseEntity.status(HttpStatus.OK).body("Your number is verified");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something is wrong or incorrect otp");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP time is expired");
		}
	}

	@GetMapping("/get/dist/code/{description}")
	public ResponseEntity<String> getDistCode(@PathVariable String description) {
		String districtCode = userService.getDistrictCode(description);
		return ResponseEntity.status(HttpStatus.OK).body(districtCode);

	}

	@GetMapping(value = "/getAllUserType", produces = { "application/json", "application/xml" })
	public ResponseEntity<List<UserType>> getAllUserTypeList() {
		List<UserType> usertypeList = userService.getUserTypeList();
		return ResponseEntity.status(HttpStatus.OK).body(usertypeList);
	}

	@GetMapping(value = "/getAllAddressType", produces = { "application/json", "application/xml" })
	public ResponseEntity<List<AddressType>> getAllAddressTypeList() {
		List<AddressType> adrstypeList = userService.getAddressTypeList();
		return ResponseEntity.status(HttpStatus.OK).body(adrstypeList);
	}

	@GetMapping("/getAllProofType")
	public ResponseEntity<List<ProofType>> getAllProofTypeList() {
		List<ProofType> prooftypeList = userService.getProofTypeList();
		return ResponseEntity.status(HttpStatus.OK).body(prooftypeList);
	}

	@PostMapping("/save/executor")
	public ResponseEntity<ServiceExecutorSaveRequestDTO> registerExecutor(
			@RequestBody ServiceExecutorSaveRequestDTO requestDTO) throws AdminException {
		UserDTO dto = userService.saveExecutor(requestDTO);
		requestDTO.setUserName(dto.getUserName());
		return ResponseEntity.status(HttpStatus.OK).body(requestDTO);
	}

	@PutMapping("/update/executor")
	public ResponseEntity<ServiceExecutorSaveRequestDTO> modifyExecutor(
			@RequestBody ServiceExecutorSaveRequestDTO requestDTO) throws AdminException {
		UserDTO dto = userService.updateExecutor(requestDTO);
		requestDTO.setUserName(dto.getUserName());
		return ResponseEntity.status(HttpStatus.OK).body(requestDTO);
	}
	
	@PutMapping("/update/executor_status")
	public ResponseEntity<String> modifyExecutorStatus(@RequestBody String seid) throws AdminException {
		String result = "Not updated";
		int status = userService.updateExecutorStatus(seid);
		if(status>0)
			result = "status updated";
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PutMapping("modify/token")
	public ResponseEntity<String> updateToken(@RequestBody UserDTO userDto) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateToken(userDto));
	}

	@GetMapping("get/token/{user_name}")
	public ResponseEntity<String> getToken(@PathVariable String user_name) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getToken(user_name));
	}

	@PutMapping("/logout")
	public ResponseEntity<String> updateFbToken(@RequestBody UserDTO userDto) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.logout(userDto));
	}
    
	//boolean
	@GetMapping("validate/phone")
	public ResponseEntity<Object> validatePhoneNumber(@RequestParam String phone, @RequestParam String userType) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.validatePhone(phone, userType));
	}

	@GetMapping("validate/mobile")
	public ResponseEntity<Object> validateMobileNumber(@RequestParam String phone, @RequestParam String userType) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.validateMobile(phone, userType));
	}

	@PutMapping("/forget/pwd")
	public ResponseEntity<UserDTO> forgetPassword(@RequestParam String phone, @RequestParam String userType) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.forgetPasswordByPhone(phone, userType));

	}

	@ApiOperation(value = "Get Login Screen of Admin Panel")
	@PostMapping("/web/login")
	public ResponseEntity<String> webLogin(@RequestBody Map<String, String> inputs, @RequestParam String userType) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService
				.validateWebLogin(inputs.get(AdminConstant.MOBILE_NUMBER), inputs.get(AdminConstant.PASSWORD), userType));

	}

	@PostMapping("/save/usertype")
	public ResponseEntity<UserType> saveUserType(@RequestBody AdminMetadata metaData) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.saveUserType(metaData));

	}

	@PostMapping("/save/adrstype")
	public ResponseEntity<AddressType> saveAddressType(@RequestBody AdminMetadata metaData) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.saveAddressType(metaData));

	}
	
	@GetMapping("/getId")
	public ResponseEntity<Long> saveAddressType(@RequestParam String mobileNumber,@RequestParam String userType) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.findByMobileNumberAndUserType(mobileNumber, userType));

	}

	@PostMapping("/save/prooftype")
	public ResponseEntity<ProofType> saveIdProofType(@RequestBody AdminMetadata metaData) throws AdminException {

		return ResponseEntity.status(HttpStatus.OK).body(userService.saveIdProofType(metaData));

	}

	@ApiOperation(value = "file", notes = "Save profile photo of user")
	@PostMapping(value = "/upload", produces = "application/json")
	public ResponseEntity<Map<String, String>> uploadFile(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "type") String type) {
		String result = "";
		Map<String, String> response = new HashMap<String, String>();
		try {

			result = userService.uploadDocument(file);

			if (!result.equals("")) {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}

	@RequestMapping(value = "/showimage", method = RequestMethod.GET)
	public byte[] displayTestPicture(@RequestParam("photoPath") String photoPath, HttpServletResponse response) {
		byte[] bytes = null;

		try {
			String imagePath = photoPath;
			LOGGER.debug("photo path: " + imagePath);
			File file = new File(imagePath);
			bytes = Files.readAllBytes(file.toPath());
			response.setHeader("Content-Type", "image/jpg/mp4");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			Files.copy(file.toPath(), response.getOutputStream());
			return Base64.getEncoder().encode(bytes);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return bytes;
		}
	}

	@GetMapping(value = "getReferralProfile", produces = { "application/json", "application/xml" })
	public ResponseEntity<ReferralDTO> getReferralProfile(@RequestParam String userName) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getReferralProfile(userName));
	}
	
	@ApiOperation(value = "Checking time duration is 48 hours")
	@GetMapping(value = "/getHours", produces = { "application/json", "application/xml" })
	public ResponseEntity<Long> getAllAcceptedList(@RequestParam String userName,@RequestParam String orderId) throws AdminException {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllAcceptedList(userName,orderId));
	}
	
	@ApiOperation(value = "view Referral Master Screen of Admin Panel")
	  @GetMapping("/getReferralMaster")
	  public ResponseEntity<List<ReferralDTO>> fetchReferralMaster(){
	    return ResponseEntity.status(HttpStatus.OK).body(userService.getAllReferralMaster());
	  }
	
	@ApiOperation(value = "accept or reject a order", notes = "cancel a order")
	@PutMapping(value = "/accept_reject_order", produces = "application/json")
	public ResponseEntity<Map<String, OrderResponseDTO>> acceptRejectSEOrders(@RequestParam String orderId, @RequestParam String status, @RequestParam String userId) throws AdminException {
		OrderResponseDTO result = null;
		Map<String, OrderResponseDTO> response = new HashMap<String, OrderResponseDTO>();
		//try {
			boolean userStatus = userService.getUserStatus(userId);
			if(userStatus)
			{
			result = userService.updateOrders(orderId, status, userId);
			
			if (result != null) {
				response.put("data", result);
				//device token of user
				if(result.getOrderStatus().equals("ORDER_ACCEPTED")) {
				String createdBy = userService.getUserIdByOrder(orderId);
				
				String deviceToken = userService.getToken(createdBy);
				if(deviceToken != null)
				{			
					NotificationDetails notificationRequest = new NotificationDetails();
					notificationRequest.setNotificationTitle("Acceptance of Service");
					notificationRequest.setNotificationMessage(NotificationMessage.ACCEPT_BOOKING.replace("{OID}", orderId).replace("{ONNE}", userId));
					notificationRequest.setNotificationStatus(0);
					notificationRequest.setSenderId(userId);
					notificationRequest.setReceiverId(createdBy);
					NotificationDetails bean = userService.saveNotificationDetails(notificationRequest);
					
					PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
					pushNotificationRequest.setTitle("Acceptance of Service");
					pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.ACCEPT_BOOKING.replace("{OID}", orderId).replace("{ONNE}", userId));
					pushNotificationRequest.setToken(deviceToken);
					pushNotificationRequest.setNotificationId(bean.getNotificationId());
					pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
				}
				return ResponseEntity.status(HttpStatus.OK).body(response);
				}else {
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

//		} catch (Exception ex) {
//			ex.printStackTrace();
//			response.put("data", result);
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//		}

	}
}