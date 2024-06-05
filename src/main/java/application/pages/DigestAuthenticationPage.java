package application.pages;

import framework.elements.HtmlElement;
import org.openqa.selenium.By;


public class DigestAuthenticationPage {
    private static final By CONGRATULATIONS_MESSAGE_TEXT_LOCATOR = By.xpath("//div[@class='example']//P[normalize-space(text())='Congratulations! You must have the proper credentials.']");



    public static HtmlElement getCongratulationsMessageTextLocator(){
      return   new HtmlElement(CONGRATULATIONS_MESSAGE_TEXT_LOCATOR);
    }


}
