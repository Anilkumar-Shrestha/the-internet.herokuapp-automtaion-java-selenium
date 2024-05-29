package application.pages;

import framework.elements.HtmlElement;
import org.openqa.selenium.By;

public class MainPage {
    private static final String EXAMPLE_FROM_LIST_BY_TEXT_LOCATOR = "//div[@id='content']//li/a[starts-with(normalize-space(),'%s')]";


    public static HtmlElement getExampleFromListByTextLocator(String name){
      return   new HtmlElement(By.xpath(String.format(EXAMPLE_FROM_LIST_BY_TEXT_LOCATOR,name)));
    }
}
