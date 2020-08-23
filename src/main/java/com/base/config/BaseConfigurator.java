package com.base.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.base.config.error.ConfigurationError;
import com.base.logger.TestLogger;

public class BaseConfigurator {

	public static final String TestRunner_ENCODING = "UTF-8";

	public static final String DEFAULT_PROPERTIES_RESOURCE = "/com/base/config/defaults.properties";

    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    protected final HashMap<String, String> confMap = new HashMap<String, String>();
	public static final String TestRunner_RESOURCEBASE = "testrunner.resource.base";
	protected static BaseConfigurator INSTANCE = null;

	private static File TestRunnerHome = null;
    protected static Properties defaults = new Properties();

	protected static final String TEST_TIMEOUT = "TestRunner.test.timeout";

	protected static final String TOTAl_TIMEOUT = "TestRunner.total.timeout";

    protected static final String CLICK_TIMEOUT = "click.timeout";

    protected static final String PAGE_LOAD_TIMEOUT = "pageload.timeout";

    protected static final String BROWSER_COMMAND = "browser.command";

    protected static final String TEST_URL = "test.url";

    public static final String TEST_OUTPUT_VERBOSITY = "test.log.verbosity";

    public static final String TEST_OUTPUT_PATTERN = "test.log.pattern";

    public static final String TEST_OUTPUT_FILENAME = "test.output.file";

//    static {
//        try {
//          defaults.load(BaseConfigurator.class.getResourceAsStream(DEFAULT_PROPERTIES_RESOURCE));
//        } catch (IOException e) {
//            throw new ConfigurationError(e.getMessage(), e);
//        }
//    }
	private static Logger logger = TestLogger.getLogger(BaseConfigurator.class);
    public static String getResourceBase() {
		String base = getConfigPropertyString(TestRunner_RESOURCEBASE).replace('\\', '/');
        if (!base.startsWith("http://")) {
            File baseDir = new File(base);
            if (baseDir.isAbsolute()) {
                base = baseDir.toURI().toString();
            } else {
                base = base.replace('\\', '/');
            }
        }
        return base.endsWith("/") ? base : (base += "/"); // make sure it ends with "/"
    }

    public static Map<String, String> getAll() {
        // return a copy of original map to prevent potential data corruption
        return new HashMap<String, String>(getInstance().confMap);
    }

    public static String getTestURL() {
        return getConfigPropertyString(TEST_URL);
    }

	public static String getBrowser() {
		return getConfigPropertyString(BROWSER_COMMAND);
	}

    public static String getTestOutputVerbosity() {
        return getConfigPropertyString(TEST_OUTPUT_VERBOSITY);
    }

    public static String getTestOutputPattern() {
        return getConfigPropertyString(TEST_OUTPUT_PATTERN);
    }

    public static int getClickTimeout() {
        return Integer.parseInt(getConfigPropertyString(CLICK_TIMEOUT));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getConfigPropertyString(PAGE_LOAD_TIMEOUT));
    }
    public static File getSeleniumOutputFile() {
        return getConfigPropertyFile(TEST_OUTPUT_FILENAME, null);
    }


    public static void initialize(Properties prop) {
        INSTANCE = new BaseConfigurator(prop);
    }

	public static File getTestRunnerHome() {
		String TestRunner_home = System.getenv("TestRunner_HOME");
		if (TestRunner_home == null) {
			TestRunnerHome = new File(System.getProperty("user.dir"));
        } else {
			TestRunnerHome = new File(TestRunner_home);
        }
		return TestRunnerHome;
    }

    public static File getConfDir() {
		return new File(getTestRunnerHome(), "conf");
    }

	public static Properties getConfigurationFromTestRunnerPropFile(String configuration) {
        File confFile;
        if (configuration.endsWith(".properties")) {
            confFile = new File(getConfDir(), (configuration));
        } else {
            confFile = new File(getConfDir(), configuration + ".properties");
        }
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(confFile));
        } catch (IOException e) {
            throw new ConfigurationError(e.getMessage(), e);
        }
        return props;
    }

    protected BaseConfigurator() {
        setDefaultsProperties(TEST_OUTPUT_PATTERN);
        setDefaultsProperties(TEST_OUTPUT_FILENAME);
        setDefaultsProperties(TEST_OUTPUT_VERBOSITY);
        setDefaultsProperties(TEST_TIMEOUT);
        setDefaultsProperties(TEST_OUTPUT_PATTERN);
        setDefaultsProperties(BROWSER_COMMAND);
        setDefaultsProperties(TEST_URL);
        setDefaultsProperties(PAGE_LOAD_TIMEOUT);
        setDefaultsProperties(CLICK_TIMEOUT);
    }



    public static String getBrowserCommand() {
        return BROWSER_COMMAND;
    }

    public void setDefaultsProperties(String option) {
        String prop_Value = defaults.getProperty(option);
        if (prop_Value != null && prop_Value.trim().isEmpty()) {
            prop_Value = null;
        }
        confMap.put(option, prop_Value);
    }
    protected BaseConfigurator(Properties prop) {
        this();
		SortedSet<String> sortedKeys = new TreeSet<String>(prop.stringPropertyNames());
        for (String origKey: sortedKeys) {
            String key = origKey;
            String value = prop.getProperty(origKey);

            if (confMap.containsKey(key)) {
                if (value != null && !value.isEmpty()) {
                    String printableValue = value;

                    logger.info("Loading property: " + key + " = " + printableValue);
                    confMap.put(key, value);
                }
            } else {
                logger.warn("Property '" + key
						+ "' is ignored as it is not supported by TestRunner!");
            }
        }
    }

    public static BaseConfigurator getInstance() {

        if (INSTANCE == null) {
            // throw new ConfigurationError("Configurator is not initialized!");
            INSTANCE = new BaseConfigurator();
        }
        return INSTANCE;
    }



    public static String getOption(String optionName) {
        return getConfigPropertyString(optionName);
    }

    protected static String getConfigPropertyString(String optionName) {
        String value = getInstance().confMap.get(optionName);
        return value == null ? null : value.trim();
    }

    protected static File getConfigPropertyFile(String optionName, String defaultValue) {
        String value = getInstance().confMap.get(optionName);
        if (value == null) {
			return (defaultValue == null) ? null : new File(getTestRunnerHome(), defaultValue);
        }
        return new File(value);
    }

    protected static List<String> getList(String optionName) {
      
		return getList(optionName, "[," + PATH_SEPARATOR + "]");
	}

    protected static List<String> getList(String optionName, String splitRegex) {
        List<String> list = new ArrayList<String>();
        String value = getInstance().confMap.get(optionName);
        if (value != null) {
            list = Arrays.asList(value
					.split("[ \\t]*" + splitRegex + "[ \\t]*"));
        }
        return list;
    }

    private static File getLogFile(String logFileName) {
        File logFile = null;
        if (logFileName != null && !logFileName.trim().isEmpty()) {
            logFile = new File(logFileName);
            if (!logFile.isAbsolute()) {
				logFile = new File(getTestRunnerHome(), "logs/" + logFileName);
            }
        }
        return logFile;
    }

    protected static int getConfigPropertyInt(String optionName) {
        try {
            return Integer.parseInt(getInstance().confMap.get(optionName));
        } catch (Exception e) {
            throw new ConfigurationError("Invalid value for property '"
                    + optionName + "'. Please set valid integer value.", e);
        }

    }

    protected static List<File> getConfigPropertyFiles(String optionName) {
        List<File> files = new ArrayList<File>();
        for (String filePath : getList(optionName)) {
            files.add(new File(filePath));
        }
        return files;
    }

    protected static boolean getConfigPropBoolean(String optionName) {
        return Boolean.parseBoolean(getInstance().confMap.get(optionName));
    }


}
