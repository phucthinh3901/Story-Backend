package com.project.storywebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.Chapter;

@EnableJpaRepositories
public interface ChapterRepository extends JpaRepository<Chapter, Integer>, JpaSpecificationExecutor<Chapter>{

	@Query("FROM Chapter c where c.chapterNumber = :chapterNumber and c.storyId.id = :storyId")
	Chapter findByChapterNumberAndStoryId(@Param("chapterNumber") Float chapterNumber, @Param("storyId") Integer storyId);
	
	@Query("FROM Chapter c where c.isDeleted = False and c.storyId.id = :storyId")
	List<Chapter> findChaptersByStoryIdAndIsDeleteFalseAndNumChapterAsc(@Param("storyId") Integer storyId);
	
	@Query("FROM Chapter c where c.isDeleted = False and c.id = :id")
	Chapter findChapterByIdAndIsDelete(@Param("id") Integer id);

	@Query(nativeQuery = true ,value = "Select c.* from  chapter c join history hs on c.id = hs.chapter_id where hs.chapter_id = :id")
	Chapter findChapterByHistory(@Param("id") Integer id);
}