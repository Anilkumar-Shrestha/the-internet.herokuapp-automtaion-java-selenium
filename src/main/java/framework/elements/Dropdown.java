package framework.elements;

import framework.driver.TIMEOUT;
import framework.driver.Waiter;
import org.openqa.selenium.By;

public class Dropdown extends HtmlElement {
	private final static By INPUT_LOCATOR = By.xpath(".//input");
	private final static String OPTION_LOCATOR_PATTERN = ".//div[contains(@class,'option') and contains(text(),'%s')]";
	private final static By CURRENT_OPTION_LOCATOR = By.xpath(".//div[contains(@class,'singleValue')]");

	public Dropdown(By locator) {
		super(locator);
	}

	public Dropdown(By locator, String elementName) {
		super(locator, elementName);
	}

	public void setOptionByText(String text) {
		getWebElement().findElement(INPUT_LOCATOR).sendKeys(text);
		By optionLocator = By.xpath(String.format(OPTION_LOCATOR_PATTERN, text));
		Waiter.waitUntil(arg0 -> {
					try {
						return getWebElement().findElement(optionLocator).isDisplayed();
					} catch (Throwable ignored) {
						return false;
					}
				},
				TIMEOUT.THREE_SEC,
				"Option with text " + text + " not visible");
		getWebElement().findElement(optionLocator).click();
	}

	public String getValue() {
		return getWebElement().findElement(CURRENT_OPTION_LOCATOR).getText();
	}
}
