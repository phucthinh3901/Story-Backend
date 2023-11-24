package com.project.storywebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationUserDto;
import com.project.storywebapi.payload.request.ChangePasswordRequest;
import com.project.storywebapi.payload.request.LoginRequest;
import com.project.storywebapi.payload.request.SignupRequest;
import com.project.storywebapi.payload.request.TokenRefreshRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.payload.response.JwtResponse;
import com.project.storywebapi.payload.response.TokenRefreshResponse;
import com.project.storywebapi.service.UserServices;
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserServices userServices;
	
	@PostMapping("/auth/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
		final JwtResponse result = userServices.signIn(loginRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	@PostMapping("/auth/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		final ApiResponse result = userServices.signUp(signUpRequest);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	@PostMapping("/auth/refreshtoken")
	public ResponseEntity<TokenRefreshResponse> refreshtoken(@RequestBody TokenRefreshRequest request) {
		final TokenRefreshResponse result = userServices.getRefreshtoken(request);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	@PostMapping("/pagination")
	public Page<PaginationUserDto> paginationUser(@RequestBody PaginationDto paginationDto){
		return userServices.paginationUser(paginationDto);
	}
	@PostMapping("/changePassword")
	public ApiResponse changeUserPassword(@RequestBody ChangePasswordRequest changePasswordRequest ){
		return userServices.changeUserPassword(changePasswordRequest);
	}
}
