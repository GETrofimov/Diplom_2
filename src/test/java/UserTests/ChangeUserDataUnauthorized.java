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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static user.constants.Credentials.*;

@RunWith(Parameterized.class)
public class ChangeUserDataUnauthorized extends UserClient {
    private User user = new User();
    private String email;
    private String name;
    private static Faker faker = new Faker();

    public ChangeUserDataUnauthorized(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {EMAIL, faker.name().fullName()},
                {faker.internet().emailAddress(), NAME},
        };
    }
    @Before
    public void setUp() {
        BaseTest.setUp();
        BaseTest.createUser(user);
        user.setName(name);
        user.setEmail(email);
    }

    @Test
    @DisplayName("Изменение пользователя. Без авторизации")
    @Description("Меняем поля email и password, но не передаем токен авторизации")
    public void changeUserDataAuthorized() {
        Response response = sendPatchUserRequestUnauthorized(user);
        asserPatchUserValidation(response);
    }

    @After
    public void cleanData() {
        BaseTest.deleteUser(user);
    }
}
