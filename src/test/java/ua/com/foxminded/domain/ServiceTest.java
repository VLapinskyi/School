package ua.com.foxminded.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;

class ServiceTest {
	@Mock private CourseDAO courseDAO;
	@Mock private GroupDAO groupDAO;
	@Mock private StudentDAO studentDAO;
	@Mock private StudentsCoursesDAO studentsCoursesDAO;
	
	private Service service;
	
	@BeforeEach
	void init () {
		MockitoAnnotations.openMocks(this);
		service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
	}
	
	@Test
	void shouldAddCourseToList() {
		ArrayList<Course> database = new ArrayList<>();
		Course expectedCourse = new Course("math", "room 101");
		doAnswer(invocation -> {
			database.add(invocation.getArgument(0));
		return null;
		}).when(courseDAO).create(any(Course.class));
		service.addCourse("math", "room 101");
		assertEquals(expectedCourse, database.get(database.size()-1));
	}
	
	@Test
	void shouldReturnAllCoursesFromList() {
		ArrayList<Course> allCourses = new ArrayList<>(Arrays.asList(
				new Course(1, "math", "room 101"),
				new Course(2, "biology", "room 102"),
				new Course(3, "english", "room 103"),
				new Course(4, "ukranian", "room 104"),
				new Course(5, "chemistry", "room 105")));
		ArrayList<Course> allExpectedCourses = new ArrayList<>(Arrays.asList(
				new Course(1, "math", "room 101"),
				new Course(2, "biology", "room 102"),
				new Course(3, "english", "room 103"),
				new Course(4, "ukranian", "room 104"),
				new Course(5, "chemistry", "room 105")));
		
		when(courseDAO.findAll()).thenReturn(allCourses);
		assertEquals(allExpectedCourses, service.getAllCourses());
		
	}
	
	@Test
	void shouldAddGroupToList() {
		ArrayList<Group> database = new ArrayList<>();
		Group expectedGroup = new Group("QW-01");
		doAnswer(invocation -> {
			database.add(invocation.getArgument(0));
		return null;
		}).when(groupDAO).create(any(Group.class));
		service.addGroup("QW-01");
		assertEquals(expectedGroup, database.get(database.size()-1));
	}
	
	@Test
	void shouldReturnAllGroupsFromList() {
		ArrayList<Group> allGroups = new ArrayList<>(Arrays.asList(
				new Group(1, "QW-01"),
				new Group(2, "AS-02"),
				new Group(3, "ZX-03"),
				new Group(4, "ER-04"),
				new Group(5, "DF-05")));
		ArrayList<Group> allExpectedGroups = new ArrayList<>(Arrays.asList(
				new Group(1, "QW-01"),
				new Group(2, "AS-02"),
				new Group(3, "ZX-03"),
				new Group(4, "ER-04"),
				new Group(5, "DF-05")));
		
		when(groupDAO.findAll()).thenReturn(allGroups);
		assertEquals(allExpectedGroups, service.getAllGroups());
	}

	@Test
	void shouldGetGroupsWithNoMoreThanThreeStudents() {
		ArrayList<Group> allGroups = new ArrayList<>(Arrays.asList(
				new Group(1, "QW-01"),
				new Group(2, "AS-02"),
				new Group(3, "ZX-03"),
				new Group(4, "ER-04"),
				new Group(5, "DF-05")));
		when(groupDAO.findAll()).thenReturn(allGroups);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(0).getGroupId())).thenReturn(1);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(1).getGroupId())).thenReturn(2);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(2).getGroupId())).thenReturn(3);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(3).getGroupId())).thenReturn(4);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(4).getGroupId())).thenReturn(5);
		ArrayList<Group> expectedGroups = new ArrayList<>(Arrays.asList(
				new Group(1, "QW-01"),
				new Group(2, "AS-02"),
				new Group(3, "ZX-03")));
		assertEquals(expectedGroups, service.getGroupsWithNoMoreStudentsThan(3));	
	}
}
