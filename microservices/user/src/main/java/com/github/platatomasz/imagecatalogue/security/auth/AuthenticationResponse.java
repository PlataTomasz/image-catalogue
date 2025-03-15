package com.github.platatomasz.imagecatalogue.security.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
	private String token;
}
