package ua.com.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

 class PropertiesForDAO {
	private static final String PROPERTIES_FILE_NAME = "database.properties";
	private static Logger logger = LogManager.getLogger(PropertiesForDAO.class);
	
	private PropertiesForDAO() {
		logger.error("PropertiesForDAO is utility class, so there is no reason to create object");
	    throw new IllegalStateException("Utility class");
	  }

	
	static Properties getQueries () {
		Properties properties = new Properties();
		try (InputStream inputStream = CourseDAO.class.getResourceAsStream("/" + PROPERTIES_FILE_NAME)) {
			if (inputStream == null) 
				throw new SQLException();
			properties.load(inputStream);
		} catch (SQLException e) {
			logger.error("cannot load properties file", e);
		} catch (IOException e) {
			logger.error("cannot find properties file", e);
		}
		return properties;
	}
}
