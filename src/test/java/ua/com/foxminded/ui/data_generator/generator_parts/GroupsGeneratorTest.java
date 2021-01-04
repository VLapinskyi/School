package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.dao.TablesCreatorDAO;
import ua.com.foxminded.dao.settings.ConnectionFactory;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Service;

class GroupsGeneratorTest {
    private static Properties properties = new Properties();

    private CourseDAO courseDAO;
    private GroupDAO groupDAO;
    private StudentDAO studentDAO;
    private StudentsCoursesDAO studentsCoursesDAO;
    private Service service;
    private GroupsGenerator groupsGenerator;

    @BeforeAll
    static void setPropertiesForQueries() throws SQLException, IOException {
        try (InputStream inputStream = GroupsGenerator.class.getResourceAsStream("/" + "queries for tests.properties")) {
            if (inputStream == null) 
                throw new SQLException();
            properties.load(inputStream);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @BeforeEach
    void init() {
        TablesCreator creator = new TablesCreator(new TablesCreatorDAO());
        creator.createTables();
        courseDAO = new CourseDAO();
        groupDAO = new GroupDAO();
        studentDAO = new StudentDAO();
        service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
        groupsGenerator = new GroupsGenerator(service);
    }

    @AfterEach
    void clearData() throws SQLException {
        String query = properties.getProperty("delete.tables");
        try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void shouldCreateTenGroups() {
        groupsGenerator.createTenGroups();
        ArrayList<Group> allGroups = (ArrayList<Group>) groupDAO.findAll();
        assertEquals(10, allGroups.size());
    }

    @Test
    void shouldGenerateRightNames() {
        groupsGenerator.createTenGroups();
        ArrayList<Group> allGroups = (ArrayList<Group>) groupDAO.findAll();
        allGroups.stream().forEach(group -> assertTrue(group.getGroupName().matches("[A-Z]{2}-\\d{2}")));
    }
}
