package ua.com.foxminded.ui;

import java.util.ArrayList;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.University;
import ua.com.foxminded.domain.data_generator.DataGenerator;

public class Launch {
	public static void main (String [] args) {
		DataGenerator datagenerator = new DataGenerator();
		datagenerator.generateRandomData();
		University university = new University();
		ArrayList<Group> groupsWithNoMoreTenStudents = university.getGroupsWithNoMoreStudentsThan(10);
		ArrayList<Student> studentsOnCourse = university.getStudentsFromCourseByName("math");
		university.addStudent("Valentyn", "Lapinskyi");
		university.deleteStudentById(1);
		university.addStudentToCourse(2, 3);
		university.deleteStudentFromCourse(2, 3);
	} 
}
