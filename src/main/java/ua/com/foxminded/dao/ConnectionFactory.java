package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionFactory {
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	
	private static Properties properties;
	private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
	private static Connection connection;
	
	static {
		properties = PropertiesForDAO.getQueries();
		URL = properties.getProperty("url");
		USER = properties.getProperty("user");
		PASSWORD = properties.getProperty("password");
	}
	
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
