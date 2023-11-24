package com.project.storywebapi.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.project.storywebapi.dto.PaginationDto;
import com.project.storywebapi.dto.PaginationUserDto;
import com.project.storywebapi.dto.RoleUserDto;
import com.project.storywebapi.dto.UserDto;
import com.project.storywebapi.entities.RefreshToken;
import com.project.storywebapi.entities.Role;
import com.project.storywebapi.entities.User;
import com.project.storywebapi.payload.request.ChangePasswordRequest;
import com.project.storywebapi.payload.request.LoginRequest;
import com.project.storywebapi.payload.request.SignupRequest;
import com.project.storywebapi.payload.request.TokenRefreshRequest;
import com.project.storywebapi.payload.response.ApiResponse;
import com.project.storywebapi.payload.response.JwtResponse;
import com.project.storywebapi.payload.response.TokenRefreshResponse;
import com.project.storywebapi.repository.RoleRepository;
import com.project.storywebapi.repository.UserRepository;
import com.project.storywebapi.security.jwt.JwtUtils;
import com.project.storywebapi.security.service.RefreshTokenService;
import com.project.storywebapi.security.service.UserDetailsImpl;
import com.project.storywebapi.service.UserServices;


@Service
public class UserServiceImpl implements UserServices{
	@Autowired
	UserRepository usersRepository;

	@Autowired
	RoleRepository rolesRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;
		
	@Autowired
	ModelMapper mapper;
	
	
	@Override
	public JwtResponse signIn(LoginRequest loginRequest) {
		JwtResponse jwtResponse = new JwtResponse();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList()).get(0);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		RefreshToken refreshToken = refreshTokenService.getByUserId(userDetails.getId());
		if (refreshToken != null ) {
			refreshTokenService.deleteByUserId(userDetails.getId());
		}
		refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		
		jwtResponse.setId(userDetails.getId());
		jwtResponse.setEmail(userDetails.getEmail());
		jwtResponse.setUsername(userDetails.getUsername());
		jwtResponse.setRefreshToken(refreshToken.getToken());
		
		jwtResponse.setToken(jwt);
		jwtResponse.setRoles(roles);
		return jwtResponse;
	}
	
	@Override
	public ApiResponse signUp(SignupRequest signUpRequest) {
		User user = new User();
		ApiResponse response = new ApiResponse();
		user.setEmail(signUpRequest.getEmail());

		user.setUsername(signUpRequest.getUserName());
		user.setVerified(true);
		user.setCreatedAt(new Date());
		user.setIsDeleted(false);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));

		String strRoles = signUpRequest.getRoles();
		Role role = null;
		
		switch (strRoles) {
			case "admin":
				role = rolesRepository.findByName("Admin")
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				break;
			case "user":
				role = rolesRepository.findByName("User")
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				break;
			default:
				role = rolesRepository.findByName("User")
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		}
		
		user.setRoleId(role);
		user.setCreatedAt(new Date());
		usersRepository.save(user);
		response.setData(user);
		response.setMessage("Đăng ký thành công!");
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	@Override
	public TokenRefreshResponse getRefreshtoken(TokenRefreshRequest request) {
		TokenRefreshResponse refreshResponse = new TokenRefreshResponse();
		String requestRefreshToken = request.getRefreshToken();
		RefreshToken refreshToken = refreshTokenService.getByToken(requestRefreshToken).orElse(null);
		if (refreshToken != null) {
			refreshToken = refreshTokenService.verifyExpiration(refreshToken);
			User user = refreshToken.getUser();
			String token = jwtUtils.generateTokenFromUsername(user.getUsername());
			refreshResponse.setAccessToken(token);
			refreshResponse.setRefreshToken(refreshToken.getToken());
		}
		return refreshResponse;
	}
	
	@Override
	public User getUserById(Integer userId) {
		return usersRepository.findById(userId).orElse(null);
	}
	
	@Override
	public UserDto getUserByUserId(Integer userId) {
		UserDto result = new UserDto();
		User user = usersRepository.findByUserId(userId);
		result = mapper.map(user, UserDto.class);
		return result;
	}
	
	public Page<PaginationUserDto> convertListToPage(List<PaginationUserDto> entityList, Pageable pageable) {
	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int startItem = currentPage * pageSize;
	    List<PaginationUserDto> pageList;
	    if (entityList.size() < startItem) {
	        pageList = Collections.emptyList();
	    } else {
	        int toIndex = Math.min(startItem + pageSize, entityList.size());
	        pageList = entityList.subList(startItem, toIndex);
	    }

	    return new PageImpl<>(pageList, pageable, entityList.size());
	}
	@Override
	public Page<PaginationUserDto> paginationUser(PaginationDto paginationDto) {
		UserSpecificationImpl specification = new UserSpecificationImpl(paginationDto);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
		Page<User> result = usersRepository.findAll(specification, pageable);
		RoleUserDto roleUserDto = null;
		com.project.storywebapi.dto.PaginationUserDto dto = null;
		List<com.project.storywebapi.dto.PaginationUserDto> paginationUserDtos = new ArrayList<com.project.storywebapi.dto.PaginationUserDto>();
	
		for(User user : result.getContent()) {
			dto = new com.project.storywebapi.dto.PaginationUserDto();
			dto.setId(user.getId());
			dto.setCreateAt(user.getCreatedAt());
			dto.setCreateBy(user.getCreatedBy());
			dto.setIsDelete(user.getIsDeleted());
			dto.setEmail(user.getEmail());
			dto.setPassword(user.getPassword());
			dto.setUserName(user.getUsername());
			dto.setAvatar(user.getAvatar());	
			roleUserDto = new RoleUserDto();
			roleUserDto.setId(user.getRoleId().getId());
			roleUserDto.setCode(user.getRoleId().getCode());
			roleUserDto.setNameRole(user.getRoleId().getName());
			dto.setRoleDtos(roleUserDto);
			paginationUserDtos.add(dto);
		}
		Page<PaginationUserDto> page = convertListToPage(paginationUserDtos,pageable);
		return page;
		}

	@Override
	public ApiResponse changeUserPassword(ChangePasswordRequest changePasswordRequest) {
		ApiResponse apiResponse = new ApiResponse();
		String oldPassword = changePasswordRequest.getOldPassword();
		User user = usersRepository.findById(changePasswordRequest.getUserId()).orElse(null);
		
		if (encoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
			apiResponse.setSuccess(true);
			user = usersRepository.save(user);
		} else {
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Mật khẩu cũ không chính xác");
		}
		return apiResponse;
	}	
}
