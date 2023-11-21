package com.project.storywebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>,JpaSpecificationExecutor<Favorite>{

	@Query("FROM Favorite where storyId.id = :id and userId.id = :userId ")
	List<Favorite> findByIdAndUser(@Param("id") Integer id, @Param("userId") Integer userId);
	
	@Query("DELETE Favorite where storyId.id = :id and userId.id = :userId")
	List<Favorite>deleteFavoriteByStoryIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
	
	
	
}
