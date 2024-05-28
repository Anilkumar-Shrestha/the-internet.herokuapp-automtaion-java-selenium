package application;

import framework.driver.UiDriver;
import framework.driver.UiDriverActions;
import framework.utility.PropertiesManager;
import framework.utility.SetUp;
import io.qameta.allure.Step;
import listeners.*;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

import static framework.driver.UiDriverActions.takeScreenshotAsBytes;
import static framework.utility.loggerator.Logger.getLogger;

@Listeners({ExtentReportListener.class,AllurePrepareListener.class})
public class TestBase{
	public static SetUp setUp = new SetUp();
	public static String FileDownloadPath;
	public static String methodName;

	@BeforeSuite(alwaysRun = true)
	public void setUp(@Optional("chrome") String browser) throws IOException {
		getLogger().info("---------------- Test Suite setUp started -----------");
		setUp = new SetUp();
		setUp.setBrowserType(browser);
		setUp.setAppUrl( PropertiesManager.getAppUrl() );
		setUp.setAppApiUrl( PropertiesManager.getAppApiUrl() );
		setUp.setExcludeScreenshot(PropertiesManager.getProperty("excludeScreenshot", String.valueOf(false)));
		setUp.setHeadlessMode( PropertiesManager.getProperty("HeadlessMode",String.valueOf(true)) );
		setUp.setRemoteRun( PropertiesManager.getProperty("remoteRun") );
		setUp.setRemoteUrl( PropertiesManager.getProperty("remoteUrl") );
		getLogger().info(setUp.toString());


		//        creating filed download directory if not present (used File.separator since when using "/" for windows it does not work in download.
		FileDownloadPath = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"test"+File.separator+"downloads"+File.separator+"common";
		File downloadFolder = new File(FileDownloadPath);
		if (downloadFolder.exists()) {
			FileUtils.forceDelete(downloadFolder);
		}
		downloadFolder.mkdirs();
		getLogger().info("browser download directory set to "+ FileDownloadPath);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestResult result){
		methodName = result.getMethod().getMethodName();

	}
	@AfterSuite(alwaysRun = true)
	public void afterSuite(){
		getLogger().info(" After Suite Method Executing : Closing browser and resetting drivers for next Suite");
		UiDriver.getDrivers().forEach((k,v)-> {UiDriver.switchDriver(k);UiDriver.getWebDriver().quit();});
		UiDriver.setDrivers(new HashMap<>());
	}

	@Step("launch application")
	public void launchApplication(String driverName, String fileDownloadPath)  {
		try {
			UiDriverActions.openNewBrowserAndNavigateToUrl(driverName, setUp.getAppUrl(),fileDownloadPath);
			UiDriver.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		}
		catch (Exception e){
			getLogger().info(String.valueOf(e));
		}
	}

	@Step("launch application")
	public void launchApplication(String driverName)  {
		try {
			UiDriverActions.openNewBrowserAndNavigateToUrl(driverName, setUp.getAppUrl());
			UiDriver.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		}
		catch (Exception e){
			getLogger().info(String.valueOf(e));
		}
		takeScreenshotAsBytes();
	}

	public static Integer getFilesCountInDownloadedFolder(File directory){
		return Objects.requireNonNull(directory.list()).length;
	}

	public static Integer getFilesCountInDownloadedFolderAndDeleteFile(){
		return getFilesCountInDirectoryAndDeleteFile(new File(FileDownloadPath));
	}

	public static synchronized Integer getFilesCountInDirectoryAndDeleteFile(File directory){
		Integer fileCount = Objects.requireNonNull(directory.list()).length;
		// added due to https://stackoverflow.com/questions/70475046/headless-mode-and-non-headless-mode-have-different-behavior-when-downloading-sam
		try {
			if (directory.exists()) {
                FileUtils.forceDelete(directory);
            }
			directory.mkdirs();
		} catch (IOException e) {
			getLogger().error("FileDownloadPath could not be deleted ", e.getCause());
		}
		return fileCount;
	}




}
