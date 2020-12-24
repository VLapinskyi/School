package ua.com.foxminded.ui;

import java.util.ArrayList;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.University;
import ua.com.foxminded.ui.data_generator.DataGenerator;

public class Launch {
	public static void main (String [] args) {
		DataGenerator datagenerator = new DataGenerator();
		datagenerator.generateRandomData();
		
		University university = new University(new GroupDAO(), new  StudentDAO(), new CourseDAO(), new StudentsCoursesDAO()); 
		 ArrayList<Group> groupsWithNoMoreTenStudents = (ArrayList<Group>) university.getGroupsWithNoMoreStudentsThan(10);
		 ArrayList<Student> studentsOnCourse = (ArrayList<Student>) university.getStudentsFromCourseByCourseName("math");
		 university.addStudent("Valentyn", "Lapinskyi");
		 university.deleteStudentById(1);
		 university.addStudentToCourse(2, 3);
		 university.deleteStudentFromCourse(2, 3);
		 
	} 
}
