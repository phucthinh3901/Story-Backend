package com.project.storywebapi.payload.request;

import lombok.Data;

@Data
public class ActiveChapterRequest {
	
	private Integer id;
	
	private Integer userId;
	
	private Boolean isDel;
	
}
