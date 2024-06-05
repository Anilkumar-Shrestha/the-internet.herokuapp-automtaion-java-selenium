package features.dragAndDrop;

import application.TestBase;
import application.actions.DragAndDropPageActions;
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


public class DragAndDropTest extends TestBase {

    AssertionModel assertionModel = new AssertionModel();
    private final String driver = "dropDrag-driver";

    @BeforeClass(alwaysRun = true)
    public void launchApplication() throws Exception {
        launchApplication(driver);
        writeSteps("PASS", "Application " + TestBase.setUp.getAppUrl() + " is launched.");
    }


    @AfterClass(alwaysRun = true)
    public void quitBrowsers() throws Exception {
        UiDriver.closeBrowser(driver);
    }


    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 0, description = "Verify if user is landed to DroopAndDrag page")
    public void DragAndDropPageIsLanded() {
        SoftAssert softAssert = new SoftAssert();

        MainPageActions.clickOnDragAndDropLink();

        assertionModel = DragAndDropPageActions.verifyDragAndDropPageIsLanded();
        if (!assertionModel.getStatus()) {
            writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
            softAssert.fail(assertionModel.getErrorMessage());
        } else {
            writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
        }

        softAssert.assertAll();

    }

    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 1, description = "Verify if Dragging and Dropping A(Left) to B(right is working")
    public void DragAndDropLeftToRightCheck() {
        SoftAssert softAssert = new SoftAssert();

        // select checkbox 1
        DragAndDropPageActions.dragFirstDivBoxToSecondDivBox();

        assertionModel = DragAndDropPageActions.verifyIfExpectedDivBoxIsInRightPlacement(1, "B");
        if (!assertionModel.getStatus()) {
            writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
            softAssert.fail(assertionModel.getErrorMessage());
        } else {
            writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
        }
        assertionModel = DragAndDropPageActions.verifyIfExpectedDivBoxIsInRightPlacement(2, "A");
        if (!assertionModel.getStatus()) {
            writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
            softAssert.fail(assertionModel.getErrorMessage());
        } else {
            writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
        }


        softAssert.assertAll();

    }


    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 2, description = "Verify if Dragging and Dropping A(Right) to B(Left) is working")
    public void DragAndDropRightToLeftCheck() {
        SoftAssert softAssert = new SoftAssert();

        // select checkbox 1
        DragAndDropPageActions.dragSecondDivBoxToFirstDivBox();

        assertionModel = DragAndDropPageActions.verifyIfExpectedDivBoxIsInRightPlacement(1, "A");
        if (!assertionModel.getStatus()) {
            writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
            softAssert.fail(assertionModel.getErrorMessage());
        } else {
            writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
        }
        assertionModel = DragAndDropPageActions.verifyIfExpectedDivBoxIsInRightPlacement(2, "B");
        if (!assertionModel.getStatus()) {
            writeSteps("FAIL", assertionModel.getErrorMessage(), assertionModel.getMedia());
            softAssert.fail(assertionModel.getErrorMessage());
        } else {
            writeSteps("PASS", assertionModel.getErrorMessage(), assertionModel.getMedia());
        }


        softAssert.assertAll();

    }


}
