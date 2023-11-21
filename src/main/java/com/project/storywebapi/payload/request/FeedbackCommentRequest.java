package com.project.storywebapi.payload.request;

import lombok.Data;

@Data
public class FeedbackCommentRequest {
	
	private Integer id;
	
	//private Integer storyId;
	
	private Integer userId;
	
	private String content;
	
	private Integer parentId;
}
