package com.sigmify.vb.booking.endpoints;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sigmify.vb.booking.dto.PaymentDto;
import com.sigmify.vb.booking.dto.PaymentInvoiceDTO;
import com.sigmify.vb.booking.dto.PaymentRequest;
import com.sigmify.vb.booking.dto.ServiceProcessingFee;
import com.sigmify.vb.booking.entity.NotificationDetails;
import com.sigmify.vb.booking.entity.PaymentDetails;
import com.sigmify.vb.booking.entity.PaymentInvoice;
import com.sigmify.vb.booking.exception.BookingException;
import com.sigmify.vb.booking.firebase.NotificationMessage;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;
import com.sigmify.vb.booking.repository.PaymentRepository;
import com.sigmify.vb.booking.service.BookingService;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentController {

	private final Logger logger = Logger.getLogger(PaymentController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private PaymentRepository paymentRepo;

	RestTemplate restTemplate;
	private static final String FB_TOKEN_URL = BookingConstant.ADMIN_APPLICATION_BASEURL + "user/get/token/";
	private static final String NOTIFICATION_URL = BookingConstant.ADMIN_APPLICATION_BASEURL
			+ "user/notification/token";


	@GetMapping("/ping")
	public ResponseEntity<String> getTestApi() {
		return new ResponseEntity<String>("Test Api running", HttpStatus.OK);
	}

	/**
	 * 
	 * @param paymentDto
	 * @return
	 * @throws BookingException
	 */
	@ApiOperation(value = "Get Payment Details", notes = "Get Payment Details")
	@GetMapping(value = "/getPaymentDetails", produces = "application/json")
	public ResponseEntity<Map<String, PaymentDto>> getPaymentDetails(@RequestParam String orderId)
			throws BookingException {
		PaymentDto result = null;
		Map<String, PaymentDto> response = new HashMap<String, PaymentDto>();
		result = bookingService.getPaymentDetails(orderId);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * 
	 * @param paymentDto
	 * @return
	 */
	@ApiOperation(value = "Update Payment Details", notes = "Update Payment Details")
	@PutMapping(value = "/updatePaymentDetails", produces = "application/json")
	public ResponseEntity<Map<String, String>> updatePaymentDetails(@RequestBody PaymentRequest paymentDto) {
		String result = null;
		Map<String, String> response = new HashMap<String, String>();
		try {
			result = bookingService.updatePaymentDetails(paymentDto);
			String deviceTokens;
			if (result != null) {
				PaymentDetails pd = paymentRepo.getPaymentDetails(paymentDto.getOrderId());
				String userName = paymentDto.getUserId();
				String farmerName = pd.getCreatedBy();
				deviceTokens = new RestTemplate().getForObject(FB_TOKEN_URL + farmerName, String.class);
				if (!deviceTokens.isEmpty()) {
					if (paymentDto.getPaymentStatus().equals("PAYMENT_COMPLETED")) {
						NotificationDetails notificationRequest = new NotificationDetails();
						notificationRequest.setNotificationTitle("Payment Update Complete Service");
						notificationRequest.setNotificationMessage(NotificationMessage.UPDATE_PAYMENT_COMPLETE
								.replace("{OID}", paymentDto.getOrderId()).replace("{ONNE}", userName));/*" & notif_Flag=1, pmt_Flag="+pd.getPaymentAcceptReject());*/
						//notificationRequest.setNotificationStatus(0);
						notificationRequest.setSenderId(userName);
						notificationRequest.setReceiverId(farmerName);
						NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);

						PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
						pushNotificationRequest.setTitle("Payment Update Complete Service");
						pushNotificationRequest
								.setMessage(bean.getNotificationId() + "_" + NotificationMessage.UPDATE_PAYMENT_COMPLETE
										.replace("{OID}", paymentDto.getOrderId()).replace("{ONNE}", userName));
										/*+" & notif_Flag=1, pmt_Flag="+pd.getPaymentAcceptReject());*/
						pushNotificationRequest.setToken(deviceTokens);
						pushNotificationRequest.setNotificationId(bean.getNotificationId());
						new RestTemplate().postForObject(NOTIFICATION_URL, pushNotificationRequest, String.class);
					} else {
						NotificationDetails notificationRequest = new NotificationDetails();
						notificationRequest.setNotificationTitle("Payment Update Start Service");
						notificationRequest.setNotificationMessage(NotificationMessage.UPDATE_PAYMENT_START
								.replace("{OID}", paymentDto.getOrderId()).replace("{ONNE}", userName));
								/*+" & notif_Flag=1, pmt_Flag="+pd.getPaymentAcceptReject());*/
						//notificationRequest.setNotificationStatus(0);
						notificationRequest.setSenderId(userName);
						notificationRequest.setReceiverId(farmerName);
						NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);

						PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
						pushNotificationRequest.setTitle("Payment Update Start Service");
						pushNotificationRequest
								.setMessage(bean.getNotificationId() + "_" + NotificationMessage.UPDATE_PAYMENT_START
										.replace("{OID}", paymentDto.getOrderId()).replace("{ONNE}", userName));
										/*+" & notif_Flag=1, pmt_Flag="+pd.getPaymentAcceptReject());*/
						pushNotificationRequest.setToken(deviceTokens);
						pushNotificationRequest.setNotificationId(bean.getNotificationId());
						new RestTemplate().postForObject(NOTIFICATION_URL, pushNotificationRequest, String.class);
					}
				}
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

	@ApiOperation(value = "ReUpdate Payment Details", notes = "ReUpdate Payment Details")
	@PutMapping(value = "/reUpdatePaymentDetails", produces = "application/json")
	public ResponseEntity<Map<String, String>> reUpdatePaymentDetails(@RequestBody PaymentRequest paymentDto) {
		String result = null;
		Map<String, String> response = new HashMap<String, String>();
		try {
			result = bookingService.reUpdatePaymentDetails(paymentDto);
			String deviceTokens;
			if (result != null) {
				PaymentDetails pd = paymentRepo.getPaymentDetails(paymentDto.getOrderId());
				String userName = paymentDto.getUserId();
				String farmerName = pd.getCreatedBy();
				deviceTokens = new RestTemplate().getForObject(FB_TOKEN_URL + farmerName, String.class);
				if (!deviceTokens.isEmpty()) {
					NotificationDetails notificationRequest = new NotificationDetails();
					notificationRequest.setNotificationTitle("Payment Update Complete Service");
					notificationRequest.setNotificationMessage(NotificationMessage.UPDATE_PAYMENT_COMPLETE
							.replace("{OID}", paymentDto.getOrderId()).replace("{ONNE}", userName));
							/*+" & notif_Flag=1, pmt_Flag="+pd.getPaymentAcceptReject());*/
					notificationRequest.setNotificationStatus(0);
					notificationRequest.setSenderId(userName);
					notificationRequest.setReceiverId(farmerName);
					NotificationDetails bean = bookingService.saveNotificationDetails(notificationRequest);

					PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
					pushNotificationRequest.setTitle("Payment Update Complete Service");
					pushNotificationRequest
							.setMessage(bean.getNotificationId() + "_" + NotificationMessage.UPDATE_PAYMENT_COMPLETE
									.replace("{OID}", paymentDto.getOrderId()).replace("{ONNE}", userName));
									/*+" & notif_Flag=1, pmt_Flag="+pd.getPaymentAcceptReject());*/
					pushNotificationRequest.setToken(deviceTokens);
					pushNotificationRequest.setNotificationId(bean.getNotificationId());
					new RestTemplate().postForObject(NOTIFICATION_URL, pushNotificationRequest, String.class);
					// pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
				}
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

	@ApiOperation(value = "Get completed paymentDetails for dashbpard", notes = "Get paymentDetails by userid and payment status")
	@GetMapping(value = "/getCompletedPaymentDetails", produces = "application/json")
	public ResponseEntity<Map<String, List<PaymentDto>>> getComletedPaymentDetailsDashboardOrders(
			@RequestParam String userId, @RequestParam String status) throws BookingException {
		List<PaymentDto> result = null;
		Map<String, List<PaymentDto>> response = new HashMap<String, List<PaymentDto>>();
		result = bookingService.findCompletedPaymnetSEOrders(userId, status);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ApiOperation(value = "Payment update notification is going to farmer mobile number", notes = "Payment update notification")
	@GetMapping("/userPaymentUpdateNotification")
	public ResponseEntity<String> userPaymentUpdateNotification(@RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.userPaymentUpdateNotification(orderId));
	}

	@ApiOperation(value = "Payment update approove is going to Owner mobile number", notes = "Payment update Approved")
	@GetMapping("/serviceOwnerPaymentUpdateApprove")
	public ResponseEntity<String> serviceOwnerPaymentUpdateApprove(@RequestParam String orderId)
			throws BookingException, UnirestException {

		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceOwnerPaymentUpdateApprove(orderId));
	}

	@ApiOperation(value = "Payment update Rejected is going to Owner mobile number", notes = "Payment update rejected")
	@GetMapping(value = "/serviceOwnerPaymentUpdateReject")
	public ResponseEntity<String> serviceOwnerPaymentUpdateReject(@RequestParam String orderId)
			throws BookingException, UnirestException {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.serviceOwnerPaymentUpdateReject(orderId));
	}

	@GetMapping("/getPaymentInvoice")
	public ResponseEntity<List<PaymentDto>> getPaymentInvoice(@RequestParam String userName) throws BookingException {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getPaymentInvoice(userName));
	}

	@GetMapping("/getPaymentLog")
	public ResponseEntity<PaymentDto> getPaymentLog(@RequestParam String userName) throws BookingException {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.getPaymentLog(userName));
	}

	@PostMapping("/doPayment")
	public ResponseEntity<List<PaymentDto>> doPayment(@RequestParam List<String> listOrderNumber)
			throws BookingException {
		return ResponseEntity.status(HttpStatus.OK).body(bookingService.doPayment(listOrderNumber));
	}
	
	
	@ApiOperation(value = "Get Payment Invoice Details", notes = "Get Payment Invoice Details")
	@GetMapping(value = "/getPaymentInvoiceDetails", produces = "application/json")
	public ResponseEntity<Map<String, List<PaymentInvoice>>> getPaymentDetails(@RequestParam String username,@RequestParam String date)
			throws BookingException {
		List<PaymentInvoice> result = null;
		Map<String, List<PaymentInvoice>> response = new HashMap<String, List<PaymentInvoice>>();
		result = bookingService.getByUsernameDate(username,date);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@ApiOperation(value = "Get Payment Invoice Details", notes = "Get Payment Invoice Details")
	@GetMapping(value = "/getPaymentInvoiceDetailsSPUserName", produces = "application/json")
	public ResponseEntity<Map<String, List<PaymentInvoiceDTO>>> getPaymentInvoiceDetails(@RequestParam String username,@RequestParam String date)
			throws BookingException {
		List<PaymentInvoiceDTO> result = null;
		Map<String, List<PaymentInvoiceDTO>> response = new HashMap<String, List<PaymentInvoiceDTO>>();
		result = bookingService.getPaymentInvoiceByUserNameAndDate(username, date);
		response.put("data", result);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/_genrate_invoice")
	public ResponseEntity<String> genrateInvoice(@RequestParam String currentDate) throws ParseException {
		String status = "invoice Genrated Successfully!";
		String minmax = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    Date min=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 00:00:00.000");
	    Date max=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 23:59:59.000");
		bookingService.genrateInvoicePerDay(min, max);
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}
	
	@PostMapping("/_update_invoice")
	public ResponseEntity<?> updateInvoice(@RequestParam String invoiceNo) {
		String status = "Invoice not paid!";
		int result = bookingService.updateInvoice(invoiceNo);
		if(result>0)
			status="Invoice paid";
		else
			status="Invoice didn't paid";
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}
	@PutMapping("/updateAcceptRejectStatus")
	public ResponseEntity<PaymentDetails> updateAcceptRejectStatus(@RequestParam String orderId,@RequestParam String status,@RequestParam int notId) throws BookingException {
		PaymentDetails paymentstatus = bookingService.updateStatus(orderId, status,notId);
		return ResponseEntity.status(HttpStatus.OK).body(paymentstatus);
	}
	
	@ApiOperation(value = "Service processing fee status for Admin panel")
	@GetMapping("/getAllServiceProcessingFee")
	public ResponseEntity<List<ServiceProcessingFee>> getAllProcessingFee(){
		List<ServiceProcessingFee> serviceProcessingFee = bookingService.getServiceProcessingFee();
		return ResponseEntity.status(HttpStatus.OK).body(serviceProcessingFee);
	}
}
