package com.qa.pages;

import com.qa.config.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
public class LoginPage {
  

      private WebDriver driver;

    public  LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open the Rainbow login page")
    public void openRainbow() {
        driver.get(ConfigReader.get("rainbow.url"));
    }
@Step("Enter username '{0}'")
public void enterUsername(String username) {

    WebDriverWait wait = new WebDriverWait(
        driver, Duration.ofSeconds(20)
    );

    wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.id("username")
        )
    ).sendKeys(username);
}



@Step("Click Next")
public void clickNext() {
    driver.findElement(By.id("username")).sendKeys(org.openqa.selenium.Keys.ENTER);
}

    public void enterPassword(String pass) {
    // not @Step: Allure would write the password argument into allure-results
    Allure.step("Enter password");
    WebDriverWait wait = new WebDriverWait(
        driver, Duration.ofSeconds(20)
    );

    // retype until the value sticks (slow renders can wipe the field)
    wait.ignoring(org.openqa.selenium.StaleElementReferenceException.class)
        .until(d -> {
            WebElement e = d.findElement(By.id("authPwd"));
            if (!e.isDisplayed()) return false;
            if (!pass.equals(e.getAttribute("value"))) {
                e.clear();
                e.sendKeys(pass);
            }
            return pass.equals(e.getAttribute("value"));
        });
}
@Step("Click Login")
public void clickLogin() {

    // ponytail: Enter submits the form like a user; Connect button click proved unreliable
    new WebDriverWait(driver, Duration.ofSeconds(20))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("authPwd")))
        .sendKeys(org.openqa.selenium.Keys.ENTER);
}

    @Step("Read the login error message")
    public String getErrorMessage() {
        By error = By.xpath("//login-page//form//fieldset/div[3]/span");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // span may exist empty; wait until it has text
        wait.until(d -> !d.findElement(error).getText().trim().isEmpty());
        return driver.findElement(error).getText();
    }

    @Step("Check the user left the login page")
    public boolean isLoginSuccessful() {

    WebDriverWait wait = new WebDriverWait(
        driver, Duration.ofSeconds(20)
    );

    // success = we left the login page, whichever screen opens next (conversations, plan, ...)
    return wait.until(ExpectedConditions.and(
        ExpectedConditions.not(ExpectedConditions.urlContains("login")),
        ExpectedConditions.invisibilityOfElementLocated(By.id("authPwd"))
    ));
}
}
