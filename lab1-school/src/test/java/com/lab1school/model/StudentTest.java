package com.lab1school.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class StudentTest {

	private Student student;

	@BeforeEach
	void setUp() {
		student = new Student("1", "Іван Петров");
	}

	@Test
	public void testAddInvalidGrade() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			student.addGrade("Матем", 0);
		});
		assertEquals("Оцінка повинна бути від 1 до 12.", exception.getMessage());
	}

	@Test
	public void testAddGradeAndCalculateAverages() {
		student.addGrade("Матем", 10);
		student.addGrade("Матем", 12);

		Subject math = student.getSubjectByName("Матем");
		assertNotNull(math);
		double subjectAvg = math.calculateAverage();
		assertEquals(11.0, subjectAvg, 0.001);

		double overallAvg = student.calculateOverallAverage();
		assertEquals(11.0, overallAvg, 0.001);
	}

	@Test
	public void testCalculateOverallAverageWithNoGrades() {
		Exception exception = assertThrows(IllegalStateException.class, () -> {
			student.calculateOverallAverage();
		});
		assertEquals("Учень не має предметів з оцінками.", exception.getMessage());
	}

	@Test
	public void testRemoveSubject() {
		student.addGrade("Історія", 9);
		student.removeSubject("Історія");
		assertNull(student.getSubjectByName("Історія"));
	}

}