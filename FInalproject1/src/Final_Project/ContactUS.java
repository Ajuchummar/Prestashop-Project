package Final_Project;


import org.testng.annotations.Test;

import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class ContactUS {

    WebDriver driver;
    WebDriverWait wait;
    String url = "https://demo.prestashop.com/#/en/front";

	@BeforeClass
    public void setUp() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(url);     
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));
        System.out.println("PrestaShop demo loaded successfully.");
    }

    // test1
    @Test(priority = 1)
    public void test01_LoadContactUsPage() {
        // Click Contact Us
        WebElement contactUs = wait.until(ExpectedConditions.elementToBeClickable( By.linkText("Contact us")));
        contactUs.click();
        // Verify Contact Us page
        WebElement contactHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Contact us')]") ));
        AssertJUnit.assertNotSame(contactHeading.isDisplayed(),"Contact Us page was not displayed.");
        System.out.println("TEST CASE 1 PASS: Contact Us page loaded successfully.");
        takeScreenshot("01_CONT_Load_Contact_Us_Page");
        // Stay in same browser/page
        driver.switchTo().defaultContent();
    }
    // test 2
    
    @Test(priority = 2)
    public void test02_AddContactUsDetails() {

        WebElement subject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-us-subject-select\"]")));
        Select selectSubject = new Select(subject);
        // inputs
        selectSubject.selectByVisibleText("Customer service");
        
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact-us-email-input\"]")));
        email.clear();
        email.sendKeys("1234@a.com");
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[@id=\"contact-us-message-textarea\"]")));
        message.clear();
        message.sendKeys("Hello");
        AssertJUnit.assertEquals(new Select(subject).getFirstSelectedOption().getText(), "Customer service","Subject was not selected correctly.");
        AssertJUnit.assertEquals(email.getAttribute("value"),"1234@a.com","Email was not entered correctly.");
        AssertJUnit.assertEquals(message.getAttribute("value"),"Hello","Message was not entered correctly.");
        System.out.println("TEST CASE 2 PASS: Contact Us form data entered successfully.");
        takeScreenshot("02_CONT__Add_Contact_Details");
        // Stay on the same browser
        driver.switchTo().defaultContent();
    }

    @Test(priority = 3)
    public void test03_SubmitContactUsForm() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable( By.name("submitMessage")));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",submitButton);
        try {
            submitButton.click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",submitButton);
        }

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Your message has been successfully sent to our team.')]")));

        AssertJUnit.assertNotSame(successMessage.isDisplayed(), "Success message was not displayed.");
        System.out.println("Test33 PASS: Contact Us form submitted successfully.");
        takeScreenshot("03_CONT_Submit_Contact_Us_Form");

        driver.switchTo().defaultContent();
    }
    
    
  //Screenshort
    public void takeScreenshot(String fileName) {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination =new File("screenshots/" + fileName + ".png");

        try {

            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // after test

	@AfterClass
    public void tearDown() {
    	System.out.println("Contact Us_All Tests completed.");

    }


}
