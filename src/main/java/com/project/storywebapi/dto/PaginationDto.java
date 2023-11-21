package com.project.storywebapi.dto;

import lombok.Data;

@Data
public class PaginationDto {

	private SearchCondition where;
	
	private Integer skip;
	
	private Integer take;
}
