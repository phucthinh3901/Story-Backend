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

import com.project.storywebapi.dto.FilterOneDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationHistoryDto;
import com.project.storywebapi.entities.Chapter;
import com.project.storywebapi.entities.History;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.CreateAndUpdateHistoryRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.ChapterRepository;
import com.project.storywebapi.repository.HistoryRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService{

	@Autowired
	ChapterRepository chapterRepository;
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ApiResponse createOrUpdateHistory(CreateAndUpdateHistoryRequest createAndUpdateHistoryRequest) {
		ApiResponse apiResponse = new ApiResponse();
		History history = null;
		
		
		Chapter chapter = chapterRepository.findById(createAndUpdateHistoryRequest.getChapterId()).orElse(null);	
		if(chapter == null) {
			apiResponse.setMessage("Không tìm thấy chapter");
			return apiResponse;
		}
			
		User user = userRepository.findById(createAndUpdateHistoryRequest.getUserId()).orElse(null);		
		history = historyRepository.findByUserIdAndChapterStoryId(chapter.getId(),createAndUpdateHistoryRequest.getUserId());		
		if(history != null) {
			history.setChapterId(chapter);
			history.setUpdatedAt(new Date());
			history.setUserId(history.getUserId());
			history.setIsDeleted(false);
			historyRepository.save(history);	
			apiResponse.setData(history);
			apiResponse.setMessage("Cập nhật lịch sử thành công.");
			apiResponse.setSuccess(true);
			return apiResponse;
		}
		
		history = new History();
		history.setCreatedBy(user.getId());
		history.setChapterId(chapter);
		history.setUpdatedAt(new Date());
		history.setUserId(history.getUserId());	
		history.setIsDeleted(false);
		historyRepository.save(history);	
		
		apiResponse.setData(history);
		apiResponse.setMessage("Cập nhật lịch sử thành công.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	@Override
	public ApiResponse deleteHistory(FilterOneDto filterOneDto) {
		
		ApiResponse apiResponse = new ApiResponse();
		
		History history = historyRepository.findByUserIdAndChapterStoryId(filterOneDto.getId(), filterOneDto.getUserId());
		if(history == null) {
			apiResponse.setMessage("Dữ liệu không được tìm thây!.");
			return apiResponse;
		}
		historyRepository.delete(history);
		apiResponse.setData(history);
		apiResponse.setMessage("Xoá yêu thích thành công!.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	public Page<PaginationHistoryDto> convertListToPage(List<PaginationHistoryDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;

	    List<PaginationHistoryDto> pageList;

	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}
	
	@Override
	public Page<PaginationHistoryDto> paginationHistory(PaginationDto request) {
		
		
		HistorySpecificationImpl specification = new HistorySpecificationImpl(request);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
		Page<History> result = historyRepository.findAll(specification ,pageable);
		
		List<PaginationHistoryDto> paginationHistoryDtos = new ArrayList<PaginationHistoryDto>();
		Chapter chapter = null; 
		PaginationHistoryDto dto = null;
		
		for(History history : result.getContent()) {
			chapter = chapterRepository.findChapterByHistory(history.getId());
			dto = new PaginationHistoryDto();
			dto.setChapterName(chapter.getChapterNumber());
			dto.setChapterId(chapter.getId());
			dto.setCreateAt(history.getCreatedAt());
			dto.setCreateBy(history.getCreatedBy());
			dto.setIsDelete(chapter.getIsDeleted());
			dto.setStoryAvatar(chapter.getStoryId().getAvatar());
			dto.setTypeName(chapter.getStoryId().getType().toString());
			dto.setUpdateAt(history.getUpdatedAt());
			dto.setStoryName(chapter.getStoryId().getName());
			dto.setStoryId(chapter.getStoryId().getId());
			paginationHistoryDtos.add(dto);
		}
		Page<PaginationHistoryDto> page = convertListToPage(paginationHistoryDtos, pageable);
		return page;
	}
}
