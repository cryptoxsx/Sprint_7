import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import io.qameta.allure.Step;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {

    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Создать курьера из JSON‑файла")
    private Response createCourierFromJson(File jsonFile) {
        return given()
                .header("Content-type", "application/json")
                .body(jsonFile)
                .post("/api/v1/courier");
    }

    @Step("Войти в систему и получить id курьера")
    private int loginCourierAndGetId(File jsonFile) {
        Response loginResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(jsonFile)
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
    public void createNewCourierAndCheckResponse() {
        File json = new File("src/test/resources/newCourier.json");

        Response createResponse = createCourierFromJson(json);
        createResponse.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = loginCourierAndGetId(json);

        deleteCourierById(courierId);
    }

    @Test
    public void dublicateCreateNewCourierReturnsError() {
        File json = new File("src/test/resources/newCourier.json");

        createCourierFromJson(json);

        Response dublicateCreateResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier");

        dublicateCreateResponse.then()
                .statusCode(409)
                .body("message", containsString("Этот логин уже используется"));

        courierId = loginCourierAndGetId(json);
        deleteCourierById(courierId);
    }

    @Test
    public void createNewCourierWithoutLoginReturnsError() {
        File json = new File("src/test/resources/newCourierWithoutLogin.json");
        Response withoutLoginResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier");
        withoutLoginResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    public void createNewCourierWithoutPasswordReturnsError() {
        File json = new File("src/test/resources/newCourierWithoutPassword.json");
        Response withoutPasswordResponse =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/courier");
        withoutPasswordResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }
}