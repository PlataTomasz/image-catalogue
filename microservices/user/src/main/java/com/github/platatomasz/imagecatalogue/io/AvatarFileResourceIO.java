package com.github.platatomasz.imagecatalogue.io;

import org.springframework.stereotype.Service;

@Service
public class AvatarFileResourceIO extends FileResourceIO {

	// FIXME: Breaks good practices - difference is only in data!
	public AvatarFileResourceIO() {
		super("images/avatars");
	}
}
