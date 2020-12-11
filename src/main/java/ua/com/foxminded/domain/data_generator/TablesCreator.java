package ua.com.foxminded.domain.data_generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ua.com.foxminded.dao.TablesCreatorDAO;

class TablesCreator {
	public void createTables (String sqlScriptName) {
		StringBuilder query = new StringBuilder();
		try (BufferedReader readerScript = new BufferedReader(new InputStreamReader(getSQLScriptFromResources(sqlScriptName)))) {
			String line = null;
			while((line = readerScript.readLine()) != null) {
				query.append(line);
			}
		} catch (IOException exception) {
			System.out.println(exception);
		}
		
		TablesCreatorDAO tablesCreatorDAO = new TablesCreatorDAO();
		tablesCreatorDAO.createTables(query.toString());
	}
	
	private InputStream getSQLScriptFromResources (String sqlScriptName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(sqlScriptName);
		if (inputStream == null) {
			throw new IllegalArgumentException("File not found! " + sqlScriptName);
		} else {
			return inputStream;
		}
	}
}
