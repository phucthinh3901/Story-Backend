package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment>{

	@Query("From Comment c where c.id = :id and c.isDeleted = false")
	Comment findByIdAndIsDeletedFalse(@Param("id") Integer id);
	
	@Query("From Comment c where c.id = :id and c.isDeleted = :newIdDelete")
	Comment findByIdAndIsDelete(@Param("id") Integer id, @Param("newIdDelete") Boolean newIdDelete);
	
	@Query("From Comment c where c.userId.id = :userId and c.storyId.id = :storyId and c.isDeleted = false")
	Comment findByIdStoryAndUserIdAndIsDeleteFalse(@Param("userId") Integer userId, @Param("storyId") Integer storyId);
}
