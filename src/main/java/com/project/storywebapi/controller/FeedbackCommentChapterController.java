package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.payload.request.DeleteFeedbackRequest;
import com.project.storywebapi.payload.request.FeedbackCommentRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.FeedbackCommentChapterService;

@RestController
@RequestMapping("/feedbackChapter")
public class FeedbackCommentChapterController {

	@Autowired
	FeedbackCommentChapterService feedbackCommentChapterService; 
	
	@PostMapping("/createOrUpdate")
	public ApiResponse createOrUpdate(@RequestBody FeedbackCommentRequest feedbackCommentRequest) {
		return feedbackCommentChapterService.createOrUpdate(feedbackCommentRequest);
	}
	@PostMapping("/deleteFeedback")
	public ApiResponse deleteFeedbackChapter(@RequestBody DeleteFeedbackRequest deleteRequest) {
		return feedbackCommentChapterService.deleteFeedback(deleteRequest);
	}
	
	
}
