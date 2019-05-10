import constants.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Base;
import webdriver.Driver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Gmail extends Base {
    Logger logger = LogManager.getLogger(Gmail.class);
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        driver = Driver.getWebDriverInstance();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        logger.trace("Starting Crome driver...");
    }

    @Test
    public void sendGoogleMailPositive() throws InterruptedException {
        logger.trace("Navigating to 'https://mail.google.com'...");
        driver.get("https://mail.google.com");
        Assert.assertTrue(driver.getCurrentUrl().contains("accounts.google.com"));
        logger.trace("User is on login page.");
        WebElement emailInput = driver.findElement(By.xpath("//input[@type='email']"));
        logger.trace("Typing email...");
        emailInput.sendKeys(Data.EMAIL);
        WebElement nextBtn1 = driver.findElement(By.xpath("//content/span[contains(text(),'Далі')]"));
        logger.trace("Clicking Next btn...");
        nextBtn1.click();
        waitFor().until(ExpectedConditions.visibilityOf(driver.findElement(By.id("profileIdentifier"))));
        WebElement helloField = driver.findElement(By.xpath("//h1/content"));
        logger.trace("Verifying user is on Welcome page...");
        waitFor().until(ExpectedConditions.visibilityOf(helloField));
        Assert.assertTrue(helloField.getText().equals("Вітаємо!"));
        logger.trace("OK");
        WebElement passwField = driver.findElement(By.cssSelector("input[type=\"password\"]"));
        logger.trace("Typing password...");
        passwField.sendKeys(Data.PASSW);
        WebElement nextBtn2 = driver.findElement(By.xpath("//content/span[contains(text(),'Далі')]"));
        logger.trace("Clicking Next btn...");
        nextBtn2.click();
        waitFor().until(ExpectedConditions.urlContains("#inbox"));
        logger.info("User is logged in.");
        WebElement writeBtn = driver.findElement(By.xpath("//div[contains(text(),'Compose')]"));
        logger.info("Clicking Compose btn...");
        writeBtn.click();
        waitFor().until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[role=\"dialog\"]"))));
        WebElement recipientField = driver.findElement(By.name("to"));
        logger.debug("Typing Recipient email...");
        recipientField.sendKeys(Data.RECIPIENT_EMAIL);
        WebElement subjectField = driver.findElement(By.name("subjectbox"));
        logger.debug("Typing subject of email...");
        subjectField.sendKeys(Data.SUBJECT);
        WebElement messageField = driver.findElement(By.cssSelector("div[role=\"textbox\"]"));
        logger.debug("Typing email body...");
        messageField.sendKeys(Data.MESSAGE_TEXT);
        WebElement sendBtn = driver.findElement(By.xpath("//div[contains(text(),'Send')]"));
        logger.debug("Clicking Send btn...");
        jsExecutor().executeScript("document.querySelector('div.dC').firstElementChild.click()");
        logger.debug("Navigating to Sent emails page...");
        jsExecutor().executeScript("document.querySelector(\"span.nU > a[title='Sent']\").click()");
        List<WebElement> listOfSentMails = driver.findElements(By.xpath("//div[@role='main']//div[@class='Cp']//tbody/tr"));
        WebElement cellThatContainsLastRecipientEmail = listOfSentMails.get(0).findElement(By.xpath("((./td)[5]/div)[2]/span"));
        logger.trace("Verifying that email was sent to correct email...");
        Assert.assertTrue(cellThatContainsLastRecipientEmail.getAttribute("email").equals(Data.RECIPIENT_EMAIL));
        logger.info("SUCCESS: email is sent properly.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            logger.trace("Closing WebDriver...");
            driver.quit();
        }
    }
}

