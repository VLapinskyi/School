package ua.com.foxminded.ui.data_generator;
 
import ua.com.foxminded.ui.data_generator.generator_parts.CoursesGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.GroupsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.StudentsGenerator;
import ua.com.foxminded.ui.data_generator.generator_parts.TablesCreator;

public class DataGenerator {
	private TablesCreator tablesCreator;
	private GroupsGenerator groupsGenerator;
	private CoursesGenerator coursesGenerator;
	private StudentsGenerator studentsGenerator;
	
	public DataGenerator(TablesCreator tablesCreator, GroupsGenerator groupsGenerator,
			CoursesGenerator coursesGenerator, StudentsGenerator studentsGenerator) {
		this.tablesCreator = tablesCreator;
		this.groupsGenerator = groupsGenerator;
		this.coursesGenerator = coursesGenerator;
		this.studentsGenerator = studentsGenerator;
	}
	
	public void generateRandomData() {
		tablesCreator.createTables();
		groupsGenerator.createTenGroups();
		coursesGenerator.createTenCourses();
		studentsGenerator.createTwoHundredRandomStudents();
		studentsGenerator.setRandomGroupsForStudents();
		studentsGenerator.setRandomCoursesForStudents();
	}
}
