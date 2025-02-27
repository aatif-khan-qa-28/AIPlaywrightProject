package org.example.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.ai.GeminiAgent;
import org.example.utils.GeminiResponseParser;
import org.example.helpers.LogManagerHelper; // Import your LogManagerHelper

import java.util.List;

public class SelfHealingLocatorHelper {

    private final Page page;

    public SelfHealingLocatorHelper(Page page) {
        this.page = page;
    }

    public Page page() {
        return page;
    }

    public void click(String elementName, String initialLocator) throws Exception {
        LogManagerHelper.info("Attempting to click: '" + elementName + "' using locator: " + initialLocator);
        if (!tryClick(elementName, initialLocator)) {
            LogManagerHelper.info("Click failed for " + elementName + ". Triggering self-healing via Gemini...");
            String healedLocator = healLocator(elementName, initialLocator);
            if (healedLocator != null && tryClick(elementName, healedLocator)) {
                return;
            }
            throw new Exception("Failed to click '" + elementName + "' even after self-healing.");
        }
    }

    public void fill(String elementName, String initialLocator, String text) throws Exception {
        LogManagerHelper.info("Attempting to fill: '" + elementName + "' using locator: " + initialLocator);
        if (!tryFill(elementName, initialLocator, text)) {
            LogManagerHelper.info("Fill failed for " + elementName + ". Triggering self-healing via Gemini...");
            String healedLocator = healLocator(elementName, initialLocator);
            if (healedLocator != null && tryFill(elementName, healedLocator, text)) {
                return;
            }
            throw new Exception("Failed to fill '" + elementName + "' even after self-healing.");
        }
    }

    private boolean tryClick(String elementName, String locator) {
        try {
            Locator element = page.locator(locator);
            if (element.isVisible() && element.isEnabled()) {
                LogManagerHelper.info("Clicking '" + elementName + "' using locator: " + locator);
                element.click();
                return true;
            }
        } catch (Exception e) {
            LogManagerHelper.error("Click failed for '" + elementName + "' with locator '" + locator + "'", e);
        }
        return false;
    }

    private boolean tryFill(String elementName, String locator, String text) {
        try {
            Locator element = page.locator(locator);
            if (element.isVisible() && element.isEnabled()) {
                LogManagerHelper.info("Filling '" + elementName + "' using locator: " + locator);
                element.fill(text);
                return true;
            }
        } catch (Exception e) {
            LogManagerHelper.error("Fill failed for '" + elementName + "' with locator '" + locator + "'", e);
        }
        return false;
    }

    private String healLocator(String elementName, String initialLocator) {
        try {
            String pageSource = page.content();

            String prompt = "The locator for '" + elementName + "' is broken. " +
                    "The initial locator was: '" + initialLocator + "'. " +
                    "Please suggest up to **5 valid CSS locators** for '" + elementName + "' based on the following page HTML. " +
                    "Return the CSS locators as a structured JSON array, like this:\n" +
                    "{ \"locators\": [\"locator1\", \"locator2\"] }\n\n" +
                    "Page HTML:\n" + pageSource;

            String response = GeminiAgent.callGemini(prompt);
            List<String> locators = GeminiResponseParser.parseLocatorSuggestions(response);

            LogManagerHelper.info("AI Suggested Locators for '" + elementName + "': " + locators);

            for (String locator : locators) {
                if (page.locator(locator).isVisible()) {
                    LogManagerHelper.info("Self-healing succeeded using locator: " + locator);
                    return locator;
                }
            }
        } catch (Exception e) {
            LogManagerHelper.error("Self-healing failed for '" + elementName + "'", e);
        }
        return null;
    }
}
