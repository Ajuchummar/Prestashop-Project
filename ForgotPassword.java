package Final_Project;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
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
import org.testng.annotations.AfterSuite;



public class ForgotPassword {

    WebDriver driver;
    WebDriverWait wait;

    String url = "https://demo.prestashop.com/#/en/front";

    @BeforeClass
    public void setup() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(url);
        // Switch to iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));

    }

    @Test(priority = 1)
    public void verifyForgetpassword() throws IOException {

        try {
        	WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign in"))); 
        	signIn.click();      	
        	System.out.println("FTest_1 PASS - Sign In is displayed.");
            takeScreenshot("01_Forget_SignIn pagevisible");
            
       	 // Verify Forgot Password display
            
            WebElement forgotPasswordHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Forgot your password?')]")));
            AssertJUnit.assertNotSame(forgotPasswordHeading.isDisplayed(),"Forgot Password page is not displayed.");                                   
            takeScreenshot("02_Forget_Forgot_Password_visible");

        } catch (Exception e) {
            takeScreenshot("FTest_1_FAIL");
            AssertJUnit.fail("FTest_1 FAILED: " + e.getMessage());
        }
    }
    
    
    @Test(priority = 2)
    public void verifyforgetpasswordpage() throws IOException {

       try {
        	WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign in"))); 
        	signIn.click();      	
        	System.out.println("FTest_2 PASS - Sign In is displayed.");
            takeScreenshot("03_Forget__forgetpassword");     
            
            WebElement forgotPassword = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Forgot your password?")));
            forgotPassword.click();
            takeScreenshot("04_Forget_Forgot_Password_clicked");

        } catch (Exception e) {
            takeScreenshot("FTest_2_FAIL");
            AssertJUnit.fail("FTest_2 FAILED: " + e.getMessage());
        }
    }
    
    
    @Test(priority = 3)
    public void enterforgetpassword() throws IOException {

       try {

    	   WebElement email = wait.until( ExpectedConditions.visibilityOfElementLocated( By.name("email"))); 
    	   email.clear(); 
    	   email.sendKeys("pub@prestashop.com"); 
    	   takeScreenshot("05_Forget_Email_Entered.png");
            

        } catch (Exception e) {
            takeScreenshot("FTest_3_FAIL");
            AssertJUnit.fail("FTest_3 FAILED: " + e.getMessage());
        }
    }
    @Test(priority = 4)
    public void  clicksendlink() throws IOException {

       try {

    	   WebElement sendResetLink = wait.until( ExpectedConditions.elementToBeClickable( By.id("send-reset-link"))); 
    	   sendResetLink.click(); 
    	   takeScreenshot("06_Forget__ResetLinkSubmitted.png");
            

        } catch (Exception e) {
            takeScreenshot("FTest_4_FAIL");
            AssertJUnit.fail("FTest_4 FAILED: " + e.getMessage());
        }
    }
             

        
    // Screenshot method
    public void takeScreenshot(String fileName) throws IOException {

        TakesScreenshot screenshot =(TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);

        File destination = new File("./Screenshots/" + fileName + ".png");
        FileUtils.copyFile(source, destination);
        System.out.println("Screenshot saved: "+ destination.getAbsolutePath());
    }

    @AfterMethod
	@AfterSuite
    public void tearDown() {
        System.out.println("ForgotPassword_All Tests completed.");

    }
}

