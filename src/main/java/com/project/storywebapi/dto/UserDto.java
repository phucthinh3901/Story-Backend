package com.project.storywebapi.dto;

import lombok.Data;

@Data
public class UserDto {
private Integer id;

	private String email;
	
	private String imageUrl;
	
	private String username;
	
	private String password;
}
