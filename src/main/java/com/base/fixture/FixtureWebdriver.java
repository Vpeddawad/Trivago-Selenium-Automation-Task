package com.base.fixture;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.base.logger.TestLogger;
/*
 * Fixture base class to get the instance of the webdriver that can be used in the implementing class.
 * 
 */

public class FixtureWebdriver {
	public static final Logger log = TestLogger.getLogger(FixtureWebdriver.class);
    static WebDriver driver;
    public static ThreadLocal<WebDriver> threadDriver = null;

    public static void setDriver(WebDriver driver) {
        FixtureWebdriver.driver = driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public RemoteWebElement getElement(By by) {
        RemoteWebElement element = null;
        RemoteWebDriver driver = (RemoteWebDriver) getDriver();
        try {
            element = (RemoteWebElement) driver.findElement(by);
        } catch (NoSuchElementException e) {
            log.info("Did not find element using:" + by.toString()
					+ " with candidate locators.");
        }
        return element;
    }

}
