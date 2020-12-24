 package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Student;

public class StudentDAO implements GenericDAO<Student> {
	private static Logger logger = LogManager.getLogger(StudentDAO.class);
	Properties properties = PropertiesForDAO.getQueries();
	
	@Override
	public void create(Student student) {
		String query = properties.getProperty("create.student");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot create student", e);
		}
	}

	@Override
	public List<Student> findAll() {
		ArrayList<Student> students = new ArrayList<>();
		String query = properties.getProperty("find.all.students");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int studentId = resultSet.getInt("student_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				Student student = new Student(studentId, firstName, lastName);
				students.add(student);
			}
		} catch (SQLException e) {
			logger.error("cannot get all students", e);
		}
		return students;
	}

	@Override
	public Student findById(int studentId) {
		Student student = null;
		String query = properties.getProperty("find.student.by.id");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			student = new Student(studentId, firstName, lastName);
		} catch (SQLException e) {
			logger.error("cannot get student by id", e);
		}
		return student;
	}
	
	public List<Student> findByName(String firstName, String lastName) {
		ArrayList<Student> resultStudents = new ArrayList<>();
		String query = properties.getProperty("find.student.by.name");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int studentId = resultSet.getInt("student_id");
				resultStudents.add(new Student(studentId, firstName, lastName));
			}
		} catch (SQLException e) {
			logger.error("cannot get student by id", e);
		}
		return resultStudents;
	}

	@Override
	public void update(Student student) {
		String query = properties.getProperty("update.student");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.setInt(3, student.getStudentId());
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot update student in the database", e);
		}
	}
	
	public void setGroupForStudent (int studentId, int groupId) {
		String query = properties.getProperty("set.group.for.student");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, groupId);
			preparedStatement.setInt(2, studentId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot set group for student", e);
		}
	}

	@Override
	public void deleteById(int id) {
		String query = properties.getProperty("delete.student.by.id");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {				
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot delete student by id", e);
		}
	}
	public int getNumberOfStudentsInGroup (int groupId) {
		int numberOfStudents = 0;
		Connection connection = ConnectionFactory.getConnection();
		String query = "SELECT COUNT (group_id) FROM students WHERE group_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, groupId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			numberOfStudents = resultSet.getInt (1);
		} catch (SQLException e) {
			logger.error("cannot get number of students in group", e);
		}
		return numberOfStudents;
	}
}
