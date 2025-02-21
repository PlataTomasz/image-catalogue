package com.github.platatomasz.imagecatalogue.user.avatar;

import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;

public class AvatarFileLoader {
	public String avatarFileDirectory = "images/avatars";
	
	public String getRelativeFilePath(String fileName) {
		return avatarFileDirectory + "/" + fileName; 
	}
	
	public FileSystemResource getAvatarFile(String fileName) {
		return new FileSystemResource(getRelativeFilePath(fileName));
	}
}
