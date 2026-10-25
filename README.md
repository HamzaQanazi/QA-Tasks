# QA Tasks

Selenium 4 + TestNG UI test automation with Allure reporting, using the Page Object pattern.

## Tasks covered

**Google search** (`GoogleSearchTest`)
- Search Google for "twitch" and verify the results page URL contains `q=twitch`.

**Rainbow login** (`LoginTest`)
- Empty password: submitting with an empty password keeps the user on the password step.
- Invalid password: a valid email with a wrong password shows the "incorrect username or password" error.
- Valid login: a valid email and password take the user past the login page.

Every test runs once per browser (Chrome and Firefox) from `testng.xml`.

## Tech stack

| Tool | Version |
|------|---------|
| Java | 17+ |
| Maven | 3.9+ |
| Selenium WebDriver | 4.38.0 |
| TestNG | 7.11.0 |
| Allure TestNG / Allure Maven plugin | 3.0.0 / 3.1.0 |
| AspectJ weaver (powers Allure `@Step`) | 1.9.26 |

## Project structure

```
QA Tasks
├── pom.xml                      Maven build: dependencies, surefire + Allure config
├── testng.xml                   Suite: Chrome and Firefox <test> blocks
└── src
    ├── main/java/com/qa
    │   ├── config/ConfigReader.java     Reads config.properties from the classpath
    │   └── pages/                       Page objects (@Step methods)
    │       ├── GooglePage.java
    │       └── LoginPage.java
    └── test
        ├── java/com/qa
        │   ├── listeners/AllureListener.java   Attaches a screenshot on test failure
        │   └── tests/
        │       ├── BaseTest.java               Starts/quits the browser per test
        │       ├── GoogleSearchTest.java
        │       └── LoginTest.java
        └── resources
            ├── config.example.properties       Template (committed)
            └── config.properties               Your real credentials (git-ignored)
```

## Setup

1. Install JDK 17+, Maven, Node.js (Allure 3 needs it) and the Chrome and Firefox browsers.
2. Create your local config from the template:

   ```bash
   cp src/test/resources/config.example.properties src/test/resources/config.properties
   ```

3. Edit `src/test/resources/config.properties`:

   ```properties
   rainbow.email=your-email@example.com
   rainbow.password=your-password
   rainbow.url=https://web.openrainbow.net/rb/2.179.17/login
   ```

   `config.properties` is git-ignored, so your credentials are never pushed.

## Run the tests

All tests, in both browsers:

```bash
mvn clean test
```

One browser only: open `testng.xml` and remove (or comment out) the `<test>` block for the browser you don't want. The `browser` parameter accepts `chrome` or `firefox`.

## Allure report

Build and open the report in your browser:

```bash
mvn allure:serve
```

Or generate it as files:

```bash
mvn allure:report
```

The report is written to `target/site/allure-maven-plugin/index.html`. Raw results are in `target/allure-results`. Run the tests first, since the report is built from those results. The first run downloads the Allure CLI into `.allure/`.

## What the report contains

- Pass, fail and skip status for every test, grouped by Epic, Feature and Story, with severity.
- Error messages and stack traces for failed tests.
- The steps each test performed (open page, search, enter username, click, and so on). The password step is recorded without its value.
- A screenshot of the browser, attached automatically when a test fails.
