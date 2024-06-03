package application.actions;

import application.TestBase;
import application.pages.CheckBoxPage;
import framework.driver.UiDriver;
import framework.models.AssertionModel;
import io.qameta.allure.Step;

import static framework.driver.UiDriverActions.takeScreenshotAsBytes;
import static framework.utility.reporter.CustomExtentReport.takeScreenshot;


public class CheckBoxPageActions {


    @Step("Verify Check Box page is landed.")
    public static AssertionModel verifyCheckBoxPageIsLanded(){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyCheckBoxPageIsLanded");
        String actualUrl = UiDriver.getCurrentUrl();
        String expectedUrl = TestBase.setUp.getAppUrl()+"checkboxes";
        if(actualUrl.equals(expectedUrl)){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("User is landed to `checkboxes`." + actualUrl);
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            takeScreenshotAsBytes();
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("User is NOT landed to `checkboxes`. actualUrl: " + actualUrl + " expected url: "+expectedUrl);
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        takeScreenshotAsBytes();
        return  assertionModel;
    }

    @Step("click on Check Box by Index number")
    public static void clickOnCheckBoxByIndexNumber(int index, Boolean check){
        CheckBoxPage.getCheckBoxLocatorByIndex(index).waitAndReturnStatusUntilPageContainsElement();
        CheckBoxPage.getCheckBoxLocatorByIndex(index).waitForElementVisibility();
        if(!CheckBoxPage.getCheckBoxLocatorByIndex(index).isSelected() && check){
            CheckBoxPage.getCheckBoxLocatorByIndex(index).click();
        }
        if(CheckBoxPage.getCheckBoxLocatorByIndex(index).isSelected() && !check){
            CheckBoxPage.getCheckBoxLocatorByIndex(index).click();
        }
    }


    @Step("Verify if expected Checkbox is selected.")
    public static AssertionModel verifyIfExpectedCheckBoxIsSelected(Integer checkBoxIndex){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyIfExpectedCheckBoxIsSelected");
        boolean isChecked = CheckBoxPage.getCheckBoxLocatorByIndex(checkBoxIndex).isSelected();
        if(isChecked){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("Expected CheckBox "+checkBoxIndex+" is Selected`." );
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            takeScreenshotAsBytes();
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("Expected CheckBox "+checkBoxIndex+" is NOT Selected`." );
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        takeScreenshotAsBytes();
        return  assertionModel;
    }





}
