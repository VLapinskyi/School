package ua.com.foxminded.ui.data_generator;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.University;

public class DataGenerator {
	public void generateRandomData() {
		University university = new University (new GroupDAO(), new StudentDAO(), new CourseDAO(), new StudentsCoursesDAO());
		TablesCreator tablesCreator = new TablesCreator();
		tablesCreator.createTables("Creating Tables.sql");
		GroupsGenerator groupsGenerator = new GroupsGenerator(university);
		groupsGenerator.createTenGroups();
		CoursesGenerator coursesGenerator = new CoursesGenerator(university);
		coursesGenerator.createTenCourses();
		StudentsGenerator studentsGenerator = new StudentsGenerator(university);
		studentsGenerator.createTwoHundredRandomStudents();
		studentsGenerator.setRandomGroupsForStudents();
		studentsGenerator.setRandomCoursesForStudents();
	}
}
