import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierApiClient {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    static {
        RestAssured.baseURI = BASE_URI;
    }

    public Response createCourier(String login, String password, String firstName) {
        return given()
                .header("Content-type", "application/json")
                .body(String.format(
                        "{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}",
                        login, password, firstName
                ))
                .post("/api/v1/courier");
    }

    public Response createCourierWithoutLogin(String password, String firstName) {
        String json = String.format(
                "{\"password\": \"%s\", \"firstName\": \"%s\"}", password, firstName
        );
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier");
    }

    public Response createCourierWithoutPassword(String login, String firstName) {
        String json = String.format(
                "{\"login\": \"%s\", \"firstName\": \"%s\"}", login, firstName
        );
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier");
    }

    public Response loginCourier(String login, String password) {
        return given()
                .header("Content-type", "application/json")
                .body(String.format(
                        "{\"login\": \"%s\", \"password\": \"%s\"}",
                        login, password
                ))
                .post("/api/v1/courier/login");
    }

    public Response loginCourierWithoutLogin(String password) {
        String json = String.format("{\"password\": \"%s\"}", password);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier/login");
    }

    public Response loginCourierWithoutPassword(String login) {
        String json = String.format("{\"login\": \"%s\"}", login);
        return given()
                .header("Content-type", "application/json")
                .body(json)
                .post("/api/v1/courier/login");
    }

    public void deleteCourier(int id) {
        given()
                .delete("/api/v1/courier/" + id);
    }
}