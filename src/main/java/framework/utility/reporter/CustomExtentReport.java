package framework.utility.reporter;

import application.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import framework.driver.UiDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.net.HostIdentifier;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static framework.utility.loggerator.Logger.getLogger;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class CustomExtentReport extends TestBase {
	private static final ExtentReports extentReports;
	private static final ThreadLocal<ExtentTest> extent_test = new ThreadLocal<>();
	private static String dateName;

	static {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		dateName = dateFormat.format(date);
		extentReports = new ExtentReports();
		String path = System.getProperty("user.dir") + "/reports/TestReport_"+dateName+".html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.viewConfigurer().viewOrder()
				.as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY })
				.apply();
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setReportName("Internet Herokuapp test execution report");
		reporter.config().setDocumentTitle("Internet Herokuapp test report");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Project", "Internet Herokuapp");
		extentReports.setSystemInfo("os", System.getProperty("os.name"));
	}

	private static String getScreenShot(String fileName) {
		TakesScreenshot ts = (TakesScreenshot) UiDriver.getWebDriver();
		File screenShotSrc = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/screenshots/" + fileName + ".png";
		try {
			FileUtils.copyFile(screenShotSrc, new File(destinationFile));
		} catch (IOException e) {
			getLogger().error("Screenshot was not copied", e);
		}
		return "screenshots/" + fileName + ".png";
	}

	public static String takeScreenshot(String ScreenshotFileFolder, String ScreenshotFileName) {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
//		Date date = new Date();
//		String dateName = dateFormat.format(date);
		String filePathExtent = System.getProperty("user.dir") + "/reports/Screenshots/"+dateName+"/"
				+ScreenshotFileFolder+"/" +ScreenshotFileName+ ".png";
//        String filePathExtent = System.getProperty("user.dir") + "/reports/Screenshots/" + "Web" + "_" + dateName + ".png";
		String filePath = filePathExtent;
		String encodedBase64=null;
		try {
			File screenshotFile = ((TakesScreenshot) UiDriver.getWebDriver()).getScreenshotAs(OutputType.FILE);
			FileInputStream fileInputStreamReader ;
			fileInputStreamReader = new FileInputStream(screenshotFile);
			byte[] bytes = new byte[(int) screenshotFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = encodeBase64String(bytes);

			FileUtils.copyFile(screenshotFile, new File(filePath));
		}catch (IOException e){
			e.getStackTrace();
			Reporter.log("Failed To Take screenshot " + e, true);
		}
		catch (NullPointerException npe){
			npe.getStackTrace();
			Reporter.log("Failed To Take screenshot as driver is closed Unexpectedly" + npe, true);
		}
		catch (org.openqa.selenium.TimeoutException toe){
			toe.getStackTrace();
			Reporter.log("Timeout while trying To Take screenshot " + toe, true);
			encodedBase64 ="iVBORw0KGgoAAAANSUhEUgAABIAAAAKICAIAAACHSRZaAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAACbCSURBVHhe7d07buRI1gbQXldvpHbQS5BR/vgjaBMyy/qBtmTJkSVH1sgVIMjVAuqP4DP4CJKpxy1V5TlIzGTyEYwIMoH7dVKsv34CAAAQQgADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYBxyfX39n//8p/sAAAC8iQDGISl9/fWXqwUAAN5FSQ0AABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAg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EcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAQP3/+P3avRT5r9jLvAAAAAElFTkSuQmCC";
			// added "Issue occured "org.openqa.selenium.TimeoutException: timeout: Timed out receiving message from renderer" while taking screenshot." msg
		}
		return encodedBase64;
	}

	public static void startTest(String className,String methodName) {
		extent_test.set(extentReports.createTest(methodName).assignCategory(className));
	}

	public static void stopSuccessTest(String message) {
		extent_test.get().log(Status.PASS, "Test Passed for "+message);
	}

	public static void stopFailedTest(String methodName, Throwable throwable) {
		extent_test.get().fail(throwable);
		// CPB-2874 In case of webdriver not present and screenshot could not be taken then showing screenshot with
		// "Instance web driver is not present. It might be closed Unexpectedly. Please check." message.
		extent_test.get().addScreenCaptureFromBase64String(CustomExtentReport.takeScreenshot(methodName,"testFailure" )==null?
		"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gghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAgigAEAAAQRwAAAAIIIYAAAAEEEMAAAgCACGAAAQBABDAAAIIgABgAAEEQAAwAACCKAAQAABBHAAAAAgghgAAAAQQQwAACAIAIYAABAEAEMAAAgiAAGAAAQRAADAAAIIoABAAAEEcAAAACCCGAAAABBBDAAAIAgAhgAAEAQAQwAACCIAAYAABBEAAMAAAjx33//D6jijLLxnyy9AAAAAElFTkSuQmCC"
		:CustomExtentReport.takeScreenshot(methodName,"testFailure" )  );
	}

	public static void stopSkippedTest(String groupDescription, List<String> groupDependUpon) {
		extent_test.get().log(Status.SKIP, "Test `" + groupDescription + "` is SKIPPED. Depends upon: "+groupDependUpon);

	}

	private static String getPageSource(String ScreenshotFileFolder, String fileName) {
		String destinationFile = System.getProperty("user.dir") + "/reports/screenshots/"+dateName+"/"
				+ScreenshotFileFolder+"/"+ fileName + ".html";
		File file = new File(destinationFile);
		try {
			file.createNewFile();
			FileUtils.writeStringToFile(file, UiDriver.getWebDriver().getPageSource(), Charset.defaultCharset());
		} catch (IOException e) {
			getLogger().error("Screenshot was not copied", e);
		}
		return "screenshots/" + fileName + ".html";
	}

	public static void finishRun() {
		extentReports.setSystemInfo("HOST", HostIdentifier.getHostName().equals("ANIL") ? "LocalHost" : HostIdentifier.getHostName() + " / "+HostIdentifier.getHostAddress());
		extentReports.setSystemInfo("Environment",TestBase.setUp.getAppUrl());
		extentReports.setSystemInfo("Browser", TestBase.setUp.getBrowserType());
		extentReports.setSystemInfo("Browser Version", TestBase.setUp.getBrowserDriverVersion());
		extentReports.flush();
	}


	public static void writeSteps(String status, String stepDesc, String media) {
		try{
			switch(status.toUpperCase()){
				case "PASS":
//                    needed to embed image in html so used base64encoded data
					if (TestBase.setUp.getExcludeScreenshot().equalsIgnoreCase("true")) {
						extent_test.get().pass(stepDesc);
					} else {
						extent_test.get().pass(stepDesc, MediaEntityBuilder.createScreenCaptureFromBase64String(media).build());
					}
					break;

				case "FAIL":
					extent_test.get().fail(stepDesc, MediaEntityBuilder.createScreenCaptureFromBase64String(media).build());
					break;

				case "INFO":
					extent_test.get().info(MarkupHelper.createLabel(stepDesc.toUpperCase(), ExtentColor.BLUE));
					break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}


	public static void writeSteps(String status, String stepDesc) {
		DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
		Date date = new Date();
		String dateName = dateFormat.format(date);
		System.out.println("datename:::::"+dateName);
		try{
			switch(status.toUpperCase()){
				case "PASS":
//                    needed to embed image in html so used base64encoded data
					if (TestBase.setUp.getExcludeScreenshot().equalsIgnoreCase("true")) {
						extent_test.get().pass(stepDesc);
					} else {
						extent_test.get().pass(stepDesc, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(TestBase.methodName, "pass_SS_" + dateName)).build());
					}
//					test.pass(stepDesc, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(TestBase.methodName,"pass_SS_"+dateName)).build());
//                    test.pass(stepDesc, MediaEntityBuilder.createScreenCaptureFromPath(screenShot(driver)).build());
					break;

				case "FAIL":
					extent_test.get().fail(stepDesc, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(TestBase.methodName,"fail_SS_"+dateName)).build());
//                    test.fail(stepDesc, MediaEntityBuilder.createScreenCaptureFromPath(screenShot(driver)).build());
					break;

				case "INFO":
					extent_test.get().info(MarkupHelper.createLabel(stepDesc.toUpperCase(), ExtentColor.BLUE));
					break;
			}
		}catch(Exception e){
			getLogger().error(String.valueOf(e));
		}


	}
}