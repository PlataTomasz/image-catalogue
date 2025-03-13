package com.github.platatomasz.imagecatalogue.appuser;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.platatomasz.imagecatalogue.appuser.avatar.AppUserAvatarService;

import jakarta.annotation.ManagedBean;

@RestController
@RequestMapping("/user")
public class AppUserControler {	
	@Autowired
	private final AppUserService userService;
	
	@Autowired
	private final AppUserAvatarService avatarService;
	
	public AppUserControler(AppUserService userService, AppUserAvatarService avatarService) {
		this.userService = userService;
		this.avatarService = avatarService;
	}
	
	// Create new user
	@PostMapping
	public void addUser(@RequestBody AppUser user) {
		userService.addUser(user);
	}

	@GetMapping
	public Iterable<AppUser> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userId}")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public AppUser getUser(@PathVariable Long userId) {
		return userService.getUserById(userId).orElseThrow();
	}
	
	@PutMapping
	public void updateUser(AppUser user) {
		userService.updateUser(user);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		// TODO:  What should happen with the user? Should he get flagged for deletion and his account deactivated?
	}
	
	@GetMapping("/{userId}/avatar")
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

