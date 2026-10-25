package com.qa.tests;

import com.qa.listeners.AllureListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners(AllureListener.class)
public class BaseTest {

    protected WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    @Parameters("browser")
    public void startBrowser(@Optional("firefox") String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {
            driver = new ChromeDriver();
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
