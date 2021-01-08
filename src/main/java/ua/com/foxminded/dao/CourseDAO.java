package ua.com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.dao.settings.ConnectionFactory;
import ua.com.foxminded.dao.settings.PropertiesCreator;
import ua.com.foxminded.domain.Course;

public class CourseDAO implements GenericDAO<Course>{
    private static Logger logger = LogManager.getLogger(CourseDAO.class);
    private Properties properties = PropertiesCreator.getProperties("queries.properties");

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
        Course course = null;
        String query = properties.getProperty("find.course.by.id");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String courseName = resultSet.getString("course_name");
            String courseDescription = resultSet.getString("course_description");
            course = new Course(id, courseName, courseDescription);
        } catch (SQLException e) {
            logger.error("cannot get course by id", e);
        }
        return course;
    }

    @Override
    public void update(Course course) {
        String query = properties.getProperty("update.course");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseDescription());
            preparedStatement.setInt(3, course.getCourseId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("cannot update course", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = properties.getProperty("delete.course.by.id");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("cannot delete course", e);
        }
    }
}
