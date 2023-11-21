package com.project.storywebapi.payload.request;

import com.project.storywebapi.dto.StoryDto;

import lombok.Data;

@Data
public class UpdateStory extends StoryDto{

	private Integer id;
	
	private Integer userId;
}
