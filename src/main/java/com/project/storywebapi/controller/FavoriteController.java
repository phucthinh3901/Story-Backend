package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.FavoriteCreateDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PanaginationFavoriteDto;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.FavoriteService;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

	@Autowired
	FavoriteService favoriteService;
	
	@PostMapping("/create_data")
	private ApiResponse createFavorite(@RequestBody FavoriteCreateDto createDto){
		return favoriteService.createFavorite(createDto);
	}
	
	@PostMapping("/delete_favorite")
	private ApiResponse activeFavorite(@RequestBody ActiveRequest request) {
		return favoriteService.activeFavorite(request);
	}
	
	@PostMapping("/pagination")
	public Page<PanaginationFavoriteDto> paginationFavorite(@RequestBody PaginationDto paginationDto){
		return favoriteService.paginationFavorite(paginationDto);
	}
}