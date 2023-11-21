package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.payload.request.DeleteFeedbackRequest;
import com.project.storywebapi.payload.request.FeedbackCommentRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.service.FeedbackCommentService;

@RestController
@RequestMapping("/feedbackComment")
public class FeedbackCommentController {

	@Autowired
	FeedbackCommentService feedbackCommentService;
	
	@RequestMapping("/createOrUpdate")
	public ApiResponse createOrUpdateFeedbackComment(@RequestBody FeedbackCommentRequest feedbackCommentRequest) {
		return feedbackCommentService.createOrUpdate(feedbackCommentRequest);
	}
	
	@RequestMapping("/deleteFeedback")
	public ApiResponse deleteFeedbackComment(@RequestBody DeleteFeedbackRequest deleteRequest) {
		return feedbackCommentService.deleteFeedback(deleteRequest);
	}
}
