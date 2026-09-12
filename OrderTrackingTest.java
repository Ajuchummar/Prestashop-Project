package Final_Project;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
//OrderTrackingTest 
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


public class OrderTrackingTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    String url = "https://demo.prestashop.com/#/en/front";

    @BeforeClass
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
        // Open PrestaShop home page
        driver.get(url);
        // Switch to PrestaShop iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));
        System.out.println("PrestaShop home page opened successfully.");
       
    }

    // TEST 1 - scroll to bottom
    
    @Test(priority = 1)
    public void scrollToFooter() throws IOException { 
   // Scroll to bottom of the page 
   js.executeScript( "window.scrollTo(0, document.body.scrollHeight);"); 
   // Wait for footer 
   WebElement footer = wait.until( ExpectedConditions.visibilityOfElementLocated( By.tagName("footer"))); 
   AssertJUnit.assertNotSame( footer.isDisplayed(), "Footer is not displayed." ); 
   System.out.println("Scrolled down to footer successfully."); 
   takeScreenshot("01_Footer"); 
   }
    
    
    // TEST 2 - Verify Order Tracking is displayed

    @Test(priority = 2)
    public void verifyOrderTracking() throws IOException {

        WebElement orderTracking = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"footer_customeraccountlinks\"]/ul/li[1]/a")));

        AssertJUnit.assertNotSame(orderTracking.isDisplayed(),"Order tracking is not displayed.");
        System.out.println("Order tracking is displayed successfully.");
        takeScreenshot("01_Order_Tracking_Home_Page");
    }

    // TEST 3 - Click Order Tracking

    @Test(priority = 3, dependsOnMethods = "verifyOrderTracking")
    public void clickOrderTracking() throws IOException {

        WebElement orderTracking = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"footer_customeraccountlinks\"]/ul/li[1]/a")));
        orderTracking.click();

        System.out.println("Order tracking clicked successfully.");

        // Verify Order Tracking page
        WebElement orderReference = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"guestOrderTrackingForm\"]/div[1]/label")));
        AssertJUnit.assertNotSame(orderReference.isDisplayed(),"Order Tracking page was not displayed.");

        takeScreenshot("02_Order_Tracking_Page");
    }


    // TEST 4 - Enter Order Reference
    @Test(priority = 4, dependsOnMethods = "clickOrderTracking")
    public void enterOrderReference() throws IOException {

        WebElement orderReference = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/section/form/div[1]/input")));
        orderReference.clear();
        orderReference.sendKeys("XKBKNABJK");

        System.out.println( "Order Reference entered: XKBKNABJK");
        takeScreenshot("03_Order_Reference_Entered");
    }

    // TEST 5 - Enter Email
    @Test(priority = 5, dependsOnMethods = "enterOrderReference")
    public void enterEmail() throws IOException {

        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/section/form/div[2]/input")));
        email.clear();
        email.sendKeys("pub@prestashop.com");

        System.out.println("Email entered: pub@prestashop.com");
        takeScreenshot("04_Email_Entered");
    }

    // TEST 6 - Verify entered details
    @Test(priority = 6, dependsOnMethods = "enterEmail")
    public void verifyOrderTrackingDetails() throws IOException {

        WebElement orderReference = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/section/form/div[1]/input")));
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/section/form/div[2]/input")));

        AssertJUnit.assertEquals(orderReference.getAttribute("value"),"XKBKNABJK","Order Reference value is incorrect.");
        AssertJUnit.assertEquals(email.getAttribute("value"),"pub@prestashop.com","Email value is incorrect.");

        System.out.println("Order Reference and Email verified successfully.");
        takeScreenshot("05_Order_Tracking_Details_Verified");
    }

    // TEST 6 - Verify entered details
    @Test(priority = 6, dependsOnMethods = "verifyOrderTrackingDetails")
    public void sendtocheck() throws IOException {

      WebElement sendbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/section/form/footer/button")));
      sendbutton.click();   

        System.out.println("Send button clciked successfully.");
        takeScreenshot("05_Order_Tracking_Details_Verified");
        
        WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"order_status_heading\"]")));
        AssertJUnit.assertNotSame(statusElement.isDisplayed(),"Order tracking is displayed.");
        System.out.println("Order tracking page displayed successfully.");
        
    }

    // Screenshot method
    public void takeScreenshot(String fileName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File("/screenshots/" + fileName + ".png");
        FileUtils.copyFile(source, destination);

        System.out.println("Screenshot saved: "+ destination.getAbsolutePath());
    }
    @AfterClass
    public void allsuccessrun() {
        System.out.println("Signin_AllTests completed.");

    }
}

