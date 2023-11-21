package com.project.storywebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.Images;

public interface ImagesRepository extends JpaRepository<Images, Integer>{

	@Query("From Images i, Chapter c where c.id = :id and i.isDeleted = :isDelete and i.chapterId.id = c.id ")
	List<Images> findByImageIdAndIsDelete(@Param("id") Integer chapterId, @Param("isDelete") Boolean isDelete);
}
