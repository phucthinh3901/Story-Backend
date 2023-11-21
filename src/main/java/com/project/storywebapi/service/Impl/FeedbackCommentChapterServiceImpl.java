package com.project.storywebapi.service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.storywebapi.entities.CommentChapter;
import com.project.storywebapi.entities.FeedbackCommentChapter;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.DeleteFeedbackRequest;
import com.project.storywebapi.payload.request.FeedbackCommentRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.CommentChapterRepository;
import com.project.storywebapi.repository.FeedbackCommentChapterRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.FeedbackCommentChapterService;

@Service
public class FeedbackCommentChapterServiceImpl implements FeedbackCommentChapterService{
	
	@Autowired 
	UserRepository userRepository;

	@Autowired
	FeedbackCommentChapterRepository feedbackCommentChapterRepository;
	
	@Autowired
	CommentChapterRepository commentChapterRepository;
	
	@Override
	public ApiResponse createOrUpdate(FeedbackCommentRequest feedbackCommentRequest) {
		ApiResponse apiResponse = new ApiResponse();
		FeedbackCommentChapter feedbackCommentChapter = null;
		
		User user = userRepository.findByUserIdAndIsDeleteFalse(feedbackCommentRequest.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy người dùng");
			return apiResponse;
		}	
		CommentChapter CommentChapter = commentChapterRepository.findById(feedbackCommentRequest.getParentId()).orElse(null);
		if(CommentChapter == null) {
			apiResponse.setMessage("Không tìm thấy comment cha.");
			return apiResponse;
		}
		if(feedbackCommentRequest.getId() != null) {
			feedbackCommentChapter = feedbackCommentChapterRepository.findByIdFeedbackChapterAndIsDeleteFalse(feedbackCommentRequest.getId());			
			feedbackCommentChapter.setUpdatedBy(user.getId());
			feedbackCommentChapter.setUpdatedAt(new Date());
		}
		else {	
			feedbackCommentChapter = new FeedbackCommentChapter();	
			feedbackCommentChapter.setCreatedBy(feedbackCommentRequest.getUserId());
			feedbackCommentChapter.setCreatedAt(new Date());	
		}
		feedbackCommentChapter.setUserId(user);
		feedbackCommentChapter.setContent(feedbackCommentRequest.getContent());
		feedbackCommentChapter.setIsDeleted(false);
		feedbackCommentChapter.setCommentChapter(CommentChapter);
		feedbackCommentChapterRepository.save(feedbackCommentChapter);
		
		apiResponse.setData(feedbackCommentChapter);
		apiResponse.setMessage("Xữ lí dữ liệu thành công!.");
		apiResponse.setSuccess(true);
		
		return apiResponse;
	}

	@Override
	public ApiResponse deleteFeedback(DeleteFeedbackRequest deleteRequest) {
		ApiResponse apiResponse = new ApiResponse();	
		User user = userRepository.findById(deleteRequest.getUserId()).orElse(null);
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!.");
			return apiResponse;
		}
		FeedbackCommentChapter feedbackCommentChapter = feedbackCommentChapterRepository.findByIdFeedbackChapterAndIsDeleteFalse(deleteRequest.getId());
		if(feedbackCommentChapter == null) {
			apiResponse.setMessage("Không tìm thấy phản hồi.");
			return apiResponse;
		}
		feedbackCommentChapterRepository.save(feedbackCommentChapter);
		apiResponse.setData(feedbackCommentChapter);
		apiResponse.setMessage("Xoá dữ liệu thành công");
		apiResponse.setSuccess(true);
		return null;
	}

	
}
