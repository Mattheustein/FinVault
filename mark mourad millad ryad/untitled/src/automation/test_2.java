package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class test_2 {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testClickCamerasCategory() {
        driver.get("https://ecommerce-playground.lambdatest.io/");

        // 1. الضغط أولاً على زرار "Shop by Category"
        WebElement shopByCategoryBtn = driver.findElement(By.xpath("//a[contains(., 'Shop by Category')]"));
        shopByCategoryBtn.click();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. دلوقتي نضغط على "Cameras" بعد ما ظهرت واستقرت
        WebElement camerasLink = driver.findElement(By.xpath("//span[contains(text(), 'Cameras')]"));
        camerasLink.click();

        // 3. التأكد من أن صفحة الكاميرات فتحت بنجاح
        WebElement pageTitle = driver.findElement(By.cssSelector("h1.title"));
        Assert.assertTrue(pageTitle.isDisplayed() && pageTitle.getText().contains("Cameras"), "Failed to navigate to Cameras page!");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();


        }
    }
}