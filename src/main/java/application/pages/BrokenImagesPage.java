package application.pages;

import framework.elements.HtmlElements;
import org.openqa.selenium.By;


public class BrokenImagesPage {
    private static final By IMAGES_TAG_LIST_LOCATOR = By.xpath("//img");



    public static HtmlElements getAllImagesLocator(){
      return   new HtmlElements(IMAGES_TAG_LIST_LOCATOR);
    }


}
