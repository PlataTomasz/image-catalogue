package com.github.platatomasz.imagecatalogue.appuser.avatar;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.platatomasz.imagecatalogue.appuser.AppUser;
import com.github.platatomasz.imagecatalogue.appuser.AppUserService;
import com.github.platatomasz.imagecatalogue.security.jwt.JwtData;
import com.github.platatomasz.imagecatalogue.security.jwt.JwtService;

import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-avatar")
@RequiredArgsConstructor
public class UserAvatarController {
	
	private final UserAvatarService userAvatarService;
	private final AppUserService appUserService;
	
	private final JwtService jwtService;
	
	private String getFileExtension(String fileName) {
		String extension = fileName.substring(fileName.indexOf("."));
		return extension;
	}
	
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
	public void uploadUserAvatar(@RequestParam MultipartFile file, @RequestHeader(value = "Authorization") String authorizationHeader ) throws IOException {
		// Not portable? What if at some point authorization method changes?
		JwtData jwtData = jwtService.getJwtFromAuthorizationHeader(authorizationHeader);
		
		Optional<AppUser> appUserOptional = appUserService.getUserByName(jwtData.getUsername());
		
		if(appUserOptional.isPresent()) {
	 		String newFileName = String.format("%d%s", appUserOptional.get().getId(), getFileExtension(file.getOriginalFilename()));
			appUserOptional.get().setAvatarFileName(newFileName);
			appUserService.save(appUserOptional.get());
	 		userAvatarService.saveFile(file, newFileName);
		}
	}
}
