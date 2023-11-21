package com.project.storywebapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class SearchCondition {
	
	private Boolean isDeleted;

	private String name;
	
	private Integer storyId;
	
	private Integer userId;
	
	private Boolean finished;
	
	private List<Integer> listCategoryId;

	private String typeStory;
	
	private Boolean verified;
	
	private Integer chapterId;
	
}
