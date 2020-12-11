package ua.com.foxminded.domain.data_generator;

import ua.com.foxminded.dao.CourseDAO;

class CoursesGenerator {
	public void writeTenCourses () {
		CourseDAO courseDAO = new CourseDAO();
		courseDAO.writeCourse("math", "room 100");
		courseDAO.writeCourse("biology", "room 102");
		courseDAO.writeCourse("english", "room 103");
		courseDAO.writeCourse("ukranian", "room 104");
		courseDAO.writeCourse("russian", "room 105");
		courseDAO.writeCourse("chemistry", "room 106");
		courseDAO.writeCourse("geography", "room 107");
		courseDAO.writeCourse("physical education", "room 108");
		courseDAO.writeCourse("art", "room 109");
		courseDAO.writeCourse("drawing", "room 110");
	}
}
