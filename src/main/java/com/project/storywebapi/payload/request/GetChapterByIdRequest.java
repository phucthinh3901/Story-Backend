package com.project.storywebapi.payload.request;

import java.util.Date;
import java.util.List;

import com.project.storywebapi.entities.Chapter;

import lombok.Data;

@Data
public class GetChapterByIdRequest {
	
	private Integer id;
	
	private String content;
	
	private Float chapterNumber;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private Boolean isDeleted;
	
	private String name;
	
	private Integer nextId;
	
	private Integer prevId;
	
	private Integer storyId;
	
	private String storyName;
	
	private String storyType;
	
	private Integer viewCount;
	
	private List<Chapter> lstChapter;
	
}
