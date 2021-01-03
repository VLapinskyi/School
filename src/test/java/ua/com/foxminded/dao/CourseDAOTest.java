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
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

class CourseDAOTest {
	private static Logger logger = LogManager.getLogger(CourseDAOTest.class);
	private static Properties properties = new Properties();
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
		courseDAO = new CourseDAO();
		allCourses = new ArrayList<>(Arrays.asList(
				new Course(1, "math", "room 101"),
				new Course(2, "biology", "room 102"),
				new Course(3, "english", "room 103"),
				new Course(4, "ukranian", "room 104"),
				new Course(5, "chemistry", "room 105")));
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
	void shouldCreateCourseInDatabase() {
		String courseName = "art";
		String courseDescription = "room 109";
		Course course = new Course(courseName, courseDescription);
		courseDAO.create(course);
		Course resultCourse = null;
		String query = properties.getProperty("get.course.for.test");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, courseName);
			preparedStatement.setString(2, courseDescription);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			resultCourse = new Course(resultSet.getString("course_name"), resultSet.getString("course_description"));
		}catch (SQLException e) {
			logger.error("cannot get connection in method shouldCreateCourseInDatabase of CourseDAOTest class", e);
		}
		assertEquals(course, resultCourse);
	}
	
	@Test
	void shouldFindAllCourses() {
		for(int i = 0; i < allCourses.size(); i++) {
			courseDAO.create(allCourses.get(i)); 
		}
		ArrayList<Course> expectedCourses = new ArrayList<>();
		expectedCourses.addAll(allCourses);
		ArrayList<Course> actualCourses = (ArrayList<Course>) courseDAO.findAll();
		assertTrue(expectedCourses.containsAll(actualCourses) && actualCourses.containsAll(expectedCourses));
	}
	
	@Test
	void shouldFindCourseByName() {
		for(int i = 0; i < allCourses.size(); i++) {
			courseDAO.create(allCourses.get(i)); 
		}
		Course actualCourse = courseDAO.findByName("math");
		Course expectedCourse = new Course(1, "math", "room 101");
		assertEquals(expectedCourse, actualCourse);
	}
	
	@Test
	void whenFindByIdShouldThrowUnsupportedOperationException() {
		assertThrows(UnsupportedOperationException.class, () -> courseDAO.findById(1));
	}
	
	@Test
	void whenUpdateShouldThrowUnsupportedOperationException() {
		Course course = new Course(1, "math", "room 102");
		assertThrows(UnsupportedOperationException.class, () -> courseDAO.update(course));
	}
	
	@Test
	void whenDeleteByIdShouldThrowUnsupportedOperationException() {
		assertThrows(UnsupportedOperationException.class, () -> courseDAO.deleteById(1));
	}
}