package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    private final WebDriver driver;
    @FindBy(css = "#first-name")
    private WebElement firstNameField;
    @FindBy(css = "#last-name")
    private WebElement lastNameField;
    @FindBy(css = "#postal-code")
    private WebElement postalCodeField;
    @FindBy(css = "#continue")
    private WebElement continueButton;
    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;
    @FindBy(css = "#finish")
    private WebElement finishButton;
    @FindBy(css = ".complete-header")
    private WebElement finishHeader;

    public CheckoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step("Чекаут шаг 1 - Заполняем форму оформления заказа")
    public void fillForm(String firstName, String lastName, String postalCode) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
        continueButton.click();
    }

    @Step("Чекаут шаг 2 - Считываем итоговую сумму заказа")
    public String getTotalSum() {
        System.out.println("orderPrice =" + totalLabel.getText());
        return totalLabel.getText();
    }

    @Step("Чекаут шаг 2 - Нажимаем Finish")
    public void clickFinish() {
        finishButton.click();
    }

    @Step("Чекаут complete - Получаем текст заголовка")
    public String getHeader() {
        return finishHeader.getText();
    }

}