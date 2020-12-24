package ua.com.foxminded.ui.data_generator;

public class DataGenerator {
	public void generateRandomData() {
		TablesCreator tablesCreator = new TablesCreator();
		tablesCreator.createTables("Creating Tables.sql");
		GroupsGenerator groupsGenerator = new GroupsGenerator();
		groupsGenerator.createTenGroups();
		CoursesGenerator coursesGenerator = new CoursesGenerator();
		coursesGenerator.createTenCourses();
		StudentsGenerator studentsGenerator = new StudentsGenerator();
		studentsGenerator.createTwoHundredRandomStudents();
		studentsGenerator.setRandomGroupsForStudents();
		studentsGenerator.setRandomCoursesForStudents();
	}
}
