package com.github.platatomasz.imagecatalogue.io;

import java.io.IOException;

import org.springframework.core.io.Resource;

public interface ResourceSaver {
	public void save(Resource resource, String fileName) throws IOException;
}
