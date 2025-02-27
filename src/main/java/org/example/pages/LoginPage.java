package org.example.pages;

import com.microsoft.playwright.Page;
import org.example.helpers.SelfHealingLocatorHelper;

public class LoginPage {

    private final SelfHealingLocatorHelper selfHealingLocatorHelper;

    // Initial locators (assumed ones - update based on your actual app)
    private static final String NAME_INPUT_LOCATOR = "input[data-qa='signup-name']";
    private static final String EMAIL_INPUT_LOCATOR = "input[data-qa='signup-password']";
    private static final String SIGNUP_BUTTON_LOCATOR = "button[data-qa='signup-button']";

    public LoginPage(Page page) {
        this.selfHealingLocatorHelper = new SelfHealingLocatorHelper(page);
    }

    public void enterEmail(String email) throws Exception {
        selfHealingLocatorHelper.fill("Email Input Field", EMAIL_INPUT_LOCATOR, email);
    }

    public void enterName(String name) throws Exception {
        selfHealingLocatorHelper.fill("Password Input Field", NAME_INPUT_LOCATOR, name);
    }
//    public void enterPassword(String password) throws Exception {
//        selfHealingLocatorHelper.fill("Password Input Field", PASSWORD_INPUT_LOCATOR, password);
//    }

    public void clicksignupbutton() throws Exception {
        selfHealingLocatorHelper.click("Signup  Button", SIGNUP_BUTTON_LOCATOR);
    }
}
