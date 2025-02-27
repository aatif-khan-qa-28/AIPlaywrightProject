package org.example.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.example.utils.ConfigLoader;

public class PlaywrightFactory {
    public static Page createPage(){
        Playwright playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        Browser browser = browserType.launch(new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(ConfigLoader.getProperty("HEADLESS"))));
        return browser.newPage();
    }
}
