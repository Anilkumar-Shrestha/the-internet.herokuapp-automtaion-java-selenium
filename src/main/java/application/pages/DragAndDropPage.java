package application.pages;

import framework.elements.HtmlElement;
import org.openqa.selenium.By;


public class DragAndDropPage {
    private static final String DIV_BOX_LOCATOR_PATTERN = "//div[@id='column-%s']";
    private static final String HEADER_TEXT_LOCATOR_PATTERN = DIV_BOX_LOCATOR_PATTERN + "/header";


    public static HtmlElement getFirstDivBoxLocator() {
        return new HtmlElement(By.xpath(String.format(DIV_BOX_LOCATOR_PATTERN, "a")));
    }

    public static HtmlElement getSecondDivBoxLocator() {
        return new HtmlElement(By.xpath(String.format(DIV_BOX_LOCATOR_PATTERN, "b")));
    }

    public static HtmlElement getDivBoxHeaderTextLocatorByIndex(int index) {
        HtmlElement returnElement =new HtmlElement(By.xpath("Please check index out of range"));
        returnElement = switch (index) {
            case 1 -> new HtmlElement(By.xpath(String.format(HEADER_TEXT_LOCATOR_PATTERN, "a")));
            case 2 -> new HtmlElement(By.xpath(String.format(HEADER_TEXT_LOCATOR_PATTERN, "b")));
            default -> returnElement;
        };

        return returnElement;
    }


}
