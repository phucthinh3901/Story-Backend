package com.project.storywebapi.service;

import com.project.storywebapi.payload.request.DeleteFeedbackRequest;
import com.project.storywebapi.payload.request.FeedbackCommentRequest;
import com.project.storywebapi.payload.response.ApiResponse;

public interface FeedbackCommentChapterService {

	ApiResponse createOrUpdate(FeedbackCommentRequest feedbackCommentRequest);
	
	ApiResponse deleteFeedback(DeleteFeedbackRequest deleteRequest);
}
