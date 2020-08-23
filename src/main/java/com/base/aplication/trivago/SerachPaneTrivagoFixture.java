package com.base.aplication.trivago;

import org.openqa.selenium.By;
/*
 * to work in the search to the application triavgo ,populating the data to the serch and save . 
 * 
 */

import com.base.exception.SeleniumException;
import com.base.fixture.Fixture;

public class SerachPaneTrivagoFixture extends Fixture {

	private String SEARCH_XPATH = "//span[@class='search-button__label'][text()='Search']";
	private String HOTEL_XPATH = "//input[@type='search']";

	enum DATE_TYPE {
		checkin, checkout
	}

	public SerachPaneTrivagoFixture() {

	}

	public void populateAllSearchDetails(String HotelOrPlaceName, String month, String checkinDate,
			String checkoutDate) {
		log.info("Enter hotel name to the seacrh field:" + HotelOrPlaceName);
		enterHotelNameOrDestination(HotelOrPlaceName);
		webDriver.findElement(By.xpath(HOTEL_XPATH)).click();
		getElement(HOTEL_XPATH).click();
		Calendar calender = new Calendar();
		log.info("Select checkin date:" + checkinDate);
		acceptCokkiee();
		calender.selectDate(DATE_TYPE.checkin, month, checkinDate);
		webDriver.findElement(By.xpath(HOTEL_XPATH)).click();
		log.info("Select checkout date:" + checkoutDate);
		calender.selectDate(DATE_TYPE.checkout, month, checkoutDate);
	}
	
	public void acceptCokkiee() {

		String Cokkie_xpath = "//button[text()='OK']";

		if (isElementPresent(Cokkie_xpath, 5000)) {
			pause(1000);// to appears properly
			getElement(Cokkie_xpath).click();

		}
	}

	private void enterHotelNameOrDestination(String hotelName) {
		waitforElementTobeClicklable(HOTEL_XPATH);
		getElement(HOTEL_XPATH).sendKeys(hotelName);
		String HOTEL_SUGGESTION = "//div[@class='ssg-suggestion__info']//mark[text()='" + hotelName + "']";
		if (isElementPresent(HOTEL_SUGGESTION, 5000)) {
			getElement(HOTEL_SUGGESTION).click();
		} else {
			throw new SeleniumException("No suggestion found for" + HOTEL_SUGGESTION);
		}
	}

	public void search() {
		waitforElementTobeClicklable(SEARCH_XPATH);
		getElement(SEARCH_XPATH).click();
	}
}
