package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

class GroupDAOTest {
	private static Logger logger = LogManager.getLogger(GroupDAOTest.class);
	private static Properties properties = new Properties();;
	private ArrayList<Group> allGroups;
	private GroupDAO groupDAO;
	
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
		TablesCreator creator = new TablesCreator(new TablesCreatorDAO());
		creator.createTables();
		groupDAO = new GroupDAO();
		allGroups = new ArrayList<>(Arrays.asList(
				new Group(1, "QW-01"),
				new Group(2, "AS-02"),
				new Group(3, "ZX-03"),
				new Group(4, "ER-04"),
				new Group(5, "DF-05")));
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
	void shouldCreateGroupInDatabase() {
		String groupName = "QW-01";
		Group group = new Group(groupName);
		groupDAO.create(group);
		Group resultGroup = null;
		String query = properties.getProperty("get.group.for.test");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, groupName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			resultGroup = new Group(resultSet.getString("group_name"));
		} catch (SQLException e) {
			logger.error("cannot get connection in method shouldCreateGroupInDatabase of GroupDAOTest class", e);
		}
		assertEquals(group, resultGroup);
	}
	
	@Test
	void shouldFindAllGroups() {
		for(int i = 0; i < allGroups.size(); i++) {
			groupDAO.create(allGroups.get(i));
		}
		ArrayList<Group> expectedGroups = new ArrayList<>();
		expectedGroups.addAll(allGroups);
		ArrayList<Group> actualGroups = (ArrayList<Group>) groupDAO.findAll();
		assertTrue(expectedGroups.containsAll(actualGroups) && actualGroups.containsAll(expectedGroups));
	}
	
	@Test
	void whenFindByIdShouldThrowUnsupportedOperationException() {
		assertThrows(UnsupportedOperationException.class, () -> groupDAO.findById(1));
	}
	
	@Test
	void shouldFindGroupByName() {
		for (int i = 0; i < allGroups.size(); i++) {
			groupDAO.create(allGroups.get(i));
		}
		Group actualGroup = groupDAO.findByName("QW-01");
		Group expectedGroup = new Group(1, "QW-01");
		assertEquals(expectedGroup, actualGroup);
	}
	
	@Test
	void whenUpdateShouldThrowUnsupportedOperationException() {
		Group group = new Group(1, "QW-02");
		assertThrows(UnsupportedOperationException.class, () -> groupDAO.update(group));
	}
	
	@Test
	void whenDeleteByIdShouldThrowUnsupportedOperationException() {
		assertThrows(UnsupportedOperationException.class, () -> groupDAO.deleteById(1));
	}
}
