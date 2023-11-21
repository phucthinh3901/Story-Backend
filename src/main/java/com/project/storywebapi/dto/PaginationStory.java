package com.project.storywebapi.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PaginationStory {
	
	private Integer id;
	
	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	private Boolean isDelete;
	
	private String name;
	
	private String otherName;
	
	private String author;
	
	private String avatar;
	
	private String content;
	
	private Boolean finished;
	
	private String type;
	
	private List<String> lstCategoryName;
	
	private List<Integer> lstCategoryId;
	
	private Integer totalView;
	
	private Integer chapterCount;
	
	private Integer commentCount;
	
	private Integer favoriteCount;

}
