package com.lab1school.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
	private String id;
	private String name;
	private Map<String, Subject> subjects;

	public Student(String id, String name) {
		if (!id.matches("\\d+")) {
			throw new IllegalArgumentException("ID повинен містити лише цифри.");
		}
		this.id = id;
		this.name = name;
		this.subjects = new HashMap<>();
	}

	public Student() {
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Map<String, Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Map<String, Subject> subjects) {
		this.subjects = subjects;
	}

	public void addGrade(String subjectName, int grade) {
		if (grade < 1 || grade > 12) {
			throw new IllegalArgumentException("Оцінка повинна бути від 1 до 12.");
		}
		subjects.putIfAbsent(subjectName, new Subject(subjectName));
		subjects.get(subjectName).addGrade(grade);
	}

	public double calculateOverallAverage() {
		if (subjects.isEmpty()) {
			throw new IllegalStateException("Учень не має предметів з оцінками.");
		}
		double total = 0;
		int count = 0;
		for (Subject subject : subjects.values()) {
			try {
				total += subject.calculateAverage();
				count++;
			} catch (IllegalStateException e) {

			}
		}
		if (count == 0) {
			throw new IllegalStateException("Учень не має оцінок для розрахунку.");
		}
		return total / count;
	}

	public void addSubject(Subject subject) {
		if (subject == null) {
			throw new IllegalArgumentException("Subject cannot be null");
		}
		subjects.put(subject.getName(), subject);
	}

	public void removeSubject(String subjectName) {
		if (subjects.containsKey(subjectName)) {
			subjects.remove(subjectName);
		} else {
			throw new IllegalArgumentException("Subject with name " + subjectName + " not found");
		}
	}

	public void updateSubjectName(String oldSubjectName, String newSubjectName) {
		if (!subjects.containsKey(oldSubjectName)) {
			throw new IllegalArgumentException("Subject with name " + oldSubjectName + " not found");
		}

		Subject subject = subjects.remove(oldSubjectName);
		subject.setName(newSubjectName);
		subjects.put(newSubjectName, subject);
	}

	public Subject getSubjectByName(String subjectName) {
		return subjects.get(subjectName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Student student = (Student) o;
		return id.equals(student.id) && name.equals(student.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}