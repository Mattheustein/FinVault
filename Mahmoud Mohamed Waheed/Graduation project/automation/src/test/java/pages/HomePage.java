package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Locators
    public final By categorySection = By.id("mz-component-1626147655");
    public final By categoryLinks = By.cssSelector("#mz-component-1626147655 a");
    public final By categoryImages = By.cssSelector("#mz-component-1626147655 img");
    public final By shopByCategoryBtn = By.xpath("//a[contains(@class, 'icon-left') and contains(., 'Shop by Category')]");
    public final By dropdownMenu = By.cssSelector(".dropdown-menu.show");

    public void navigateToHome() {
        navigateTo("https://ecommerce-playground.lambdatest.io/");
    }

    public List<String> getCategoryTexts() {
        List<WebElement> elements = driver.findElements(categoryLinks);
        return elements.stream()
                .map(el -> el.getText().trim())
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }
}