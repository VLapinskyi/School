package ua.com.foxminded.ui;

import java.util.ArrayList;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.dao.TablesCreatorDAO;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.Service;
import ua.com.foxminded.ui.data_generator.DataGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.CoursesGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.GroupsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.StudentsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

public class Launch {
    public static void main (String [] args) {
        Service service = new Service(new GroupDAO(), new  StudentDAO(), new CourseDAO(), new StudentsCoursesDAO()); 
        DataGenerator datagenerator = new DataGenerator(new TablesCreator(new TablesCreatorDAO()),
                new GroupsGenerator(service), new CoursesGenerator(service), new StudentsGenerator(service));
        datagenerator.generateRandomData(); 
        ArrayList<Group> groupsWithNoMoreTenStudents = (ArrayList<Group>) service.getGroupsWithNoMoreStudentsThan(10);
        ArrayList<Student> studentsOnCourse = (ArrayList<Student>) service.getStudentsFromCourseByCourseName("math");
        service.addStudent("Valentyn", "Lapinskyi");
        service.deleteStudentById(1);
        service.addStudentToCourse(2, 3);
        service.deleteStudentFromCourse(2, 3);

    } 
}
