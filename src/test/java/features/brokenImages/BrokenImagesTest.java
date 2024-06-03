package features.brokenImages;

import application.TestBase;
import application.actions.BrokenImagesPageActions;
import application.actions.MainPageActions;
import framework.driver.UiDriver;
import framework.models.AssertionModel;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static framework.utility.reporter.CustomExtentReport.writeSteps;


public class BrokenImagesTest extends TestBase {

	AssertionModel assertionModel = new AssertionModel();
	private final String driver = "brokenImages-driver";

	@BeforeClass(alwaysRun = true)
	public void launchApplication() throws Exception {
		launchApplication(driver);
		writeSteps("PASS", "Application "+TestBase.setUp.getAppUrl()+" is launched.");
	}


	@AfterClass(alwaysRun = true)
	public void quitBrowsers() throws Exception {
		UiDriver.closeBrowser(driver);
	}



	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1, description = "Verify if broken images page is landed" )
	public void BrokenImagesPage() {
		SoftAssert softAssert = new SoftAssert();
		MainPageActions.clickOnBrokenImagesLink();

		assertionModel = BrokenImagesPageActions.verifyBrokenImagesPageIsLanded();
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}


	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 2, description = "Verify if there are broken images in the Broken Images page" )
	public void BrokenImagesCheck() {
		SoftAssert softAssert = new SoftAssert();

		assertionModel = BrokenImagesPageActions.verifyEachImagesToSeeIfItsBroken();
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}



}
