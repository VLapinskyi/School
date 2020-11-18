package ua.com.foxminded.ui;

import ua.com.foxminded.domain.data_generator.TablesCreator;

public class Launch {
	public static void main (String [] args) {
		TablesCreator tablesCreator = new TablesCreator();
		tablesCreator.createTables("Creating Tables.sql");
	}
}
