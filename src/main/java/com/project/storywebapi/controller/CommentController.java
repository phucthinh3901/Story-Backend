package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.PaginationCommentDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.payload.request.ActiveRequestComment;
import com.project.storywebapi.payload.request.CreateOrUpdateComment;
import com.project.storywebapi.payload.request.DeleteRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@PostMapping("/create_data")
	public ApiResponse createComment(@RequestBody CreateOrUpdateComment createOrUpdateComment){
		return commentService.createOrUpdateComment(createOrUpdateComment);
	}
	
	@PostMapping("/active_data")
	public ApiResponse updateComment(@RequestBody ActiveRequestComment activeRequestComment) {
		return commentService.activeUpdate(activeRequestComment);
	}
	
	@PostMapping("/delete_data")
	public ApiResponse deleteComment(@RequestBody DeleteRequest deleteRequest) {
		return commentService.deleteComment(deleteRequest);
	}
	
	
	@PostMapping("/pagination")
	public Page<PaginationCommentDto> paginationChapter(@RequestBody PaginationDto paginationDto){
		return commentService.paginationCommentStory(paginationDto);
	}
}
