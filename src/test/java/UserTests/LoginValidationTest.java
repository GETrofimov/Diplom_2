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
public class LoginValidationTest extends UserClient {
    private User user = new User();
    private String email;
    private String password;
    private static Faker faker = new Faker();

    public LoginValidationTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {null, PASSWORD},
                {EMAIL, null},
                {null, null},
                {EMAIL, faker.animal().name()},
                {faker.internet().emailAddress(), PASSWORD}
        };
    }

    @Before
    public void setUp() {
        BaseTest.setUp();
        BaseTest.createUser(user);
        this.user.setEmail(email);
        this.user.setPassword(password);
    }

    @Test
    @DisplayName("Авторизация пользователя. Не заполняем одно из обязательных полей")
    @Description("Авторизуемся без полей email, password, name. Авторизуемся, но указываем неправильные креды")
    public void loginTestValidation() {
        Response response = sendLoginUserRequest(user);
        assertLoginUserValidation(response);
    }

    @After
    public void cleanData() {
        BaseTest.deleteUser(user);
    }
}
