package com.project.storywebapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>{

	@Query("SELECT c FROM Category c where c.name =: nameCate")
	List<Category> findByNameCategory(@Param("nameCate") String name);

	List<Category> findByName(String name);
	
	@Query("SELECT c FROM Category c where c.id in (:idCate) and c.isDeleted = false")
	List<Category> findNameCategoryById(List<Integer> idCate);
}
