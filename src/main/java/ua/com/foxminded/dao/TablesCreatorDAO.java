package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TablesCreatorDAO {
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";
	
	private static Logger logger = LogManager.getLogger(TablesCreatorDAO.class);
	
	public void createTables (String query) {		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
					preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot create table", e); 
		}
	}
}
