package Application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
    WebDriver driver;
    //
    // Locators for the username, password fields, and login button
    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");

    // Constructor to initialize the WebDriver
    public Login(WebDriver driver) {
        this.driver = driver;
    }

    // Method to enter username
    public void enterUsername(String username) {
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys(username);
    }

    // Method to enter password
    public void enterPassword(String password) {
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
    }

    // Method to click the login button
    public void clickLogin() {
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
    }
}
