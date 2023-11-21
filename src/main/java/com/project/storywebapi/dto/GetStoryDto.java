package com.project.storywebapi.dto;

import java.util.Date;
import java.util.List;

import com.project.storywebapi.entities.Category;
import com.project.storywebapi.entities.Category_Story;

import lombok.Data;

@Data
public class GetStoryDto {
	
	private String author;

	private String avatar;
	
	private Integer commentCount;
	
	private Integer favoriteCount;
	
	private String content;
	
	private Date createdAt;
	
	private String createdBy;
		
	private Boolean finished;
	
	private Boolean isDeleted;
	
	private Integer id;
	
	private List<Integer> lstCategoryIds;
	
	private List<String> lstCategoryName;
	
	private List<Integer> lstUserIdFavorite;
	
	private String name;
	
	private Integer nextChapterId;
	
	private String otherName;
	
	private String startChapterId;
	
	private String type;
	
	private Integer totalView;
	
	private String typeName;
	
	private Date updatedAt;
	
	private Integer updatedBy;

}
