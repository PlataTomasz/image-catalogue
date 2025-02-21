package com.github.platatomasz.imagecatalogue.user;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.platatomasz.imagecatalogue.user.avatar.AppUserAvatarService;

import jakarta.annotation.ManagedBean;

@RestController
@RequestMapping("/user")
public class UserControler {	
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final AppUserAvatarService avatarService;
	
	public UserControler(UserService userService, AppUserAvatarService avatarService) {
		this.userService = userService;
		this.avatarService = avatarService;
	}

	@GetMapping
	public Iterable<AppUser> getAllUsers() {
		return userService.getAllUsers(); 
	}
	
	@PostMapping
	public void addUser(@RequestBody AppUser user) {
		userService.addUser(user);
	}
	
	@GetMapping("{userId}/avatar")
	public ByteArrayResource getUserAvatar(@RequestParam Long userId ) {
		return userService.getUserById(userId)
			.map(user -> {
				byte[] avatarImageData = avatarService.getAvatarFileData(user.getAvatarFileName()).getData();
				if(avatarImageData.length > 0) {
					return new ByteArrayResource(avatarImageData);
				} else {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
				}
			}).orElseThrow(() -> {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			});
	}
	
}

