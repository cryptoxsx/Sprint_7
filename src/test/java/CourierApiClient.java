import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class CourierApiClient {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    static {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Создать курьера")
    public Response createCourier(CourierModel courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Создать курьера без логина")
    public Response createCourierWithoutLogin(String password, String firstName) {
        CourierModel courier = new CourierModel();
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Создать курьера без пароля")
    public Response createCourierWithoutPassword(String login, String firstName) {
        CourierModel courier = new CourierModel();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Войти в систему")
    public Response loginCourier(String login, String password) {
        CourierModel loginBody = new CourierModel();
        loginBody.setLogin(login);
        loginBody.setPassword(password);
        return given()
                .header("Content-type", "application/json")
                .body(loginBody)
                .post("/api/v1/courier/login");
    }

    @Step("Войти без логина")
    public Response loginCourierWithoutLogin(String password) {
        CourierModel loginBody = new CourierModel();
        loginBody.setPassword(password);
        return given()
                .header("Content-type", "application/json")
                .body(loginBody)
                .post("/api/v1/courier/login");
    }

    @Step("Войти без пароля")
    public Response loginCourierWithoutPassword(String login) {
        CourierModel loginBody = new CourierModel();
        loginBody.setLogin(login);
        return given()
                .header("Content-type", "application/json")
                .body(loginBody)
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public void deleteCourier(int id) {
        given()
                .delete("/api/v1/courier/" + id);
    }
}