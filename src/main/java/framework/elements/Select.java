package framework.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static framework.utility.loggerator.Logger.getLogger;

public class Select extends HtmlElement {

	public Select(By locator) {
		super(locator);
	}

	public Select(By locator, String elementName) {
		super(locator, elementName);
	}

	public Select(HtmlElement timeZone, By countrySelectLocator) {
		super(timeZone, countrySelectLocator);
	}

	public Select(WebElement webElement) {
		super(webElement);
	}

	public void selectOptionByText(String text) {
		getLogger().debug("Select '" + text + "' from " + this);
		org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
		select.selectByVisibleText(text);
	}
	public void selectOptionByIndex(int index) {
		getLogger().debug("Selecting  '" + index + "' index option from " + this);
		org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
		select.selectByIndex(index);
	}

	public void selectOptionByValue(String value) {
		getLogger().debug("Selecting  '" + value + "' value option from " + this);
		org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(getWebElement());
		select.selectByValue(value);
	}
}
