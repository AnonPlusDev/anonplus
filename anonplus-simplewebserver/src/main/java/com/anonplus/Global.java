package com.anonplus;

import org.apache.log4j.Logger;

public class Global {
	static boolean DEBUG = true;
	static Logger logger = Logger.getLogger(Global.class);
	
	public static void message(String m)	{
		System.out.println (m);
	}
	
	public static void logInfoMessage(String message) {
		logger.info(message);
	}
	
	public static void logDebugMessage(String message) {
		logger.debug(message);
	}

	public static void logWarnMessage(String message) {
		logger.warn(message);
	}

	public static void logFatalMessage(String message) {
		logger.fatal(message);
	}
	
	public static void logErrorMessage(String message) {
		logger.error(message);
	}	
}
