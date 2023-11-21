package com.project.storywebapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.storywebapi.entities.User;
public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User>{

	Optional<User> findByUsername(String usename);
	
	@Query("SELECT user FROM User user where user.id = :userId")
	User findByUserId(@Param("userId") Integer id);
	
	@Query("From User u where u.id = :userId and u.isDeleted = false")
	User findByUserIdAndIsDeleteFalse(@Param("userId") Integer userId);
}
