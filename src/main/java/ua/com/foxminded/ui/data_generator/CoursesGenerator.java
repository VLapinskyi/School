package ua.com.foxminded.ui.data_generator;

import ua.com.foxminded.domain.Service;

class CoursesGenerator {
	
	private Service service;
	
	public CoursesGenerator (Service service) {
		this.service = service;
	}

	public void createTenCourses () {
		service.addCourse("math", "room 100");
		service.addCourse("biology", "room 102");
		service.addCourse("english", "room 103");
		service.addCourse("ukranian", "room 104");
		service.addCourse("chemistry", "room 106");
		service.addCourse("geography", "room 107");
		service.addCourse("physical education", "room 108");
		service.addCourse("art", "room 109");
		service.addCourse("drawing", "room 110");
		service.addCourse("russian", "room 105");
	}
}
