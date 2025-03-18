package com.github.platatomasz.imagecatalogue.appuser.avatar;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class responsible for managing user avatar image, such as saving and retrieving 
 */
@Service
public class UserAvatarService {
	public Resource getUserAvatarResource(String avatarFileName) {
		return new AvatarFileLoader().getAvatarFile(avatarFileName);
	}

	/**
	 * Saves passed avatar file to disk
	 * @throws IOException - If file failed to be written
	 */
	public void saveFile(MultipartFile file) throws IOException {
		
	}
}
