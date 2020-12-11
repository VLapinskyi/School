package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Group;

public class GroupDAO {
	private static final String URL = "jdbc:postgresql://127.0.0.1:5432/sql-jdbc-school";
	private static final String USER = "postgres";
	private static final String PASSWORD = "123456";

	private static Logger logger = LogManager.getLogger(GroupDAO.class);

	public void writeGroup (String groupName) {
		String query = "INSERT INTO groups (group_name) VALUES (?)";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, groupName);
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot write group", e);
		}
	}
	public ArrayList<Group> getAllGroups () {
		ArrayList<Group> groups = new ArrayList<>();
		String query = "SELECT * FROM groups";
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Group group = new Group();
				group.setGroupId(resultSet.getInt("group_id"));
				group.setGroupName(resultSet.getString("group_name"));
				groups.add(group);
			}
		} catch (SQLException e) {
			logger.error("cannot get all groups", e);
		}
		return groups;
	}
}
