package application.actions;

import application.TestBase;
import application.pages.BrokenImagesPage;
import framework.driver.UiDriver;
import framework.models.AssertionModel;
import framework.utility.loggerator.Logger;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import java.util.List;

import static framework.driver.UiDriverActions.takeScreenshotAsBytes;
import static framework.utility.reporter.CustomExtentReport.takeScreenshot;


public class BrokenImagesPageActions {

    @Step("Verify Broken Images page is landed.")
    public static AssertionModel verifyBrokenImagesPageIsLanded(){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyBrokenImagesPageIsLanded");
        String actualUrl = UiDriver.getCurrentUrl();
        String expectedUrl = TestBase.setUp.getAppUrl()+"broken_images";
        if(actualUrl.equals(expectedUrl)){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("User is landed to `broken_images`." + actualUrl);
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("User is NOT landed to `broken_images`. actualUrl: " + actualUrl + " expected url: "+expectedUrl);
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        return  assertionModel;
    }

    @Step("Get total images on the page")
    public static int getTotalImagesOnThePage(){
       return BrokenImagesPage.getAllImagesLocator().getListOfHtmlElements().size();
    }



    @Step("Verify Broken images in Broken Images page.")
    public static AssertionModel verifyEachImagesToSeeIfItsBroken(){
        AssertionModel assertionModel = new AssertionModel();
        assertionModel.setMethodName("verifyEachImagesToSeeIfItsBroken");

         List<String> linksImages =
                 BrokenImagesPage.getAllImagesLocator().getListOfHtmlElements().stream()
                .map(e-> e.getAttributeValue("src"))
                .filter( src -> !isUrlValid(src))
                         .toList();
        if(linksImages.isEmpty()){
            assertionModel.setStatus(true);
            assertionModel.setErrorMessage("There are no images broken in this Broken Images page" );
            assertionModel.setMedia(takeScreenshot(TestBase.methodName, "pass_SS_" + assertionModel.currentDateTime()));
            takeScreenshotAsBytes();
            return assertionModel;
        }
        assertionModel.setStatus(false);
        assertionModel.setErrorMessage("There are images broken in this Broken Images page. "+ linksImages );
        assertionModel.setMedia(takeScreenshot(TestBase.methodName, "fail_SS_" + assertionModel.currentDateTime()));
        takeScreenshotAsBytes();
        return  assertionModel;
    }



    // Method to check if a URL returns a 200 response
    private static boolean isUrlValid(String url) {
        try {
            int statusCode = RestAssured.get(url).getStatusCode();
            return statusCode == 200;
        } catch (Exception e) {
            Logger.getLogger().info(url+" does not status code as 200");
            return false;
        }
    }


}
