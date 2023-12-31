package com.project.storywebapi.dto;

import java.util.Date;

import lombok.Data;

@Data	
public class PaginationUserDto {
	private Integer id;
	
	private Date createAt;
	
	private Integer createBy;
	
	private Date updateAt;
	
	private Integer updateBy;
	
	private Boolean isDelete;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String avatar;
	
	private String verified;
	
	private RoleUserDto roleDtos;
}
