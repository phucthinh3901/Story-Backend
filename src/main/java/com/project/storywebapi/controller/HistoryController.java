package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.FilterOneDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationHistoryDto;
import com.project.storywebapi.entities.Favorite;
import com.project.storywebapi.entities.History;
import com.project.storywebapi.payload.request.CreateAndUpdateHistoryRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.HistoryService;


@RestController
@RequestMapping("/history")
public class HistoryController {

	@Autowired
	HistoryService historyService;
	
	@PostMapping("/create_data")
	private ApiResponse createorUpdateHistory(@RequestBody CreateAndUpdateHistoryRequest createAndUpdateHistoryRequest) {
		return historyService.createOrUpdateHistory(createAndUpdateHistoryRequest);
		
	}	
	@PostMapping("/update_active")
	private ApiResponse deleteHistory(@RequestBody FilterOneDto filterOneDto) {
		return historyService.deleteHistory(filterOneDto);
	}
	@PostMapping("/pagination")
	public Page<PaginationHistoryDto> paginationFavorite(@RequestBody PaginationDto paginationDto){
		return historyService.paginationHistory(paginationDto);
	}
}
