package application.actions;

import application.TestBase;
import application.pages.AddRemoveElementsPage;
import framework.driver.UiDriver;
import framework.models.AssertionModel;
import io.qameta.allure.Step;

import static framework.utility.reporter.CustomExtentReport.takeScreenshot;


public class AddRemoveElementsPageActions {

    @Step("click on Add Element")
    public static void clickOnAddElement(){
        AddRemoveElementsPage.getAddElementButtonLocator().waitAndReturnStatusUntilPageContainsElement();
        AddRemoveElementsPage.getAddElementButtonLocator().waitForElementVisibility();
        AddRemoveElementsPage.getAddElementButtonLocator().click();
    }

    @Step("click on Delete Element")
    public static void clickOnDeleteElementByIndex(int index){
        AddRemoveElementsPage.getDeleteElementButtonLocatorByIndex(index).waitAndReturnStatusUntilPageContainsElement();
        AddRemoveElementsPage.getDeleteElementButtonLocatorByIndex(index).waitForElementVisibility();
        AddRemoveElementsPage.getDeleteElementButtonLocatorByIndex(index).click();
    }

    @Step("Get Number of Delete Button")
    public static int getNumberOfDeleteButton(){
      return  AddRemoveElementsPage.getListOfDeleteButtonLocator().getListOfHtmlElements().size();
    }


    @Step("Verify add remove element page is landed.")
    public static AssertionModel verifyAddRemoveElementsPageIsLanded(){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyAddRemoveElementsPageIsLanded");
        String actualUrl = UiDriver.getCurrentUrl();
        String expectedUrl = TestBase.setUp.getAppUrl()+"add_remove_elements/";
        if(actualUrl.equals(expectedUrl)){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("User is landed to `AddRemoveElementsPage`." + actualUrl);
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("User is NOT landed to `AddRemoveElementsPage`. actualUrl: " + actualUrl + " expected url: "+expectedUrl);
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        return  assertionModel;
    }

    @Step("Verify number of delete button is as expected.")
    public static AssertionModel verifyNumberOfDeleteButtonIsAsExpected(int expectedNumberOfDeleteButton){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyNumberOfDeleteButtonIsAsExpected");
        int actualDeleteButtonCount = AddRemoveElementsPageActions.getNumberOfDeleteButton();
        if(actualDeleteButtonCount==expectedNumberOfDeleteButton){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("Expected number of Delete button is as expected." + actualDeleteButtonCount);
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("Expected number of Delete button is as expected. actualDeleteButtonCount: " + actualDeleteButtonCount + " expectedNumberOfDeleteButton: "+expectedNumberOfDeleteButton);
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        return  assertionModel;
    }



}
