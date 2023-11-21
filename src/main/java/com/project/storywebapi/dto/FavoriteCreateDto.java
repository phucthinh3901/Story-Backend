package com.project.storywebapi.dto;

import lombok.Data;

@Data
public class FavoriteCreateDto {

	private Integer storyId;
	
	private Integer userId;
}
