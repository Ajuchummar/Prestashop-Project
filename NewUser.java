package Final_Project;



import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;


public class NewUser{

    WebDriver driver;
    WebDriverWait wait;
    String url = "https://demo.prestashop.com/#/en/front";

    @BeforeMethod
	@BeforeClass
    public void setUp() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(url);
        // Wait for demo iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));
    }

    @Test (priority = 1)
    public void createCustomerAccount() {

        try {

            // Click Sign in
           WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign in"))); 
        	signIn.click();      	
        	System.out.println("Test_1 PASS - Sign In is displayed."); 	
        	
            // Click Create an account
            WebElement createAccount = wait.until(ExpectedConditions.elementToBeClickable( By.linkText("Create an account")));
            createAccount.click();
            System.out.println("Create an account clicked.");

            // Enter First Name
            WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated( By.id("field-firstname")));
            firstName.sendKeys("John");
            // Enter Last Name
            WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-lastname")));
            lastName.sendKeys("Cook");
            // Enter Email
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
            email.sendKeys("johncook@customer.com");
            // Enter Password
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password")));
            password.sendKeys("JohnCook@1234567892");
            // checkbox selections
            WebElement checkbox1 =wait.until(ExpectedConditions.presenceOfElementLocated(By.id("field-psgdpr")));
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();",checkbox1);            
            WebElement checkbox2 =wait.until(ExpectedConditions.presenceOfElementLocated(By.id("field-customer_privacy")));
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();",checkbox2);   
            System.out.println("Necessary checkbox selected.");
            // screenshort            
            takeScreenshot("Test_1_accountdetails");
            // Click Create Account
            WebElement createButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Create account')]")));
            createButton.click();
            System.out.println("Create Account button clicked.");
            // Verify successful account creation
            WebElement accountName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".account")));
            AssertJUnit.assertNotSame(accountName.isDisplayed(),"Account was not created successfully.");
            System.out.println("Customer account created successfully for John Cook.");            
            takeScreenshot("Test_1_logeedin");

        } catch (Exception e) {

            AssertJUnit.fail("Create Account test failed because: "+ e.getMessage());
        }
    }
    
    // Screenshot
    public void takeScreenshot(String fileName) {

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File("screenshots/" + fileName + ".png");
        try {

            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @AfterClass
        public void allsuccessrun() {
            System.out.println("Signin_AllTests completed.");

        }
}
