package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.Service;

class CoursesGeneratorTest {
	@Mock private CourseDAO courseDAO;
	@Mock private GroupDAO groupDAO;
	@Mock private StudentDAO studentDAO;
	@Mock private StudentsCoursesDAO studentsCoursesDAO;
	
	private Service service;
	private Service spyService;
	private CoursesGenerator coursesGenerator;
	
	@BeforeEach
	void init () {
		MockitoAnnotations.openMocks(this);
		service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
		spyService = spy(service);
		coursesGenerator = new CoursesGenerator(spyService);
	}
	@Test
	void shouldCreateTenCourses() {
		coursesGenerator.createTenCourses();
		verify(spyService, times(10)).addCourse(anyString(), anyString());
	}

}
