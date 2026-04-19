import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private final CourierApiClient client = new CourierApiClient();
    private int courierId;

    @Test
    public void loginCourierAndCheckResponse() {
        client.createCourier("ninja4356", "1234", "saske");
        Response loginResponse = client.loginCourier("ninja4356", "1234");
        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue());
        courierId = loginResponse.jsonPath().getInt("id");
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
        Response loginResponse = client.loginCourier("ninja12121212", "1234000");
        loginResponse.then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    public void loginWithWrongPasswordReturnsError() {
        client.createCourier("ninja4356", "1234", "saske");

        Response loginResponse = client.loginCourier("ninja4356", "12341234");
        loginResponse.then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @After
    public void deleteCourierAfterTest() {
        if (courierId > 0) {
            client.deleteCourier(courierId);
        }
    }
}