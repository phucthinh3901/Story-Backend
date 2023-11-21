package com.project.storywebapi.payload.request;

import lombok.Data;

@Data
public class CreateOrUpdateComment {

	private Integer id;
	
	private Integer storyId;
	
	private Integer userId;
	
	private String content;
	
	
		
}
