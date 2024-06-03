package application.pages;

import framework.elements.Checkbox;
import org.openqa.selenium.By;


public class CheckBoxPage {
    private static final String CHECKBOX_LOCATOR ="//input[@type='checkbox'][%d]";


    public static Checkbox getCheckBoxLocatorByIndex(int index){
        return   new Checkbox(By.xpath(String.format(CHECKBOX_LOCATOR,index)));
    }

}
