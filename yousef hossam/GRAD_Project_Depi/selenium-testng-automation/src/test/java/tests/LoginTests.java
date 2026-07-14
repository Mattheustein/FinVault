package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    LoginPage loginPage;

    @BeforeMethod
    public void goToLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.navigateTo();
    }

    // LG-001: Valid Login with Correct Credentials
    @Test(description = "LG-001: Valid Login with Correct Credentials")
    public void testValidLoginWithCorrectCredentials() {
        loginPage.insertEmail("user@example.com");
        loginPage.insertPassword("Test@1234");
        loginPage.clickOnLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/account"),
            "Expected to be on My Account page");
        Assert.assertTrue(driver.getPageSource().contains("My Account"),
            "Expected welcome message on page");
    }

    // LG-002: Login with Incorrect Password
    @Test(description = "LG-002: Login with Incorrect Password")
    public void testLoginWithIncorrectPassword() {
        loginPage.insertEmail("user@example.com");
        loginPage.insertPassword("WrongPass999");
        loginPage.clickOnLoginButton();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Warning: No match for E-Mail Address and/or Password."),
            "Unexpected error message: " + loginPage.getErrorMessage());
    }

    // LG-003: Login with Unregistered Email
    @Test(description = "LG-003: Login with Unregistered Email")
    public void testLoginWithUnregisteredEmail() {
        loginPage.insertEmail("notregistered@example.com");
        loginPage.insertPassword("Test@1234");
        loginPage.clickOnLoginButton();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Warning: No match for E-Mail Address and/or Password."),
            "Unexpected error message: " + loginPage.getErrorMessage());
    }

    // LG-004: Login with Empty Fields
    @Test(description = "LG-004: Login with Empty Fields")
    public void testLoginWithEmptyFields() {
        loginPage.insertEmail("");
        loginPage.insertPassword("");
        loginPage.clickOnLoginButton();

        boolean hasError = loginPage.isErrorDisplayed() || driver.getPageSource().contains("danger");
        Assert.assertTrue(hasError, "Expected a validation error for empty fields");
    }

    // LG-005: Login with Invalid Email Format
    @Test(description = "LG-005: Login with Invalid Email Format")
    public void testLoginWithInvalidEmailFormat() {
        loginPage.insertEmail("notanemail");
        loginPage.insertPassword("Test@1234");
        loginPage.clickOnLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/login"),
            "Expected to stay on login page due to email format error");
    }

    // LG-006: Forgotten Password Link Navigation
    @Test(description = "LG-006: Forgotten Password Link Navigation")
    public void testForgottenPasswordLinkNavigation() {
        loginPage.clickOnForgottenPasswordLink();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/forgotten"),
            "Expected to be on Forgotten Password page");
    }

    // LG-007: Password Field Masking
    @Test(description = "LG-007: Password Field Masking")
    public void testPasswordFieldMasking() {
        loginPage.insertPassword("Test@1234");

        Assert.assertEquals(loginPage.getPasswordFieldType(), "password", "Password field type mismatch");
    }

    // LG-008: Register Link Navigation from Login Page
    @Test(description = "LG-008: Register Link Navigation from Login Page")
    public void testRegisterLinkNavigationFromLoginPage() {
        loginPage.clickOnRegisterLink();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/register"),
            "Expected to be on Register page");
    }
}
