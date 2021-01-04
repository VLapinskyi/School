package ua.com.foxminded.dao.settings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConnectionFactoryTest {

    @Test
    void shouldGetConnection() {
        assertNotNull(ConnectionFactory.getConnection());
    }
}
