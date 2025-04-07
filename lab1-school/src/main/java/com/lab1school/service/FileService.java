package com.lab1school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab1school.model.School;
import com.lab1school.model.Student;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class FileService {

	public ObjectMapper mapper;

	public FileService() {
		this.mapper = new ObjectMapper();
	}

	public void write(String fileName, String content) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		java.nio.file.Files.write(file.toPath(), content.getBytes());
	}

	public String read(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new IOException("Файл не знайдено: " + fileName);
		}
		return new String(java.nio.file.Files.readAllBytes(file.toPath()));
	}

	public void exportData(School school, String fileName, Comparator<Student> comparator) throws IOException {
		List<Student> students = school.getStudents();
		if (comparator != null) {
			students.sort(comparator);
		}
		String json = mapper.writeValueAsString(school);
		write(fileName, json);
	}

	public School importData(String fileName) throws IOException {
		String json = read(fileName);
		return mapper.readValue(json, School.class);
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
}
