package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage {
    WebDriver driver;
    public RegisterPage(WebDriver driver) {
        this.driver=driver;
    }
    //Locators
    private By firstNameField = By.id("input-firstname");
    private By lastNameField = By.id("input-lastname");
    private By emailField = By.id("input-email");
    private By telephoneField = By.id("input-telephone");
    private By passwordField = By.id("input-password");
    private By confirmPasswordField = By.id("input-confirm");
    private By newsletterYes = By.cssSelector("input[name='newsletter'][value='1']");
    private By newsletterNo = By.cssSelector("input[name='newsletter'][value='0']");
    private By privacyPolicyCheck = By.name("agree");
    private By continueButton = By.cssSelector("input[value='Continue']");
    private By loginLink = By.linkText("login page");
    private By errorAlert = By.cssSelector(".alert-danger");
    private By firstNameError = By.cssSelector("#input-firstname ~ .text-danger");
    private By lastNameError = By.cssSelector("#input-lastname ~ .text-danger");
    private By emailError = By.cssSelector("#input-email ~ .text-danger");
    private By telephoneError = By.cssSelector("#input-telephone ~ .text-danger");
    private By passwordError = By.cssSelector("#input-password ~ .text-danger");

    private static final String REGISTER_URL = "https://ecommerce-playground.lambdatest.io/index.php?route=account/register";

    //Actions
    public void navigateTo(){
        driver.get(REGISTER_URL);
    }
    public void enterFirstName(String firstName){
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }
    public void enterLastName(String lastName){
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }
    public void enterEmail(String email){
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }
    public void enterTelephone(String telephone){
        driver.findElement(telephoneField).clear();
        driver.findElement(telephoneField).sendKeys(telephone);
    }
    public void enterPassword(String password){
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }
    public void enterConfirmPassword(String confirmPassword){
        driver.findElement(confirmPasswordField).clear();
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
    }
    public void selectNewsletter(String value){
        if (value.equalsIgnoreCase("Yes")) {
            driver.findElement(newsletterYes).click();
        } else {
            driver.findElement(newsletterNo).click();
        }
    }
    public void acceptPrivacyPolicy(){
        WebElement checkbox = driver.findElement(privacyPolicyCheck);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }
    public void clickOnContinueButton(){
        driver.findElement(continueButton).click();
    }
    public void clickOnLoginLink(){
        driver.findElement(loginLink).click();
    }
    public String getErrorMessage(){
        return driver.findElement(errorAlert).getText();
    }
    public boolean isErrorDisplayed(){
        return !driver.findElements(errorAlert).isEmpty();
    }
    public boolean isFieldErrorDisplayed(String field){
        By locator;
        switch (field.toLowerCase()) {
            case "firstname": locator = firstNameError; break;
            case "lastname":  locator = lastNameError;  break;
            case "email":     locator = emailError;     break;
            case "telephone": locator = telephoneError; break;
            case "password":  locator = passwordError;  break;
            default: return false;
        }
        return !driver.findElements(locator).isEmpty();
    }
    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }
}
