package framework.elements;

import org.openqa.selenium.By;

public class ToastMessage extends HtmlElement {
	private static final By locator = By.xpath("//div[@class='toast-message']");

	public ToastMessage() {
		super(locator);
	}

	public ToastMessage(By locator, String elementName) {
		super(locator, elementName);
	}
}
