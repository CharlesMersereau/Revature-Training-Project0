package com.revature.util;

import org.apache.log4j.Logger;

public class LoggerUtil {
	private static Logger logger = Logger.getRootLogger();
	
	public static void fatal(String m) {
		logger.fatal(m);
	}

	public static void error(String m) {
		logger.error(m);
	}

	public static void warn(String m) {
		logger.warn(m);
	}

	public static void debug(String m) {
		logger.debug(m);
	}

	public static void info(String m) {
		logger.info(m);
	}

	public static void trace(String m) {
		logger.trace(m);
	}
}
