package com.project.storywebapi.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PaginationCommentDto {

	private Integer id;
	
	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	private Boolean isDelete;	
	
	private Integer storyId;
	
	private String content;
	
	private String avatarUser;
	
	private Integer userId;
	
	private String userName;
	
	List<PaginationChildrenCommentDto> childrenComments;
	
}
