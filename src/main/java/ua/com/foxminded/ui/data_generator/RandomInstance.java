package ua.com.foxminded.ui.data_generator;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomInstance {
	private static Logger logger = LogManager.getLogger(RandomInstance.class);
	
	private RandomInstance () {
		logger.error("Utility class RandomInstance");
		throw new IllegalStateException();
	}
	
	public static Random getRandomInstance () {
		Random randomInstance = null;
		try {
			randomInstance = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			logger.error("something is wrong with SecureRandom instance in GroupsGenerator.class", e);
		}
		return randomInstance;
	}
}
