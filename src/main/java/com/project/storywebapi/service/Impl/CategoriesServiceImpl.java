package com.project.storywebapi.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.entities.Category;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.request.CaterogyRequest;
import com.project.storywebapi.payload.request.UpdateCategory;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.CategoryRepository;
import com.project.storywebapi.service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService{

	@Autowired
	private CategoryRepository categoryRepository;
	
		
	@Override
	public ApiResponse addCategory(CaterogyRequest addCate) {
		Category add = new Category();
		add.setName(addCate.getName());
		add.setDescription(addCate.getDescription());
		add.setCreatedAt(new Date());
		add.setIsDeleted(false);
		categoryRepository.save(add);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setData(add);
		apiResponse.setMessage("Thêm mới thành công");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	@Override
	public ApiResponse updateCategory(UpdateCategory updateCate) {
		ApiResponse apiResponse = new ApiResponse();
		
		Category updateCategory = categoryRepository.findById(updateCate.getId()).orElse(null);
		if(updateCategory == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		if(updateCategory.getName() != updateCate.getName()) {
			List<Category> listName = categoryRepository.findByName(updateCate.getName());
			if(listName.isEmpty()) {
				updateCategory.setDescription(updateCate.getDescription());
				updateCategory.setName(updateCate.getName());
				categoryRepository.save(updateCategory);
				apiResponse.setData(updateCategory);
				apiResponse.setMessage("Cập nhật thành công.");
				apiResponse.setSuccess(true);
			}		
			else {
				apiResponse.setMessage("Tên đã được sử dụng!");
				apiResponse.setSuccess(false);
			}
			return apiResponse;
		}
		return null;
	}

	@Override
	public ApiResponse deleteCategory(ActiveRequest delCate) {
		
		ApiResponse apiResponse = new ApiResponse();
		Category deleCate = categoryRepository.findById(delCate.getId()).orElse(null);
		if(deleCate == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		deleCate.setIsDeleted(true);
		categoryRepository.save(deleCate);
		apiResponse.setMessage("Cập nhật trạng thái thành công!");
		apiResponse.setData(deleCate);
		apiResponse.setSuccess(true);
		return apiResponse;
	}
	@Override
	public Page<Category> paginationCategory(PaginationDto request){
		
		CategorySpecificationImpl specification = new CategorySpecificationImpl(request);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
		Page<Category> resutl = categoryRepository.findAll(specification, pageable);
		
		return resutl;
	}
}
