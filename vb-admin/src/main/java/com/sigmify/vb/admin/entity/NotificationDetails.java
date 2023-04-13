package com.sigmify.vb.admin.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

	@Entity
	@Table(schema = "admin" , name = "notification_details")
	@JsonInclude(Include.NON_NULL)
	public class NotificationDetails implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 832795377890723957L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "notification_id_seq")
		 @SequenceGenerator(schema = "admin", name = "notification_id_seq",
	      sequenceName = "notification_id_seq", allocationSize = 1)
		
		private int notificationId;
		
		@Column(name = "sender_id",length = 50, nullable = false)
		private String senderId;
		
		@Column(name = "receiver_id",length = 50, nullable = false)
		private String receiverId;
		
		@Column(name = "notification_title",length = 500, nullable = false)
		private String notificationTitle;

		@Column(name="notification_message",length = 500,nullable = false)
		private String notificationMessage;
		
		@Column(name = "notification_status",length = 2)
		private int notificationStatus;

		@Column(name="create_time")
		@CreationTimestamp
		  private Timestamp createTime;
		
		@Column(name="updated_time")
		@UpdateTimestamp
		  private Timestamp updated_time;

		public int getNotificationId() {
			return notificationId;
		}

		public void setNotificationId(int notificationId) {
			this.notificationId = notificationId;
		}

		public String getSenderId() {
			return senderId;
		}

		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}

		public String getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(String receiverId) {
			this.receiverId = receiverId;
		}

		public String getNotificationTitle() {
			return notificationTitle;
		}

		public void setNotificationTitle(String notificationTitle) {
			this.notificationTitle = notificationTitle;
		}

		public String getNotificationMessage() {
			return notificationMessage;
		}

		public void setNotificationMessage(String notificationMessage) {
			this.notificationMessage = notificationMessage;
		}

		public int getNotificationStatus() {
			return notificationStatus;
		}

		public void setNotificationStatus(int notificationStatus) {
			this.notificationStatus = notificationStatus;
		}

		
		public Timestamp getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Timestamp createTime) {
			this.createTime = createTime;
		}

		public Timestamp getUpdated_time() {
			return updated_time;
		}

		public void setUpdated_time(Timestamp updated_time) {
			this.updated_time = updated_time;
		}

		@Override
		public String toString() {
			return "NotificationDetails [notificationId=" + notificationId + ", senderId=" + senderId + ", receiverId="
					+ receiverId + ", notificationTitle=" + notificationTitle + ", notificationMessage="
					+ notificationMessage + ", notificationStatus=" + notificationStatus + ", createTime=" + createTime
					+ ", updated_time=" + updated_time + "]";
		}
		

}
