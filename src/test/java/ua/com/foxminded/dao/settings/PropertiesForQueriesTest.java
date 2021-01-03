package ua.com.foxminded.dao.settings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PropertiesForQueriesTest {

	@Test
	void shouldGetQueries() {
		assertNotEquals(null, PropertiesForQueries.getQueries());
	}

}
