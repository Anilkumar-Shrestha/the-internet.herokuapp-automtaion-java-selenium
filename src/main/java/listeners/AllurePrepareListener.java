package listeners;

import application.TestBase;
import org.openqa.selenium.net.HostIdentifier;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static framework.driver.UiDriverActions.takeScreenshotAsBytes;
import static framework.utility.loggerator.Logger.getLogger;

public class AllurePrepareListener implements ITestListener {

	@Override
	public void onStart(ITestContext iTestContext) {
		File allureResFolder = new File("allure-results");
		if (!allureResFolder.exists()) {
			allureResFolder.mkdir();
		}
		File allurePropFile = new File("allure-results/environment.properties");
		if (!allurePropFile.exists()) {
			try {
				allurePropFile.createNewFile();
			} catch (IOException e) {
				getLogger().error("Can not create environment.properties file", e);
			}
		}
		try (FileWriter fw = new FileWriter(allurePropFile)) {
			fw.append("Project=").append("KCP-Legacy")
					.append("\n")
					.append("os=").append(System.getProperty("os.name"))
					.append("\n")
					.append("HOST=").append(HostIdentifier.getHostName().equals("ANIL") ? "LocalHost" : HostIdentifier.getHostName() + " / "+HostIdentifier.getHostAddress())
					.append("\n")
					.append("Environment=").append(TestBase.setUp.getAppUrl())
					.append("\n")
					.append("Browser=").append(TestBase.setUp.getBrowserType())
					.append("\n")
					.append("Browser-Version=").append(TestBase.setUp.getBrowserDriverVersion())
					.append("\n");
		} catch (IOException e) {
			getLogger().error("Can not read/write environment.properties file", e);
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		takeScreenshotAsBytes();
	}





}
