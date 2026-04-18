import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetOrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Получить список всех доступных заказов")
    private Response getOrdersList(){
        Response response =
                given()
                        .get("/api/v1/orders");

        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));

        return response;
    }

    @Test
    public void getOrdersListAndCheckResponse() {
        Response response = getOrdersList();

        response.then()
                .body("orders[0].id", notNullValue())
                .body("orders[0].track", notNullValue())
                .body("orders[0].status", notNullValue());

        response.then()
                .body("pageInfo.page", equalTo(0))
                .body("pageInfo.total", greaterThan(0));

        response.then()
                .body("availableStations", hasSize(greaterThan(0)))
                .body("availableStations[0].name", notNullValue())
                .body("availableStations[0].number", notNullValue())
                .body("availableStations[0].color", notNullValue());
    }
}