package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterTests extends BaseTest {

    RegisterPage registerPage;

    @BeforeMethod
    public void goToRegisterPage() {
        registerPage = new RegisterPage(driver);
        registerPage.navigateTo();
    }

    // RG-001: Successful Registration with Valid Data
    @Test(description = "RG-001: Successful Registration with Valid Data")
    public void testSuccessfulRegistrationWithValidData() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe_auto@example.com");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@1234");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/success"),
            "Expected to be on account success page");
    }

    // RG-002: Registration with Already Registered Email
    @Test(description = "RG-002: Registration with Already Registered Email")
    public void testRegistrationWithAlreadyRegisteredEmail() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("user@example.com");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@1234");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        Assert.assertTrue(registerPage.isErrorDisplayed(), "Error message not displayed");
        Assert.assertTrue(registerPage.getErrorMessage().contains("Warning: E-Mail Address is already registered!"),
            "Unexpected error message: " + registerPage.getErrorMessage());
    }

    // RG-003: Registration with Empty Required Fields
    @Test(description = "RG-003: Registration with Empty Required Fields")
    public void testRegistrationWithEmptyRequiredFields() {
        registerPage.clickOnContinueButton();

        boolean hasError = registerPage.isErrorDisplayed()
            || registerPage.isFieldErrorDisplayed("firstname")
            || registerPage.isFieldErrorDisplayed("lastname");
        Assert.assertTrue(hasError, "Expected validation error for empty form");
    }

    // RG-004: Password and Confirm Password Mismatch
    @Test(description = "RG-004: Password and Confirm Password Mismatch")
    public void testPasswordAndConfirmPasswordMismatch() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe2@example.com");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@5678");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        Assert.assertTrue(registerPage.isErrorDisplayed(), "Error message not displayed");
        Assert.assertTrue(registerPage.getErrorMessage().contains("Password confirmation does not match password!"),
            "Unexpected error message: " + registerPage.getErrorMessage());
    }

    // RG-005: Registration without Accepting Privacy Policy
    @Test(description = "RG-005: Registration without Accepting Privacy Policy")
    public void testRegistrationWithoutAcceptingPrivacyPolicy() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe3@example.com");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@1234");
        // Do NOT accept the privacy policy
        registerPage.clickOnContinueButton();

        Assert.assertTrue(registerPage.isErrorDisplayed(), "Error message not displayed");
        Assert.assertTrue(registerPage.getErrorMessage().contains("Warning: You must agree to the Privacy Policy!"),
            "Unexpected error message: " + registerPage.getErrorMessage());
    }

    // RG-006: Registration with Invalid Email Format
    @Test(description = "RG-006: Registration with Invalid Email Format")
    public void testRegistrationWithInvalidEmailFormat() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("bademail");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@1234");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/register"),
            "Expected to stay on Register page due to invalid email");
    }

    // RG-007: Registration with Invalid Phone Number
    @Test(description = "RG-007: Registration with Invalid Phone Number")
    public void testRegistrationWithInvalidPhoneNumber() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe4@example.com");
        registerPage.enterTelephone("abc123");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@1234");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        boolean hasError = registerPage.isErrorDisplayed() || registerPage.isFieldErrorDisplayed("telephone");
        Assert.assertTrue(hasError, "Expected telephone validation error");
    }

    // RG-008: Password Minimum Length Validation
    @Test(description = "RG-008: Password Minimum Length Validation")
    public void testPasswordMinimumLengthValidation() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe5@example.com");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("123");
        registerPage.enterConfirmPassword("123");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        Assert.assertTrue(registerPage.isErrorDisplayed(), "Error message not displayed");
        Assert.assertTrue(registerPage.getErrorMessage().contains("Password must be between 4 and 20 characters!"),
            "Unexpected error message: " + registerPage.getErrorMessage());
    }

    // RG-009: Newsletter Subscription Enabled
    @Test(description = "RG-009: Newsletter Subscription Enabled")
    public void testNewsletterSubscriptionEnabled() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe_news@example.com");
        registerPage.enterTelephone("01012345678");
        registerPage.enterPassword("Test@1234");
        registerPage.enterConfirmPassword("Test@1234");
        registerPage.selectNewsletter("Yes");
        registerPage.acceptPrivacyPolicy();
        registerPage.clickOnContinueButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/success"),
            "Expected to be on account success page");
    }

    // RG-010: Login Link on Registration Page
    @Test(description = "RG-010: Login Link on Registration Page")
    public void testLoginLinkOnRegistrationPage() {
        registerPage.clickOnLoginLink();

        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/login"),
            "Expected to be on Login page");
    }
}
