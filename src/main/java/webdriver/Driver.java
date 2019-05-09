package webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {
    private static WebDriver driver;

    public static WebDriver getWebDriverInstance(){
        if(driver == null){
            driver = new ChromeDriver();
        }
        return driver;
    }
}
