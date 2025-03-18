package com.github.platatomasz.imagecatalogue.io;

import java.io.IOException;

import org.apache.catalina.webresources.FileResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
/**
 * Loads resource from directory relative to classpath 
 */
public class FileResourceIO implements ResourceLoader, ResourceSaver {
	
	public String fileStorageDirectory;

	@Override
	public void save(Resource resource, String fileName) throws IOException {
		// TODO: Implement
	}
	
	public String getRelativeFilePath(String fileName) {
		return fileStorageDirectory + "/" + fileName;
	}

	@Override
	public Resource load(String fileName) {
		return new FileSystemResource(getRelativeFilePath(fileName));
	}
	
	public FileResourceIO(String fileStorageDirectory) {
		this.fileStorageDirectory = fileStorageDirectory;
	}

	// TODO Check if default constructor is not available
}
