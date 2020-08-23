package com.base.fixture;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.base.config.Configurator;
import com.base.logger.TestLogger;
/*
 * this has the starting method with the URL defined in the selenium,properites file 
 *every step definition class must implement this .
 * 
 */

public abstract class SeleniumTestCase {

	public static ThreadLocal<WebDriver> threadDriver = null;
	public final Logger log;

	protected SeleniumTestCase() {
		Configurator.getInstance();
		TestLogger.initlizedLogger();
		log = TestLogger.getLogger(this.getClass());
	}

	/*
	 * the test will start with this method. url is read by the configurator.java
	 * from the selenium.properitesd
	 * 
	 * @return driver objects.
	 */
	@SuppressWarnings("unchecked")
	public WebDriver start() {
		log.info("started the test :" + Configurator.getTestURL());
		return start(Configurator.getTestURL());
	}

	/*
	 * the test will start with this method user can give there own URL as well .
	 * 
	 * @param url url to start with
	 * 
	 * @return driver objects.
	 */
	public WebDriver start(String url) {
		Configurator config = Configurator.getInstance();
		threadDriver = new ThreadLocal<WebDriver>();
		WebDriver driver = null;
		String browser = config.getBrowser();
		if (config.getBrowser().equalsIgnoreCase("*chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/main/resources/drivers/chrome/84/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (config.getBrowser().equalsIgnoreCase("*firefox")) {

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/src/main/resources/drivers/firefox/geckodriver.exe");

			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		threadDriver.set(driver);
		threadDriver.get().get(url);
		FixtureWebdriver.setDriver(threadDriver.get());
		return threadDriver.get();
	}

	public void stop() {
		threadDriver.get().quit();
	}

	public WebDriver getDriver() {
		return threadDriver.get();
	}

}
