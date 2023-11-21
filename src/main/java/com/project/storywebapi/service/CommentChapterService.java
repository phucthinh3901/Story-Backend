package com.project.storywebapi.service;

import org.springframework.data.domain.Page;

import com.project.storywebapi.dto.PaginationCommentChapterDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.payload.request.CreateOrUpdateCommentChapter;
import com.project.storywebapi.payload.request.DeleteRequest;
import com.project.storywebapi.payload.response.ApiResponse;

public interface CommentChapterService {

	ApiResponse createOrUpdate(CreateOrUpdateCommentChapter createOrUpdateComment);
	
	ApiResponse deleteCommentChapter(DeleteRequest deleteRequest);
	
	Page<PaginationCommentChapterDto> paginationCommentChapter(PaginationDto request);
}
