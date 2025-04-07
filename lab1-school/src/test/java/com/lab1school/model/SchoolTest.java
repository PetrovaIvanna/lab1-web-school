package com.lab1school.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class SchoolTest {

	private School school;

	@BeforeEach
	void setUp() {
		school = new School("Школа №11");
	}

	@Test
	public void testSchoolCalculateAverage() {
		Student student1 = new Student("123", "Іван");
		student1.addGrade("Матем", 10);
		student1.addGrade("Хімія", 12);

		Student student2 = new Student("456", "Вася");
		student2.addGrade("Матем", 8);

		school.addStudent(student1);
		school.addStudent(student2);

		double schoolAvg = school.calculateSchoolAverage();
		assertEquals(9.5, schoolAvg, 0.001);
	}

	@Test
	public void testSchoolUpdateStudent() {
		Student student = new Student("123", "Іван");
		school.addStudent(student);
		Student updatedStudent = new Student("123", "Іван Петров");
		school.updateStudent(updatedStudent);
		Student fetchedStudent = school.getStudentById("123");
		assertEquals("Іван Петров", fetchedStudent.getName());
	}

	@Test
	public void testSchoolGetStudentByIdNotFound() {
		Student student = school.getStudentById("999");
		assertNull(student);
	}
}
