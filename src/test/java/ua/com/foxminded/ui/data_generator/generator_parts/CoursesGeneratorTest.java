package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Service;

class CoursesGeneratorTest {
    private static Properties properties = new Properties();
    
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;
    private StudentDAO studentDAO;
    private StudentsCoursesDAO studentsCoursesDAO;
    private Service service;
    private CoursesGenerator coursesGenerator;
    
    @BeforeAll
    static void setPropertiesForQueries() throws SQLException, IOException {
        try (InputStream inputStream = CoursesGeneratorTest.class.getResourceAsStream("/" + "queries for tests.properties")) {
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
    void init () {
        TablesCreator creator = new TablesCreator(new TablesCreatorDAO());
        creator.createTables();
        courseDAO = new CourseDAO();
        groupDAO = new GroupDAO();
        studentDAO = new StudentDAO();
        studentsCoursesDAO = new StudentsCoursesDAO();
        service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
        coursesGenerator = new CoursesGenerator(service);
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
    void shouldCreateTenCourses() {
        coursesGenerator.createTenCourses();
        ArrayList<Course> allCourses = (ArrayList<Course>) courseDAO.findAll();
        assertEquals(10, allCourses.size());
    }
}
