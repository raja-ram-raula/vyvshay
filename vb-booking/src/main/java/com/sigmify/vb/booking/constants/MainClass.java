package com.sigmify.vb.booking.constants;

import org.springframework.web.client.RestTemplate;

import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;

public class MainClass {
	
	private static final String NOTIFICATION_URL=BookingConstant.ADMIN_APPLICATION_BASEURL + "user/notification/token";
	RestTemplate restTemplate;
	public static void main(String ar[])
	{
		
		
		PushNotificationRequest request = new PushNotificationRequest();
			  String message = "Manual Notification";
			  String title= "Testing manually";
			  String token="dKlgxuaTIDg:APA91bER9KaW6w0QCSBk5a48Zn7RXG8GKrQA8EAz1yPKY4wtX3WGDrm9dTgRlKevciScA72uTEachnqtop5SmwdILkpzidL1NzD5W_4qpmvRtP0vUbxvpfyD2ygp_XqMfpgPHjXkQ8xr";
			request.setMessage(message);
			request.setTitle(title);
			request.setToken(token);
			//Object responce="";
			
			new RestTemplate().postForObject(NOTIFICATION_URL, request, String.class);
	
	}

}
