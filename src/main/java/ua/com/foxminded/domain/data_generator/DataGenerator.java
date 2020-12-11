package ua.com.foxminded.domain.data_generator;

public class DataGenerator {
	public void generateRandomData() {
		TablesCreator tablesCreator = new TablesCreator();
		tablesCreator.createTables("Creating Tables.sql");
		GroupsGenerator groupsGenerator = new GroupsGenerator();
		groupsGenerator.writeTenGroups();
		CoursesGenerator coursesGenerator = new CoursesGenerator();
		coursesGenerator.writeTenCourses();
		StudentsGenerator studentsGenerator = new StudentsGenerator();
		studentsGenerator.writeTwoHundredStudents();
		studentsGenerator.setRandomGroupsForStudents();
		studentsGenerator.setRandomCoursesForStudents();
	}
}
