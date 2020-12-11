package ua.com.foxminded.domain;

import java.util.ArrayList;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;

public class University {
	public ArrayList<Group> getGroupsWithNoMoreStudentsThan(int requestedNumber) {
		GroupDAO groupDAO = new GroupDAO();
		StudentDAO studentDAO = new StudentDAO();
		ArrayList<Group> allGroups = groupDAO.getAllGroups();
		ArrayList<Group> resultGroups = new ArrayList<>();
		allGroups.stream().forEach(group -> {
			int numberOfStudentsInGroup = studentDAO.getNumberOfStudentsInGroup(group.getGroupId());
			if (numberOfStudentsInGroup <= requestedNumber)
				resultGroups.add(group);
		});
		return resultGroups;
	}
	
	public ArrayList<Student> getStudentsFromCourseByName (String courseName) {
		CourseDAO courseDAO = new CourseDAO();
		int courseId = courseDAO.getCourseIdByName (courseName);
		StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
		ArrayList<Integer> studentIdOnCourse = studentsCoursesDAO.getStudentsIdFromCourse(courseId);
		StudentDAO studentDAO = new StudentDAO();
		ArrayList<Student> studentsOnCourse = new ArrayList<>();
		studentIdOnCourse.stream().forEach(studentId -> studentsOnCourse.add(studentDAO.getStudentById(studentId)));
		return studentsOnCourse;
	}
	
	public void addStudent(String firstName, String lastName) {
		Student student = new Student();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		StudentDAO studentDAO = new StudentDAO();
		studentDAO.writeStudent(student);
	}
	
	public void deleteStudentById (int studentId) {
		StudentDAO studentDAO = new StudentDAO();
		studentDAO.deleteStudentById(studentId);
	}
	
	public void addStudentToCourse (int studentId, int courseId) {
		StudentsCoursesDAO studentsCorusesDAO = new StudentsCoursesDAO();
		studentsCorusesDAO.addStudentToCourse(studentId, courseId);
	}
	
	public void deleteStudentFromCourse (int studentId, int courseId) {
		StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
		studentsCoursesDAO.deleteStudentFromCourse(studentId, courseId);
	}
}
