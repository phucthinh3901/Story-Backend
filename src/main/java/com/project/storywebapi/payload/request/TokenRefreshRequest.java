package com.project.storywebapi.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenRefreshRequest {
	  private String refreshToken;
}
