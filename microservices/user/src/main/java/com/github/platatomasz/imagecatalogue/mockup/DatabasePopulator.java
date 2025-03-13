package com.github.platatomasz.imagecatalogue.mockup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;

import com.github.platatomasz.imagecatalogue.appuser.AppUser;
import com.github.platatomasz.imagecatalogue.appuser.AppUserService;

import jakarta.annotation.PostConstruct;

@Component
public class DatabasePopulator {
	
	@Autowired
	private final AppUserService userService;
	
	public DatabasePopulator(AppUserService userService) {
		this.userService = userService;
	}

	@PostConstruct
	private void onComponentConstruct() {

	}
}
