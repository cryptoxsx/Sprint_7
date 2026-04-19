import io.restassured.response.Response;
import org.junit.*;
import io.qameta.allure.Step;
import static org.hamcrest.Matchers.*;

public class GetOrdersListTest {
    private final OrdersApiClient client = new OrdersApiClient();

    @Step("Получить список всех доступных заказов")
    private Response getOrdersList(){
        return client.getOrdersList();
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