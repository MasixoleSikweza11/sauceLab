package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Reporting.ExtentReportManager;
import Utilities.BrowserManager;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    //
    @BeforeSuite
    public void setupSuite() {
        extent = ExtentReportManager.createInstance("test-output/ExtentReport.html");
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
        openReport();
    }

    protected String takeScreenshot(String testName) {
        if (driver == null) {
            System.out.println("WebDriver is not initialized. Cannot take screenshot.");
            return null;
        }

        // Wait for the page to load completely
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("saucedemo"));

        // Taking screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        File destFile = new File(screenshotPath);
        try {
            FileHandler.copy(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }

    @BeforeMethod
    @Parameters({"browser", "url", "keepBrowserOpen"})
    public void setup(@Optional("chrome") String browser,
                      @Optional("https://www.saucedemo.com/") String url,
                      @Optional("false") String keepBrowserOpen) {
        driver = BrowserManager.getDriver(browser);
        driver.get(url);

        // Log driver state and URL for debugging
        System.out.println("WebDriver initialized. Current URL: " + driver.getCurrentUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void log(String message) {
        test.log(Status.INFO, message);
    }

    private void openReport() {
        try {
            File htmlFile = new File("test-output/ExtentReport.html");
            if (htmlFile.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                System.out.println("Report file not found or Desktop is not supported.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
