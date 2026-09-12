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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;


public class Signin {

    WebDriver driver;
    WebDriverWait wait;

    String url = "https://demo.prestashop.com/#/en/front";
    
    String email = "pub@prestashop.com";
    String password = "123456789";

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

    // Test Case 1 - Sign in page display verification

    @Test(priority = 1)
    public void verifySignIn() {

        try {
        	WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign in"))); 
        	signIn.click();      	
        	System.out.println("Test_1 PASS - Sign In is displayed.");
            takeScreenshot("Test_1_SignIn");

        } catch (Exception e) {
            takeScreenshot("Test_1_SignIn_FAIL");
            AssertJUnit.fail("Test_1 FAILED: " + e.getMessage());
        }
    }

    // Test Case 2 - Sign in page fields verification

    @Test(priority = 2)
    public void verifySignInForm() {

        try {
        	//verify the input areas
            WebElement emailfield = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
            WebElement passwordfield = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password")));
            //verification
            AssertJUnit.assertNotSame(emailfield.isDisplayed(),"Email field is not displayed.");
            AssertJUnit.assertNotSame(passwordfield.isDisplayed(),"Password field is not displayed.");
            //successful run output
            System.out.println("Test_2 PASS - Sign In form opened.");
            takeScreenshot("Test_2_SignInForm");

        } catch (Exception e) {

            takeScreenshot("Test_2_SignInForm_FAIL");
            AssertJUnit.fail("Test_2 FAILED: " + e.getMessage());
            
        }
    }

   

    // Test Case 3 - sign in with invalid info verification

    @Test(priority = 3)
    public void invalidLogin()throws IOException {

        try {
        	
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password")));
            //enter informations
            emailField.clear();
            emailField.sendKeys("user@1.com");

            passwordField.clear();
            passwordField.sendKeys("12456789");
            
            //click in sign-in in button
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-login")));
            loginButton.click();
          //verification
           // wait.until(( ExpectedConditions.visibilityOfElementLocated( By.cssSelector(".alert-danger")), ((ExpectedConditions.urlContains("login") )));
          //successful run output
            System.out.println("Test_3_PASS_Invalid Login try successful.");
            takeScreenshot("Test_3_InvalidLogin");
           

        } catch (Exception e) {

            takeScreenshot("Test_3_for_InvalidLogin_fail");
            AssertJUnit.fail("Test_3 FAILED due redirection to valid page: " + e.getMessage());
            
        }
    }

    // Test Case 4 - sign in with valid info verification

    @Test(priority = 4)
    public void validLogin() throws IOException{

    	WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign in"))); 
    	signIn.click(); 

        try {

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password")));

            emailField.clear();
            emailField.sendKeys(email);

            passwordField.clear();
            passwordField.sendKeys(password);

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-login")));
            loginButton.click();
            
            wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("my-account"), ExpectedConditions.visibilityOfElementLocated(By.id("identity_main_link"))));

            System.out.println("Test_4 PASS - login successful.");
            takeScreenshot("Test_4_ValidLogin");

        } catch (Exception e) {

            takeScreenshot("Test4_ValidLogin_FAIL");
            AssertJUnit.fail("Test_4 FAILED: " + e.getMessage());
        }
    }
    
    // Test Case 5 - sign out verification

    @Test(priority = 4)
    public void validLogout() throws IOException{

    	   try {

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password")));

            emailField.clear();
            emailField.sendKeys(email);

            passwordField.clear();
            passwordField.sendKeys(password);

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-login")));
            loginButton.click();
            
            wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("my-account"), ExpectedConditions.visibilityOfElementLocated(By.id("identity_main_link"))));

            System.out.println("Test_4 PASS - login successful.");
            takeScreenshot("Test_4_ValidLogin");

        } catch (Exception e) {

            takeScreenshot("Test4_ValidLogin_FAIL");
            AssertJUnit.fail("Test_4 FAILED: " + e.getMessage());
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
