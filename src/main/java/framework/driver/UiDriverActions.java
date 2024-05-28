package framework.driver;

import framework.elements.HtmlElement;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import static framework.driver.UiDriver.getWebDriver;
import static framework.utility.loggerator.Logger.getLogger;

public class UiDriverActions {
	private static final ThreadLocal<String> mainHandle = new ThreadLocal<>();
	private static final ThreadLocal<String> newHandle = new ThreadLocal<>();


	public static void openNewBrowserAndNavigateToUrl(String driverName,String url){
        getLogger().info("opening new browser and navigating to url:: {}", url);
		UiDriver.setDriver(driverName);
		JavascriptExecutor js = (JavascriptExecutor) UiDriver.getWebDriver();
		String userAgent = (String) js.executeScript("return navigator.userAgent;");
		System.out.println("**********************userAgent***************************");
		System.out.println(userAgent);
		System.out.println("*************************************************");
		navigateToUrl(url);
	}



	public static void openNewBrowserAndNavigateToUrl(String driverName,String url,String fileDownloadPath) {
        getLogger().info("opening new browser with chrome options 2 and navigating to url:: {}", url);
		UiDriver.setDriver(driverName, ChromeOptionsFactory.defaultChromeOption(fileDownloadPath));
		JavascriptExecutor js = (JavascriptExecutor) UiDriver.getWebDriver();
		String userAgent = (String) js.executeScript("return navigator.userAgent;");
		System.out.println("**********************userAgent***************************");
		System.out.println(userAgent);
		System.out.println("*************************************************");
		navigateToUrl(url);
	}



	public static void navigateToUrl(String url) {
		navigateToUrl(url, false);
	}
	public static void navigateToUrl(String url, Boolean cookiesAllowExists){
        getLogger().info("navigating to url:: {}", url);
		WebDriver newDriver = UiDriver.getWebDriver();
		newDriver.get(url);
		newDriver.manage().window().maximize();
		if(cookiesAllowExists) {
			// write steps to accept the cookies
		}
	}

	public static void openLinkInNewTab(String urlLink) {
		openLinkInNewTab(urlLink,false);
	}


	public static void openLinkInNewTab(String urlLink, Boolean cookiesAllowExists){
		mainHandle.set(UiDriver.getWebDriver().getWindowHandle());
		UiDriver.getWebDriver().switchTo().newWindow(WindowType.TAB);
		navigateToUrl(urlLink, cookiesAllowExists);
		newHandle.set(UiDriver.getWebDriver().getWindowHandle());
	}

	public static void doActionsAndSwitchToNewWindow(Runnable runnable) {
		mainHandle.set(UiDriver.getWebDriver().getWindowHandle());
		Set<String> beforeHandles = UiDriver.getWebDriver().getWindowHandles();

		runnable.run();

		Set<String> afterHandles = UiDriver.getWebDriver().getWindowHandles();
		afterHandles.removeAll(beforeHandles);
		newHandle.set(String.valueOf(afterHandles.toArray()[0]));
		UiDriver.switchToWindow(newHandle.get());
	}

	public static void closeAndSwitchToMainWindow() {
		UiDriver.getWebDriver().close();
		UiDriver.switchToWindow(mainHandle.get());
	}

	public static void switchToMainWindow() {
		UiDriver.switchToWindow(mainHandle.get());
	}

	public static void switchToWindowTabByIndex(int tabIndex) {
		ArrayList<String> tabs = new ArrayList<>(UiDriver.getWebDriver().getWindowHandles());
		UiDriver.getWebDriver().switchTo().window(tabs.get(tabIndex));
	}

	public static void refreshPage() {
		UiDriver.getWebDriver().navigate().refresh();
	}

    public static void zoomIn(int zoomPercentage){
		getLogger().info("window size zoomed to ::{}  %.",zoomPercentage);
        UiDriver.executeScript(String.format("document.body.style.zoom='%d%s", zoomPercentage,"%'") ,0);
    }

	public static void setWindowSize(int width, int height ){
		getLogger().info("setting window size to {}  * {} ",width, height);
		Dimension dimension = new Dimension(width, height);
		UiDriver.getWebDriver().manage().window().setSize(dimension);
	}

	public static void maximizeWindow(){
		UiDriver.getWebDriver().manage().window().maximize();
	}

	public static void switchToIframe(HtmlElement htmlElement) {
		UiDriver.getWebDriver().switchTo().frame(htmlElement.getWebElement());
	}

	public static void switchToDefaultContentFromIframe() {
		UiDriver.getWebDriver().switchTo().defaultContent();
	}

	public static void scrollToBottomPage() {
		JavascriptExecutor js = (JavascriptExecutor) UiDriver.getWebDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}


	@Attachment(value = "Page screenshot As Byte", type = "image/png")
	public static byte[] takeScreenshotAsBytes() {
		try {
			return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
		} catch (Throwable exception) {
			throw new RuntimeException("Something went wrong with driver during getting taking screenshot", exception);
		}
	}

	@Attachment(value = "Page screenshot as File", type = "image/png")
	public static File takeScreenshotAsFile() {
		try {
			return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
		} catch (Throwable exception) {
			throw new RuntimeException("Something went wrong with driver during getting taking screenshot", exception);
		}
	}

	public static String takePageSource() {
		return getWebDriver().getPageSource();
	}
}
