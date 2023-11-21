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

import com.project.storywebapi.dto.PaginationChildrenCommentChapterDto;
import com.project.storywebapi.dto.PaginationChildrenCommentDto;
import com.project.storywebapi.dto.PaginationCommentChapterDto;
import com.project.storywebapi.dto.PaginationCommentDto;
import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.entities.Chapter;
import com.project.storywebapi.entities.CommentChapter;
import com.project.storywebapi.entities.FeedbackCommentChapter;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.CreateOrUpdateCommentChapter;
import com.project.storywebapi.payload.request.DeleteRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.repository.ChapterRepository;
import com.project.storywebapi.repository.CommentChapterRepository;
import com.project.storywebapi.repository.FeedbackCommentChapterRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.service.CommentChapterService;

@Service
public class CommentChapterServiceImpl implements CommentChapterService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	ChapterRepository chapterRepository;

	@Autowired
	CommentChapterRepository commentChapterRepository;
	
	@Autowired
	FeedbackCommentChapterRepository feedbackCommentChapterRepository;
	
	@Override
	public ApiResponse createOrUpdate(CreateOrUpdateCommentChapter createOrUpdateComment) {
		ApiResponse apiResponse = new ApiResponse();
		
		CommentChapter commentChapter = null;

		User user = userRepository.findByUserIdAndIsDeleteFalse(createOrUpdateComment.getUserId());
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy người dùng");
			return apiResponse;
		}
		Chapter chapter = chapterRepository.findChapterByIdAndIsDelete(createOrUpdateComment.getChapterId());
		if(chapter == null) {
			apiResponse.setMessage("Không tìm thấy chương này!.");
			return apiResponse;
		}
		if(createOrUpdateComment.getId() != null) {
			commentChapter = commentChapterRepository.findByIdAndIsDeletedFalse(createOrUpdateComment.getId());	
			commentChapter.setUpdatedBy(user.getId());
			commentChapter.setUpdatedAt(new Date());
		}
		else {
			commentChapter = new CommentChapter();	
			commentChapter.setCreatedBy(createOrUpdateComment.getUserId());
			commentChapter.setCreatedAt(new Date());	
		}
		commentChapter.setUserId(user);
		commentChapter.setChapter(chapter);
		commentChapter.setContent(createOrUpdateComment.getContent());
		commentChapter.setIsDeleted(false);
		
		commentChapterRepository.save(commentChapter);
		
		apiResponse.setData(commentChapter);
		apiResponse.setMessage("Thêm mới thành công.");
		apiResponse.setSuccess(true);
		return apiResponse;
	}

	@Override
	public ApiResponse deleteCommentChapter(DeleteRequest deleteRequest) {
		
		ApiResponse apiResponse = new ApiResponse();	
		User user = userRepository.findById(deleteRequest.getUserId()).orElse(null);
		if(user == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!.");
			return apiResponse;
		}
		
		CommentChapter commentChapter = commentChapterRepository.findByCommentIdAndUserIdAndIsDeleteFalse(deleteRequest.getId(),user.getId());
		if(commentChapter == null) {
			apiResponse.setMessage("Không tìm thấy dữ liệu!.");
			return apiResponse;
		}
		for(FeedbackCommentChapter feedbackCommentChapter :  commentChapter.getFeedbackCommentChapters()) {
			feedbackCommentChapterRepository.delete(feedbackCommentChapter);
			apiResponse.setData(feedbackCommentChapter);
		}
		commentChapterRepository.delete(commentChapter);
		apiResponse.setData(commentChapter);
		apiResponse.setMessage("Xoá dữ liệu thành công");
		apiResponse.setSuccess(true);
		
		return apiResponse;
	}
	public Page<PaginationCommentChapterDto> convertListToPage(List<PaginationCommentChapterDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;

	    List<PaginationCommentChapterDto> pageList;

	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}

	@Override
	public Page<PaginationCommentChapterDto> paginationCommentChapter(PaginationDto request) {
	
		CommentChapterSpecificationImpl specificationImpl = new CommentChapterSpecificationImpl(request);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
		Page<CommentChapter> result = commentChapterRepository.findAll(specificationImpl, pageable);
		
		List<PaginationCommentChapterDto> commentChapterDtos = new ArrayList<PaginationCommentChapterDto>();
		PaginationCommentChapterDto dto = null;
		
		List<PaginationChildrenCommentChapterDto> childrenCommentChapterDtos = new ArrayList<PaginationChildrenCommentChapterDto>();
		PaginationChildrenCommentChapterDto childrenCommentDto = null;
		
		for(CommentChapter commentChapter : result.getContent()) {
			dto = new PaginationCommentChapterDto();
			dto.setContent(commentChapter.getContent());
			dto.setCreateAt(commentChapter.getCreatedAt());
			dto.setUpdateAt(commentChapter.getUpdatedAt());
			dto.setUpdateBy(commentChapter.getUpdatedBy());
			dto.setIsDelete(commentChapter.getIsDeleted());
			dto.setId(commentChapter.getId());
			dto.setChapterId(commentChapter.getChapter().getId());
			dto.setUserName(commentChapter.getUserId().getUsername());
			dto.setAvatarUser(commentChapter.getUserId().getAvatar());
			dto.setUserName(commentChapter.getUserId().getUsername());
			dto.setUserId(commentChapter.getUserId().getId());
			
			for(FeedbackCommentChapter feedbackCommentChapter : commentChapter.getFeedbackCommentChapters()) {
				childrenCommentDto = new PaginationChildrenCommentChapterDto();
				childrenCommentDto.setContent(feedbackCommentChapter.getContent());
				childrenCommentDto.setCreateAt(feedbackCommentChapter.getCreatedAt());
				childrenCommentDto.setCreateBy(feedbackCommentChapter.getCreatedBy());
				childrenCommentDto.setUpdateAt(feedbackCommentChapter.getUpdatedAt());
				childrenCommentDto.setUpdateBy(feedbackCommentChapter.getUpdatedBy());
				childrenCommentDto.setIsDelete(feedbackCommentChapter.getIsDeleted());
				childrenCommentDto.setChapterId(feedbackCommentChapter.getCommentChapter().getChapter().getId());
				childrenCommentDto.setParentId(feedbackCommentChapter.getCommentChapter().getId());
				childrenCommentDto.setAvatarUser(feedbackCommentChapter.getUserId().getAvatar());
				childrenCommentDto.setUserName(feedbackCommentChapter.getUserId().getUsername());
				childrenCommentDto.setUserId(feedbackCommentChapter.getUserId().getId());
				childrenCommentChapterDtos.add(childrenCommentDto);
			}
			dto.setChildrenCommentChapterDtos(childrenCommentChapterDtos);
			commentChapterDtos.add(dto);
		}
		Page<PaginationCommentChapterDto> page = convertListToPage(commentChapterDtos, pageable);
		return page;
	}
	
}
