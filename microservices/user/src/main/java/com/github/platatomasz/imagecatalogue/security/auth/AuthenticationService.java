package com.github.platatomasz.imagecatalogue.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.platatomasz.imagecatalogue.appuser.AppUser;
import com.github.platatomasz.imagecatalogue.appuser.AppUserRepository;
import com.github.platatomasz.imagecatalogue.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final AppUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		AppUser user = AppUser.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				//.role(Role.USER)
				.build();
		userRepository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow();
		
		var jwtToken = jwtService.generateToken(user);
				
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
