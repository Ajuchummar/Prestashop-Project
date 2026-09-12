package Final_Project;

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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class Home {

    WebDriver driver;
    WebDriverWait wait;

    String URL = "https://demo.prestashop.com/#/en/front";

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(URL);
    }

    // URL
    @BeforeMethod
    public void resetPage() {      
        driver.get(URL);
    }

  // iframe loading
    public void switchToPrestaShopFrame() {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( By.id("framelive")));
    }

    public void waitForPrestaShop() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loadingMessage")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("framelive")));
    }

   

    // test 1

    @Test (priority = 1)
    public void Test1_VerifyHomePage() {

        try {
        	switchToPrestaShopFrame();
        	WebElement logo = wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector("main") ) );
        	AssertJUnit.assertNotSame(logo.isDisplayed(),"PrestaShop logo is not displayed");
            System.out.println("Test_1 PASS - Home page displayed successfully.");

        } 
        finally {      
            takeScreenshot("01_Home_VerifyHomePage");
            driver.switchTo().defaultContent();
        }
    }

    // test2

    @Test (priority = 2)
    public void Test2_ClickClothes() {

        try {
            switchToPrestaShopFrame();
            WebElement clothes = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clothes"))); 
            clothes.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            System.out.println("Test_2 PASS - Clothes clicked.");

        } 
        finally {
            takeScreenshot("02_Home_ClickClothes");
            driver.switchTo().defaultContent();
        }
    }

    // test 3
    @Test (priority = 3)
    public void Test3_ClickWomen() {

        try {

            switchToPrestaShopFrame();
            // First click Clothes
            WebElement clothes = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clothes"))); 
            clothes.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            // Click Women
            WebElement women = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Women")));
            women.click();
            System.out.println("Test_3 PASS - Women category clicked.");            
        } 
        finally {
            takeScreenshot("03_Home_ClickWomen");
            driver.switchTo().defaultContent();
        }
    }

    // test 4

    @Test (priority = 4)
    public void Test4_ClickArt() {

        try {

            switchToPrestaShopFrame();
            WebElement art =wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Art")));
            art.click();
            wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("body")));
            System.out.println("Test_4 PASS - Art category clicked.");

        } 
        finally {
            takeScreenshot("04_Home_ClickArt");
            driver.switchTo().defaultContent();
        }
    }

    // test5

    @Test (priority = 5)
    public void Test5_SearchProduct() {

        try {

            switchToPrestaShopFrame();
            WebElement searchBox =wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector("input[name='s']")));
            searchBox.clear();
            searchBox.sendKeys("shirt");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));
            System.out.println("Test_5 PASS - Product search executed.");

        } 
        finally {

            takeScreenshot("05_Home_SearchProduct");
            driver.switchTo().defaultContent();
        }
    }

 // test6

    @Test (priority = 6)
    public void Test6_OpenContactUs() {

        try {
            switchToPrestaShopFrame();
            WebElement contact =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(normalize-space(),'Contact us')]")));
            contact.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form")));
            AssertJUnit.assertNotSame(driver.getPageSource().toLowerCase().contains("contact"),"Contact page not displayed");
            System.out.println("Test 6- Contact Us page opened.");

        } finally {
            takeScreenshot("06_Home_OpenContactUs");
            driver.switchTo().defaultContent();
        }
    }
    // test7
    @Test (priority = 7)
    public void Test7_AddProductToCart() {

        try {

            switchToPrestaShopFrame();
            WebElement product = wait.until( ExpectedConditions.elementToBeClickable( By.cssSelector( "a[title='Hummingbird printed t-shirt']" ) ) ); 
         // Click product 
         product.click(); 
         System.out.println("Hummingbird printed t-shirt opened."); 
         // Wait for Add to cart button  & Add to cart 
         WebElement addToCart = wait.until( ExpectedConditions.elementToBeClickable( By.cssSelector( "button.add-to-cart")));       
         addToCart.click(); 
         System.out.println("Add to cart button clicked."); 
         // Wait for cart confirmation modal 
         WebElement confirmation = wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector( "#myModalLabel" ) ) );
         AssertJUnit.assertNotSame( confirmation.isDisplayed(), "Product was not added to cart." );
         System.out.println("Test_7 PASS - Product added to cart.");
        } 
        finally {

            takeScreenshot("07_Home_AddProductToCart");
            driver.switchTo().defaultContent();
        }
    }

    // test8

    @Test (priority = 8)
    public void Test8_OpenCart() {
        try {
            switchToPrestaShopFrame();
            WebElement cart = wait.until( ExpectedConditions.elementToBeClickable( By.cssSelector("a.cart-preview")));
            cart.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));
            AssertJUnit.assertNotSame(driver.getPageSource().toLowerCase().contains("cart"),"Cart page not displayed");
            System.out.println("Test_8 PASS - Cart page opened.");

        } finally {
            takeScreenshot("08_Home_OpenCart");
            driver.switchTo().defaultContent();
        }
    }
    // Screenshot option

    public void takeScreenshot(String testName) {

        File source =
            ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        File destination =new File("screenshots/"+ testName+ ".png");
        try {
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved: "+ destination.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("Home_All tests completed.");
    }
}