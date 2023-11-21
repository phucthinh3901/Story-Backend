package com.project.storywebapi.payload.request;

import lombok.Data;

@Data
public class CreateOrUpdateCommentChapter {
	private Integer id;
	
	private Integer chapterId;
	
	private Integer userId;
	
	private String content;
}
