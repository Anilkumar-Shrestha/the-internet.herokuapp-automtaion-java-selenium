package features.digestAuthentication;

import application.TestBase;
import application.actions.DigestAuthenticationPageActions;
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


public class DigestAuthenticationTest extends TestBase {

	AssertionModel assertionModel = new AssertionModel();
	private final String driver = "digestAuth-driver";

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
	@Test(priority = 1, description = "Verify User can Authenticate using digest auth method" )
	public void AuthenticateUsingDigestAuth() {
		SoftAssert softAssert = new SoftAssert();
		DigestAuthenticationPageActions.registerDigestAuthenticationUsingUsernameAndPassword();
		MainPageActions.clickOnDigestAuthenticationLink();
		assertionModel = DigestAuthenticationPageActions.verifyCongratulationOnAuthenticationMessageIsSeen();
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}



}
