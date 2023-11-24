package com.project.storywebapi.payload.request;

import java.util.Date;

import lombok.Data;

@Data
public class EmailPaymentNotificationRequest {

	private String userName;
	
	private Double amount;
	
	private String content;
	
	private Date createAt;
	
	private String toEmail;
	
}
