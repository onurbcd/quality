package com.onurbcd.qa.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

	private FileUtil() {
	}

	public static boolean writeStringToFile(String file, String str) {
		if (file == null || file.trim().isEmpty() || str == null || str.trim().isEmpty()) {
			return false;
		}

		try {
			Path path = Paths.get(file);
			byte[] strToBytes = str.getBytes();
			Files.write(path, strToBytes);
			return true;
		} catch (InvalidPathException | IOException | UnsupportedOperationException | SecurityException e) {
			return false;
		}
	}
}
