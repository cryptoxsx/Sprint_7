import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;

public class OrdersApiClient {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    static {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Создать заказ")
    public int createOrder(OrderModel order) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)                 // RestAssured сам читает файл
                        .post("/api/v1/orders");

        response.then()
                .statusCode(201)
                .body("track", notNullValue());

        return response.body().jsonPath().getInt("track");
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .get("/api/v1/orders");
    }
}