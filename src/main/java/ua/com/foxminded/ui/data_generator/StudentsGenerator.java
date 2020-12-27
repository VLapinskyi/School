package ua.com.foxminded.ui.data_generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.University;

class StudentsGenerator {
	private static Random random = RandomInstance.getRandomInstance();
	private University university;
	
	public StudentsGenerator (University university) {
		this.university = university;
	}
	
	public void createTwoHundredRandomStudents() {
		ArrayList <String> twentyFirstNames = new ArrayList<> (Arrays.asList("Oleksandr", "Ismet", "Vladyslav",
				"Andrii", "Denys", "Oleksii", "Serhii", "Stanislav", "Inha", "Oleh", "Ievhenii", "Dmytro",
				"Anastasiia", "Sofiia", "Volodymyr", "Anna", "Bakhtior", "Artem", "Ihor", "Nataliia"));
		ArrayList<String> twentyLastNames = new ArrayList<>(Arrays.asList("Iashchenko", "Iaremenko","Iatsenko",
				"Iasko", "Iaroshenko", "Ianiuk", "Iamborak", "Iakoviuk", "Iakovenko", "Iakymenko", "Iahnichenko",
				"Iushchenko", "Iushko", "Iuvzhenko", "Shchurenko", "Shcherbina", "Shumeiko", "Shuliak", "Shulha",
				"Shtoma"));
		for (int i = 0; i < 200; i++) {
			university.addStudent(twentyFirstNames.get(random.nextInt(20)), twentyLastNames.get(random.nextInt(20)));
		}
	}

	public void setRandomGroupsForStudents () {
		ArrayList<Student> students = (ArrayList<Student>) university.getAllStudents();
		ArrayList<Group> groups = (ArrayList<Group>) university.getAllGroups();
		for (int i = 0; i < students.size(); i++) {
			int studentId = students.get(random.nextInt(students.size())).getStudentId();
			int groupId = groups.get(random.nextInt(groups.size())).getGroupId();
			university.setGroupForStudent(studentId, groupId);
		}
	}

	public void setRandomCoursesForStudents() {
		ArrayList<Student> students = (ArrayList<Student>) university.getAllStudents();
		ArrayList<Course> courses = (ArrayList<Course>) university.getAllCourses();
		students.stream().forEach(student -> {
			int numberOfCoursesForStudent = random.nextInt(3)+1;
			int failedAttemp = 0;
			ArrayList<Integer> randomNumberList = new ArrayList<>();
			for (int i = 0; i < numberOfCoursesForStudent + failedAttemp; i++) {
				int randomIndex = random.nextInt(courses.size());
				if (randomNumberList.contains(randomIndex)) {
					failedAttemp++;
				} else {
					university.addStudentToCourse(student.getStudentId(), courses.get(randomIndex).getCourseId());
					randomNumberList.add(randomIndex);
				}
			}
		});
	}
}
