package ua.com.foxminded.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;

class ServiceTest {
    @Mock private CourseDAO courseDAO;
    @Mock private GroupDAO groupDAO;
    @Mock private StudentDAO studentDAO;
    @Mock private StudentsCoursesDAO studentsCoursesDAO;

    private Service service;

    @BeforeEach
    void init () {
        MockitoAnnotations.openMocks(this);
        service = new Service(groupDAO, studentDAO, courseDAO, studentsCoursesDAO);
    }

    @Test
    void shouldAddCourseIntoDataBase() {
        String courseName = "math";
        String courseDescription = "room 101";
        service.addCourse(courseName, courseDescription);
        verify(courseDAO).create(any(Course.class));
    }

    @Test
    void shouldInvokeFindAllForCourses() {
        service.getAllCourses();
        verify(courseDAO).findAll();
    }

    @Test
    void shouldAddGroupIntoDatabase() {
        String groupName = "QW-01";
        service.addGroup(groupName);
        verify(groupDAO).create(any(Group.class));
    }

    @Test
    void shouldInvokeFindAllForGroups() {
        service.getAllGroups();
        verify(groupDAO).findAll();
    }

    @Test
    void shouldGetGroupsWithNoMoreThanThreeStudents() {
        ArrayList <Group> allGroups = new ArrayList<>(Arrays.asList(
                new Group(1, "QW-01"),
                new Group(2, "AS-02"),
                new Group(3, "ZX-03"),
                new Group(4, "ER-04"),
                new Group(5, "DF-05")));
        when(groupDAO.findAll()).thenReturn(allGroups);
        when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(0).getGroupId())).thenReturn(1);
        when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(1).getGroupId())).thenReturn(2);
        when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(2).getGroupId())).thenReturn(3);
        when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(3).getGroupId())).thenReturn(4);
        when(studentDAO.getNumberOfStudentsInGroup(allGroups.get(4).getGroupId())).thenReturn(5);
        ArrayList<Group> expectedGroups = new ArrayList<>(Arrays.asList(
                new Group(1, "QW-01"),
                new Group(2, "AS-02"),
                new Group(3, "ZX-03")));
        assertEquals(expectedGroups, service.getGroupsWithNoMoreStudentsThan(3));
        verify(groupDAO).findAll();
        verify(studentDAO, times(allGroups.size())).getNumberOfStudentsInGroup(anyInt());
    }
    
    @Test
    void shouldSetGroupForStudent() {
        service.setGroupForStudent(anyInt(), anyInt());
        verify(studentDAO).setGroupForStudent(anyInt(), anyInt());
    }

    @Test
    void shouldReturnStudentsFromCourseByCourseName() {
        int courseId = 1;
        String courseName = "math";
        String courseDescription = "room 101";
        Course course = new Course(courseId, courseName, courseDescription);
        when(courseDAO.findByName(courseName)).thenReturn(course);
        service.getStudentsFromCourseByCourseName(courseName);
        verify(courseDAO).findByName(courseName);
        verify(studentsCoursesDAO).getStudentsFromCourse(course.getCourseId());
    }

    @Test
    void shouldAddStudentIntoDatabase() {
        String firstName = "Valentyn";
        String lastName = "Lapinskyi";
        service.addStudent(firstName, lastName);
        verify(studentDAO).create(any(Student.class));
    }

    @Test
    void shouldDeleteStudentFromDatabase() {
        service.deleteStudentById(anyInt());
        verify(studentDAO).deleteById(anyInt());
    }

    @Test
    void shouldAddStudentToCourseInDatabase() {
        service.addStudentToCourse(anyInt(), anyInt());
        verify(studentsCoursesDAO).addStudentToCourse(anyInt(), anyInt());
    }

    @Test
    void shouldDeleteStudentFromCourseInDatabase() {
        service.deleteStudentFromCourse(anyInt(), anyInt());
        verify(studentsCoursesDAO).deleteStudentFromCourse(anyInt(), anyInt());
    }

    @Test
    void shouldInvoveFindAllForStudents() {
        service.getAllStudents();
        verify(studentDAO).findAll();
    }
}
