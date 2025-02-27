package org.example.core;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public class BaseTest {
    protected Page page;

    @BeforeTest
    public void setup() {
        page = PlaywrightFactory.createPage();
    }

    @AfterTest
    public void teardown() {
        if (page != null) page.context().browser().close();
    }
}
