package framework.elements;

import org.openqa.selenium.By;

public class Link extends HtmlElement {
	public Link(By locator) {
		super(locator);
	}

	public Link(By locator, String elementName) {
		super(locator, elementName);
	}
}
