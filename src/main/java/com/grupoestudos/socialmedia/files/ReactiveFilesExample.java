package com.grupoestudos.socialmedia.files;

import org.springframework.util.FileCopyUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReactiveFilesExample {

	private static final String UPLOAD_DIR = "upload-dir";

	public static void main(String[] args) {

		try {
			Files.createDirectory(Paths.get(UPLOAD_DIR));
			FileCopyUtils.copy("sample content", new FileWriter(UPLOAD_DIR + "/file1.txt"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
