package application.actions;

import application.TestBase;
import application.pages.BasicAuthPage;
import framework.driver.UiDriver;
import framework.driver.UiDriverActions;
import framework.models.AssertionModel;
import framework.utility.Helper.SetCredentialsForSuite;
import io.qameta.allure.Step;

import static framework.driver.UiDriverActions.takeScreenshotAsBytes;
import static framework.utility.reporter.CustomExtentReport.takeScreenshot;


public class BasicAuthPageActions {

    @Step("Register Authentication using username and password")
    public static void registerAuthenticationUsingUsernameAndPassword(){
        UiDriver.registerAuthenticationUsing(SetCredentialsForSuite.basicAuthUserName,SetCredentialsForSuite.basicAuthUserPassword);
    }

    @Step("Basic Authentication using username and password in web url (old method)")
    public static void basicAuthenticationUsingOldMethod(){
        UiDriverActions.navigateToUrl("https://admin:admin@the-internet.herokuapp.com/basic_auth");
    }



    @Step("VerifyCongratulations on basic Authentication message is seen.")
    public static AssertionModel verifyCongratulationOnAuthenticationMessageIsSeen(){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyCongratulationOnAuthenticationMessageIsSeen");
        BasicAuthPage.getCongratulationsMessageTextLocator().waitAndReturnStatusUntilPageContainsElement();
        if(BasicAuthPage.getCongratulationsMessageTextLocator().waitAndReturnStatusForElementVisibility()){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("Congratulations on Authentication Message is seen after successful authentication." );
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            takeScreenshotAsBytes();
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("Congratulations on Authentication Message is NOT seen after successful authentication." );
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        takeScreenshotAsBytes();
        return  assertionModel;
    }



}
