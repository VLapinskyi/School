package ua.com.foxminded.ui.data_generator.generator_parts;

import ua.com.foxminded.dao.TablesCreatorDAO;

public class TablesCreator {
    private static final String SQL_SCRIPT_NAME = "Creating Tables.sql";
    private TablesCreatorDAO tablesCreatorDAO;

    public TablesCreator(TablesCreatorDAO tablesCreatorDAO) {
        this.tablesCreatorDAO = tablesCreatorDAO;
    }
    
    public void createTables () {
        tablesCreatorDAO.createTables(SQL_SCRIPT_NAME);
    }
}
