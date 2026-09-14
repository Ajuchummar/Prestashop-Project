
package Final_Project;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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


public class ProductFilterTest{

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
        // Open PrestaShop
        driver.get(url);

        // Switch to PrestaShop iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("framelive")));
        System.out.println("PrestaShop home page opened successfully.");
    }

    // TEST 1 - Verify All Featured Products is displayed in home page
    @Test(priority = 1)
    public void verifyAllFeaturedProducts() throws IOException {
        WebElement featuredProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(normalize-space(),"+ "'All featured products')]")));
        AssertJUnit.assertNotSame(featuredProducts.isDisplayed(),"All featured products is not displayed.");
        System.out.println("Filter_All featured products is displayed.");
        takeScreenshot("01_Filter_All_Featured_Products");
    }

    // TEST 2 - Click All Featured Products in home page
    @Test(priority = 2, dependsOnMethods = "verifyAllFeaturedProducts")
    public void clickAllFeaturedProducts() throws IOException {

        WebElement featuredProducts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(normalize-space()," + "'All featured products')]")));
        featuredProducts.click();
        
        System.out.println("_Filter_Clicked All featured products_");
        System.out.println("---------------------------------------------------------");
        // Wait for Products page to be displayed
       wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("Home"),ExpectedConditions.urlContains("Showing"),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"products\"]"))));
       
        takeScreenshot("02_Filter_Featured_Products_Page");
        System.out.println("---------------------------------------------------------");
    }
    
    // TEST 3 - Click Availability
    @Test(priority = 3, dependsOnMethods = "clickAllFeaturedProducts")
    public void Availability() throws IOException {

        // Locate Availability filter
        WebElement availability = wait.until(ExpectedConditions.elementToBeClickable( By.xpath("//*[contains(normalize-space(),"+ "'Availability')]")));
        availability.click();
        System.out.println("Availability button clicked successfully.");
        takeScreenshot("03_Filter_Availability_Opened");
        }
       
    // TEST 4 - Select Availability 
    @Test(priority = 4, dependsOnMethods = "Availability")
    public void selectAvailability() throws IOException {       
    	
    WebElement availability = wait.until(ExpectedConditions.elementToBeClickable(By.xpath( "//*[normalize-space()='Availability']")));
    js.executeScript("arguments[0].scrollIntoView({block:'center'});",availability );
    availability.click();
    System.out.println("Availability clicked successfully.");
    AssertJUnit.assertNotSame( availability.isDisplayed(),"Availability filter is not displayed.");    
    takeScreenshot("04_Filter_Availability_Clicked");    
    }
    
    // TEST 5 - Select In Stock 
    @Test(priority = 5, dependsOnMethods = "selectAvailability")
    public void instock() throws IOException {      	
        WebElement Instock =wait.until
        		(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div[1]/div[2]/div/div/div/section[1]/div/ul/li/div/div/label/a")));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",Instock );
        Instock.click();
        AssertJUnit.assertNotSame( Instock.isDisplayed(),"Instock filter is not selected.");
        takeScreenshot("05_Filter_instock_Clicked");    

    }
 // TEST 6 - Click price
    @Test(priority = 6, dependsOnMethods = "instock")
    public void Categories() throws IOException {
        clearall();
        // Locate price filter
        
        WebElement categories = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search-filters\"]/div/section[4]/button")));

        //js.executeScript("arguments[0].scrollIntoView({block:'center'});",categories);
        categories.click();
        System.out.println("Categories clicked successfully.");
        takeScreenshot("06_Filter_Categories_Opened");       
        
        }

    
  /*  // TEST  7 - Select price
    @Test(priority = 7, dependsOnMethods = "Categories")
    public void selectCategories() throws IOException {       
    	
    WebElement Categories = wait.until(ExpectedConditions.elementToBeClickable(By.xpath( "//*[normalize-space()='Categories']")));
    js.executeScript("arguments[0].scrollIntoView({block:'center'});",Categories );
    Categories.click();
    System.out.println("Categories clicked successfully.");
    Assert.assertTrue( Categories.isDisplayed(),"Categories filter is not displayed.");    
    takeScreenshot("07_Filter_Categories_Clicked");    
    }
    
    // TEST 8- Select In Stock 
    @Test(priority = 8, dependsOnMethods = "selectCategories")
    public void art () throws IOException {      	
        WebElement Art =wait.until
        		(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div[1]/div[2]/div/div/div/section[4]/div/ul/li[2]/div/div")));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",Art );
        Art.click();
        Assert.assertTrue( Art.isDisplayed(),"Art filter is not selected.");
        takeScreenshot("08_Filter_Art_Clicked");     	
    }*/
 
    
    // TEST  - Clear all

    public void clearall() throws IOException {      	
        WebElement clearall1 =wait.until
        		(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div[1]/div[2]/div/div/div[1]/button")));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});",clearall1 );
        clearall1.click();

         	
    }
    
    
    
    
    
    
    // SCREENSHOT METHOD
    public void takeScreenshot(String fileName) throws IOException {

        TakesScreenshot ts =(TakesScreenshot) driver;
        File source =ts.getScreenshotAs(OutputType.FILE);
        File destination =new File("/screenshots/"+ fileName + ".png");
        FileUtils.copyFile(source,destination);
        System.out.println("Screenshot saved: "+ destination.getAbsolutePath());
    }
    @AfterClass
    public void allsuccessrun() {
        System.out.println("Signin_AllTests completed.");

    }

}

