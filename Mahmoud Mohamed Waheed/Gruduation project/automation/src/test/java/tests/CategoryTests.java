package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class CategoryTests {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // تشغيل صامت لتوفير الوقت والـ resources
        options.addArguments("--window-size=1920,1080");

        // تفعيل تسجيل أخطاء الـ Javascript Console
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.SEVERE);
        options.setCapability("goog:loggingPrefs", logPrefs);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test_tc_cat_001_visibility() {
        homePage.navigateToHome();
        WebElement section = driver.findElement(homePage.categorySection);
        homePage.scrollToElement(section);
        Assert.assertTrue(section.isDisplayed(), "Category Section is NOT visible on homepage!");
    }

    @Test
    public void test_tc_cat_002_all_items_displayed() {
        homePage.navigateToHome();
        List<String> expectedCategories = Arrays.asList(
                "Components", "Cameras", "Phone, Tablets & iPod", "Software", "MP3 Players",
                "Laptops & Notebooks", "Desktops and Monitors", "Printers & Scanners",
                "Mice and Trackballs", "Fashion and Accessories", "Beauty and Saloon",
                "Autoparts and Accessories", "Washing machine", "Gaming consoles",
                "Air conditioner", "Web Cameras"
        );

        List<String> actualCategories = homePage.getCategoryTexts();
        Assert.assertEquals(actualCategories.size(), 16, "Expected 16 categories, found: " + actualCategories.size());

        for (String expected : expectedCategories) {
            boolean found = actualCategories.stream()
                    .anyMatch(actual -> actual.toLowerCase().replace(",", "")
                            .contains(expected.toLowerCase().replace(",", "").substring(0, 4)));
            Assert.assertTrue(found, "Category not found in UI list: " + expected);
        }
    }

    @Test
    public void test_tc_cat_003_no_js_errors() {
        homePage.navigateToHome();
        WebElement section = driver.findElement(homePage.categorySection);
        homePage.scrollToElement(section);

        // سحب الـ Console Logs للتأكد من عدم وجود أخطاء SEVERE
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            Assert.assertNotEquals(entry.getLevel(), Level.SEVERE, "JS Error detected: " + entry.getMessage());
        }
    }

    @Test
    public void test_tc_cat_004_heading_text() {
        homePage.navigateToHome();
        WebElement sectionHeader = driver.findElement(By.cssSelector("#mz-component-1626147655 h3, #mz-component-1626147655 .title"));
        String headingText = sectionHeader.getText().trim();
        Assert.assertTrue(headingText.toLowerCase().contains("top categories") || headingText.toLowerCase().contains("shop by category"),
                "Unexpected Section Heading Text: " + headingText);
    }

    @Test
    public void test_tc_cat_005_images_render() {
        homePage.navigateToHome();
        List<WebElement> images = driver.findElements(homePage.categoryImages);
        Assert.assertFalse(images.isEmpty(), "No category images found!");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (WebElement img : images) {
            String src = img.getAttribute("src");
            Assert.assertNotNull(src, "Image src attribute is null.");
            Assert.assertFalse(src.isEmpty(), "Image src is empty.");

            // التحقق من اكتمال تحميل الصورة فعلياً
            boolean isLoaded = (Boolean) js.executeScript(
                    "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", img);
            Assert.assertTrue(isLoaded, "Image failed to load: " + src);
        }
    }

    @DataProvider(name = "navigationData")
    public Object[][] getNavigationData() {
        return new Object[][]{
                {"Components", "path=25"},
                {"Cameras", "path=33"},
                {"Phone, Tablets & iPod", "path=57"},
                {"Laptops & Notebooks", "path=18"},
                {"Software", "path=17"},
                {"MP3 Players", "path=34"},
                {"Web Cameras", "path=32"},
                {"Desktops and Monitors", "path=28"},
                {"Printers & Scanners", "path=30"},
                {"Mice and Trackballs", "path=29"}
        };
    }

    @Test(dataProvider = "navigationData")
    public void test_tc_cat_006_to_015_navigation(String categoryName, String expectedPath) {
        homePage.navigateToHome();
        WebElement link = driver.findElement(By.xpath("//#mz-component-1626147655//a[contains(text(), '" + categoryName + "')]"));
        homePage.scrollToElement(link);
        link.click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains(expectedPath));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedPath), "Expected url path '" + expectedPath + "' but got '" + currentUrl + "'");
    }

    @Test
    public void test_tc_cat_016_placeholder_links() {
        homePage.navigateToHome();
        String initialUrl = driver.getCurrentUrl();
        List<String> placeholders = Arrays.asList(
                "Fashion and Accessories", "Beauty and Saloon", "Autoparts and Accessories",
                "Washing machine", "Gaming consoles", "Air conditioner"
        );

        for (String placeholder : placeholders) {
            List<WebElement> elements = driver.findElements(By.xpath("//#mz-component-1626147655//a[contains(text(), '" + placeholder + "')]"));
            if (!elements.isEmpty()) {
                WebElement link = elements.get(0);
                String href = link.getAttribute("href");
                // الروابط اللي ملهاش وجهة حقيقية
                Assert.assertTrue(href == null || href.isEmpty() || href.equals("#") || href.contains("<>"),
                        "Placeholder '" + placeholder + "' has an active href attribute: " + href);

                link.click();
                Assert.assertEquals(driver.getCurrentUrl(), initialUrl, "Clicking placeholder '" + placeholder + "' redirected the browser!");
            }
        }
    }

    @Test
    public void test_tc_cat_017_opens_in_same_tab() {
        homePage.navigateToHome();
        WebElement link = driver.findElement(By.xpath("//#mz-component-1626147655//a[contains(text(), 'Cameras')]"));
        String target = link.getAttribute("target");
        Assert.assertNotEquals(target, "_blank", "Link is configured to open in a new tab!");
    }

    @Test
    public void test_tc_cat_018_back_button() {
        homePage.navigateToHome();
        String homeUrl = driver.getCurrentUrl();

        WebElement link = driver.findElement(By.xpath("//#mz-component-1626147655//a[contains(text(), 'Cameras')]"));
        link.click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("path=33"));

        driver.navigate().back();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlToBe(homeUrl));
        Assert.assertEquals(driver.getCurrentUrl(), homeUrl, "Did not return to correct homepage URL after browser back.");
    }

    @Test
    public void test_tc_cat_019_shop_by_category_dropdown_expands() {
        homePage.navigateToHome();
        WebElement dropdownBtn = driver.findElement(homePage.shopByCategoryBtn);
        dropdownBtn.click();

        WebElement menu = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(homePage.dropdownMenu));
        Assert.assertTrue(menu.isDisplayed(), "Shop by Category Dropdown did not expand on click!");
    }

    @Test
    public void test_tc_cat_020_shop_by_category_dropdown_closes_on_outside_click() {
        homePage.navigateToHome();
        WebElement dropdownBtn = driver.findElement(homePage.shopByCategoryBtn);
        dropdownBtn.click();

        // التأكد من ظهور القائمة
        WebElement menu = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(homePage.dropdownMenu));
        Assert.assertTrue(menu.isDisplayed());

        // الضغط خارج الـ Dropdown (مثلاً على الـ Body)
        driver.findElement(By.tagName("body")).click();

        // التأكد من إغلاق القائمة
        boolean isClosed = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOf(menu));
        Assert.assertTrue(isClosed, "Shop by Category Dropdown stayed open after clicking outside!");
    }
}