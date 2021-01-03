package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.dao.settings.ConnectionFactory;
import ua.com.foxminded.dao.settings.PropertiesForQueries;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

class StudentsCoursesDAOTest {
	private static Logger logger = LogManager.getLogger(StudentsCoursesDAOTest.class);
	private static Properties properties = new Properties();
	private StudentsCoursesDAO studentsCoursesDAO;
	private ArrayList<Student> allStudents;
	private StudentDAO studentDAO;
	private ArrayList<Course> allCourses;
	private CourseDAO courseDAO;
	
	@BeforeAll
	static void setPropertiesForQueries() {
		final String PROPERTIES_FILE_NAME = "queries for tests.properties";
		try (InputStream inputStream = PropertiesForQueries.class.getResourceAsStream("/" + PROPERTIES_FILE_NAME)) {
			if (inputStream == null) 
				throw new SQLException();
			properties.load(inputStream);
		} catch (SQLException e) {
			logger.error("cannot load properties file", e);
		} catch (IOException e) {
			logger.error("cannot find properties file", e);
		}
	}
	
	@BeforeEach
	void init() {
		TablesCreator creator = new TablesCreator(new TablesCreatorDAO());
		creator.createTables();
		studentsCoursesDAO = new StudentsCoursesDAO();
		studentDAO = new StudentDAO();
		courseDAO = new CourseDAO();
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
				new Course(2, "biology", "room 102"),
				new Course(3, "english", "room 103"),
				new Course(4, "ukranian", "room 104"),
				new Course(5, "chemistry", "room 105")));
		
		for (int i = 0; i < allStudents.size(); i++) {
			studentDAO.create(allStudents.get(i));
		}
		for (int i = 0; i < allCourses.size(); i++) {
			courseDAO.create(allCourses.get(i));
		}
		for(int i = 0; i < 5; i++) {
			studentsCoursesDAO.addStudentToCourse(allStudents.get(i).getStudentId(), allCourses.get(i).getCourseId());
		}
		studentsCoursesDAO.addStudentToCourse(1, 2);
		studentsCoursesDAO.addStudentToCourse(1, 5);
	}
	
	@AfterEach
	void clearData() {
		String query = properties.getProperty("delete.tables");
		try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot delete tables", e);
		}
	}
	
	@Test
	void shouldAddStudentToCourse() {
		int actualStudentId = 3;
		int actualCourseId = 1;
		studentsCoursesDAO.addStudentToCourse(actualStudentId, actualCourseId);
		String query = properties.getProperty("get.student.with.course.for.test");
		int resultStudentId = 0;
		int resultCourseId = 0;
		try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, actualStudentId);
			preparedStatement.setInt(2, actualCourseId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			resultStudentId = resultSet.getInt("student_id");
			resultCourseId = resultSet.getInt("course_id");
		} catch (SQLException e) {
			logger.error("cannot get connection in method shouldAddStudentToCourse of StudentsCoursesDAOTest class", e);
		}
		assertTrue(resultStudentId == actualStudentId && resultCourseId == actualCourseId);
	}

	@Test
	void shouldDeleteStudentFromCourse() {
		int actualStudentId = 1;
		int actualCourseId = 1;
		studentsCoursesDAO.deleteStudentFromCourse(actualStudentId, actualCourseId);
		ArrayList<Integer> listOfCoursesForActualStudent = new ArrayList<>();
		String query = properties.getProperty("get.list.of.courses.for.one.student");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, actualStudentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				listOfCoursesForActualStudent.add(resultSet.getInt("course_id"));
			}
		} catch (SQLException e) {
			logger.error("cannot get connection in method shouldDeleteStudentFromCourse", e);
		}
		assertTrue(!listOfCoursesForActualStudent.contains(actualCourseId));
	}

	@Test
	void shouldGetStudentsFromCourse() {
		int actualCourseId = 2;
		ArrayList<Student> expectedStudents = new ArrayList<>(Arrays.asList(
				new Student(1, "Myroslava", "Ruban"),
				new Student(2, "Oksana", "Bochkovska")));
		ArrayList<Student> resultStudents = (ArrayList<Student>) studentsCoursesDAO.getStudentsFromCourse(actualCourseId);
		assertTrue(expectedStudents.containsAll(resultStudents) && resultStudents.containsAll(expectedStudents));
	}

}
