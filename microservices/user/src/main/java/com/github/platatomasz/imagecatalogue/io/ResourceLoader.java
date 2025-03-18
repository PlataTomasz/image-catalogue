package com.github.platatomasz.imagecatalogue.io;

import org.springframework.core.io.Resource;

public interface ResourceLoader {
	public Resource load(String fileName);
}
