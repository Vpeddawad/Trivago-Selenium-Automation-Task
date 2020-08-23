package com.base.logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.base.config.Configurator;

/*
 * to generate the log.
 */
public class TestLogger {
	// creates pattern layout
	//

	public static final String CONVERSION_PATTERN = "<%d> %-5p %c [%t] (%x) - %m%n";// <%d> %-5p %c [%t] (%x) - %m%n

	public static final String fileName = "selenium_log.log";

	public static Logger initlizedLogger() {
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern(CONVERSION_PATTERN);
		// creates console appender
		ConsoleAppender consoleAppender = new ConsoleAppender();
		consoleAppender.setLayout(layout);
		consoleAppender.activateOptions();

		// creates file appender
		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile(Configurator.getSeleniumOutputFile().getName());
		fileAppender.setLayout(layout);
		fileAppender.activateOptions();

		// configures the root logger
		Logger rootLogger = Logger.getRootLogger();

		if (Configurator.getTestOutputVerbosity().toUpperCase().equalsIgnoreCase("info")) {
			rootLogger.setLevel(Level.INFO);
		} else if (Configurator.getTestOutputVerbosity().toUpperCase().equalsIgnoreCase("debug")) {
			rootLogger.setLevel(Level.DEBUG);
		} else {
			rootLogger.setLevel(Level.ALL);
		}

		rootLogger.addAppender(consoleAppender);
		rootLogger.addAppender(fileAppender);
		return rootLogger;

	}

	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static Logger getLogger(String name) {
		return LogManager.getLogger("TestLoger." + name);
	}

}
