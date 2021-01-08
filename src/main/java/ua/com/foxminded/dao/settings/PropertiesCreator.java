package ua.com.foxminded.dao.settings;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertiesCreator {
    private static Logger logger = LogManager.getLogger(PropertiesCreator.class);

    private PropertiesCreator() {
        logger.error("PropertiesForQueries is utility class, so there is no reason to create object");
        throw new IllegalStateException("Utility class");
    }

    public static Properties getProperties (String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesCreator.class.getResourceAsStream("/" + fileName)) {
            if (inputStream == null) 
                throw new SQLException();
            properties.load(inputStream);
        } catch (SQLException e) {
            logger.error("cannot load properties file", e);
        } catch (IOException e) {
            logger.error("cannot find properties file", e);
        }
        return properties;
    }
}
