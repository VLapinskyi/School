package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionFactory {
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";
	private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
	private static Connection connection;
	
	private ConnectionFactory() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static Connection getConnection () {
		try {
			if (connection == null || connection.isClosed())
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		}catch (SQLException exception) {
			logger.error("cannot get connection", exception);
			throw new RuntimeException("cannot get connection", exception);
		}
	}
}
