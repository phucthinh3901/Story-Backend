package com.project.storywebapi.dto;

import lombok.Data;

@Data
public class WalletHistoryCreateDto {
	
	private String type;
	
	private String currency;
	
	private String content;
	
	private Double amount;
	
	private Integer userId;
	

}
