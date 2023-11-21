package com.project.storywebapi.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationStory;
import com.project.storywebapi.dto.StoryDto;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.request.UpdateStory;
import com.project.storywebapi.payload.response.ApiResponse;

public interface StoryService {

	 ApiResponse createStory(MultipartFile[] file,StoryDto storyDto);
	 
	 ApiResponse updateActive(ActiveRequest activeRequest);
	 
	 ApiResponse getStory(ActiveRequest storyId);
	 
	 ApiResponse updateStory( UpdateStory updateStory);
	 
	 Page<PaginationStory> paginationStory(PaginationDto request);
	 
}
