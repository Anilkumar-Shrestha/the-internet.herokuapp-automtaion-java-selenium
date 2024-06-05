package application.actions;

import application.TestBase;
import application.pages.DragAndDropPage;
import framework.driver.UiDriver;
import framework.elements.Action;
import framework.models.AssertionModel;
import io.qameta.allure.Step;

import static framework.driver.UiDriverActions.takeScreenshotAsBytes;
import static framework.utility.reporter.CustomExtentReport.takeScreenshot;


public class DragAndDropPageActions {


    @Step("Verify Drag and Drop page is landed.")
    public static AssertionModel verifyDragAndDropPageIsLanded(){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyDragAndDropPageIsLanded");
        String actualUrl = UiDriver.getCurrentUrl();
        String expectedUrl = TestBase.setUp.getAppUrl()+"drag_and_drop";
        if(actualUrl.equals(expectedUrl)){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("User is landed to `drag_and_drop`." + actualUrl);
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            takeScreenshotAsBytes();
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("User is NOT landed to `drag_and_drop`. actualUrl: " + actualUrl + " expected url: "+expectedUrl);
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        takeScreenshotAsBytes();
        return  assertionModel;
    }

    @Step("Drag first div box to second div box")
    public static void dragFirstDivBoxToSecondDivBox(){
        DragAndDropPage.getFirstDivBoxLocator().waitAndReturnStatusUntilPageContainsElement();
        DragAndDropPage.getSecondDivBoxLocator().waitAndReturnStatusUntilPageContainsElement();
        Action.dragAndDrop(DragAndDropPage.getFirstDivBoxLocator(),DragAndDropPage.getSecondDivBoxLocator());
    }

    @Step("Drag second div box to first div box")
    public static void dragSecondDivBoxToFirstDivBox(){
        DragAndDropPage.getFirstDivBoxLocator().waitAndReturnStatusUntilPageContainsElement();
        DragAndDropPage.getSecondDivBoxLocator().waitAndReturnStatusUntilPageContainsElement();
        Action.dragAndDrop(DragAndDropPage.getSecondDivBoxLocator(),DragAndDropPage.getFirstDivBoxLocator());
    }


    @Step("Verify if expected divBox is in the right placement")
    public static AssertionModel verifyIfExpectedDivBoxIsInRightPlacement(int divIndex, String expectedHeaderText ){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyIfExpectedDivBoxIsInRightPlacement");
        DragAndDropPage.getDivBoxHeaderTextLocatorByIndex(divIndex).waitAndReturnStatusUntilPageContainsElement();
        String actualHeaderText = DragAndDropPage.getDivBoxHeaderTextLocatorByIndex(divIndex).getText().trim();
        if(actualHeaderText.equals(expectedHeaderText)){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("Expected Header text "+actualHeaderText+" is in right placement : "+divIndex );
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            takeScreenshotAsBytes();
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("In " +divIndex+ " div, Expected Header text is "+expectedHeaderText+" But actual is : "+actualHeaderText );
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        takeScreenshotAsBytes();
        return  assertionModel;
    }





}
