package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import ua.com.foxminded.domain.Service;
import ua.com.foxminded.domain.Student;

class StudentsGeneratorTest {
    private static Properties properties = new Properties();

    private CourseDAO courseDAO;
    private GroupDAO groupDAO;
    private StudentDAO studentDAO;
    private StudentsCoursesDAO studentsCoursesDAO;
    private Service service;
    private StudentsGenerator studentsGenerator;

    @BeforeAll
    static void setPropertiesForQueries() throws SQLException, IOException {
        try (InputStream inputStream = StudentsGenerator.class.getResourceAsStream("/" + "queries for tests.properties")) {
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
        studentsGenerator = new StudentsGenerator(service);
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
    void shouldCreateTwoHundredRandomStudents() {
        studentsGenerator.createTwoHundredRandomStudents();
        ArrayList<Student> allStudents = (ArrayList<Student>) studentDAO.findAll();
        assertEquals(200, allStudents.size());
    }

    @Test
    void shouldSetCorrectRandomGroupsId() throws SQLException {
        GroupsGenerator groupsGenerator = new GroupsGenerator(service);
        groupsGenerator.createTenGroups();
        studentsGenerator.createTwoHundredRandomStudents();
        studentsGenerator.setRandomGroupsForStudents();
        ArrayList<Integer> allGroupsId = new ArrayList<>();
        groupDAO.findAll().stream().forEach(group -> allGroupsId.add(group.getGroupId()));
        String query = properties.getProperty("get.all.setted.groups");
        ArrayList<Integer> allSettedGroupId = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                allSettedGroupId.add(resultSet.getInt("group_id"));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        allSettedGroupId.stream().forEach(groupId -> assertTrue(allGroupsId.contains(groupId) || groupId == 0));
    }

    @Test
    void shouldSetCorrectRandomCoursesAndStudentsId() throws SQLException {	
        CoursesGenerator coursesGenerator = new CoursesGenerator(service);
        coursesGenerator.createTenCourses();
        studentsGenerator.createTwoHundredRandomStudents();
        studentsGenerator.setRandomCoursesForStudents();
        ArrayList<Integer> allCoursesId = new ArrayList<>();
        courseDAO.findAll().stream().forEach(course -> allCoursesId.add(course.getCourseId()));
        ArrayList<Integer> allStudentsId = new ArrayList<>();
        studentDAO.findAll().stream().forEach(student -> allStudentsId.add(student.getStudentId()));
        ArrayList<Integer> allSettedStudentsId = new ArrayList<>();
        ArrayList<Integer> allSettedCoursesId = new ArrayList<>();
        String query = properties.getProperty("get.all.setted.student_id.and.course_id");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                allSettedStudentsId.add(resultSet.getInt("student_id"));
                allSettedCoursesId.add(resultSet.getInt("course_id"));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        allSettedCoursesId.stream().forEach(courseId -> assertTrue(allCoursesId.contains(courseId)));
        allSettedStudentsId.stream().forEach(studentId -> assertTrue(allStudentsId.contains(studentId)));
    }

    @Test
    void shouldSetFromOneToThreeCoursesForStudent() throws SQLException {
        CoursesGenerator coursesGenerator = new CoursesGenerator(service);
        coursesGenerator.createTenCourses();
        studentsGenerator.createTwoHundredRandomStudents();
        studentsGenerator.setRandomCoursesForStudents();
        ArrayList<Student> allStudents = (ArrayList<Student>) studentDAO.findAll();
        String query = properties.getProperty("get.list.of.courses.for.one.student");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            for (int i = 0; i < allStudents.size(); i++) {
                preparedStatement.setInt(1, allStudents.get(i).getStudentId());
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Integer> resultCoursesId = new ArrayList<>();
                while (resultSet.next()) {
                    resultCoursesId.add(resultSet.getInt("course_id"));
                }
                assertTrue(resultCoursesId.size() > 0 && resultCoursesId.size() <= 3);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
