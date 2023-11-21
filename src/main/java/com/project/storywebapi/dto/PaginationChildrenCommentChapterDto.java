package com.project.storywebapi.dto;

import java.util.Date;

import lombok.Data;
@Data
public class PaginationChildrenCommentChapterDto {
	private Integer id;
	
	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	private Boolean isDelete;	
	
	private Integer chapterId;
	
	private String content;
	
	private String avatarUser;
	
	private Integer userId;
	
	private String userName;
	
	private Integer parentId;
}
