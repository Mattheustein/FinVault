package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver=driver;
    }
    //Locators
    private By emailField = By.id("input-email");
    private By passwordField = By.id("input-password");
    private By loginButton = By.cssSelector("input[value='Login']");
    private By forgottenPasswordLink = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Continue");
    private By errorAlert = By.cssSelector(".alert-danger");

    private static final String LOGIN_URL = "https://ecommerce-playground.lambdatest.io/index.php?route=account/login";

    //Actions
    public void navigateTo(){
        driver.get(LOGIN_URL);
    }
    public void insertEmail(String email){
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }
    public void insertPassword(String password){
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickOnLoginButton(){
        driver.findElement(loginButton).click();
    }
    public void clickOnForgottenPasswordLink(){
        driver.findElement(forgottenPasswordLink).click();
    }
    public void clickOnRegisterLink(){
        driver.findElement(registerLink).click();
    }
    public String getErrorMessage(){
        return driver.findElement(errorAlert).getText();
    }
    public boolean isErrorDisplayed(){
        return !driver.findElements(errorAlert).isEmpty();
    }
    public String getPasswordFieldType(){
        return driver.findElement(passwordField).getAttribute("type");
    }
    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }
}
