package ua.com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.dao.settings.ConnectionFactory;
import ua.com.foxminded.dao.settings.PropertiesForQueries;
import ua.com.foxminded.domain.Student;

public class StudentsCoursesDAO {
	private static Logger logger = LogManager.getLogger(StudentsCoursesDAO.class);
	Properties properties = PropertiesForQueries.getQueries();

	public void addStudentToCourse (int studentId, int courseId) {
		String query = properties.getProperty("add.student.to.course");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot add student to course", e);
		}
	}

	public void deleteStudentFromCourse (int studentId, int courseId) {
		String query = properties.getProperty("delete.student.from.course");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot delete student from course", e);
		}
	}

	public List<Student> getStudentsFromCourse(int courseId) {
		ArrayList<Student> studentsFromCourse = new ArrayList<>();
		String query = properties.getProperty("get.students.from.course");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setInt(1, courseId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				int studentId = resultSet.getInt("student_id");
				Student student = new Student(studentId, firstName, lastName);
				studentsFromCourse.add(student);
			}
		} catch (SQLException e) {
			logger.error("cannot get lists of student's id from one course", e);
		}
		return studentsFromCourse;
	}
}
