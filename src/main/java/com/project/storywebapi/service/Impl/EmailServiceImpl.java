package com.project.storywebapi.service.Impl;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.project.storywebapi.payload.EmailDetails;
import com.project.storywebapi.payload.request.EmailPaymentNotificationRequest;
import com.project.storywebapi.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	JavaMailSender emailSender;

	@Autowired
	SpringTemplateEngine templateEngine;
	
	@Override
	public void sendSimpleMail(EmailDetails email) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariables(email.getProperties());
		helper.setFrom(email.getFrom());
		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		String html = templateEngine.process(email.getTemplate(), context);
		helper.setText(html, true);
		emailSender.send(message);
		System.out.println("Mail Send...");
		
	}

	@Override
	public void sendMailUPpgradeRank(EmailPaymentNotificationRequest request) {
		EmailDetails email = new EmailDetails();
		try {
			Map<String, Object> properties = new HashMap<>();
			email.setSubject("[Email thông báo nạp tiền]");
			properties.put("userName", request.getUserName());
			properties.put("amount", formatMoneyWithCurrencyVN(request.getAmount()/100));
			properties.put("content", request.getContent());
			properties.put("createAt", request.getCreateAt());
			

			email.setFrom("fromemail@gmail.com");
			email.setTemplate("DepositEmailNotification.html");
			email.setProperties(properties);
			String regex = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(request.getToEmail());
			if (matcher.matches()) {
				email.setTo(request.getToEmail());
				sendSimpleMail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String formatMoneyWithCurrencyVN(Double price) {
		Locale locale = new Locale("vi", "VN");
		Currency currency = Currency.getInstance("VND");
		DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
		df.setCurrency(currency);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		numberFormat.setCurrency(currency);
		return numberFormat.format(price);
	}
	

}
