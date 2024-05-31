package features.basicAuth;

import application.TestBase;
import application.actions.BasicAuthPageActions;
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


public class BasicAuthTest extends TestBase {

	AssertionModel assertionModel = new AssertionModel();
	private final String driver = "basicAuth-driver";

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
	@Test(priority = 1, description = "Verify User can Authenticate using basic auth method" )
	public void AuthenticateUsingBasicAuth() {
		SoftAssert softAssert = new SoftAssert();
		BasicAuthPageActions.registerAuthenticationUsingUsernameAndPassword();
		MainPageActions.clickOnBasicAuthLink();
		assertionModel = BasicAuthPageActions.verifyCongratulationOnAuthenticationMessageIsSeen();
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}



}
