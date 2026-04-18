import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import io.qameta.allure.Step;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.given;


import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    @Parameterized.Parameters(name = "color: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { "Black.json" },
                { "Grey.json" },
                { "BlackAndGrey.json" },
                { "WithoutColor.json" }
        });
    }

    private final String fileName;

    public CreateOrderTest(String fileName) {
        this.fileName = fileName;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Создать заказ из файла {fileName} и проверить track")
    private int createOrderAndGetTrack(String fileName) {
        File json = new File("src/test/resources/" + fileName);

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(json)
                        .post("/api/v1/orders");

        response.then()
                .statusCode(201)
                .body("track", notNullValue());

        return response.body().jsonPath().getInt("track");
    }

    @Test
    public void createOrderWithColorParameterized() {
        int track = createOrderAndGetTrack(fileName);
        assertThat(track, notNullValue());
    }
}