package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentsCoursesDAO {
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";

	private static Logger logger = LogManager.getLogger(StudentsCoursesDAO.class);

	public void addStudentToCourse (int studentId, int courseId) {
		String query = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot add student to course", e);
		}
	}

	public void deleteStudentFromCourse (int studentId, int courseId) {
		String query = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot delete student from course", e);
		}
	}

	public ArrayList<Integer> getStudentsIdFromCourse(int courseId) {
		ArrayList<Integer> studentsIdFromCourse = new ArrayList<>();
		String query = "SELECT student_id FROM students_courses WHERE course_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, courseId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				studentsIdFromCourse.add(resultSet.getInt("student_id"));
			}
		} catch (SQLException e) {
			logger.error("cannot get lists of student's id from one course", e);
		}
		return studentsIdFromCourse;
	}
}
