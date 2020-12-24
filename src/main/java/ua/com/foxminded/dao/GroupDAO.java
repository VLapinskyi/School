package ua.com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.domain.Group;

public class GroupDAO implements GenericDAO<Group> {
	private static Logger logger = LogManager.getLogger(GroupDAO.class);
	Properties properties = PropertiesForDAO.getQueries();
	@Override
	public void create(Group group) {
		String query = properties.getProperty("create.group");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, group.getGroupName());
			preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot write group", e);
		}
	}
	@Override
	public List<Group> findAll() {
		ArrayList<Group> groups = new ArrayList<>();
		String query = properties.getProperty("find.all.groups");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int groupId = resultSet.getInt("group_id");
				String groupName = resultSet.getString("group_name");
				Group group = new Group(groupId, groupName);
				groups.add(group);
			}
		} catch (SQLException e) {
			logger.error("cannot get all groups", e);
		}
		return groups;
	}
	@Override
	public Group findById(int id) {
		logger.error("method findById() from GroupDAO.class not implemented");
		throw new UnsupportedOperationException("method findById() from GroupDAO.class not implemented");
	}
	public Group findByName (String groupName) {
		Group group = null;
		String query = properties.getProperty("find.group.by.name");
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, groupName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int groupId = resultSet.getInt("group_id");
			group = new Group(groupId, groupName);
		} catch (SQLException e) {
			logger.error("cannot find group by name", e);
		}
		return group;
	}
	@Override
	public void update(Group group) {
		logger.error("method update() from GroupDAO.class not implemented");
		throw new UnsupportedOperationException("method update() from GroupDAO.class not implemented");
	}
	@Override
	public void deleteById(int id) {
		logger.error("method deleteById() from GroupDAO.class not implemented");
		throw new UnsupportedOperationException("method deleteById() from GroupDAO.class not implemented");
	}
}
