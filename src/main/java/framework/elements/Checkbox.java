package framework.elements;

import framework.utility.loggerator.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Checkbox extends HtmlElement {
	public Checkbox(By locator) {
		super(locator);
	}

	public Checkbox(HtmlElement parentElement, By locator) {
		super(parentElement, locator);
	}

	public Checkbox(WebElement webElement) {
		super(webElement);
	}

	public Checkbox(By locator, String elementName) {
		super(locator, elementName);
	}

	public boolean isSelected() {
		return getWebElement().isSelected();
	}

	public void select() {
		if (!isSelected()) {
			Logger.getLogger().debug("Checkbox select");
			getWebElement().click();
		} else {
			Logger.getLogger().debug("Checkbox already selected");
		}
	}

	public void unselect() {
		if (isSelected()) {
			Logger.getLogger().debug("Checkbox unselect");
			getWebElement().click();
		} else {
			Logger.getLogger().debug("Checkbox already unselected");
		}
	}

	public boolean toggle() {
		getWebElement().click();
		return isSelected();
	}

	public void setState(boolean state) {
		if (state != isSelected()){
			toggle();
		}
	}
}
