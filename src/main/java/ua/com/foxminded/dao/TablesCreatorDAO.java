package ua.com.foxminded.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TablesCreatorDAO {
	private static Logger logger = LogManager.getLogger(TablesCreatorDAO.class);
	
	public void createTables (String query) {
		try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
					preparedStatement.execute();
		} catch (SQLException e) {
			logger.error("cannot create table", e); 
		}
	}
}
