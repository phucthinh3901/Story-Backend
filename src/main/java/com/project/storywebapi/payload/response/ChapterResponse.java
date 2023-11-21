package com.project.storywebapi.payload.response;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ChapterResponse {
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
	
	private List<String> listImage;
}
