package com.project.storywebapi.service;

import org.springframework.data.domain.Page;


import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationUserDto;
import com.project.storywebapi.dto.UserDto;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.ChangePasswordRequest;
import com.project.storywebapi.payload.request.LoginRequest;
import com.project.storywebapi.payload.request.SignupRequest;
import com.project.storywebapi.payload.request.TokenRefreshRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.payload.response.JwtResponse;
import com.project.storywebapi.payload.response.TokenRefreshResponse;

public interface UserServices {
		
	JwtResponse signIn(LoginRequest loginRequest);
	
	ApiResponse signUp(SignupRequest signUpRequest);
	
	TokenRefreshResponse getRefreshtoken(TokenRefreshRequest request);
	
	UserDto getUserByUserId(Integer userId);
	
	User getUserById(Integer userId);
	
	Page<PaginationUserDto> paginationUser(PaginationDto paginationDto);
	
	ApiResponse changeUserPassword(ChangePasswordRequest changePasswordRequest);
	
}
