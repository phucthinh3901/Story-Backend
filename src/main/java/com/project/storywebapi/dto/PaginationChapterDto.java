package com.project.storywebapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaginationChapterDto {

	private Integer id;
	
	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	private Boolean isDelete;
	
	private Float chapterNumber;
	
	private String name;
	
	private String content;
	
	private Integer viewCount;
	
	private Integer storyId;
}
