package com.sena.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	private final String folder = "images/";

	public String saveImages(MultipartFile file, String nombre) throws IOException {
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Path.of(folder + nombre + "_" + file.getOriginalFilename());
			Files.write(path, bytes);
			return nombre + "_" + file.getOriginalFilename();
		}
		return "default.jpg";
	}

	public void deleteImage(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return;
		}
		File file = new File(folder + nombre);
		if (file.exists()) {
			file.delete();
		}
	}
}
