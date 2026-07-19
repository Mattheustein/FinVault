package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class test_4 {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testClickDesktopsCategory() {
        driver.get("https://ecommerce-playground.lambdatest.io/");

        WebElement shopByCategoryBtn = driver.findElement(By.xpath("//a[contains(., 'Shop by Category')]"));
        shopByCategoryBtn.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement desktopsLink = driver.findElement(By.xpath("//span[contains(text(), 'Desktops')]"));
        desktopsLink.click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("desktop"), "URL does not contain 'desktop'!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}