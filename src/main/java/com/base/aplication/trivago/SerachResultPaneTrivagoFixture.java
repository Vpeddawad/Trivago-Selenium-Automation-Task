package com.base.aplication.trivago;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.base.aplication.trivago.hotel.Hotel;
import com.base.exception.SeleniumException;
import com.base.fixture.Fixture;

public class SerachResultPaneTrivagoFixture extends Fixture {

	private String ROOT_XPATH = "//div[@class='container_itemlist']";
	private String LIST_ALL_HOTEL_CPATH = ROOT_XPATH
			+ "//li[contains(@class,'hotel-item')]";

	private ArrayList<Hotel> hotels = new ArrayList<Hotel>();

	public SerachResultPaneTrivagoFixture() {

		prepare();
	}

	ArrayList<Hotel> getHotellist() {
		return hotels;
	}

	public void prepare() {
		waitforElementTobecomeVisible(ROOT_XPATH);

	}

////div[@class='container_itemlist']//li[2][contains(@class,'hotel-item')]//h3[@itemprop='name']
	public Hotel getResultAtIndex(int index) {
		String hotelName_xpath = ROOT_XPATH + "//li[" + index
				+ "][contains(@class,'hotel-item')]//h3[@itemprop='name']";
		String loest_price = ROOT_XPATH + "//li[" + index
				+ "]//button[@data-qa='cheapest-deal']//span[contains(@class,'accommodation-list__price')]";
		waitforElementTobecomeVisible(hotelName_xpath);
		String hotel_name = webDriver.findElement(By.xpath(hotelName_xpath)).getText();
		String hotel_lowest_price = webDriver.findElement(By.xpath(loest_price)).getText();

		Hotel hotel = new Hotel();
		hotel.setHotelName(hotel_name);
		char y = hotel_lowest_price.charAt(0);
		hotel.sethotel_OUR_Loest_Price(hotel_lowest_price.substring(1, hotel_lowest_price.length()));
		return hotel;
	}

	public void clickOurLowestPrice(int index) {
		String resut = ROOT_XPATH + "//li[" + index
				+ "]//button[@data-qa='cheapest-deal']//span[contains(@class,'accommodation-list__price')]";
		waitforElementTobecomeVisible(resut);
			if (isElementPresent(resut, 5000)) {
		webDriver.findElement(By.xpath(resut)).click();
		} else {
			throw new SeleniumException("Our lowest price no avaliable.");

	}
	}

	

	public void clickOur_lower_price_FreeCancellation() {
		String resut = ROOT_XPATH
				+ "//span[contains(@class,'slideouts__container')]//span[contains(text() ,'Our lowest price with')]//span[contains(text() ,'free cancellation')]";
		if (isElementPresent(resut, 5000)) {
			getElement(resut).click();
		} else {

		}
	}

	public String switchtoNewVender() {
		return switchToTab();
	}


	public int getAllresultCountATPAge() {
		List<WebElement> list = webDriver.findElements(By.xpath(LIST_ALL_HOTEL_CPATH));
		return list.size();
	}

	public ArrayList<Hotel> getAllResultCurrentPage() {
		List<WebElement> list = webDriver.findElements(By.xpath(LIST_ALL_HOTEL_CPATH));
		int hcownt = getAllresultCountATPAge();
		for (int i = 1; i <= hcownt; i++) {
			String hotelName_xpath = ROOT_XPATH + "//li[" + i
					+ "][contains(@class,'hotel-item')]//h3[@itemprop='name']";
			String loest_price = ROOT_XPATH + "//li[" + i
					+ "]//button[@data-qa='cheapest-deal']//span[contains(@class,'accommodation-list__price')]";

			String hotel_name = webDriver.findElement(By.xpath(hotelName_xpath)).getText();
			String hotel_lowest_price = webDriver.findElement(By.xpath(loest_price)).getText();

			Hotel hotel = new Hotel();
			hotel.setHotelName(hotel_name);
			hotel.sethotel_OUR_Loest_Price(hotel_lowest_price);
			hotels.add(hotel);
		}
		return hotels;
	}

}
