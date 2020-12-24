package ua.com.foxminded.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;

class UniversityTest {
	
	
	@Mock private CourseDAO courseDAO;
	@Mock private GroupDAO groupDAO;
	@Mock private StudentDAO studentDAO;
	@Mock private StudentsCoursesDAO studentsCoursesDAO;
	
	ArrayList<Group> allGroups;
	ArrayList<Student> allStudents;
	HashMap<Student, Group> studentsGroups;
	University university;
	Group group1;
	Group group2;
	Group group3;
	Group group4;
	Group group5;
	
	@BeforeEach
	public void init () {
		MockitoAnnotations.openMocks(this);
		university = new University(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
	}

	@Test
	void shouldGetGroupsWithNoMoreThanThreeStudents() {
		group1 = new Group(1, "QW-01");
		group2 = new Group(2, "AS-02");
		group3 = new Group(3, "ZX-03");
		group4 = new Group(4, "ER-04");
		group5 = new Group(5, "DF-05");
		allGroups = new ArrayList<>(Arrays.asList(
				group1, group2, group3, group4, group5));
		when(groupDAO.findAll()).thenReturn(allGroups);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(0).getGroupId())).thenReturn(1);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(1).getGroupId())).thenReturn(2);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(2).getGroupId())).thenReturn(3);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(3).getGroupId())).thenReturn(4);
		when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(4).getGroupId())).thenReturn(5);
		ArrayList<Group> expectedGroups = new ArrayList<>(Arrays.asList(
				group1, group2, group3));
		assertEquals(expectedGroups, university.getGroupsWithNoMoreStudentsThan(3));	
	}

}
