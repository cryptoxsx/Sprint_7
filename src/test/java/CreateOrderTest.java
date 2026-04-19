import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Collection;
import java.util.*;
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
    private final OrdersApiClient ordersClient = new OrdersApiClient();
    public CreateOrderTest(String fileName) {
        this.fileName = fileName;
    }

    @Test
    public void createOrderWithColorParameterized() {
        int track = ordersClient.createOrderFromFile("src/test/resources/" + fileName);
        assertThat(track, notNullValue());
    }
}