package Application;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Products {
    private WebDriver driver;
    //
    // Locators
    private By shoppingCartLink = By.xpath("//*[@id=\"shopping_cart_container\"]/a");
    private By menuButton = By.xpath("//*[@id='react-burger-menu-btn']");
    private By logoutLink = By.xpath("//*[@id=\"logout_sidebar_link\"]");

    public Products(WebDriver driver) {
        this.driver = driver;
    }

    // Method to add Sauce Labs Onesie to cart
    public void addSauceLabsOnesieToCart() {
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-onesie\"]")).click();
    }

    // Method to click the shopping cart link
    public void goToShoppingCart() {
        driver.findElement(shoppingCartLink).click();
    }

    // Method to log out
    public void logout() {
        driver.findElement(menuButton).click();
        // Adding sleep to wait for the menu to appear
        try {
            Thread.sleep(1000); // Adjust the wait time as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(logoutLink).click();
    }

    // Method to select an item and add it to cart by item name
    public void selectItemAndAddToCart(String itemName) {
        String itemXPath = String.format("//*[@id=\"item_4_title_link\"]/div", itemName);
        driver.findElement(By.xpath(itemXPath)).click();
        String addToCartButtonXPath = String.format("//*[@id=\"add-to-cart\"]", itemName.replace(" ", "-").toLowerCase());
        driver.findElement(By.xpath(addToCartButtonXPath)).click();
    }
}
