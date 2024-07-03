package test;

import Application.*;
import Utilities.BrowserManager;
import Utilities.ExcelUtils;
import Utilities.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class SauceDemoTest extends BaseTest {
    private ScreenshotUtils screenshotUtils;

    @BeforeTest
    public void setUpTest() {
        // Initialize the WebDriver and other setup tasks here
        driver = BrowserManager.getDriver("chrome"); // Pass the required browser type
        screenshotUtils = new ScreenshotUtils(driver);
    }

    @AfterTest
    public void tearDown() {
        // Clean up and close the driver
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() throws IOException {
        String filePath = "src/test/resources/exel.xlsx";  // Corrected file path
        String sheetName = "Sheet1"; // Change as necessary

        ExcelUtils excel = new ExcelUtils(filePath, sheetName);
        int rowCount = excel.getRowCount();
        int colCount = excel.getColumnCount();

        Object[][] data = new Object[rowCount - 1][colCount]; // Skipping the header row

        for (int i = 1; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = excel.getCellData(i, j);
            }
        }

        excel.closeWorkbook();
        return data;
    }

    @Test(dataProvider = "loginData")
    public void loginAndAddRemoveProductTest(String username, String password) throws InterruptedException, IOException {
        test = extent.createTest("Login and Add/Remove Product Test");
        Products productsPage = new Products(driver);
        Checkout checkout = new Checkout(driver);
        Cart cartPage = new Cart(driver);
        Login loginPage = new Login(driver);

        try {
            test.log(Status.PASS, "Starting test with error user: " + username);

            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLogin();
            Thread.sleep(2000);
            test.log(Status.PASS, "Login attempt completed")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("Login"));

            // Verify login was successful
            Assert.assertFalse(driver.findElements(By.cssSelector(".error-message-container")).size() > 0, "Error message displayed");

            // Navigate to Products Page
            System.out.println("Navigated to Products Page");

            // Perform action to add the Sauce Labs Backpack to cart
            productsPage.addSauceLabsOnesieToCart();
            Thread.sleep(4000);
            test.log(Status.PASS, "Added Sauce Labs Onesie to cart")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("AddProduct"));

            // Verify product is added to the cart
            if (!driver.findElement(By.id("remove-sauce-labs-onesie")).isDisplayed()) {
                test.log(Status.FAIL, "Product not added to cart")
                        .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("ProductNotAdded"));
                Assert.fail("Product not added to cart");
            }
            test.log(Status.PASS, "Verified product added to cart");

            // Navigate to the shopping cart
            productsPage.goToShoppingCart();
            Thread.sleep(4000);
            test.log(Status.PASS, "Navigated to shopping cart")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("Cart"));

            // Verify the cart page is displayed
            if (!driver.findElement(By.cssSelector(".cart_list")).isDisplayed()) {
                test.log(Status.FAIL, "Cart page not displayed")
                        .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("CartPageNotDisplayed"));
                Assert.fail("Cart page not displayed");
            }
            test.log(Status.PASS, "Verified cart page is displayed");

            // Proceed to checkout
            cartPage.proceedToCheckout();
            Thread.sleep(4000);
            test.log(Status.PASS, "Proceeded to checkout")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("Checkout"));

            // Verify the checkout page is displayed
            if (!driver.findElement(By.id("checkout_info_container")).isDisplayed()) {
                test.log(Status.FAIL, "Checkout page not displayed")
                        .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("CheckoutPageNotDisplayed"));
                Assert.fail("Checkout page not displayed");
            }

            // Enter shipping details and proceed
            checkout.enterShippingDetails("Jordan", "Smith", "4960");
            Thread.sleep(4000);
            checkout.clickContinue();
            Thread.sleep(4000);
            test.log(Status.PASS, "Entered shipping details and continued")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("ShippingDetails"));



            // Finish the checkout process
            checkout.clickFinish();
            Thread.sleep(4000);
            test.log(Status.PASS, "Finished the checkout process")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("FinishCheckout"));

            // Verify the checkout completion
            if (!driver.findElement(By.cssSelector(".complete-header")).isDisplayed()) {
                test.log(Status.FAIL, "Checkout process not completed")
                        .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("CheckoutNotCompleted"));
                Assert.fail("Checkout process not completed");
            }
//
            Confirmation confirmationPage = new Confirmation(driver);
            // Verify the order confirmation message
            String confirmationMessage = confirmationPage.getOrderConfirmationMessage();
            Thread.sleep(4000);
            if (!"THANK YOU FOR YOUR ORDER".equals(confirmationMessage)) {
                test.log(Status.FAIL, "Order confirmation message not displayed")
                        .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("OrderConfirmationNotDisplayed"));
                Assert.fail("Order confirmation message not displayed");
            }
            test.log(Status.PASS, "Verified order confirmation message");

            // Go back to home page
            Thread.sleep(4000);
            confirmationPage.clickBackHome();
            Thread.sleep(4000);
            test.log(Status.PASS, "Navigated back to home page")
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("BackHome"));



        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed with exception: " + e.getMessage())
                    .addScreenCaptureFromPath(screenshotUtils.takeScreenshot("Exception"));
            throw e;
        }
    }
}


