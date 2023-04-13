package com.sigmify.vb.booking.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sigmify.vb.booking.constants.BookingConstant;
import com.sigmify.vb.booking.dto.AdminMetadata;
import com.sigmify.vb.booking.dto.AdminPanelOrderStatus;
import com.sigmify.vb.booking.dto.DistanceMatrixRoot;
import com.sigmify.vb.booking.dto.MyServiceDetailsDTO;
import com.sigmify.vb.booking.dto.OrderDTO;
import com.sigmify.vb.booking.dto.OrderDetailsDTO;
import com.sigmify.vb.booking.dto.OrderRequestDTO;
import com.sigmify.vb.booking.dto.OrderResponseDTO;
import com.sigmify.vb.booking.dto.OrderUpdateDto;
import com.sigmify.vb.booking.dto.PaymentDto;
import com.sigmify.vb.booking.dto.PaymentInvoiceDTO;
import com.sigmify.vb.booking.dto.PaymentRequest;
import com.sigmify.vb.booking.dto.SearchResponse;
import com.sigmify.vb.booking.dto.SelfservicesDetailsDTO;
import com.sigmify.vb.booking.dto.ServiceCategoryTypeDTO;
import com.sigmify.vb.booking.dto.ServiceDTo;
import com.sigmify.vb.booking.dto.ServiceDetailsDTO;
import com.sigmify.vb.booking.dto.ServiceExecutorListDTO;
import com.sigmify.vb.booking.dto.ServiceProcessingFee;
import com.sigmify.vb.booking.dto.ServiceRequest;
import com.sigmify.vb.booking.dto.ServiceResponse;
import com.sigmify.vb.booking.dto.ServiceSubCatMetaData;
import com.sigmify.vb.booking.dto.ServiceSubCategoryDto;
import com.sigmify.vb.booking.dto.ServiceSubCategoryTypeDTO;
import com.sigmify.vb.booking.dto.ServiceTypeDTO;
import com.sigmify.vb.booking.dto.ServiceTypeMetaData;
import com.sigmify.vb.booking.dto.TrackOrderDTO;
import com.sigmify.vb.booking.dto.UpdateLatLongDTO;
import com.sigmify.vb.booking.dto.UserDTO;
import com.sigmify.vb.booking.endpoints.OrderController;
import com.sigmify.vb.booking.entity.NotificationDetails;
import com.sigmify.vb.booking.entity.Order;
import com.sigmify.vb.booking.entity.OrderDetails;
import com.sigmify.vb.booking.entity.OrderStatusType;
import com.sigmify.vb.booking.entity.PaymentDetails;
import com.sigmify.vb.booking.entity.PaymentInvoice;
import com.sigmify.vb.booking.entity.PaymentLog;
import com.sigmify.vb.booking.entity.Price;
import com.sigmify.vb.booking.entity.ServiceCategoryType;
import com.sigmify.vb.booking.entity.ServiceDetails;
import com.sigmify.vb.booking.entity.ServiceExecutorContactType;
import com.sigmify.vb.booking.entity.ServiceStatusUser;
import com.sigmify.vb.booking.entity.ServiceSubCategoryType;
import com.sigmify.vb.booking.entity.ServiceType;
import com.sigmify.vb.booking.entity.UnitMeasurementType;
import com.sigmify.vb.booking.enums.ErrorCode;
import com.sigmify.vb.booking.exception.BookingException;
import com.sigmify.vb.booking.firebase.NotificationMessage;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;
import com.sigmify.vb.booking.mapperImpl.OrderDetailsMapper;
import com.sigmify.vb.booking.mapperImpl.OrderMapper;
import com.sigmify.vb.booking.mapperImpl.OrdersMapper;
import com.sigmify.vb.booking.mapperImpl.PaymentDetailsMapper;
import com.sigmify.vb.booking.mapperImpl.ServiceDetailsMapper;
import com.sigmify.vb.booking.repository.CategoryTypeRepository;
import com.sigmify.vb.booking.repository.NotificationDetailsRepository;
import com.sigmify.vb.booking.repository.OrderDetailsTypeRepository;
import com.sigmify.vb.booking.repository.OrderRepository;
import com.sigmify.vb.booking.repository.OrderStatusTypeRepository;
import com.sigmify.vb.booking.repository.PaymentInvoiceRepository;
import com.sigmify.vb.booking.repository.PaymentLogRepository;
import com.sigmify.vb.booking.repository.PaymentRepository;
import com.sigmify.vb.booking.repository.PriceRepository;
import com.sigmify.vb.booking.repository.ServiceDetailsRepository;
import com.sigmify.vb.booking.repository.ServiceExecutorContactTypeRepository;
import com.sigmify.vb.booking.repository.ServiceStatusRepository;
import com.sigmify.vb.booking.repository.ServiceTypeRepository;
import com.sigmify.vb.booking.repository.SubCategoryRepository;
import com.sigmify.vb.booking.repository.UnitMeasurementTypeRepository;
import com.sigmify.vb.booking.service.BookingService;
import com.sigmify.vb.booking.util.GenerateUniqueCode;
import com.sigmify.vb.booking.util.RandomString;
import com.sigmify.vb.booking.util.UploadFile;

@Service
public class BookingServiceImpl implements BookingService {
	@Value("${app.due.payment}")
	private Double payment;

	@Autowired
	private CategoryTypeRepository categoryTypeRepository;
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	@Autowired
	private ServiceTypeRepository serviceRepository;
	@Autowired
	private UnitMeasurementTypeRepository unitRepository;
	@Autowired
	private PriceRepository priceRepository;
	@Autowired
	private ServiceExecutorContactTypeRepository serviceExecutorRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ServiceStatusRepository serviceStatusRepository;
	@Autowired
	private OrderStatusTypeRepository orderStatusTypeRepository;
	@Autowired
	private OrderDetailsTypeRepository orderDetailsTypeRepository;
	@Autowired
	private ServiceDetailsRepository serviceDetailsRepository;
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private PaymentInvoiceRepository invoiceRepository;

	@Autowired
	private PaymentLogRepository paymentLogRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private ServiceDetailsMapper serviceDetailsMapper;
	@Autowired
	private OrderDetailsMapper orderDetailsMapper;
	@Autowired
	private PaymentDetailsMapper paymentDetailsMapper;
	private final Logger logger = Logger.getLogger(BookingServiceImpl.class);

	@Autowired
	private NotificationDetailsRepository notificationDetailsRepository;

	// msg91 PropertiesFile
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

	@Value("${User.Service.Start.OTP}")
	private String userServiceStartOTP;
	
	@Value("${User.Service.Completion.OTP}")
	private String userServiceCompletionOTP;
	
	@Value("${sms.flow.url}")
	private String smsFlowUrl;
	
	@Value("${Partner.Service.Request.Updation}")
	private String partnerServiceRequestUpdation;
	
	@Value("${Partner.Service.Request.Cancellation}")
	private String partnerServiceRequestCancellation;
	
	@Value("${Partner.Associate.Service.Cancellation}")
	private String partnerAssociateServiceCancellation;
	
	@Value("${Partner.Associate.Enrolment}")
	private String partnerAssociateEnrolment;
	
	@Value("${Partner.Associate.Order.Updation}")
	private String partnerAssociateOrderUpdation;
	
	@Value("${Partner.Associate.Service.Allocation}")
	private String partnerAssociateServiceAllocation;

	/*@Override
	public List<ServiceCategoryType> getActiveCategoryList() {
		List<ServiceCategoryType> catLst = null;
		try {
			catLst = categoryTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
			return catLst;
		} catch (Exception e) {
			e.printStackTrace();
			return catLst;
		}
	}*/

	@Override
	public List<ServiceCategoryTypeDTO> getActiveCategoryList(String lang) {
		List<ServiceCategoryTypeDTO> scDtoList = new ArrayList<ServiceCategoryTypeDTO>();
		try {
			List<ServiceCategoryType> scList = categoryTypeRepository.getAlll(lang);
			for (ServiceCategoryType obj : scList) {
				ServiceCategoryTypeDTO scDto = new ServiceCategoryTypeDTO();
				scDto.setCategoryId(obj.getId());
				scDto.setCategoryname(obj.getName());
				scDto.setDescription(obj.getDescription());
				scDto.setImagePath(obj.getImagePath());
				scDtoList.add(scDto);
			}
			return scDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			return scDtoList;
		}
	}

	/*@Override
	public List<ServiceSubCategoryType> getActiveSubCategoryListByCategoryId(Long categoryId) {
		List<ServiceSubCategoryType> subCatLst = null;
		try {
			subCatLst = subCategoryRepository.findByServiceCategoryType(categoryId);
			return subCatLst;
		} catch (Exception e) {
			e.printStackTrace();
			return subCatLst;
		}
	}*/

	@Override
	public List<ServiceSubCategoryTypeDTO> getActiveSubCategoryListByCategoryId(Long categoryId,String lang) {
		List<ServiceSubCategoryTypeDTO> dtoList=new ArrayList<ServiceSubCategoryTypeDTO>();
		List<ServiceSubCategoryType> subCatLst = null;
		try {
			subCatLst = subCategoryRepository.findByServiceCategoryTypeAllLang(categoryId,lang);
			for(ServiceSubCategoryType obj:subCatLst) {
				ServiceSubCategoryTypeDTO scDto=new ServiceSubCategoryTypeDTO();
				scDto.setId(obj.getId());
				scDto.setName(obj.getName());
				scDto.setDescription(obj.getDescription());
				scDto.setServiceCategoryTypeID(obj.getServiceCategoryType().getId());
				scDto.setServiceCategoryTypeDesc(obj.getServiceCategoryType().getDescription());
				dtoList.add(scDto);
			}
			return dtoList;
		} catch (Exception e) {
			e.printStackTrace();
			return dtoList;
		}
	}
	/*@Override
	public List<ServiceType> getActiveServiceTypeByServiceId(Long subCategoryId) {
		List<ServiceType> serviceList = null;
		try {
			serviceList = serviceRepository.findBySubCategoryId(subCategoryId);
			return serviceList;
		} catch (Exception e) {
			return serviceList;
		}
	
	}*/

	public List<ServiceTypeDTO> getActiveServiceTypeByServiceId(Long subCategoryId,String lang) {
		List<ServiceTypeDTO> serviceTypeDtoList=new ArrayList<ServiceTypeDTO>();
		List<ServiceType> serviceList = null;
		try {
			serviceList = serviceRepository.findBySubCategoryId(subCategoryId,lang);
			for (ServiceType obj : serviceList) {
				ServiceTypeDTO dto=new ServiceTypeDTO();
				dto.setId(obj.getId());
				dto.setName(obj.getName());
				dto.setDescription(obj.getDescription());
				dto.setServiceCategoryTypeID(obj.getServiceCategoryType().getId());
				dto.setServiceExecutorContactTypeId(obj.getServiceExContactType().getId());
				dto.setServiceExContactType(obj.getServiceExContactType().getName());
				serviceTypeDtoList.add(dto);
			}
			return serviceTypeDtoList;
		} catch (Exception e) {
			return serviceTypeDtoList;
		}

	}
	@Override
	public List<UnitMeasurementType> getActiveMeasurementList() {
		List<UnitMeasurementType> measurementList = null;
		try {
			measurementList = unitRepository.findAll();
			return measurementList;
		} catch (Exception e) {
			e.printStackTrace();
			return measurementList;
		}
	}

	@Override
	public List<Price> getActivePriceListById(Long id) {
		List<Price> priceList = null;
		try {
			priceList = priceRepository.findAll();
			return priceList;
		} catch (Exception e) {
			e.printStackTrace();
			return priceList;
		}
	}

	@Override
	public List<ServiceExecutorContactType> getExecutorContactList() {
		List<ServiceExecutorContactType> serviceExecutorContactList = null;
		try {
			serviceExecutorContactList = serviceExecutorRepository.findAll();
			return serviceExecutorContactList;
		} catch (Exception e) {
			e.printStackTrace();
			return serviceExecutorContactList;
		}
	}

	@Override
	public List<Order> getACtiveOrderList() {
		List<Order> orderList = null;
		try {
			orderList = orderRepository.findAll();
			return orderList;
		} catch (Exception e) {
			e.printStackTrace();
			return orderList;
		}
	}

	@Override
	public ServiceStatusUser getStatusUserById(Long orderId) {
		ServiceStatusUser serviceStatusUser = null;
		try {
			Optional<ServiceStatusUser> opt = serviceStatusRepository.findById(orderId);
			if (opt.isPresent()) {
				serviceStatusUser = opt.get();
			}
			return serviceStatusUser;
		} catch (Exception e) {
			e.printStackTrace();
			return serviceStatusUser;
		}

	}

	@Override
	public OrderStatusType getOrderStatusTypeById(Long id) {
		OrderStatusType orderStatusType = null;
		try {
			Optional<OrderStatusType> opt = orderStatusTypeRepository.findById(id);
			if (opt.isPresent()) {
				orderStatusType = opt.get();
			}
			return orderStatusType;
		} catch (Exception e) {
			e.printStackTrace();
			return orderStatusType;
		}
	}

	@Override
	public OrderDetails getOrderDetailsByOrderId(Long orderId) {
		OrderDetails orderDetails = null;
		try {
			Optional<OrderDetails> opt = orderDetailsTypeRepository.findById(orderId);
			if (opt.isPresent()) {
				orderDetails = opt.get();
			}
			return orderDetails;
		} catch (Exception e) {
			e.printStackTrace();
			return orderDetails;
		}
	}

	@Override
	public List<ServiceDetails> getActiveServiceList() {
		List<ServiceDetails> serviceList = null;
		try {
			serviceList = serviceDetailsRepository.findAll();
			return serviceList;
		} catch (Exception e) {
			e.printStackTrace();
			return serviceList;
		}
	}

	/*@Override
	public ServiceResponse getServiceDetailsByCategoryIdId(Long categoryId) {
		List<ServiceSubCategoryDto> serviceSubCategoryDtoList = new ArrayList<ServiceSubCategoryDto>();
		ServiceSubCategoryDto serviceSubCategoryDto = null;
		List<ServiceDTo> serviceDetailList = null;
		ServiceDTo serviceDTo = null;
		Map<ServiceSubCategoryType, List<ServiceType>> dto = null;
		ServiceResponse result = new ServiceResponse();
		try {
			List<ServiceType> serviceList = serviceRepository.getServiceDetailsByServiceCategoryType(categoryId);
			dto = serviceList.stream().collect(Collectors.groupingBy(ServiceType::getServiceSubCategoryType));
			for (Entry<ServiceSubCategoryType, List<ServiceType>> entry : dto.entrySet()) {
				serviceSubCategoryDto = new ServiceSubCategoryDto();
				// Integer subCatId = entry.getKey().getId();
				serviceDetailList = new ArrayList<ServiceDTo>();
				serviceSubCategoryDto.setSubCategoryId(entry.getKey().getId());
				serviceSubCategoryDto.setSubCategoryName(entry.getKey().getName());
				serviceSubCategoryDto.setSubCategoryDesc(entry.getKey().getDescription());
				List<ServiceType> servicePreFilterList = entry.getValue();
				// List<ServiceType> serviceFilterList =
				// servicePreFilterList.parallelStream().filter(f ->
				// f.getServiceSubCategoryType().getId().equals(subCatId)).collect(Collectors.toList());
				for (ServiceType serviceObj : servicePreFilterList) {
	
					serviceDTo = new ServiceDTo();
					serviceDTo.setServiceDesc(serviceObj.getDescription());
					serviceDTo.setServiceName(serviceObj.getName());
					serviceDTo.setServiceId(serviceObj.getId());
					serviceDTo.setServicImagePath("/" + serviceObj.getImagePath());
					serviceDTo.setServiceExeCutorContType(serviceObj.getServiceExContactType().getId());
					serviceDTo.setServiceExeCutorContTypeName(serviceObj.getServiceExContactType().getName());
					serviceDTo.setServiceExeCutorContTypeDesc(serviceObj.getServiceExContactType().getDescription());
					serviceDetailList.add(serviceDTo);
				}
	
				serviceSubCategoryDto.setServiceDetails(serviceDetailList);
				serviceSubCategoryDtoList.add(serviceSubCategoryDto);
			}
	
			Collections.sort(serviceSubCategoryDtoList, new Comparator<ServiceSubCategoryDto>() {
				public int compare(ServiceSubCategoryDto obj1, ServiceSubCategoryDto obj2) {
					return obj1.getSubCategoryId().compareTo(obj2.getSubCategoryId());
				}
			});
	
			result.setServiceSubCategoryDto(serviceSubCategoryDtoList);
			return result;
		} catch (Exception e) {
			return result;
		}
	
	}*/

	@Override
	public ServiceResponse getServiceDetailsByCategoryId(Long categoryId,String lang) {
		List<ServiceSubCategoryDto> serviceSubCategoryDtoList = new ArrayList<ServiceSubCategoryDto>();
		ServiceSubCategoryDto serviceSubCategoryDto = null;
		List<ServiceDTo> serviceDetailList = null;
		ServiceDTo serviceDTo = null;
		Map<ServiceSubCategoryType, List<ServiceType>> dto = null;
		ServiceResponse result = new ServiceResponse();
		try {
			List<ServiceType> serviceList = serviceRepository.getServiceDetailsByServiceCategoryType(categoryId,lang);
			dto = serviceList.stream().collect(Collectors.groupingBy(ServiceType::getServiceSubCategoryType));
			for (Entry<ServiceSubCategoryType, List<ServiceType>> entry : dto.entrySet()) {
				serviceSubCategoryDto = new ServiceSubCategoryDto();
				// Integer subCatId = entry.getKey().getId();
				serviceDetailList = new ArrayList<ServiceDTo>();
				serviceSubCategoryDto.setSubCategoryId(entry.getKey().getId());
				serviceSubCategoryDto.setSubCategoryName(entry.getKey().getName());
				serviceSubCategoryDto.setSubCategoryDesc(entry.getKey().getDescription());
				List<ServiceType> servicePreFilterList = entry.getValue();
				// List<ServiceType> serviceFilterList =
				// servicePreFilterList.parallelStream().filter(f ->
				// f.getServiceSubCategoryType().getId().equals(subCatId)).collect(Collectors.toList());
				for (ServiceType serviceObj : servicePreFilterList) {

					serviceDTo = new ServiceDTo();
					serviceDTo.setServiceDesc(serviceObj.getDescription());
					serviceDTo.setServiceName(serviceObj.getName());
					serviceDTo.setServiceId(serviceObj.getId());
					serviceDTo.setServicImagePath("/" + serviceObj.getImagePath());
					serviceDTo.setServiceExeCutorContType(serviceObj.getServiceExContactType().getId());
					serviceDTo.setServiceExeCutorContTypeName(serviceObj.getServiceExContactType().getName());
					serviceDTo.setServiceExeCutorContTypeDesc(serviceObj.getServiceExContactType().getDescription());
					serviceDetailList.add(serviceDTo);
				}

				serviceSubCategoryDto.setServiceDetails(serviceDetailList);
				serviceSubCategoryDtoList.add(serviceSubCategoryDto);
			}

			Collections.sort(serviceSubCategoryDtoList, new Comparator<ServiceSubCategoryDto>() {
				public int compare(ServiceSubCategoryDto obj1, ServiceSubCategoryDto obj2) {
					return obj1.getSubCategoryId().compareTo(obj2.getSubCategoryId());
				}
			});

			result.setServiceSubCategoryDto(serviceSubCategoryDtoList);
			return result;
		} catch (Exception e) {
			return result;
		}

	}
	@Override
	public ServiceDetailsDTO saveExecutorDetails(ServiceDetailsDTO serviceDetailsDTO, ServiceDetails serviceDetails)
			throws BookingException {
		ServiceDetails isSaved = null;
		HttpStatus status = null;
		int isValid = 0;
		List<ServiceDetails> serviceDetail = serviceDetailsRepository.findByMobileNo(serviceDetailsDTO.getPhoneNo());
		isValid = serviceDetailsRepository.validate(serviceDetails.getVehicleNumber(), serviceDetails.getServiceType());
		if (!serviceDetail.isEmpty()/* || (serviceDetail.get(0).getPhoneNo() != serviceDetailsDTO.getPhoneNo()) */) {
			if (isValid == 0) {
				serviceDetails.setAvailability("AVAILABLE");
				serviceDetails.setActive(true);
				serviceDetails.setServiceType(serviceDetailsDTO.getServiceType());
				serviceDetails.setServiceOwnerName(serviceDetailsDTO.getServiceOwnerName());
				serviceDetails.setServiceExecutorName(serviceDetailsDTO.getServiceExecutorName());
				serviceDetails.setUanNo(serviceDetailsDTO.getUanNo());
				serviceDetails.setOffSeasonPrice(serviceDetailsDTO.getOffSeasonPrice());
				serviceDetails.setOnSeasonPrice(serviceDetailsDTO.getOnSeasonPrice());
				serviceDetails.setUnitMeasurementType(serviceDetailsDTO.getUnitMeasurementType());
				serviceDetails.setLattitude(serviceDetailsDTO.getLattitude());
				serviceDetails.setLongitude(serviceDetailsDTO.getLongitude());
				serviceDetails.setPhoneNo(serviceDetailsDTO.getPhoneNo());
				serviceDetails.setUserName(serviceDetailsDTO.getUserName());
				serviceDetails.setUserType(serviceDetailsDTO.getUserType());
				serviceDetails.setZipCode(serviceDetailsDTO.getZipCode());
				serviceDetails.setIdproofPhoto(serviceDetailsDTO.getIdproofPhoto());
				serviceDetails.setCreatedAt(new Date());
				serviceDetails.setCreatedBy(serviceDetailsDTO.getUserName());
				serviceDetails.setUpdatedAt(new Date());
				serviceDetails.setUpdatedBy(serviceDetailsDTO.getUserName());
				serviceDetails.setVehicleNumber(serviceDetailsDTO.getVehicleNumber());
				isSaved = serviceDetailsRepository.save(serviceDetails);
			} else {
				logger.error(
						"Service already registerd on this Vehicle number :" + serviceDetailsDTO.getVehicleNumber());
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This service has been already registered in this vehicle number: "
								+ serviceDetailsDTO.getVehicleNumber()
								+ " Please register another tractor for this service");
			}
			return serviceDetailsDTO;

		} else {
			try {
				UserDTO userDto = new UserDTO();
				userDto.setfName(serviceDetailsDTO.getServiceExecutorName());
				userDto.setUsertype(serviceDetailsDTO.getUserType());
				userDto.setUan(serviceDetailsDTO.getUanNo());
				userDto.setProofType("IDF_TYPE_PAN");
				System.out.println(serviceDetailsDTO.getIdproofPhoto());
				userDto.setImagePath(serviceDetailsDTO.getIdproofPhoto());
				userDto.setPhoneNo(serviceDetailsDTO.getPhoneNo());

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDto, headers);
				logger.info("File entered for upload ");
				ResponseEntity<UserDTO> out = null;

				if (!serviceDetailsDTO.getUserType().equals("U_TYPE_SERVICE_PROVIDER")) {
					out = restTemplate.exchange(
							BookingConstant.ADMIN_APPLICATION_BASEURL + BookingConstant.SAVE_SERVICE_EXECUTOR_URL,
							HttpMethod.POST, entity, UserDTO.class);
					status = out.getStatusCode();
					logger.info("Response from checking" + out.getStatusCodeValue());
				}
				// }else {

				System.out.println("status:" + status);

				if (status == HttpStatus.OK || serviceDetailsDTO.getUserType().equals("U_TYPE_SERVICE_PROVIDER")) {
					serviceDetails.setAvailability("AVAILABLE");
					serviceDetails.setActive(true);
					serviceDetails.setUserName(serviceDetails.getUserName());
					serviceDetails.setCreatedAt(new Date());
					serviceDetails.setCreatedBy(serviceDetails.getUserName());
					serviceDetails.setUpdatedAt(new Date());
					serviceDetails.setUpdatedBy(serviceDetails.getUserName());
					serviceDetails.setPhoneNo(serviceDetails.getPhoneNo());
					serviceDetails.setUanNo(serviceDetails.getUanNo());
					isSaved = serviceDetailsRepository.save(serviceDetails);
					return serviceDetailsDTO;
				} else {
					return null;
				}

			} catch (Exception e) {
				System.out.println("status:" + status);
				e.printStackTrace();
				logger.error(e.getStackTrace());
				throw e;
			}
		}

	}

	@Override
	public String uploadDocument(MultipartFile file) {
		String docPath = "";
		try {
			if (file.isEmpty() == false) {
				logger.info("File entered for upload ");
				docPath = UploadFile.upload(file, "document");
			}
			return docPath;

		} catch (Exception e) {
			e.printStackTrace();
			return docPath;
		}
	}

	@Override
	public SearchResponse getServiceDetails(ServiceRequest serviceRequest) throws BookingException {
		if (serviceRequest == null) {
			logger.error("The given service details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Service type and pincode is mandatory for this operation");
		} else {
			List<ServiceDetails> serviceLst = null;
			SearchResponse response = null;
			response = new SearchResponse();
			serviceLst = serviceDetailsRepository.findByServiceandZipcode(serviceRequest.getZipcode(),
					serviceRequest.getServicetype());
			if (serviceLst.isEmpty()) {
				logger.error("No Service Details found for this given ServiceType: " + serviceRequest.getServicetype()
						+ " and zipCode:" + serviceRequest.getZipcode());
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This serviceType " + serviceRequest.getServicetype() + " or zipCode "
								+ serviceRequest.getZipcode()
								+ " are invalid,Please enter a valid service type and zipcode");
			} else {
				ServiceDetails sd = Collections.max(serviceLst, Comparator.comparing(s -> s.getOnSeasonPrice()));
				// ServiceDetails sd1 = Collections.max(serviceLst, Comparator.comparing(s ->
				// s.getOnSeasonPrice()));
				// if (sd.getOffSeasonPrice() > sd1.getOnSeasonPrice()) {
				response.setIndicativePrice(sd.getOnSeasonPrice());
				response.setAvailability("Available");
				response.setServiceType(serviceRequest.getServicetype());
				return response;
				/*
				 * } else { response.setIndicativePrice(sd1.getOnSeasonPrice());
				 * response.setAvailability("Available");
				 * response.setServiceType(serviceRequest.getServicetype()); return response; }
				 */
			}
		}
	}

	@Override
	@Transactional
	public Order createOrder(OrderRequestDTO orderRequestDTO) throws Exception {

		Order orderObj = null;
		PaymentDetails pdObj = null;
		OrderDetails odsObj = null;
		try {
			ServiceType sc = serviceRepository.getOne(((orderRequestDTO.getServiceId())));
			List<ServiceDetails> serviceLst = serviceDetailsRepository
					.findByServiceandZipcode(orderRequestDTO.getZipcode(), sc.getName());
			serviceLst.stream().limit(5).collect(Collectors.toList());
			String userId = null;

			if (orderRequestDTO != null) {
				// for (ServiceDetails sd : serviceLst) {
				// if (sd.getActive()) {
				orderObj = new Order();
				Long maxId = orderRepository.selectMaxId();
				orderObj.setOrderId((GenerateUniqueCode.getGeneratedCodeForSP("ORDERNUM", "VY", ++maxId)));
				orderObj.setUom(unitRepository.getOne((orderRequestDTO.getUom())));
				orderObj.setFieldArea(orderRequestDTO.getFieldArea());
				orderObj.setLattitude(orderRequestDTO.getLattitude());
				orderObj.setLongitude(orderRequestDTO.getLongitude());
				orderObj.setOrderStatus("NEW_ORDER_CREATED");
				orderObj.setPaymentStatus("PENDING");
				orderObj.setAddress(orderRequestDTO.getAddress());
				orderObj.setLandMark(orderRequestDTO.getLandMark());
				orderObj.setPinCode(orderRequestDTO.getZipcode());
				String orderDate = orderRequestDTO.getStartTime();
				Date startOrdertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(orderDate);
				orderObj.setOrderExecutionTime(startOrdertDate);
				orderObj.setIsRunning(false);
				// orderObj.setTotalAmount(orderRequestDTO.getFieldArea() *
				// orderRequestDTO.getUnitPrice());

				orderObj.setServiceId(sc);
				orderObj.setCreatedAt(new Date());
				orderObj.setCreatedBy(orderRequestDTO.getUserId());
				orderObj.setUpdatedAt(new Date());
				orderObj.setUpdatedBy(orderRequestDTO.getUserId());
				orderObj.setRequesterContact(orderRequestDTO.getMobileNumber());
				orderObj.setRequesterName(orderRequestDTO.getRequesterName());
				orderObj.setIsActive(true);

				PaymentDetails pd = new PaymentDetails();
				pd.setAdvanceAmount(0.00);
				pd.setDueAmount(payment);
				pd.setDuePaymentStatus("DUE_PAYMENT_PENDING");
				pd.setBalanceAmount(0.00);
				pd.setTotalAmount(0.00);
				// pd.setBalanceAmount(orderObj.getTotalAmount());
				// pd.setTotalAmount(orderObj.getTotalAmount());
				orderObj.setTotalAmount(pd.getTotalAmount());
				orderObj = orderRepository.save(orderObj);
				pd.setOrder(orderObj.getOrderId());
				pd.setPaymentReferenceId(GenerateUniqueCode.getGeneratedCodeForSP("PAYMENTREF", "VP", ++maxId));
				pd.setCreatedAt(new Date());
				pd.setUpdatedAt(new Date());
				pd.setUpdatedBy(orderRequestDTO.getUserId());
				pd.setCreatedBy(orderRequestDTO.getUserId());
				pd.setIsCompleted(false);
				pd.setPaymentStatus("PENDING");
				pdObj = paymentRepository.save(pd);

				List<OrderDetails> lstOrerDetails = new ArrayList<OrderDetails>();

				for (ServiceDetails service : serviceLst) {
					String date = orderRequestDTO.getStartTime();
					String pincode = service.getZipCode();
					String serviceName = service.getServiceType();
					userId = service.getUserName();
					// lstOrerDetails=orderDetailsTypeRepository.findByOrderSave(userId,orderRequestDTO.getServiceId(),pincode);
					// if(odsObj == null) {
					// Pageable pageable=PageRequest.of(0, 1);
					// List<ServiceDetails>
					// serviceDetailsLst=serviceDetailsRepository.findByServiceOwnerAndServiceName(pincode,
					// serviceName, userId,pageable);
					SimpleDateFormat minmax = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					// Date min=new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss.SSS").parse(minmax+"00:00:00.000");
					Date min = minmax.parse(date + "00:00:00.000");
					Date max = minmax.parse(date + "23:59:59.000");
					// Date max=new SimpleDateFormat("yyyy-MM-dd
					// HH:mm:ss.SSS").parse(minmax+"23:59:59.000");
					int count = orderDetailsTypeRepository.findPendingSRCount(userId, sc.getId(), min, max);

					if (count < 5) {
						OrderDetails odObj = new OrderDetails();
						odsObj = orderDetailsTypeRepository.findByOrderSave(userId, orderRequestDTO.getServiceId(),
								pincode);
						if (odsObj == null) {
							odObj.setOrder(orderObj);
							odObj.setOrdeNumber(orderObj.getOrderId());
							odObj.setOrderStatus("NEW");
							odObj.setPaymentStatus("PENDING");
							odObj.setServiceDetailsId(service);
							odObj.setCreatedAt(new Date());
							odObj.setUpdatedAt(new Date());
							odObj.setServiceID(orderRequestDTO.getServiceId());
							odObj.setUpdatedBy(orderRequestDTO.getUserId());
							odObj.setCreatedBy(orderRequestDTO.getUserId());
							date = orderRequestDTO.getStartTime();
							Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(date);
							odObj.setOrderExecutionTime(startDate);
							odObj.setIsActive(true);
							odObj.setZipCode(orderRequestDTO.getZipcode());
							odObj.setServiceExecutorUserId(service.getUserName());
							// lstOrerDetails.add(odObj);
							orderDetailsTypeRepository.save(odObj);
						}
					}

				}
				// orderDetailsTypeRepository.saveAll(lstOrerDetails);
				// }
				// }
			}
			return orderObj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	@Transactional
	public Order updateOrder(OrderUpdateDto orderUpdateDto) {
		Order orderObj = null;
		try {
			if (orderUpdateDto != null && !orderUpdateDto.getOrderNumber().equals("")) {
				orderObj = orderRepository.findByOrderId(orderUpdateDto.getOrderNumber());
				if (orderObj != null) {
					Long maxId = orderRepository.selectMaxId();
					orderObj.setUom(unitRepository.getOne((orderUpdateDto.getUom())));
					orderObj.setFieldArea(orderUpdateDto.getFieldArea());
					orderObj.setLattitude(orderUpdateDto.getLongitude());
					orderObj.setLongitude(orderUpdateDto.getLongitude());
					orderObj.setAddress(orderUpdateDto.getAddress());
					orderObj.setLandMark(orderUpdateDto.getLandMark());
					orderObj.setTotalAmount(orderUpdateDto.getFieldArea() * orderUpdateDto.getUnitPrice());
					ServiceType sc = serviceRepository.getOne(((orderUpdateDto.getServiceId())));
					orderObj.setServiceId(sc);
					orderObj.setCreatedAt(new Date());
					orderObj.setCreatedBy(orderUpdateDto.getUserId());
					orderObj.setUpdatedAt(new Date());
					orderObj.setUpdatedBy(orderUpdateDto.getUserId());
					orderObj.setRequesterContact(orderUpdateDto.getMobileNumber());
					orderObj.setRequesterName(orderUpdateDto.getRequesterName());
					orderObj.setOrderExecutionTime(orderUpdateDto.getStartTime());
					orderObj.setIsActive(true);
					orderObj = orderRepository.save(orderObj);
					List<OrderDetails> odLst = orderDetailsTypeRepository
							.updateOtherOrders(orderUpdateDto.getOrderNumber(), "NEW");

					if (odLst != null && orderObj.getServiceId().getId() == odLst.get(0).getServiceID()) {
						odLst.forEach(od -> od.setUpdatedAt(new Date()));
						odLst.forEach(od -> od.setUpdatedBy(orderUpdateDto.getUserId()));
						orderDetailsTypeRepository.saveAll(odLst);
					} else {

						odLst.forEach(od -> od.setOrderStatus("ORDER_UPDATED_BY_REQUESTER"));
						odLst.forEach(od -> od.setIsActive(false));
						odLst.forEach(od -> od.setUpdatedAt(new Date()));
						odLst.forEach(od -> od.setUpdatedBy(orderUpdateDto.getUserId()));
						orderDetailsTypeRepository.saveAll(odLst);

						List<ServiceDetails> serviceLst = serviceDetailsRepository
								.findByServiceandZipcode(orderUpdateDto.getZipcode(), sc.getName());
						serviceLst.stream().limit(5).collect(Collectors.toList());

						List<OrderDetails> lstOrerDetails = new ArrayList<OrderDetails>();
						for (ServiceDetails service : serviceLst) {
							OrderDetails odObj = new OrderDetails();
							odObj.setOrder(orderObj);
							odObj.setOrdeNumber(orderObj.getOrderId());
							odObj.setOrderStatus("NEW");
							odObj.setPaymentStatus("PENDING");
							odObj.setServiceDetailsId(service);
							odObj.setCreatedAt(new Date());
							odObj.setUpdatedAt(new Date());
							odObj.setServiceID(orderUpdateDto.getServiceId());
							odObj.setUpdatedBy(orderUpdateDto.getUserId());
							odObj.setCreatedBy(orderUpdateDto.getUserId());
							odObj.setOrderExecutionTime(orderUpdateDto.getStartTime());
							odObj.setIsActive(true);
							odObj.setServiceExecutorUserId(service.getUserName());
							lstOrerDetails.add(odObj);
						}
						orderDetailsTypeRepository.saveAll(lstOrerDetails);
					}
				}
			}
			return orderObj;
		} catch (Exception e) {
			e.printStackTrace();
			return orderObj;
		}

	}

	@Override
	public List<OrderDTO> findOrdersByUserId(String userId) throws BookingException {
		if (userId.isEmpty()) {
			logger.error("The given Farmer details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Farmer username is mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoLst = new ArrayList<OrderDTO>();
			List<Order> orderList = orderRepository.findByUserId(userId);
			if (orderList.isEmpty()) {
				logger.error("No Farmer Details found for this given username: " + userId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Farmer username " + userId + " is invalid,Please enter a valid Username");
			} else {
				for (Order orderObj : orderList) {
					orderDtoLst.add(orderMapper.convert(orderObj));
				}
				return orderDtoLst;
			}
		}
	}

	@Override
	public OrderDTO findOrdersByOrderId(String orderId) throws BookingException {
		if (orderId.isEmpty()) {
			logger.error("The given Order details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Order Id is mandatory for this operation");
		} else {
			Order orderObj = orderRepository.findByOrderId(orderId);
			if (orderObj == null) {
				logger.error("No Order Details found for this given orderId: " + orderId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Order Id " + orderId + " is invalid,Please enter a valid order id");
			} else {

				orderMapper.convert(orderObj);
			}
			return orderMapper.convert(orderObj);
		}
	}

	@Override
	public List<OrderDTO> ordersByOrderIdStatus(String orderId, String status) throws BookingException {
		if (orderId.isEmpty() && status.isEmpty()) {
			logger.error("Get Farmer Order details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Order Id and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoLst = new ArrayList<OrderDTO>();
			List<Order> orderList = orderRepository.ordersByOrderIdStatus(orderId, status);
			if (orderList.isEmpty()) {
				logger.error("No Order Details found for this given orderId: " + orderId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Farmer Order Id " + orderId + " is invalid,Please enter a valid order id");
			} else {
				for (Order orderObj : orderList) {
					orderDtoLst.add(orderMapper.convert(orderObj));
				}
				return orderDtoLst;
			}
		}
	}

	@Override
	public List<OrderDTO> findOrdersByUserIdandStatus(String userId, String status) throws BookingException {
		if (userId.isEmpty() && status.isEmpty()) {
			logger.error("Get Farmer Order details by username failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Farmer UserName and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoLst = new ArrayList<OrderDTO>();
			// List<Order> orderList = orderRepository.findOrdersByUserIdandStatus(userId,
			// status);
			List<Order> orderList = orderRepository.findOrdersByUserIdandStatus(userId, status);
			if (orderList.isEmpty()) {
				logger.error("No Order Details found for this given username: " + userId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Farmer username " + userId + " is invalid,Please enter a valid username");
			} else {
				for (Order orderObj : orderList) {
					orderDtoLst.add(orderMapper.convert(orderObj));
				}
			}
			return orderDtoLst;
		}
	}

	@Override
	@Transactional
	public OrderResponseDTO updateOrders(String orderId, String status, String userId) {
		Order orderObj = null;
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		try {
			OrderDetails odObj = orderDetailsTypeRepository.findByOrderId(orderId, userId);
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

			} else if (status.equals("CANCEL")) {
				odObj.getOrder().setOrderStatus("ORDER_CANCELLED");
				odObj.setOrderStatus("ORDER_CANCELLED");
				odObj.setPaymentStatus("PAYMENT_CANCELLED");
				odObj.setUpdatedAt(new Date());
				odObj.setUpdatedBy(userId);
				odObj.setIsActive(false);
				orderDetailsTypeRepository.save(odObj);

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
			orderResponseDTO.setOrderStatus(status);

			return orderResponseDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return orderResponseDTO;
		}
	}

	@Override
	@Transactional
	public OrderResponseDTO updateOrders(String orderId, String userId) throws BookingException {
		Order orderObj = null;
		PaymentDetails pdObj = null;
		List<OrderDetails> odObj = new ArrayList<OrderDetails>();
//		Long diff;
//		orderObj = orderRepository.findByOrderId(orderId);
//		Date dateStart = orderObj.getOrderExecutionTime();
//		Date currentDate = new Date();
//		diff = ((dateStart.getTime() - currentDate.getTime()) / (60 * 60 * 1000));
//		if (diff > 48) {
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		orderObj = orderRepository.findByOrderId(orderId);
		if (!orderObj.getOrderStatus().equals("ORDER_CANCELLED")) {

			if (orderObj.getCreatedBy().equals(userId)) {
				// for order
				orderObj.setOrderStatus("ORDER_CANCELLED");
				orderObj.setPaymentStatus("PAYMENT_CANCELLED");
				orderObj.setUpdatedAt(new Date());
				orderObj.setUpdatedBy(userId);
				orderRepository.save(orderObj);
				// for orderDetails
				pdObj = paymentRepository.updatePaymentStatus(orderId);
				pdObj.setPaymentStatus(orderObj.getPaymentStatus());
				pdObj.setUpdatedAt(new Date());
				pdObj.setUpdatedBy(userId);
				paymentRepository.save(pdObj);
				odObj = orderDetailsTypeRepository.findByOrderIdAndUsername(orderObj.getOrderId(),
						orderObj.getCreatedBy());
				for (OrderDetails odsObj : odObj) {
					odsObj.getOrder().setOrderStatus("ORDER_CANCELLED");
					odsObj.setOrderStatus("ORDER_CANCELLED");
					odsObj.setPaymentStatus("PAYMENT_CANCELLED");
					odsObj.setUpdatedAt(new Date());
					odsObj.setUpdatedBy(userId);
					odsObj.setIsActive(false);
				}
				orderDetailsTypeRepository.saveAll(odObj);
				orderResponseDTO.setMessage("Order Updated");
				orderResponseDTO.setOrderId(orderId);
				orderResponseDTO.setOrderStatus(orderObj.getOrderStatus());

			} else {
				logger.error("User Id does not exist. ");
				throw new BookingException(ErrorCode.REQUEST_ERROR, "You are not authorize");
			}

			/*
			 * if (status.equals("ACCEPT")) {
			 * odObj.getOrder().setOrderStatus("ORDER_ACCEPTED");
			 * odObj.setOrderStatus("ORDER_ACCEPTED"); odObj.setUpdatedAt(new Date());
			 * odObj.setUpdatedBy(userId);
			 * odObj.setServiceExLattitude(odObj.getServiceDetailsId().getLattitude());
			 * odObj.setServiceExLongitude(odObj.getServiceDetailsId().getLongitude());
			 * odObj.setServiceExecutorName(odObj.getServiceDetailsId().
			 * getServiceExecutorName()); orderDetailsTypeRepository.save(odObj);
			 * 
			 * List<OrderDetails> odLst =
			 * orderDetailsTypeRepository.updateOtherOrders(orderId, "NEW");
			 * odLst.stream().filter(od ->
			 * !od.getCreatedAt().equals(userId)).collect(Collectors.toList());
			 * odLst.forEach(od -> od.setOrderStatus("ORDER_ACCEPTED_BY_OTHERS"));
			 * odLst.forEach(od -> od.setIsActive(false));
			 * orderDetailsTypeRepository.saveAll(odLst);
			 * 
			 * } else if (status.equals("CANCEL")) {
			 * odObj.getOrder().setOrderStatus("ORDER_CANCELLED");
			 * odObj.setOrderStatus("ORDER_CANCELLED");
			 * odObj.setPaymentStatus("PAYMENT_CANCELLED"); odObj.setUpdatedAt(new Date());
			 * odObj.setUpdatedBy(userId); odObj.setIsActive(false);
			 * orderDetailsTypeRepository.save(odObj);
			 * 
			 * }else if (status.equals("COMPLETE")) {
			 * odObj.getOrder().setOrderStatus("ORDER_COMPLETED");
			 * odObj.setOrderStatus("ORDER_COMPLETED");
			 * odObj.setPaymentStatus("PAYMENT_COMPLETED"); odObj.setUpdatedAt(new Date());
			 * odObj.setUpdatedBy(userId); odObj.setIsActive(false);
			 * orderDetailsTypeRepository.save(odObj);
			 * 
			 * orderResponseDTO.setMessage("Order Updated");
			 * orderResponseDTO.setOrderId(orderId);
			 * orderResponseDTO.setOrderStatus(status); return orderResponseDTO; }
			 */

			return orderResponseDTO;

		} else {
			logger.error("You have already cancelled on this orderId " + orderId);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"You have already cancelled this order " + orderId + " You can't again cancel");
		}
//		} else {
//			logger.error(
//					"You are not allowing to cancel the order.You can cancel the order 48 hour before execution time");
//			throw new BookingException(ErrorCode.REQUEST_ERROR,
//					"You can not cacel the order because your service executor will reach in between " + diff
//							+ " hours");
//		}
	}

	@Override
	public List<ServiceDetailsDTO> getAvailableServiceNearToFarmer(String farmerLocationLatitude,
			String farmaerLocationlongitude, String zipCode, String service) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(BookingConstant.HEADER_PARAMETERS, headers);
		String sources = farmaerLocationlongitude + BookingConstant.COMMA + farmerLocationLatitude
				+ BookingConstant.SEMICOLON;
		List<ServiceDetails> allavailableServices = serviceDetailsRepository.findByServiceandZipcode(zipCode, service);
		if (allavailableServices.isEmpty()) {
			logger.error("There is no services available in this pincode " + zipCode);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "please enter valid details");
		} else {
			List<ServiceDetailsDTO> serviceToNotify = new ArrayList<ServiceDetailsDTO>();
			String destinations = BookingConstant.EMPTY_STRING;
			for (ServiceDetails serviceDetails : allavailableServices) {
				String providersLatitude = serviceDetails.getLattitude();
				String providersLongitude = serviceDetails.getLongitude();
				destinations = destinations + providersLongitude + BookingConstant.COMMA + providersLatitude
						+ BookingConstant.SEMICOLON;
			}
			destinations = destinations.substring(0, destinations.length() - 1);
			String positions = sources + destinations;
			String url = BookingConstant.DISTANCEMATRIX_BASE_API + BookingConstant.DISTANCEMATRIX_API_KEY
					+ BookingConstant.DISTANCEMATRIX_RESOURCES + BookingConstant.DISTANCEMATRIX_PROFILE + positions
					+ BookingConstant.DEPT_TIME;
			ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			ObjectMapper om = new ObjectMapper();
			DistanceMatrixRoot root = om.readValue(result.getBody(), DistanceMatrixRoot.class);
			List<Double> distances = root.results.getDistances().get(0);
			for (int i = 1; i < distances.size(); i++) {
				double distance = distances.get(i);
				if (distance <= BookingConstant.RADIOUS1) {
					ServiceDetails serviceDetailsObj = allavailableServices.get(i - 1);
					if (serviceDetailsObj != null) {
						serviceToNotify.add(serviceDetailsMapper.convert(serviceDetailsObj));
					}
				} else if (distance <= BookingConstant.RADIOUS2) {
					ServiceDetails serviceDetailsObj = allavailableServices.get(i - 1);
					if (serviceDetailsObj != null) {
						serviceToNotify.add(serviceDetailsMapper.convert(serviceDetailsObj));
					}
				}
			}
			return serviceToNotify;
		}

	}

	@Override
	public List<OrderDTO> findSEOrders(String userId, String status) throws BookingException {
		if (userId.isEmpty() && status.isEmpty()) {
			logger.error("Get Service Executor Order details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Service Executor UserName and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoLst = new ArrayList<OrderDTO>();
			List<OrderDetails> orderList = orderDetailsTypeRepository.findSEOrders(userId, status);
			if (orderList.isEmpty()) {
				logger.error("No Order Details found for this given username: " + userId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Service Executor username " + userId + " is invalid,Please enter a valid username");
			} else {
				for (OrderDetails orderObj : orderList) {
					String description = orderObj.getOrder().getServiceId().getName();
					String ownername = orderObj.getServiceDetailsId().getServiceOwnerName();
					String exeName = orderObj.getServiceDetailsId().getServiceExecutorName();
					String vehicleNumber = orderObj.getServiceDetailsId().getVehicleNumber();
					// String userName=orderObj.getServiceExecutorUserId();
					// ServiceDetails serviceDetails =
					// serviceDetailsRepository.getByServiceOwnerNameAndServiceExecutorName(description);
					ServiceDetails serviceDetails = serviceDetailsRepository
							.getByServiceOwnerNameAndServiceExecutorNameDesc(ownername, exeName, description,
									vehicleNumber);
					// System.out.println(ownername + "-" + exeName + "--" + description);
					orderObj.getOrder().setIndicativeRate(serviceDetails.getOnSeasonPrice());
					/*
					 * Integer offSeasonPrice = serviceDetails.getOffSeasonPrice(); Integer
					 * onSeasonPrice = serviceDetails.getOnSeasonPrice(); if (offSeasonPrice >
					 * onSeasonPrice) { orderObj.getOrder().setIndicativeRate(offSeasonPrice); }
					 * else { orderObj.getOrder().setIndicativeRate(onSeasonPrice); }
					 */

					orderDtoLst.add(orderDetailsMapper.convert(orderObj));
				}
				return orderDtoLst;
			}
		}
	}

	@Override
	public List<MyServiceDetailsDTO> getSelfServiceDetails(String userName) throws BookingException {
		if (userName.isEmpty()) {
			logger.error("You have entered empty information ");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "UserName no is mandatory for this operation");
		} else {
			List<MyServiceDetailsDTO> serviceDtoList = new ArrayList<MyServiceDetailsDTO>();
			List<ServiceDetails> serviceList = serviceDetailsRepository.findBySelfService(userName);
			if (serviceList == null) {
				logger.error("There are no information found for this given username " + userName);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This given username is not valid,please enter valid user name");
			} else {
				for (ServiceDetails serviceDetailsObj : serviceList) {
					MyServiceDetailsDTO serviceDetailsDto = new MyServiceDetailsDTO();
					ServiceType serviceType = serviceRepository.findByName(serviceDetailsObj.getServiceType());
					serviceDetailsDto.setServiceName(serviceType.getDescription());
					serviceDetailsDto.setVehicleNumber(serviceDetailsObj.getVehicleNumber());
					serviceDetailsDto.setServiceCategory(serviceType.getServiceCategoryType().getDescription());
					serviceDetailsDto.setServiceSubCategory(serviceType.getServiceSubCategoryType().getDescription());
					serviceDetailsDto.setServiceTypeImage(serviceType.getImagePath());
					serviceDtoList.add(serviceDetailsDto);
				}
				return serviceDtoList;
			}
		}
	}

	@Override
	public List<OrderDTO> findCurrentSEOrders(String userId, String status) throws BookingException {
		if (userId.isEmpty() && status.isEmpty()) {
			logger.error("Get Current Order details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Service Executor UserName and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoList = new ArrayList<OrderDTO>();
			List<OrderDetails> orderList = orderDetailsTypeRepository.findSEOrders(userId, status);
			if (orderList.isEmpty()) {
				logger.error("No Current Order Details found for this given username: " + userId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Service Executor username " + userId + " is invalid,Please enter a valid username");
			} else {
				for (OrderDetails orderObj : orderList) {
					if (new Date().getDate() == orderObj.getOrderExecutionTime().getDate())
						orderDtoList.add(orderDetailsMapper.convert(orderObj));
				}
				return orderDtoList;
			}
		}
	}

	@Override
	public List<OrderDTO> findUpcomingSEOrders(String userId, String status) throws BookingException {
		if (userId.isEmpty() && status.isEmpty()) {
			logger.error("Get Upcoming Order details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Service Executor UserName and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoList = new ArrayList<OrderDTO>();
			List<OrderDetails> orderList = orderDetailsTypeRepository.findSEOrders(userId, status);
			if (orderList.isEmpty()) {
				logger.error("No Upcoming Order Details found for this given username: " + userId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Service Executor username " + userId + " is invalid,Please enter a valid username");
			} else {
				for (OrderDetails orderObj : orderList) {
					if (new Date().getDate() < orderObj.getOrderExecutionTime().getDate())
						orderDtoList.add(orderDetailsMapper.convert(orderObj));
				}
				return orderDtoList;
			}
		}
	}

	@Override
	public List<OrderDTO> findCompleteSEOrders(String userId, String status) throws BookingException {
		if (userId.isEmpty() && status.isEmpty()) {
			logger.error("Get Upcoming Order details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Service Executor UserName and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoList = new ArrayList<OrderDTO>();
			List<OrderDetails> orderList = orderDetailsTypeRepository.findSEOrders(userId, status);
			if (orderList.isEmpty()) {
				logger.error("No Upcoming Order Details found for this given username: " + userId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This Service Executor username " + userId + " is invalid,Please enter a valid username");
			} else {
				for (OrderDetails orderObj : orderList) {
					orderDtoList.add(orderDetailsMapper.convert(orderObj));
				}
				return orderDtoList;
			}
		}
	}

	@Override
	public PaymentDto getPaymentDetails(String orderId) throws BookingException {
		if (orderId.isEmpty()) {
			logger.error("Get payment details failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Order Id is mandatory for this operation");
		} else {
			PaymentDto paymentObj = null;
			PaymentDetails pd = paymentRepository.getPaymentDetails(orderId);
			if (pd != null) {
				paymentObj = new PaymentDto();
				paymentObj.setAdvanceAmount(pd.getAdvanceAmount());
				paymentObj.setBalanceAmount(pd.getBalanceAmount());
				paymentObj.setTotalAmount(pd.getTotalAmount());
				paymentObj.setPaymentStatus(pd.getPaymentStatus());
				paymentObj.setOrderId(pd.getOrder());
				return paymentObj;
			} else {
				logger.error("No Payment Details found for this given : " + orderId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"No Payment Details exists for the given OrderId " + orderId);
			}
		}
	}

	@Override
	public PaymentDetails updateStatus(String orderId, String status, int notiId) throws BookingException {
		if (orderId.isEmpty() && status.isEmpty()) {
			logger.error("OrderId and status are mandatory for this operation");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "OrderId and status are mandatory for this operation");
		} else {
			PaymentDetails pd = paymentRepository.updatePaymentStatus(orderId);
			if (pd == null) {
				logger.error("OrderId is Invalid");
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"OrderId " + orderId + " is inValid please enter a valid orderId");
			} else {
				NotificationDetails notificationDetails = null;
				if (status.equals("ACCEPT")) {
					pd.setPaymentAcceptReject("ACCEPTED");
					paymentRepository.save(pd);
					Optional<NotificationDetails> opt = notificationDetailsRepository.findById(notiId);
					if (opt.isPresent()) {
						notificationDetails = opt.get();
					}
					notificationDetails.setNotificationStatus(1);
					notificationDetailsRepository.save(notificationDetails);
				} else if (status.equals("DECLINE")) {
					pd.setPaymentAcceptReject("DECLINED");
					paymentRepository.save(pd);
					Optional<NotificationDetails> opt = notificationDetailsRepository.findById(notiId);
					if (opt.isPresent()) {
						notificationDetails = opt.get();
					}
					notificationDetails.setNotificationStatus(2);
					notificationDetailsRepository.save(notificationDetails);
				} else {
					throw new BookingException(ErrorCode.REQUEST_ERROR,
							"You can use only ACCEPT or DECLINE for this function");
				}
				// String paymentstaus = "PAYMENT " + pd.getPaymentAcceptReject();
				return pd;
			}
		}
	}

	@Override
	public List<PaymentDto> findCompletedPaymnetSEOrders(String userId, String status) throws BookingException {
		if (userId.isEmpty() && status.isEmpty()) {
			logger.error("Find Completed Payment status failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "UserName and status are mandatory for this operation");
		} else {
			List<PaymentDto> paymentDtoList = new ArrayList<PaymentDto>();
			List<PaymentDetails> paymentList = paymentRepository.findOrdersByUserIdandcompletedStatus(userId, status);
			if (paymentList.isEmpty()) {
				logger.error("No Payment found for this given : " + userId + " and " + status);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"No Payment exists for the given Username " + userId + ",please provide a valid User Name");
			} else {
				for (PaymentDetails paymentObj : paymentList) {
					paymentDtoList.add(paymentDetailsMapper.convert(paymentObj));
				}
				return paymentDtoList;
			}
		}
	}

	@Override
	@Transactional
	public String updatePaymentDetails(PaymentRequest paymentDto) {
		try {
			PaymentDetails pd = paymentRepository.getPaymentDetails(paymentDto.getOrderId());
			if (pd != null) {
				/*
				 * Double advanceAmt = pd.getAdvanceAmount() + paymentDto.getAdvanceAmount();
				 * Double balanceAmt = pd.getTotalAmount() - advanceAmt;
				 */
				pd.setTotalAmount(paymentDto.getTotalAmount());
				// Double advanceAmt =paymentDto.getAdvanceAmount();
				// Double balanceAmt =paymentDto.getBalanceAmount();
				// if (advanceAmt <= pd.getTotalAmount()) {
				pd.setAdvanceAmount(paymentDto.getAdvanceAmount());
				pd.setBalanceAmount(paymentDto.getBalanceAmount());
				/*
				 * if (balanceAmt == 0) { pd.setPaymentStatus("COMPLETED");
				 * pd.setIsCompleted(true); } else { pd.setPaymentStatus("PAYMENT_INITIATED"); }
				 */
				pd.setPaymentStatus(paymentDto.getPaymentStatus());
				if (paymentDto.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
					pd.setIsCompleted(true);
					pd.setDueAmount(100.00);
					pd.setUpdatedAt(new Date());
				}

				pd.setUpdatedBy(paymentDto.getUserId());
				pd.setUpdatedAt(new Date());
				paymentRepository.save(pd);

				Order orderObj = orderRepository.findByOrderId(paymentDto.getOrderId());
				OrderDetails od = orderDetailsTypeRepository.findByOrderIdAndStatus(paymentDto.getOrderId(),
						"ORDER_ACCEPTED");

				if (orderObj != null && od != null) {
					orderObj.setPaymentStatus(pd.getPaymentStatus());
					orderObj.setUpdatedAt(new Date());
					// orderObj.setUpdatedBy(paymentDto.getUserId());
					if (pd.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
						orderObj.setOrderStatus("ORDER_COMPLETED");
					}
					orderRepository.save(orderObj);

					od.setPaymentStatus(pd.getPaymentStatus());
					od.setUpdatedAt(new Date());
					od.setUpdatedBy(paymentDto.getUserId());
					if (orderObj.getOrderStatus().equals("ORDER_COMPLETED")) {
						od.setOrderStatus("ORDER_COMPLETED");
					}
					orderDetailsTypeRepository.save(od);
					PaymentLog paymentLog = null;
					paymentLog = paymentLogRepository.findByUserName(od.getServiceExecutorUserId());

					if (paymentLog != null) {
						Double due = paymentLog.getTotalDue();
						paymentLog.setTotalDue(due + 100.00);
					} else {
						paymentLog = new PaymentLog();
						paymentLog.setTotalDue(100.00);
						paymentLog.setUserName(od.getServiceExecutorUserId());
					}
					paymentLog.setLastPaymentReceived(new Date());
					paymentLogRepository.save(paymentLog);

				}
				// } else {
				// return "Failed";
				// }
				return "Success";
			} else {
				return "Failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}
	}

	@Override
	public String reUpdatePaymentDetails(PaymentRequest paymentDto) {
		try {
			PaymentDetails pd = paymentRepository.getPaymentDetails(paymentDto.getOrderId());
			if (pd != null) {
				pd.setAdvanceAmount(paymentDto.getAdvanceAmount());
				pd.setBalanceAmount(paymentDto.getBalanceAmount());
				pd.setUpdatedAt(new Date());
				paymentRepository.save(pd);
				return "success";
			} else {
				return "failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}

	}

	@Override
	public List<OrderDTO> findCompletedSEOrders(String orderId, String status) throws BookingException {
		if (orderId.isEmpty() && status.isEmpty()) {
			logger.error("Find Completed Orders failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "OrderId and status are mandatory for this operation");
		} else {
			List<OrderDTO> orderDtoLst = new ArrayList<OrderDTO>();
			List<OrderDetails> orderList = orderDetailsTypeRepository.findOrdersByOrderIdandcompletedStatus(orderId,
					status);
			if (orderList.isEmpty()) {
				logger.error("No orders found for this given : " + orderId + " and " + status);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"No Orders exists for the given order Id " + orderId + " ,please provide a valid order Id");
			} else {
				for (OrderDetails orderObj : orderList) {
					orderDtoLst.add(orderDetailsMapper.convert(orderObj));
				}
				return orderDtoLst;
			}
		}
	}

	@Override
	public String updateLatLongOfServiceExecutor(UpdateLatLongDTO updateDto) throws BookingException {
		if (updateDto == null) {
			logger.error("updation failed due to input information is empty: " + updateDto);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Service Executor details is mandatory for updation");
		} else {
			OrderDetails orderDetails = null;
			if (updateDto.getServiceExecutorUserName() != null && updateDto.getOrderNumber() != null) {
				orderDetails = orderDetailsTypeRepository.findByServiceExecutorUserId(
						updateDto.getServiceExecutorUserName(), updateDto.getOrderNumber());
				if (orderDetails != null) {
					orderDetails.setServiceExLattitude(updateDto.getLattitude());
					orderDetails.setServiceExLongitude(updateDto.getLongitude());
					orderDetails.setUpdatedAt(new Date());
					orderDetailsTypeRepository.save(orderDetails);
					return "Lattitude and Longitude is updated successfully";
				} else {
					logger.error("updation failed due to given Service Executor name is not valid: "
							+ updateDto.getServiceExecutorUserName());
					throw new BookingException(ErrorCode.REQUEST_ERROR,
							"A valid Service Executor name is mandatory for updation");
				}
			} else {
				logger.error("updation failed due to Service Executor name is empty: "
						+ updateDto.getServiceExecutorUserName());
				throw new BookingException(ErrorCode.REQUEST_ERROR, "Service Executor name is mandatory for updation");
			}
		}
	}

	@Override
	public List<TrackOrderDTO> trackOrderBySeUserId(String userName) throws BookingException {
		if (userName.isEmpty()) {
			logger.error("Track order failed due to input information is empty:");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Username is mandatory for this operation");
		} else {
			List<TrackOrderDTO> orderDtoLst = new ArrayList<TrackOrderDTO>();
			List<OrderDetails> orderList = orderDetailsTypeRepository.trackOrdersByUserId(userName);
			if (orderList.isEmpty()) {
				logger.error("operation failed due to given User Name is not valid: " + userName);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"A valid User name is mandatory for this operation");
			} else {
				for (OrderDetails orderDetailsObj : orderList) {
					TrackOrderDTO trackDto = new TrackOrderDTO();
					trackDto.setOrderId(orderDetailsObj.getOrderNumber());
					trackDto.setServiceExecuterName(orderDetailsObj.getServiceDetailsId().getServiceExecutorName());
					trackDto.setServiceExLattitude(orderDetailsObj.getServiceExLattitude());
					trackDto.setServiceExLongitude(orderDetailsObj.getServiceExLongitude());
					trackDto.setFarmerLattitude(orderDetailsObj.getOrder().getLattitude());
					trackDto.setFarmerLongitude(orderDetailsObj.getOrder().getLongitude());
					trackDto.setServiceExecuterMobileNumber(orderDetailsObj.getServiceDetailsId().getPhoneNo());
					orderDtoLst.add(trackDto);
				}
				return orderDtoLst;
			}
		}
	}

	@Override
	public List<ServiceExecutorListDTO> getServiceExecutors(String name) throws BookingException {
		if (name.isEmpty()) {
			logger.error("operation failed due to input information is empty: " + name);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"Service Provider name is mandatory for this operation");
		} else {
			List<ServiceDetails> listServiceDetails = serviceDetailsRepository.findByUserName(name);
			if (listServiceDetails.isEmpty()) {
				logger.error("operation failed due to given Service Provider name is not valid: " + name);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"A valid Service Provider name is mandatory for this operation");
			} else {
				List<ServiceExecutorListDTO> listServiceExecutor = new ArrayList();
				listServiceDetails.forEach(serviceDetails -> {
					// if(serviceDetails.getUserType().equals("U_TYPE_SERVICE_EXECUTOR")) {
					ServiceExecutorListDTO serviceExecutor = new ServiceExecutorListDTO();
					serviceExecutor.setId(serviceDetails.getId());
					serviceExecutor.setExecutorName(serviceDetails.getServiceExecutorName());
					serviceExecutor.setExecutorPhone(serviceDetails.getPhoneNo());
					serviceExecutor.setOffSeasonPrice(serviceDetails.getOffSeasonPrice());
					serviceExecutor.setOnSeasonPrice(serviceDetails.getOnSeasonPrice());
					serviceExecutor.setUan(serviceDetails.getUanNo());
					serviceExecutor.setUnitOfMeasurement(serviceDetails.getUnitMeasurementType());
					serviceExecutor.setVehicleNumber(serviceDetails.getVehicleNumber());
					serviceExecutor.setIdproofPhoto(serviceDetails.getIdproofPhoto());
					ServiceType servicetype = serviceRepository.findByName(serviceDetails.getServiceType());
					serviceExecutor.setServiceTypePhoto(servicetype.getImagePath());
					serviceExecutor.setServiceDesc(servicetype.getDescription());
					serviceExecutor.setServiceName(servicetype.getName());
					serviceExecutor.setUserType(serviceDetails.getUserType());
					listServiceExecutor.add(serviceExecutor);
					// }
					// else {
					// logger.error("operation failed due to no executor found for the given Service
					// Provider name : " + name);
					// throw new BookingException(ErrorCode.REQUEST_ERROR,"A valid Service Provider
					// name is mandatory for this operation");
					// }

				});
				return listServiceExecutor;
			}
		}
	}

	@Override
	public ServiceDetailsDTO updateServiceExecutorDetails(ServiceDetailsDTO serviceDetailsDto) throws BookingException {
		if (serviceDetailsDto != null) {
			// if(serviceDetailsDto.getUserName()!=null &&
			// serviceDetailsDto.getServiceType()!=null) {
			// ServiceDetails
			// serviceDetails=serviceDetailsRepository.getByUserNameAndServiceType(serviceDetailsDto.getUserName(),
			// serviceDetailsDto.getServiceType());
			if (serviceDetailsDto.getId() != null) {
				ServiceDetails serviceDetails = null;
				Optional<ServiceDetails> opt = serviceDetailsRepository.findById(serviceDetailsDto.getId());
				if (opt.isPresent()) {
					serviceDetails = opt.get();
				}
				if (serviceDetails != null) {
					UserDTO userDto = new UserDTO();
					userDto.setfName(serviceDetailsDto.getServiceExecutorName());
					userDto.setUan(serviceDetailsDto.getUanNo());
					userDto.setUsertype(serviceDetailsDto.getUserType());
					userDto.setProofType("IDF_TYPE_PAN");
					userDto.setImagePath(serviceDetailsDto.getIdproofPhoto());
					userDto.setPhoneNo(serviceDetailsDto.getPhoneNo());

					HttpHeaders headers = new HttpHeaders();
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDto, headers);
					logger.info("File entered for upload ");

					ResponseEntity<UserDTO> out = null;
					HttpStatus status = null;
					String name = serviceDetails.getServiceExecutorName();
					String phone = serviceDetails.getPhoneNo();
					String uan = serviceDetails.getUanNo();

					if (!name.equals(serviceDetailsDto.getServiceExecutorName())
							&& !phone.equals(serviceDetailsDto.getPhoneNo())
							&& !uan.equals(serviceDetailsDto.getUanNo())) {
						out = restTemplate.exchange(
								BookingConstant.ADMIN_APPLICATION_BASEURL + BookingConstant.SAVE_SERVICE_EXECUTOR_URL,
								HttpMethod.POST, entity, UserDTO.class);
						status = out.getStatusCode();
						logger.info("Response from checking" + out.getStatusCodeValue());
					} else {
						out = restTemplate.exchange(
								BookingConstant.ADMIN_APPLICATION_BASEURL + BookingConstant.UPDATE_SERVICE_EXECUTOR_URL,
								HttpMethod.PUT, entity, UserDTO.class);
						status = out.getStatusCode();
						logger.info("Response from checking" + out.getStatusCodeValue());
					}
					status = out.getStatusCode();
					logger.info("Response from checking" + out.getStatusCodeValue());

					if (status == HttpStatus.OK) {
						serviceDetails.setAvailability("AVAILABLE");
						serviceDetails.setLattitude(serviceDetailsDto.getLattitude());
						serviceDetails.setLongitude(serviceDetailsDto.getLongitude());
						serviceDetails.setOffSeasonPrice(serviceDetailsDto.getOffSeasonPrice());
						serviceDetails.setOnSeasonPrice(serviceDetailsDto.getOnSeasonPrice());
						serviceDetails.setPhoneNo(serviceDetailsDto.getPhoneNo());
						serviceDetails.setServiceExecutorName(serviceDetailsDto.getServiceExecutorName());
						serviceDetails.setUserType(serviceDetailsDto.getUserType());
						serviceDetails.setUanNo(serviceDetailsDto.getUanNo());
						serviceDetails.setUnitMeasurementType(serviceDetailsDto.getUnitMeasurementType());
						serviceDetails.setActive(true);
						serviceDetails.setIdproofPhoto(serviceDetailsDto.getIdproofPhoto());
						serviceDetails.setVehicleNumber(serviceDetailsDto.getVehicleNumber());
						// serviceDetails.setUserName(serviceDetailsDto.getUserName());
						serviceDetails.setCreatedAt(new Date());
						serviceDetails.setCreatedBy(serviceDetails.getUserName());
						serviceDetails.setUpdatedAt(new Date());
						serviceDetails.setUpdatedBy(serviceDetails.getUserName());
						serviceDetails.setZipCode(serviceDetailsDto.getZipCode());
						serviceDetailsRepository.save(serviceDetails);
						return serviceDetailsDto;
					} else {
						return null;
					}

				} else {
					logger.error("updation failed due to no service executor details is exists for given input->{}{} "
							+ serviceDetailsDto.getUserName() + " ," + serviceDetailsDto.getServiceType());
					throw new BookingException(ErrorCode.REQUEST_ERROR,
							"Service executor details is not found, please enter valid user name and service type");
				}

			} else {
				logger.error("updation failed due to input information is empty for->{}{} "
						+ serviceDetailsDto.getUserName() + " ," + serviceDetailsDto.getServiceType());
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"Service executor user name and service type is mandatory for updation");
			}

		} else {
			logger.error("updation failed due to input information is empty: " + serviceDetailsDto);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Service executor details is mandatory for updation");
		}

	}

	/*@Override
	public ServiceResponse getServiceDetailsInHindiByCategoryIdId(Long categoryId) throws BookingException {
		List<ServiceSubCategoryDto> serviceSubCategoryDtoList = new ArrayList<ServiceSubCategoryDto>();
		if (categoryId == null) {
			logger.error("You have entered empty category id");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Category ID is mandatory for this operation");
		} else {
			ServiceSubCategoryDto serviceSubCategoryDto = null;
			List<ServiceDTo> serviceDetailList = null;
			ServiceDTo serviceDTo = null;
			Map<ServiceSubCategoryType, List<ServiceType>> dto = null;
			ServiceResponse result = new ServiceResponse();
			List<ServiceType> serviceList = serviceRepository.getServiceDetailsByServiceCategoryType(categoryId);
			dto = serviceList.stream().collect(Collectors.groupingBy(ServiceType::getServiceSubCategoryType));
			if (dto.isEmpty()) {
				logger.error("There are no information found for this given category id " + categoryId);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This given category is not valid,please enter valid category id");
			} else {
				for (Entry<ServiceSubCategoryType, List<ServiceType>> entry : dto.entrySet()) {
					serviceSubCategoryDto = new ServiceSubCategoryDto();
					// Integer subCatId = entry.getKey().getId();
					serviceDetailList = new ArrayList<ServiceDTo>();
					serviceSubCategoryDto.setSubCategoryId(entry.getKey().getId());
					serviceSubCategoryDto.setSubCategoryName(entry.getKey().getName());
					serviceSubCategoryDto.setSubCategoryDesc(entry.getKey().getNameHindi());
					List<ServiceType> servicePreFilterList = entry.getValue();
					// List<ServiceType> serviceFilterList =
					// servicePreFilterList.parallelStream().filter(f ->
					// f.getServiceSubCategoryType().getId().equals(subCatId)).collect(Collectors.toList());
					for (ServiceType serviceObj : servicePreFilterList) {
	
						serviceDTo = new ServiceDTo();
						serviceDTo.setServiceDesc(serviceObj.getNameHindi());
						serviceDTo.setServiceName(serviceObj.getNameHindi());
						serviceDTo.setServiceId(serviceObj.getId());
						serviceDTo.setServicImagePath("/" + serviceObj.getImagePath());
						serviceDTo.setServiceExeCutorContType(serviceObj.getServiceExContactType().getId());
						serviceDTo.setServiceExeCutorContTypeName(serviceObj.getServiceExContactType().getName());
						serviceDTo
								.setServiceExeCutorContTypeDesc(serviceObj.getServiceExContactType().getDescription());
						serviceDetailList.add(serviceDTo);
					}
	
					serviceSubCategoryDto.setServiceDetails(serviceDetailList);
					serviceSubCategoryDtoList.add(serviceSubCategoryDto);
				}
	
				Collections.sort(serviceSubCategoryDtoList, new Comparator<ServiceSubCategoryDto>() {
					public int compare(ServiceSubCategoryDto obj1, ServiceSubCategoryDto obj2) {
						return obj1.getSubCategoryId().compareTo(obj2.getSubCategoryId());
					}
				});
	
				result.setServiceSubCategoryDto(serviceSubCategoryDtoList);
				return result;
			}
		}
	}*/

	@Override
	public ServiceResponse getAllSelfServices(String userName, ServiceCategoryType id) throws BookingException {
		/*
		 * if (userName == null) { logger.error("The input information is empty : " +
		 * userName); throw new BookingException(ErrorCode.REQUEST_ERROR,
		 * "Input information is mandatory for this operation"); } else {
		 * List<ServiceDetails> listServiceDetails =
		 * serviceDetailsRepository.findByUserName(userName); if (listServiceDetails ==
		 * null) { logger.error("No service details found for the given username : " +
		 * userName); throw new BookingException(ErrorCode.REQUEST_ERROR,
		 * "No service details exists for the given username " + userName +
		 * " ,please provide a valid username"); } else { List<ServiceType> serviceList
		 * = new ArrayList<ServiceType>(); List<SelfservicesDetailsDTO>
		 * listSelfServiceDto = new ArrayList<SelfservicesDetailsDTO>();
		 * listServiceDetails.forEach(serviceDetails -> { if
		 * (serviceDetails.getUserType().equals("U_TYPE_SERVICE_PROVIDER")) {
		 * SelfservicesDetailsDTO selfServiceDto = new SelfservicesDetailsDTO();
		 * ServiceType serviceType =
		 * serviceRepository.findByName(serviceDetails.getServiceType()); //
		 * selfServiceDto.setServiceType(serviceType.getDescription()); //
		 * selfServiceDto.setServiceTpeImage(serviceType.getImagePath()); //
		 * selfServiceDto.setSubCategory(serviceType.getServiceSubCategoryType().
		 * getDescription()); //
		 * selfServiceDto.setCategoryType(serviceType.getServiceCategoryType().
		 * getDescription()); //
		 * selfServiceDto.setCategoryImage(serviceType.getServiceCategoryType().
		 * getImagePath()); listSelfServiceDto.add(selfServiceDto);
		 * serviceList.add(serviceType); } }); List<ServiceSubCategoryDto>
		 * serviceSubCategoryDtoList = new ArrayList<ServiceSubCategoryDto>();
		 * ServiceSubCategoryDto serviceSubCategoryDto = null; List<ServiceDTo>
		 * serviceDetailList = null; ServiceDTo serviceDTo = null;
		 * Map<ServiceSubCategoryType, List<ServiceType>> dto = null; ServiceResponse
		 * result = new ServiceResponse();
		 * 
		 * dto = serviceList.stream().collect(Collectors.groupingBy(ServiceType::
		 * getServiceSubCategoryType)); List<SelfServiceDTO> selfServiceDTOList = new
		 * ArrayList(); for (Entry<ServiceSubCategoryType, List<ServiceType>> entry :
		 * dto.entrySet()) {
		 * 
		 * SelfServiceDTO selfServiceDTO = new SelfServiceDTO();
		 * selfServiceDTO.setParentServiceID(entry.getValue().get(0).
		 * getServiceCategoryType().getId()); selfServiceDTO
		 * .setParentServiceActive(entry.getValue().get(0).getServiceCategoryType().
		 * getIsActive()); selfServiceDTO
		 * .setParentServiceName(entry.getValue().get(0).getServiceCategoryType().
		 * getDescription()); serviceSubCategoryDto = new ServiceSubCategoryDto(); //
		 * Integer subCatId = entry.getKey().getId(); serviceDetailList = new
		 * ArrayList<ServiceDTo>();
		 * serviceSubCategoryDto.setSubCategoryId(entry.getKey().getId());
		 * serviceSubCategoryDto.setSubCategoryName(entry.getKey().getName());
		 * serviceSubCategoryDto.setSubCategoryDesc(entry.getKey().getDescription());
		 * List<ServiceType> servicePreFilterList = entry.getValue(); //
		 * List<ServiceType> serviceFilterList = //
		 * servicePreFilterList.parallelStream().filter(f -> //
		 * f.getServiceSubCategoryType().getId().equals(subCatId)).collect(Collectors.
		 * toList()); for (ServiceType serviceObj : servicePreFilterList) {
		 * 
		 * serviceDTo = new ServiceDTo();
		 * serviceDTo.setServiceDesc(serviceObj.getDescription());
		 * serviceDTo.setServiceName(serviceObj.getName());
		 * serviceDTo.setServiceId(serviceObj.getId());
		 * serviceDTo.setServicImagePath("/" + serviceObj.getImagePath());
		 * serviceDetailList.add(serviceDTo); }
		 * 
		 * serviceSubCategoryDto.setServiceDetails(serviceDetailList);
		 * serviceSubCategoryDtoList.add(serviceSubCategoryDto);
		 * selfServiceDTO.setServiceSubCategoryDto(serviceSubCategoryDtoList);
		 * selfServiceDTOList.add(selfServiceDTO); }
		 * 
		 * Collections.sort(serviceSubCategoryDtoList, new
		 * Comparator<ServiceSubCategoryDto>() { public int
		 * compare(ServiceSubCategoryDto obj1, ServiceSubCategoryDto obj2) { return
		 * obj1.getSubCategoryId().compareTo(obj2.getSubCategoryId()); } });
		 * 
		 * result.setSelfServiceDTO(selfServiceDTOList); return result; } }
		 */
		if (userName.isEmpty()) {
			logger.error("The input information is empty : " + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Input information is mandatory for this operation");
		} else {
			List<ServiceDetails> listServiceDetails = serviceDetailsRepository.findByUserName(userName);
			if (listServiceDetails.isEmpty()) {
				logger.error("No service details found for the given username : " + userName);
				throw new BookingException(ErrorCode.REQUEST_ERROR, "No service details exists for the given username "
						+ userName + " ,please provide a valid username");
			} else {
				List<ServiceType> serviceList = new ArrayList<ServiceType>();
				List<SelfservicesDetailsDTO> listSelfServiceDto = new ArrayList<SelfservicesDetailsDTO>();
				listServiceDetails.forEach(serviceDetails -> {
//			            if(serviceDetails.getUserType().equals("U_TYPE_SERVICE_PROVIDER") || serviceDetails.getUserType().equals("U_TYPE_SERVICE_EXECUTOR")) {
					// SelfservicesDetailsDTO selfServiceDto=new SelfservicesDetailsDTO();
					ServiceType serviceType = serviceRepository.getByNameAndCategoryId(serviceDetails.getServiceType(),
							id);
					// System.out.println(serviceType);
					// selfServiceDto.setServiceType(serviceType.getDescription());
					// selfServiceDto.setServiceTpeImage(serviceType.getImagePath());
					// selfServiceDto.setSubCategory(serviceType.getServiceSubCategoryType().getDescription());
					// selfServiceDto.setCategoryType(serviceType.getServiceCategoryType().getDescription());
					// selfServiceDto.setCategoryImage(serviceType.getServiceCategoryType().getImagePath());
					// listSelfServiceDto.add(selfServiceDto);
					if (serviceType != null) {
						serviceList.add(serviceType);
					}

					// }
				});
				List<ServiceSubCategoryDto> serviceSubCategoryDtoList = new ArrayList<ServiceSubCategoryDto>();
				ServiceSubCategoryDto serviceSubCategoryDto = null;
				List<ServiceDTo> serviceDetailList = null;
				ServiceDTo serviceDTo = null;
				Map<ServiceSubCategoryType, List<ServiceType>> dto = null;
				ServiceResponse result = new ServiceResponse();

				dto = serviceList.stream().collect(Collectors.groupingBy(ServiceType::getServiceSubCategoryType));
				for (Entry<ServiceSubCategoryType, List<ServiceType>> entry : dto.entrySet()) {
					serviceSubCategoryDto = new ServiceSubCategoryDto();
					// Integer subCatId = entry.getKey().getId();
					serviceDetailList = new ArrayList<ServiceDTo>();
					serviceSubCategoryDto.setSubCategoryId(entry.getKey().getId());
					serviceSubCategoryDto.setSubCategoryName(entry.getKey().getName());
					serviceSubCategoryDto.setSubCategoryDesc(entry.getKey().getDescription());
					List<ServiceType> servicePreFilterList = entry.getValue();
					// List<ServiceType> serviceFilterList =
					// servicePreFilterList.parallelStream().filter(f ->
					// f.getServiceSubCategoryType().getId().equals(subCatId)).collect(Collectors.toList());
					for (ServiceType serviceObj : servicePreFilterList) {

						serviceDTo = new ServiceDTo();
						List<ServiceDetails> serviceDetailsList = serviceDetailsRepository
								.getByUserNameAndServiceType(userName, serviceObj.getName());
						serviceDTo.setServiceDesc(serviceObj.getDescription());
						serviceDTo.setServiceName(serviceObj.getName());
						serviceDTo.setServiceExeCutorContType(serviceObj.getServiceExContactType().getId());
						serviceDTo.setServiceExeCutorContTypeName(serviceObj.getServiceExContactType().getName());
						serviceDTo
								.setServiceExeCutorContTypeDesc(serviceObj.getServiceExContactType().getDescription());
						// System.out.println(serviceObj.getName());
						serviceDTo.setServicImagePath("/" + serviceObj.getImagePath());
						serviceDTo.setServiceId(serviceObj.getId());
						for (ServiceDetails serviceDetails : serviceDetailsList) {
							serviceDTo.setServiceDetailsId(serviceDetails.getId());
							serviceDTo.setExecutorName(serviceDetails.getServiceExecutorName());
							serviceDTo.setExecutorPhone(serviceDetails.getPhoneNo());
							serviceDTo.setIdproofPhoto(serviceDetails.getIdproofPhoto());
							serviceDTo.setOffSeasonPrice(serviceDetails.getOffSeasonPrice());
							serviceDTo.setOnSeasonPrice(serviceDetails.getOnSeasonPrice());
							serviceDTo.setUan(serviceDetails.getUanNo());
							serviceDTo.setUnitOfMeasurement(serviceDetails.getUnitMeasurementType());
							serviceDTo.setVehicleNumber(serviceDetails.getVehicleNumber());
							serviceDTo.setUserType(serviceDetails.getUserType());
						}
						serviceDetailList.add(serviceDTo);
					}

					serviceSubCategoryDto.setServiceDetails(serviceDetailList);
					serviceSubCategoryDtoList.add(serviceSubCategoryDto);
				}

				Collections.sort(serviceSubCategoryDtoList, new Comparator<ServiceSubCategoryDto>() {
					public int compare(ServiceSubCategoryDto obj1, ServiceSubCategoryDto obj2) {
						return obj1.getSubCategoryId().compareTo(obj2.getSubCategoryId());
					}
				});

				result.setServiceSubCategoryDto(serviceSubCategoryDtoList);
				return result;

			}
		}
	}

	@Override
	public List<OrderDTO> findLastThreeOrdersByUserName(String userName) throws BookingException {
		List<OrderDTO> orderDtoLst = new ArrayList<OrderDTO>();
		if (userName.isEmpty()) {
			logger.error("You have entered empty information " + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "username is mandatory for this operation");
		} else {
			Pageable pageable = PageRequest.of(0, 3);
			List<Order> orderList = orderRepository.findByUserName(userName, pageable);
			if (orderList.isEmpty()) {
				logger.error("There are no information found for this given username " + userName);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This given user name is not valid,please enter valid username");
			} else {
				for (Order orderObj : orderList) {
					orderDtoLst.add(orderMapper.convert(orderObj));
				}
				return orderDtoLst;
			}
		}
	}

	@Override
	public List<MyServiceDetailsDTO> getServiceDetailsByUserName(String userName) throws BookingException {
		List<MyServiceDetailsDTO> serviceDtoList = new ArrayList<MyServiceDetailsDTO>();
		if (userName.isEmpty()) {
			logger.error("You have entered empty information " + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "username is mandatory for this operation");
		} else {
			List<ServiceDetails> serviceList = serviceDetailsRepository.findBySeviceExecutorName(userName);
			if (serviceList.isEmpty()) {
				logger.error("There are no information found for this given username " + userName);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This given user name is not valid,please enter valid username");
			} else {
				for (ServiceDetails serviceDetailsObj : serviceList) {
					MyServiceDetailsDTO serviceDetailsDto = new MyServiceDetailsDTO();
					serviceDetailsDto.setServiceName(serviceDetailsObj.getServiceType());
					DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
					String strDate = dateFormat.format(serviceDetailsObj.getCreatedAt());
					String strTime = timeFormat.format(serviceDetailsObj.getCreatedAt());
					serviceDetailsDto.setCreatedDate(strDate);
					serviceDetailsDto.setCreatedTime(strTime);
					serviceDtoList.add(serviceDetailsDto);
				}
				return serviceDtoList;
			}
		}
	}

	@Override
	public List<MyServiceDetailsDTO> getSEServiceDetails(String phone) throws BookingException {
		if (phone.isEmpty()) {
			logger.error("You have entered empty information ");
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Mobile no is mandatory for this operation");
		} else {
			List<MyServiceDetailsDTO> serviceDtoList = new ArrayList<MyServiceDetailsDTO>();
			List<ServiceDetails> serviceList = serviceDetailsRepository.findBySeviceExecutorMobile(phone);
			if (serviceList.isEmpty()) {
				logger.error("There are no information found for this given username " + phone);
				throw new BookingException(ErrorCode.REQUEST_ERROR,
						"This given mobile number is not valid,please enter valid mobile number");
			} else {
				for (ServiceDetails serviceDetailsObj : serviceList) {
					MyServiceDetailsDTO serviceDetailsDto = new MyServiceDetailsDTO();
					ServiceType serviceType = serviceRepository.findByName(serviceDetailsObj.getServiceType());
					serviceDetailsDto.setServiceName(serviceType.getDescription());
					serviceDetailsDto.setVehicleNumber(serviceDetailsObj.getVehicleNumber());
					serviceDetailsDto.setServiceCategory(serviceType.getServiceCategoryType().getDescription());
					serviceDetailsDto.setServiceSubCategory(serviceType.getServiceSubCategoryType().getDescription());
					serviceDetailsDto.setServiceTypeImage(serviceType.getImagePath());
					serviceDtoList.add(serviceDetailsDto);
				}
				return serviceDtoList;
			}
		}
	}

	@Override
	public String serviceStartSenddOtp(String mobile, String ORDERID) throws BookingException, UnirestException {
		if (mobile.length() == 10) {
			Order order = orderRepository.getByNumberAndOrderId(mobile, ORDERID);
			String EXENAME = order.getServiceExecutorName();
			//ServiceDetails serviceDetails = serviceDetailsRepository.getByExecutorNameAndServiceType(EXENAME,order.getServiceId().getName());
			//String EXEPHONE = serviceDetails.getPhoneNo();
			String EXEPHONE=order.getServiceOwnerMobile();

			String url = sendOtpBaseApi + sendOtpBaseTemplate
					+ userServiceStartOTP + msg91MobileNo
					+ indCode + mobile + sendOtpAuthKey
					+ authKey + expiryOtp + otpExpiryTime;

			/*
			 * System.out.println("{\"NAME\":\"" + EXENAME + "\",\"NUMBER\":\"" + "+91" +
			 * EXEPHONE + "\",\"ORDER_ID\":\"" + ORDERID + "\"}");
			 */

			HttpResponse<String> response = Unirest
					.post(url).header("content-type", "application/json").body("{\"NAME\":\"" + EXENAME
							+ "\",\"NUMBER\":\"" + "+91" + EXEPHONE + "\",\"ORDER_ID\":\"" + ORDERID + "\"}")
					.asString();

			return response.getBody();
			// return "hii";

		} else {

			logger.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public String serviceStartVerifyOtp(String mobile, String otp) throws BookingException, UnirestException {
		String url = BookingConstant.VERIFY_OTP + BookingConstant.SEND_OTP_AUTH_KEY + BookingConstant.MOBILE_NO
				+ BookingConstant.IND_CODE + mobile + BookingConstant.SE_OTP + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		return response.getBody();
	}

	@Override
	public String serviceCompleteSenddOtp(String mobile, String orderId) throws BookingException, UnirestException {
		if (mobile.length() == 10) {
			Order order = orderRepository.getByNumberAndOrderId(mobile, orderId);
			String exeName = order.getServiceExecutorName();
			//ServiceDetails serviceDetails = serviceDetailsRepository.getByExecutorNameAndServiceType(exeName,order.getServiceId().getName());
			//String exePhone = serviceDetails.getPhoneNo();
			String exePhone=order.getServiceOwnerMobile();

			String url = sendOtpBaseApi + sendOtpBaseTemplate
					+ userServiceCompletionOTP + msg91MobileNo
					+ indCode + mobile + sendOtpAuthKey
					+ authKey + expiryOtp + otpExpiryTime;

			HttpResponse<String> response = Unirest
					.post(url).header("content-type", "application/json").body("{\"NAME\":\"" + exeName
							+ "\",\"NUMBER\":\"" + "+91" + exePhone + "\",\"ORDER_ID\":\"" + orderId + "\"}")
					.asString();

			return response.getBody();
		} else {

			logger.error("Send otp failed due to mobile  number in wrong format" + mobile);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "The mobile number should be 10 digits");
		}
	}

	@Override
	public String serviceCompleteVerifyOtp(String mobile, String otp) throws BookingException, UnirestException {
		String url = BookingConstant.VERIFY_OTP + BookingConstant.SEND_OTP_AUTH_KEY + BookingConstant.MOBILE_NO
				+ BookingConstant.IND_CODE + mobile + BookingConstant.SE_OTP + otp;
		HttpResponse<String> response = Unirest.get(url).asString();
		return response.getBody();
	}

	@Override
	public String serviceOwnerNewServiceReqMsg(String mobile, String orderId)
			throws BookingException, UnirestException {

		Order order = orderRepository.findByOrderId(orderId);
		String farmerName = order.getRequesterName();
		String serviceType = order.getServiceId().getDescription();

		HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "362351Aw39Lf0Zt60c43e55P1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"60f29f2a0b9cd900a977f53a\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + farmerName + "\",\"SERVICE_TYPE\":\"" + serviceType + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceOwnerUpdateServiceReqMsg(String mobile, String orderId)
			throws BookingException, UnirestException {

		Order order = orderRepository.findByOrderId(orderId);
		String farmerName = order.getRequesterName();
		String serviceType = order.getServiceId().getDescription();

		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerServiceRequestUpdation + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode + mobile
						+ "\",\"USER_NAME\":\"" + farmerName + "\",\"SERVICE_TYPE\":\"" + serviceType
						+ "\",\"ORDER_ID\":\"" + orderId + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceOwnerCancelServiceReqMsg(String orderId) throws BookingException, UnirestException {

		Order order = orderRepository.findByOrderId(orderId);
		String farmerName = order.getRequesterName();
		String mobile = order.getRequesterContact();
		String ownerName = order.getServiceOwnerName();
		String serviceType = order.getServiceId().getDescription();
		// System.out.println(farmerName + " " + mobile + " " + ownerName + " " +
		// serviceType);

		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerServiceRequestCancellation + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode + mobile
						+ "\",\"USER_NAME\":\"" + farmerName + "\",\"SERVICE_TYPE\":\"" + serviceType
						+ "\",\"ORDER_ID\":\"" + orderId + "\",\"OWNER_NAME\":\"" + ownerName + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceExeNewServiceReqMsg(String mobile, String orderId) throws BookingException, UnirestException {
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerAssociateServiceAllocation + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode + mobile
						+ "\",\"ORDER_ID\":\"" + orderId + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceExeUpdateServiceReqMsg(String mobile, String orderId)
			throws BookingException, UnirestException {
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerAssociateOrderUpdation + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode + mobile
						+ "\",\"ORDER_ID\":\"" + orderId + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceExeCancelServiceReqMsg(String mobile, String orderId)
			throws BookingException, UnirestException {
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerAssociateServiceCancellation + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode + mobile
						+ "\",\"ORDER_ID\":\"" + orderId + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String seEnrollmentMsg(String mobile, String serviceName) throws BookingException, UnirestException {
		ServiceDetails serviceDetails = serviceDetailsRepository.getByPhoneNo(mobile, serviceName);
		String ownerName = serviceDetails.getServiceOwnerName();
		// System.out.println(ownerName + "--" + mobile);
		HttpResponse<String> response = Unirest.post(smsFlowUrl)
				.header("authkey", authKey).header("content-type", "application/JSON")
				.body("{\"flow_id\":\"" + partnerAssociateEnrolment + "\",\"sender\":\"" + senderId + "\",\"mobiles\":\"" + indCode + mobile
						+ "\",\"OWNER_NAME\":\"" + ownerName + "\"}")
				.asString();
		return response.getBody();
	}

	@Override
	public String userPaymentUpdateNotification(String orderId) throws BookingException, UnirestException {
		System.out.println(orderId);
		Order order = orderRepository.findByOrderId(orderId);
		if (order == null) {
			System.out.println(order);
		}
		String mobile = order.getRequesterContact();
		String ownerName = order.getServiceOwnerName();

		HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "362351Aw39Lf0Zt60c43e55P1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"60f31ada138bcc19c71839b4\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + ownerName + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceOwnerPaymentUpdateApprove(String orderId) throws BookingException, UnirestException {
		Order order = orderRepository.findByOrderId(orderId);
		String mobile = order.getServiceOwnerMobile();
		String farmerName = order.getRequesterName();
		HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
				.header("authkey", "362351Aw39Lf0Zt60c43e55P1").header("content-type", "application/JSON")
				.body("{\"flow_id\":\"60f319a7c9bd407a9770d9c3\",\"sender\":\"BALATA\",\"mobiles\":\"91" + mobile
						+ "\",\"NAME\":\"" + farmerName + "\"," + "\"ORDER_ID\":\"" + orderId + "\"}")
				.asString();

		return response.getBody();
	}

	@Override
	public String serviceOwnerPaymentUpdateReject(String orderId) throws BookingException, UnirestException {
		Order order = orderRepository.findByOrderId(orderId);
		String mobile = order.getServiceOwnerMobile();
		String farmerName = order.getRequesterName();
		System.out.println(mobile + "--" + farmerName);
//        HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
//				  .header("authkey", "362351Aw39Lf0Zt60c43e55P1")
//				  .header("content-type", "application/JSON")
//				  .body("{\"flow_id\":\"60f31eeb8766bd0b2c05d8e2\",\"sender\":\"BALATA\",\"mobiles\":\"91"+mobile+"\",\"NAME\":\"" + farmerName +"\","
//				  		+ "\"ORDER_ID\":\""+orderId+"\"}")
//				  .asString();
//		
//		return response.getBody();
		return null;
	}

	@Override
	public ServiceCategoryType saveServiceCategoryType(AdminMetadata metaData) {
		ServiceCategoryType categoryType = null;
		if (metaData.getId() != null) {
			Optional<ServiceCategoryType> opt = categoryTypeRepository.findById(metaData.getId());
			if (opt.isPresent()) {
				categoryType = opt.get();
			}
		} else {
			categoryType = new ServiceCategoryType();
		}

		categoryType.setDescription(metaData.getDescription());
		categoryType.setName(metaData.getName());
		categoryType.setIsActive(true);
		categoryType.setImagePath(metaData.getPhoto());
		ServiceSubCategoryTypeDTO subCatDto = new ServiceSubCategoryTypeDTO();
		return categoryTypeRepository.save(categoryType);
	}

	@Override
	public ServiceExecutorContactType saveServiceExecutorContactType(AdminMetadata metaData) {
		ServiceExecutorContactType serviceContact = null;
		if (metaData.getId() != null) {
			Optional<ServiceExecutorContactType> opt = serviceExecutorRepository.findById(metaData.getId());
			if (opt.isPresent()) {
				serviceContact = opt.get();
			}
		} else {
			serviceContact = new ServiceExecutorContactType();
		}

		serviceContact.setDescription(metaData.getDescription());
		serviceContact.setName(metaData.getName());
		return serviceExecutorRepository.save(serviceContact);
	}

	@Override
	public UnitMeasurementType saveUnitOfMeasurementType(AdminMetadata metaData) {
		UnitMeasurementType uomType = null;
		if (metaData.getId() != null) {
			Optional<UnitMeasurementType> opt = unitRepository.findById(metaData.getId());
			if (opt.isPresent()) {
				uomType = opt.get();
			}
		} else {
			uomType = new UnitMeasurementType();
		}

		uomType.setDescription(metaData.getDescription());
		uomType.setName(metaData.getName());
		uomType.setActive(true);
		return unitRepository.save(uomType);
	}

	@Override
	public ServiceSubCategoryTypeDTO saveServiceSubCatType(ServiceSubCatMetaData serviceSubCatMeta) {
		ServiceSubCategoryType serviceSubCatType = null;
		if (serviceSubCatMeta.getId() != null) {
			Optional<ServiceSubCategoryType> opt = subCategoryRepository.findById(serviceSubCatMeta.getId());
			if (opt.isPresent()) {
				serviceSubCatType = opt.get();
			}

		} else {
			serviceSubCatType = new ServiceSubCategoryType();
		}

		serviceSubCatType.setDescription(serviceSubCatMeta.getDescription());
		serviceSubCatType.setName(serviceSubCatMeta.getName());
		ServiceCategoryType categoryType = null;
		if (serviceSubCatMeta.getCategoryDescription() != null) {
			categoryType = categoryTypeRepository.findByDescription(serviceSubCatMeta.getCategoryDescription());
		}
		serviceSubCatType.setServiceCategoryType(categoryType);
		serviceSubCatType = subCategoryRepository.save(serviceSubCatType);
		ServiceSubCategoryTypeDTO subCatDto = new ServiceSubCategoryTypeDTO();
		subCatDto.setId(serviceSubCatType.getId());
		subCatDto.setDescription(serviceSubCatType.getDescription());
		subCatDto.setName(serviceSubCatType.getName());
		subCatDto.setServiceCategoryTypeID(serviceSubCatType.getServiceCategoryType().getId());
		return subCatDto;
	}

	@Override
	public ServiceType saveServiceType(ServiceTypeMetaData serviceTypeMeta) {
		ServiceType serviceType = null;
		if (serviceTypeMeta.getId() != null) {
			Optional<ServiceType> opt = serviceRepository.findById(serviceTypeMeta.getId());
			if (opt.isPresent()) {
				serviceType = opt.get();
			}
		} else {
			serviceType = new ServiceType();
		}

		// serviceType.setDescription(serviceTypeMeta.getDescription());
		serviceType.setImagePath(serviceTypeMeta.getImage());
		serviceType.setDescription(serviceTypeMeta.getName());
		ServiceSubCategoryType serviceSubCat = null;
		if (serviceTypeMeta.getSubCatDescription() != null) {
			serviceSubCat = subCategoryRepository.findByDescription(serviceTypeMeta.getSubCatDescription());
			serviceType.setServiceSubCategoryType(serviceSubCat);
		}
		serviceType.setServiceCategoryType(serviceSubCat.getServiceCategoryType());
		ServiceExecutorContactType serviceExeCont = null;
		if (serviceTypeMeta.getServiceExeContDesc() != null) {
			serviceExeCont = serviceExecutorRepository.findByDescription(serviceTypeMeta.getServiceExeContDesc());
			serviceType.setServiceExContactType(serviceExeCont);
		}
		serviceType.setActive(true);
		return serviceRepository.save(serviceType);
	}

	@Override
	public List<ServiceSubCategoryTypeDTO> fetchAllSubCategory() {
		List<ServiceSubCategoryTypeDTO> listSubCatDto = new ArrayList();
		List<ServiceSubCategoryType> listSubCatType = subCategoryRepository.findAll();
		for (ServiceSubCategoryType subCatType : listSubCatType) {
			ServiceSubCategoryTypeDTO subCatDto = new ServiceSubCategoryTypeDTO();
			subCatDto.setId(subCatType.getId());
			subCatDto.setDescription(subCatType.getDescription());
			subCatDto.setName(subCatType.getName());
			subCatDto.setServiceCategoryTypeDesc(subCatType.getServiceCategoryType().getDescription());
			listSubCatDto.add(subCatDto);
		}

		return listSubCatDto;
	}

	@Override
	public List<ServiceTypeMetaData> fetchAllServiceType() {
		List<ServiceTypeMetaData> listServiceTypeMeta = new ArrayList();
		List<ServiceType> listServiceType = serviceRepository.findAll();
		for (ServiceType serviceType : listServiceType) {
			ServiceTypeMetaData serviceTypeMeta = new ServiceTypeMetaData();
			serviceTypeMeta.setId(serviceType.getId());
			// serviceTypeMeta.setDescription(serviceType.getDescription());
			serviceTypeMeta.setName(serviceType.getDescription());
			serviceTypeMeta.setImage(serviceType.getImagePath());
			serviceTypeMeta.setCategoryDesc(serviceType.getServiceCategoryType().getDescription());
			serviceTypeMeta.setSubCatDescription(serviceType.getServiceSubCategoryType().getDescription());
			serviceTypeMeta.setServiceExeContDesc(serviceType.getServiceExContactType().getDescription());
			listServiceTypeMeta.add(serviceTypeMeta);
		}
		return listServiceTypeMeta;
	}

	@Override
	public List<ServiceExecutorContactType> fetchAllServiceExecutorContactType() {
		// TODO Auto-generated method stub
		return serviceExecutorRepository.findAll();
	}

	@Override
	public List<UnitMeasurementType> fetchAllUnitOfMeasurementType() {
		// TODO Auto-generated method stub
		return unitRepository.findAll();
	}

	@Override
	public NotificationMessage makeNotificationMessage(PushNotificationRequest request) {
		NotificationMessage notificationRequest = new NotificationMessage().getMessage(request);
		return notificationRequest;
	}

	@Override
	public NotificationMessage makeNotificationMessage(PushNotificationRequest request, OrderDTO orderDto) {
		NotificationMessage notificationRequest = new NotificationMessage().getMessage(request, orderDto);
		return notificationRequest;
	}

	@Override
	public List<String> getUserIdByOrder(String orderId) {
		List<String> createdBy = orderDetailsTypeRepository.findByOrderId(orderId);
		return createdBy;
	}

	@Override
	public List<PaymentDto> getAllCompleteOrders(String userName) throws BookingException {
		if (userName == null) {

			logger.error("operation failed due to input information is empty" + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to input information is empty, please provide user name");

		} else {
			List<OrderDetails> listOrderDetails = orderDetailsTypeRepository.findByServiceExecutorUserId(userName);
			List<PaymentDto> listPaymentDto = new ArrayList();
			listOrderDetails.forEach(orderDetails -> {
				if (orderDetails.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
					PaymentDto paymentDto = new PaymentDto();
					PaymentDetails paymentDetails = paymentRepository.getPaymentDetails(orderDetails.getOrderNumber());
					paymentDto.setAdvanceAmount(paymentDetails.getAdvanceAmount());
					paymentDto.setBalanceAmount(paymentDetails.getBalanceAmount());
					paymentDto.setTotalAmount(paymentDetails.getTotalAmount());
					paymentDto.setOrderId(orderDetails.getOrderNumber());
					Double paid = (paymentDetails.getTotalAmount() - paymentDetails.getAdvanceAmount())
							- paymentDetails.getBalanceAmount();
					paymentDto.setPaidAmount(paid);
					DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
					String strDate = dateFormat.format(paymentDetails.getUpdatedAt());
					String strTime = timeFormat.format(paymentDetails.getUpdatedAt());
					paymentDto.setStartDate(strDate);
					paymentDto.setStartTime(strTime);
					listPaymentDto.add(paymentDto);
				}
			});
			return listPaymentDto;
		}

	}

//	@Override
//	public List<ServiceDetailsDTO> listOfServices(String userName) throws BookingException {
//		List<ServiceDetailsDTO> serviceDtoList = new ArrayList<ServiceDetailsDTO>();
//		if (userName.isEmpty()) {
//			logger.error("You have entered empty information " + userName);
//			throw new BookingException(ErrorCode.REQUEST_ERROR, "username is mandatory for this operation");
//		} else {
//			List<ServiceDetails> serviceList = serviceDetailsRepository.findBySeviceExecutorName(userName);
//			if (serviceList.isEmpty()) {
//				logger.error("There are no information found for this given username " + userName);
//				throw new BookingException(ErrorCode.REQUEST_ERROR,
//						"This given user name is not valid,please enter valid username");
//			} else {
//				for (ServiceDetails serviceDetailsObj : serviceList) {
//					ServiceDetailsDTO serviceDetailsDto = new ServiceDetailsDTO();
//					serviceDetailsDto.setServiceType(serviceDetailsObj.getServiceType());
//					serviceDtoList.add(serviceDetailsDto);
//				}
//				return serviceDtoList;
//			}
//		}
//	}
	@Override
	public List<OrderDetailsDTO> listOfProviderList(String orderId) throws BookingException {
		List<OrderDetailsDTO> orderList = new ArrayList<OrderDetailsDTO>();
		if (orderId.isEmpty()) {
			logger.error("You have entered empty information " + orderId);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "Order Id is mandatory for this operation");
		} else {
			List<OrderDetails> orderDetailsList = orderDetailsTypeRepository.findProviderListByOrderId(orderId);
			for (OrderDetails orderDetailsObj : orderDetailsList) {
				OrderDetailsDTO providerList = new OrderDetailsDTO();
				providerList.setServiceExecuterName(orderDetailsObj.getServiceExecutorUserId());
				orderList.add(providerList);
			}
			return orderList;
		}
	}

	@Override
	public List<PaymentDto> getAllCompleteOrdersBySelf(String userName) throws BookingException {
		if (userName == null) {

			logger.error("operation failed due to input information is empty" + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to input information is empty, please provide user name");

		} else {
			List<ServiceDetails> listServiceDetails = serviceDetailsRepository.findByUserName(userName);
			List<PaymentDto> listPaymentDto = new ArrayList();

			for (ServiceDetails serviceDetails : listServiceDetails) {
				List<OrderDetails> listOrderDetails = new ArrayList();
				if (serviceDetails.getUserType().equals("U_TYPE_SERVICE_PROVIDER"))
					listOrderDetails = orderDetailsTypeRepository.findByServiceDetailsId(serviceDetails);

				if (!listOrderDetails.isEmpty()) {
					for (OrderDetails orderDetails : listOrderDetails) {
						if (orderDetails.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
							PaymentDto paymentDto = new PaymentDto();
							PaymentDetails paymentDetails = paymentRepository
									.getPaymentDetails(orderDetails.getOrderNumber());
							paymentDto.setAdvanceAmount(paymentDetails.getAdvanceAmount());
							paymentDto.setBalanceAmount(paymentDetails.getBalanceAmount());
							paymentDto.setTotalAmount(paymentDetails.getTotalAmount());
							paymentDto.setOrderId(orderDetails.getOrderNumber());
							Double paid = (paymentDetails.getTotalAmount() - paymentDetails.getAdvanceAmount())
									- paymentDetails.getBalanceAmount();
							paymentDto.setPaidAmount(paid);
							DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
							DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
							String strDate = dateFormat.format(paymentDetails.getUpdatedAt());
							String strTime = timeFormat.format(paymentDetails.getUpdatedAt());
							paymentDto.setStartDate(strDate);
							paymentDto.setStartTime(strTime);
							listPaymentDto.add(paymentDto);
						}
					}

				}
			}
			return listPaymentDto;
		}

	}

	@Override
	public List<PaymentDto> getAllCompleteOrdersByExecutor(String phone) throws BookingException {
		if (phone == null) {

			logger.error("operation failed due to input information is empty" + phone);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to input information is empty, please provide user name");

		} else {
			List<Order> listOrder = orderRepository.findByServiceOwnerMobile(phone);

			List<PaymentDto> listPaymentDto = new ArrayList();
			listOrder.forEach(order -> {
				if (order.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
					PaymentDto paymentDto = new PaymentDto();
					PaymentDetails paymentDetails = paymentRepository.getPaymentDetails(order.getOrderId());

					paymentDto.setAdvanceAmount(paymentDetails.getAdvanceAmount());
					paymentDto.setBalanceAmount(paymentDetails.getBalanceAmount());
					paymentDto.setTotalAmount(paymentDetails.getTotalAmount());
					paymentDto.setOrderId(order.getOrderId());
					Double paid = (paymentDetails.getTotalAmount() - paymentDetails.getAdvanceAmount())
							- paymentDetails.getBalanceAmount();
					paymentDto.setPaidAmount(paid);
					DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
					String strDate = dateFormat.format(paymentDetails.getUpdatedAt());
					String strTime = timeFormat.format(paymentDetails.getUpdatedAt());
					paymentDto.setStartDate(strDate);
					paymentDto.setStartTime(strTime);
					listPaymentDto.add(paymentDto);
				}
			});

			return listPaymentDto;
		}

	}

	@Override
	public List<PaymentDto> getPaymentInvoice(String userName) throws BookingException {
		if (userName == null) {
			logger.error("operation failed due to input information is empty" + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to input information is empty, please provide user name");
		} else {
			List<OrderDetails> listOrderDetails = orderDetailsTypeRepository.findByServiceExecutorUserId(userName);
			List<PaymentDto> listPaymentDto = new ArrayList();
			listOrderDetails.forEach(orderDetails -> {
				if (orderDetails.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
					PaymentDto paymentDto = new PaymentDto();
					PaymentDetails paymentDetails = paymentRepository.getPaymentDetails(orderDetails.getOrderNumber());
					paymentDto.setDueAmount(paymentDetails.getDueAmount());
					paymentDto.setOrderId(orderDetails.getOrderNumber());

					DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
					DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
					String strDate = dateFormat.format(paymentDetails.getUpdatedAt());
					String strTime = timeFormat.format(paymentDetails.getUpdatedAt());
					paymentDto.setStartDate(strDate);
					paymentDto.setStartTime(strTime);
					listPaymentDto.add(paymentDto);
				}
			});
			return listPaymentDto;
		}

	}

	@Override
	public PaymentDto getPaymentLog(String userName) throws BookingException {
		PaymentLog paymentLog = paymentLogRepository.findByUserName(userName);
		if (paymentLog != null) {
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setTotalDue(paymentLog.getTotalDue());
			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
			String strDate = dateFormat.format(paymentLog.getLastPaymentReceived());
			paymentDto.setStartDate(strDate);
			return paymentDto;

		} else {
			logger.error("operation failed due to no single order has completed by this given user name" + userName);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to no single order has completed by this given user name: " + userName
							+ ", please complete an order first");
		}
	}

	@Override
	public List<PaymentInvoiceDTO> getPaymentInvoiceByUserNameAndDate(String userName, String date)
			throws BookingException {
		List<PaymentInvoiceDTO> listInvoiceDto = new ArrayList<PaymentInvoiceDTO>();
		if (userName.isEmpty() || date.isEmpty()) {
			logger.error("operation failed due to not entered username and date");
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to not entered date and username,please enter date and username");
		} else {
			List<PaymentInvoice> invoiceList = invoiceRepository.findByServiceOwnerName(userName, date);
			for (PaymentInvoice invoice : invoiceList) {
				PaymentInvoiceDTO dto = new PaymentInvoiceDTO();
				dto.setInvoiceNo(invoice.getInvoiceNo());
				dto.setInvoiceDate(invoice.getInvoiceDate());
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String orderDate = dateFormat.format(invoice.getOrderExecutionDate());
				dto.setOrderDate(orderDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(invoice.getOrderExecutionDate());
				cal.add(Calendar.DATE, 3);
				Date dateWith3Days = cal.getTime();
				DateFormat dueDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String strDueDate = dueDateFormat.format(dateWith3Days);
				dto.setDueDate(strDueDate);
				dto.setDueAmount(invoice.getDueAmount());
				dto.setOrderId(invoice.getOrderId());
				listInvoiceDto.add(dto);
			}
			return listInvoiceDto;
		}
	}

	@Override
	public List<PaymentDto> doPayment(List<String> listOrderNumber) throws BookingException {
		System.out.println(listOrderNumber);
		List<PaymentDto> listPaymentDto = new ArrayList();
		listOrderNumber.forEach(orderNumber -> {
			System.out.println("orderNum: " + orderNumber);
			PaymentDetails paymentDetails = paymentRepository.getPaymentDetails(orderNumber);
			paymentDetails.setDuePaymentStatus("PAID PAYMENT");
			List<OrderDetails> orderDetails = orderDetailsTypeRepository.findByOrderNumber(orderNumber);
			PaymentLog paymentLog = paymentLogRepository.findByUserName(orderDetails.get(0).getServiceExecutorUserId());
			Double due = paymentLog.getTotalDue();
			paymentLog.setTotalDue(due - 100.00);
			paymentLog.setLastPaymentReceived(new Date());
			paymentRepository.save(paymentDetails);
			paymentLogRepository.save(paymentLog);
			PaymentDto paymentDto = new PaymentDto();
			paymentDto.setDueAmount(paymentDetails.getDueAmount());
			paymentDto.setOrderId(orderDetails.get(0).getOrderNumber());
			listPaymentDto.add(paymentDto);
		});
		return listPaymentDto;
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
	public int getPSRCount(String userId, long serviceId, Date min, Date max) {

		int count = orderDetailsTypeRepository.findPendingSRCount(userId, serviceId, min, max);
		return count;
	}

	@Override
	public void genrateInvoicePerDay(Date min, Date max) {
		// PaymentDetails pdObj=null;
		// pdObj=paymentRepository.getPaymentDetails(paymentInvoice.getOrderId());
		List<OrderDetails> orderObj = orderDetailsTypeRepository.getOrderDetails(min, max);
		List<PaymentInvoice> saveAll = new ArrayList<PaymentInvoice>();
		String id = "";
		String InvoiceNo = "0";
		RandomString rd = new RandomString(15);
		for (OrderDetails od : orderObj) {
			PaymentInvoice invoicePayment = new PaymentInvoice();
			invoicePayment.setOrderId(od.getOrderNumber());
			invoicePayment.setServiceOwnerId(od.getServiceExecutorUserId());
			invoicePayment.setDueAmount(payment);
			invoicePayment.setDueAmountStatus("PENDING PAYMENT");
			String invoiceDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			invoicePayment.setInvoiceDate(invoiceDate);
			invoicePayment.setOrderExecutionDate(od.getOrderExecutionTime());
			invoicePayment.setAmountReceivedDate(new Date());

			if (!id.contains(od.getServiceExecutorUserId())) {
				InvoiceNo = rd.nextString();

			}
			id = od.getServiceExecutorUserId();

			invoicePayment.setInvoiceNo(InvoiceNo);
			saveAll.add(invoicePayment);
		}
		invoiceRepository.saveAll(saveAll);
	}

	@Override
	public List<PaymentInvoice> getByUsernameDate(String username, String date) throws BookingException {
		List<PaymentInvoice> listInvoice = null;
		if (username.isEmpty() && date.isEmpty()) {
			logger.error("operation failed due to no single order has completed by this given user name" + username);
			throw new BookingException(ErrorCode.REQUEST_ERROR,
					"operation failed due to no single order has completed by this given user name: " + username
							+ ", please complete an order first");
		} else {
			// List<PaymentInvoiceDTO> listDto=new ArrayList<PaymentInvoiceDTO>();
			listInvoice = invoiceRepository.findByServiceOwnerName(username, date);

		}
		return listInvoice;
	}

	@Override
	public List<PaymentInvoice> getPendingInvoicePayment(String date) {
		List<PaymentInvoice> listInvoice = null;
		listInvoice = invoiceRepository.findPendingInvoicePayment(date);
		return listInvoice;
	}

	@Override
	public int updateInvoice(String invoiceNo) {
		int result = 0;
		result = invoiceRepository.updateInvoice(new Date(), invoiceNo);
		return result;
	}

	@Override
	public Order reCreateOrder(Order orderObject) {
		List<ServiceDetails> serviceLst = serviceDetailsRepository.findByServiceandZipcode(orderObject.getPinCode(),
				orderObject.getServiceId().getName());
		serviceLst.stream().limit(5).collect(Collectors.toList());
		String userId = null;

		Order order = new Order();

		if (serviceLst != null) {
			for (ServiceDetails sd : serviceLst) {
				if (sd.getActive()) {

					List<OrderDetails> lstOrerDetails = orderDetailsTypeRepository
							.findByOrderNumber(orderObject.getOrderId());
					order.setCreatedAt(lstOrerDetails.get(0).getOrderExecutionTime());
					for (ServiceDetails service : serviceLst) {
						// String
						// date=lstOrerDetails.get(0).getOrderExecutionTime().toString();//------from
						// orderDetails table execution date
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date date = lstOrerDetails.get(0).getOrderExecutionTime();
						String exeDate = format.format(date);// ------from orderDetails table execution date
						// Date minmax, min, max;
						userId = service.getUserName();
						SimpleDateFormat minmax;
						Date min, max;
						int count = 0;
						// SimpleDateFormat minmax=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						// Date min=new SimpleDateFormat("yyyy-MM-dd
						// HH:mm:ss.SSS").parse(minmax+"00:00:00.000");
						// Date min=minmax.parse(date+"00:00:00.000");
						// Date max=minmax.parse(date+"23:59:59.000");
						try {
//					minmax = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//					min=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 00:00:00.000");
//					max=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 23:59:59.000");

							minmax = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
							min = minmax.parse(exeDate + " 00:00:00.000");
							max = minmax.parse(exeDate + " 23:59:59.000");
							count = orderDetailsTypeRepository.findPendingSRCount(userId,
									orderObject.getServiceId().getId(), min, max);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (count < 5) {
							OrderDetails odObj = new OrderDetails();
							odObj.setOrder(orderObject);
							odObj.setOrdeNumber(orderObject.getOrderId());
							odObj.setOrderStatus("NEW");
							odObj.setPaymentStatus("PENDING");
							odObj.setServiceDetailsId(service);
							odObj.setCreatedAt(new Date());
							odObj.setUpdatedAt(new Date());
							odObj.setServiceID(orderObject.getServiceId().getId());
							odObj.setUpdatedBy(orderObject.getUpdatedBy());
							odObj.setCreatedBy(orderObject.getCreatedBy());
							// date=lstOrerDetails.get(0).getOrderExecutionTime().toString();//------from
							// orderDetails table execution date
							exeDate = lstOrerDetails.get(0).getOrderExecutionTime().toString();// ------from
																								// orderDetails table
																								// execution date
							Date startDate;
							try {
								startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(exeDate);
								odObj.setOrderExecutionTime(startDate);
							} catch (ParseException e) {
								e.printStackTrace();
							}

							odObj.setIsActive(true);
							odObj.setServiceExecutorUserId(service.getUserName());
							lstOrerDetails.add(odObj);
						}
					}
					orderDetailsTypeRepository.saveAll(lstOrerDetails);
				}
			}
		}
		return order;
	}

	public void SendPushNotificationHourly() {
		String status = "NEW_ORDER_CREATED";
		Date createdAt = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1));
		List<Order> orderList = orderRepository.findAllByStatus(status, createdAt);
		for (Order orderObj : orderList) {
			List<OrderDetails> orderDetailsList = orderDetailsTypeRepository.findByOrderNumber(orderObj.getOrderId());
			for (OrderDetails od : orderDetailsList) {
				RestTemplate restTemplate = new RestTemplate();
				String deviceToken = restTemplate.getForObject(
						BookingConstant.ADMIN_APPLICATION_BASEURL + "get/token/" + od.getServiceExecutorUserId(),
						String.class);
				new OrderController().sentNotificationHourlyWithoutActionBy(deviceToken, od.getServiceExecutorUserId(),
						od.getCreatedBy(), od.getOrderNumber());

			}
		}
	}

	@Override
	public String deleteServiceDetailsById(Long id) throws BookingException, UnirestException {
		String result = null;
		int status = 0;
		ServiceDetails sd = null;

		Long seId = 0L;
		List<OrderDetails> orderList = orderDetailsTypeRepository.getOrdersByServiceDetailsId(id);
		if (!orderList.isEmpty()) {
			logger.error("operation failed due to order has been already booked by this given serviceDetailsId " + id);
			throw new BookingException(ErrorCode.REQUEST_ERROR, "You can not delete this service details id: " + id
					+ ", because it has been already booked untill coplete the order");
		} else {
			sd = serviceDetailsRepository.findByServiceExeId(id);
			if (sd == null) {
				logger.error(
						"operation failed due to there is no Service Details by this given serviceDetailsId " + id);
				throw new BookingException(ErrorCode.REQUEST_ERROR, "Please enter a valid service details id: " + id);
			} else {
				String mobileNumber = sd.getPhoneNo();
				String userType = sd.getUserType();
				status = serviceDetailsRepository.count(mobileNumber, userType);
				if (status == 1 && sd.getUserType().equals("U_TYPE_SERVICE_EXECUTOR")) {
					String exeIdurl = BookingConstant.ADMIN_APPLICATION_BASEURL + "user/getId?mobileNumber="
							+ mobileNumber + "&userType=" + userType;
					seId = new RestTemplate().getForObject(exeIdurl, Long.class);
					String deleteUrl = BookingConstant.ADMIN_APPLICATION_BASEURL + "user/delete/" + seId;
					new RestTemplate().delete(deleteUrl);
					if (serviceDetailsRepository.existsById(id)) {
						serviceDetailsRepository.deleteById(id);

						result = id + " Id ServiceDetails deleted successfully";
					} else {
						result = "ServiceDetails Id-" + id + " not exists";
					}
				} else {
					if (serviceDetailsRepository.existsById(id)) {
						serviceDetailsRepository.deleteById(id);

						result = id + " Id ServiceDetails deleted successfully";
					} else {
						result = "ServiceDetails Id-" + id + " not exists";
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<ServiceProcessingFee> getServiceProcessingFee() {
		// TODO Auto-generated method stub
		List<PaymentInvoice> listInvoioce = invoiceRepository.findAll();
		List<ServiceProcessingFee> listProcessingFee = new ArrayList();
		listInvoioce.forEach(invoice -> {
			ServiceProcessingFee processingFee = new ServiceProcessingFee();
			processingFee.setServiceOwnerName(invoice.getServiceOwnerId());
			processingFee.setOrderId(invoice.getOrderId());
			processingFee.setDueAmount(invoice.getDueAmount());
			if (invoice.getDueAmountStatus().equals("PENDING PAYMENT")) {
				processingFee.setAmountPaid(false);
			} else {
				processingFee.setAmountPaid(true);
			}
			listProcessingFee.add(processingFee);
		});
		return listProcessingFee;
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
			newOrder.setCostOfService(order.getIndicativeRate());
			listOrderStatus.add(newOrder);
		});
		return listOrderStatus;
	}

	@Override
	public List<ServiceDetailsDTO> getOwnerOrderStatus() {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public Order findOrdersDetails(String serviceId, String createdBy, Date orderStartTime) throws BookingException {
//			Order orderObj = orderRepository.findByOrderDetails(serviceId, createdBy, orderStartTime);
//				return orderObj;
//	}

	@Override
	public ServiceResponse getServiceDetailsByCategoryId(Long categoryId, Long contactId) {
		// TODO Auto-generated method stub
		return null;
	}
}
