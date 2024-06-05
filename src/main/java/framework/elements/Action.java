package framework.elements;

import framework.driver.UiDriver;
import org.openqa.selenium.interactions.Actions;

public class Action {

    public static void dragAndDrop(HtmlElement source, HtmlElement target) {
        Actions actions = new Actions(UiDriver.getWebDriver());
        actions.dragAndDrop(source.getWebElement(), target.getWebElement()).perform();
    }

    public static void dragAndDropByOffset(HtmlElement source, int xOffset, int yOffset) {
        Actions actions = new Actions(UiDriver.getWebDriver());
        actions.dragAndDropBy(source.getWebElement(), xOffset, yOffset).perform();
    }

}
