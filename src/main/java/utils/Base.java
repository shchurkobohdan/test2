package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import webdriver.Driver;

public class Base {

    public Wait waitFor(){
        Wait wait = new WebDriverWait(Driver.getWebDriverInstance(), 25);
        return wait;
    }

    public JavascriptExecutor jsExecutor(){
        JavascriptExecutor jsExec = (JavascriptExecutor) Driver.getWebDriverInstance();
        return jsExec;
    }
}
