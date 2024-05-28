package framework.elements;

import framework.driver.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static framework.utility.loggerator.Logger.getLogger;

public class TimePicker extends Select {
	private static final String OPTIONS_PATTERN_LOCATOR = "//li[@class='react-datepicker__time-list-item ' and text()='%s']";

	public TimePicker(By locator) {
		super(locator);
	}

	public TimePicker(HtmlElement timeZone, By countrySelectLocator) {
		super(timeZone, countrySelectLocator);
	}

	public TimePicker(WebElement webElement) {
		super(webElement);
	}

	@Override
	public void selectOptionByText(String text) {
		getWebElement().click();
		Waiter.getWaiter()
				.withMessage("Options list is not opened")
				.untilElementVisible(getOptionByTextContains(text));
		getLogger().debug("Select '" + text + "' of " + this);
		getOptionByTextContains(text).click();
	}

	private Button getOptionByTextContains(String text) {
		By locator = By.xpath(String.format(OPTIONS_PATTERN_LOCATOR, text));
		return new Button(locator);
	}
}
