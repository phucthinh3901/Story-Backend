package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.PaginationCommentChapterDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.payload.request.CreateOrUpdateCommentChapter;
import com.project.storywebapi.payload.request.DeleteRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.CommentChapterService;

@RestController
@RequestMapping("/commentChapter")
public class CommentChapterController {

	@Autowired
	CommentChapterService commentChapterService; 
	
	@PostMapping("/createOrUpdate")
	public ApiResponse createOrUpdate(@RequestBody CreateOrUpdateCommentChapter createOrUpdateComment) {
		return commentChapterService.createOrUpdate(createOrUpdateComment);
	}
	
	@PostMapping("/deleteCommentChapter")
	public ApiResponse deleteCommentChapter(@RequestBody DeleteRequest deleteRequest) {
		return commentChapterService.deleteCommentChapter(deleteRequest);
	}
	@PostMapping("/pagination")
	public Page<PaginationCommentChapterDto> paginationChapter(@RequestBody PaginationDto paginationDto){
		return commentChapterService.paginationCommentChapter(paginationDto);
	}
}
