package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.dao.settings.ConnectionFactory;
import ua.com.foxminded.dao.settings.PropertiesForQueries;

class TablesCreatorDAOTest {
	private final static String SQL_SCRIPT_NAME = "Creating Tables.sql";
	private static Logger logger = LogManager.getLogger(CourseDAOTest.class);
	private static Properties properties = new Properties();
	private TablesCreatorDAO tablescreatorDAO;
	
	@BeforeAll
	static void setPropertiesForQueries() {
		final String PROPERTIES_FILE_NAME = "queries for tests.properties";
		try (InputStream inputStream = PropertiesForQueries.class.getResourceAsStream("/" + PROPERTIES_FILE_NAME)) {
			if (inputStream == null) 
				throw new SQLException();
			properties.load(inputStream);
		} catch (SQLException e) {
			logger.error("cannot load properties file", e);
		} catch (IOException e) {
			logger.error("cannot find properties file", e);
		}
	}
	
	@BeforeEach
	void init() {
		tablescreatorDAO = new TablesCreatorDAO();
	}
	
	@AfterEach
	void clearData() {
		String query = properties.getProperty("delete.tables");
		try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot delete tables", e);
		}
	}
	
	@Test
	void shouldCreateTables() {
		tablescreatorDAO.createTables(SQL_SCRIPT_NAME);
		String query = properties.getProperty("check.existing.tables");
		ArrayList<String> actualTablesNames = new ArrayList<>();
		ArrayList<String> expectedTablesNames = new ArrayList<>(Arrays.asList(
				"STUDENTS", "COURSES", "GROUPS", "STUDENTS_COURSES"));
		try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)){
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				actualTablesNames.add(resultSet.getString("TABLE_NAME"));
			}
		} catch(SQLException e) {
			logger.error("cannot check tables in method shouldCreateTables of TablesCreatorDAOTest class", e);
		}
		
		assertTrue(actualTablesNames.containsAll(expectedTablesNames) && expectedTablesNames.containsAll(actualTablesNames));
	}

}
