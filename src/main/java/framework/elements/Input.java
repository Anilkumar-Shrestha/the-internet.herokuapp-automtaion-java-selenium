package framework.elements;

import framework.driver.UiDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static framework.utility.loggerator.Logger.getLogger;

public class Input extends HtmlElement {
	public Input(By locator) {
		super(locator);
	}

	public Input(HtmlElement parentElement, By locator) {
		super(parentElement, locator);
	}

	public Input(By locator, String elementName) {
		super(locator, elementName);
	}

	public void sendKeys(String text) {
		getLogger().debug("Send '" + text + "' to element " + this);
		Actions actions = new Actions(UiDriver.getWebDriver());
		actions.moveToElement(this.getWebElement()).perform();
		this.getWebElement().sendKeys(Keys.CONTROL,"A");
		this.getWebElement().sendKeys(Keys.BACK_SPACE);
		getWebElement().sendKeys(text);
	}

	public void sendKeys(Keys keys) {
		getLogger().debug("Send '" + keys.toString() + "' to element " + this);
		getWebElement().clear();
		getWebElement().sendKeys(keys);
	}

	public void uploadFile(String path) {
		getLogger().debug("Upload file by sending '" + path + "' to element " + this);
		UiDriver.getWebDriver().findElement(this.locator).sendKeys(path);
	}

	public void pasteText(String text) {
		getLogger().debug("Paste text '" + text + "' to element " + this);
		StringSelection selection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		this.getWebElement().sendKeys(Keys.CONTROL + "v");
	}

	public String getValue() {
		return getAttributeValue("value");
	}

	public boolean isSelected() {
		getLogger().info("checking if element: "+this + "is selected");
		return this.getWebElement().isSelected();
	}

}
