package utils;

import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.Driver;

public class Base {

    public Wait waitFor(){
        Wait wait = new WebDriverWait(Driver.getWebDriverInstance(), 15);
        return wait;
    }
}
