package ua.com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Course;

public class CourseDAO implements GenericDAO<Course>{
	private static Logger logger = LogManager.getLogger(CourseDAO.class);
	private Properties properties = PropertiesForDAO.getQueries();

	@Override
	public void create(Course course) {
		String query = properties.getProperty("create.course");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, course.getCourseName());
			preparedStatement.setString(2, course.getCourseDescription());
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot insert into course", e);
		}
	}

	@Override
	public List<Course> findAll() {
		ArrayList<Course> allCourses = new ArrayList<>();
		String query = properties.getProperty("find.all.courses");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int courseId = resultSet.getInt("course_id");
				String courseName = resultSet.getString("course_name");
				String courseDescription = resultSet.getString("course_description");
				Course course = new Course(courseId, courseName, courseDescription);
				allCourses.add(course);
			}
		} catch (SQLException e) {
			logger.error("cannot get all courses", e);
		}
		return allCourses;
	}
	
	public Course findByName(String name) {
		Course course = null;
		String query = properties.getProperty("find.course.by.name");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int courseId = resultSet.getInt("course_id");
			String courseDescription = resultSet.getString("course_description");
			course = new Course(courseId, name, courseDescription);
		} catch (SQLException e) {
			logger.error("cannot get course by name", e);
		} 
		return course;
	}
	
	@Override
	public Course findById (int id) {
		logger.error("method findById() from CourseDAO.class not implemented");
		throw new UnsupportedOperationException("method findById() from CourseDAO.class not implemented");
	}

	@Override
	public void update(Course course) {
		logger.error("method update() from CourseDAO.class not implemented");
		throw new UnsupportedOperationException("method update() from CourseDAO.class not implemented");
	}

	@Override
	public void deleteById(int id) {
		logger.error("method deleteById() from CourseDAO.class not implemented");
		throw new UnsupportedOperationException("method deleteById() from CourseDAO.class not implemented");
	}
	
}
