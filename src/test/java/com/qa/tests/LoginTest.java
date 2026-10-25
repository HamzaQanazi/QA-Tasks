package com.qa.tests;

import com.qa.config.ConfigReader;
import com.qa.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Rainbow Login")
@Feature("Authentication")
public class LoginTest extends BaseTest {

    private LoginPage rainbow;

    @BeforeMethod
    public void openLoginPage() {
        rainbow = new LoginPage(driver);
        rainbow.openRainbow();
    }

    @Test(
        testName = "Rainbow empty password",
        description = "Leave the password empty and verify login is not possible"
    )
    @Story("Login with empty password")
    @Severity(SeverityLevel.NORMAL)
    @Description("Enter a valid email, submit an empty password and verify the user stays on the password step")
    public void testEmptyPassword() {
        rainbow.enterUsername(ConfigReader.get("rainbow.email"));
        rainbow.clickNext();

        // submit with an empty password
        rainbow.clickLogin();

        // outcome check: the password field must stay on screen for the full 3s
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id("authPwd")));
            Assert.fail("Left the password step with an empty password");
        } catch (TimeoutException expected) {
            // good: still on the password step
        }
    }

    @Test(
        testName = "Rainbow invalid password",
        description = "Log in with a valid email and a wrong password and verify the error message is shown"
    )
    @Story("Login with invalid password")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Enter a valid email and a wrong password and verify the 'incorrect username or password' error is shown")
    public void testInvalidPassword() {
        rainbow.enterUsername(ConfigReader.get("rainbow.email"));
        rainbow.clickNext();
        rainbow.enterPassword("WrongPassword123!");
        rainbow.clickLogin();

        String error = rainbow.getErrorMessage();
        Assert.assertTrue(
            error.toLowerCase().contains("incorrect username or password"),
            "Unexpected error message: " + error);
    }

    @Test(
        testName = "Rainbow valid login",
        description = "Log in with a valid email and password and verify the user leaves the login page"
    )
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Enter a valid email and password and verify the user is taken past the login page")
    public void testValidLogin() {
        rainbow.enterUsername(ConfigReader.get("rainbow.email"));
        rainbow.clickNext();
        rainbow.enterPassword(ConfigReader.get("rainbow.password"));
        rainbow.clickLogin();

        Assert.assertTrue(
            rainbow.isLoginSuccessful(),
            "Login failed: still on the login page");
    }
}
