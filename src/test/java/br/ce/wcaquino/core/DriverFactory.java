package br.ce.wcaquino.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;

public class DriverFactory {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            setupDriver();
        }
        return driver;
    }

    public static void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        configureLogging(options);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    public static void configureLogging(ChromeOptions options) {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
    }

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
}