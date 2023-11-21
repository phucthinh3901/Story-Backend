package com.project.storywebapi.dto;

import org.hibernate.annotations.UuidGenerator;

import lombok.Data;

@Data
public class CreateUseDto {

	private Integer id;
	
	private String username;
	
	private String roleCode;
}
