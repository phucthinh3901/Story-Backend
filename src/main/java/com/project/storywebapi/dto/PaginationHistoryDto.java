package com.project.storywebapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaginationHistoryDto {

	private Integer chapterId;
	
	private Float chapterName;
	
	private String storyName;
	
	private String typeName;
	
	private String storyAvatar;
	
	private Integer storyId;
	
	private Boolean isDelete;

	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
}
