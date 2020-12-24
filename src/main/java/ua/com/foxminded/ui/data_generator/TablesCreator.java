package ua.com.foxminded.ui.data_generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.foxminded.dao.TablesCreatorDAO;

class TablesCreator {
	private static Logger logger = LogManager.getLogger(TablesCreator.class);
	public void createTables (String sqlScriptName) {
		StringBuilder query = new StringBuilder();
		try (BufferedReader readerScript = new BufferedReader(new InputStreamReader(getSQLScriptFromResources(sqlScriptName)))) {
			String line = null;
			while((line = readerScript.readLine()) != null) {
				query.append(line);
			}
		} catch (IOException exception) {
			logger.error("cannot get SQLScript from resources", exception);
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
