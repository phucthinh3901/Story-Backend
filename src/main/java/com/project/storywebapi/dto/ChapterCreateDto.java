package com.project.storywebapi.dto;

import lombok.Data;

@Data
public class ChapterCreateDto {

		private Float chapterNumber;
		
		private String name;
		
		private Integer storyId;	
		
		private String content;
		
}
