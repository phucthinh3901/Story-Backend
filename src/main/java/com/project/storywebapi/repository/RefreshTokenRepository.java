package com.project.storywebapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import com.project.storywebapi.entities.RefreshToken;
import com.project.storywebapi.entities.User;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer>{
	Optional<RefreshToken> findByToken(String token);
	
	RefreshToken findByUserId(Integer userId);

	@Modifying
	int deleteByUser(User user);
}
