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
import ua.com.foxminded.domain.Group;

public class GroupDAO implements GenericDAO<Group> {
    private static Logger logger = LogManager.getLogger(GroupDAO.class);
    private Properties properties = PropertiesCreator.getProperties("queries.properties");

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
        Group group = null;
        String query = properties.getProperty("find.group.by.id");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String groupName = resultSet.getString("group_name");
            group = new Group(id, groupName);
        } catch (SQLException e) {
            logger.error("cannot get group by id", e);
        }
        return group;
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
        String query = properties.getProperty("update.group");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, group.getGroupName());
            preparedStatement.setInt(2, group.getGroupId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("cannot update group", e);
        }
    }
    
    @Override
    public void deleteById(int id) {
        String query = properties.getProperty("delete.group.by.id");
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("cannot delete group by id", e);
        }
    }
}
