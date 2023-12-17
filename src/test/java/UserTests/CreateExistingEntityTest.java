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

public class CreateExistingEntityTest extends UserClient {
    private User user = new User();

    @Before
    public void setUp() {
        BaseTest.setUp();
        BaseTest.createUser(user);
    }

    @Test
    @DisplayName("Создание пользователя. Пользователь с указанными кредами уже существует")
    @Description("Создаем пользователя, но такой пользователь уже существует")
    public void createValidUser() {
        Response response = sendCreateUserRequest(user);
        assertCreateUserAlreadyExists(response);
    }

    @After
    public void cleanData() {
        if(user.getAccessToken() != null) {
            BaseTest.deleteUser(user);
        }
    }
}
