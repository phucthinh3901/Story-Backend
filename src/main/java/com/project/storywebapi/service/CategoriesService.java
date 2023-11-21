package com.project.storywebapi.service;

import org.springframework.data.domain.Page;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.entities.Category;
import com.project.storywebapi.payload.request.CaterogyRequest;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.request.UpdateCategory;
import com.project.storywebapi.payload.response.ApiResponse;


public interface CategoriesService {
 
	ApiResponse addCategory(CaterogyRequest add);
	
	ApiResponse updateCategory(UpdateCategory updateCate);
	
	ApiResponse deleteCategory(ActiveRequest delCate);
	
	Page<Category> paginationCategory(PaginationDto request);
	

}
