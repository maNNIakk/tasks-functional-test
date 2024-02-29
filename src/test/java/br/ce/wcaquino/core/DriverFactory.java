package br.ce.wcaquino.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class DriverFactory {
    public static RemoteWebDriver driver;

    public static RemoteWebDriver getDriver() {
        if (driver == null) {
            setupDriver();
        }
        return driver;
    }

    public static void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        configureLogging(options);

        WebDriverManager.chromedriver().setup();
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
                    options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver.manage().window().maximize();
    }

    public static void configureLogging(ChromeOptions options) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
    }

    @SuppressWarnings("Vou usar no futuro")
    public static void printHttpXhrLogs() {
        if (driver != null) {
            // Get and print only HTTP/XHR logs
            LogEntries logs = driver.manage().logs().get(LogType.PERFORMANCE);
            System.out.println("HTTP/XHR Logs:");
            for (LogEntry entry : logs) {
                // Check if the log entry is related to HTTP/XHR requests
                if (entry.getMessage().contains("Network.requestWillBeSent") ||
                        entry.getMessage().contains("Network.responseReceived")) {
                    System.out.println(entry.getMessage());
                }
            }
        } else {
            System.out.println("Driver is null. No logs to print.");
        }
    }

    public static void killDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reset driver after quitting
        }
    }

    public static String getTestName() {
        return new Object(){}.getClass().getEnclosingMethod().getName();
    }
}