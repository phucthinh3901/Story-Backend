package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationStory;
import com.project.storywebapi.dto.StoryDto;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.request.UpdateStory;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.StoryService;

@RestController
@RequestMapping("/story")
public class StoryController {

	@Autowired
	StoryService storyService;
	
	@PostMapping("/create_data")
	
	public ApiResponse createStory(@RequestParam(value ="file", required = false) MultipartFile[] file ,@RequestParam(value ="data", required = false) String storyCreateDto) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ApiResponse apiResponse = null;
		StoryDto dto = null;
		if(storyCreateDto != null) {
			 dto = mapper.readValue(storyCreateDto, StoryDto.class);	
		}
		apiResponse = storyService.createStory(file,dto);
		
		return new ApiResponse(true,"The story completed successful",apiResponse);
	}
	
	@PostMapping("/update_active")
	public ApiResponse updateActive(@RequestBody ActiveRequest activeRequest) {
		return storyService.updateActive(activeRequest);
	}
	
	@PostMapping("/get_story")
	public ApiResponse getStory(@RequestBody ActiveRequest storyId) {
		return storyService.getStory(storyId);
	}
	
	@PostMapping("/update_data")
	public ApiResponse updateStory(@RequestBody UpdateStory updateStory) {
		return storyService.updateStory(updateStory);
	}
	
	@PostMapping("/pagination")
	public Page<PaginationStory> paginationStory(@RequestBody PaginationDto paginationDto){
		return storyService.paginationStory(paginationDto);
	}
}
