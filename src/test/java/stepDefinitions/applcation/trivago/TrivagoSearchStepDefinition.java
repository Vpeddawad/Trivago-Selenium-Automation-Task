package stepDefinitions.applcation.trivago;

import org.openqa.selenium.WebDriver;

import com.base.aplication.trivago.SerachPaneTrivagoFixture;
import com.base.aplication.trivago.SerachResultPaneTrivagoFixture;
import com.base.aplication.trivago.hotel.Hotel;
import com.base.fixture.Fixture;
import com.base.fixture.SeleniumTestCase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TrivagoSearchStepDefinition extends SeleniumTestCase {
	WebDriver driver;
	SerachResultPaneTrivagoFixture result;
	Hotel h;

	@Given("^user is already on trivago Page$")
	public void user_is_already_on_trivago_Page() throws Throwable {
		driver = start();
	}

	@When("^title of login page is \"([^\"]*)\"$")
	public void title_of_login_page_is(String homepageTitle) throws Throwable {
		String title = driver.getTitle();
		assert ((title.contains(homepageTitle)));

	}

	@Then("^enter \"([^\"]*)\" to search$")
	public void enter_to_search(String hotelName) throws Throwable {
		SerachPaneTrivagoFixture searhPane = new SerachPaneTrivagoFixture();
		searhPane.populateAllSearchDetails(hotelName, "September 2020", "2020-09-15", "2020-09-16");
		searhPane.search();
	}

	@Then("^it will list new serach page click to top 2 result$")
	public void it_will_list_new_serach_page() throws Throwable {
		result = new SerachResultPaneTrivagoFixture();
		h = result.getResultAtIndex(2);
	}

	@Then("^click on Our lowest price$")
	public void click_on_Our_lowest_price() throws Throwable {
		result = new SerachResultPaneTrivagoFixture();
		result.clickOurLowestPrice(2);
	}

	@Then("^goto the Our lowest price with free canceleation and  click$")
	public void goto_the_Our_lowest_price_with_free_canceleation_and_click() throws Throwable {
		result = new SerachResultPaneTrivagoFixture();
		result.clickOur_lower_price_FreeCancellation();
	}

	@Then("^it will land to the vender page at different tab and validate the hotel and price\\.$")
	public void it_will_land_to_the_vender_page_at_different_tab_and_validate_the_hotel_and_price() throws Throwable {
		String url = result.switchtoNewVender();
		driver.quit();
		driver = start(url);

		log.info("Hotel Name:" + h.getHotelName() + " Price :" + h.gethotel_OUR_Loest_Price());

		String[] hotel = h.getHotelName().split(" ");
		String hotel_2WordsCheck = hotel[0] + " " + hotel[1];
		log.info("Validating only the starting two words of Hotel for accuracy : " + hotel_2WordsCheck);
		Fixture.validatetextAvaliableTothePage(hotel_2WordsCheck);
		log.info("Validation the price : " + h.gethotel_OUR_Loest_Price());
		Fixture.validatetextAvaliableTothePage(h.gethotel_OUR_Loest_Price());

	}

	@Then("^Close browser$")
	public void close_browser() throws Throwable {
		driver.quit();
	}
}
