package com.project.storywebapi.payload.request;

import lombok.Data;

@Data
public class DeleteFeedbackRequest {
	
	private Integer id;
	
	private Integer userId;
	
}
