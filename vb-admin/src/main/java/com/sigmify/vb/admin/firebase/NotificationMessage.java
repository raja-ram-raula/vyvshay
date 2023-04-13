package com.sigmify.vb.admin.firebase;

public class NotificationMessage {

	//User
	public static final String ACCEPT_BOOKING = "Booking Confirmation: Your service request of OrderId {OID} has been accepted by the service owner {ONNE}. Please log in to Vyavshay application to view the details. Team <Balat>";
	
	//Admin
	
		public static final String REGISTRATION_SUCCESS = "Congratulation {NME}, you have successfully registered in Vyavshay Application. Enjoy your service booking experience with our app and get rewarded. Team <Balat>";
		public String SERVICE_ALLOCATION = "New Service request having Order ID {OID} has been allocated to you. Please log in to Vyavshay application to view details. Team <Balat>";
		public String CANCEL_SERVICE_ALLOCATION = "Service request having Order ID {OID} allocated to you has been cancelled by user. Please log in to Vyavshay application to view details. Team <Balat>";
		public String NEW_SERVICE_REQUEST = "You have a new {SRV} service request from {NME}. Please review and accept/decline the request. Team <Balat>";
		public String CANCEL_SERVICE_REQUEST = "Service request having Order ID {OID} allocated to you has been cancelled by user. Please log in to Vyavshay application to view details. Team <Balat>";
		public String UPDATE_SERVICE_REQUEST = "Order ID {OID} of {SRV} service request from {NME} has been updated by {NME-BY}. Please login to Vyavshay App to review and accept/decline the request. Team <Balat>";
		public String PAYMENT_UPDATE_APPROVE = "Payment Update: Payment update for Order ID {OID} has been approved by {NME-BY}. Team <Balat>";
		public String PAYMENT_UPDATE_REJECT = "Payment Update: Payment update for Order ID {OID} has been Rejected by {NME-BY}. Please revisit your payment update and get in touch with {NME-BY} for any discrepancies. Team <Balat>";
}
