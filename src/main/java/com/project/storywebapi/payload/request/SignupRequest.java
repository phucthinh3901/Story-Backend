package com.project.storywebapi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SignupRequest {
	private String userName;
	
//	private String firstName;
//	
//	private String lastName;
	
	private String email;
	
	private String password;
	
	private Boolean verified;
	
	private String roles;
}
