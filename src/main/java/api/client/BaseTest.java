package api.client;

import ingredient.IngredientData;
import ingredient.ResponseData;
import user.body.User;
import common.constants.BaseParams;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static api.client.IngredientClient.sendGetIngredientsRequest;
import static user.constants.Credentials.*;
import static api.client.UserClient.*;
import static common.constants.StatusCodes.ACCEPTED;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {
    @Step("Set URL for test")
    public static void setUp() {
        RestAssured.baseURI = BaseParams.BASE_URL;
    }

    @Step("Удаление тестового пользователя")
    public static void deleteUser(User body) {
        Response response = sendDeleteUserRequest(body);
        response.then().assertThat().statusCode(ACCEPTED)
                .and()
                .body("message", equalTo("User successfully removed"))
                .body("success", equalTo(true));
    }

    @Step("Создание тестового пользователя")
    public static void createUser(User user) {
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);
        Response create = sendCreateUserRequest(user);
        user.setAccessToken(create.then().extract().body().path("accessToken"));
    }

    @Step("Получение списка доступных ингредиентов")
    public static List<IngredientData> getIngredients() {
        ResponseData response = sendGetIngredientsRequest();
        List<IngredientData> data = response.getData();
        return data;
    }
}
