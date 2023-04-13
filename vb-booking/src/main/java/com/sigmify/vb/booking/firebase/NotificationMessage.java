/**
 * 
 */
package com.sigmify.vb.booking.firebase;

import com.sigmify.vb.booking.dto.OrderDTO;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;

/**
 * @author Developer
 *
 */
public class NotificationMessage {

	// User
	public String REGISTRATION_SUCCESS = "Congratulation {#}, you have successfully registered in Vyavshay Application. Enjoy your service booking experience with our app and get rewarded. Team <Balat>";
	public String PENDING_BOOKING = "Booking Confirmation: Your service request has been kept on hold for 24hrs. Vyavshay Admin is on the job to find out a service provider to serve your request. Please log in to Vyavshay application to view the details. Team <Balat>";
	public String ACCEPT_BOOKING = "Booking Confirmation: Your service request has been accepted by the service owner. Please log in to Vyavshay application to view the details. Team <Balat>";
	public String CANCEL_BOOKING = "Booking Cancellation: Regret to inform you that Vyavshay admin could not find out service owner to serve your request. Please log in to Vyavshay App and try again later. Team <Balat>";
	public static final String UPDATE_PAYMENT_START = "Payment Update: Your Service Owner/Executor {ONNE} updated the payment details of order Id ({OID}) and waiting for your approval and start the service. Please log in to Vyavshay App and view the details in notification page. Team <Balat>";
	public static final String UPDATE_PAYMENT_COMPLETE = "Payment Update: Your Service Owner/Executor {ONNE} updated the payment details of order Id ({OID}) and waiting for complete the service. Please log in to Vyavshay App and view the details in notification page. Team <Balat>";
	public static final String STOPPED_SERVICE_DUE_TO_PAYMENT = "Your service has been stopped due to unpaid invoice payment, Please pay the invoice payment to resume your service";
	// Admin
	public static final String SERVICE_ALLOCATION = "New Service request having Order ID ({OID}) has been allocated to you. Please log in to Vyavshay application to view details. Team <Balat>";
	public static final String UPDATE_SERVICE_ALLOCATION = "Service request having Order ID ({OID}) allocated to you has been updated by user. Please log in to Vyavshay application to view details. Team <Balat>";
	public static final String CANCEL_SERVICE_ALLOCATION = "Service request having Order ID ({OID}) allocated to you has been cancelled by user. Please log in to Vyavshay application to view details. Team <Balat>";
	public String NEW_SERVICE_REQUEST = "You have a new {SRV} service request from {NME}. Please review and accept/decline the request. Team <Balat>";
	public String CANCEL_SERVICE_REQUEST = "Service request having Order ID {OID} allocated to you has been cancelled by user. Please log in to Vyavshay application to view details. Team <Balat>";
	public String UPDATE_SERVICE_REQUEST = "Order ID {OID} of {SRV} service request from {NME} has been updated by {NME-BY}. Please login to Vyavshay App to review and accept/decline the request. Team <Balat>";
	public String PAYMENT_UPDATE_APPROVE = "Payment Update: Payment update for Order ID {OID} has been approved by {NME-BY}. Team <Balat>";
	public String PAYMENT_UPDATE_REJECT = "Payment Update: Payment update for Order ID {OID} has been Rejected by {NME-BY}. Please revisit your payment update and get in touch with {NME-BY} for any discrepancies. Team <Balat>";

	private String message;
	private String title;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "NotificationMessage [message=" + message + ", title=" + title + "]";
	}

	public NotificationMessage getMessage(PushNotificationRequest request) {
		NotificationMessage notificationMessage = new NotificationMessage();
		switch (request.getTitle()) {
		// User
		case "REGISTRATION_SUCCESS":
			message = REGISTRATION_SUCCESS.replace("{#}", request.getUserName());
			title = "Registration Success";
			break;

		case "PENDING_BOOKING":
			message = PENDING_BOOKING.replace("{#}", request.getUserName());
			title = "Pending Booking";
			break;

		case "ACCEPT_BOOKING":
			message = PENDING_BOOKING.replace("{#}", request.getUserName());
			title = "Accept Booking";
			break;

		case "CANCEL_BOOKING":
			message = CANCEL_BOOKING.replace("{#}", request.getUserName());
			title = "Cancel Booking";
			break;

		case "UPDATE_PAYMENT_START":
			message = UPDATE_PAYMENT_START.replace("{#}", request.getUserName());
			title = "Payment Update Start Service";
			break;
			
		case "UPDATE_PAYMENT_COMPLETE":
			message = UPDATE_PAYMENT_COMPLETE.replace("{#}", request.getUserName());
			title = "Payment Update Complete Service";
			break;
		}
		notificationMessage.setMessage(message);
		notificationMessage.setTitle(title);
		return notificationMessage;
	}

	// Admin
	public NotificationMessage getMessage(PushNotificationRequest request, OrderDTO orderDto) {
		NotificationMessage notificationMessage = new NotificationMessage();
		switch (request.getTitle()) {

		case "SERVICE_ALLOCATION":
			message = SERVICE_ALLOCATION.replace("{OID}", request.getOrderId());
			title = "Service Allocation";
			break;

		case "UPDATE_SERVICE_ALLOCATION":
			message = UPDATE_SERVICE_ALLOCATION.replace("{OID}", request.getOrderId());
			title = "Update Service Allocation";
			break;

		case "CANCEL_SERVICE_ALLOCATION":
			message = CANCEL_SERVICE_ALLOCATION.replace("{OID}", request.getOrderId());
			title = "Cancel Service Allocation";
			break;

		case "NEW_SERVICE_REQUEST":
			message = NEW_SERVICE_REQUEST.replace("{SRV}", orderDto.getServiceName()).replace("{NME}", orderDto.getServiceExecutorName());
			title = "New Service Request";
			break;

		case "CANCEL_SERVICE_REQUEST":
			message = CANCEL_SERVICE_REQUEST.replace("{OID}", request.getOrderId());
			title = "Cancel Service Request";
			break;

		case "UPDATE_SERVICE_REQUEST":
			message = UPDATE_SERVICE_REQUEST.replace("{OID}", request.getOrderId()).replace("{SRV}", orderDto.getServiceName())
					.replace("{NME}", orderDto.getServiceExecutorName()).replace("{NME-BY}", orderDto.getServiceExecutorName());
			title = "Update Service Request";
			break;

		case "PAYMENT_UPDATE_APPROVE":
			message = PAYMENT_UPDATE_APPROVE.replace("{OID}", request.getOrderId()).replace("{NME-BY}", orderDto.getServiceExecutorName());
			title = "Payment Update Approved";
			break;

		case "PAYMENT_UPDATE_REJECT":
			message = PAYMENT_UPDATE_REJECT.replace("{OID}", request.getOrderId()).replace("{NME-BY}", orderDto.getServiceExecutorName());
			title = "Payment Update Rejected";
			break;

		}
		notificationMessage.setMessage(message);
		notificationMessage.setTitle(title);
		return notificationMessage;
	}
}
