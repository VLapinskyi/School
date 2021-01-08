package ua.com.foxminded.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.dao.settings.ConnectionFactory;

public class TablesCreatorDAO {
    private static Logger logger = LogManager.getLogger(TablesCreatorDAO.class);

    public void createTables (String sqlScriptName) {
        String query = this.getQueryFromScript(sqlScriptName);
        try (PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("cannot create table", e); 
        }
    }

    private String getQueryFromScript(String sqlScriptName) {
        StringBuilder queryBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                TablesCreatorDAO.class.getResourceAsStream("/" + sqlScriptName)))) {
            String line = null;
            while((line = reader.readLine()) != null) {
                queryBuilder.append(line);
            }
        } catch (IOException e) {
            logger.error("cannot get SQLScript from resources", e);
        }
        return queryBuilder.toString();
    }
}
