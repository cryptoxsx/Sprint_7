import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private final CourierApiClient client = new CourierApiClient();
    private int courierId;

    private static final String LOGIN = "ninja4355";
    private static final String PASSWORD = "1234";
    private static final String FIRST_NAME = "saske";

    @Before
    public void createCourierIfNotExists() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        Response createResponse = client.createCourier(courier);
        createResponse.then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createNewCourierAndCheckResponse() {
    }

    @Test
    public void dublicateCreateNewCourierReturnsError() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        client.createCourier(courier);
        Response dublicateResponse = client.createCourier(courier);
        dublicateResponse.then()
                .statusCode(409)
                .body("message", containsString("Этот логин уже используется"));
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
    public void loginAndDeleteCourierAfterTest() {
        Response loginResponse = client.loginCourier(LOGIN, PASSWORD);
        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue());
        courierId = loginResponse.jsonPath().getInt("id");
        if (courierId > 0) {
            client.deleteCourier(courierId);
        }
    }
}