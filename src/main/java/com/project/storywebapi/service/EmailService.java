package com.project.storywebapi.service;

import com.project.storywebapi.payload.EmailDetails;
import com.project.storywebapi.payload.request.EmailPaymentNotificationRequest;

public interface EmailService {
	
	void sendSimpleMail(EmailDetails email) throws Exception;
	
	void sendMailUPpgradeRank(EmailPaymentNotificationRequest request);
}
