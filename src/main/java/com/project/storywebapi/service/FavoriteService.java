package com.project.storywebapi.service;

import org.springframework.data.domain.Page;

import com.project.storywebapi.dto.FavoriteCreateDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PanaginationFavoriteDto;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.response.ApiResponse;

public interface FavoriteService {
	
	ApiResponse createFavorite(FavoriteCreateDto createDto);
	
	ApiResponse activeFavorite(ActiveRequest request);
	
	Page<PanaginationFavoriteDto> paginationFavorite(PaginationDto request);
}
