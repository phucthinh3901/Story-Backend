package com.project.storywebapi.service;

import org.springframework.data.domain.Page;

import com.project.storywebapi.dto.FilterOneDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationHistoryDto;
import com.project.storywebapi.entities.History;
import com.project.storywebapi.payload.request.CreateAndUpdateHistoryRequest;
import com.project.storywebapi.payload.response.ApiResponse;

public interface HistoryService {
	
	ApiResponse createOrUpdateHistory(CreateAndUpdateHistoryRequest createAndUpdateHistoryRequest);

	ApiResponse deleteHistory(FilterOneDto filterOneDto);
	
	Page<PaginationHistoryDto> paginationHistory(PaginationDto request);
}
