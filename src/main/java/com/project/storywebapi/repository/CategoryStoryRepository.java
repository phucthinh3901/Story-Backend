package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.storywebapi.entities.Category_Story;

public interface CategoryStoryRepository extends JpaRepository<Category_Story, Integer>{

//	@Query("select * from category_story cs where cs.storyId =: id")
//	List<Category_Story> findCategoryId(@Param("id") Integer id);
}
