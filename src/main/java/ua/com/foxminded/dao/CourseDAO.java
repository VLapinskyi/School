package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDAO {
	public void insertCourse(String courseName, String courseDescription) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school", "postgres",
            "123456")) {
			if (connection != null) {
				String query = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
				try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
					preparedStatement.setString(1, courseName);
					preparedStatement.setString(2, courseDescription);
					preparedStatement.execute();
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
