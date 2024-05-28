package framework.driver;

import application.TestBase;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import static framework.utility.loggerator.Logger.getLogger;



public class UiDriver extends TestBase {
	private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();
	@Setter
	private WebDriver webDriver;
	@Getter @Setter
	public static Map<String, WebDriver> drivers= new HashMap<>();

	private UiDriver() throws MalformedURLException {
		new UiDriver(ChromeOptionsFactory.defaultChromeOption());
	}
	private UiDriver(ChromeOptions chromeOptions) throws MalformedURLException {
		String remoteRun = setUp.getRemoteRun().toLowerCase();
		// for running remotely
		if (Objects.equals(remoteRun, "true")) {
			switch (setUp.getBrowserType().toUpperCase()) {
				case "CHROME":
					webDriver = new RemoteWebDriver(new URL(setUp.getRemoteUrl()), chromeOptions);
					TestBase.setUp.setBrowserDriverVersion(((RemoteWebDriver) webDriver).getCapabilities().getBrowserVersion());
					webDriver.manage().deleteAllCookies();
					webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
					((RemoteWebDriver) webDriver).setFileDetector(new LocalFileDetector());
//                    driver.get("chrome://settings/clearBrowserData");
                    break;
//					return driver;


				case "FIREFOX":
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("browser.download.folderList", 2);
					firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
					firefoxProfile.setPreference("browser.download.dir", FileDownloadPath);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv, text/csv, text/plain,application/octet-stream doc xls pdf txt");
//					firefoxProfile.setPreference("general.useragent.override", "User-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36 Edg/99.0.1150.36");
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setProfile(firefoxProfile);
					//Set the setHeadless is equal to False which will not run test in Headless mode
//					firefoxOptions.setHeadless(Boolean.parseBoolean(setUp.getHeadlessMode()));
					firefoxOptions.addArguments("--headless=old");
					webDriver = new RemoteWebDriver(new URL(setUp.getRemoteUrl()), firefoxOptions);
					webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
                    break;
//					return driver;



			}
		} else {
			switch (setUp.getBrowserType().toUpperCase()) {
				case "CHROME":
					webDriver = new ChromeDriver(chromeOptions);
					TestBase.setUp.setBrowserDriverVersion(((ChromeDriver) webDriver).getCapabilities().getBrowserVersion());
					webDriver.manage().deleteAllCookies();
					webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
					webDriver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(10));
//                    driver.get("chrome://settings/clearBrowserData");
                    break;
//					return driver;


				case "FIREFOX":
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("browser.download.folderList", 2);
					firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
					firefoxProfile.setPreference("browser.download.dir", FileDownloadPath);
					firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv, text/csv, text/plain,application/octet-stream doc xls pdf txt");
//					firefoxProfile.setPreference("general.useragent.override", "User-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36 Edg/99.0.1150.36");
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setProfile(firefoxProfile);
					//Set the setHeadless is equal to False which will not run test in Headless mode
//					firefoxOptions.setHeadless(Boolean.parseBoolean(setUp.getHeadlessMode()));
					firefoxOptions.addArguments("--headless=old");
					webDriver = new FirefoxDriver(firefoxOptions);
					webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
                    break;
//					return driver;


			}

		}
//		return driver;
	}

	public static void setDriver(String driverName){
		setDriver(driverName, ChromeOptionsFactory.defaultChromeOption());
	}
	public static synchronized void setDriver(String driverName, ChromeOptions chromeOptions) {
		try {
			if (drivers.get(driverName) == null) {
				System.out.println("**********************************************************************");
				System.out.println("new Uidriver created. " + driverName);
				System.out.println("**********************************************************************");
				threadLocal.set(new UiDriver(chromeOptions).webDriver);
				threadLocal.get().manage().window().maximize();
			}
			drivers.put(driverName, threadLocal.get());
			getLogger().info("Driver name set to '"+driverName+"' for threadlocal webdriver '"+ threadLocal.get()+"'");
		} catch (MalformedURLException e) {
			getLogger().error("Can not create webDriver", e);
		}
		System.out.println(drivers);
	}

	public static void switchDriver(String driverName) {
		getLogger().info("Previous threadlocal is : "+threadLocal.get());
		threadLocal.set(drivers.get(driverName));
		getLogger().info("Switched back to driver "+driverName+ " and Webdriver threadlocal set to "+drivers.get(driverName));
		getLogger().info("Current switched threadlocal is : "+threadLocal.get());
	}


	private static WebDriver getInstanceOfDriverWrapper() {
		if (threadLocal.get() == null) {
			getLogger().info("Instance web driver is not present. It might be closed Unexpectedly. Please check.");
		}
		return threadLocal.get();
	}

	public static WebDriver getWebDriver() {
		return getInstanceOfDriverWrapper();
	}
	public static String getCurrentUrl() {
		return getWebDriver().getCurrentUrl();
	}


	public static void quit() {
		getWebDriver().quit();
		getLogger().info("Driver closed");
	}
	public static synchronized void closeBrowser(String driverName) {
		if (drivers.get(driverName) == null) {
			getLogger().info(driverName +  " not found.");
		} else {
			switchDriver(driverName);
			getWebDriver().quit();
			getLogger().info(driverName + " Driver closed");
			drivers.remove(driverName);
		}
	}


	public static void switchToWindow(String windowHandle){
		getWebDriver().switchTo().window(windowHandle);
	}


	public static Object executeScript(String script, Object... args) {
		return ((JavascriptExecutor) getWebDriver()).executeScript(script, args);
	}

	public static LogEntries getLogs() {
		return getWebDriver().manage().logs().get(LogType.BROWSER);
	}

	public static Boolean isLoginErrorLog() {
		LogEntries logEntries = getLogs();
		return logEntries.getAll().stream()
				.anyMatch(logEntry -> logEntry.getMessage().contains("An invalid email address was specified"));
	}



}
