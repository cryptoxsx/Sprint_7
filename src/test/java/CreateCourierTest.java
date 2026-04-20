import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private final CourierApiClient client = new CourierApiClient();
    private int courierId;

    @Test
    public void createNewCourierAndCheckResponse() {
        CourierModel courier = new CourierModel("ninja4355", "1234", "saske");
        Response createResponse = client.createCourier(courier);
        createResponse.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        Response loginResponse = client.loginCourier(courier.getLogin(), courier.getPassword());
        courierId = loginResponse.jsonPath().getInt("id");
    }

    @Test
    public void dublicateCreateNewCourierReturnsError() {
        CourierModel courier = new CourierModel("ninja4355", "1234", "saske");
        client.createCourier(courier);
        Response dublicateResponse = client.createCourier(courier);
        dublicateResponse.then()
                .statusCode(409)
                .body("message", containsString("Этот логин уже используется"));

        Response loginResponse = client.loginCourier(courier.getLogin(), courier.getPassword());
        courierId = loginResponse.jsonPath().getInt("id");
    }

    @Test
    public void createNewCourierWithoutLoginReturnsError() {
        Response withoutLoginResponse = client.createCourierWithoutLogin("1234", "saske");
        withoutLoginResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    public void createNewCourierWithoutPasswordReturnsError() {
        Response withoutPasswordResponse = client.createCourierWithoutPassword("ninja4355", "saske");
        withoutPasswordResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных"));
    }

    @After
    public void deleteCourierAfterTest() {
        if (courierId > 0) {
            client.deleteCourier(courierId);
        }
    }
}