package com.project.storywebapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class StoryDto {
	
	private Integer userId;
	
	private String name;
	
	private String otherName;
	
	private String type;
	
	private String author;
	
	private String content;
		
	private Boolean finished;
	
	private String avatar;
	
	private Integer storyId;
	
	private String storyImage;
	
	private List<Integer> lstCategoryIds;
	
}
