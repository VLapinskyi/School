package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Service;
import ua.com.foxminded.domain.Student;

class StudentsGeneratorTest {
	@Mock private CourseDAO courseDAO;
	@Mock private GroupDAO groupDAO;
	@Mock private StudentDAO studentDAO;
	@Mock private StudentsCoursesDAO studentsCoursesDAO;
	
	@Captor ArgumentCaptor<Integer> groupsIdCaptor;
	@Captor ArgumentCaptor<Integer> coursesIdCaptor;
	@Captor ArgumentCaptor<Integer> studentsIdCaptor;
	
	private Service service;
	private Service spyService;
	private StudentsGenerator studentsGenerator;
	private ArrayList<Student> allStudents;
	ArrayList<Course> allCourses;
	
	@BeforeEach
	void init () {
		MockitoAnnotations.openMocks(this);
		service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
		spyService = spy(service);
		studentsGenerator = new StudentsGenerator(spyService);
		allStudents = new ArrayList<>(Arrays.asList(
				new Student(1, "Myroslava", "Ruban"),
				new Student(2, "Oksana", "Bochkovska"),
				new Student(3, "Viktoriia", "Vovk"),
				new Student(4, "Nataliia", "Khodorkovska"),
				new Student(5, "Tetiana", "Shcherbak"),
				new Student(6, "Anna", "Sydorenko"),
				new Student(7, "Iuliia", "Mostunenko"),
				new Student(8, "Kateryna", "Lytvynenko"),
				new Student(9, "Liliia", "Leonenko"),
				new Student(10, "Nataliia", "Gudym")));
		allCourses = new ArrayList<>(Arrays.asList(	
				new Course(1, "math", "room 101"),
				new Course(2, "english", "room 102"),
				new Course(3, "ukrainian", "room 103"),
				new Course(4, "russian", "room 401"),
				new Course(5, "chemistry", "room 105")));
	}
		
	@Test
	void shouldCreateTwoHundredRandomStudents() {
		studentsGenerator.createTwoHundredRandomStudents();
		verify(spyService, times(200)).addStudent(anyString(), anyString());
	}

	@Test
	void shouldSetCorrectRandomGroupsId() {
		ArrayList<Group> allGroups = new ArrayList<>(Arrays.asList(
				new Group(1, "QW-01"),
				new Group(2, "QE-02"),
				new Group(3, "QR-03"),
				new Group(4, "QT-04"),
				new Group(5, "QY-05")));
		ArrayList<Integer> groupsId = new ArrayList<>();
		allGroups.stream().forEach(group -> groupsId.add(group.getGroupId()));
		when(spyService.getAllStudents()).thenReturn(allStudents);
		when(spyService.getAllGroups()).thenReturn(allGroups);
		studentsGenerator.setRandomGroupsForStudents();
		verify(spyService, times(allStudents.size())).setGroupForStudent(anyInt(), groupsIdCaptor.capture());
		ArrayList<Integer> generatedGroupsId = new ArrayList<>(groupsIdCaptor.getAllValues());
		generatedGroupsId.stream().forEach(id -> assertTrue(groupsId.contains(id)));
	}

	@Test
	void shouldSetCorrectRandomCoursesAndStudentsId() {	
	ArrayList<Integer> coursesId = new ArrayList<>();
	allCourses.stream().forEach(course -> coursesId.add(course.getCourseId()));
	ArrayList<Integer> studentsId = new ArrayList<>();
	allStudents.stream().forEach(student -> studentsId.add(student.getStudentId()));
	
	when(spyService.getAllStudents()).thenReturn(allStudents);
	when(spyService.getAllCourses()).thenReturn(allCourses);
	
	studentsGenerator.setRandomCoursesForStudents();
	
	verify(spyService, atLeastOnce()).addStudentToCourse(studentsIdCaptor.capture(), coursesIdCaptor.capture());
	ArrayList<Integer> generatedStudentsId = new ArrayList<>(studentsIdCaptor.getAllValues());
	ArrayList<Integer> generatedCoursesId = new ArrayList<>(coursesIdCaptor.getAllValues());
	
	generatedStudentsId.stream().forEach(id -> assertTrue(studentsId.contains(id)));
	generatedCoursesId.stream().forEach(id -> assertTrue(coursesId.contains(id)));
	}
	
	@Test
	void shouldSetFromOneToThreeCoursesForStudent() {
		ArrayList<Integer> coursesId = new ArrayList<>();
		allCourses.stream().forEach(course -> coursesId.add(course.getCourseId()));
		ArrayList<Integer> studentsId = new ArrayList<>();
		allStudents.stream().forEach(student -> studentsId.add(student.getStudentId()));
		
		when(spyService.getAllStudents()).thenReturn(allStudents);
		when(spyService.getAllCourses()).thenReturn(allCourses);
		
		studentsGenerator.setRandomCoursesForStudents();
		
		verify(spyService, atLeastOnce()).addStudentToCourse(studentsIdCaptor.capture(), coursesIdCaptor.capture());
		ArrayList<Integer> generatedStudentsId = new ArrayList<>(studentsIdCaptor.getAllValues());
		ArrayList<Integer> generatedCoursesId = new ArrayList<>(coursesIdCaptor.getAllValues());
		studentsId.stream().forEach(studentId -> {
			HashMap<Integer, Integer> coursesStudentsId = new HashMap<>();
			for(int i = 0; i < generatedStudentsId.size(); i++) {
				if (studentId == generatedStudentsId.get(i)) {
					coursesStudentsId.put(generatedCoursesId.get(i), generatedStudentsId.get(i));
				}
			}
			assertTrue(coursesStudentsId.size() <= 3 && coursesStudentsId.size() >=1 );
		});
	}
}
