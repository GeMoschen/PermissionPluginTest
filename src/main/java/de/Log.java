package de;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	private final static Logger logger = LoggerFactory.getLogger(Log.class);

	public static void info(String message) {
		logger.info(message);
	}
}
