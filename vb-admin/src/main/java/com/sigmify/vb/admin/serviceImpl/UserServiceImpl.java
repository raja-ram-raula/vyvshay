package com.sigmify.vb.admin.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sigmify.vb.admin.aspect.Logged;
import com.sigmify.vb.admin.constants.AdminConstant;
import com.sigmify.vb.admin.dto.AccountDetailsDTO;
import com.sigmify.vb.admin.dto.AddressDTO;
import com.sigmify.vb.admin.dto.AdminFarmerDto;
import com.sigmify.vb.admin.dto.AdminPanelDashboard;
import com.sigmify.vb.admin.dto.AdminPanelOrderStatus;
import com.sigmify.vb.admin.dto.OrderResponseDTO;
import com.sigmify.vb.admin.dto.ProofTypeDTO;
import com.sigmify.vb.admin.dto.ReferralDTO;
import com.sigmify.vb.admin.dto.UserContactDetailsDTO;
import com.sigmify.vb.admin.dto.UserDTO;
import com.sigmify.vb.admin.dto.UserIdProofDTO;
import com.sigmify.vb.admin.dto.UserPasswordDTO;
import com.sigmify.vb.admin.dto.UserTypeDTO;
import com.sigmify.vb.admin.dto.request.AdminMetadata;
import com.sigmify.vb.admin.dto.request.ServiceExecutorSaveRequestDTO;
import com.sigmify.vb.admin.entity.AccountDetails;
import com.sigmify.vb.admin.entity.Address;
import com.sigmify.vb.admin.entity.NotificationDetails;
import com.sigmify.vb.admin.entity.Order;
import com.sigmify.vb.admin.entity.OrderDetails;
import com.sigmify.vb.admin.entity.PaymentDetails;
import com.sigmify.vb.admin.entity.Referral;
import com.sigmify.vb.admin.entity.ReferralCodeContact;
import com.sigmify.vb.admin.entity.ServiceDetails;
import com.sigmify.vb.admin.entity.ServiceType;
import com.sigmify.vb.admin.entity.User;
import com.sigmify.vb.admin.entity.UserActivity;
import com.sigmify.vb.admin.entity.UserContactDetails;
import com.sigmify.vb.admin.entity.UserIdProof;
import com.sigmify.vb.admin.entity.UserPassword;
import com.sigmify.vb.admin.entity.metadata.AddressType;
import com.sigmify.vb.admin.entity.metadata.District;
import com.sigmify.vb.admin.entity.metadata.ProofType;
import com.sigmify.vb.admin.entity.metadata.State;
import com.sigmify.vb.admin.entity.metadata.UserType;
import com.sigmify.vb.admin.enums.ErrorCode;
import com.sigmify.vb.admin.exception.AdminException;
import com.sigmify.vb.admin.firebase.NotificationMessage;
import com.sigmify.vb.admin.mapperImpl.AccountDetailsMapper;
import com.sigmify.vb.admin.mapperImpl.AddressMapper;
import com.sigmify.vb.admin.mapperImpl.OrderDetailsMapper;
import com.sigmify.vb.admin.mapperImpl.ReferralCodeContactMapper;
import com.sigmify.vb.admin.mapperImpl.ReferralMapper;
import com.sigmify.vb.admin.mapperImpl.UserContactDetailsMapper;
import com.sigmify.vb.admin.mapperImpl.UserIdProofMapper;
import com.sigmify.vb.admin.mapperImpl.UserMapper;
import com.sigmify.vb.admin.mapperImpl.UserPasswordMapper;
import com.sigmify.vb.admin.mapperImpl.UserTypeMapper;
import com.sigmify.vb.admin.repositories.AddressRepository;
import com.sigmify.vb.admin.repositories.AddressTypeRepositories;
import com.sigmify.vb.admin.repositories.DistrictRepository;
import com.sigmify.vb.admin.repositories.NotificationDetailsRepository;
import com.sigmify.vb.admin.repositories.OrderDetailsTypeRepository;
import com.sigmify.vb.admin.repositories.OrderRepository;
import com.sigmify.vb.admin.repositories.PasswordRepository;
import com.sigmify.vb.admin.repositories.PaymentRepository;
import com.sigmify.vb.admin.repositories.ReferralCodeContactRepository;
import com.sigmify.vb.admin.repositories.ReferralRepository;
import com.sigmify.vb.admin.repositories.ServiceDetailsRepository;
import com.sigmify.vb.admin.repositories.ServiceTypeRepository;
import com.sigmify.vb.admin.repositories.StateRepository;
import com.sigmify.vb.admin.repositories.UserActivityRepository;
import com.sigmify.vb.admin.repositories.UserContactDetailsRepository;
import com.sigmify.vb.admin.repositories.UserIdProofRepository;
import com.sigmify.vb.admin.repositories.UserProofTypeRepository;
import com.sigmify.vb.admin.repositories.UserRepository;
import com.sigmify.vb.admin.repositories.UserTypeRepository;
import com.sigmify.vb.admin.service.IUserService;
import com.sigmify.vb.admin.util.ReferralCodeGenerator;
import com.sigmify.vb.admin.util.UploadFile;

@Service
public class UserServiceImpl implements IUserService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserActivityRepository userActivityRepo;
	@Autowired
	private UserTypeRepository userTypeRepo;
	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private AddressTypeRepositories addressTypeRepo;
	@Autowired
	private DistrictRepository districtRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private UserContactDetailsRepository ucRepo;
	@Autowired
	private UserProofTypeRepository IdProofTypeRepo;
	@Autowired
	private ReferralRepository referRepo;
	@Autowired
	private PasswordRepository passRepo;
	@Autowired
	private UserContactDetailsRepository userContactRepo;
	@Autowired
	private ReferralCodeContactRepository referralCodeContactRepo;
	@Autowired
	private UserIdProofRepository userIdProofRepo;

	@Autowired
	private ReferralCodeGenerator randomNumber;

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserTypeMapper userTypeMapper;
	@Autowired
	private AddressMapper adrsMapper;
	@Autowired
	private UserContactDetailsMapper contactDetailsMapper;
	@Autowired
	private UserPasswordMapper userPwdMapper;
	@Autowired
	private AccountDetailsMapper accountMapper;
	@Autowired
	private UserIdProofMapper idProofMapper;
	@Autowired
	private ReferralMapper referMapper;
	@Autowired
	private ReferralCodeContactMapper referralCodeContactMapper;

	@Autowired
	private OrderDetailsTypeRepository orderDetailsTypeRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private NotificationDetailsRepository notificationDetailsRepository;

	@Autowired
	private OrderDetailsMapper orderDetailsMapper;

	@Autowired
	private ServiceDetailsRepository serviceDetailsRepo;

	@Autowired
	private ServiceTypeRepository serviceTypeRepo;

	@Autowired
	private PaymentRepository paymentRepo;
	
	//msg91 PropertiesFile
	@Value("${app.auth.key}")
	private String authKey;
	
	@Value("${app.sender.id}")
	private String senderId;
	
	@Value("${OTP.EXPIRY.TIME}")
	private String otpExpiryTime;
	
	@Value("${SEND.OTP.BASE.API}")
	private String sendOtpBaseApi;
	
	@Value("${SEND.OTP.BASE.TEMPLATE}")
	private String sendOtpBaseTemplate;
	
	@Value("${MOBILE.NO}")
	private String msg91MobileNo;
	
	@Value("${SEND.OTP.AUTHKEY}")
	private String sendOtpAuthKey;
	
	@Value("${IND.CODE}")
	private String indCode;
	
	@Value("${OTP.EXPIRY}")
	private String expiryOtp;
	
	@Value("${VERIFY.OTP}")
	private String verifyOtp;
	
	@Value("${SE.OTP}")
	private String sendOtp;
	
	@Value("${User.Registration.OTP}")
	private String userRegistrationOTP;
	
	@Value("${User.Login.OTP}")
	private String userLoginOTP;
	
	@Value("${User.Reset.Password.OTP}")
	private String userResetPasswordOTP;
	
	@Value("${Partner.Registration.OTP}")
	private String partnerRegistrationOTP;
	
	@Value("${Partner.Login.OTP}")
	private String partnerLoginOTP;
	
	@Value("${Partner.Reset.Password.OTP}")
	private String partnerResetPasswordOTP;
	
	@Value("${User.Successful.Registration}")
	private String userSuccessfulRegistration;
	
	@Value("${Partner.Successful.Registration}")
	private String partnerSuccessfulRegistration;
	
	@Value("${sms.flow.url}")
	private String smsFlowUrl;
	
	@Value("${Partner.Associate.Login.OTP}")
	private String partnerAssociateLoginOTP;
	
	@Value("${User.Booking.Pending}")
	private String userBookingPending;
	
	@Value("${User.Booking.Acceptance}")
	private String userBookingAcceptance;
	
	@Value("${User.Referral.Friend}")
	private String userReferralFriend;

	@Logged
	@Override
	public UserDTO saveUser(UserDTO userDto) throws AdminException {
		UserContactDetails ucDetails = null;
		ucDetails = userContactRepo.getUserContactsByPhoneNo(userDto.getUcDto().getPhoneNo(),
				userDto.getUsertype().getName());
		if (ucDetails != null) {
			LOGGER.error("Registartion failed due to the given phone number:(" + userDto.getUcDto().getPhoneNo()
					+ ") already exists ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"Given phone number already registered with us. please register using other phone number");
		} else {
			UserType userType = null;
			User user = userMapper.toEntity(userDto);
			if (user != null) {
				if (userDto.getUsertype() != null) {
					userType = userTypeRepo.findByName(userDto.getUsertype().getName());
				}
				user.setUserType(userType);
				ucDetails = contactDetailsMapper.toEntity(userDto.getUcDto());
				if (ucDetails != null) {
					ucDetails.setLastUpdateBy(user);
					ucDetails.setUser(user);
					ucDetails.setUserType(userDto.getUsertype().getName());
					user.setUcDetails(ucDetails);
				} else {
					LOGGER.error("Registartion failed due to user contact details is empty for ->{}-{}", userDto);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"User contact details is mandatory for user registration");
				}

				Referral refer = new Referral();
				refer.setGeneratedfor(user);
				refer.setReferralCode(randomNumber.createRandomCode(4));
				refer.setTotalInstalled(0);
				refer.setTotalReferred(0);
				refer.setReferralPoint(0);
				user.setReferral(refer);

				Random random = new Random();
				int no = random.nextInt(1000);
				user.setUserName(userDto.getfName() + no);
				if (!userDto.getReferralDto().getReferralCode().isEmpty()) {
					String code = userDto.getReferralDto().getReferralCode();
					if (code != null) {
						Referral newReferral = referRepo.findByReferralCode(code);
						if (newReferral != null) {
							String phone = userDto.getUcDto().getPhoneNo();
							ReferralCodeContact referralCodeContact = referralCodeContactRepo
									.findByMobileNumberAndReferralCode(phone, newReferral);
							System.out.println(referralCodeContact);
							if (referralCodeContact != null) {
								User newUser = newReferral.getGeneratedfor();
								int i = newUser.getReferral().getTotalInstalled();
								int j = newUser.getReferral().getReferralPoint();
								newUser.getReferral().setTotalInstalled(i + 1);
								newUser.getReferral().setReferralPoint(j + AdminConstant.REFERRAL_POINT);
								referralCodeContact.setAppInstalled(true);
							} else {
								LOGGER.error("Referral code is not matching with this given phone number", phone);
								throw new AdminException(ErrorCode.REQUEST_ERROR,
										"Referral code is not matching with this given phone number: (" + phone
												+ ") , please provide the correct phone number or referral code");
							}

						} else {
							LOGGER.error("Invalid referral code", code);
							throw new AdminException(ErrorCode.REQUEST_ERROR,
									"Invalid referral code: " + code + " , please provide a valid referral code");
						}
					}
				} else {
					userDto.getReferralDto().setReferralCode("");
				}

				UserPasswordDTO userPwdDto = userDto.getUserPasswordDto();
				UserPassword userPwd = userPwdMapper.toEntity(userPwdDto);
				if (userPwd != null) {
					userPwd.setLastUpdateBy(user);
					userPwd.setUser(user);
					user.setUserPassword(userPwd);
				} else {
					LOGGER.error("Registartion failed due to user password is empty for ->{}-{}", userDto.getfName(),
							userDto.getlName());
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"User password is mandatory for user registration");
				}

				List<Address> listAddress = new ArrayList<Address>();
				for (AddressDTO adrsDto : userDto.getAddressdto()) {
					Address addrs = adrsMapper.toEntity(adrsDto);
					if (addrs != null) {
						AddressType addtype = null;
						if (adrsDto.getAddressType() != null) {
							addtype = addressTypeRepo.findByName(adrsDto.getAddressType().getName());
						}
						addrs.setAddressType(addtype);
						addrs.setCreatedBy(user);
						addrs.setLastUpdateBy(user);
						addrs.setUser(user);
						District district = null;
						if (adrsDto.getDistrictName() != null) {
							district = districtRepo.findByDescription(adrsDto.getDistrictName());
						}
						// addrs.setDistrictName(district);
						// addrs.setDistrictId(district);
						addrs.setDistrictName(adrsDto.getDistrictName());
						State state = null;
						if (adrsDto.getStateName() != null) {
							state = stateRepo.findByDescription(adrsDto.getStateName());
						}
						addrs.setStateName(state);
						addrs.setStateId(state);
						listAddress.add(addrs);
					} // if
					else {
						LOGGER.error("Registartion failed due to user address is empty for ->{}-{}", userDto.getfName(),
								userDto.getlName());
						throw new AdminException(ErrorCode.REQUEST_ERROR,
								"User address is mandatory for user registration");
					}
				} // for
				user.setListAddress(listAddress);
				userRepo.save(user);
				userDto = userMapper.convert(user);
				List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
				for (Address adrs : user.getListAddress()) {
					AddressDTO adrsDto = adrsMapper.convert(adrs);
					listAddressDto.add(adrsDto);
				}
				userDto.setAddressdto(listAddressDto);
				userDto.setUcDto(contactDetailsMapper.convert(ucDetails));
				return userDto;
			} // if
			else {
				return null;
			}
		} // else

	}

	@Override
	public List<AddressDTO> updateAddress(UserDTO userDto) throws AdminException {
		// updateDate=LocalDateTime.now();
		User user = null;
		if (userDto.getId() != null) {
			Optional<User> opt = userRepo.findById(userDto.getId());
			if (opt.isPresent()) {
				user = opt.get();
			}
		}
		if (user != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			List<Address> listadress = new ArrayList<Address>();
			for (AddressDTO adrsDto : userDto.getAddressdto()) {
				if (adrsDto != null) {
					Address addrs = null;
					if (adrsDto.getId() != null) {
						Optional<Address> opt1 = addressRepo.findById(adrsDto.getId());
						if (opt1.isPresent()) {
							addrs = opt1.get();

						}
						if (addrs != null && adrsDto.isDelete()) {
							addrs.setUser(null);
							addrs.setAddressType(null);
							addrs.setDistrictName(null);
							addrs.setStateName(null);
							addrs.setDistrictId(null);
							addrs.setStateId(null);
							addressRepo.delete(addrs);
							continue;
						}

					} else {
						addrs = adrsMapper.toEntity(adrsDto);
						if (addrs != null) {
							AddressType addtype = null;
							if (adrsDto.getAddressType() != null) {
								addtype = addressTypeRepo.findByName(adrsDto.getAddressType().getName());
							}
							addrs.setAddressType(addtype);
							addrs.setUser(user);
							addrs.setCreatedBy(user);
							addrs.setLastUpdateBy(user);
							District district = null;
							if (adrsDto.getDistrictName() != null) {
								district = districtRepo.findByName(adrsDto.getDistrictName());
							}
							// addrs.setDistrictName(district);
							// addrs.setDistrictId(district);
							addrs.setDistrictName(adrsDto.getDistrictName());
							State state = null;
							if (adrsDto.getStateName() != null) {
								state = stateRepo.findByName(adrsDto.getStateName());
							}
							addrs.setStateName(state);
							addrs.setStateId(state);
							// add Address to AddressList
							listadress.add(addrs);
						} // if
					} // else
						// to avoid null in previous addresses
					for (Address adrs1 : user.getListAddress()) {
						listadress.add(adrs1);
					}
					user.setListAddress(listadress);
					userRepo.save(user);
				}

			} // for
			userDto = fetchSingleUser(user.getId());
			return userDto.getAddressdto();
		} // if
		else {
			LOGGER.error("Address update failed because of wrong user information", userDto.getId());
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Invalid User information for userID" + userDto.getId());
		}

	}

	@Override
	public UserIdProofDTO saveIdProof(UserDTO userDto) throws AdminException {
		User user = null;
		Optional<User> opt = userRepo.findById(userDto.getId());
		if (opt.isPresent()) {
			user = opt.get();
		}
		if (user != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			UserIdProof idProof = null;
			if (user.getUserIdProof() != null) {
				// idProof=user.getUserIdProof();
				idProof = idProofMapper.toEntity(userDto.getUserIdProofDto());
				if (idProof != null) {
					/*
					 * idProof.setId(user.getUserIdProof().getId()); idProof.setUser(user);
					 * idProof.setCreatedBy(user); idProof.setCreationDate(createDate);
					 * idProof.setLastUpdateBy(user); ProofType proofType=null;
					 * if(userDto.getUserIdProofDto().getProofType()!=null) {
					 * proofType=IdProofTypeRepo.findByName(userDto.getUserIdProofDto().getProofType
					 * ().getName()); } idProof.setProofType(proofType);
					 */
					user.setUserIdProof(idProof);
					user = userRepo.save(user);
					return idProofMapper.convert(user.getUserIdProof());
				} else {
					return null;
				}
			} else {
				idProof = idProofMapper.toEntity(userDto.getUserIdProofDto());
				if (idProof != null) {
					// idProof.setId(user.getUserIdProof().getId());
					idProof.setUser(user);
					idProof.setCreatedBy(user);
					idProof.setLastUpdateBy(user);
					ProofType proofType = null;
					if (userDto.getUserIdProofDto().getProofType() != null) {
						proofType = IdProofTypeRepo.findByName(userDto.getUserIdProofDto().getProofType().getName());
					}
					idProof.setProofType(proofType);
					user.setUserIdProof(idProof);
					user = userRepo.save(user);
					return idProofMapper.convert(user.getUserIdProof());
				} else {
					return null;
				}

			} // else
		} else {
			return null;
		}

	}

	@Override
	public UserContactDetailsDTO updateContact(UserDTO userDto) throws AdminException {
		User user = null;
		Optional<User> opt = userRepo.findById(userDto.getId());
		if (opt.isPresent()) {
			user = opt.get();
		}
		if (user != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			UserContactDetails ucDetails = contactDetailsMapper.toEntity(userDto.getUcDto());
			if (ucDetails != null) {
				user.setUcDetails(ucDetails);
				user = userRepo.save(user);
				return contactDetailsMapper.convert(user.getUcDetails());
			} else {
				LOGGER.error("User Contact update failed because of wrong user contact  information",
						userDto.getUcDto());
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"No User contact information for userID" + userDto.getId());
			}
		} else {
			LOGGER.error("User Contact update failed because of wrong user contact  information", userDto.getUcDto());
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"No User contact information for userID" + userDto.getId());
		}
	}

	@Override
	public String updatePassword(UserDTO userDto) throws AdminException {
		if (userDto == null) {
			LOGGER.error("Updation failed due to user information is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Insufficient information to update password");
		}
		User user = null;
		if (userDto.getId() != null) {
			Optional<User> opt = userRepo.findById(userDto.getId());
			if (opt.isPresent()) {
				user = opt.get();
			}
		} else {
			LOGGER.error("Updation failed due to user id is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "User id is mandatory for user password updation");
		}

		if (user != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			UserPassword userPassword = passRepo.getUserPasswordByUser(user);
			userPassword.setPassword(userDto.getUserPasswordDto().getPassword());
			userPassword.setLastUpdateDate(LocalDateTime.now());
			userRepo.save(user);
			return "password updated successfully for user: " + user.getUserName() + " on date: "
					+ user.getLastUpdateDate();
		} else {
			LOGGER.error("updation failed due to  no user is found for given id ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"A valid user id is mandatory for user password updation");
		}

	}

	@Override
	public String updateUser(UserDTO userDto) throws AdminException {
		if (userDto == null) {
			LOGGER.error("Updation failed due to user information is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Insufficient information to update User");
		}
		User user = null;
		if (userDto.getId() != null) {
			Optional<User> opt = userRepo.findById(userDto.getId());
			if (opt.isPresent()) {
				user = opt.get();
			}
		} else {
			LOGGER.error("Updation failed due to user id is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "User id is mandatory for user updation");
		}

		if (user != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			user.setfName(userDto.getfName());
			user.setlName(userDto.getlName());
			user.setPhoto(userDto.getPhoto());
			UserContactDetails ucDetails = user.getUcDetails();
			ucDetails.seteMail(userDto.getUcDto().geteMail());
			ucDetails.setLastUpdateDate(LocalDateTime.now());
			ListIterator<Address> listIterator = user.getListAddress().listIterator();
			for (AddressDTO adrsDto : userDto.getAddressdto()) {
				while (listIterator.hasNext()) {
					Address adrs = listIterator.next();
					adrs.setAddress(adrsDto.getAddress());
					adrs.setCityLocality(adrsDto.getCityLocality());
					adrs.setLastUpdateDate(LocalDateTime.now());
					adrs.setLattitude(adrsDto.getLattitude());
					adrs.setLongitude(adrsDto.getLongitude());
					District district = null;
					if (adrsDto.getDistrictName() != null) {
						district = districtRepo.findByDescription(adrsDto.getDistrictName());
					}
					System.out.println(district);
					// adrs.setDistrictName(district);
					// adrs.setDistrictId(district);

					adrs.setDistrictName(adrsDto.getDistrictName());
					State state = null;
					if (adrsDto.getStateName() != null) {
						state = stateRepo.findByDescription(adrsDto.getStateName());
					}
					adrs.setStateName(state);
					adrs.setStateId(state);
					adrs.setZipCode(adrsDto.getZipCode());
					userRepo.save(user);
				}

			}
			return "updation succeded for user: " + user.getUserName() + " on date: " + user.getLastUpdateDate();

		} else {
			LOGGER.error("updation failed due to  no user is found for given id ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "A valid user id is mandatory for user updation");
		}

	}

	@Override
	public AccountDetailsDTO updateAccDetails(UserDTO userDto) throws AdminException {
		User user = null;
		Optional<User> opt = userRepo.findById(userDto.getId());
		if (opt.isPresent()) {
			user = opt.get();
		}
		if (user != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			AccountDetails accDetails = null;
			if (user.getAccDetails() != null) {
				accDetails = accountMapper.toEntity(userDto.getAccDetailsDto());
				if (accDetails != null) {
					user.setAccDetails(accDetails);
					user = userRepo.save(user);
					return accountMapper.convert(user.getAccDetails());
				} // if
				else {
					return null;
				}
			} // if
			else {
				accDetails = accountMapper.toEntity(userDto.getAccDetailsDto());
				if (accDetails != null) {
					accDetails.setUser(user);
					accDetails.setCreatedBy(user);
					accDetails.setLastUpdateBy(user);
					user.setAccDetails(accDetails);
					user = userRepo.save(user);
					return accountMapper.convert(user.getAccDetails());
				} else {
					LOGGER.error("Account Updation failed due to Account information is empty for ->{}", userDto);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"User Information is mandatory for user Account updation");
				}
			}
		} // if
		else {
			LOGGER.error("Account Updation failed due to user information is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"User Information is mandatory for user Account updation");
		}

	}

	@Override
	public String deleteUser(Long id) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			return id + " Id User deleted successfully";
		} else {
			return "User Id-" + id + " not exists";
		}
	}

	@Override
	public List<UserDTO> fetchAllUser() {
		List<User> listUser = userRepo.findAll();
		List<UserDTO> listUserDto = new ArrayList<UserDTO>();
		listUser.forEach(user -> {
			UserDTO userDto = userMapper.convert(user);

			UserContactDetailsDTO ucDto = contactDetailsMapper.convert(user.getUcDetails());
			userDto.setUcDto(ucDto);

			UserPasswordDTO userPwdDto = userPwdMapper.convert(user.getUserPassword());
			userDto.setUserPasswordDto(userPwdDto);

			AccountDetails accDetails = null;
			accDetails = user.getAccDetails();
			if (accDetails != null) {
				AccountDetailsDTO accDetailsDto = accountMapper.convert(accDetails);
				userDto.setAccDetailsDto(accDetailsDto);
			}
			Referral refer = null;
			refer = user.getReferral();
			if (refer != null) {
				ReferralDTO referDto = referMapper.convert(refer);
				referDto.setGeneratedFor(user.getId());
				userDto.setReferralDto(referDto);
			}
			UserIdProof uidProof = null;
			uidProof = user.getUserIdProof();
			if (uidProof != null) {
				UserIdProofDTO userIdProofDto = idProofMapper.convert(uidProof);
				userDto.setUserIdProofDto(userIdProofDto);
			}

			List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
			for (Address adrs : user.getListAddress()) {
				AddressDTO adrsDto = adrsMapper.convert(adrs);
				listAddressDto.add(adrsDto);
			}
			userDto.setAddressdto(listAddressDto);

			listUserDto.add(userDto);
		});
		return listUserDto;

	}

	@Override
	public UserDTO fetchSingleUser(Long id) {
		Optional<User> opt = userRepo.findById(id);
		User user = null;
		if (opt.isPresent()) {
			user = opt.get();
		}
		UserDTO userDto = null;
		if (user != null) {
			userDto = userMapper.convert(user);

			UserContactDetailsDTO ucDto = contactDetailsMapper.convert(user.getUcDetails());
			userDto.setUcDto(ucDto);

			UserPasswordDTO userPwdDto = userPwdMapper.convert(user.getUserPassword());
			userDto.setUserPasswordDto(userPwdDto);

			AccountDetails accDetails = null;
			accDetails = user.getAccDetails();
			if (accDetails != null) {
				AccountDetailsDTO accDetailsDto = accountMapper.convert(accDetails);
				userDto.setAccDetailsDto(accDetailsDto);
			}

			UserIdProof uidProof = null;
			uidProof = user.getUserIdProof();
			if (uidProof != null) {
				UserIdProofDTO userIdProofDto = idProofMapper.convert(uidProof);
				userDto.setUserIdProofDto(userIdProofDto);
			}

			Referral refer = null;
			refer = user.getReferral();
			if (refer != null) {
				ReferralDTO referDto = referMapper.convert(refer);
				referDto.setGeneratedFor(user.getId());
				userDto.setReferralDto(referDto);
			}

			List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
			for (Address adrs : user.getListAddress()) {
				AddressDTO adrsDto = adrsMapper.convert(adrs);
				listAddressDto.add(adrsDto);
			}
			userDto.setAddressdto(listAddressDto);

		}
		return userDto;
	}

	@Override
	public UserDTO fetchAddress(Long id) {
		Optional<User> opt = userRepo.findById(id);
		User user = null;
		if (opt.isPresent()) {
			user = opt.get();
		}
		UserDTO userDto = null;
		if (user != null) {
			userDto = userMapper.convert(user);
			List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
			for (Address adrs : user.getListAddress()) {
				AddressDTO adrsDto = adrsMapper.convert(adrs);
				listAddressDto.add(adrsDto);
			}
			userDto.setAddressdto(listAddressDto);

		}
		return userDto;
	}

	@Override
	public UserDTO fetchContactDetails(Long id) {
		Optional<User> opt = userRepo.findById(id);
		User user = null;
		if (opt.isPresent()) {
			user = opt.get();
		}
		UserDTO userDto = null;
		if (user != null) {
			userDto = userMapper.convert(user);
			UserContactDetailsDTO ucDto = contactDetailsMapper.convert(user.getUcDetails());
			userDto.setUcDto(ucDto);
		}
		return userDto;
	}

	@Override
	public String applyReferralcode(Long id, String code) throws Exception {
		UserDTO userDto = fetchSingleUser(id);

		Referral refer = referRepo.findByReferralCode(code);
		List<ReferralCodeContact> refercontactlist = null;
		if (refer != null) {
			refercontactlist = refer.getReferralCodeContact();
			ReferralCodeContact contactCode = new ReferralCodeContact();
			contactCode.setMailId(userDto.getUcDto().geteMail());
			contactCode.setMobileNumber(userDto.getUcDto().getPhoneNo());
			contactCode.setLastUpdateDate(LocalDateTime.now());
			contactCode.setMessageSend(true);
			refercontactlist.add(contactCode);

			// set referral list back to referral
			refer.setReferralCodeContact(refercontactlist);
			referRepo.save(refer);

			return "Referral code applied successfully";
		} else {
			return "Invalid Referral Code";
		}
	}

	@Override
	public String verifyUser(String username, String password) {
		User user = userRepo.findByUserName(username);
		if (user == null) {
			return "Invalid UserName";
		} else if (password.equals(user.getUserPassword().getPassword())) {
			return "success";
		} else {
			return "invalid password";
		}
	}

	@Override
	public UserDTO isVerify(String mobileNumber, String userType) throws AdminException {
		// System.out.println(mobileNumber);
		UserContactDetails ucd = ucRepo.getUserContactsByPhoneNo(mobileNumber, userType);
		if (null != ucd) {
			User user = ucd.getUser();
			// UserPassword userPassword=user.getUserPassword() ;
			UserContactDetails usercontact = user.getUcDetails();
			if (usercontact.getPhoneNo().equals(mobileNumber)) {
				UserDTO userDto = userMapper.convert(user);
				List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
				for (Address adrs : user.getListAddress()) {
					AddressDTO adrsDto = adrsMapper.convert(adrs);
					listAddressDto.add(adrsDto);
				}
				userDto.setAddressdto(listAddressDto);
				userDto.setUcDto(contactDetailsMapper.convert(ucd));

				return userDto;
			} else {
				LOGGER.error("The given Mobile Number " + mobileNumber + " is not valid");
				throw new AdminException(ErrorCode.REQUEST_ERROR, "Mobile number or otp is not valid");
			}
		} else {
			LOGGER.error("The given Mobile Number " + mobileNumber + " is not registered");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Mobile number is not valid");
		}
	}

	@Override
	public UserDTO isAuthenticMobileNumber(String mobileNumber, String password, String userType)
			throws AdminException {
		UserContactDetails ucd = ucRepo.getUserContactsByPhoneNo(mobileNumber, userType);
		if (null != ucd) {
			User user = ucd.getUser();
			UserPassword userPassword = user.getUserPassword();

			if (userPassword.getPassword().equals(password)) {
				UserDTO userDto = userMapper.convert(user);

				List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
				for (Address adrs : user.getListAddress()) {
					AddressDTO adrsDto = adrsMapper.convert(adrs);
					listAddressDto.add(adrsDto);
				}
				userDto.setAddressdto(listAddressDto);
				userDto.setUcDto(contactDetailsMapper.convert(ucd));
				UserActivity activity=new UserActivity();
				activity.setApiType("LOGIN");
				activity.setApiTypeDetails(AdminConstant.LOGIN_PWD);
				activity.setfName("string");
				activity.setlName("string");
				//activity.setUserName("string");
				activity.setMobileNo(mobileNumber);
				activity.setTimeStamp(LocalDateTime.now());
				activity.setUsertype(userType);
				userActivityRepo.save(activity);

				return userDto;
			} else {
				LOGGER.error("The given Mobile Number " + mobileNumber + " or password " + password + " is not valid");
				throw new AdminException(ErrorCode.REQUEST_ERROR, "Mobile number or password is not valid");
			}
		} else {
			LOGGER.error("The given Mobile Number " + mobileNumber + " is not registered");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Mobile number is not valid");
		}

	}

	@Override
	public boolean isAuthenticEmailID(String emailID, String password) {
		// TODO Auto-generated method stub
		// return ucRepo.isEmailExist(emailID,password);
		return true;
	}

	@Override
	public String getDistrictCode(String description) {
		return districtRepo.findNameByDescription(description);
	}

	@Override
	public List<UserType> getUserTypeList() {
		/*
		 * ListUserTypeDTO listUserTypeDto=new ListUserTypeDTO(); List<UserTypeDTO>
		 * newListUserTypeDto=new ArrayList(); List<UserType>
		 * listUserType=userTypeRepo.findAll(); for(UserType userType:listUserType) {
		 * UserTypeDTO userTypeDto=userTypeMapper.convert(userType);
		 * 
		 * newListUserTypeDto.add(userTypeDto);
		 * listUserTypeDto.setListUserTypeDto(newListUserTypeDto); }
		 */
		return userTypeRepo.findAll();
	}

	@Override
	public List<AddressType> getAddressTypeList() {
		/*
		 * ListAddressType listAddressType=new ListAddressType(); List<AddressType>
		 * addressType=addressTypeRepo.findAll();
		 * listAddressType.setListAddressType(addressType);
		 */
		return addressTypeRepo.findAll();
	}

	@Override
	public List<ProofType> getProofTypeList() {
		return IdProofTypeRepo.findAll();
	}

	@Override
	public UserDTO updateExecutor(ServiceExecutorSaveRequestDTO requestDTO) throws AdminException {
		User user = null;
		UserContactDetails ucDetails = null;
		UserIdProof userIdProof = null;

		if (requestDTO.getfName() != null) {
			user = userRepo.findByfName(requestDTO.getfName());
		}
		if (requestDTO.getPhoneNo() != null) {
			ucDetails = userContactRepo.getUserContactsByPhoneNo(requestDTO.getPhoneNo(), requestDTO.getUsertype());
		}
		if (requestDTO.getUan() != null) {
			userIdProof = userIdProofRepo.findByUan(requestDTO.getUan());
		}

		if (user == null && ucDetails == null) {
			user = userIdProof.getUser();
			user.setfName(requestDTO.getfName());
			user.setLastUpdateDate(LocalDateTime.now());
			ucDetails = userContactRepo.findByUser(user);
			ucDetails.setPhoneNo(requestDTO.getPhoneNo());
			ucDetails.setLastUpdateDate(LocalDateTime.now());
			user.setUcDetails(ucDetails);
		} else if (user == null && userIdProof == null) {
			user = ucDetails.getUser();
			user.setfName(requestDTO.getfName());
			user.setLastUpdateDate(LocalDateTime.now());
			userIdProof = userIdProofRepo.findByUser(user);
			userIdProof.setUan(requestDTO.getUan());
			userIdProof.setImagePath(requestDTO.getImagePath());
			userIdProof.setLastUpdateDate(LocalDateTime.now());
			user.setUserIdProof(userIdProof);
		} else if (user != null && ucDetails == null) {
			ucDetails = userContactRepo.findByUser(user);
			ucDetails.setPhoneNo(requestDTO.getPhoneNo());
			ucDetails.setLastUpdateDate(LocalDateTime.now());
			user.setUcDetails(ucDetails);
			user.setLastUpdateDate(LocalDateTime.now());

		} else if (user != null && userIdProof == null) {
			userIdProof = userIdProofRepo.findByUser(user);
			userIdProof.setUan(requestDTO.getUan());
			userIdProof.setImagePath(requestDTO.getImagePath());
			userIdProof.setLastUpdateDate(LocalDateTime.now());
			user.setUserIdProof(userIdProof);
			user.setLastUpdateDate(LocalDateTime.now());
		} else if (user != null && ucDetails != null && userIdProof != null) {
			user.setLastUpdateDate(LocalDateTime.now());
			ucDetails.setLastUpdateDate(LocalDateTime.now());
			userIdProof.setLastUpdateDate(LocalDateTime.now());
		} else {
			user = ucDetails.getUser();
			user.setfName(requestDTO.getfName());
			user.setLastUpdateDate(LocalDateTime.now());
		}
		userRepo.save(user);
		UserDTO updatedUserDTO = userMapper.convert(user);
		updatedUserDTO.setUcDto(contactDetailsMapper.convert(ucDetails));
		updatedUserDTO.setUserIdProofDto(idProofMapper.convert(userIdProof));
		return updatedUserDTO;
	}

	@Override
	public UserDTO saveExecutor(ServiceExecutorSaveRequestDTO requestDTO) throws AdminException {
		UserContactDetails ucDetails = userContactRepo.getUserContactsByPhoneNo(requestDTO.getPhoneNo(),
				requestDTO.getUsertype());
		if (ucDetails != null) {
			LOGGER.error("Registartion failed due to the given phone number:(" + requestDTO.getPhoneNo()
					+ ") already exists ->{}", requestDTO);
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"Given phone number already registered with us. please register using other phone number");
		} else {

			UserDTO userDTO = new UserDTO();
			userDTO.setfName(requestDTO.getfName());
			UserTypeDTO userTypeDTO = new UserTypeDTO();
			userTypeDTO.setName(requestDTO.getUsertype());
			userDTO.setUsertype(userTypeDTO);
			UserContactDetailsDTO ucDTO = new UserContactDetailsDTO();
			ucDTO.setPhoneNo(requestDTO.getPhoneNo());
			ucDTO.setDeviceId(requestDTO.getDeviceId());
			ucDTO.setFbToken(requestDTO.getFbToken());
			userDTO.setUcDto(ucDTO);
			UserIdProofDTO idProofDTO = new UserIdProofDTO();
			idProofDTO.setImagePath(requestDTO.getImagePath());
			idProofDTO.setUan(requestDTO.getUan());
			ProofTypeDTO proofDTO = new ProofTypeDTO();
			proofDTO.setName(requestDTO.getProofType());
			idProofDTO.setProofType(proofDTO);
			userDTO.setUserIdProofDto(idProofDTO);

			UserType userType = null;
			User user = userMapper.toEntity(userDTO);
			if (user != null) {
				if (userDTO.getUsertype() != null) {
					userType = userTypeRepo.findByName(userDTO.getUsertype().getName());
				}
				user.setUserType(userType);
				ucDetails = contactDetailsMapper.toEntity(userDTO.getUcDto());
				if (ucDetails != null) {
					ucDetails.setLastUpdateBy(user);
					ucDetails.setUser(user);
					ucDetails.setUserType(user.getUsertype().getName());
					user.setUcDetails(ucDetails);
				} else {
					LOGGER.error("Registartion failed due to user contact details is empty for ->{}-{}", userDTO);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"User contact details is mandatory for user registration");
				}
				Random random = new Random();
				int no = random.nextInt(1000);
				user.setUserName(userDTO.getfName() + no);
				UserIdProofDTO newIdProofDTO = userDTO.getUserIdProofDto();
				UserIdProof idProof = null;
				if (newIdProofDTO != null) {
					idProof = idProofMapper.toEntity(idProofDTO);
					idProof.setImagePath(idProofDTO.getImagePath());
					idProof.setCreatedBy(user);
					idProof.setLastUpdateBy(user);
					idProof.setUser(user);
					user.setUserIdProof(idProof);

				} else {
					LOGGER.error("Registartion failed due to user ID proof details is empty for ->{}", userDTO);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"User ID Proof details is mandatory for user registration");
				}

				userRepo.save(user);
				UserDTO updatedUserDTO = userMapper.convert(user);
				updatedUserDTO.setUcDto(contactDetailsMapper.convert(ucDetails));
				updatedUserDTO.setUserIdProofDto(idProofMapper.convert(idProof));
				return updatedUserDTO;
			} // if
			else {
				LOGGER.error("Registartion failed due to user is empty for ->{}-{}", userDTO);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"User ID Proof details is mandatory for user registration");
			}

		}

	}

	@Override
	public String getToken(String userName) throws AdminException {
		User user = userRepo.getByUserName(userName);
		return user.getUcDetails().getFbToken();
	}

	@Override
	public boolean validatePhone(String phone, String userType) throws AdminException {
		if (phone.isEmpty()) {
			LOGGER.error("Registartion failed due to phone number is empty");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "A phone number is mandatory for new registration");

		} else {
			UserContactDetails ucDetails = null;
			ucDetails = userContactRepo.getUserContactsByPhoneNo(phone, userType);
			return ucDetails != null;
		}
	}

	@Override
	public String validateMobile(String phone, String userType) throws AdminException {
		UserContactDetails ucDetails = null;
		if (phone.isEmpty()) {
			LOGGER.error("Registartion failed due to phone number is empty");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "A phone number is mandatory for new registration");

		} else {

			ucDetails = userContactRepo.getUserContactsByPhoneNo(phone, userType);

			if (ucDetails != null) {
				userType = ucDetails.getUser().getUserType().getName();
				return userType;
			} else {
				LOGGER.error("Mobile number is not found for this userType : " + userType);
				throw new AdminException(ErrorCode.REQUEST_ERROR, "Please enter valid user type phone  number");
			}
		}
	}

	@Override
	public UserDTO forgetPasswordByPhone(String phone, String userType) throws AdminException {
		if (phone.isEmpty()) {
			LOGGER.error("password updation failed due to phone number is empty");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "A phone number is mandatory for password updation");

		} else {
			UserContactDetails ucDetails = null;
			// getting UserContactDetails entity by phone number from repository
			ucDetails = userContactRepo.getUserContactsByPhoneNo(phone, userType);
			if (ucDetails != null) {
				User user = ucDetails.getUser();
				UserDTO userDto = userMapper.convert(user);
				return userDto;
			} else {
				LOGGER.error(
						"password updation failed due to no user exists with the given phone number: (" + phone + ")");
				throw new AdminException(ErrorCode.REQUEST_ERROR, "No user exists with the given phone number: ("
						+ phone + "). please try with a valid  phone number");
			}
		}
	}

	@Override
	public String validateWebLogin(String phone, String password, String userType) throws AdminException {
		System.out.println("service phone:" + phone);
		System.out.println("service pass:" + password);
		UserContactDetails ucd = ucRepo.getUserContactsByPhoneNo(phone, userType);
		if (null != ucd) {
			User user = ucd.getUser();
			String userTypeName = user.getUserType().getName();
			UserPassword userPassword = user.getUserPassword();

			if (userPassword.getPassword().equals(password) && userTypeName.equals("U_TYPE_ADMIN")) {

				return "Logged in successfully";
			} else {
				LOGGER.error(
						"Either The given password " + password + " is not valid or the user type is not matching");
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Either password is not valid or the user type is not matching");
			}
		} else {
			LOGGER.error("The given Mobile Number " + phone + " is not registered");
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Mobile number is not valid");
		}

	}

	@Override
	public UserType saveUserType(AdminMetadata metaData) throws AdminException {
		UserType newUserType = null;
		if (metaData.getId() != null) {
			Optional<UserType> opt = userTypeRepo.findById(metaData.getId());
			if (opt.isPresent()) {
				newUserType = opt.get();
			}
		} else {
			newUserType = new UserType();
		}

		newUserType.setName(metaData.getName());
		newUserType.setDescription(metaData.getDescription());

		return userTypeRepo.save(newUserType);
	}

	@Override
	public AddressType saveAddressType(AdminMetadata metaData) throws AdminException {
		AddressType adrsType = new AddressType();
		adrsType.setName(metaData.getName());
		adrsType.setDescription(metaData.getDescription());

		return addressTypeRepo.save(adrsType);
	}

	@Override
	public ProofType saveIdProofType(AdminMetadata metaData) throws AdminException {
		ProofType proofType = new ProofType();
		proofType.setName(metaData.getName());
		proofType.setDescription(metaData.getDescription());
		return IdProofTypeRepo.save(proofType);
	}

	@Override
	public String logout(UserDTO userDto) throws AdminException {
		if (userDto == null) {
			LOGGER.error("Updation failed due to user information is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Insufficient information to update fbtoken");
		}
		User user = null;
		user = userRepo.findByUserName(userDto.getUserName());
		if (user != null) {
			user.getUcDetails().setFbToken(null);
			// user.getUcDetails().setDeviceId(null);
			userRepo.save(user);
			return "You have successfully logout";
		} else {
			LOGGER.error("No user exist for given user name ", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Please enter valid username");

		}

	}

	@Override
	public String uploadDocument(MultipartFile file) {
		String docPath = "";
		try {
			if (file.isEmpty() == false) {
				LOGGER.info("File entered for upload ");
				docPath = UploadFile.upload(file, "document");
			}
			return docPath;

		} catch (Exception e) {
			e.printStackTrace();
			return docPath;
		}
	}

	@Override
	public String serviceExeSendOtp(String mobile, String userType) throws AdminException, UnirestException {
		if (mobile.length() == 10) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
			if (ucDetails != null) {
				User user = ucDetails.getUser();
				String name = user.getUserType().getName();

				System.out.println(name);
				if (name.equals("U_TYPE_SERVICE_EXECUTOR")) {
					String url = sendOtpBaseApi + sendOtpBaseTemplate
							+ partnerAssociateLoginOTP + msg91MobileNo + indCode
							+ mobile + sendOtpAuthKey + authKey
							+ expiryOtp + otpExpiryTime;
					HttpResponse<String> response = Unirest.get(url).asString();
					return response.getBody();
				} else {
					LOGGER.error(
							"Send otp failed due to  user type is not matching  with this given phone number" + mobile);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"Send otp failed due to  user type is not matching with this given phone number: " + mobile
									+ " ,please provide a valid phone number");
				}
			} else {
				LOGGER.error("Send otp failed due to no user exists with this given phone number" + mobile);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Send otp failed due to no user exists with this given phone number: " + mobile
								+ " ,please provide a valid phone number");
			}

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public UserDTO serviceExeVerifyOtp(String mobile, String otp, String userType)
			throws AdminException, UnirestException {
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + mobile + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		String msg = response.getBody().toString();
		JSONObject jsonObj = new JSONObject(msg);
		String string = jsonObj.getString("message").toString();

		if (response.getStatus() == 200 && string.equals("OTP verified success")) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
			User user = ucDetails.getUser();
			UserDTO userDTO = userMapper.convert(user);
			return userDTO;
		} else {
			LOGGER.error("Otp is not matching for this mobile number: " + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Otp is not matching, please provide valid otp");
		}
	}

	@Override
	public String userRegistrationOtp(String phone, String userType) throws AdminException, UnirestException {
		if (phone.length() == 10) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(phone, userType);
			if (ucDetails == null) {
				String url = sendOtpBaseApi + sendOtpBaseTemplate
						+ userRegistrationOTP + msg91MobileNo
						+ indCode + phone + sendOtpAuthKey
						+ authKey + expiryOtp + otpExpiryTime;
				HttpResponse<String> response = Unirest.get(url).asString();
				return response.getBody();
			} else {
				LOGGER.error("Send otp failed due to An user already exists with this given phone number: " + phone);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Send otp failed due An user already exists with this given phone number: " + phone
								+ " ,please register with another number");
			}

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + phone);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public String userRegistrationVerifyOtp(String phone, String otp, String userType)
			throws AdminException, UnirestException {
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + phone + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		return response.getBody();

	}

	@Override
	public String userLoginSendOtp(String mobile, String userType) throws AdminException, UnirestException {
		if (mobile.length() == 10) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
			if (ucDetails != null) {
				User user = ucDetails.getUser();
				String name = user.getUserType().getName();
				if (name.equals("U__TYPE_FARMER")) {
					String url = sendOtpBaseApi + sendOtpBaseTemplate
							+ userLoginOTP + msg91MobileNo
							+ indCode + mobile + sendOtpAuthKey
							+ authKey + expiryOtp
							+ otpExpiryTime;
					HttpResponse<String> response = Unirest.get(url).asString();
					return response.getBody();
				} else {
					LOGGER.error(
							"Send otp failed due to  user type is not matching  with this given phone number" + mobile);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"Send otp failed due to user type is not matching with this given phone number: " + mobile
									+ " ,please provide a valid phone number");
				}
			} else {
				LOGGER.error("Send otp failed due to no user exists with this given phone number" + mobile);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Send otp failed due to no user exists with this given phone number: " + mobile
								+ " ,please provide a valid phone number");
			}

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public UserDTO userLoginVerifyOtp(String mobile, String otp, String userType)
			throws AdminException, UnirestException {
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + mobile + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		String msg = response.getBody().toString();
		JSONObject jsonObj = new JSONObject(msg);
		String string = jsonObj.getString("message").toString();

		if (response.getStatus() == 200 && string.equals("OTP verified success")) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
			User user = ucDetails.getUser();
			List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
			for (Address adrs : user.getListAddress()) {
				AddressDTO adrsDto = adrsMapper.convert(adrs);
				listAddressDto.add(adrsDto);
			}
			UserDTO userDTO = userMapper.convert(user);
			userDTO.setAddressdto(listAddressDto);
			userDTO.setUcDto(contactDetailsMapper.convert(ucDetails));
			return userDTO;
		} else {
			LOGGER.error("Otp is not matching for this mobile number: " + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Otp is not matching, please provide valid otp");
		}
	}

	@Override
	public String ServiceOwnerRegistrationOtp(String phone, String userType) throws AdminException, UnirestException {
		if (phone.length() == 10) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(phone, userType);
			if (ucDetails == null) {
				String url = sendOtpBaseApi + sendOtpBaseTemplate
						+ partnerRegistrationOTP + msg91MobileNo
						+ indCode + phone + sendOtpAuthKey
						+ authKey + expiryOtp + otpExpiryTime;
				HttpResponse<String> response = Unirest.get(url).asString();
				UserActivity activity=new UserActivity();
				activity.setApiType("REGISTRATION");
				activity.setApiTypeDetails(AdminConstant.LOGIN_OTP);
				activity.setfName("string");
				activity.setlName("string");
				activity.setMobileNo(phone);
				activity.setTimeStamp(LocalDateTime.now());
				activity.setUsertype(userType);
				userActivityRepo.save(activity);
				return response.getBody();
			} else {
				LOGGER.error("Send otp failed due to An user already exists with this given phone number: " + phone);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Send otp failed due An user already exists with this given phone number: " + phone
								+ " ,please register with another number");
			}

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + phone);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public String serviceOwnerRegistrationVerifyOtp(String phone, String otp, String userType)
			throws AdminException, UnirestException {
		
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + phone + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		return response.getBody();
	}

	@Override
	public String serviceOwnerLoginSendOtp(String mobile, String userType) throws AdminException, UnirestException {
		if (mobile.length() == 10) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
			if (ucDetails != null) {
				User user = ucDetails.getUser();
				String name = user.getUserType().getName();
				if (name.equals("U_TYPE_SERVICE_PROVIDER")) {
					String url = sendOtpBaseApi + sendOtpBaseTemplate
							+ partnerLoginOTP + msg91MobileNo + indCode
							+ mobile + sendOtpAuthKey + authKey
							+ expiryOtp + otpExpiryTime;
					HttpResponse<String> response = Unirest.get(url).asString();
					UserActivity activity=new UserActivity();
					activity.setApiType("LOGIN");
					activity.setApiTypeDetails(AdminConstant.LOGIN_OTP);
					activity.setfName("string");
					activity.setlName("string");
					//activity.setUserName("string");
					activity.setMobileNo(mobile);
					activity.setTimeStamp(LocalDateTime.now());
					activity.setUsertype(userType);
					userActivityRepo.save(activity);
					return response.getBody();
				} else {
					LOGGER.error(
							"Send otp failed due to  user type is not matching  with this given phone number" + mobile);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"Send otp failed due to user type is not matching with this given phone number: " + mobile
									+ " ,please provide a valid phone number");
				}
			} else {
				LOGGER.error("Send otp failed due to no user exists with this given phone number" + mobile);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Send otp failed due to no user exists with this given phone number: " + mobile
								+ " ,please provide a valid phone number");
			}

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public UserDTO serviceOwnerLoginVerifyOtp(String mobile, String otp, String userType)
			throws AdminException, UnirestException {
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + mobile + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		String msg = response.getBody().toString();
		JSONObject jsonObj = new JSONObject(msg);
		String string = jsonObj.getString("message").toString();

		if (response.getStatus() == 200 && string.equals("OTP verified success")) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
			User user = ucDetails.getUser();
			List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
			for (Address adrs : user.getListAddress()) {
				AddressDTO adrsDto = adrsMapper.convert(adrs);
				listAddressDto.add(adrsDto);
			}
			UserDTO userDTO = userMapper.convert(user);
			userDTO.setAddressdto(listAddressDto);
			userDTO.setUcDto(contactDetailsMapper.convert(ucDetails));
			return userDTO;
		} else {
			LOGGER.error("Otp is not matching for this mobile number: " + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Otp is not matching, please provide valid otp");
		}
	}

	@Override
	public String userSendResetPasswordOtp(String mobile) throws AdminException, UnirestException {
		if (mobile.length() == 10) {
			String url = sendOtpBaseApi + sendOtpBaseTemplate
					+ userResetPasswordOTP + msg91MobileNo
					+ indCode + mobile + sendOtpAuthKey + authKey
					+ expiryOtp + otpExpiryTime;
			HttpResponse<String> response = Unirest.get(url).asString();
			return response.getBody();

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public String userResetPwdVerifyOtp(String mobile, String otp, String userType)
			throws AdminException, UnirestException {
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + mobile + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		return response.getBody();
	}

	@Override
	public String ServiceOwnerSendResetPasswordOtp(String mobile) throws AdminException, UnirestException {
		if (mobile.length() == 10) {
			String url = sendOtpBaseApi + sendOtpBaseTemplate
					+ partnerResetPasswordOTP + msg91MobileNo + indCode
					+ mobile + sendOtpAuthKey + authKey
					+ expiryOtp + otpExpiryTime;
			HttpResponse<String> response = Unirest.get(url).asString();
			return response.getBody();

		} else {
			LOGGER.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public String ServiceOwnerResetPwdVerifyOtp(String mobile, String otp, String userType)
			throws AdminException, UnirestException {
		String url = verifyOtp + authKey + msg91MobileNo
				+ indCode + mobile + sendOtp + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		return response.getBody();
	}

	@Override
	public String userRegistrationSuccessMsg(String mobile, String userType) throws AdminException, UnirestException {
		UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
		User user = ucDetails.getUser();
		String name = user.getfName() + " " + user.getlName();
		
		
		System.out.println("{\"flow_id\":\"" + userSuccessfulRegistration +"\",\"sender\":\"" + senderId + "\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + name + "\"}");
		
		System.out.println("\"authkey\", \"" + authKey +"\"");
		
		System.out.println("{\"flow_id\":\"60f180d23d038f02766ea8f5\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + name + "\"}");
		
		
		HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "362351Aw39Lf0Zt60c43e55P1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"60f180d23d038f02766ea8f5\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + name + "\"}")
				.asString();

		return response.getBody();
		
		/*HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "367508AQf0Ro5Bj614b119aP1" ).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + userSuccessfulRegistration +"\",\"sender\":\"" + senderId + "\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + name + "\"}")
				.asString();
		
		return response.getBody();*/
		//return null;
	}

	@Override
	public String serviceOwnerRegistrationSuccessMsg(String mobile, String userType)
			throws AdminException, UnirestException {
		UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(mobile, userType);
		User user = ucDetails.getUser();
		String name = user.getfName() + " " + user.getlName();
		
		/*HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "367508AQf0Ro5Bj614b119aP1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"6151a49e78b31a4ed65b9b83\",\"sender\":\"VYVSHY\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + name + "\"}")
				.asString();
		
		return response.getBody();*/
		
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey ).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerSuccessfulRegistration +"\",\"sender\":\"" + senderId + "\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + name + "\"}")
				.asString();
		
		return response.getBody();
		
	}

	@Override
	public String userBookingPendingMsg(String mobile) throws AdminException, UnirestException {
		/*HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "362351Aw39Lf0Zt60c43e55P1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"60f194bda1b14c10652b56c1\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\"}")
				.asString();
		
		return response.getBody();*/
		
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + userBookingPending +"\",\"sender\":\"" + senderId +"\",\"mobiles\":\"" +indCode + mobile
						+ "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String userBookingAcceptanceMsg(String mobile) throws AdminException, UnirestException {
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + userBookingAcceptance +"\",\"sender\":\"" + senderId +"\",\"mobiles\":\"" +indCode + mobile
						+ "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String userBookingCancelMsg(String mobile) throws AdminException, UnirestException {
		HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "362351Aw39Lf0Zt60c43e55P1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"60f197077162d00b5a7cf360\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String referralMsg(List<String> mobile, String userName, String userType)
			throws AdminException, UnirestException {
		// TODO Auto-generated method stub

		String result = null;
		for (String phone : mobile) {
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(phone, userType);
			if (ucDetails != null) {
				LOGGER.error(
						"this phone number is already exists with us. please refer some other phone number" + phone);
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"this phone number is already exists with us. please refer some other phone number" + mobile);

			} else {
				ReferralCodeContact referralCodeContact = referralCodeContactRepo.findByMobileNumber(phone);
				// System.out.println(ucDetails);
				if (referralCodeContact != null) {
					LOGGER.error(
							"this phone number is already referred by our user, please refer some other phone number"
									+ phone);
					throw new AdminException(ErrorCode.REQUEST_ERROR,
							"this phone number is alredy referred by our user, please refer some other phone number"
									+ mobile);

				} else {
					User user = userRepo.findByUserName(userName);
					String code = user.getReferral().getReferralCode();
					int i = user.getReferral().getTotalReferred();
					System.out.println(code);
					referralCodeContact = new ReferralCodeContact();
					referralCodeContact.setLastUpdateBy(user);
					referralCodeContact.setLastUpdateDate(LocalDateTime.now());
					referralCodeContact.setMailSend(false);
					referralCodeContact.setMessageSend(true);
					referralCodeContact.setMessageSentTime(LocalDateTime.now());
					referralCodeContact.setMobileNumber(phone);
					referralCodeContact.setReferralCode(user.getReferral());
					referralCodeContactRepo.save(referralCodeContact);
					user.getReferral().setTotalReferred(i + 1);
					userRepo.save(user);
					String name = user.getfName() + " " + user.getlName();
					System.out.println(name);
					String link = "https://www.google.com";
					HttpResponse<String> response = Unirest.post(smsFlowUrl)
							.header("authkey", authKey).header("content-type", "application/JSON")
							.body("{\"flow_id\":\"" + userReferralFriend + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode
									+ phone + "\",\"NAME\":\"" + name + "\",\"CODE\":\"" + code + "\",\"LINK\":\""
									+ link + "\"}")
							.asString();

					result = response.getBody();
				}
			}

		}

		return result;
	}

	@Override
	public ReferralDTO getReferralProfile(String userName) throws AdminException {
		if (userName != null) {
			User user = userRepo.findByUserName(userName);
			if (user != null) {
				ReferralDTO referralDto = referMapper.convert(user.getReferral());
				return referralDto;
			} else {
				LOGGER.error("No user exists with this given user name: " + userName);
				throw new AdminException(ErrorCode.REQUEST_ERROR, "No user exists with this given user name: ("
						+ userName + "), please provide a valid user name");
			}
		} else {
			LOGGER.error("input information is mandatory");
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"user name must not be empty, please provide an user name");

		}

	}

	@Override
	public ServiceExecutorSaveRequestDTO findByUserName(String userName, String deviceToken) {
		User user = null;
		ServiceExecutorSaveRequestDTO requestDTO = new ServiceExecutorSaveRequestDTO();
		user = userRepo.findByUserName(userName);
		if (user != null) {
			UserContactDetails ucDetails = user.getUcDetails();
			requestDTO.setUserName(userName);
//			if(deviceToken.equalsIgnoreCase(ucDetails.getFbToken()))
//			{
			requestDTO.setFbToken(deviceToken);
//			}
//			else
//			{
//				LOGGER.error("Device Token does not exist.");
//			}
//			

		} else {
			LOGGER.error("this user name does not exist.");

		}
		return requestDTO;
	}

	@Override
	// public UserContactDetailsDTO updateToken(UserDTO userDto) throws
	// AdminException {
	public String updateToken(UserDTO userDto) throws AdminException {
		// TODO Auto-generated method stub
		if (userDto == null) {
			LOGGER.error("Updation failed due to user information is empty for ->{}", userDto);
			throw new AdminException(ErrorCode.REQUEST_ERROR, "Insufficient information to update token");
		}
		User user = null;
		user = userRepo.findByUserName(userDto.getUserName());
		if (user != null) {
			UserContactDetails ucDetails = user.getUcDetails();
			// System.out.println(ucDetails);
			// System.out.println(userDto.getUcDto().getPhoneNo());
			// System.out.println(ucDetails.getPhoneNo());
			if (user.getUserName().equals(userDto.getUserName())) {
				user.setLastUpdateDate(LocalDateTime.now());
				ucDetails.setFbToken(userDto.getUcDto().getFbToken());

				ucDetails.setLastUpdateDate(LocalDateTime.now());
				user.setUcDetails(ucDetails);
				userRepo.save(user);
				// return contactDetailsMapper.convert(ucDetails);
				return "You have login successfully";
			} else {
				LOGGER.error("updation failed due to given username is not exist ->{}", userDto.getUserName());
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Given Username : (" + userDto.getUserName() + ") is not matching with existing user");

			}
		} else {
			LOGGER.error("updation failed due to username is empty for ->{}", userDto.getUserName());
			throw new AdminException(ErrorCode.REQUEST_ERROR,
					"Given user name: (" + userDto.getUserName() + ") is not an existing user");

		}
	}

	@Override
	public NotificationMessage makeNotificationMessage(String title, String param) {

		NotificationMessage notificationRequest = null;// new NotificationMessage().getMessage(title, param);
		return notificationRequest;
	}

	@Override
	@Transactional
	public OrderResponseDTO updateOrders(String orderId, String status, String userId) throws AdminException {
		Order orderObj = null;
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		OrderDetails odObj = orderDetailsTypeRepository.findByOrderId(orderId, userId);
		// String status = odObj.getOrderStatus();
//			if(orderObj.getCreatedBy().equals(userId))
//			{
		// for order
//			orderObj = orderRepositoty.findByOrderId(orderId);
//			orderObj.setOrderStatus("ORDER_CANCELLED");
//			orderObj.setUpdatedAt(new Date());
//			orderObj.setUpdatedBy(userId);
//			orderObj.setServiceExecutorName(odObj.getServiceDetailsId().getServiceExecutorName());
//			orderObj.setServiceOwnerName(odObj.getServiceDetailsId().getServiceOwnerName());
//			orderObj.setServiceOwnerMobile(odObj.getServiceDetailsId().getPhoneNo());
//			orderRepositoty.save(orderObj);
//			// for orderDetails
//			odObj.getOrder().setOrderStatus("ORDER_CANCELLED");
//			odObj.setOrderStatus("ORDER_CANCELLED");
//			odObj.setPaymentStatus("PAYMENT_CANCELLED");
//			odObj.setUpdatedAt(new Date());
//			odObj.setUpdatedBy(userId);
//			odObj.setIsActive(false);
//			orderDetailsTypeRepository.save(odObj);
//			
//			
//			}
//			else
//			{
//				logger.error("User Id does not exist. ");
//				throw new BookingException(ErrorCode.REQUEST_ERROR, "You are not authorize");
//			}
		orderObj = orderRepository.findByOrderId(orderId);
		orderObj.setOrderStatus(status);
		orderObj.setUpdatedAt(new Date());
		orderObj.setUpdatedBy(userId);
		orderObj.setServiceExecutorName(odObj.getServiceDetailsId().getServiceExecutorName());
		orderObj.setServiceOwnerName(odObj.getServiceDetailsId().getServiceOwnerName());
		orderObj.setServiceOwnerMobile(odObj.getServiceDetailsId().getPhoneNo());
		orderRepository.save(orderObj);

		if (status.equals("ACCEPT")) {
			odObj.getOrder().setOrderStatus("ORDER_ACCEPTED");
			odObj.setOrderStatus("ORDER_ACCEPTED");
			odObj.setUpdatedAt(new Date());
			odObj.setUpdatedBy(userId);
			odObj.setServiceExLattitude(odObj.getServiceDetailsId().getLattitude());
			odObj.setServiceExLongitude(odObj.getServiceDetailsId().getLongitude());
			odObj.setServiceExecutorName(odObj.getServiceDetailsId().getServiceExecutorName());
			orderDetailsTypeRepository.save(odObj);

			List<OrderDetails> odLst = orderDetailsTypeRepository.updateOtherOrders(orderId, "NEW");
			odLst.stream().filter(od -> !od.getCreatedAt().equals(userId)).collect(Collectors.toList());
			odLst.forEach(od -> od.setOrderStatus("ORDER_ACCEPTED_BY_OTHERS"));
			odLst.forEach(od -> od.setIsActive(false));
			orderDetailsTypeRepository.saveAll(odLst);
		}

		else if (status.equals("CANCEL")) {
			long result = getAllAcceptedList(userId, orderId);
			if (result > 24) {

				////////////////////////////////////////////////////

				// ServiceType sc =
				// serviceRepository.getOne(((orderRequestDTO.getServiceId())));
				/*	Order orderObject =orderRepository.findByOrderId(orderId);
					 System.out.println(orderObj);
				    RestTemplate restTemplate = new RestTemplate();
					HttpHeaders headers = new HttpHeaders();
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					HttpEntity<Order> entity = new HttpEntity<Order>(orderObject, headers);
					ResponseEntity<Order> out = restTemplate.exchange(
							AdminConstant.BOOKING_APPLICATION_BASEURL + AdminConstant.RECREATE_ORDER, HttpMethod.POST,
							entity, Order.class);
					//////////////////////////////////////////
					HttpStatus httpStatus = out.getStatusCode();
					System.out.println(httpStatus);*/
				// if (httpStatus == HttpStatus.OK) {
				odObj.getOrder().setOrderStatus("NEW_ORDER_CREATED");
				odObj.setOrderStatus("ORDER_CANCELLED");
				odObj.setPaymentStatus("PAYMENT_CANCELLED");
				odObj.setUpdatedAt(new Date());
				odObj.setIsActive(false);
				odObj.setUpdatedBy(userId);
				odObj.setServiceExLongitude(odObj.getServiceDetailsId().getLongitude());
				odObj.setServiceExecutorName(odObj.getServiceDetailsId().getServiceExecutorName());

				orderDetailsTypeRepository.save(odObj);
				List<OrderDetails> odLst = orderDetailsTypeRepository.updateOtherOrders(orderId,
						"ORDER_ACCEPTED_BY_OTHERS");
				odLst.stream().filter(od -> !od.getCreatedAt().equals(userId)).collect(Collectors.toList());
				odLst.forEach(od -> od.setOrderStatus("NEW"));
				odLst.forEach(od -> od.setIsActive(true));
				orderDetailsTypeRepository.saveAll(odLst);
				// }
			} else {
				LOGGER.error("You can not can cel the order");
				throw new AdminException(ErrorCode.REQUEST_ERROR,
						"Your Cancellation of order time is expired so you can,t cancel the order");
			}
		} else if (status.equals("COMPLETE")) {
			odObj.getOrder().setOrderStatus("ORDER_COMPLETED");
			odObj.setOrderStatus("ORDER_COMPLETED");
			odObj.setPaymentStatus("PAYMENT_COMPLETED");
			odObj.setUpdatedAt(new Date());
			odObj.setUpdatedBy(userId);
			odObj.setIsActive(false);
			orderDetailsTypeRepository.save(odObj);

			orderResponseDTO.setMessage("Order Updated");
			orderResponseDTO.setOrderId(orderId);
			orderResponseDTO.setOrderStatus(status);
			return orderResponseDTO;
		}
		orderResponseDTO.setMessage("Order Updated");
		orderResponseDTO.setOrderId(orderId);
		orderResponseDTO.setOrderStatus(odObj.getOrder().getOrderStatus());
		return orderResponseDTO;
	}

	@Override
	public long getAllAcceptedList(String username, String orderId) {
		long diff;
		OrderDetails orderObj = orderDetailsTypeRepository.findByAcceptedList(username, orderId);
		Date dateStart = orderObj.getOrderExecutionTime();
		System.out.println("datestart--" + dateStart);
		Date currentDate = new Date();
		diff = ((dateStart.getTime() - currentDate.getTime()) / (60 * 60 * 1000));
		System.out.println("currentDate--" + currentDate);
		System.out.println("Total hours" + diff);
		return diff;
	}

	@Override
	public String getUserIdByOrder(String orderId) {
		String createdBy = orderDetailsTypeRepository.findByOrderId(orderId);
		return createdBy;
	}

	@Override
	public NotificationDetails saveNotificationDetails(NotificationDetails notificationRequest) {
		return notificationDetailsRepository.save(notificationRequest);
	}

	@Override
	public int updateNotificationStatus(int notificationId) {
		int status = notificationDetailsRepository.updateNotificationStatus(notificationId);
		return status;
	}

	@Override
	public List<NotificationDetails> fetchAllNotification(String receiverId) {
		List<NotificationDetails> notificationList = notificationDetailsRepository.findAllByRId(receiverId);
		return notificationList;
	}

	@Override
	public int updateExecutorStatus(String seid) {
		int result = 0;
		result = userRepo.updateExecutorStatus(seid);
		return result;
	}

	public boolean getUserStatus(String seid) {
		boolean status = true;
		int result = userRepo.getUserStatus(seid);
		if (result > 0)
			status = true;
		else
			status = false;

		return status;

	}

	@Override
	public List<AdminFarmerDto> fetchAllFarmer() {
		List<User> listUser = userRepo.findAll();
		List<AdminFarmerDto> listFarmerDto = new ArrayList<AdminFarmerDto>();
		listUser.forEach(user -> {
			if (user.getUserType().getName().equals("U__TYPE_FARMER")) {
				// UserDTO userDto = userMapper.convert(user);
				AdminFarmerDto adminFarmerDto = new AdminFarmerDto();
				String fName = user.getfName();
				String lName = user.getlName();
				adminFarmerDto.setName(fName + " " + lName);

				// UserContactDetailsDTO ucDto =
				// contactDetailsMapper.convert(user.getUcDetails());
				// userDto.setUcDto(ucDto);
				adminFarmerDto.setEmail(user.getUcDetails().geteMail());
				adminFarmerDto.setPhone(user.getUcDetails().getPhoneNo());

				// UserPasswordDTO userPwdDto = userPwdMapper.convert(user.getUserPassword());
				// userDto.setUserPasswordDto(userPwdDto);

				/*AccountDetails accDetails = null;
				accDetails = user.getAccDetails();
				if (accDetails != null) {
					AccountDetailsDTO accDetailsDto = accountMapper.convert(accDetails);
					userDto.setAccDetailsDto(accDetailsDto);
				}
				Referral refer = null;
				refer = user.getReferral();
				if (refer != null) {
					ReferralDTO referDto = referMapper.convert(refer);
					referDto.setGeneratedFor(user.getId());
					userDto.setReferralDto(referDto);
				}
				UserIdProof uidProof = null;
				uidProof = user.getUserIdProof();
				if (uidProof != null) {
					UserIdProofDTO userIdProofDto = idProofMapper.convert(uidProof);
					userDto.setUserIdProofDto(userIdProofDto);
				}*/

				List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
				for (Address adrs : user.getListAddress()) {
					// AddressDTO adrsDto = adrsMapper.convert(adrs);
					// listAddressDto.add(adrsDto);
					adminFarmerDto.setAddress(adrs.getAddress());
				}

				// userDto.setAddressdto(listAddressDto);

				listFarmerDto.add(adminFarmerDto);
			}

		});
		return listFarmerDto;

	}

	@Override
	public List<AdminFarmerDto> fetchAllServiceOwner() {
		List<User> listUser = userRepo.findAll();
		List<AdminFarmerDto> listFarmerDto = new ArrayList<AdminFarmerDto>();
		listUser.forEach(user -> {
			if (user.getUserType().getName().equals("U_TYPE_SERVICE_PROVIDER")) {
				// UserDTO userDto = userMapper.convert(user);
				AdminFarmerDto adminFarmerDto = new AdminFarmerDto();
				String fName = user.getfName();
				String lName = user.getlName();
				adminFarmerDto.setName(fName + " " + lName);

				// UserContactDetailsDTO ucDto =
				// contactDetailsMapper.convert(user.getUcDetails());
				// userDto.setUcDto(ucDto);
				adminFarmerDto.setEmail(user.getUcDetails().geteMail());
				adminFarmerDto.setPhone(user.getUcDetails().getPhoneNo());

				// UserPasswordDTO userPwdDto = userPwdMapper.convert(user.getUserPassword());
				// userDto.setUserPasswordDto(userPwdDto);

				/*AccountDetails accDetails = null;
				accDetails = user.getAccDetails();
				if (accDetails != null) {
					AccountDetailsDTO accDetailsDto = accountMapper.convert(accDetails);
					userDto.setAccDetailsDto(accDetailsDto);
				}
				Referral refer = null;
				refer = user.getReferral();
				if (refer != null) {
					ReferralDTO referDto = referMapper.convert(refer);
					referDto.setGeneratedFor(user.getId());
					userDto.setReferralDto(referDto);
				}
				UserIdProof uidProof = null;
				uidProof = user.getUserIdProof();
				if (uidProof != null) {
					UserIdProofDTO userIdProofDto = idProofMapper.convert(uidProof);
					userDto.setUserIdProofDto(userIdProofDto);
				}*/

				List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
				for (Address adrs : user.getListAddress()) {
					// AddressDTO adrsDto = adrsMapper.convert(adrs);
					// listAddressDto.add(adrsDto);
					adminFarmerDto.setAddress(adrs.getAddress());
				}

				// userDto.setAddressdto(listAddressDto);

				listFarmerDto.add(adminFarmerDto);
			}

		});
		return listFarmerDto;

	}

	@Override
	public List<AdminFarmerDto> fetchAllAdminUser() {
		List<User> listUser = userRepo.findAll();
		List<AdminFarmerDto> listFarmerDto = new ArrayList<AdminFarmerDto>();
		listUser.forEach(user -> {
			if (user.getUserType().getName().equals("U_TYPE_ADMIN")) {
				// UserDTO userDto = userMapper.convert(user);
				AdminFarmerDto adminFarmerDto = new AdminFarmerDto();
				String fName = user.getfName();
				String lName = user.getlName();
				adminFarmerDto.setName(fName + " " + lName);

				// UserContactDetailsDTO ucDto =
				// contactDetailsMapper.convert(user.getUcDetails());
				// userDto.setUcDto(ucDto);
				adminFarmerDto.setEmail(user.getUcDetails().geteMail());
				adminFarmerDto.setPhone(user.getUcDetails().getPhoneNo());

				List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
				for (Address adrs : user.getListAddress()) {
					// AddressDTO adrsDto = adrsMapper.convert(adrs);
					// listAddressDto.add(adrsDto);
					adminFarmerDto.setAddress(adrs.getAddress());
				}

				// userDto.setAddressdto(listAddressDto);

				listFarmerDto.add(adminFarmerDto);
			}

		});
		return listFarmerDto;

	}

	@Override
	public List<AdminFarmerDto> fetchAllExecutor() {
		List<AdminFarmerDto> listFarmerDto = new ArrayList<AdminFarmerDto>();
		List<ServiceDetails> listserviceDetails = serviceDetailsRepo.getAllExecutor();
		listserviceDetails.forEach(serviceDetails -> {
			AdminFarmerDto adminFarmerDto = new AdminFarmerDto();
			ServiceType service = serviceTypeRepo.findByName(serviceDetails.getServiceType());
			adminFarmerDto.setServiceAllocated(service.getDescription());
			adminFarmerDto.setExecutorContactType(service.getServiceExContactType().getDescription());
			adminFarmerDto.setPhone(serviceDetails.getPhoneNo());
			UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(serviceDetails.getPhoneNo(),
					"U_TYPE_SERVICE_EXECUTOR");
			adminFarmerDto.setEmail(ucDetails.geteMail());

			User user = ucDetails.getUser();
			String fName = user.getfName();
			String lName = user.getlName();
			adminFarmerDto.setName(fName + " " + lName);
			adminFarmerDto.setIdProof(serviceDetails.getIdproofPhoto());

			List<AddressDTO> listAddressDto = new ArrayList<AddressDTO>();
			for (Address adrs : user.getListAddress()) {
				// AddressDTO adrsDto = adrsMapper.convert(adrs);
				// listAddressDto.add(adrsDto);
				adminFarmerDto.setAddress(adrs.getAddress());
			}
			listFarmerDto.add(adminFarmerDto);

		});

		return listFarmerDto;
	}

	@Override
	public AdminPanelDashboard getAdminDashboard() {

		/*int totalFarmers=userRepo.getTotalFarmers();
		int totalServiceOwners=userRepo.getTotalServiceOwners();
		System.out.println(totalFarmers);
		System.out.println(totalServiceOwners);*/

		AdminPanelDashboard adminPanelDB = new AdminPanelDashboard();
		adminPanelDB.setTotalUsersRegister(userRepo.getTotalFarmers());
		adminPanelDB.setTotalServiceOwnerRegister(userRepo.getTotalServiceOwners());
		adminPanelDB.setTotalServiceRequestRaised(orderRepository.getAllReq());
		return adminPanelDB;
	}

	@Override
	public Long findByMobileNumberAndUserType(String mobileNo, String userType) throws AdminException {
		UserContactDetails ucd = ucRepo.getUserContactsByPhoneNo(mobileNo, userType);
		Long id = ucd.getUser().getId();
		return id;
	}

	@Override
	public List<AdminPanelOrderStatus> getFarmerOrderStatus() {
		// TODO Auto-generated method stub
		List<Order> listOrder = orderRepository.findAll();
		List<AdminPanelOrderStatus> listOrderStatus = new ArrayList();
		listOrder.forEach(order -> {
			AdminPanelOrderStatus newOrder = new AdminPanelOrderStatus();
			newOrder.setPhoneNo(order.getRequesterContact());
			newOrder.setOrderId(order.getOrderId());
			// String invoiceDate = new
			// SimpleDateFormat("yyyy-MM-dd").format(order.getCreatedAt());
			// newOrder.setOrderDate(invoiceDate);
			newOrder.setOrderDate(order.getCreatedAt());
			newOrder.setServiceOrdered(order.getServiceId().getDescription());
			// newOrder.setCostOfService();
			listOrderStatus.add(newOrder);
		});
		return listOrderStatus;
	}

	@Override
	public List<AdminPanelOrderStatus> getOwnerOrderStatus() {
		List<Order> listOrder = orderRepository.findAll();
		List<AdminPanelOrderStatus> listOrderStatus = new ArrayList();
		listOrder.forEach(order -> {
			if (!order.getOrderStatus().equals("NEW_ORDER_CREATED")) {
				UserContactDetails ucDetails = ucRepo.getUserContactsByPhoneNo(order.getServiceOwnerMobile(),
						"U_TYPE_SERVICE_PROVIDER");
				if (ucDetails != null) {
					AdminPanelOrderStatus orderStatus = new AdminPanelOrderStatus();
					if (order.getOrderStatus().equals("ORDER_CANCELLED")) {
						orderStatus.setOrderDeclined(true);

					}
					
					orderStatus.setOrderId(order.getOrderId());
					orderStatus.setPhoneNo(order.getServiceOwnerMobile());
					orderStatus.setOrderDate(order.getCreatedAt());
					orderStatus.setServiceOrdered(order.getServiceId().getDescription());

					PaymentDetails paymentDetails = paymentRepo.getPaymentDetails(order.getOrderId());
					if (paymentDetails.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
						orderStatus.setCostOfService(paymentDetails.getTotalAmount());
						orderStatus.setCloserStatus(true);
					} else {
						orderStatus.setCostOfService(0.0);
						orderStatus.setCloserStatus(false);
					}
					listOrderStatus.add(orderStatus);
				}

			}

		});
		return listOrderStatus;
	}
	
	@Override
	public List<ReferralDTO> getAllReferralDetails() {
		// TODO Auto-generated method stub
		List<ReferralDTO> listReferralDto=new ArrayList();
		List<Referral> listReferral = referRepo.findAll();
		listReferral.forEach(referral->{
			ReferralDTO referralDto=new ReferralDTO();
			referralDto.setReferralCode(referral.getReferralCode());
			referralDto.setTotalReferred(referral.getTotalReferred());
			referralDto.setTotalInstalled(referral.getTotalInstalled());
			referralDto.setReferralPoint(referral.getReferralPoint());
			User user = userRepo.findByReferral(referral);
			referralDto.setPhone(user.getUcDetails().getPhoneNo());
			listReferralDto.add(referralDto);
		});
		return listReferralDto;
	}
	@Override
	  public List<ReferralDTO> getAllReferralMaster() {
	    List<ReferralDTO> listReferralDto=new ArrayList();
	    List<Referral> listReferral = referRepo.findAll();
	    listReferral.forEach(referral->{
	      ReferralDTO referralDto=new ReferralDTO();
	      User user=referral.getGeneratedfor();
	      String fName=user.getfName();
	      String lName=user.getlName();
	      referralDto.setName(fName+" "+lName);
	      referralDto.setReferralPoint(AdminConstant.REFERRAL_POINT);
	      listReferralDto.add(referralDto);
	    });
	    return listReferralDto;
	  }

	@Override
	public UserActivity saveUserActivity(UserActivity userActivity) {
		UserActivity activity = userActivityRepo.save(userActivity);
		return activity;
	}


}
