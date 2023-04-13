package com.sigmify.vb.admin.service;



import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.exceptions.UnirestException;
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
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.firebase.NotificationMessage;


public interface IUserService {
	public UserDTO saveUser(UserDTO userDto)throws AdminException;
	public List<AddressDTO> updateAddress(UserDTO userDto)throws Exception;
	public UserContactDetailsDTO updateContact(UserDTO userDto)throws Exception;
	public String updatePassword(UserDTO userDto)throws Exception;
	public AccountDetailsDTO updateAccDetails(UserDTO userDto)throws Exception;
	public String deleteUser(Long id);
	public UserIdProofDTO saveIdProof(UserDTO userDto)throws Exception;
	public List<UserDTO> fetchAllUser();
	public UserDTO fetchSingleUser(Long id);
	public UserDTO fetchAddress(Long id);
	public UserDTO fetchContactDetails(Long id);
	public String applyReferralcode(Long id,String code) throws Exception;
	public String verifyUser(String username,String password);
	public UserDTO isAuthenticMobileNumber(String mobileNumber, String password, String userType) throws AdminException;
	public boolean isAuthenticEmailID(String emailID, String password);
	public String getDistrictCode(String description);
	public List<UserType> getUserTypeList();
	public List<AddressType> getAddressTypeList();
	public List<ProofType> getProofTypeList();
	public UserDTO saveExecutor(ServiceExecutorSaveRequestDTO userDto)throws AdminException;
	public UserDTO updateExecutor(ServiceExecutorSaveRequestDTO userDto)throws AdminException;
	//public UserContactDetailsDTO updateToken(UserDTO userDto) throws AdminException;
    public String updateToken(UserDTO userDto) throws AdminException;
    public String getToken(String userName)throws AdminException;
    public boolean validatePhone(String phone, String userType)throws AdminException;
	public String validateMobile(String phone, String userType) throws AdminException;
	public UserDTO forgetPasswordByPhone(String phone, String userType)throws AdminException;
	public UserDTO isVerify(String mobileNumber, String userType)throws AdminException;
	public String validateWebLogin(String phone,String password, String userType)throws AdminException;
	public UserType saveUserType(AdminMetadata metaData)throws AdminException;
	public AddressType saveAddressType(AdminMetadata metaData)throws AdminException;
	public ProofType saveIdProofType(AdminMetadata metaData)throws AdminException;
	public String updateUser(UserDTO userDto)throws AdminException;
    public String logout(UserDTO userDto)throws AdminException;
    public String uploadDocument(MultipartFile file);
    public String serviceExeSendOtp(String mobile, String userType)throws AdminException,UnirestException;
    public UserDTO serviceExeVerifyOtp(String mobile,String otp, String userType)throws AdminException,UnirestException;
    public String userRegistrationOtp(String phone, String userType)throws AdminException,UnirestException;
    public String userRegistrationVerifyOtp(String phone,String otp, String userType)throws AdminException,UnirestException;
    public String userLoginSendOtp(String phone, String userType)throws AdminException,UnirestException;
    public UserDTO userLoginVerifyOtp(String mobile,String otp, String userType)throws AdminException,UnirestException;
    public String ServiceOwnerRegistrationOtp(String phone, String userType)throws AdminException,UnirestException;
    public String serviceOwnerRegistrationVerifyOtp(String phone,String otp,String userType)throws AdminException,UnirestException;
    public String serviceOwnerLoginSendOtp(String phone, String userType)throws AdminException,UnirestException;
    public UserDTO serviceOwnerLoginVerifyOtp(String mobile,String otp, String userType)throws AdminException,UnirestException;
    public String userSendResetPasswordOtp(String mobile)throws AdminException,UnirestException;
    public String userResetPwdVerifyOtp(String mobile,String otp, String userType)throws AdminException,UnirestException;
    public String ServiceOwnerSendResetPasswordOtp(String mobile)throws AdminException,UnirestException;
    public String ServiceOwnerResetPwdVerifyOtp(String mobile,String otp, String userType)throws AdminException,UnirestException;
    public String userRegistrationSuccessMsg(String mobile, String userType)throws AdminException,UnirestException;
    public String serviceOwnerRegistrationSuccessMsg(String mobile, String userType)throws AdminException,UnirestException;
    public String userBookingPendingMsg(String mobile)throws AdminException,UnirestException;
    public String userBookingAcceptanceMsg(String mobile)throws AdminException,UnirestException;
    public String userBookingCancelMsg(String mobile)throws AdminException,UnirestException;
    public String referralMsg(List<String> mobile,String userName, String userType)throws AdminException,UnirestException;
    public ReferralDTO getReferralProfile(String userName)throws AdminException;
    public ServiceExecutorSaveRequestDTO findByUserName(String userName, String deviceToken);
    public NotificationMessage makeNotificationMessage(String title, String param);
    public NotificationDetails saveNotificationDetails(NotificationDetails notificationRequest);
    public List<NotificationDetails> fetchAllNotification(String receiverId);
    public int updateNotificationStatus(int notificationId);
    
    OrderResponseDTO updateOrders(String orderId, String status, String userId)throws AdminException;
    String getUserIdByOrder(String orderId);
    long getAllAcceptedList(String username, String orderId) throws AdminException;
    
    public int updateExecutorStatus(String seid);
    public boolean getUserStatus(String seid);
    
    public List<AdminFarmerDto> fetchAllFarmer();
    public List<AdminFarmerDto> fetchAllServiceOwner();
    public List<AdminFarmerDto> fetchAllAdminUser();
    public List<AdminFarmerDto> fetchAllExecutor();
    public AdminPanelDashboard getAdminDashboard();
    public Long findByMobileNumberAndUserType(String mobileNo,String userType)throws AdminException;
    public List<AdminPanelOrderStatus> getFarmerOrderStatus();
    public List<AdminPanelOrderStatus> getOwnerOrderStatus();
    public List<ReferralDTO> getAllReferralDetails();
    public List<ReferralDTO> getAllReferralMaster();
    public UserActivity saveUserActivity(UserActivity userActivity);
    
}
