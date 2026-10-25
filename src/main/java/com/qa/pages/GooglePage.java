
package com.qa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class GooglePage {

    private WebDriver driver;

    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Open Google")
    public void openGoogle() {
        driver.get("https://www.google.com");
    }

    @Step("Search for '{0}'")
    public void search(String keyword) {
        driver.findElement(By.name("q"))
              .sendKeys(keyword, Keys.ENTER);
    }

    @Step("Read the current URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
