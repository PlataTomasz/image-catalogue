package com.github.platatomasz.imagecatalogue.user.avatar;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

/**
 * Class responsible for managing user avatar image, such as saving and retrieving 
 */
@Service
public class AppUserAvatarService {
	/**
	 * @return Binary contents of file containing the avatar
	 * @throws IOException 
	 */
	public AvatarData getAvatarFileData(String fileName) {
		FileSystemResource avatarFile = new AvatarFileLoader().getAvatarFile(fileName);
		try {
			return new AvatarData(avatarFile.getContentAsByteArray());
		} catch (IOException e) {
			return new AvatarData(new byte[0]);
		}
	}
	
	/**
	 * 
	 * @param avatarToSave - Avatar to be saved on disk
	 */
	public void saveAvatar(Avatar avatarToSave) {
		// Process avatar data - Convert to desirable format
	}
}
