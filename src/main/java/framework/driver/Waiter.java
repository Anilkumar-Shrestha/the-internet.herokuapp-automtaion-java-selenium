package framework.driver;

import framework.elements.HtmlElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

import static framework.driver.TIMEOUT.ONE_SEC;
import static framework.driver.UiDriver.getWebDriver;
import static framework.utility.loggerator.Logger.getLogger;


public class Waiter extends WebDriverWait {

	private static final TIMEOUT DEFAULT_WAITER_TIME = ONE_SEC;

	private Waiter(WebDriver webDriver) {
		super(webDriver, DEFAULT_WAITER_TIME.getDurationInSeconds());
	}

	public static Waiter getWaiter() {
		return new Waiter(getWebDriver());
	}

	public static void waitUntil(ExpectedCondition<Boolean> expectedCondition, TIMEOUT timeout, String message) {
		getLogger().info("Wait until {}", message);
		getWaiter()
				.withTimeout(timeout.getDurationInSeconds())
				.withMessage(message)
				.until(expectedCondition);
	}

	public static void waitUntil(ExpectedCondition<Boolean> expectedCondition, TIMEOUT timeout) {
		waitUntil(expectedCondition, timeout, null);
	}

	public static Boolean waitWithoutDriver(Supplier<Boolean> p, String errorMessage) {
		boolean isDone = false;
		int attemptCount = 0;
		do {
			if (p.get()) {
				isDone = true;
			} else {
				attemptCount++;
				Waiter.sleep(500);
			}
		} while (!isDone && attemptCount < 40);
		return isDone; // returned boolean to check if the supplier is found.
	}

	public static void waitUntilUrlIsChanged(String oldUrl){
		getLogger().info("waiting for url to be changed: "+oldUrl);
		WebDriverWait wait = new WebDriverWait(UiDriver.getWebDriver(), Duration.ofSeconds(45));
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
	}

	public WebElement waitForElementClickable(HtmlElement element) {
		getLogger().debug("Waiting for element clickable: {}", element);
		return super.until(ExpectedConditions.elementToBeClickable(element.getWebElement()));
	}

	public <V> Boolean untilElementVisible(HtmlElement element) {
		getLogger().debug("Wait until element visible : " + element);
		ExpectedCondition<Boolean> elementIsVisible = arg0 -> {
			try {
				return element.getWebElement().isDisplayed();
			} catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
				return false;
			}
		};
		return super.withMessage(element.getClass().getSimpleName() + " '" + element.getName() + "' is not visible").until(elementIsVisible);
	}

	public <V> Boolean untilElementInvisible(HtmlElement element) {
		getLogger().debug("Wait until element invisible : " + element);
		ExpectedCondition<Boolean> elementIsInvisible = arg0 -> {
			try {
				return !element.getWebElement().isDisplayed();
			} catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
				return true;
			}
		};
		return super.withMessage(element.getClass().getSimpleName() + " '" + element.getName() + "' is not Invisible").until(elementIsInvisible);
	}

	public <V> Boolean untilElementDisable(HtmlElement element) {
		getLogger().debug("Wait until element disable : " + element);
		ExpectedCondition<Boolean> elementIsNotEnabled = arg0 -> {
			try {
				return !element.getWebElement().isEnabled()
						|| element.getWebElement().getAttribute("class").contains("disabled")
						|| element.getWebElement().getCssValue("color").equals("rgba(128, 128, 128, 1)");
			} catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
				return false;
			}
		};
		return super.withMessage(element.getClass().getSimpleName() + " '" + element.getName() + "' is not disabled").until(elementIsNotEnabled);
	}

	public <V> Boolean untilElementEnable(HtmlElement element) {
		getLogger().debug("Wait until element enable: " + element);
		ExpectedCondition<Boolean> elementIsNotEnabled = arg0 -> {
			try {
				return element.getWebElement().isEnabled()
						&& !element.getWebElement().getAttribute("class").contains("disabled")
						&& !element.getWebElement().getCssValue("color").equals("rgba(128, 128, 128, 1)");
			} catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
				return false;
			}
		};
		return super.withMessage(element.getClass().getSimpleName() + " '" + element.getName() + "' is not enabled").until(elementIsNotEnabled);
	}

	@Override
	public Waiter withMessage(String message) {
		super.withMessage(message);
		return this;
	}

	/**
	 * @param timeout - Default - ONE_SEC
	 * @return this
	 */
	@Override
	public Waiter withTimeout(Duration timeout) {
		super.withTimeout(timeout);
		return this;
	}

	public static void sleep(long timeInMillis) {
		getLogger().info("Start wait " + timeInMillis + " millis" );
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getLogger().debug("Stop wait " + timeInMillis + " millis" );
	}


	public <V> HtmlElement untilElementClickable(HtmlElement htmlElement) {
		return new HtmlElement(
				super.withMessage(htmlElement.getClass().getSimpleName() + " '" + htmlElement.getName() + "' is not clickable")
						.until(ExpectedConditions.elementToBeClickable(htmlElement.getLocator())));
	}
}
