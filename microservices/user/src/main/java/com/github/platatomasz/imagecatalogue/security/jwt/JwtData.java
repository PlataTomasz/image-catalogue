package com.github.platatomasz.imagecatalogue.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Stores claims from JWT token
 */
@Data
@AllArgsConstructor
public class JwtData {
	private String username;
}
