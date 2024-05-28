package framework.elements;

import org.openqa.selenium.By;

public class Notification extends HtmlElement {
	private static final By locator = By.xpath("//h2[.='Notifications']");

	public Notification() {
		super(locator);
	}

	public Notification(By locator, String elementName) {
		super(locator, elementName);
	}
}
