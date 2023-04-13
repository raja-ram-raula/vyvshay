package com.sigmify.vb.booking.endpoints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sigmify.vb.booking.constants.BookingConstant;
import com.sigmify.vb.booking.dto.OrderDTO;
import com.sigmify.vb.booking.dto.OrderDetailsDTO;
import com.sigmify.vb.booking.dto.OrderRequestDTO;
import com.sigmify.vb.booking.dto.OrderResponseDTO;
import com.sigmify.vb.booking.dto.OrderUpdateDto;
import com.sigmify.vb.booking.dto.PaymentDto;
import com.sigmify.vb.booking.dto.ServiceDetailsDTO;
import com.sigmify.vb.booking.dto.TrackOrderDTO;
import com.sigmify.vb.booking.dto.UpdateLatLongDTO;
import com.sigmify.vb.booking.entity.NotificationDetails;
import com.sigmify.vb.booking.entity.Order;
import com.sigmify.vb.booking.exception.BookingException;
import com.sigmify.vb.booking.firebase.NotificationMessage;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;
import com.sigmify.vb.booking.firebase.model.PushNotificationResponse;
import com.sigmify.vb.booking.firebase.service.PushNotificationService;
import com.sigmify.vb.booking.repository.OrderRepository;
import com.sigmify.vb.booking.service.BookingService;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

	private final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private OrderRepository orderRepo;

	RestTemplate restTemplate;
	private static final String FB_TOKEN_URL=BookingConstant.ADMIN_APPLICATION_BASEURL + "user/get/token";

    @Autowired
	private PushNotificationService pushNotificationService;

    public OrderController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }
    public OrderController() {
       
    }

	@PostMapping("/notification/token")
	public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) throws BookingException{
		PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
		OrderDTO orderDto = bookingService.findOrdersByOrderId(request.getOrderId());
		NotificationMessage notificationRequest = bookingService.makeNotificationMessage(request, orderDto);
		pushNotificationRequest.setMessage(notificationRequest.getMessage());
		pushNotificationRequest.setTitle(notificationRequest.getTitle());
		pushNotificationRequest.setToken(request.getToken());
		pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}

	@GetMapping("/get_psr_count")
	public ResponseEntity getPendingServiceRequestCount(@RequestParam String userId, @RequestParam long serviceId, @RequestParam String minmax) throws ParseException {
		int count =0;
	    Date min=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 00:00:00.000");
	    Date max=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 23:59:59.000");
		count = bookingService.getPSRCount(userId, serviceId, min, max);
		return new ResponseEntity(count, HttpStatus.OK);
	}
	@GetMapping("/ping")
	public ResponseEntity<String> getTestApi() {
		return new ResponseEntity<String>("Test Api running", HttpStatus.OK);
	}

	@ApiOperation(value = "createOrder", notes = "Create a Order")
	@PostMapping(value = "/createOrder", produces = "application/json")
	public ResponseEntity<Map<String, OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		Order result = null;
		OrderResponseDTO orderResObj = null;
		Map<String, OrderResponseDTO> response = new HashMap<String, OrderResponseDTO>();
		try {
			result = bookingService.createOrder(orderRequestDTO);
		

			if (result != null) {
				orderResObj = new OrderResponseDTO();
				orderResObj.setMessage("Order Created");
				orderResObj.setOrderId(result.getOrderId());
				orderResObj.setOrderStatus(result.getOrderStatus());
				response.put("data", orderResObj);
				//push Notification
				String deviceTokens = null;
				//=we need only device token==//
				List<ServiceDetailsDTO> providerList = bookingService.getAvailableServiceNearToFarmer(result.getLattitude(), result.getLongitude(), result.getPinCode(), result.getServiceId().getName());
				//---------------near by 3KM------------//
				List<String> spName = providerList.stream().map(ServiceDetailsDTO::getUserName).collect(Collectors.toList());
				//List<String> deviceToken = bookingService.getServiceExecutors(spName);
				
				
				if(!spName.isEmpty())
				{
				for(String spNames:spName)
				{
					String minmax = orderRequestDTO.getStartTime();
				    Date min=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 00:00:00.000");
				    Date max=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 23:59:59.000");
					int count = bookingService.getPSRCount(spNames, result.getServiceId().getId(), min, max);
					if(count<5)
					{
						deviceTokens = new RestTemplate().getForObject(FB_TOKEN_URL+"/"+spNames, String.class);
				
					if(deviceTokens!=null)
					{
						NotificationDetails notificationRequest = new NotificationDetails();
						notificationRequest.setNotificationTitle("Service Allocation");
						notificationRequest.setNotificationMessage(NotificationMessage.SERVICE_ALLOCATION.replace("{OID}", result.getOrderId()));
						notificationRequest.setNotificationStatus(0);
						notificationRequest.setSenderId(result.getCreatedBy());
						notificationRequest.setReceiverId(spNames);
						NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);	
						
				PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
				pushNotificationRequest.setTitle("Service Allocation");
				pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.SERVICE_ALLOCATION.replace("{OID}", result.getOrderId()));
				pushNotificationRequest.setToken(deviceTokens);
				pushNotificationRequest.setNotificationId(bean.getNotificationId());
				pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
				}
					}
				}
				}
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", orderResObj);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", orderResObj);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}
	
	
	@ApiOperation(value = "recreateOrder", notes = "Re Create a Order")
	@PostMapping(value = "/_recreateOrder", produces = "application/json")
	public ResponseEntity<Map<String, Order>> reCreateOrder(@RequestBody Order order) {
		Order result = null;
		OrderResponseDTO orderResObj = null;
		Map<String, Order> response = new HashMap<String, Order>();
		try {
			result = bookingService.reCreateOrder(order);
		

			if (result != null) {
				orderResObj = new OrderResponseDTO();
				orderResObj.setMessage("Order Created");
				orderResObj.setOrderId(result.getOrderId());
				orderResObj.setOrderStatus(result.getOrderStatus());
				response.put("data", order);
				//push Notification
				String deviceTokens = null;
				//=we need only device token==//
				List<ServiceDetailsDTO> providerList = bookingService.getAvailableServiceNearToFarmer(result.getLattitude(), result.getLongitude(), result.getPinCode(), result.getServiceId().getName());
				//---------------near by 3KM------------//
				List<String> spName = providerList.stream().map(ServiceDetailsDTO::getUserName).collect(Collectors.toList());
				//List<String> deviceToken = bookingService.getServiceExecutors(spName);
				
				
				if(!spName.isEmpty())
				{
				for(String spNames:spName)
				{
					String minmax = result.getCreatedAt().toString();
				    Date min=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 00:00:00.000");
				    Date max=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 23:59:59.000");
					int count = bookingService.getPSRCount(spNames, result.getServiceId().getId(), min, max);
					if(count<5)
					{
						deviceTokens = new RestTemplate().getForObject(FB_TOKEN_URL+"/"+spNames, String.class);
				
					if(deviceTokens!=null)
					{
						NotificationDetails notificationRequest = new NotificationDetails();
						notificationRequest.setNotificationTitle("Service Allocation");
						notificationRequest.setNotificationMessage(NotificationMessage.SERVICE_ALLOCATION.replace("{OID}", result.getOrderId()));
						notificationRequest.setNotificationStatus(0);
						notificationRequest.setSenderId(result.getCreatedBy());
						notificationRequest.setReceiverId(spNames);
						NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);	
						
				PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
				pushNotificationRequest.setTitle("Service Allocation");
				pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.SERVICE_ALLOCATION.replace("{OID}", result.getOrderId()));
				pushNotificationRequest.setToken(deviceTokens);
				pushNotificationRequest.setNotificationId(bean.getNotificationId());
				pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
				}
					}
				}
				}
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", order);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", order);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}

	@ApiOperation(value = "updateOrder", notes = "Create a Order")
	@PutMapping(value = "/updateOrder", produces = "application/json")
	public ResponseEntity<Map<String, OrderResponseDTO>> updateOrder(@RequestBody OrderUpdateDto orderUpdateDTO) {
		Order result = null;
		OrderResponseDTO orderResObj = null;
		Map<String, OrderResponseDTO> response = new HashMap<String, OrderResponseDTO>();
		try {
			result = bookingService.updateOrder(orderUpdateDTO);

			if (result != null) {
				orderResObj = new OrderResponseDTO();
				orderResObj.setMessage("Order Updated");
				orderResObj.setOrderId(result.getOrderId());
				orderResObj.setOrderStatus(result.getOrderStatus());
				response.put("data", orderResObj);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", orderResObj);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			response.put("data", orderResObj);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		}

	}

	@ApiOperation(value = "Get orders for dashbpard", notes = "Get orders by SP username and status")
	@GetMapping(value = "/getDashboardOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getDashboardOrders(@RequestParam String userId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findSEOrders(userId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Get Current orders for dashbpard", notes = "Get current date orders by userid and status")
	@GetMapping(value = "/getCurrentDashboardOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getCurrentDashboardOrders(@RequestParam String userId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findCurrentSEOrders(userId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Get Upcoming orders for dashbpard", notes = "Get UpComing/after current date orders by userid and status")
	@GetMapping(value = "/getUpcomingDashboardOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getUpcomingDashboardOrders(@RequestParam String userId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findUpcomingSEOrders(userId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = "Get Complete orders for dashbpard", notes = "Get Complete orders by userid and status")
	@GetMapping(value = "/getCompleteDashboardOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getCompleteDashboardOrders(@RequestParam String userId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findCompleteSEOrders(userId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "cancel a order", notes = "cancel a order from farmer side")
	@PutMapping(value = "/cancel_order", produces = "application/json")
	public ResponseEntity<Map<String, OrderResponseDTO>> acceptRejectSEOrders(@RequestParam String orderId, @RequestParam String userId) throws BookingException{
		OrderResponseDTO result = null;
		Map<String, OrderResponseDTO> response = new HashMap<String, OrderResponseDTO>();
		//try {
			result = bookingService.updateOrders(orderId, userId);

			if (result != null) {
				response.put("data", result);
				//device token of user
				String deviceTokens=null;
				List<String> providerList=bookingService.getUserIdByOrder(result.getOrderId());
					for(String spNames:providerList) {
				//List<String> createdBy = bookingService.getUserIdByOrder(orderId);
				String URL=FB_TOKEN_URL+"/"+spNames;
				
				  deviceTokens = new RestTemplate().getForObject(URL, String.class);
				if(deviceTokens != null)
				{
					NotificationDetails notificationRequest = new NotificationDetails();
					notificationRequest.setNotificationTitle("Cancel Service Allocation");
					notificationRequest.setNotificationMessage(NotificationMessage.CANCEL_SERVICE_ALLOCATION.replace("{OID}", orderId));
					notificationRequest.setNotificationStatus(0);
					notificationRequest.setSenderId(userId);
					notificationRequest.setReceiverId(spNames);
					NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);	
					
					PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
					pushNotificationRequest.setTitle("Cancel Service Allocation");
					pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.CANCEL_SERVICE_ALLOCATION.replace("{OID}", orderId));
					pushNotificationRequest.setToken(deviceTokens);
					pushNotificationRequest.setNotificationId(bean.getNotificationId());
					pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
				}
				}
					return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				response.put("data", result);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
			}

		//} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.error(ex.getMessage());
//			response.put("data", result);
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//		}

	}
	
	/*@ApiOperation(value = "Accept or Reject a Order", notes = "Update a order")
	@PutMapping(value = "/acceptRejectSEOrders", produces = "application/json")
	public ResponseEntity<Map<String, OrderResponseDTO>> acceptRejectSEOrders(@RequestParam String orderId,
			@RequestParam String status, @RequestParam String userId) {
		OrderResponseDTO result = null;
		Map<String, OrderResponseDTO> response = new HashMap<String, OrderResponseDTO>();
		try {
			result = bookingService.updateOrders(orderId, status, userId);
			
			if (result != null) {
				response.put("data", result);
				//device token of user
				String createdBy = bookingService.getUserIdByOrder(orderId);
				 RestTemplate restTemplate = new RestTemplate();
				 String deviceToken = restTemplate.getForObject(BookingConstant.ADMIN_APPLICATION_BASEURL+"get/token/"+createdBy, String.class);
				if(!deviceToken.isEmpty())
				{
					NotificationDetails notificationRequest = new NotificationDetails();
					notificationRequest.setNotificationTitle("Update Service Allocation");
					notificationRequest.setNotificationMessage(NotificationMessage.UPDATE_SERVICE_ALLOCATION.replace("{OID}", result.getOrderId()));
					notificationRequest.setNotificationStatus(0);
					notificationRequest.setSenderId(userId);
					notificationRequest.setReceiverId(createdBy);
					NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);	
					
					
					PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
					pushNotificationRequest.setTitle("Update Service Allocation");
					pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.UPDATE_SERVICE_ALLOCATION.replace("{OID}", result.getOrderId()));
					pushNotificationRequest.setToken(deviceToken);
					pushNotificationRequest.setNotificationId(bean.getNotificationId());
					pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
				}
				 
				
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

	@ApiOperation(value = "Get orders by User Farmer username", notes = "Get orders by userid")
	@GetMapping(value = "/getAllOrderByUserId", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getOrderByUserId(@RequestParam String userId)
			throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findOrdersByUserId(userId);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Find orders by orderid", notes = "Find orders by orderid")
	@GetMapping(value = "/getOrder", produces = "application/json")
	public ResponseEntity<Map<String, OrderDTO>> getOrder(@RequestParam String orderId) throws BookingException {
		OrderDTO result = null;
		Map<String, OrderDTO> response = new HashMap<String, OrderDTO>();
		result = bookingService.findOrdersByOrderId(orderId);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Find orders by orderid and status", notes = "Find orders by orderid and status")
	@GetMapping(value = "/ordersByOrderIdStatus", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> ordersByOrderIdStatus(@RequestParam String orderId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.ordersByOrderIdStatus(orderId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Get orders By User Farmer username and status", notes = "Get orders by userid and status")
	@GetMapping(value = "/getAllOrderByUserIdandStatus", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getAllOrderByUserIdandStatus(@RequestParam String userId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findOrdersByUserIdandStatus(userId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Get completed orders for dashbpard", notes = "Get completed orders by userid and payment status")
	@GetMapping(value = "/getCompletedPaymentOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> getComletedPaymentDashboardOrders(@RequestParam String orderId,
			@RequestParam String status) throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findCompletedSEOrders(orderId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Get track all orders", notes = "Track all orders by username")
	@GetMapping(value = "/getTrackAllOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<TrackOrderDTO>>> gerTrackOrders(@RequestParam String userName)
			throws BookingException {
		List<TrackOrderDTO> result = null;
		Map<String, List<TrackOrderDTO>> response = new HashMap<String, List<TrackOrderDTO>>();
		result = bookingService.trackOrderBySeUserId(userName);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Updation lattitude and longitude", notes = "For updating current lttitude and longitude")
	@PutMapping("/update/latlong")
	public ResponseEntity<String> modifyLatLongOfServiceExecutor(@RequestBody UpdateLatLongDTO orderDto)
			throws BookingException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.updateLatLongOfServiceExecutor(orderDto));

	}

	@ApiOperation(value = "Find last three orders by orderid and status", notes = "Find orders by orderid and status")
	@GetMapping(value = "/ordersByUserNameOfLastThreeOrders", produces = "application/json")
	public ResponseEntity<Map<String, List<OrderDTO>>> ordersByUserId(@RequestParam String userName)
			throws BookingException {
		List<OrderDTO> result = null;
		Map<String, List<OrderDTO>> response = new HashMap<String, List<OrderDTO>>();
		result = bookingService.findLastThreeOrdersByUserName(userName);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/serviceStartSendOtp")
	public ResponseEntity<String> serviceStartSendOtp(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {
		String result=bookingService.serviceStartSenddOtp(phone, orderId);
		Order order=orderRepo.findByOrderId(orderId);
		System.out.println(orderId);
		order.setIsRunning(true);
		orderRepo.save(order);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/verifyServiceStartOtp")
	public ResponseEntity<String> servicestartVerifyOtp(@RequestParam String phone, @RequestParam String otp)
			throws BookingException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceStartVerifyOtp(phone, otp));
	}
	
	@GetMapping("/serviceCompleteSendOtp")
	public ResponseEntity<String> serviceCompleteSendOtp(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceCompleteSenddOtp(phone, orderId));
	}

	@GetMapping("/verifyServiceCompleteOtp")
	public ResponseEntity<String> serviceCompleteVerifyOtp(@RequestParam String phone, @RequestParam String otp)
			throws BookingException, UnirestException {
		// String result=userService.serviceExeVerifyOtp(phone, otp);
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceCompleteVerifyOtp(phone, otp));
	}
	
	@GetMapping("/serviceOwnerNewServiceReqSms")
	public ResponseEntity<String> serviceOwnerNewServiceReqSms(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceOwnerNewServiceReqMsg(phone, orderId));
	}
	
	@GetMapping("/serviceOwnerUpdateServiceReqSms")
	public ResponseEntity<String> serviceOwnerUpdateServiceReqSms(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceOwnerUpdateServiceReqMsg(phone, orderId));
	}
	
	@GetMapping("/serviceOwnerCancelServiceReqSms")
	public ResponseEntity<String> serviceOwnerCancelServiceReqSms(@RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceOwnerCancelServiceReqMsg(orderId));
	}
	
	@GetMapping("/serviceExeNewServiceReqSms")
	public ResponseEntity<String> serviceExeNewServiceReqSms(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceExeNewServiceReqMsg(phone, orderId));
	}
	
	@GetMapping("/serviceExeUpdateServiceReqSms")
	public ResponseEntity<String> serviceExeUpdateServiceReqSms(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceExeUpdateServiceReqMsg(phone, orderId));
	}
	
	@GetMapping("/serviceExeCancelServiceReqSms")
	public ResponseEntity<String> serviceExeCancelServiceReqSms(@RequestParam String phone, @RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceExeCancelServiceReqMsg(phone, orderId));
	}
	
	@GetMapping("/serviceExeEnrollmentSms")
	public ResponseEntity<String> serviceExeEnrollmentSms(@RequestParam String phone,@RequestParam String serviceName)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.seEnrollmentMsg(phone, serviceName));
	}
	
	@GetMapping("/getAllCompleteOrders")
	public ResponseEntity<List<PaymentDto>> getAllCompleteOrders(@RequestParam String userName) throws BookingException{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllCompleteOrders(userName));
	}
	
	@GetMapping("/getAllCompleteOrdersBySelf")
	public ResponseEntity<List<PaymentDto>> getAllCompleteOrdersByType(@RequestParam String userName) throws BookingException{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllCompleteOrdersBySelf(userName));
	}
	
	@GetMapping("/getAllCompleteOrdersByExecutor")
	public ResponseEntity<List<PaymentDto>> getAllCompleteOrdersByExecutor(@RequestParam String phone) throws BookingException{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllCompleteOrdersByExecutor(phone));
	}
	
	@GetMapping("/fetchAllProviderList")
	public ResponseEntity<List<OrderDetailsDTO>> fetchAllProviderList(@RequestParam String orderId ) throws BookingException{
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.listOfProviderList(orderId));
	}
	
	public void sentNotification(String deviceToken, String spNames)
	{
		NotificationDetails notificationRequest = new NotificationDetails();
		notificationRequest.setNotificationTitle("Service Allocation");
		notificationRequest.setNotificationMessage(NotificationMessage.STOPPED_SERVICE_DUE_TO_PAYMENT);
		notificationRequest.setNotificationStatus(0);
		notificationRequest.setSenderId(spNames);
		notificationRequest.setReceiverId(spNames);
		NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);	
		
		PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
		pushNotificationRequest.setTitle("Service Allocation");
		pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.STOPPED_SERVICE_DUE_TO_PAYMENT);
		pushNotificationRequest.setToken(deviceToken);
		pushNotificationRequest.setNotificationId(bean.getNotificationId());
		pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
	}
	
	public void sentNotificationHourlyWithoutActionBy(String deviceToken, String spName, String userName, String orderId)
	{
		NotificationDetails notificationRequest = new NotificationDetails();
		notificationRequest.setNotificationTitle("Service Allocation");
		notificationRequest.setNotificationMessage(NotificationMessage.SERVICE_ALLOCATION.replace("{OID}", orderId));
		notificationRequest.setNotificationStatus(0);
		notificationRequest.setSenderId(userName);
		notificationRequest.setReceiverId(spName);
		NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);	
		
		PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
		pushNotificationRequest.setTitle("Service Allocation");
		pushNotificationRequest.setMessage(bean.getNotificationId()+"_"+NotificationMessage.SERVICE_ALLOCATION.replace("{OID}", orderId));
		pushNotificationRequest.setToken(deviceToken);
		pushNotificationRequest.setNotificationId(bean.getNotificationId());
		pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
	}
}
