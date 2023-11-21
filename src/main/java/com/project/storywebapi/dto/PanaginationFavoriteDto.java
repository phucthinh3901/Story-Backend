package com.project.storywebapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PanaginationFavoriteDto {

	private Integer id;
	
	private String avatar;
	
	private Boolean isDelete;

	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	private String name;
	
	private String type;
	
	private Integer userId;
	
}
