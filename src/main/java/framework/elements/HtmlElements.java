package framework.elements;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class HtmlElements extends HtmlElement {

	public HtmlElements(By locator) {
		super(locator);
	}

	public HtmlElements(By locator, String elementName) {
		super(locator, elementName);
	}

	public HtmlElements(WebElement webElement) {
		super(webElement);
	}

	public List<HtmlElement> getListOfHtmlElements() {
		AtomicInteger index = new AtomicInteger();
		return getWebElements().stream()
				.map(el -> new HtmlElement(el, elementName + " " + index.getAndIncrement()))
				.collect(toList());
	}
}
