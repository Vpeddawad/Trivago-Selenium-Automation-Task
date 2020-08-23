package com.base.aplication.trivago;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.base.aplication.trivago.SerachPaneTrivagoFixture.DATE_TYPE;
import com.base.fixture.Fixture;
import com.base.logger.TestLogger;

/*
 * generic fixture to work in the calendar checkin and checkout date. 
 * @TODO we can cover all the functionality related to calendar.
 * 
 */

public class Calendar extends Fixture {
	public Calendar() {

	}

	public static Logger log = TestLogger.getLogger(Calendar.class);

	public void selectMonth(String month) {
		log.info("select the month :" + month);
		String month_xpath = "//span[text()='" + month + "']";

		if (!isElementPresentAndVisible(month_xpath, 5000)) {
			clicknextMonth();
		}
	}

	public void clicknextMonth() {
		log.info("click next button.");
		String cal_next_xpath = "//button[@class='cal-btn-next']";
		click(cal_next_xpath);
	}

	public void selectDate(DATE_TYPE datetype, String month, String date) {

		String SELECT_DATE_XPATH = "//time[@datetime='" + date + "']";
		String key;
		if (datetype.equals(DATE_TYPE.checkin)) {
			key = "checkInButton";
		} else {
			key = "checkOutButton";
		}
		String DATE_TYPE_XPATH = "//button[@key='" + key + "']";
		click(DATE_TYPE_XPATH);

		selectMonth(month);
		// work for only September only right now
		waitforElementTobeClicklable(SELECT_DATE_XPATH);

		log.info("select the date:" + date + " to the calendar for " + datetype);
		click(SELECT_DATE_XPATH);
	}

	public void click(String date_locator) {
		waitforElementTobeClicklable(date_locator);
		webDriver.findElement(By.xpath(date_locator)).click();
	}
}
