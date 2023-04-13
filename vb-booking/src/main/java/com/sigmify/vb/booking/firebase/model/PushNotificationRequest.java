package com.sigmify.vb.booking.firebase.model;

public class PushNotificationRequest {

    private String title;
    private String message;
    private String topic;
    private String token;
    private String orderId;
    private String userName;
    private int notificationId;
   

    public PushNotificationRequest() {
    }

    public PushNotificationRequest(int notificationId, String title, String orderId, String userName, String token) {
        this.title = title;
        this.orderId=orderId;
        this.userName=userName;
        this.token = token;
        this.notificationId = notificationId;
    }
    
    public PushNotificationRequest(int notificationId, String title,  String userName, String token) {
        this.title = title;
        this.userName=userName;
        this.token = token;
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
    
	
}
