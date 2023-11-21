package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.Story;

@EnableJpaRepositories
public interface StoryRepository extends JpaRepository<Story, Integer>,JpaSpecificationExecutor<Story>{

	@Query("FROM Story where name = :nameStory and type = :typeStory")
	Story findOneByNameAndType(@Param("nameStory") String nameStory, @Param("typeStory") String typeStory);
	
	@Query("FROM Story s where s.id = :id and s.isDeleted = false")
	Story findOneByIdAndType(@Param("id") Integer id);
	
	@Query("Select s from Favorite f join Story s on f.storyId.id = s.id where f.id = :id")
	Story findStoryByFavorite(@Param("id") Integer id);
	
	
}
