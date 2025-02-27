
# Playwright Java Automation Framework with Self-Healing Locators (Powered by Gemini AI)

## Overview
This project is a **Java automation framework** using **Playwright** for web automation, with advanced **self-healing locators** using **Google Gemini AI**. When a locator fails, the framework automatically retrieves potential locators from Gemini AI and retries the action using the suggested locators.

---

## Features
- ✅ Playwright for Java for browser automation.
- ✅ Self-healing locators via Google Gemini AI.
- ✅ TestNG for test management.
- ✅ Centralized Logging with Log4j2.
- ✅ Allure Reporting for test result visualization.
- ✅ Configurable settings using `config.properties`.

---

## Project Structure
```text
src/
├── main/
│   ├── java/
│   │   ├── org/example/
│   │   │   ├── ai/                # GeminiAgent - AI locator handler
│   │   │   ├── helpers/           # SelfHealingLocatorHelper and LogManagerHelper
│   │   │   ├── pages/             # Page Object Model (POM) classes
│   │   │   ├── tests/             # Test classes using TestNG
│   │   │   ├── utils/             # ConfigManager, GeminiResponseParser, etc.
│   ├── resources/
│   │   ├── config.properties      # Configuration (URL, API keys, etc.)
│   │   ├── log4j2.xml              # Log4j2 configuration
├── test-output/                    # TestNG reports
├── allure-results/                 # Allure report files
├── logs/                           # Log files directory
├── pom.xml                         # Maven project descriptor
├── README.md                       # Project documentation (this file)
```

---

## Configuration
### Example `config.properties`
```properties
base.url=https://automationexercise.com/login
gemini.api.key=YOUR_GEMINI_API_KEY
browser=chromium
log.level=INFO
```

| Property          | Description                                     |
|-------------------|-------------------------------------------------|
| base.url          | Application URL                                |
| gemini.api.key    | API Key for Gemini AI                          |
| browser           | Browser (chromium, firefox, webkit)            |
| log.level         | Log level (INFO, DEBUG, ERROR)                  |

---

## Self-Healing Process
1. Element action (click/fill) is attempted using initial locator.
2. If the locator fails, page HTML is sent to Gemini AI.
3. Gemini AI responds with suggested locators.
4. Suggested locators are tried one by one until a working locator is found.
5. Logs are generated at each step.

---

## Logging
- Logs are handled via **LogManagerHelper** using **Log4j2**.
- All logs are saved in:
    ```text
    logs/app.log
    ```

---

## Running Tests
### Command
```bash
mvn clean test
```

### To View Allure Report
```bash
allure serve allure-results
```

---

## Dependencies (Key Excerpts from `pom.xml`)
```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.43.0</version>
</dependency>
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.7.1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.20.0</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.20.0</version>
</dependency>
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.21.0</version>
</dependency>
```

---

## Key Classes
| Class                          | Responsibility |
|---------------------------------|----------------|
| **SelfHealingLocatorHelper**   | Element actions with self-healing |
| **GeminiAgent**                 | Gemini API communication |
| **GeminiResponseParser**        | Parse AI responses into locators |
| **LogManagerHelper**            | Centralized logging |
| **ConfigManager**               | Config properties handler |
| **Page Classes**                | POM - Page Object classes |
| **Test Classes**                | Test execution via TestNG |

---

## Author
**Mohd Aatif Khan**
