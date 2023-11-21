package com.project.storywebapi.payload.request;

import lombok.Data;

@Data
public class DeleteRequest {

	private Integer id;
	
	private Integer userId;
}
