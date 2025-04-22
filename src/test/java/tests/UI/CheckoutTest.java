package tests.UI;

import helper.ConfProperties;
import io.qameta.allure.Attachment;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page_object.CartPage;
import page_object.CheckoutPage;
import page_object.HomePage;
import page_object.LoginPage;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Severity(SeverityLevel.BLOCKER)

public class CheckoutTest {
    private static WebDriver driver;
    private static final String username_standard = ConfProperties.getProperty("username_standard");
    private static final String username_glitched = ConfProperties.getProperty("username_glitched");
    private static final String password = ConfProperties.getProperty("password");
    private static final String url = ConfProperties.getProperty("url");
    private static final String checkoutFormFirstName = ConfProperties.getProperty("checkoutFormFirstName");
    private static final String checkoutFormLastName = ConfProperties.getProperty("checkoutFormLastName");
    private static final String checkoutFormPostalCode = ConfProperties.getProperty("checkoutFormPostalCode");
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static CartPage cartPage;
    private static CheckoutPage checkoutPage;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @ParameterizedTest
    @MethodSource("provideUsers")
    @DisplayName("e2e - Авторизация, кладем 3 товара в корзину, оформляем заказ")
    public void checkout(String username, String password) {
        driver.get(url);
        loginPage.login(username, password);
        homePage.add3ItemsToCart();
        homePage.goToCart();
        assertEquals(3, cartPage.checkAmountOfItems());

        cartPage.clickCheckout();
        checkoutPage.fillForm(checkoutFormFirstName, checkoutFormLastName, checkoutFormPostalCode);
        assertEquals("Total: $58.29", checkoutPage.getTotalSum());

        checkoutPage.clickFinish();
        assertEquals("Thank you for your order!", checkoutPage.getHeader());
    }

    private static Stream<Arguments> provideUsers() {
        return Stream.of(
                Arguments.of(username_standard, password),
                Arguments.of(username_glitched, password)
        );
    }

    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    private byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterEach
    public void tearDownEach() {
        takeScreenshot();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}

