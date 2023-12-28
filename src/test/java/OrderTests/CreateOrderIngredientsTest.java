package OrderTests;

import api.client.BaseTest;
import api.client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.body.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.body.User;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderIngredientsTest extends OrderClient {
    private User user = new User();
    private Order order = new Order();
    private List<String> ingredients = new ArrayList<String>();

    @Before
    public void setUp() {
        BaseTest.setUp();
        BaseTest.createUser(user);
    }

    @Test
    @DisplayName("Создание заказа. С ингредиентами")
    @Description("Создаем заказ с ингредиентами")
    public void createOrderWithIngredientsTest() {
        ingredients.add(BaseTest.getIngredients().get(0).get_id());
        ingredients.add(BaseTest.getIngredients().get(1).get_id());
        order.setIngredients(ingredients);
        Response response = sendCreateOrderRequestAuthorized(order, user);
        assertCreateOrderAuthorized(response);
    }

    @Test
    @DisplayName("Создание заказа. Без ингредиентов")
    @Description("Создаем заказ без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        Response response = sendCreateOrderRequestAuthorized(order, user);
        assertCreateOrderWithoutIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа. С неверным кодом ингредиентов")
    @Description("Создаем заказ с неверным кодом ингредиентов")
    public void createOrderWithWrongIngredientsTest() {
        ingredients.add(BaseTest.getIngredients().get(0).getName());
        ingredients.add(BaseTest.getIngredients().get(1).getName());
        order.setIngredients(ingredients);
        Response response = sendCreateOrderRequestAuthorized(order, user);
        assertCreateOrderWrongIngredients(response);
    }

    @After
    public void cleanData() {
        BaseTest.deleteUser(user);
    }
}
