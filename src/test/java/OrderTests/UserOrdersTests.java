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

public class UserOrdersTests extends OrderClient {
    private User user = new User();
    private Order order = new Order();
    private List<String> ingredients = new ArrayList<String>();

    @Before
    public void setUp() {
        BaseTest.setUp();
        BaseTest.createUser(user);
        ingredients.add(BaseTest.getIngredients().get(0).get_id());
        ingredients.add(BaseTest.getIngredients().get(1).get_id());
        order.setIngredients(ingredients);
        sendCreateOrderRequestAuthorized(order, user);
    }

    @Test
    @DisplayName("Получение заказов пользователя. Пользователь авторизован")
    @Description("Получаем заказы пользователя")
    public void getUserOrdersAuthorizedTest() {
        Response response = sendGetUserOrdersRequestAuthorized(user);
        assertGetUsersOrdersAuthorized(response);
    }

    @Test
    @DisplayName("Получение заказов пользователя. Пользователь не авторизован")
    @Description("Получаем заказы пользователя")
    public void getUserOrdersUnauthorizedTest() {
        Response response = sendGetUserOrdersRequestUnauthorized();
        assertGetOrdersUnauthorized(response);
    }

    @After
    public void cleanData() {
        BaseTest.deleteUser(user);
    }
}
