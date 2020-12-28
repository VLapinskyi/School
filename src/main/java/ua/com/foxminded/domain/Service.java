package ua.com.foxminded.domain;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.dao.CourseDAO;
import ua.com.foxminded.dao.GroupDAO;
import ua.com.foxminded.dao.StudentDAO;
import ua.com.foxminded.dao.StudentsCoursesDAO;

public class Service {
	private GroupDAO groupDAO;
	private StudentDAO studentDAO;
	private CourseDAO courseDAO;
	private StudentsCoursesDAO studentsCoursesDAO;
	
	public Service (GroupDAO groupDAO, StudentDAO studentDAO, CourseDAO courseDAO, StudentsCoursesDAO studentsCoursesDAO) {
		this.groupDAO = groupDAO;
		this.studentDAO = studentDAO;
		this.courseDAO = courseDAO;
		this.studentsCoursesDAO = studentsCoursesDAO;
	}
	
	public void addCourse (String courseName, String courseDescription) {
		Course course = new Course (courseName, courseDescription);
		courseDAO.create(course);
	}
	
	public List<Course> getAllCourses () {
		return courseDAO.findAll();
	}
	
	public void addGroup (String groupName) {
		Group group = new Group(groupName);
		groupDAO.create(group);
	}
	
	public List<Group> getAllGroups () {
		return groupDAO.findAll();
	}

	public List<Group> getGroupsWithNoMoreStudentsThan(int requestedNumber) {
		ArrayList<Group> allGroups = (ArrayList<Group>) groupDAO.findAll();
		ArrayList<Group> resultGroups = new ArrayList<>();
		allGroups.stream().forEach(group -> {
			int numberOfStudentsInGroup = studentDAO.getNumberOfStudentsInGroup(group.getGroupId());
			if (numberOfStudentsInGroup <= requestedNumber)
				resultGroups.add(group);
		});
		return resultGroups;
	}
	
	public void setGroupForStudent(int studentId, int groupId) {
		studentDAO.setGroupForStudent(studentId, groupId);
	}
	
	public List<Student> getStudentsFromCourseByCourseName (String courseName) {
		Course course = courseDAO.findByName(courseName);
		return (ArrayList<Student>) studentsCoursesDAO.getStudentsFromCourse(course.getCourseId());
	}
	
	public void addStudent(String firstName, String lastName) {
		Student student = new Student(firstName, lastName);
		studentDAO.create(student);
	}
	
	public void deleteStudentById (int studentId) {
		studentDAO.deleteById(studentId);
	}
	
	public void addStudentToCourse (int studentId, int courseId) {
		studentsCoursesDAO.addStudentToCourse(studentId, courseId);
	}
	
	public void deleteStudentFromCourse (int studentId, int courseId) {
		studentsCoursesDAO.deleteStudentFromCourse(studentId, courseId);
	}
	
	public List<Student> getAllStudents () {
		return studentDAO.findAll();
	}
}
