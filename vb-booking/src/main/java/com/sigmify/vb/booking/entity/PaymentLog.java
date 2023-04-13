package com.sigmify.vb.booking.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table (schema = "booking" , name ="payment_log")
public class PaymentLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4912160215048805056L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "payment_log_id_seq")
	@SequenceGenerator(schema = "booking" , name = "payment_log_id_seq", sequenceName = "payment_log_id_seq", allocationSize =1 )
	private Long id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "total_due")
	private Double totalDue;
	
	@LastModifiedDate
	@Column(name = "last_payment_received")
	@Temporal(TemporalType.TIMESTAMP)
	private  Date lastPaymentReceived;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(Double totalDue) {
		this.totalDue = totalDue;
	}

	public Date getLastPaymentReceived() {
		return lastPaymentReceived;
	}

	public void setLastPaymentReceived(Date lastPaymentReceived) {
		this.lastPaymentReceived = lastPaymentReceived;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
