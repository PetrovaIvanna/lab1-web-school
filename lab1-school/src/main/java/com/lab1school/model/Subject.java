package com.lab1school.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subject {
	private String name;
	private List<Integer> grades;

	public Subject(String name) {
		this.name = name;
		this.grades = new ArrayList<>();
	}

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public List<Integer> getGrades() {
		return grades;
	}

	public void setGrades(List<Integer> grades) {
		this.grades = grades;
	}

	public void addGrade(int grade) {
		if (grade < 1 || grade > 12) {
			throw new IllegalArgumentException("Оцінка повинна бути від 1 до 12.");
		}
		grades.add(grade);
	}

	public double calculateAverage() {
		if (grades.isEmpty()) {
			throw new IllegalStateException("Немає оцінок для предмету " + name);
		}
		int sum = 0;
		for (int grade : grades) {
			sum += grade;
		}
		return (double) sum / grades.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Subject subject = (Subject) o;
		return name.equals(subject.name) && grades.equals(subject.grades);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, grades);
	}
}