package ua.com.foxminded.ui.data_generator;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.Service;

public class DataGenerator {
	public void generateRandomData() {
		Service service = new Service (new GroupDAO(), new StudentDAO(), new CourseDAO(), new StudentsCoursesDAO());
		TablesCreator tablesCreator = new TablesCreator();
		tablesCreator.createTables("Creating Tables.sql");
		GroupsGenerator groupsGenerator = new GroupsGenerator(service);
		groupsGenerator.createTenGroups();
		CoursesGenerator coursesGenerator = new CoursesGenerator(service);
		coursesGenerator.createTenCourses();
		StudentsGenerator studentsGenerator = new StudentsGenerator(service);
		studentsGenerator.createTwoHundredRandomStudents();
		studentsGenerator.setRandomGroupsForStudents();
		studentsGenerator.setRandomCoursesForStudents();
	}
}
