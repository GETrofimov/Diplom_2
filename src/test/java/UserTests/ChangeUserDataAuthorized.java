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
public class ChangeUserDataAuthorized extends UserClient {
    private User user = new User();
    private String email;
    private String name;
    private String password;
    private static Faker faker = new Faker();

    public ChangeUserDataAuthorized(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {EMAIL, faker.name().fullName(), PASSWORD},
                {faker.internet().emailAddress(), NAME, PASSWORD},
                {EMAIL, NAME, faker.internet().password()}
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
    @DisplayName("Изменение пользователя. С авторизацией")
    @Description("Меняем поля email и password")
    public void changeUserDataAuthorized() {
        Response response = sendPatchUserRequestAuthorized(user);
        asserPatchUser(response);
    }

    @After
    public void cleanData() {
        BaseTest.deleteUser(user);
    }
}
