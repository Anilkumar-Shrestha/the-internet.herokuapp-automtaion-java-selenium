package features.addRemoveElements;

import application.TestBase;
import application.actions.AddRemoveElementsPageActions;
import application.actions.MainPageActions;
import application.pages.MainPage;
import framework.driver.UiDriver;
import framework.models.AssertionModel;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import static framework.utility.reporter.CustomExtentReport.writeSteps;


public class AddRemoveElementsTest extends TestBase {

	AssertionModel assertionModel = new AssertionModel();
	private final String driver = "addRemoveElements-driver";
	private int deleteButtonCount = 0;


	@AfterClass(alwaysRun = true)

	public void quitBrowsers() throws Exception {
		UiDriver.closeBrowser(driver);
	}



	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 1, description = "Verify Add/Remove link is clickable" )
	public void LandToAddRemoveElementsPage() {
		SoftAssert softAssert = new SoftAssert();
		launchApplication(driver);
		writeSteps("PASS", "Application "+TestBase.setUp.getAppUrl()+" is launched.");
		MainPageActions.clickOnAddRemoveElementsLink();

		assertionModel = AddRemoveElementsPageActions.verifyAddRemoveElementsPageIsLanded();
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 2, description = "Verify Element can be added", dependsOnMethods = {"LandToAddRemoveElementsPage"})
	public void AddElements() {
		SoftAssert softAssert = new SoftAssert();

		// assertion for adding 1 button
		AddRemoveElementsPageActions.clickOnAddElement();
		deleteButtonCount++;

		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for adding 2 button
		AddRemoveElementsPageActions.clickOnAddElement();
		deleteButtonCount++;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for adding 3 button
		AddRemoveElementsPageActions.clickOnAddElement();
		deleteButtonCount++;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}

	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 3, description = "Verify Element can be Deleted", dependsOnMethods = {"AddElements"})
	public void DeleteElements() {
		SoftAssert softAssert = new SoftAssert();

		// assertion for removing first 1 button
		AddRemoveElementsPageActions.clickOnDeleteElementByIndex(1);
		deleteButtonCount--;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for removing last button
		AddRemoveElementsPageActions.clickOnDeleteElementByIndex(2);
		deleteButtonCount--;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for adding 3 button
		AddRemoveElementsPageActions.clickOnDeleteElementByIndex(1);
		deleteButtonCount--;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();
	}


	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4, description = "Verify Element can be Added/Deleted", dependsOnMethods = {"LandToAddRemoveElementsPage"})
	public void AddDeleteElements() {
		SoftAssert softAssert = new SoftAssert();


		// assertion for adding 1 button
		AddRemoveElementsPageActions.clickOnAddElement();
		deleteButtonCount++;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for adding 2 button
		AddRemoveElementsPageActions.clickOnAddElement();
		deleteButtonCount++;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for removing first 1 button
		AddRemoveElementsPageActions.clickOnDeleteElementByIndex(1);
		deleteButtonCount--;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for adding 2 button
		AddRemoveElementsPageActions.clickOnAddElement();
		deleteButtonCount++;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		// assertion for adding 3 button
		AddRemoveElementsPageActions.clickOnDeleteElementByIndex(1);
		deleteButtonCount--;
		assertionModel = AddRemoveElementsPageActions.verifyNumberOfDeleteButtonIsAsExpected(deleteButtonCount);
		if (!assertionModel.getStatus()) {
			writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
			softAssert.fail(assertionModel.getErrorMessage());
		} else {
			writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
		}

		softAssert.assertAll();

	}


}
