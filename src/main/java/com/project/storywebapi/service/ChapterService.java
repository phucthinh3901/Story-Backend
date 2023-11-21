package com.project.storywebapi.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.project.storywebapi.dto.ChapterCreateDto;
import com.project.storywebapi.dto.ChapterUpdataDto;
import com.project.storywebapi.dto.FilterOneDto;
import com.project.storywebapi.dto.PaginationChapterDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.payload.request.ActiveChapterRequest;
import com.project.storywebapi.payload.response.ApiResponse;

public interface ChapterService {

	ApiResponse createData(MultipartFile[] files ,ChapterCreateDto chapterCreateDto);
	
	ApiResponse updateData(ChapterUpdataDto chapterUpdataDto);
	
	ApiResponse update_active(ActiveChapterRequest filter);
	
	ApiResponse getChapterByStory(FilterOneDto filter);
	
	ApiResponse getChapterById(FilterOneDto filterOneDto);
	
	ApiResponse plusViewChapter(FilterOneDto filterOneDto);
	
	ApiResponse getPrintChapter(FilterOneDto filterOneDto);
	
	Page<PaginationChapterDto> paginationChapter(PaginationDto paginationDto);
}
