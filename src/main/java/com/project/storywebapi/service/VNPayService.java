package com.project.storywebapi.service;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.storywebapi.common.VNPayConst;
import com.project.storywebapi.dto.WalletHistoryCreateDto;
import com.project.storywebapi.payload.request.PayRequest;
import com.project.storywebapi.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;			
@Service
public class VNPayService {
	
	@Autowired
	WalletService walletService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserRepository userRepository;
	
	public String CreatePayment( PayRequest requestParams , HttpServletRequest request) {
		double amountDouble = requestParams.getAmount();
		int amount = (int)amountDouble*100;
		String paymentUrl = null;
		try {
			Map<String,String> vnp_params = new HashMap<>();
			vnp_params.put("vnp_Version",VNPayConst.vnp_Version);
			vnp_params.put("vnp_Command", VNPayConst.vnp_Command);
			vnp_params.put("vnp_TmnCode", VNPayConst.vnp_TmnCode);
			vnp_params.put("vnp_Amount", String.valueOf(amount));
			String bank_code = requestParams.getBankCode();
			if (bank_code != null && !bank_code.isEmpty()) {
				vnp_params.put("vnp_BankCode", bank_code);
			}
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String vnp_CreateDate = time.format(formatter);
			vnp_params.put("vnp_CreateDate",vnp_CreateDate);
			vnp_params.put("vnp_CurrCode",VNPayConst.vnp_CurrCode);
			vnp_params.put("vnp_IpAddr",VNPayConst.getIpAddress(request));
			vnp_params.put("vnp_Locale",VNPayConst.vnp_Locale);
			vnp_params.put("vnp_OrderInfo",requestParams.getContent());
			vnp_params.put("vnp_OrderType", VNPayConst.vnp_OrderType);
			vnp_params.put("vnp_ReturnUrl",VNPayConst.vnp_ReturnUrl +"?userId="+ requestParams.getUserId());
			vnp_params.put("vnp_TxnRef", VNPayConst.getOTP(8));

//		vnp_params.put("vnp_SecureHash",PaymentConfig.vnp_SecureHash);
			List fieldName = new ArrayList(vnp_params.keySet());
			Collections.sort(fieldName);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			Iterator iterator = fieldName.iterator();
			while (iterator.hasNext()){
				String name = (String) iterator.next();
				String value = vnp_params.get(name);
				if ((value != null)&&(value.length()>0)){

					hashData.append(name);
					hashData.append("=");
					hashData.append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));

					query.append(URLEncoder.encode(name,StandardCharsets.US_ASCII.toString()));
					query.append("=");
					query.append(URLEncoder.encode(value,StandardCharsets.US_ASCII));

					if (iterator.hasNext()){
						query.append("&");
						hashData.append("&");
					}
				}
			}
			String queryUrl = query.toString();
			String vnp_SecureHash = VNPayConst.hmacSHA512(VNPayConst.vnp_HashSecret, hashData.toString());
			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			paymentUrl = VNPayConst.vnp_PayUrl + "?" + queryUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return paymentUrl;
	}
	public void paymentCallback(Map<String, String> queryParams,HttpServletResponse response) {
		String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
		String userId = queryParams.get("userId");
		String amount = queryParams.get("vnp_Amount");
		String currency = "VND";
		String content = queryParams.get("vnp_OrderInfo");
		String type = "Deposit";
				
		Double parseAmount = Double.parseDouble(amount);
		Integer parseUserId = Integer.parseInt(userId);
		
		WalletHistoryCreateDto wh = new WalletHistoryCreateDto();
		wh.setAmount(parseAmount);		
		wh.setContent(content);
		wh.setCurrency(currency);
		wh.setType(type);
		wh.setUserId(parseUserId);
		
        try {
			if ("00".equals(vnp_ResponseCode)) {
//				response.sendRedirect("http://localhost:4200/payment-success");
				System.out.println("THanh cong");
			walletService.createHistoryWallet(wh);
			
			} else {
			    response.sendRedirect("http://localhost:4200/payment-failed");
			    System.out.println("thất bại");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
