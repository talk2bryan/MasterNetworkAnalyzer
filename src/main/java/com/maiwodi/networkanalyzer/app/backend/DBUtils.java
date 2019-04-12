package com.maiwodi.networkanalyzer.app.backend;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtils {
	private static final Logger LOGGER = LogManager.getLogger(DBUtils.class.getName());

	private static final String PROP_FILE_NAME = "config.properties";

	public static String loadProp(String key) {

		InputStream inputStream = null;
		Properties properties = new Properties();
		String value = "";

		inputStream = DBUtils.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
		if (inputStream == null) {
			LOGGER.debug("Sorry, unable to find {}", PROP_FILE_NAME);
		} else {
			LOGGER.debug("Successfully found the property file: {}", PROP_FILE_NAME);
		}

		try {
			properties.load(inputStream);
			value = properties.getProperty(key);
			LOGGER.debug("Property value: {}", value);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}
}
