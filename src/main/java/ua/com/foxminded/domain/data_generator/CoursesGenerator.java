package ua.com.foxminded.domain.data_generator;

import ua.com.foxminded.dao.CourseDAO;

public class CoursesGenerator {
	public void generateTenCourses () {
		CourseDAO courseDAO = new CourseDAO();
		courseDAO.insertCourse("math", "room 100");
		courseDAO.insertCourse("biology", "room 102");
		courseDAO.insertCourse("english", "room 103");
		courseDAO.insertCourse("ukranian", "room 104");
		courseDAO.insertCourse("russian", "room 105");
		courseDAO.insertCourse("chemistry", "room 106");
		courseDAO.insertCourse("geography", "room 107");
		courseDAO.insertCourse("physical education", "room 108");
		courseDAO.insertCourse("art", "room 109");
		courseDAO.insertCourse("drawing", "room 110");
	}
}
