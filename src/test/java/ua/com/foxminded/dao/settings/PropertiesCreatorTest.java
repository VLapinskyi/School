package ua.com.foxminded.dao.settings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PropertiesCreatorTest {

    @Test
    void shouldGetProperties() {
        assertNotNull(PropertiesCreator.getProperties("log in database.properties"));
    }
}
