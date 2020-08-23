package com.base.config;

import java.util.Properties;
/*
 * This class is to read the property file under ./conf/selenium.properites
 * first the default property should load and then the selenium.properity will get loaded.
 * user has an access to updated anytime with his requirement.
 */

public class Configurator extends BaseConfigurator {

	public static Configurator INSTANCE;
	static {
		initialize(getConfigurationFromTestRunnerPropFile("selenium.properties"));
	}

	Configurator() {
		super();
	}

	Configurator(Properties prop) {

	}

	@Override
	public void setDefaultsProperties(String option) {
		// TODO Auto-generated method stub
		super.setDefaultsProperties(option);
	}

	public static Configurator getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Configurator();
		return INSTANCE;

	}

}
