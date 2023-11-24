package com.project.storywebapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayRequest {
	
	private Double amount;
	
	private String bankCode;	
	
	private String type;
	
	private String currency;
	
	private String content;
	
	private Integer userId;
	
}
