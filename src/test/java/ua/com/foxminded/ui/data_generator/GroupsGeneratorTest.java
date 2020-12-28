package ua.com.foxminded.ui.data_generator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.Service;

class GroupsGeneratorTest {
	@Mock private CourseDAO courseDAO;
	@Mock private GroupDAO groupDAO;
	@Mock private StudentDAO studentDAO;
	@Mock private StudentsCoursesDAO studentsCoursesDAO;
	
	@Captor private ArgumentCaptor<String> captureNames;
	
	private Service service;
	private Service spyService;
	private GroupsGenerator groupsGenerator;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
		spyService = spy(service);
		groupsGenerator = new GroupsGenerator(spyService);
	}
	
	@Test
	void shouldCreateTenGroups() {
		groupsGenerator.createTenGroups();
		verify(spyService, times(10)).addGroup(anyString());
	}

	@Test
	void shouldGenerateRightNames() {
		groupsGenerator.createTenGroups();
		verify(spyService, times(10)).addGroup(captureNames.capture());
		ArrayList<String> names = new ArrayList<>(captureNames.getAllValues());
		names.stream().forEach(name -> assertTrue(name.matches("[A-Z]{2}-\\d{2}")));
	}

}
