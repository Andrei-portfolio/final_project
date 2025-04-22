package tests.API;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import helper.APIHelper;
import model.TestEmployee;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeAPITests {

    Gson gson = new Gson();
    static String userToken;
    static int COMPANY_ID;
    static int EMPLOYEE_ID;

    @BeforeEach
    void beforeEach() {
        String params = "{\"username\": \"leonardo\", \"password\": \"leads\"}";
        JsonObject jsonObject =
                APIHelper.postJsonResponseFromRequest("https://x-clients-be.onrender.com/auth/login", params,
                        null);
        userToken = gson.fromJson(jsonObject.get("response").getAsString(), JsonObject.class).get("userToken").getAsString();
    }

    @BeforeAll
    static void beforeAll() {
        Gson gson = new Gson();
        String params = "{\"username\": \"leonardo\", \"password\": \"leads\"}";
        JsonObject jsonObject =
                APIHelper.postJsonResponseFromRequest("https://x-clients-be.onrender.com/auth/login", params,
                        null);
        userToken = gson.fromJson(jsonObject.get("response").getAsString(), JsonObject.class).get("userToken").getAsString();
        params = "{\"name\": \"FQF_Test\", \"description\": \"FQF_Test\"}";
        jsonObject = APIHelper.postJsonResponseFromRequest("https://x-clients-be.onrender.com/company", params, userToken);
        COMPANY_ID = gson.fromJson(jsonObject.get("response").getAsString(), JsonObject.class).get("id").getAsInt();
    }

    @AfterAll
    static void afterAll() {//Удалить созданную компанию после завершения тестов
        APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/company/delete/" + COMPANY_ID, userToken);
    }

    @Test
    @Order(1)
    @Tag("positive")
    @DisplayName("Проверрить код ответа при получении списка сотрудников новой компании")
    void getEmployeeListCodeTest() {
        JsonObject jsonObject =
                APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/employee?company=" +
                        511, null);
        int result = getCodeFromResponse(jsonObject);
        assert result == 200;
    }

    @Test
    @Order(2)
    @Tag("positive")
    @DisplayName("Проверить текст ответа при получении списка сотрудников новой компании")
    void getEmployeeListBodyTest() {
        JsonObject jsonObject =
                APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/employee?company=" +
                        COMPANY_ID, null);
        String result = jsonObject.get("response").getAsString();
        assert result.equals("[]");
    }

    @Test
    @Order(3)
    @Tag("negative")
    @DisplayName("Проверить код ответа на запрос с неверным идентификатором компании")
    void getEmployeeWrongListCode() {
        JsonObject jsonObject =
                APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/employee?company=garbage",
                        null);
        int result = getCodeFromResponse(jsonObject);
        assert result == 500;
    }

    @Test
    @Order(4)
    @Tag("positive")
    @DisplayName("Код ответа на запрос о создании сотрудника")
    void testCodeCreateEmployeeTest() {
        TestEmployee employee = new TestEmployee(COMPANY_ID);
        String params = gson.toJson(employee);
        JsonObject jsonObject =
                APIHelper.postJsonResponseFromRequest("https://x-clients-be.onrender.com/employee", params,
                        userToken);
        int code = getCodeFromResponse(jsonObject);
        assert code == 201;
        System.out.println(jsonObject);
    }

    @Test
    @Order(5)
    @Tag("positive")
    @DisplayName("Текст ответа на запрос о создании сотрудника")
    void testBodyCreateEmployeeTest() {
        TestEmployee employee = new TestEmployee(COMPANY_ID);
        String params = gson.toJson(employee);
        JsonObject jsonObject =
                APIHelper.postJsonResponseFromRequest("https://x-clients-be.onrender.com/employee", params,
                        userToken);
        EMPLOYEE_ID = getBodyFromResponse(jsonObject).get("id").getAsInt();
        assert EMPLOYEE_ID > 0;
    }

    @Test
    @Order(6)
    @Tag("negative")
    @DisplayName("Код ответа на неверный запрос о создании сотрудника")
    void testCodeCreateWrongEmployeeTest() {
        TestEmployee employee = new TestEmployee(0);
        String params = gson.toJson(employee);
        JsonObject jsonObject =
                APIHelper.postJsonResponseFromRequest("https://x-clients-be.onrender.com/employee", params,
                        userToken);
        int code = getCodeFromResponse(jsonObject);
        assert code == 500;
    }

    @Test
    @Order(7)
    @Tag("positive")
    @DisplayName("Получить код ответа при запросе данных о сотруднике по идентификатору")
    void testCodeGetEmployeeById() {
        JsonObject jsonObject =
                APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/employee/" +
                        EMPLOYEE_ID, null);
        int result = getCodeFromResponse(jsonObject);
        assert result == 200;
    }

    @Test
    @Order(8)
    @Tag("positive")
    @DisplayName("Проверить текст ответа при получении списка сотрудников новой компании")
    void testBodyGetEmployeeById() {
        JsonObject jsonObject =
                APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/employee/" +
                        EMPLOYEE_ID, null);
        String result = jsonObject.get("response").getAsString();
        TestEmployee testEmployee = gson.fromJson(result, TestEmployee.class);
        assert testEmployee.id == EMPLOYEE_ID;
        assert testEmployee.companyId == COMPANY_ID;
    }

    @Test
    @Order(9)
    @Tag("negative")
    @DisplayName("Получите код ответа при запросе данных о сотруднике по неправильному идентификатору")
    void testCodeGetWrongEmployeeById() {
        JsonObject jsonObject =
                APIHelper.getJsonResponseFromRequest("https://x-clients-be.onrender.com/employee/garbage",
                        null);
        int result = jsonObject.get("statusCode").getAsInt();
        assert result == 500;
    }

    @Test
    @Order(10)
    @Tag("positive")
    @DisplayName("Получить код ответа, исправив данные сотрудника по идентификатору")
    void testCodePatchEmployeeById() {
        String params = "{\"lastName\": \"Testing\", \"email\": \"Another@email.com\"," +
                "\"url\": \"phone\", \"phone\": \"url.net\"}";
        JsonObject jsonObject =
                APIHelper.patchJsonResponseFromRequest("https://x-clients-be.onrender.com/employee/" +
                        EMPLOYEE_ID, params, userToken);
        int result = getCodeFromResponse(jsonObject);
        assert result == 200;
    }

    @Test
    @Order(11)
    @Tag("positive")
    @DisplayName("Получить основную часть ответа, исправив данные о сотруднике по идентификатору")
    void testBodyPatchEmployeeById() {
        String params = "{\"lastName\": \"Testing\", \"email\": \"Another@email.com\"," +
                "\"url\": \"url.net\", \"phone\": \"string\"}";
        JsonObject jsonObject =
                APIHelper.patchJsonResponseFromRequest("https://x-clients-be.onrender.com/employee/" +
                        EMPLOYEE_ID, params, userToken);
        TestEmployee result = gson.fromJson(getBodyFromResponse(jsonObject), TestEmployee.class);
        assert result.id == EMPLOYEE_ID;
        //Пример ответа на Swagger резко отличается от реального ответа,
        //В реальном ответе отсутствует половина полей!
        assert result.email.equals("Another@email.com");
        assert result.url.equals("url.net");
    }

    @Test
    @Order(12)
    @Tag("negative")
    @DisplayName("Получить код из некорректного запроса на исправление данных")
    void testCodeWrongPatchEmployeeById() {
        String params = "{\"lastName\": \"Testing\", \"email\": \"Another@email.com\"," +
                "\"url\": \"phone\", \"phone\": \"url.net\"}";
        JsonObject jsonObject =
                APIHelper.patchJsonResponseFromRequest("https://x-clients-be.onrender.com/employee/" +
                        99999999, params, userToken);
        int result = getCodeFromResponse(jsonObject);
        assert result == 500;
    }

    int getCodeFromResponse(JsonObject jsonObject) {//Получить статус по ответу
        return jsonObject.get("statusCode").getAsInt();
    }

    JsonObject getBodyFromResponse(JsonObject jsonObject) {//Получите полный ответ
        return gson.fromJson(jsonObject.get("response").getAsString(), JsonObject.class);
    }
}
