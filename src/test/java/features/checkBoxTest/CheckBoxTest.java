package features.checkBoxTest;

import application.TestBase;
import application.actions.CheckBoxPageActions;
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


public class CheckBoxTest extends TestBase {

	AssertionModel assertionModel = new AssertionModel();
	private final String driver = "checkBox-driver";

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
	@Test(priority = 0, description = "Verify if user is landed to checkBox page" )
	public void CheckBoxPageIsLanded() {
		SoftAssert softAssert = new SoftAssert();

		MainPageActions.clickOnCheckboxesLink();

		assertionModel = CheckBoxPageActions.verifyCheckBoxPageIsLanded();
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1, description = "Verify if checkBox 1 can be checked/unchecked" )
	public void CheckBox1Check() {
		SoftAssert softAssert = new SoftAssert();

		// select checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(1,true);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(1);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// UnSelect checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(1,false);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(1);
		if (assertionModel.getStatus()) { //opposite
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}
		softAssert.assertAll();

	}


	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 2, description = "Verify if checkBox 2 can be checked/unchecked" )
	public void CheckBox2Check() {
		SoftAssert softAssert = new SoftAssert();

		// select checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(2,true);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(2);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// UnSelect checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(2,false);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(2);
		if (assertionModel.getStatus()) { //opposite
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}
		softAssert.assertAll();

	}


	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 3, description = "Verify if checkBox 1 and Checkbox 2 can be checked" )
	public void CheckBox1And2Check() {
		SoftAssert softAssert = new SoftAssert();

		// select checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(1,true);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(1);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// select checkbox 2
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(2,true);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(2);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}
		softAssert.assertAll();

	}


	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4, description = "Verify if checkBox 1 and Checkbox 2 can be Unchecked" )
	public void CheckBox1And2UnCheck() {
		SoftAssert softAssert = new SoftAssert();

		// select checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(1,false);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(1);
		if (assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// select checkbox 2
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(2,false);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(2);
		if (assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}
		softAssert.assertAll();

	}


	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 5, description = "Verify if checkBox 1 can be check and Checkbox 2 can be Unchecked" )
	public void CheckBox1CheckAnd2UnCheck() {
		SoftAssert softAssert = new SoftAssert();

		// select checkbox 1
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(1,true);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(1);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// select checkbox 2
		CheckBoxPageActions.clickOnCheckBoxByIndexNumber(2,false);

		assertionModel = CheckBoxPageActions.verifyIfExpectedCheckBoxIsSelected(2);
		if (assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}
		softAssert.assertAll();

	}


}
