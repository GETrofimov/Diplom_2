package UserTests;

import user.body.User;
import api.client.BaseTest;
import api.client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest extends UserClient {
    private User user = new User();
    @Before
    public void setUp() {
        BaseTest.setUp();
        BaseTest.createUser(user);
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Авторизуемся с валидными кредами")
    public void loginUserSuccess() {
        Response response = sendLoginUserRequest(user);
        assertLoginUser(response);
    }

    @After
    public void cleanData() {
        BaseTest.deleteUser(user);
    }
}
