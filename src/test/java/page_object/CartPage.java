package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class CartPage {
    private final WebDriver driver;
    @FindBy(css = ".inventory_item_name")
    private List<WebElement> cartItems;
    @FindBy(css = "#checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step("Получаем количество товаров в корзине")
    public int checkAmountOfItems() {
        return cartItems.size();
    }

    @Step("Нажимаем на Checkout")
    public void clickCheckout() {
        checkoutButton.click();
    }
}