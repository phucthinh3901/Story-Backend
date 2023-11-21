package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.FeedbackComment;

public interface FeedBackCommentStoryRepository extends JpaRepository<FeedbackComment, Integer>{
	
	@Query("FROM FeedbackComment fb where fb.id = :feedbackId and fb.isDeleted = false")
	FeedbackComment findByFeedbackIdAndIsDeleteFalse(@Param("feedbackId") Integer id);
	
	@Query("FROM FeedbackComment fb where fb.id = :feedbackId and fb.userId.id = :userId and fb.isDeleted = false")
	FeedbackComment findByFeedbackIdAndUserIdAndIsDeleteFalse(@Param("feedbackId") Integer feedbackId,@Param("userId") Integer userId);
}
