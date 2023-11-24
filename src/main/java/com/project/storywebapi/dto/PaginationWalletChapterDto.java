package com.project.storywebapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PaginationWalletChapterDto {

	private String storyName;
		
	private Integer storyId;
	
	private Integer chapterId;
	
	private Float chapterNumber;
	
	private String chapterName;
	
	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	
}
