package org.example.tests;

import com.microsoft.playwright.*;
import org.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;
import org.example.utils.ConfigLoader;

import java.nio.file.Paths;

public class LoginTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    public void loginTestWithSelfHealing() {
        try {
            page.navigate(ConfigLoader.getProperty("BASE_URL"));

            LoginPage loginPage = new LoginPage(page);

            loginPage.enterEmail("testus@example.com");
            loginPage.enterName("testpassword");
            loginPage.clicksignupbutton();

            // Add your actual assertion or validation step here (after successful login)
            System.out.println("Login successful - add assertions here.");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @AfterClass
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
