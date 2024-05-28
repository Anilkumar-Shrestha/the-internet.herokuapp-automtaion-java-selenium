package features.addRemoveElements;

import application.TestBase;
import framework.driver.UiDriver;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


import static framework.utility.reporter.CustomExtentReport.writeSteps;


public class AddRemoveElementsTest extends TestBase {

	private final String driver = "addRemoveElements-driver";


	@AfterClass(alwaysRun = true)

	public void quitBrowsers() throws Exception {
		UiDriver.closeBrowser(driver);
	}



	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1, description = "Verify Add Element is clickable and can be added" )
	public void AddElements() {
		launchApplication(driver);
		writeSteps("PASS", "Application "+TestBase.setUp.getAppUrl()+" is launched.");

	}






}
