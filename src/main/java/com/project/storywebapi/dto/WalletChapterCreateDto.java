package com.project.storywebapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class WalletChapterCreateDto {

	private List<Integer> chapterId;
	
	private Integer userId;
}
