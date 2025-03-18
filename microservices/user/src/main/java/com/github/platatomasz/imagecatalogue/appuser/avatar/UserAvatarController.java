package com.github.platatomasz.imagecatalogue.appuser.avatar;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.platatomasz.imagecatalogue.appuser.AppUser;
import com.github.platatomasz.imagecatalogue.appuser.AppUserService;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-avatar")
@RequiredArgsConstructor
public class UserAvatarController {
	
	private final UserAvatarService userAvatarService;
	private final AppUserService appUserService;
	
	@GetMapping( value = "/{userId}", produces = "image/png")
	@ResponseBody
	public Resource getUserAvatar(@PathVariable Long userId) {
		Optional<AppUser> userOptional = appUserService.getUserById(userId);
		if(userOptional.isPresent()) {
			return userAvatarService.getUserAvatarResource(userOptional.get().getAvatarFileName());
		} else {
			throw new NoSuchElementException("No such user!");
		}
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public void uploadUserAvatar(@RequestParam MultipartFile file) throws IOException {
		userAvatarService.saveFile(file);
	}
}
