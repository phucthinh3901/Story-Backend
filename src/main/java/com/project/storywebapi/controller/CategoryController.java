package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.entities.Category;
import com.project.storywebapi.payload.request.CaterogyRequest;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.request.UpdateCategory;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.CategoriesService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoriesService categoriesService;
	
	@PostMapping("/create_data")
	public ApiResponse addCategory(@RequestBody CaterogyRequest add) {
		return categoriesService.addCategory(add);
	}
	@PostMapping("/update_data")
	public ApiResponse updateCategory(@RequestBody UpdateCategory updateCate) {
		return categoriesService.updateCategory(updateCate);
	}
	@PostMapping("/pagination")
	public Page<Category> paginationCategory(@RequestBody PaginationDto request) {
		return categoriesService.paginationCategory(request);
	}
	@PostMapping("/update_active")
	public ApiResponse updateActive(@RequestBody ActiveRequest delCate) {
		return categoriesService.deleteCategory(delCate);
	}
	
}
