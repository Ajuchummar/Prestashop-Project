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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Addcartoutside {

    WebDriver driver;
    WebDriverWait wait;

    String url = "https://demo.prestashop.com/#/en/front";

    @BeforeClass
    public void setup() {

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // Open PrestaShop without login
        driver.get(url);
        // Switch to PrestaShop iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt( By.id("framelive")));
        System.out.println("PrestaShop home page opened successfully.");
    }

    // TEST 1 - Verify All Featured Products is displayed
    @Test(priority = 1)
    public void verifyAllFeaturedProducts() throws IOException {

        WebElement featuredProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(normalize-space(),"+ "'All featured products')]")));

        AssertJUnit.assertNotSame(featuredProducts.isDisplayed(),"All featured products is not displayed.");
        System.out.println("All featured products is displayed.");
        takeScreenshot("01_All_Featured_Products");
    }

    // TEST 2 - Click All Featured Products
    @Test(priority = 2, dependsOnMethods = "verifyAllFeaturedProducts")
    public void clickAllFeaturedProducts() throws IOException {

        WebElement featuredProducts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(normalize-space()," + "'All featured products')]")));
        featuredProducts.click();
        
        System.out.println("Clicked All featured products.");
        System.out.println("---------------------------------------------------------");

        // Wait for Products page
       wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("Home"),
                ExpectedConditions.urlContains("Showing"),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"products\"]"))));

        takeScreenshot("02_Featured_Products_Page");
    }

    // TEST 3 - Search for The best is yet to come' Framed poster

    @Test(priority = 3, dependsOnMethods = "clickAllFeaturedProducts")
    public void searchFramedPoster() throws IOException {

        String productName ="Mug The best is yet to come";

        // Search for product text on the current page
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[contains(normalize-space(),"+ "'" + productName + "')]")));
        AssertJUnit.assertNotSame(product.isDisplayed(),"Product was not found.'" + productName );
        System.out.println("Product found: " + productName);

        // Highlight product before screenshot
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",product);
        takeScreenshot("03_Framed_Poster_Found");
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
}



//Addcartoutside 
    