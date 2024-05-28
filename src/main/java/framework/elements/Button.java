package framework.elements;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@ToString(callSuper = true)
public class Button extends HtmlElement {

	public Button(By locator) {
		super(locator);
	}

	public Button(HtmlElement parentElement, By locator) {
		super(parentElement, locator);
	}

	public Button(HtmlElement parentElement, By locator, String name) {
		super(parentElement, locator, name);
	}

	public Button(WebElement webElement, String name) {
		super(webElement, name);
	}

	public Button(By locator, String elementName) {
		super(locator, elementName);
	}

	public boolean isEnabled() {
		WebElement button = getWebElement();
		if (button.getAttribute("class").contains("disabled") || button.getAttribute("disabled") != null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isActive() {
		WebElement button = getWebElement();
		if (button.getAttribute("class").contains("active") || button.getAttribute("active") != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isChecked() {
		WebElement button = getWebElement();
		if (button.getAttribute("checked") == null || button.getAttribute("checked").equalsIgnoreCase("false")) {
			return false;
		} else {
			return true;
		}
	}
}
