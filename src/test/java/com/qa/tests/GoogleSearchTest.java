
package com.qa.tests;

import com.qa.pages.GooglePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

@Epic("Google Search")
@Feature("Search")
public class GoogleSearchTest extends BaseTest {

    @Test(
        testName = "Google search for Twitch",
        description = "Search Google for 'twitch' and verify the results page URL contains the keyword"
    )
    @Story("Search by keyword")
    @Severity(SeverityLevel.NORMAL)
    @Description("Open Google, search for 'twitch' and verify the results URL contains q=twitch")
    public void testGoogleSearch() {

        GooglePage google = new GooglePage(driver);

        // Open Google
        google.openGoogle();

        // Search for Twitch
        google.search("twitch");

        // Wait for the search results URL
        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(d ->
            google.getCurrentUrl().contains("/search?")
        );

        // Check the search keyword
        Assert.assertTrue(
            google.getCurrentUrl().contains("q=twitch"),
            "Search failed!"
        );
    }

}
