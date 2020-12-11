package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Course;

public class CourseDAO {
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";

	private static Logger logger = LogManager.getLogger(CourseDAO.class);

	public void writeCourse(String courseName, String courseDescription) {
		String query = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
		try (Connection connection = DriverManager.getConnection(URL, USER,PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, courseName);
			preparedStatement.setString(2, courseDescription);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot write course", e);
		}
	}

	public ArrayList<Course> getAllCourses () {
		ArrayList<Course> allCourses = new ArrayList<>();
		String query = "SELECT * FROM courses";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseId(resultSet.getInt("course_id"));
				course.setCourseName(resultSet.getString("course_name"));
				course.setCourseDescription(resultSet.getString("course_description"));
				allCourses.add(course);
			}
		} catch (SQLException e) {
			logger.error("cannot get all courses", e);
		}
		return allCourses;
	}

	public int getCourseIdByName (String courseName) {
		int courseId = 0;
		String query = "SELECT course_id FROM courses WHERE course_name = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, courseName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			courseId = resultSet.getInt("course_id");
		} catch (SQLException e) {
			logger.error("cannot get course by id", e);
		}
		return courseId;
	}
}
