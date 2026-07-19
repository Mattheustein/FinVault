package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class test_1 {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testSearchiPhone() {
        driver.get("https://ecommerce-playground.lambdatest.io/");

        WebElement searchBox = driver.findElement(By.cssSelector("div#search input[name='search']"));
        searchBox.sendKeys("iPhone");
        searchBox.sendKeys(Keys.ENTER);

        WebElement searchResults = driver.findElement(By.cssSelector("div.product-layout"));
        Assert.assertTrue(searchResults.isDisplayed(), "Search results are not displayed!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}