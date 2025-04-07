package com.lab1school.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SubjectTest {
	@Test
	public void testSubjectCalculateAverageWithNoGrades() {
		Subject subject = new Subject("Хімія");
		Exception exception = assertThrows(IllegalStateException.class, () -> {
			subject.calculateAverage();
		});
		assertTrue(exception.getMessage().contains("Немає оцінок для предмету Хімія"));
	}

	@Test
	public void testSubjectCalculateAverage() {
		Subject subject = new Subject("Хімія");
		subject.addGrade(8);
		subject.addGrade(10);
		subject.addGrade(12);
		double avg = subject.calculateAverage();
		assertEquals(10.0, avg, 0.001);
	}

	@Test
	public void testSubjectUpdateName() {
		Subject subject = new Subject("Історія");
		subject.setName("Всесвітня Історія");
		assertEquals("Всесвітня Історія", subject.getName());
	}
}
