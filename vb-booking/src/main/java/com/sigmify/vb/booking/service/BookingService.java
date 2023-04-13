package com.sigmify.vb.booking.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.sigmify.vb.booking.dto.AdminMetadata;
import com.sigmify.vb.booking.dto.AdminPanelOrderStatus;
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
import com.sigmify.vb.booking.dto.ServiceCategoryTypeDTO;
import com.sigmify.vb.booking.dto.ServiceDetailsDTO;
import com.sigmify.vb.booking.dto.ServiceExecutorListDTO;
import com.sigmify.vb.booking.dto.ServiceProcessingFee;
import com.sigmify.vb.booking.dto.ServiceRequest;
import com.sigmify.vb.booking.dto.ServiceResponse;
import com.sigmify.vb.booking.dto.ServiceSubCatMetaData;
import com.sigmify.vb.booking.dto.ServiceSubCategoryTypeDTO;
import com.sigmify.vb.booking.dto.ServiceTypeDTO;
import com.sigmify.vb.booking.dto.ServiceTypeMetaData;
import com.sigmify.vb.booking.dto.TrackOrderDTO;
import com.sigmify.vb.booking.dto.UpdateLatLongDTO;
import com.sigmify.vb.booking.entity.NotificationDetails;
import com.sigmify.vb.booking.entity.Order;
import com.sigmify.vb.booking.entity.OrderDetails;
import com.sigmify.vb.booking.entity.OrderStatusType;
import com.sigmify.vb.booking.entity.PaymentDetails;
import com.sigmify.vb.booking.entity.PaymentInvoice;
import com.sigmify.vb.booking.entity.Price;
import com.sigmify.vb.booking.entity.ServiceCategoryType;
import com.sigmify.vb.booking.entity.ServiceDetails;
import com.sigmify.vb.booking.entity.ServiceExecutorContactType;
import com.sigmify.vb.booking.entity.ServiceStatusUser;
import com.sigmify.vb.booking.entity.ServiceSubCategoryType;
import com.sigmify.vb.booking.entity.ServiceType;
import com.sigmify.vb.booking.entity.UnitMeasurementType;
import com.sigmify.vb.booking.exception.BookingException;
import com.sigmify.vb.booking.firebase.NotificationMessage;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;

public interface BookingService {

	//List<ServiceCategoryType> getActiveCategoryList();

	List<ServiceCategoryTypeDTO> getActiveCategoryList(String lang);

	//List<ServiceSubCategoryType> getActiveSubCategoryListByCategoryId(Long categoryId);

	List<ServiceSubCategoryTypeDTO> getActiveSubCategoryListByCategoryId(Long categoryId,String lang);
	//List<ServiceType> getActiveServiceTypeByServiceId(Long serviceId);
	List<ServiceTypeDTO> getActiveServiceTypeByServiceId(Long serviceId,String lang);

	List<UnitMeasurementType> getActiveMeasurementList();

	List<Price> getActivePriceListById(Long id);

	List<ServiceExecutorContactType> getExecutorContactList();

	List<Order> getACtiveOrderList();

	ServiceStatusUser getStatusUserById(Long orderId);

	OrderStatusType getOrderStatusTypeById(Long id);

	OrderDetails getOrderDetailsByOrderId(Long orderId);

	List<ServiceDetails> getActiveServiceList();

	ServiceResponse getServiceDetailsByCategoryId(Long categoryId,Long contactId);
	ServiceResponse getServiceDetailsByCategoryId(Long categoryId,String lang);
	List<MyServiceDetailsDTO> getSelfServiceDetails(String userName) throws BookingException;

	//ServiceResponse getServiceDetailsByCategoryIdId(Long categoryId);

	//ServiceResponse getServiceDetailsInHindiByCategoryIdId(Long categoryId) throws BookingException;

	ServiceDetailsDTO saveExecutorDetails(ServiceDetailsDTO serviceDetails, ServiceDetails sd)throws BookingException;

	String uploadDocument(MultipartFile file);

	SearchResponse getServiceDetails(ServiceRequest serviceRequest) throws BookingException;

	Order createOrder(OrderRequestDTO orderRequestDTO)throws Exception;

	List<OrderDTO> findOrdersByUserId(String userId) throws BookingException;

	OrderDTO findOrdersByOrderId(String orderId) throws BookingException;

	List<OrderDTO> ordersByOrderIdStatus(String orderId, String status) throws BookingException;

	List<OrderDTO> findOrdersByUserIdandStatus(String userId, String status) throws BookingException;

	OrderResponseDTO updateOrders(String orderId, String userId)throws BookingException;

	OrderResponseDTO updateOrders(String orderId, String status, String userId);

	List<ServiceDetailsDTO> getAvailableServiceNearToFarmer(String farmerLocationLatitude,
			String farmerLocationLongitude, String zipCode, String service) throws Exception;

	List<OrderDTO> findSEOrders(String userId, String status) throws BookingException;

	List<OrderDTO> findCurrentSEOrders(String userId, String status) throws BookingException;

	List<OrderDTO> findUpcomingSEOrders(String userId, String status) throws BookingException;

	List<OrderDTO> findCompleteSEOrders(String userId, String status) throws BookingException;

	List<OrderDTO> findCompletedSEOrders(String orderId, String status) throws BookingException;

	List<PaymentDto> findCompletedPaymnetSEOrders(String orderId, String status) throws BookingException;

	Order updateOrder(OrderUpdateDto orderUpdateDTO);

	PaymentDto getPaymentDetails(String orderId) throws BookingException;

	String updatePaymentDetails(PaymentRequest paymentDto);

	String reUpdatePaymentDetails(PaymentRequest paymentDto);

	String updateLatLongOfServiceExecutor(UpdateLatLongDTO updateDto) throws BookingException;

	List<TrackOrderDTO> trackOrderBySeUserId(String userName) throws BookingException;

	List<ServiceExecutorListDTO> getServiceExecutors(String name) throws BookingException;

	ServiceDetailsDTO updateServiceExecutorDetails(ServiceDetailsDTO serviceDetailsDto) throws BookingException;

	ServiceResponse getAllSelfServices(String userName, ServiceCategoryType id) throws BookingException;

	List<OrderDTO> findLastThreeOrdersByUserName(String userName) throws BookingException;

	List<MyServiceDetailsDTO> getServiceDetailsByUserName(String userName) throws BookingException;

	List<MyServiceDetailsDTO> getSEServiceDetails(String phone) throws BookingException;

	String serviceStartSenddOtp(String mobile, String orderId) throws BookingException, UnirestException;

	String serviceStartVerifyOtp(String mobile, String otp) throws BookingException, UnirestException;

	String serviceCompleteSenddOtp(String mobile, String orderId) throws BookingException, UnirestException;

	String serviceCompleteVerifyOtp(String mobile, String otp) throws BookingException, UnirestException;

	String serviceOwnerNewServiceReqMsg(String mobile, String orderId) throws BookingException, UnirestException;

	String serviceOwnerUpdateServiceReqMsg(String mobile, String orderId) throws BookingException, UnirestException;

	String serviceOwnerCancelServiceReqMsg(String orderId) throws BookingException, UnirestException;

	String serviceExeNewServiceReqMsg(String mobile, String orderId) throws BookingException, UnirestException;

	String serviceExeUpdateServiceReqMsg(String mobile, String orderId) throws BookingException, UnirestException;

	String serviceExeCancelServiceReqMsg(String mobile, String orderId) throws BookingException, UnirestException;

	String userPaymentUpdateNotification(String orderId) throws BookingException, UnirestException;

	String serviceOwnerPaymentUpdateApprove(String orderId) throws BookingException, UnirestException;

	String serviceOwnerPaymentUpdateReject(String orderId) throws BookingException, UnirestException;

	String seEnrollmentMsg(String mobile, String serviceName) throws BookingException, UnirestException;

	List<String> getUserIdByOrder(String orderId);

	ServiceCategoryType saveServiceCategoryType(AdminMetadata metaData);

	ServiceExecutorContactType saveServiceExecutorContactType(AdminMetadata metaData);

	UnitMeasurementType saveUnitOfMeasurementType(AdminMetadata metaData);

	ServiceSubCategoryTypeDTO saveServiceSubCatType(ServiceSubCatMetaData serviceSubCatMeta);

	ServiceType saveServiceType(ServiceTypeMetaData serviceTypeMeta);

	List<ServiceSubCategoryTypeDTO> fetchAllSubCategory();

	List<ServiceTypeMetaData> fetchAllServiceType();

	List<ServiceExecutorContactType> fetchAllServiceExecutorContactType();

	List<UnitMeasurementType> fetchAllUnitOfMeasurementType();

	public NotificationMessage makeNotificationMessage(PushNotificationRequest request);

	public NotificationMessage makeNotificationMessage(PushNotificationRequest request, OrderDTO orderDto);

	public List<PaymentDto> getAllCompleteOrders(String userName) throws BookingException;

	public List<PaymentDto> getAllCompleteOrdersBySelf(String userName) throws BookingException;

	public List<PaymentDto> getAllCompleteOrdersByExecutor(String phone) throws BookingException;

	public List<PaymentDto> getPaymentInvoice(String userName) throws BookingException;

	public PaymentDto getPaymentLog(String userName) throws BookingException;

	public List<PaymentDto> doPayment(List<String> listOrderNumber) throws BookingException;

	List<OrderDetailsDTO> listOfProviderList(String orderId) throws BookingException;

	public NotificationDetails saveNotificationDetails(NotificationDetails notificationRequest);

	public List<NotificationDetails> fetchAllNotification(String receiverId);

	public int updateNotificationStatus(int notificationId);
	
	public int getPSRCount(String userId, long serviceId, Date min, Date max);
	
    public void genrateInvoicePerDay(Date min, Date max);
    
    public int updateInvoice(String invoiceNo);
	
	List<PaymentInvoice> getByUsernameDate(String username,String date)throws BookingException;
	
	List<PaymentInvoice> getPendingInvoicePayment(String date);
	
	List<PaymentInvoiceDTO> getPaymentInvoiceByUserNameAndDate(String userName,String date)throws BookingException;
	
	public Order reCreateOrder(Order orderObject);
	
	PaymentDetails updateStatus(String orderId,String status,int notiId)throws BookingException;
	
	public String deleteServiceDetailsById(Long id)throws BookingException,UnirestException;
	
	public List<ServiceProcessingFee> getServiceProcessingFee();
	public List<ServiceDetailsDTO> getOwnerOrderStatus();
	public List<AdminPanelOrderStatus> getFarmerOrderStatus();
	//Order findOrdersDetails(String serviceId, String createdBy, Date orderStartTime) throws BookingException;
}
