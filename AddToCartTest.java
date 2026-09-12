package Final_Project;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class AddToCartTest {

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

    @Test(priority = 1)
    public void signIn() {

        try {
            // Click Sign In
            WebElement signIn = wait.until( ExpectedConditions.elementToBeClickable(By.linkText("Sign in")));
            signIn.click();
            // Enter Email
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-email")));
            email.sendKeys("pub@prestashop.com");

            // Enter Password
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("field-password")));
            password.sendKeys("123456789");
            // Click Sign In button
            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-login")));
            signInButton.click();
            System.out.println("Sign In Test sucessfull: " );
            takeScreenshot("01__ADCART_Login Button clciked");
           
        } catch (Exception e) {
            System.out.println("Sign In Test Failed: " + e.getMessage());

        }
    }

    @Test(priority = 2)
    public void searchProduct() {

        try {

            // Search box
            WebElement searchBox = wait.until( ExpectedConditions.visibilityOfElementLocated(By.name("s")));
            searchBox.clear();
            searchBox.sendKeys("Hummingbird printed t-shirt");

            // Click Search
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/header/div[2]/div/div/div[4]/div/div/div/a/p")));
            searchButton.click();

            System.out.println("Search button clicked.");

            // Verify product is displayed
            WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"center-column\"]/div[1]/div[2]")));
            AssertJUnit.assertNotSame(product.isDisplayed(),"Hummingbird Printed T-Shirt is not displayed.");
            System.out.println("product displayed successfully.");
            takeScreenshot("02_ADCART_Product searched");

        } catch (Exception e) {

            System.out.println("Search Product Test Failed: " + e.getMessage());

        }
    }

    
     @Test(priority = 3)
    public void Addtocart() {

        try {

            // Adding to cart
            WebElement addin = wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/div[1]/div[2]/div[4]/form/div[2]/div[2]/div[2]/button")));
            addin.click();
            System.out.println("add button clicked.");

            // Verify product is displayed
            WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div[1]/div/div[2]/div[1]")));
            AssertJUnit.assertNotSame(product.isDisplayed(),"Hummingbird Printed T-Shirt is not displayed.");
           System.out.println("Product added successfully.");
           takeScreenshot("03_ADCART_Product Added");

        } catch (Exception e) {

            System.out.println("Adding Product Test Failed: " + e.getMessage());

        }
    }
 
     @Test(priority = 4)
     public void proceedtocart() {

         try {

             // proceed cart
             WebElement paddin = wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/div/div/div[3]/a")));
             paddin.click();
             System.out.println("proceed button clicked.");
             takeScreenshot("04_ADCART_proceed to cart popup");

           } catch (Exception e) {

             System.out.println("proceed tocart failed ");
         }
     }
     
     @Test(priority = 5)
     public void proceedtocheckout() {

         try {

             // proceed to checkout
             WebElement prockeck = wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div[3]/div/div[1]/div[2]/div/div[1]/div/div[3]/div/a")));
             prockeck.click();
             System.out.println("proceed to checkout button clicked.");     
             takeScreenshot("05_ADCART_proceed to checkout");

         } catch (Exception e) {

             System.out.println("proceed to checkout faild");
         }
     }
     
     @Test(priority = 6)
     public void addresschoose() {

         try {

             // selection of address
             WebElement addprockeck = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("/html/body/main/div[4]/div/div/div[1]/div/section[2]/div[2]/div/form/div[1]/article[1]/label/div[1]/span[1]/input")));
             addprockeck.click();
             System.out.println("radio button selected.");    
             takeScreenshot("06_ADCART_Address selection");

         } catch (Exception e) {

             System.out.println("address selection radiobutton faild");
         }
     }

     
     @Test(priority = 7)
     public void proceedtocheckout1() {

         try {

             // proceed to checkout
             WebElement addprockeck = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("/html/body/main/div[4]/div/div/div[1]/div/section[2]/div[2]/div/form/div[3]/button[2]")));
             addprockeck.click();
             System.out.println("prceed to checkout selected.");    
             takeScreenshot("07_ADCART_Checkout");

         } catch (Exception e) {

             System.out.println("checkout faild");
         }
     }

     @Test(priority = 8)
     public void payment1() {

         try {

             // 
             WebElement addprockeck = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("//*[@id=\"delivery_option_1\"]")));
             addprockeck.click();
             System.out.println("courier selected.");     
             // Adding to cart
             
             WebElement addprockeck1 = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("/html/body/main/div[4]/div/div/div[1]/div/section[3]/div[2]/div[2]/form/div[3]/button[2]")));
             addprockeck1.click();
             System.out.println("continue to payment.");     
             takeScreenshot("08_ADCART_Courier selection");

         } catch (Exception e) {

             System.out.println("payment page  faild");
         }
     }
     
     @Test(priority = 9)
     public void payments() {

         try {

             // Pay by Cash on Delivery 
             WebElement paymentsk = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("//*[@id=\"payment-option-2\"]")));
             paymentsk.click();
             System.out.println("Pay by Cash on Delivery .");     
             
             
             // Adding to cart
             
             WebElement payment21 = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("//*[@id=\"conditions_to_approve[terms-and-conditions]\"]")));
             payment21.click();
             System.out.println("I agree to the terms of service and will adhere to them unconditionally.");       
             
             WebElement payment22 = wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("/html/body/main/div[4]/div/div/div[1]/div/section[4]/div[2]/div[3]/div/div[1]/button")));
             payment22.click();
             System.out.println("place order.");  
             takeScreenshot("09_ADCART_order placement");
             

         } catch (Exception e) {

             System.out.println("payment page  faild");
         }
     }
     
     @Test(priority = 10)
     public void ordersucess() {

         try {

             wait.until( ExpectedConditions.visibilityOfElementLocated
            		 (By.xpath("//*[contains(text(),'Your order is confirmed')]")));
             
             System.out.println("order placed sucessfull .");  
             takeScreenshot("10_ADCART_order placement confirmed");

         } catch (Exception e) {

             System.out.println("order  faild");
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
    public void tearDown() {

    	 System.out.println("   Test execution completed.");
    }
}
