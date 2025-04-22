package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
    private final WebDriver driver;
    @FindBy(css = "#add-to-cart-sauce-labs-backpack")
    private WebElement itemBackpack;
    @FindBy(css = "#add-to-cart-sauce-labs-bolt-t-shirt")
    private WebElement itemBoltTShirt;
    @FindBy(css = "#add-to-cart-sauce-labs-onesie")
    private WebElement itemOnesie;
    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step("Кладем 3 товара в корзину")
    public void add3ItemsToCart() {
        itemBackpack.click();
        itemBoltTShirt.click();
        itemOnesie.click();
    }

    @Step("Заходим в корзину через иконку в правом верхнем углу")
    public void goToCart() {
        cartIcon.click();
    }

}