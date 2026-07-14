# selenium-testng-automation

Login & Register automated test suite for the LambdaTest E-Commerce Playground,
using plain Selenium + TestNG (plain page-object model, TestNG assertions and lifecycle).

## Structure

```
cucumber-automation/
├── pom.xml
└── src
    ├── main
    │   └── java/pages/
    │       ├── LoginPage.java
    │       └── RegisterPage.java
    └── test
        └── java/tests/
            ├── BaseTest.java     (driver setup/teardown, @BeforeMethod/@AfterMethod)
            ├── LoginTests.java   (8 test cases: LG-001–LG-008)
            └── RegisterTests.java (10 test cases: RG-001–RG-010)
```

## Test cases

All 18 manual test cases from the SAM1 Jira board are covered as individual
`@Test` methods — `LoginTests` covers LG-001 through LG-008, `RegisterTests`
covers RG-001 through RG-010, each named and commented with its Jira ID.

## Running

Right-click `LoginTests.java` or `RegisterTests.java` (or an individual
`@Test` method) in IntelliJ and choose **Run**. TestNG is on the classpath,
so IntelliJ will offer a TestNG run configuration automatically. Firefox
must be installed — Selenium 4.41's built-in Selenium Manager downloads
geckodriver automatically if it isn't already on your PATH.

To run everything via Maven: `mvn test`.
