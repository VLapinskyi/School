package ua.com.foxminded.ui;

import ua.com.foxminded.domain.data_generator.CoursesGenerator;
import ua.com.foxminded.domain.data_generator.GroupsGenerator;
import ua.com.foxminded.domain.data_generator.StudentsGenerator;
import ua.com.foxminded.domain.data_generator.TablesCreator;

public class Launch {
	public static void main (String [] args) {
		TablesCreator tablesCreator = new TablesCreator();
		tablesCreator.createTables("Creating Tables.sql");
		GroupsGenerator groupsGenerator = new GroupsGenerator();
		groupsGenerator.generateTenGroups();
		CoursesGenerator coursesGenerator = new CoursesGenerator();
		coursesGenerator.generateTenCourses();
		StudentsGenerator studentsGenerator = new StudentsGenerator();
		studentsGenerator.generateTwoHundredStudents();
	}
}
