package ua.com.foxminded.domain.data_generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

class StudentsGenerator {
	private Random random = new Random();
	public void writeTwoHundredStudents() {
		ArrayList <String> twentyFirstNames = new ArrayList<String> (Arrays.asList("Oleksandr", "Ismet", "Vladyslav",
				"Andrii", "Denys", "Oleksii", "Serhii", "Stanislav", "Inha", "Oleh", "Ievhenii", "Dmytro",
				"Anastasiia", "Sofiia", "Volodymyr", "Anna", "Bakhtior", "Artem", "Ihor", "Nataliia"));
		ArrayList<String> twentyLastNames = new ArrayList<String>(Arrays.asList("Iashchenko", "Iaremenko","Iatsenko",
				"Iasko", "Iaroshenko", "Ianiuk", "Iamborak", "Iakoviuk", "Iakovenko", "Iakymenko", "Iahnichenko",
				"Iushchenko", "Iushko", "Iuvzhenko", "Shchurenko", "Shcherbina", "Shumeiko", "Shuliak", "Shulha",
				"Shtoma"));
		ArrayList<Student> twoHundredStudents = generateTwoHundredStudents(twentyFirstNames, twentyLastNames);
		StudentDAO studentDAO = new StudentDAO();
		twoHundredStudents.stream().forEach(studentDAO :: writeStudent);
	}

	public void setRandomGroupsForStudents () {
		StudentDAO studentDAO = new StudentDAO();
		ArrayList<Student> students = studentDAO.getAllStudents();
		GroupDAO groupDAO = new GroupDAO();
		ArrayList<Group> groups = groupDAO.getAllGroups();
		HashMap<Integer, Integer> relationsBetweenId = setRelationsBetweenId(students, groups);	//HashTable <studentId, groupId>
		relationsBetweenId.forEach((studentId, groupId) -> {
			studentDAO.writeGroupForStudent(studentId, groupId);
		});
	}

	public void setRandomCoursesForStudents() {
		StudentDAO studentDAO = new StudentDAO();
		ArrayList<Student> students = studentDAO.getAllStudents();
		CourseDAO courseDAO = new CourseDAO();
		ArrayList<Course> courses = courseDAO.getAllCourses();
		students.stream().forEach(student -> {
			int numberOfCoursesForStudent = random.nextInt(3);
			for (int i = 0; i <= numberOfCoursesForStudent; i++) {
				int randomIndex = random.nextInt(courses.size());
				if (!(student.getCourses().contains(courses.get(randomIndex))))
					student.addCourse(courses.get(randomIndex));
			}
		});
		StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();		
		students.stream().filter(student -> !(student.getCourses().isEmpty())).forEach(student -> 
		student.getCourses().stream().forEach(course -> 
		studentsCoursesDAO.addStudentToCourse(student.getStudentId(), course.getCourseId())));
	}

	private ArrayList<Student> generateTwoHundredStudents(ArrayList<String> twentyFirstNames, ArrayList<String> twentyLastNames) {
		ArrayList<Student> twoHundredStudents = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			Student student = new Student();
			student.setFirstName(twentyFirstNames.get(random.nextInt(twentyFirstNames.size())));
			student.setLastName(twentyLastNames.get(random.nextInt(twentyLastNames.size())));
			twoHundredStudents.add(student);
		}
		return twoHundredStudents;
	}

	private HashMap<Integer, Integer> setRelationsBetweenId (ArrayList<Student> students, ArrayList<Group> groups) {
		HashMap<Integer, Integer> resultId = new HashMap<>();	//HashTable <studentId, groupId>
		for (int i = 0; i < students.size(); i++) {
			int studentId = random.nextInt(students.size())+1;
			int groupId = random.nextInt(groups.size())+1;
			resultId.put(studentId, groupId);
		}

		for (int i = 0; i < groups.size(); i++) {
			Integer currentGroupId = Integer.valueOf(groups.get(i).getGroupId());
			int numberOfStudentsInGroup = (int) resultId.values().stream().filter(currentGroupId :: equals).count();
			if (numberOfStudentsInGroup < 10 && numberOfStudentsInGroup > 30) {
				resultId.forEach((studentId, groupId) -> {
					if (groupId.equals(currentGroupId))
						resultId.remove(studentId);
				});
			}
		}

		return resultId;		
	}
}
