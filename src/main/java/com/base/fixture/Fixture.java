package com.base.fixture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.config.Configurator;
import com.base.exception.SeleniumException;
import com.base.logger.TestLogger;

/*
 * Fixture is base class that will have the common utility methods related to the web elements .
 * every page must implement this to use the feature .
 * 
 */
public abstract class Fixture {

	private static final long NANO = 1000 * 1000 * 1000;

	public static Logger log = TestLogger.getLogger(Fixture.class);

	private final HashMap<String, String> parameters = new HashMap<String, String>();
	public WebDriver webDriver = null;

	protected int clickretry;

	public WebDriver getWebDriver() {
		return FixtureWebdriver.getDriver();
	}

	public void setWebdriver(WebDriver driver) {
		FixtureWebdriver.setDriver(driver);
		this.webDriver = FixtureWebdriver.getDriver();
	}

	public void prepare() {

	}

	public Fixture() {
		this((WebDriver) null);
	}

	Fixture(WebDriver driver) {
		if (driver != null) {
			FixtureWebdriver.setDriver(driver);

		}
		this.webDriver = FixtureWebdriver.getDriver();

	}

	public Fixture(String... params) {
		this();
		for (String param : params) {
			try {
				String name = param.substring(0, param.indexOf('=')).trim();
				String value = param.substring(param.indexOf('=') + 1).trim();
				if (!parameters.containsKey(name))
					parameters.put(name, value);
				else
					log.warn("Duplicate parameter '" + name + "' given.  Ignoring...");
			} catch (Exception e) {
				log.error("Error evaulating Solvent Parameter '" + param
						+ "'.  Correct format is 'name=value'. This parameter will be ignored.\n" + e.getMessage());
			}
		}
	}

	public RemoteWebElement getElement(By by) {
		RemoteWebElement element = null;
		RemoteWebDriver driver = (RemoteWebDriver) webDriver;
		try {
			element = (RemoteWebElement) driver.findElement(by);
		} catch (NoSuchElementException e) {
			log.info("Did not find element using:" + by.toString()
					+ " This is usually a normal part of probing with candidate locators.");
		}
		return element;
	}

	public static void scrollTo(String xpath) {
		((JavascriptExecutor) FixtureWebdriver.getDriver()).executeScript("arguments[0].scrollIntoView();",
				FixtureWebdriver.getDriver().findElement(By.xpath(xpath)));

	}

	public static void validatetextAvaliableTothePage(String text) {
		// webDriver.getPageSource();
		List<WebElement> hotel_list = FixtureWebdriver.getDriver()
				.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
		Assert.assertTrue(text + " not found!", hotel_list.size() > 0);
	}

	public static String switchToTab() {
		FixtureWebdriver.getDriver().getWindowHandle();
		ArrayList<String> availableWindows = new ArrayList<String>(FixtureWebdriver.getDriver().getWindowHandles());
		if (!availableWindows.isEmpty()) {
			FixtureWebdriver.getDriver().switchTo().window(availableWindows.get(1));
			return FixtureWebdriver.getDriver().getCurrentUrl();
		}
		return "";
	}

	public void waitForConditionInCurrentWindow(final String scriptToEvaluate, int timeout) {
		long start = System.nanoTime();
		try {
			final WebDriver driver = webDriver;
			ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {

					String errorMessage = "Document is not ready!";
					try {
						errorMessage = "Failed to execute script: " + scriptToEvaluate;
						JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
						return (Boolean) javascriptExecutor.executeScript("return " + scriptToEvaluate + ";");
					} catch (Exception e) {
						log.error(errorMessage, e);
						return false;
					}
				}
			};

			long timeoutInSeconds = 5;
			(new WebDriverWait(driver, timeoutInSeconds)).until(expectedCondition);
		} catch (Exception e) {
			String message = (scriptToEvaluate) + "  " + e.getMessage();
			throw new SeleniumException("ERROR: waitForConditionInCurrentWindow() timed out! " + message);
		}
		float delta = (System.nanoTime() - start) / NANO;
		log.info("ELAPSED in waitForConditionInCurrentWindow()= " + delta);
	}

	public RemoteWebElement getElement(String locator) {
		RemoteWebElement element = null;
		if (locator != null) {
			if (locator.startsWith("id=")) {
				element = getElement(By.id(locator.substring(3)));
			} else if (locator.startsWith("css=")) {
				element = getElement(By.cssSelector(locator.substring(4)));
			} else if (locator.startsWith("xpath=")) {
				element = getElement(By.xpath(locator.substring(6)));
			} else if (locator.startsWith("link=")) {
				element = getElement(By.linkText(locator.substring(5)));
			} else if (locator.startsWith("name=")) {
				element = getElement(By.name(locator.substring(5)));
			} else {
				element = getElement(By.xpath(locator));
				// didn't find the element by xpath so try again by id
				if (element == null) {
					element = getElement(By.id(locator));
				}
				// didn't find the element by id so try again by name
				if (element == null) {
					element = getElement(By.name(locator));
				}
			}
		}
		return element;
	}

	public void pause(long milisec) {
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isElementPresent(final String locator, int wait) {
		int timeout = (int) Math.round(wait / 1000.0);
		WebDriverWait wdWait = new WebDriverWait(webDriver, timeout);
		ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return getElement(locator) != null;
			}
		};
		try {
			wdWait.until(expectedCondition);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementVisible(final String locator, int wait) {
		boolean visible = false;
		int timeout = (int) Math.round(wait / 1000.0);
		WebDriverWait wdWait = new WebDriverWait(webDriver, timeout);
		ExpectedCondition<Boolean> expectedCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				RemoteWebElement element = getElement(locator);
				return (element != null) && element.isDisplayed();
			}
		};
		try {
			wdWait.until(expectedCondition);
			visible = true;
		} catch (Exception e) {
			visible = false;
		}
		if (!visible) {
			log.info("element not visible.  Locator: " + locator);
		}
		return visible;
	}

	public void waitforElementTobeClicklable(String xpath) {
		WebDriverWait wait = new WebDriverWait(webDriver, Configurator.getClickTimeout() / 1000);
		wait.until(ExpectedConditions.elementToBeClickable(getWebDriver().findElement(By.xpath(xpath))));
	}

	public void waitforElementTobecomeInvisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(webDriver, Configurator.getClickTimeout() / 1000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	}

	public void waitforElementTobecomeVisible(String xpath) {// 50 sec.
		WebDriverWait wait = new WebDriverWait(webDriver, Configurator.getClickTimeout() / 1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public void waitforElementTobecomeVisible(String xpath, int timer) {// 50 sec.
		WebDriverWait wait = new WebDriverWait(webDriver, timer / 1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public boolean isElementPresentAndVisible(String locator, int wait) {
		long start = System.nanoTime();
		boolean result = false;
		// first check if element is in the DOM
		if (isElementPresent(locator, wait)) {
			// then check if scroll attempt is needed
			result = isElementVisible(locator, 0);
			if (!result) {
				try {
					log.info("Attempting scrollTo since element " + locator + " is present but not visible.");
					scrollTo(locator);
				} catch (Exception e) {
					log.info("Exception attempting to scroll to element", e);
				}
				// since scroll attempt was needed, re-check if the element is now visible
				result = isElementVisible(locator, wait);
			}
		}
		float delta = (System.nanoTime() - start) / NANO;
		log.info("ELAPSED in isElementPresentAndVisible()= " + delta + " locator: " + locator + "returning: " + result);
		return result;
	}

	public void end() {
		getWebDriver().close();
	}
}
