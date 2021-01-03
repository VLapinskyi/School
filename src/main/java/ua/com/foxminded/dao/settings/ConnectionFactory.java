package ua.com.foxminded.dao.settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConnectionFactory {
	private static Properties properties = PropertiesForSettingUpDatabase.getDatabaseProperties();
	private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
	private static Connection connection;
	
	private static final String URL = properties.getProperty("url");
	private static final String USER = properties.getProperty("user");
	private static final String PASSWORD = properties.getProperty("password");
	
	private ConnectionFactory() {
		logger.error("ConnectionFactory class is utility class, so there is no reason to create object");
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
