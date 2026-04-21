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
                { Arrays.asList("BLACK") },
                { Arrays.asList("GREY") },
                { Arrays.asList("BLACK", "GREY") },
                { Arrays.asList() }
        });
    }

    private final List<String> colors;
    private final OrdersApiClient ordersClient = new OrdersApiClient();
    public CreateOrderTest(List<String> colors) {
        this.colors = colors;
    }

    @Test
    public void createOrderWithColorParameterized() {
        OrderModel order = new OrderModel();
        order.setFirstName("Naruto");
        order.setLastName("Uchiha");
        order.setAddress("Konoha, 142 apt.");
        order.setMetroStation("4");
        order.setPhone("+78003553535");
        order.setColor(colors);
        order.setComment("Saske, come back to Konoha");

        int track = ordersClient.createOrder(order);
        assertThat(track, notNullValue());
    }
}