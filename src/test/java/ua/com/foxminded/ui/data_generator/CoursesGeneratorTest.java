package ua.com.foxminded.ui.data_generator;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.University;

class CoursesGeneratorTest {
	@Mock private CourseDAO courseDAO;
	@Mock private GroupDAO groupDAO;
	@Mock private StudentDAO studentDAO;
	@Mock private StudentsCoursesDAO studentsCoursesDAO;
	
	private University university;
	private University spyUniversity;
	private CoursesGenerator coursesGenerator;
	
	@BeforeEach
	public void init () {
		MockitoAnnotations.openMocks(this);
		university = new University(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
		spyUniversity = spy(university);
		coursesGenerator = new CoursesGenerator(spyUniversity);
	}
	@Test
	public void shouldCreateTenCourses() {
		coursesGenerator.createTenCourses();
		verify(spyUniversity, times(10)).addCourse(anyString(), anyString());
	}

}
