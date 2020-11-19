package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TablesCreatorDAO {
	public void createTables (String query) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e +"!");
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school", "postgres",
            "123456")) {
			if (connection != null) {
				try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
