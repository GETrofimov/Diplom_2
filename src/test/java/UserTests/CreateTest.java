package UserTests;

import user.body.User;
import api.client.BaseTest;
import api.client.UserClient;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateTest extends UserClient {
    private User user = new User();
    static Faker faker = new Faker();

    @Before
    public void setUp() {
        BaseTest.setUp();
        user.setEmail(faker.internet().emailAddress());
        user.setName(faker.name().fullName());
        user.setPassword(faker.internet().password());
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Создаем пользователя с валидными кредами")
    public void createValidUser() {
        Response response = sendCreateUserRequest(user);
        user.setAccessToken(response.then().extract().body().path("accessToken"));
        user.setToken(response.then().extract().body().path("refreshToken"));
        assertCreateUser(response);
    }

    @After
    public void cleanData() {
        if(user.getAccessToken() != null) {
            BaseTest.deleteUser(user);
        }
    }
}
