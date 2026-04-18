import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import io.qameta.allure.Step;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {

    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Создать курьера из JSON‑файла")
    private Response createCourierFromJson(File json) {
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier");
    }

    @Step("Войти в систему и получить id курьера")
    private int loginCourierAndGetId(File json) {
        Response loginResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier/login");

        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue());

        return loginResponse.jsonPath().getInt("id");
    }

    @Step("Удалить курьера по id через DELETE /api/v1/courier/{id}")
    private void deleteCourierById(int id) {
        given()
                .delete("/api/v1/courier/" + id);
    }

    @Test
    public void loginCourierAndCheckResponse() {
        File json = new File("src/test/resources/loginCourier.json");

        createCourierFromJson(json);

        courierId = loginCourierAndGetId(json);

        deleteCourierById(courierId);
    }

    @Test
    public void loginCourierWithoutLoginReturnsError() {
        File json = new File("src/test/resources/loginCourierWithoutLogin.json");

        Response loginResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier/login");

        loginResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithoutPasswordReturnsError() {
        File json = new File("src/test/resources/loginCourierWithoutPassword.json");

        Response loginResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier/login");

        loginResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    public void loginUnregisteredCourierReturnsError() {
        File json = new File("src/test/resources/loginUnregisteredCourier.json");

        Response loginResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier/login");

        loginResponse.then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }
}