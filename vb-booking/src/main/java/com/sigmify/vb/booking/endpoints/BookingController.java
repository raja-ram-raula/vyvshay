package com.sigmify.vb.booking.endpoints;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sigmify.vb.booking.constants.BookingConstant;
import com.sigmify.vb.booking.dto.AdminMetadata;
import com.sigmify.vb.booking.dto.AdminPanelOrderStatus;
import com.sigmify.vb.booking.dto.MyServiceDetailsDTO;
import com.sigmify.vb.booking.dto.SearchResponse;
import com.sigmify.vb.booking.dto.ServiceCategoryTypeDTO;
import com.sigmify.vb.booking.dto.ServiceCategoryTypeDTOHi;
import com.sigmify.vb.booking.dto.ServiceDetailsDTO;
import com.sigmify.vb.booking.dto.ServiceExecutorListDTO;
import com.sigmify.vb.booking.dto.ServiceRequest;
import com.sigmify.vb.booking.dto.ServiceResponse;
import com.sigmify.vb.booking.dto.ServiceSubCatMetaData;
import com.sigmify.vb.booking.dto.ServiceSubCategoryTypeDTO;
import com.sigmify.vb.booking.dto.ServiceTypeDTO;
import com.sigmify.vb.booking.dto.ServiceTypeMetaData;
import com.sigmify.vb.booking.dto.UnitMeasurementTypeDTO;
import com.sigmify.vb.booking.entity.ServiceCategoryType;
import com.sigmify.vb.booking.entity.ServiceDetails;
import com.sigmify.vb.booking.entity.ServiceExecutorContactType;
import com.sigmify.vb.booking.entity.ServiceSubCategoryType;
import com.sigmify.vb.booking.entity.ServiceType;
import com.sigmify.vb.booking.entity.UnitMeasurementType;
import com.sigmify.vb.booking.exception.BookingException;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;
import com.sigmify.vb.booking.firebase.model.PushNotificationResponse;
import com.sigmify.vb.booking.firebase.service.PushNotificationService;
import com.sigmify.vb.booking.service.BookingService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/booking")
public class BookingController{
	
	private final Logger logger = Logger.getLogger(BookingController.class);
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	RestTemplate restTemplate;
	private static final String NOTIFICATION_URL=BookingConstant.ADMIN_APPLICATION_BASEURL + "user/notification/token";

	
	private PushNotificationService pushNotificationService;

    public BookingController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }
    
	/*
	 * @PostMapping("/notification/token") public ResponseEntity
	 * sendTokenNotification(@RequestBody PushNotificationRequest request) {
	 * PushNotificationRequest pushNotificationRequest = new
	 * PushNotificationRequest(); NotificationMessage notificationRequest =
	 * bookingService.makeNotificationMessage(request);
	 * pushNotificationRequest.setMessage(notificationRequest.getMessage());
	 * pushNotificationRequest.setTitle(notificationRequest.getTitle());
	 * pushNotificationRequest.setToken(request.getToken());
	 * pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
	 * return new ResponseEntity<>(new
	 * PushNotificationResponse(HttpStatus.OK.value(),
	 * "Notification has been sent."), HttpStatus.OK); }
	 */
	
   
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
    
    
	@GetMapping("/ping")
	public ResponseEntity<String> getTestApi(){
		return new ResponseEntity<String>("Test Api running successfully for docker!!!!!",HttpStatus.OK);
	}
	
    /**
     * to get active category list
     * @return
     */
	
	/*@GetMapping("/getAllCategory")
	public ResponseEntity<Map<String, List<ServiceCategoryTypeDTO>>> getAllCategory(){
		Map<String,List<ServiceCategoryTypeDTO>> response = new HashMap<String,List<ServiceCategoryTypeDTO>>();
		List<ServiceCategoryType> categoryList = bookingService.getActiveCategoryList();
		List<ServiceCategoryTypeDTO> result = null;
		try {
		if(categoryList != null) {
			result = categoryList.stream().map(this :: convertToServiceCategoryDto).collect(Collectors.toList());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
	}*/
	
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<ServiceCategoryTypeDTO>> getAlllCategory(@RequestParam String lang) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getActiveCategoryList(lang));
	}

	/*@GetMapping("/getAllCategory/hi")
	public ResponseEntity<Map<String, List<ServiceCategoryTypeDTOHi>>> getAllCategoryHi(){
		Map<String,List<ServiceCategoryTypeDTOHi>> response = new HashMap<String,List<ServiceCategoryTypeDTOHi>>();
		List<ServiceCategoryType> categoryList = bookingService.getActiveCategoryList();
		List<ServiceCategoryTypeDTOHi> result = null;
		try {
		if(categoryList != null) {
			result = categoryList.stream().map(this :: convertToServiceCategoryDtoHi).collect(Collectors.toList());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
	}*/
    /**
     * to get subcatagoory details by passing category id
     * @param categoryId
     * @return
     */
	
	/*@PostMapping("/getSubCategoryById/{categoryId}")
	public ResponseEntity<Map<String, List<ServiceSubCategoryTypeDTO>>> getSubCategoryById(@PathVariable Long categoryId) {
		List<ServiceSubCategoryType> subCategoryList = bookingService.getActiveSubCategoryListByCategoryId(categoryId);
		List<ServiceSubCategoryTypeDTO> result = null;
		Map<String, List<ServiceSubCategoryTypeDTO>> response = new HashMap<String, List<ServiceSubCategoryTypeDTO>>();
		try {
			if (subCategoryList != null) {
				result = subCategoryList.stream().map(this::convertToServiceSubCatTypeDto).collect(Collectors.toList());
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
	
	}*/
	@PostMapping("/getSubCategoryById/{categoryId}/{lang}")
	public ResponseEntity<List<ServiceSubCategoryTypeDTO>> getSubCategoryById(@PathVariable Long categoryId,@PathVariable String lang) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getActiveSubCategoryListByCategoryId(categoryId, lang));
	}
	
    /**
     * to get service type by subcategory id
     * @param subCategoryId
     * @return
     */	
	
	/*@PostMapping("/getServiceTypeById/{subCategoryId}")
	public ResponseEntity<Map<String, List<ServiceTypeDTO>>> getServiceTypeId(@PathVariable Long subCategoryId) {
		List<ServiceType> serviceTypeList = bookingService.getActiveServiceTypeByServiceId(subCategoryId);
		List<ServiceTypeDTO> result = null;
		Map<String, List<ServiceTypeDTO>> response = new HashMap<String, List<ServiceTypeDTO>>();
		try {
			if (serviceTypeList != null) {
				result = serviceTypeList.stream().map(this::convertToServiceTypeDto).collect(Collectors.toList());
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
	
	}*/
	@PostMapping("/getServiceTypeById/{subCategoryId}/{lang}")
	public ResponseEntity<List<ServiceTypeDTO>> getServiceTypeId(@PathVariable Long subCategoryId,@PathVariable String lang) {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getActiveServiceTypeByServiceId(subCategoryId,lang));
	}
	@GetMapping("/getServiceDetailsByCategoryId/{categoryId}/{lang}")
	public ResponseEntity<Map<String, ServiceResponse>> getServiceDetailsByCategoryId(@PathVariable Long categoryId,@PathVariable String lang) {
		ServiceResponse result = bookingService.getServiceDetailsByCategoryId(categoryId,lang);
		Map<String, ServiceResponse> response = new HashMap<String,ServiceResponse>();
		try {
			if (result != null) {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}
	@GetMapping("/getAllMeasurements")
	public ResponseEntity<Map<String, List<UnitMeasurementTypeDTO>>> getAllMeasurment(){
		List<UnitMeasurementType> measurementList=bookingService.getActiveMeasurementList();
		List<UnitMeasurementTypeDTO> result=null;
		Map<String, List<UnitMeasurementTypeDTO>> response = new HashMap<String, List<UnitMeasurementTypeDTO>>();
		try {
			if (measurementList != null) {
				result = measurementList.stream().map(this::convertToUomDto).collect(Collectors.toList());
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}
	
	/*
	 * @PostMapping("/getPriceById/{id}") public ResponseEntity<?>
	 * getPriceList(@PathVariable Integer id){ List<Price> priceList=null; try {
	 * priceList=bookingService.getActivePriceListById(id); return
	 * ResponseEntity.status(HttpStatus.OK).body(priceList); } catch (Exception ex)
	 * { ex.printStackTrace(); logger.error(ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(priceList); } }
	 * 
	 * @GetMapping("/getExecutorContactList") public ResponseEntity<?>
	 * getServiceExecutorContactList(){ List<ServiceExecutorContactType>
	 * executorContactList=null; try {
	 * executorContactList=bookingService.getExecutorContactList(); return
	 * ResponseEntity.status(HttpStatus.OK).body(executorContactList); } catch
	 * (Exception ex) { ex.printStackTrace(); logger.error(ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(executorContactList); } }
	 * 
	 * @GetMapping("/getOrderList") public ResponseEntity<?>getOrderList(){
	 * List<Order> orderList=null; try {
	 * orderList=bookingService.getACtiveOrderList(); return
	 * ResponseEntity.status(HttpStatus.OK).body(orderList); } catch (Exception ex)
	 * { ex.printStackTrace(); logger.error(ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderList); } }
	 * 
	 * @PostMapping("/getServiceStatus/{orderId}") public ResponseEntity<?>
	 * getServiceStatusUser(@PathVariable Long orderId){ ServiceStatusUser
	 * serviceStatusUser=null; try {
	 * serviceStatusUser=bookingService.getStatusUserById(orderId); return
	 * ResponseEntity.status(HttpStatus.OK).body(serviceStatusUser); } catch
	 * (Exception ex) { ex.printStackTrace(); logger.error(ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(serviceStatusUser); } }
	 * 
	 * @PostMapping("/getOrderStatusType/{Id}") public ResponseEntity<?>
	 * getOrderStatusType(@PathVariable Integer Id){ OrderStatusType
	 * orderStatusType=null; try {
	 * orderStatusType=bookingService.getOrderStatusTypeById(Id); return
	 * ResponseEntity.status(HttpStatus.OK).body(orderStatusType); } catch
	 * (Exception ex) { ex.printStackTrace(); logger.error(ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderStatusType); } }
	 * 
	 * @PostMapping("/getOrderDetails/{orderId}") public ResponseEntity<?>
	 * getOrderDetails(@PathVariable Long orderId){ OrderDetails orderDetails=null;
	 * try { orderDetails=bookingService.getOrderDetailsByOrderId(orderId); return
	 * ResponseEntity.status(HttpStatus.OK).body(orderDetails); } catch (Exception
	 * ex) { ex.printStackTrace(); logger.error(ex.getMessage()); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderDetails); } }
	 * 
	 * @GetMapping("/getAllServiceDetails") public ResponseEntity<?>
	 * getAllServiceDetails(){ List<ServiceDetails> serviceDetailsList = null; try {
	 * serviceDetailsList = bookingService.getActiveServiceList(); return
	 * ResponseEntity.status(HttpStatus.OK).body(serviceDetailsList);
	 * }catch(Exception ex) { ex.printStackTrace(); logger.error(ex.getMessage());
	 * return ResponseEntity.status(HttpStatus.NO_CONTENT).body(serviceDetailsList);
	 * } }
	 */
	
	/**
	 * to get the service and subcategories details by passing categoryid
	 * @param categoryId
	 * @return
	 */
	
	/*@GetMapping("/getServiceDetailsByCategoryId/{categoryId}")
	public ResponseEntity<Map<String, ServiceResponse>> getServiceDetailsByCategoryId(@PathVariable Long categoryId) {
		ServiceResponse result = bookingService.getServiceDetailsByCategoryIdId(categoryId);
		Map<String, ServiceResponse> response = new HashMap<String,ServiceResponse>();
		try {
			if (result != null) {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}
	
	}*/
	/*@GetMapping("/getServiceDetailsByCategoryId/hi/{categoryId}")
	public ResponseEntity<Map<String, ServiceResponse>> getServiceDetailsByCategoryIdHi(@PathVariable Long categoryId) throws BookingException {
		ServiceResponse result = bookingService.getServiceDetailsInHindiByCategoryIdId(categoryId);
		Map<String, ServiceResponse> response = new HashMap<String,ServiceResponse>();
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.OK).body(response);
	}*/
	
	/*
	 * @PostMapping("/getServiceDetailsByCategoryId/{categoryId}") public
	 * ResponseEntity<Map<String, ServiceResponse>>
	 * getServiceDetailsByCategoryIdId(@PathVariable Integer categoryId) {
	 * ServiceResponse result =
	 * bookingService.getServiceDetailsByCategoryIdId(categoryId); Map<String,
	 * ServiceResponse> response = new HashMap<String,ServiceResponse>(); try { if
	 * (result != null) { response.put("data", result); return
	 * ResponseEntity.status(HttpStatus.OK).body(response); } else {
	 * response.put("data", result); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(response); } } catch
	 * (Exception ex) { ex.printStackTrace(); logger.error(ex.getMessage());
	 * response.put("data", result); return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).body(response); }
	 * 
	 * }
	 */
	
	
	@RequestMapping(value = "/showImage", method = RequestMethod.GET)
	public byte[] displayPicture(@RequestParam("photoPath") String photoPath) {
		byte[] bytes = null;
		
		  try { 
			  String imagePath = photoPath; logger.debug("photo path: "+imagePath);
			  File file = new File(imagePath); 
			  bytes = Files.readAllBytes(file.toPath()); 
			  return Base64.getEncoder().encode(bytes);
		  
		  } catch (Exception ex) {
			  logger.error(ex);
			  return bytes;
		}
	}
	
	
	@RequestMapping(value = "/testShowImage", method = RequestMethod.GET)
	public byte[] displayTestPicture(@RequestParam("photoPath") String photoPath,HttpServletResponse response) {
		byte[] bytes = null;
		
		  try { 
			  String imagePath = photoPath; 
			  logger.debug("photo path: "+imagePath);
			  File file = new File(imagePath); 
			  bytes = Files.readAllBytes(file.toPath());
			  response.setHeader("Content-Type", "image/svg+xml/mp4");
			  response.setHeader("Content-Length", String.valueOf(file.length()));
			  response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
			  Files.copy(file.toPath(), response.getOutputStream());
			  return Base64.getEncoder().encode(bytes);
		  
		  } catch (Exception ex) {
			  logger.error(ex);
			  return bytes;
		}
	}
	
	/**
	 * save service executor details
	 * @param serviceType
	 * @return
	 */
	@ApiOperation(value = "serviceDetails", notes = "Save Sefrvice Executor details")
	@PostMapping(value = "/saveServiceExecutor",produces = "application/json")
	public ResponseEntity<Map<String, ServiceDetailsDTO>> getServiceTypeId(@RequestBody ServiceDetailsDTO serviceDetails) {
		ServiceDetailsDTO result = null;
		Map<String, ServiceDetailsDTO> response = new HashMap<String,ServiceDetailsDTO>();
		try {
			ServiceDetails sd  = convertToServiceDetailsEntity(serviceDetails);
			result = bookingService.saveExecutorDetails(serviceDetails,sd);
			
				if(result != null){
					response.put("data", result);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				  } 
				else { 
					response.put("data", result); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
					}
				 
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}
	
	@ApiOperation(value = "file", notes = "Save Sefrvice Executor details with files")
	@PostMapping(value = "/upload",produces = "application/json")
	public ResponseEntity<Map<String, String>> uploaFile(@RequestParam(value="file",required = false) MultipartFile file,
			@RequestParam(value="type") String type) {
		String result = "";
		Map<String, String> response = new HashMap<String,String>();
		try {
			
				
				result = bookingService.uploadDocument(file);
			
				if(!result.equals("")){
					response.put("data", result);
					return ResponseEntity.status(HttpStatus.OK).body(response);
				  } 
				else { 
					response.put("data", result); 
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
					}
				 
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", result);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}
	
	/**
	 * get service  details
	 * @param serviceType and zipcode
	 * @return
	 * @throws BookingException 
	 */
	@ApiOperation(value = "serviceDetails", notes = "Get List of Services by zip and servoce type")
	@PostMapping(value = "/getService",produces = "application/json")
	public ResponseEntity<Map<String, SearchResponse>> getService(@RequestBody ServiceRequest serviceRequest) throws BookingException {
		SearchResponse result = null;
		Map<String, SearchResponse> response = new HashMap<String,SearchResponse>();
			result = bookingService.getServiceDetails(serviceRequest);
					response.put("data", result);
					return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = "Get All service providers available with in 5 km radious from order owners location ", notes = "get service providers")
	@GetMapping(value = "/getServiceProviders",produces = "application/json")
	public ResponseEntity<List<ServiceDetailsDTO>> getServiceDetails(@RequestParam String farmerLatitude,@RequestParam String farmerLongitude, @RequestParam String farmerZipCode, @RequestParam String service) throws Exception {
            List<ServiceDetailsDTO> providerList = bookingService.getAvailableServiceNearToFarmer(farmerLatitude, farmerLongitude, farmerZipCode, service);
		return ResponseEntity.status(HttpStatus.OK).body(providerList);
	}
	
	@ApiOperation(value = "Get ServiceDetails ", notes = "get service details by service executor name")
	@GetMapping(value = "/getServiceDetailsByExecutorName",produces = "application/json")
	public ResponseEntity<List<MyServiceDetailsDTO>> getServiceDetailsByExecutorName(@RequestParam String userName) throws BookingException {
            List<MyServiceDetailsDTO> serviceList = bookingService.getServiceDetailsByUserName(userName);
		return ResponseEntity.status(HttpStatus.OK).body(serviceList);
	}
	
	@GetMapping("/getSelfServices")
	public ResponseEntity<ServiceResponse> getSelfServiceDetails(@RequestParam String userName,@RequestParam ServiceCategoryType id) throws BookingException{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllSelfServices(userName,id));
	}
	
		private ServiceDetails convertToServiceDetailsEntity(ServiceDetailsDTO serviceDetailsDTO) {
		ServiceDetails serviceDetails = modelMapper.map(serviceDetailsDTO, ServiceDetails.class);
	    return serviceDetails;
	}
		
		
	private ServiceTypeDTO convertToServiceTypeDto(ServiceType serviceType) {
		ServiceTypeDTO serviceTypeDTO = modelMapper.map(serviceType, ServiceTypeDTO.class);
	    return serviceTypeDTO;
	}
	

	private ServiceSubCategoryTypeDTO convertToServiceSubCatTypeDto(ServiceSubCategoryType serviceSubCatType) {
		ServiceSubCategoryTypeDTO serviceTypeDTO = modelMapper.map(serviceSubCatType, ServiceSubCategoryTypeDTO.class);
	    return serviceTypeDTO;
	}
	
	private ServiceCategoryTypeDTO convertToServiceCategoryDto(ServiceCategoryType serviceCatType) {
		ServiceCategoryTypeDTO serviceCategoryTypeDTO = modelMapper.map(serviceCatType, ServiceCategoryTypeDTO.class);
	    return serviceCategoryTypeDTO;

	}
	private ServiceCategoryTypeDTOHi convertToServiceCategoryDtoHi(ServiceCategoryType serviceCatType) {
		ServiceCategoryTypeDTOHi serviceCategoryTypeDTOHi = modelMapper.map(serviceCatType, ServiceCategoryTypeDTOHi.class);
	    return serviceCategoryTypeDTOHi;

	}
	private UnitMeasurementTypeDTO convertToUomDto(UnitMeasurementType unitMeasurementType) {
		UnitMeasurementTypeDTO unitMeasurementTypeDTO = modelMapper.map(unitMeasurementType, UnitMeasurementTypeDTO.class);
	    return unitMeasurementTypeDTO;

	}
	
	@GetMapping("/get/serviceExecutors")
	public ResponseEntity<List<ServiceExecutorListDTO>> getServiceExecutor(@RequestParam String name) throws BookingException{
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getServiceExecutors(name));
	}
	
	@PutMapping("/update/executor/serviceDetails")
	public ResponseEntity<ServiceDetailsDTO> modifyServiceExecutorServiceDetails(@RequestBody ServiceDetailsDTO serviceDetailsDto) throws BookingException{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.updateServiceExecutorDetails(serviceDetailsDto));
	}
	
	@ApiOperation(value = "Get All service Details of Service Executor", notes = "get service details")
	@GetMapping(value = "/getServiceExecutorMyServices",produces = "application/json")
	public ResponseEntity<List<MyServiceDetailsDTO>> getServiceExeServiceDetails(@RequestParam String phone) throws Exception {
            List<MyServiceDetailsDTO> serviceDetailsList = bookingService.getSEServiceDetails(phone);
		return ResponseEntity.status(HttpStatus.OK).body(serviceDetailsList);
	}
	
	@ApiOperation(value = "Get All self service Details of Service Provider", notes = "get service details")
	@GetMapping(value = "/getSelfMyServices",produces = "application/json")
	public ResponseEntity<List<MyServiceDetailsDTO>> getSelfMyServiceDetails(@RequestParam String userName) throws Exception {
            List<MyServiceDetailsDTO> serviceDetailsList = bookingService.getSelfServiceDetails(userName);
		return ResponseEntity.status(HttpStatus.OK).body(serviceDetailsList);
	}
	
	@ApiOperation(value = "Add Service Category Master Screen of Admin Panel")
	@PostMapping("/save/service/category")
	public ResponseEntity<ServiceCategoryType> saveServiceCategory(@RequestBody AdminMetadata metaData){
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.saveServiceCategoryType(metaData));
	}
	
	@ApiOperation(value = "Add Service Executor Contact Master Screen of Admin Panel")
	@PostMapping("/save/serviceExecutor/contact")
	public ResponseEntity<ServiceExecutorContactType> saveServiceExecutorContact(@RequestBody AdminMetadata metaData){
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.saveServiceExecutorContactType(metaData));
	}
	
	@ApiOperation(value = "Add UOM Master Screen of Admin Panel")
	@PostMapping("/save/unit")
	public ResponseEntity<UnitMeasurementType> saveUnitOfMeasuremtType(@RequestBody AdminMetadata metaData){
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.saveUnitOfMeasurementType(metaData));
	}
	
	@ApiOperation(value = "Add Service Heading Master Screen of Admin Panel")
	@PostMapping("/save/service/subcat")
	public ResponseEntity<ServiceSubCategoryTypeDTO> saveServiceSubCatType(@RequestBody ServiceSubCatMetaData serviceSubCatMeta){
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.saveServiceSubCatType(serviceSubCatMeta));
	}
	
	@ApiOperation(value = "Add Service Master Screen of Admin Panel")
	@PostMapping("/save/servicetype")
	public ResponseEntity<ServiceType> saveServiceType(@RequestBody ServiceTypeMetaData serviceTypeMeta){
		
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.saveServiceType(serviceTypeMeta));
	}
	
	@ApiOperation(value = "Get Service Heading Master Screen of Admin Panel")
	@GetMapping("/fetchAllSubCategory")
	public ResponseEntity<List<ServiceSubCategoryTypeDTO>> getAllSubCategory(){
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.fetchAllSubCategory());
	}
	
	@ApiOperation(value = "Get Service Master Screen of Admin Panel")
	@GetMapping("/fetchAllServicetype")
	public ResponseEntity<List<ServiceTypeMetaData>> getAllServicetype(){
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.fetchAllServiceType());
	}
	
	@ApiOperation(value = "Get Service Executor Contact Master Screen of Admin Panel")
	@GetMapping("/fetchAllServiceExecutorContactType")
	public ResponseEntity<List<ServiceExecutorContactType>> fetchAllServiceExecutorContactType(){
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.fetchAllServiceExecutorContactType());
	}
	
	@ApiOperation(value = "Get UOM Master Screen of Admin Panel")
	@GetMapping("/fetchAllUnitOfMeasurementType")
	public ResponseEntity<List<UnitMeasurementType>> fetchAllUnitOfMeasurementType(){
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.fetchAllUnitOfMeasurementType());
	}
	
	@ApiOperation(value = "view order status Screen for farmer of Admin Panel")
	@GetMapping("/getFarmerOrderStatus")
	public ResponseEntity<List<AdminPanelOrderStatus>> fetchFarmerOrderStatus(){
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getFarmerOrderStatus());
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteServiceDetailsId(@RequestParam Long id) throws BookingException, UnirestException{
		String deleteServiceDetails = bookingService.deleteServiceDetailsById(id);
		return ResponseEntity.status(HttpStatus.OK).body(deleteServiceDetails);
	}
}
