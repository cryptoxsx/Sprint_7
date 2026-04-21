import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private final CourierApiClient client = new CourierApiClient();
    private int courierId;

    private static final String LOGIN = "ninja4356";
    private static final String PASSWORD = "1234";
    private static final String FIRST_NAME = "saske";

    @Before
    public void createCourierForLogin() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        client.createCourier(courier);
    }

    @Test
    public void loginCourierAndCheckResponse() {
        Response loginResponse = client.loginCourier(LOGIN, PASSWORD);
        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void loginCourierWithoutLoginReturnsError() {
        Response loginResponse = client.loginCourierWithoutLogin("123456");
        loginResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    public void loginCourierWithoutPasswordReturnsError() {
        Response loginResponse = client.loginCourierWithoutPassword("ninja");
        loginResponse.then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithWrongLoginReturnsError() {
        Response loginResponse = client.loginCourier("ninja12121212", PASSWORD);
        loginResponse.then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    public void loginWithWrongPasswordReturnsError() {
        Response loginResponse = client.loginCourier(LOGIN, "12341234");
        loginResponse.then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
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