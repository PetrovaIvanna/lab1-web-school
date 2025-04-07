package com.lab1school.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class School {
	private String name;
	private List<Student> students;

	public School() {
	}

	public School(String name) {
		this.name = name;
		this.students = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public double calculateSchoolAverage() {
		if (students.isEmpty()) {
			throw new IllegalStateException("Відсутні учні для розрахунку.");
		}
		double total = 0;
		int count = 0;
		for (Student s : students) {
			try {
				total += s.calculateOverallAverage();
				count++;
			} catch (IllegalStateException e) {

			}
		}
		if (count == 0) {
			throw new IllegalStateException("Немає даних для розрахунку середнього балу школи.");
		}
		return total / count;
	}

	public void addStudent(Student s) {

		students.add(s);
	}

	public void removeStudent(Student s) {
		students.remove(s);
	}

	public Student getStudentById(String id) {
		for (Student s : students) {
			if (s.getId().equals(id)) {
				return s;
			}
		}
		return null;
	}

	public void updateStudent(Student updatedStudent) {
		if (updatedStudent == null) {
			throw new IllegalArgumentException("Updated student cannot be null");
		}
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getId().equals(updatedStudent.getId())) {
				students.set(i, updatedStudent);
				return;
			}
		}
		throw new IllegalArgumentException("Student with id " + updatedStudent.getId() + " not found");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		School school = (School) o;
		return name.equals(school.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}