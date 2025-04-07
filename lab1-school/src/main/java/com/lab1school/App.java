package com.lab1school;

import java.io.IOException;
import java.util.*;

import com.lab1school.model.School;
import com.lab1school.model.Student;
import com.lab1school.model.Subject;
import com.lab1school.service.FileService;

import java.util.List;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		School school = new School("Школа №11");
		Scanner scanner = new Scanner(System.in);
		FileService fileService = new FileService();
		int choice = 0;

		System.out.println("\nЗастосунок для управління школою.");

		do {
			System.out.println("\nМожливі наступні функції:");
			System.out.println("1. Додати учня");
			System.out.println("2. Видалити учня");
			System.out.println("3. Додати предмет учню");
			System.out.println("4. Видалити предмет учню");
			System.out.println("5. Оновити назву предмета учню");
			System.out.println("6. Додати оцінку до предмету учня");
			System.out.println("7. Показати інформацію про учнів та їх предмети");
			System.out.println("8. Обчислити успішність учня");
			System.out.println("9. Обчислити середній бал школи");
			System.out.println("10. Експортувати дані у JSON");
			System.out.println("11. Імпортувати дані з JSON");
			System.out.println("0. Вихід");
			System.out.print("Введіть ваш вибір: ");

			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Невірний ввід. Будь ласка, введіть число.");
				continue;
			}

			switch (choice) {
			case 1:
				System.out.print("Введіть ID учня (лише цифри): ");
				String id = scanner.nextLine();
				if (!id.matches("\\d+")) {
					System.out.println("Помилка: ID повинен містити лише цифри.");
					break;
				}
				System.out.print("Введіть ім'я учня: ");
				String name = scanner.nextLine();
				try {
					school.addStudent(new Student(id, name));
					System.out.println("Учня додано.");
				} catch (IllegalArgumentException ex) {
					System.out.println("Помилка: " + ex.getMessage());
				}
				break;

			case 2:
				System.out.print("Введіть ID учня для видалення: ");
				id = scanner.nextLine();
				Student studentToRemove = school.getStudentById(id);
				if (studentToRemove != null) {
					school.removeStudent(studentToRemove);
					System.out.println("Учня видалено.");
				} else {
					System.out.println("Учня не знайдено.");
				}
				break;

			case 3:
				System.out.print("Введіть ID учня: ");
				String studentId = scanner.nextLine();
				Student student = school.getStudentById(studentId);
				if (student != null) {
					System.out.print("Введіть назву предмету: ");
					String subjectName = scanner.nextLine();
					Subject newSubject = new Subject(subjectName);
					student.addSubject(newSubject);
					System.out.println("Предмет додано.");
				} else {
					System.out.println("Учня не знайдено.");
				}
				break;

			case 4:
				System.out.print("Введіть ID учня: ");
				studentId = scanner.nextLine();
				student = school.getStudentById(studentId);
				if (student != null) {
					System.out.print("Введіть назву предмету для видалення: ");
					String subjectToRemove = scanner.nextLine();
					try {
						student.removeSubject(subjectToRemove);
						System.out.println("Предмет видалено.");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Учня не знайдено.");
				}
				break;

			case 5:
				System.out.print("Введіть ID учня: ");
				studentId = scanner.nextLine();
				student = school.getStudentById(studentId);
				if (student != null) {
					System.out.print("Введіть назву предмету для оновлення: ");
					String oldSubjectName = scanner.nextLine();
					System.out.print("Введіть нову назву предмету: ");
					String newSubjectName = scanner.nextLine();
					try {
						student.updateSubjectName(oldSubjectName, newSubjectName);
						System.out.println("Предмет оновлено.");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				} else {
					System.out.println("Учня не знайдено.");
				}
				break;

			case 6:
				System.out.print("Введіть ID учня: ");
				id = scanner.nextLine();
				student = school.getStudentById(id);
				if (student != null) {
					System.out.print("Введіть назву предмету: ");
					String subjectName = scanner.nextLine();
					if (!student.getSubjects().containsKey(subjectName)) {
						System.out.println("Предмет не знайдено. Спочатку додайте предмет (опція 3).");
						break;
					}
					System.out.print("Введіть оцінку (від 1 до 12): ");
					try {
						int grade = Integer.parseInt(scanner.nextLine());
						student.addGrade(subjectName, grade);
						System.out.println("Оцінку додано до предмету.");
					} catch (NumberFormatException e) {
						System.out.println("Неправильний формат оцінки.");
					} catch (IllegalArgumentException e) {
						System.out.println("Помилка: " + e.getMessage());
					}
				} else {
					System.out.println("Учня не знайдено.");
				}
				break;

			case 7:
				List<Student> students = school.getStudents();
				if (students.isEmpty()) {
					System.out.println("У школі немає учнів.");
				} else {
					for (Student s : students) {
						System.out.println("Учень: " + s.getName() + " (ID: " + s.getId() + ")");
						Map<String, Subject> subjects = s.getSubjects();
						if (subjects.isEmpty()) {
							System.out.println("  Немає предметів.");
						} else {
							subjects.forEach((subjectName, subject) -> {
								System.out.println("  Предмет: " + subjectName + " - оцінки: " + subject.getGrades());
							});
						}
					}
				}
				break;

			case 8:
				System.out.print("Введіть ID учня: ");
				id = scanner.nextLine();
				student = school.getStudentById(id);
				if (student != null) {
					try {
						double overallAvg = student.calculateOverallAverage();
						System.out.println("Успішність учня: " + overallAvg);
					} catch (IllegalStateException e) {
						System.out.println("Помилка: " + e.getMessage());
					}
				} else {
					System.out.println("Учня не знайдено.");
				}
				break;

			case 9:
				try {
					double schoolAvg = school.calculateSchoolAverage();
					System.out.println("Середній бал школи: " + schoolAvg);
				} catch (IllegalStateException e) {
					System.out.println("Помилка: " + e.getMessage());
				}
				break;
			case 10:
				System.out.print("Введіть шлях/назву файлу для експорту: ");
				String exportFileName = scanner.nextLine();
				System.out.println("1. Сортувати за іменем учня");
				System.out.println("2. Без сортування");
				int sortChoice = Integer.parseInt(scanner.nextLine());
				Comparator<Student> comparator = null;
				if (sortChoice == 1) {
					comparator = Comparator.comparing(Student::getName);
				}
				try {
					fileService.exportData(school, exportFileName, comparator);
					System.out.println("Дані успішно експортовано.");
				} catch (IOException e) {
					System.out.println("Помилка експорту: " + e.getMessage());
				}
				break;
			case 11:
				System.out.print("Введіть шлях до файлу для імпорту: ");
				String importFileName = scanner.nextLine();
				try {
					School importedSchool = fileService.importData(importFileName);
					school = importedSchool;
					System.out.println("Дані успішно імпортовано. Школа оновлена.");
				} catch (IOException e) {
					System.out.println("Помилка імпорту: " + e.getMessage());
				}
				break;

			case 0:
				System.out.println("Вихід...");
				break;

			default:
				System.out.println("Невірний вибір.");
			}
		} while (choice != 0);

		scanner.close();
	}
}