package com.project.storywebapi.service;

import org.springframework.data.domain.Page;

import com.project.storywebapi.dto.PaginationCommentDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.payload.request.ActiveRequestComment;
import com.project.storywebapi.payload.request.CreateOrUpdateComment;
import com.project.storywebapi.payload.request.DeleteRequest;
import com.project.storywebapi.payload.response.ApiResponse;

public interface CommentService {

	ApiResponse createOrUpdateComment(CreateOrUpdateComment commentCreateDto);
	
	ApiResponse activeUpdate(ActiveRequestComment activeRequest);
	
	ApiResponse deleteComment(DeleteRequest deletRequest);
	
	Page<PaginationCommentDto> paginationCommentStory(PaginationDto request);
	
}
