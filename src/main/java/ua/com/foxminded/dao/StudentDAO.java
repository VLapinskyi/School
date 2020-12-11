package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Student;

public class StudentDAO {
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";

	private static Logger logger = LogManager.getLogger(StudentDAO.class);

	public void writeStudent (Student student) {
		String query = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot write student", e);
		}
	}

	public void deleteStudentById(int studentId) {
		String query = "DELETE FROM students WHERE student_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {				
			preparedStatement.setInt(1, studentId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot delete student by id", e);
		}
	}

	public ArrayList<Student> getAllStudents() {
		ArrayList<Student> students = new ArrayList<>();
		String query = "SELECT * FROM students";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Student student = new Student();
				student.setStudentId(resultSet.getInt("student_id"));
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				students.add(student);
			}
		} catch (SQLException e) {
			logger.error("cannot get all students", e);
		}
		return students;
	}

	public void writeGroupForStudent (Integer studentId, Integer groupId) {
		String query = "UPDATE students SET group_id=? WHERE student_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, groupId);
			preparedStatement.setInt(2, studentId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot write group for student", e);
		}
	}
	public int getNumberOfStudentsInGroup (int groupId) {
		int numberOfStudents = 0;
		String query = "SELECT COUNT (group_id) FROM students WHERE group_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, groupId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			numberOfStudents = resultSet.getInt(1);
		} catch (SQLException e) {
			logger.error("cannot get number of students in group", e);
		}
		return numberOfStudents;
	}

	public Student getStudentById (int studentId) {
		Student student = null;
		String query = "SELECT * FROM students WHERE student_id = ?";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			student = new Student();
			student.setStudentId(resultSet.getInt("student_id"));
			student.setFirstName(resultSet.getString("first_name"));
			student.setLastName(resultSet.getString("last_name"));
			student.setGroupId(resultSet.getInt("group_id"));
		} catch (SQLException e) {
			logger.error("cannot get student by id", e);
		}
		return student;
	}
}
