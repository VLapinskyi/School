package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.dao.settings.ConnectionFactory;

class TablesCreatorDAOTest {
    private final static String SQL_SCRIPT_NAME = "Creating Tables.sql";
    private static Properties properties = new Properties();
    private TablesCreatorDAO tablescreatorDAO;

    @BeforeAll
    static void setPropertiesForQueries() throws SQLException, IOException {
        try (InputStream inputStream = TablesCreatorDAOTest.class.getResourceAsStream("/" + "queries for tests.properties")) {
            if (inputStream == null) 
                throw new SQLException();
            properties.load(inputStream);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @BeforeEach
    void init() {
        tablescreatorDAO = new TablesCreatorDAO();
    }

    @AfterEach
    void clearData() throws SQLException {
        String query = properties.getProperty("delete.tables");
        try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void shouldCreateTables() throws SQLException {
        tablescreatorDAO.createTables(SQL_SCRIPT_NAME);
        String query = properties.getProperty("check.existing.tables");
        ArrayList<String> actualTablesNames = new ArrayList<>();
        ArrayList<String> expectedTablesNames = new ArrayList<>(Arrays.asList(
                "STUDENTS", "COURSES", "GROUPS", "STUDENTS_COURSES"));
        try(PreparedStatement preparedStatement = ConnectionFactory.getConnection().prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                actualTablesNames.add(resultSet.getString("TABLE_NAME"));
            }
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        }

        assertTrue(actualTablesNames.containsAll(expectedTablesNames) && expectedTablesNames.containsAll(actualTablesNames));
    }
}
