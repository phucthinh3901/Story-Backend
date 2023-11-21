package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.storywebapi.dto.FavoriteCreateDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PanaginationFavoriteDto;
import com.project.storywebapi.entities.Favorite;
import com.project.storywebapi.entities.Story;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.ActiveRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.FavoriteRepository;
import com.project.storywebapi.repository.StoryRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService{

	@Autowired
	StoryRepository storyRepository;
	
	@Autowired
	FavoriteRepository favoriteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ApiResponse createFavorite(FavoriteCreateDto createDto) {
		ApiResponse apiResponse = new ApiResponse();
//		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		  User user = (User) authentication.getPrincipal();
		
		Story story = storyRepository.findOneByIdAndType(createDto.getStoryId());
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy truyện");
			return apiResponse;
		}		
		User user = userRepository.findById(createDto.getUserId()).orElse(null);	
		Favorite favorite = new Favorite();
		
		favorite.setCreatedAt(new Date());
		favorite.setIsDeleted(false);
		
		favorite.setStoryId(story);
		
		favorite.setCreatedBy(createDto.getUserId());
		favorite.setUserId(user);
	
		favoriteRepository.save(favorite);
		apiResponse.setData(favorite);	
		apiResponse.setMessage("Thêm mới thành công.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	@Override
	public ApiResponse activeFavorite(ActiveRequest request) {
		ApiResponse apiResponse = new ApiResponse();
		
		User user = userRepository.findById(request.getUserId()).orElse(null);
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu của người dùng!");
			return apiResponse;
		}
		Story story = storyRepository.findById(request.getId()).orElse(null);
		
		List<Favorite> favorite = favoriteRepository.findByIdAndUser(story.getId(),user.getId());
		if(favorite == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		apiResponse.setData(favorite);
		favoriteRepository.deleteAll(favorite);
		apiResponse.setMessage("Xoá yêu thích thành công.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	public Page<PanaginationFavoriteDto> convertListToPage(List<PanaginationFavoriteDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;

	    List<PanaginationFavoriteDto> pageList;

	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}
	
	@Override
	public Page<PanaginationFavoriteDto> paginationFavorite(PaginationDto request) {
		
		FavoriteSpecificationImpl specification = new FavoriteSpecificationImpl(request);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
		Page<Favorite> result = favoriteRepository.findAll(specification ,pageable);
		
		List<PanaginationFavoriteDto> storyDtos = new ArrayList<PanaginationFavoriteDto>();
		PanaginationFavoriteDto dto = null;	
		Story story = null;
		
		for(Favorite favorite : result.getContent()) {
			story =	storyRepository.findStoryByFavorite(favorite.getId());
			dto = new PanaginationFavoriteDto();
			dto.setId(story.getId());
			dto.setAvatar(story.getAvatar());
			dto.setIsDelete(story.getIsDeleted());	
			dto.setName(story.getName());
			dto.setAvatar(story.getAvatar());
			dto.setUserId(favorite.getUserId().getId());
			dto.setCreateAt(favorite.getCreatedAt());
			dto.setCreateBy(favorite.getCreatedBy());
			dto.setUpdateAt(favorite.getUpdatedAt());
			dto.setUpdateBy(favorite.getUpdatedBy());		
			storyDtos.add(dto);
		}
		Page<PanaginationFavoriteDto> page = convertListToPage(storyDtos,pageable);
		return page;
	}

}
