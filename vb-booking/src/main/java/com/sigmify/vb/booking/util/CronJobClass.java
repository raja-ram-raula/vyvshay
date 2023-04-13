package com.sigmify.vb.booking.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sigmify.vb.booking.constants.BookingConstant;
import com.sigmify.vb.booking.dto.UserDTO;
import com.sigmify.vb.booking.endpoints.OrderController;
import com.sigmify.vb.booking.entity.NotificationDetails;
import com.sigmify.vb.booking.entity.PaymentInvoice;
import com.sigmify.vb.booking.firebase.NotificationMessage;
import com.sigmify.vb.booking.firebase.model.PushNotificationRequest;
import com.sigmify.vb.booking.service.BookingService;
import com.sigmify.vb.booking.serviceImpl.BookingServiceImpl;


@Component
public class CronJobClass {
	private final Logger logger = Logger.getLogger(CronJobClass.class);

	@Autowired
	private BookingService bookingService;
	RestTemplate restTemplate;
	private static final String FB_TOKEN_URL=BookingConstant.ADMIN_APPLICATION_BASEURL + "user/get/token";
	
	
	@Scheduled(cron = "0 59 23 * * *", zone="IST")
	public void performTaskUsingCron() throws ParseException {
		logger.info("======Crone Job Started for Invoice======= "+new Date());
		String minmax = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    Date min=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 00:00:00.000");
	    Date max=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(minmax+" 23:59:59.000");
		bookingService.genrateInvoicePerDay(min, max);
		new CronJobClass().getInvoiceStatus();

	}
	
	public List<PaymentInvoice> getInvoiceStatus()
	{
		DateTime dateTime = new DateTime();
	    String minmax = new SimpleDateFormat("yyyy-MM-dd").format(dateTime.minusDays(1));
		List<PaymentInvoice> list = bookingService.getPendingInvoicePayment(minmax);
		//List<String> SEList = new ArrayList<String>();
		String deviceToken = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		for(PaymentInvoice object:list)
		{
			HttpEntity<String> entity = new HttpEntity<String>(object.getServiceOwnerId(), headers);
			restTemplate.exchange(BookingConstant.ADMIN_APPLICATION_BASEURL+ BookingConstant.UPDATE_SERVICE_EXECUTOR_URL+"_status", HttpMethod.POST, entity, String.class);
			deviceToken = new RestTemplate().getForObject(FB_TOKEN_URL+"/"+object.getServiceOwnerId(), String.class);
			if(deviceToken!=null)
			{
				new OrderController().sentNotification(deviceToken, object.getServiceOwnerId());
			}
			
			
			
		}
		
		return list;		
	}
	
	
	@Scheduled(cron = "0 0 0/1 * * *", zone="IST")
	public void performTaskEveryHourUsingCron() throws ParseException {
		logger.info("======Crone Job Started hourly bases======= "+new Date());
		new BookingServiceImpl().SendPushNotificationHourly();

	}

}
