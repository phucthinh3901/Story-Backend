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
import com.project.storywebapi.dto.ChapterCreateDto;
import com.project.storywebapi.dto.ChapterUpdataDto;
import com.project.storywebapi.dto.FilterOneDto;
import com.project.storywebapi.dto.PaginationChapterDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.payload.request.ActiveChapterRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.ChapterService;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

	@Autowired
	ChapterService chapterService;
	
	@PostMapping("/create_data")
	private ApiResponse createData(@RequestParam(value ="file", required = false) MultipartFile[] files ,@RequestParam(value ="data", required = false) String chapterCreateDto) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ApiResponse apiResponse = null;
		ChapterCreateDto dto = null;
		if(chapterCreateDto != null) {
			 dto = mapper.readValue(chapterCreateDto, ChapterCreateDto.class);	
		}
		apiResponse = chapterService.createData(files, dto);
		return new ApiResponse(true,"Complete Create",apiResponse);
	}
	
	@PostMapping("/update_data")
	private ApiResponse updateData(@RequestBody ChapterUpdataDto chapterUpdataDto) {
		return chapterService.updateData(chapterUpdataDto);
	}
	@PostMapping("/update_active")
	private ApiResponse update_active(@RequestBody ActiveChapterRequest filter ) {
		return chapterService.update_active(filter);
	}
	@PostMapping("/get_chapter_by_story")
	private ApiResponse getChapterByStory(@RequestBody FilterOneDto filterOneDto) {
		return chapterService.getChapterByStory(filterOneDto);
	}
	
	@PostMapping("get_chapter_by_id")
	private ApiResponse getChapterById(@RequestBody FilterOneDto filterOneDto) {
		return chapterService.getChapterById(filterOneDto);
	}

	@PostMapping("/plus_view_chapter")
	private ApiResponse plusViewChapter(@RequestBody FilterOneDto filterOneDto) {
		return chapterService.plusViewChapter(filterOneDto);
	}
	@PostMapping("/get_chapter_print")
	private ApiResponse getPrintChapter(@RequestBody FilterOneDto filterOneDto) {
		return chapterService.getPrintChapter(filterOneDto);
	}
	@PostMapping("/pagination")
	public Page<PaginationChapterDto> paginationChapter(@RequestBody PaginationDto paginationDto){
		return chapterService.paginationChapter(paginationDto);
	}
	
}
