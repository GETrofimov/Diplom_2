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

@RunWith(Parameterized.class)
public class CreateValidationTest extends UserClient {
    private User user = new User();
    static Faker faker = new Faker();

    public CreateValidationTest(String email, String password, String name) {
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][] {
                {null, faker.internet().password(), faker.name().fullName()},
                {faker.internet().emailAddress(), null, faker.name().fullName()},
                {faker.internet().emailAddress(), faker.internet().password(), null},
        };
    }
    @Before
    public void setUp() {
        BaseTest.setUp();
    }

    @Test
    @DisplayName("Создание пользователя. Не заполняем одно из обязательных полей")
    @Description("Создаем пользователя без полей email, password, name")
    public void createUserWithEmptyFields() {
        Response response = sendCreateUserRequest(user);
        user.setAccessToken(response.then().extract().body().path("accessToken"));
        user.setToken(response.then().extract().body().path("refreshToken"));
        assertCreateUserEmptyFields(response);
    }

    @After
    public void cleanData() {
        if(user.getAccessToken() != null) {
            BaseTest.deleteUser(user);
        }
    }
}
