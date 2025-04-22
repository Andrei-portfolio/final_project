package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;
    @FindBy(css = "#user-name")
    private WebElement loginField;
    @FindBy(css = "#password")
    private WebElement pswField;
    @FindBy(css = "#login-button")
    private WebElement loginBtn;
    @FindBy(css = "h3[data-test='error']")
    private WebElement errorText;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step("Авторизация")
    public void login(String login, String password) {
        loginField.sendKeys(login);
        pswField.sendKeys(password);
        loginBtn.submit();
    }

    @Step("Считываем сообщение об ошибке авторизации")
    public String getErrorText() {
        return errorText.getText();
    }


}