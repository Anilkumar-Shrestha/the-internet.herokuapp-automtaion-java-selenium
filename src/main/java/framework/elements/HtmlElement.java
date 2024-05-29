package framework.elements;

import framework.driver.TIMEOUT;
import framework.driver.UiDriver;
import framework.driver.Waiter;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static framework.utility.loggerator.Logger.getLogger;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class HtmlElement {
	@Getter
    protected By locator;
	protected String elementName;
	protected HtmlElement parentHtmlElement;
	protected WebElement webElement;

	public HtmlElement(By locator) {
		this.locator = locator;
		this.elementName = locator.toString();
	}

	public HtmlElement(By locator, String elementName) {
		this.locator = locator;
		this.elementName = elementName;
	}

	public HtmlElement(HtmlElement parentElement, By locator) {
		this.locator = locator;
		this.parentHtmlElement = parentElement;
	}

	public HtmlElement(HtmlElement parentElement, By locator, String elementName) {
		this.locator = locator;
		this.parentHtmlElement = parentElement;
		this.elementName = elementName;
	}

	public HtmlElement(WebElement webElement) {
		this.webElement = webElement;
	}

	public HtmlElement(WebElement webElement, String name) {
		this.webElement = webElement;
		this.elementName = name;
	}

	public WebElement getWebElement() {
		if (webElement != null) {
			return webElement;
		}
		if (parentHtmlElement != null) {
			return parentHtmlElement.getWebElement().findElement(locator);
		}
		return UiDriver.getWebDriver().findElement(locator);
	}

	public List<WebElement> getWebElements() {
		if (webElement != null) {
			return List.of(webElement);
		}
		if (parentHtmlElement != null) {
			return parentHtmlElement.getWebElement().findElements(locator);
		}
		return UiDriver.getWebDriver().findElements(locator);
	}

	public WebElement waitForElementVisibility(){
		return waitForElementVisibility(30);

	}

	public WebElement waitForElementVisibility(long timeOutInSeconds){
		getLogger().info("Waiting for element to be Visible in "+timeOutInSeconds+" for element: " + this);

		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
		try {
			return wait.until(ExpectedConditions.visibilityOf(this.getWebElement()));
		}
		catch (TimeoutException e){
			throw new AssertionError(e);
		}

	}

	public boolean waitAndReturnStatusForElementVisibility(){
		return waitAndReturnStatusForElementVisibility(30);

	}

	public boolean waitAndReturnStatusForElementVisibility(long timeOutInSeconds){
		getLogger().info("Waiting for element to return visibility status in "+timeOutInSeconds+" sec for element: " + this);
		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
		try {
			wait.until(ExpectedConditions.visibilityOf(this.getWebElement()));
			return true;
		}
		catch (Exception e){
			getLogger().info("Element is NOT visible: " + this);
			return false;
		}

	}

	public Boolean waitForElementInVisibility( ){
		return waitForElementInVisibility(30);
	}

	public Boolean waitForElementInVisibility( long timeOutInSeconds){
		getLogger().info("Waiting for element to disappear(not visible) for "+timeOutInSeconds+" sec : "+ this);

		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
		try {
			return wait.until(ExpectedConditions.invisibilityOf(this.getWebElement()));
		}
		catch (TimeoutException e){
			throw new AssertionError(e);
		}

	}

	public WebElement waitUntilElementIsEnabled(){
		return waitUntilElementIsEnabled(30);
	}
	public WebElement waitUntilElementIsEnabled(long timeOutInSeconds){
		getLogger().info("Waiting for element to be enabled for "+timeOutInSeconds+" sec : "  + this);
		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
		return wait.until(ExpectedConditions.elementToBeClickable(this.getWebElement()));
	}

	public boolean waitAndReturnStatusUntilPageContainsElement(){
		return waitAndReturnStatusUntilPageContainsElement(30);
	}
	public boolean waitAndReturnStatusUntilPageContainsElement( long timeOutInSeconds){
		getLogger().info("Waiting for element to be present for "+timeOutInSeconds+" sec : "  + this);
		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		}
		catch (Exception e){
			return false;
		}

	}

	public boolean waitAndReturnStatusUntilPageDoesNotContainsElement( long timeOutInSeconds){
		getLogger().info("Waiting for element not to be present for "+timeOutInSeconds+" sec : "  + this);
		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
		try {
			wait.until(not(ExpectedConditions.presenceOfElementLocated(locator)));
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	public boolean isPresent() {
		try {
			this.getWebElement();
			return true;
		}catch (NoSuchElementException  e){
			return false;
		}
	}

	public boolean isDisplayed() {
		return isDisplayed(TIMEOUT.ONE_SEC);
	}
	public boolean isEnabled() {
		return this.getWebElement().isEnabled();
	}

	public boolean isDisabled() {
        getLogger().info("Checking if element : {} is disabled.", this);
		return this.checkAttributeIsPresent("disabled");
	}

	public boolean isDisplayed(TIMEOUT timeout) {
		getLogger().info("Wait element displayed: " + this);
		try {
			if (locator != null) {
				Waiter.getWaiter()
						.withTimeout(timeout.getDurationInSeconds())
						.until(presenceOfElementLocated(locator));
				Actions actions = new Actions(UiDriver.getWebDriver());
				actions.moveToElement(UiDriver.getWebDriver().findElement(locator)).perform();
				Waiter.getWaiter()
						.withTimeout(timeout.getDurationInSeconds())
						.untilElementVisible(this);
			} else if (webElement != null) {
				Waiter.getWaiter()
						.withTimeout(timeout.getDurationInSeconds())
						.until(ExpectedConditions.visibilityOfAllElements(webElement));
			}
		} catch (StaleElementReferenceException | TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean isNotDisplayed(TIMEOUT timeout) {
        getLogger().info("Wait element not displayed: {} ", this);
		try {
			Waiter.getWaiter()
					.withTimeout(timeout.getDurationInSeconds())
					.untilElementInvisible(this);
			return true;
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public boolean checkAttributeIsPresent(String attribute){
		boolean bln = false;
		try{
			if(getAttributeValue(attribute)!= null){
				bln = true;
			}
		}catch(NotFoundException nf){
			nf.printStackTrace();
		}
		return bln;
	}

	public String getAttributeValue(String attribute) {
		String value = getWebElement().getAttribute(attribute);
        getLogger().debug("Got attribute value '{}' of {}", value, this);
		return value;
	}

	public String getCssValue(String name) {
		String value = getWebElement().getCssValue(name);
        getLogger().debug("Got css value '{}' of {}", value, this);
		return value;
	}

	public String getText() {
		String text = getWebElement().getText();
        getLogger().info("Got text : '{}' from {}", text, this);
		return text;
	}

	public String getHoverText() {
		String title = getAttributeValue("title");
        getLogger().debug("Got hover title : '{}' from {}", title, this);
		return title;
	}

	public void dragAndDropTo(WebDriver driver, WebElement elementTo) {
		Actions actions = new Actions(driver);
		WebElement source = this.getWebElement();
        getLogger().debug("DragNDrop element {} to {}", locator, elementTo.toString());
		actions.dragAndDrop(source, elementTo)
				.build()
				.perform();
	}

	public void click() {
		getLogger().info("Click on " + this);
		moveToElement();
		getWebElement().click();
	}

    public String getName() {
		return elementName;
	}

	public void scrollToElement() {
		((JavascriptExecutor) UiDriver.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);window.scrollBy(0,-100);", this.getWebElement());
	}

	public void moveToElement(){
		Actions actions = new Actions(UiDriver.getWebDriver());
		try{
		actions.moveToElement(this.getWebElement()).build().perform();
		}
		catch (StaleElementReferenceException e){
			waitAndReturnStatusUntilPageContainsElement(10);
			actions.moveToElement(this.getWebElement()).build().perform();
		}
		catch ( MoveTargetOutOfBoundsException e){ // added to ignore exception on Teams scheduling page for recording/captioning enabled button
			getLogger().info(e.getMessage());
		}
	}

	public void zoomWebElementBy(int times){
		Dimension originalSize = getWebElement().getSize();
		((JavascriptExecutor) UiDriver.getWebDriver()).executeScript("arguments[0].style.width='" + originalSize.width * times + "px'", getWebElement());
		((JavascriptExecutor) UiDriver.getWebDriver()).executeScript("arguments[0].style.height='" + originalSize.height * times + "px'", getWebElement());
	}


	@Override
	public String toString() {
		return "HtmlElement{" +
				"elementName='" + elementName + '\'' +
				", class='" + this.getClass().getSimpleName() + '\'' +
				", locator=" + locator +
				", parentHtmlElement=" + parentHtmlElement +
				", webElement=" + webElement +
				'}';
	}
}
