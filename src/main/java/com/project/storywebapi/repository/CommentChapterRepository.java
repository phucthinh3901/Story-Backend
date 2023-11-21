package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.CommentChapter;

public interface CommentChapterRepository extends JpaRepository<CommentChapter, Integer>, JpaSpecificationExecutor<CommentChapter>{

	@Query("FROM CommentChapter c where c.isDeleted = False and c.id = :id")
	CommentChapter findByIdAndIsDeletedFalse(@Param("id") Integer id);
	
	@Query("FROM CommentChapter cc where cc.id = :id and cc.userId.id = :userId and cc.isDeleted = False ")
	CommentChapter findByCommentIdAndUserIdAndIsDeleteFalse(@Param("id") Integer id, @Param("userId") Integer userId);
}
