package ua.com.foxminded.ui.data_generator.generator_parts;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.com.foxminded.dao.TablesCreatorDAO;

class TablesCreatorTest {
	@Mock private TablesCreatorDAO tablesCreatorDAO;
	private TablesCreator tablesCreator;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		tablesCreator = new TablesCreator(tablesCreatorDAO);
	}
	
	@Test
	void shouldInvokeCreateTablesDAO() {
		tablesCreator.createTables();
		verify(tablesCreatorDAO).createTables(anyString());
	}

}
