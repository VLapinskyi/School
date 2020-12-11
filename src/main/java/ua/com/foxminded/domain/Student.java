package ua.com.foxminded.domain;

import java.util.ArrayList;

public class Student {
	private int studentId;
	private int groupId;
	private String firstName;
	private String lastName;
	private ArrayList<Course> courses = new ArrayList<>();
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public ArrayList<Course> getCourses() {
		return courses;
	}
	public void addCourse(Course course) {
		courses.add(course);
	}	
}
