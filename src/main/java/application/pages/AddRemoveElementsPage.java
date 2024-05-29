package application.pages;

import framework.elements.HtmlElement;
import framework.elements.HtmlElements;
import org.openqa.selenium.By;


public class AddRemoveElementsPage {
    private static final By ADD_ELEMENT_BUTTON_LOCATOR = By.xpath("//div[@class='example']/button");
    private static final By LIST_OF_DELETE_ELEMENT_BUTTON_LOCATOR = By.xpath( "//button[@class='added-manually']");
    private static final String DELETE_ELEMENT_BUTTON_LOCATOR = "//button[@class='added-manually'][%d]";


    public static HtmlElement getAddElementButtonLocator(){
      return   new HtmlElement(ADD_ELEMENT_BUTTON_LOCATOR);
    }

    public static HtmlElements getListOfDeleteButtonLocator(){
        return  new HtmlElements(LIST_OF_DELETE_ELEMENT_BUTTON_LOCATOR," list of delete button") ;
    }

    public static HtmlElement getDeleteElementButtonLocatorByIndex(int index){
        return   new HtmlElement(By.xpath(String.format(DELETE_ELEMENT_BUTTON_LOCATOR,index)));
    }

}
