package com.project.storywebapi.service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.storywebapi.entities.Comment;
import com.project.storywebapi.entities.FeedbackComment;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.DeleteFeedbackRequest;
import com.project.storywebapi.payload.request.FeedbackCommentRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.CommentRepository;
import com.project.storywebapi.repository.FeedBackCommentStoryRepository;
import com.project.storywebapi.repository.StoryRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.FeedbackCommentService;

@Service
public class FeedbackCommentServiceImpl implements FeedbackCommentService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StoryRepository storyRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	FeedBackCommentStoryRepository feedBackCommentStoryRepository;
	
	
	@Override
	public ApiResponse createOrUpdate(FeedbackCommentRequest feedbackCommentRequest) {
				
		ApiResponse apiResponse = new ApiResponse();
		FeedbackComment feedbackComment = null;
		
		User user = userRepository.findByUserIdAndIsDeleteFalse(feedbackCommentRequest.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy người dùng");
			return apiResponse;
		}	
//		Story story = storyRepository.findOneByIdAndType(feedbackCommentRequest.getStoryId());
//		if(story == null) {
//			apiResponse.setMessage("Không tìm thấy truyện");
//			return apiResponse;	
//		}	
		Comment comment = commentRepository.findById(feedbackCommentRequest.getParentId()).orElse(null);
		if(comment == null) {
			apiResponse.setMessage("Không tìm thấy bình luận cha");
			return apiResponse;
		}
		if(feedbackCommentRequest.getId() != null) {
			feedbackComment = feedBackCommentStoryRepository.findByFeedbackIdAndIsDeleteFalse(feedbackCommentRequest.getId());			
			feedbackComment.setUpdatedBy(user.getId());
			feedbackComment.setUpdatedAt(new Date());
		}
		else {
			
			feedbackComment = new FeedbackComment();	
			feedbackComment.setCreatedBy(feedbackCommentRequest.getUserId());
			feedbackComment.setCreatedAt(new Date());	
		}
		feedbackComment.setUserId(user);
		feedbackComment.setContent(feedbackCommentRequest.getContent());
		feedbackComment.setIsDeleted(false);
		feedbackComment.setFeedbackId(comment);
		feedBackCommentStoryRepository.save(feedbackComment);
		apiResponse.setData(feedbackComment);
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
		FeedbackComment feedbackComment = feedBackCommentStoryRepository.findByFeedbackIdAndUserIdAndIsDeleteFalse(deleteRequest.getId(),user.getId());
		if(feedbackComment == null) {
			apiResponse.setMessage("Không tìm thấy phản hồi.");
			return apiResponse;
		}
		feedBackCommentStoryRepository.delete(feedbackComment);
		
		apiResponse.setData(feedbackComment);
		apiResponse.setMessage("Xoá dữ liệu thành công");
		apiResponse.setSuccess(true);
		return apiResponse;
	}


	

	
}
