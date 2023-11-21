package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.History;

public interface HistoryRepository extends JpaRepository<History, Integer>, JpaSpecificationExecutor<History>{
	
	@Query("FROM History h where h.chapterId.id = :chapterId and h.userId.id = :userId")
	History findByUserIdAndChapterStoryId(@Param("chapterId") Integer chapterId, @Param("userId") Integer userId);

}
