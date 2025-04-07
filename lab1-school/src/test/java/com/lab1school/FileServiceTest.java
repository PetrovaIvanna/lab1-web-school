package com.lab1school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab1school.model.School;
import com.lab1school.model.Student;
import com.lab1school.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

	@Spy
	private FileService fileServiceSpy;

	private final String testFileName = "testImport.json";
	private String fakeJsonContent;
	private School expectedSchool;

	@BeforeEach
	void setUp() {
		expectedSchool = new School("Школа з JSON");
		expectedSchool.addStudent(new Student("101", "Тест Юзер"));

		ObjectMapper tempMapper = new ObjectMapper();
		try {
			fakeJsonContent = tempMapper.writeValueAsString(expectedSchool);
		} catch (IOException e) {
			fail("Помилка при створенні тестового JSON: " + e.getMessage());
		}
		fileServiceSpy = Mockito.spy(new FileService());
	}

	@Test
	void testImportDataSchool() throws IOException {

		doReturn(fakeJsonContent).when(fileServiceSpy).read(eq(testFileName));

		School actualSchool = fileServiceSpy.importData(testFileName);

		verify(fileServiceSpy, times(1)).read(eq(testFileName));

		assertNotNull(actualSchool, "Імпортована школа не повинна бути null");
	}

	@Test
	void testImportDataFail() throws IOException {
		String errorMessage = "Помилка читання файлу";
		doThrow(new IOException(errorMessage)).when(fileServiceSpy).read(eq(testFileName));

		IOException ex = assertThrows(IOException.class, () -> fileServiceSpy.importData(testFileName));
		assertEquals(errorMessage, ex.getMessage());

		verify(fileServiceSpy, times(1)).read(eq(testFileName));
	}
}
