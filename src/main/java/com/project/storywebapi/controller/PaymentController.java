package com.project.storywebapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.payload.request.PayRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.VNPayService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/payment")	
public class PaymentController {

	@Autowired
	private VNPayService payService; 
	
	 @GetMapping("payment-callback")
	    public void paymentCallback(@RequestParam Map<String, String> queryParams,HttpServletResponse response) throws IOException	 {
	        payService.paymentCallback(queryParams, response);
	    }
	 @PostMapping("create-pay")
		public ResponseEntity<ApiResponse> createPayment(@RequestBody PayRequest requestParams , HttpServletRequest request){
			final String response = payService.CreatePayment(requestParams, request);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Thanh toán",response));
		}
}
	