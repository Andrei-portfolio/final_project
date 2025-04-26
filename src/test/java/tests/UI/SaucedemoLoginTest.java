package tests.UI;

import helper.ConfProperties;
import io.qameta.allure.Attachment;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;//
import page_object.LoginPage;

import java.io.File;//

import static org.junit.jupiter.api.Assertions.assertEquals;

@Severity(SeverityLevel.BLOCKER)

public class SaucedemoLoginTest { private static WebDriver driver;
    private final static String USERNAME_STANDARD = ConfProperties.getProperty("username_standard");
    private final static String USERNAME_LOCKED = ConfProperties.getProperty("username_locked");
    private final static String PASSWORD = ConfProperties.getProperty("password");
    private final static String URL = ConfProperties.getProperty("url");
    private final static String URL_TO_BE_AFTER_LOGIN = URL + "inventory.html";
    private final static String ERROR_TEXT_TO_BE = "Epic sadface: Sorry, this user has been locked out.";
    private static LoginPage loginPage;

    //    private static WebDriverWait wait;
    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @BeforeEach
    public void setUpEach() {
//        ChromeOptions options = new ChromeOptions();//
//        options.addExtensions(new File("src/test/resources/User-Agent-Switcher-for-Chrome-Chrome.crx"));//
//        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);//
//        options.addArguments("--headless");//
        driver.get(URL);
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void login() {
        loginPage.login(USERNAME_STANDARD, PASSWORD);
        String urlIs = driver.getCurrentUrl();

        assertEquals(URL_TO_BE_AFTER_LOGIN, urlIs);
    }

    @Test
    @DisplayName("Авторизация заблокированным пользователем")
    public void loginByLockedUser() {
        loginPage.login(USERNAME_LOCKED, PASSWORD);
        String errorText = loginPage.getErrorText();
        String urlIs = driver.getCurrentUrl();

        assertEquals(ERROR_TEXT_TO_BE, errorText);
        assertEquals(URL, urlIs);
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