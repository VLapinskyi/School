package ua.com.foxminded.ui.data_generator;

import ua.com.foxminded.domain.University;

class CoursesGenerator {
	
	private University university;
	
	public CoursesGenerator (University university) {
		this.university = university;
	}

	public void createTenCourses () {
		university.addCourse("math", "room 100");
		university.addCourse("biology", "room 102");
		university.addCourse("english", "room 103");
		university.addCourse("ukranian", "room 104");
		university.addCourse("chemistry", "room 106");
		university.addCourse("geography", "room 107");
		university.addCourse("physical education", "room 108");
		university.addCourse("art", "room 109");
		university.addCourse("drawing", "room 110");
		university.addCourse("russian", "room 105");
	}
}
