package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.storywebapi.dto.PaginationChildrenCommentDto;
import com.project.storywebapi.dto.PaginationCommentDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.entities.Comment;
import com.project.storywebapi.entities.FeedbackComment;
import com.project.storywebapi.entities.Story;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.ActiveRequestComment;
import com.project.storywebapi.payload.request.CreateOrUpdateComment;
import com.project.storywebapi.payload.request.DeleteRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.CommentRepository;
import com.project.storywebapi.repository.FeedBackCommentStoryRepository;
import com.project.storywebapi.repository.StoryRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StoryRepository storyRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	FeedBackCommentStoryRepository feedBackCommentStoryRepository;
	
	@Override
	public ApiResponse createOrUpdateComment(CreateOrUpdateComment createOrUpdateComment) {
		
		ApiResponse apiResponse = new ApiResponse();
		Comment comment = null;
		
		User user = userRepository.findByUserIdAndIsDeleteFalse(createOrUpdateComment.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy người dùng");
			return apiResponse;
		}
		Story story = storyRepository.findOneByIdAndType(createOrUpdateComment.getStoryId());
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy truyện");
			return apiResponse;	
		}	
		if(createOrUpdateComment.getId() != null) {
			comment = commentRepository.findByIdAndIsDeletedFalse(createOrUpdateComment.getId());	
			comment.setUpdatedBy(user.getId());
			comment.setUpdatedAt(new Date());
		}
		else {
			comment = new Comment();	
			comment.setCreatedBy(createOrUpdateComment.getUserId());
			comment.setCreatedAt(new Date());	
		}
		comment.setUserId(user);
		comment.setStoryId(story);
		comment.setContent(createOrUpdateComment.getContent());
		comment.setIsDeleted(false);
		
		 commentRepository.save(comment);
		 
		 apiResponse.setData(comment);
		 apiResponse.setMessage("Thêm mới thành công.");
		 apiResponse.setSuccess(true);
		return apiResponse;
	}

	@Override
	public ApiResponse activeUpdate(ActiveRequestComment activeRequestComment ) {
		ApiResponse apiResponse = new ApiResponse();
		List<FeedbackComment> feedbackComments = new ArrayList<FeedbackComment>();
		Comment comment = commentRepository.findByIdAndIsDeletedFalse(activeRequestComment.getId());
		if(comment == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			apiResponse.setSuccess(false);
			return apiResponse;
		}
		comment.setIsDeleted(activeRequestComment.getIsDelete());	
		for(FeedbackComment feedbackComment : comment.getFeedbackComment()) {
			feedbackComment.setIsDeleted(activeRequestComment.getIsDelete());
			feedbackComments.add(feedbackComment);
			feedBackCommentStoryRepository.save(feedbackComment);
		}
		commentRepository.save(comment);
		apiResponse.setData(comment);
		apiResponse.setData(feedbackComments);
		apiResponse.setMessage("Sửa dữ liệu thành công");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	@Override
	public ApiResponse deleteComment(DeleteRequest deleteRequest) {	
		ApiResponse apiResponse = new ApiResponse();	
		User user = userRepository.findById(deleteRequest.getUserId()).orElse(null);
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!.");
			return apiResponse;
		}
		Story story = storyRepository.findById(deleteRequest.getId()).orElse(null);	
		if(story == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!.");
			return apiResponse;			
		}
		Comment comment = commentRepository.findByIdStoryAndUserIdAndIsDeleteFalse(user.getId(), story.getId());
		if(comment == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!");
			return apiResponse;
		}
		for(FeedbackComment feedbackComment : comment.getFeedbackComment()) {
			feedBackCommentStoryRepository.delete(feedbackComment);
		}
		commentRepository.delete(comment);
		apiResponse.setData(comment);
		apiResponse.setMessage("Xoá dữ liệu thành công");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	
	public Page<PaginationCommentDto> convertListToPage(List<PaginationCommentDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;

	    List<PaginationCommentDto> pageList;

	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}

	@Override
	public Page<PaginationCommentDto> paginationCommentStory(PaginationDto request) {
		CommentSpecificationImpl specification = new CommentSpecificationImpl(request);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
		Page<Comment> result = commentRepository.findAll(specification, pageable);
		
		PaginationCommentDto dto = null;
		List<PaginationCommentDto> paginationCommentDtos = new ArrayList<PaginationCommentDto>();
		List<PaginationChildrenCommentDto> childrenCommentDtos = new ArrayList<PaginationChildrenCommentDto>();
		PaginationChildrenCommentDto childrenCommentDto = null;
		for(Comment comment : result.getContent()) {
			dto = new PaginationCommentDto();
			dto.setContent(comment.getContent());
			dto.setCreateAt(comment.getCreatedAt());
			dto.setUpdateAt(comment.getUpdatedAt());
			dto.setUpdateBy(comment.getUpdatedBy());
			dto.setIsDelete(comment.getIsDeleted());
			dto.setId(comment.getId());
			dto.setStoryId(comment.getStoryId().getId());
			dto.setUserName(comment.getUserId().getUsername());
			dto.setAvatarUser(comment.getUserId().getAvatar());
			dto.setUserName(comment.getUserId().getUsername());
			dto.setUserId(comment.getUserId().getId());
			
			for(FeedbackComment feedbackComment : comment.getFeedbackComment()) {
				childrenCommentDto = new PaginationChildrenCommentDto();
				childrenCommentDto.setContent(feedbackComment.getContent());
				childrenCommentDto.setCreateAt(feedbackComment.getCreatedAt());
				childrenCommentDto.setCreateBy(feedbackComment.getCreatedBy());
				childrenCommentDto.setUpdateAt(feedbackComment.getUpdatedAt());
				childrenCommentDto.setUpdateBy(feedbackComment.getUpdatedBy());
				childrenCommentDto.setIsDelete(feedbackComment.getIsDeleted());
				childrenCommentDto.setStoryId(feedbackComment.getFeedbackId().getStoryId().getId());
				childrenCommentDto.setParentId(feedbackComment.getFeedbackId().getId());
				childrenCommentDto.setAvatarUser(feedbackComment.getUserId().getAvatar());
				childrenCommentDto.setUserName(feedbackComment.getUserId().getUsername());
				childrenCommentDto.setUserId(feedbackComment.getUserId().getId());
				childrenCommentDtos.add(childrenCommentDto);
			}
			dto.setChildrenComments(childrenCommentDtos);
			paginationCommentDtos.add(dto);
			
		}
		Page<PaginationCommentDto> page = convertListToPage(paginationCommentDtos, pageable);
		return page;
	}

}
