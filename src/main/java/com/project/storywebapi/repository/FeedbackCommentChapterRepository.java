package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.FeedbackCommentChapter;

public interface FeedbackCommentChapterRepository extends JpaRepository<FeedbackCommentChapter, Integer>{

	@Query("From FeedbackCommentChapter fc where fc.id = :id and fc.isDeleted = false")
	FeedbackCommentChapter findByIdFeedbackChapterAndIsDeleteFalse(@Param("id") Integer id);
}
