package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.dao.settings.ConnectionFactory;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

class StudentDAOTest {
    private static Properties properties = new Properties();
    
    private ArrayList<Student> allStudents;
    private StudentDAO studentDAO;
    private ArrayList<Group> allGroups;
    private GroupDAO groupDAO;

    @BeforeAll
    static void setPropertiesForQueries() throws SQLException, IOException {
        try (InputStream inputStream = StudentDAOTest.class.getResourceAsStream("/" + "queries for tests.properties")) {
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
        studentDAO = new StudentDAO();
        allStudents = new ArrayList<>(Arrays.asList(
                new Student(1, "Myroslava", "Ruban"),
                new Student(2, "Oksana", "Bochkovska"),
                new Student(3, "Viktoriia", "Vovk"),
                new Student(4, "Nataliia", "Khodorkovska"),
                new Student(5, "Tetiana", "Shcherbak"),
                new Student(6, "Anna", "Sydorenko"),
                new Student(7, "Iuliia", "Mostunenko"),
                new Student(8, "Kateryna", "Lytvynenko"),
                new Student(9, "Liliia", "Leonenko"),
                new Student(10, "Nataliia", "Gudym")));
        groupDAO = new GroupDAO();
        allGroups = new ArrayList<>(Arrays.asList(
                new Group(1, "QW-01"),
                new Group(2, "AS-02"),
                new Group(3, "ZX-03"),
                new Group(4, "ER-04"),
                new Group(5, "DF-05")));
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
    void shouldCreateStudentInDatabase() throws SQLException {
        String firstName = "Valentyn";
        String lastName = "Lapinskyi";
        Student student = new Student(firstName, lastName);
        studentDAO.create(student);
        Student resultStudent = null;
        String query = properties.getProperty("get.student.for.test");
        try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            resultStudent = new Student(resultSet.getString("first_name"), resultSet.getString("last_name"));
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        assertEquals(student, resultStudent);
    }

    @Test
    void shouldFindAllStudents() {
        for (int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        ArrayList<Student> expectedStudents = new ArrayList<>();
        expectedStudents.addAll(allStudents);
        ArrayList<Student> actualStudents = (ArrayList<Student>) studentDAO.findAll();
        assertTrue(expectedStudents.containsAll(actualStudents) && actualStudents.containsAll(expectedStudents));
    }

    @Test
    void shouldFindStudentById() {
        for(int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        Student expectedStudent = new Student(1, "Myroslava", "Ruban");
        Student actualStudent = studentDAO.findById(1);
        assertEquals(expectedStudent, actualStudent);

    }

    @Test
    void shouldFindByName() {
        for (int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        Student expectedStudent = new Student(10, "Nataliia", "Gudym");
        ArrayList<Student> actualStudents = (ArrayList<Student>) studentDAO.findByName("Nataliia", "Gudym");
        assertTrue(actualStudents.contains(expectedStudent));
    }

    @Test
    void shouldUpdateStudent() {
        for (int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        Student student = new Student(1, "Roman", "Dudchenko");
        studentDAO.update(student);
        Student resultStudent = studentDAO.findById(1);
        assertEquals(student, resultStudent);
    }

    @Test
    void shouldSetGroupForStudent() throws SQLException {
        for(int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        for (int i = 0; i < allGroups.size(); i++) {
            groupDAO.create(allGroups.get(i));
        }

        HashMap<Student, Integer> expectedStudentWithGroupId = new HashMap<>();
        expectedStudentWithGroupId.put(new Student(5, "Tetiana", "Shcherbak"), 1);
        studentDAO.setGroupForStudent(5, 1);
        HashMap<Student, Integer> actualStudentWithGroupId = new HashMap<>();
        String query = properties.getProperty("get.student.with.group.by.id.for.test");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, 5);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int groupId = resultSet.getInt("group_id");
                actualStudentWithGroupId.put(new Student(studentId, firstName, lastName), groupId);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        assertEquals(expectedStudentWithGroupId, actualStudentWithGroupId);
    }

    @Test
    void shouldDeleteStudentById() {
        for (int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        Student deletedStudent = new Student(1, "Myroslava", "Ruban");
        studentDAO.deleteById(1);
        ArrayList<Student> allStudentsInDatabase = (ArrayList<Student>) studentDAO.findAll();
        assertTrue(!allStudentsInDatabase.contains(deletedStudent));
    }

    @Test
    void shouldGetNumberOfStudentsInGroup() {
        for(int i = 0; i < allStudents.size(); i++) {
            studentDAO.create(allStudents.get(i));
        }
        for (int i = 0; i < allGroups.size(); i++) {
            groupDAO.create(allGroups.get(i));
        }
        studentDAO.setGroupForStudent(1, 1);
        studentDAO.setGroupForStudent(3, 1);
        studentDAO.setGroupForStudent(5, 1);
        studentDAO.setGroupForStudent(7, 1);
        int expectedResult = 4;
        int actualResult = studentDAO.getNumberOfStudentsInGroup(1);
        assertEquals(expectedResult, actualResult);
    }
}
