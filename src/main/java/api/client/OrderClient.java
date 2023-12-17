package api.client;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import order.body.Order;
import user.body.User;

import static common.constants.BaseParams.*;
import static common.constants.StatusCodes.*;
import static ingredient.constants.Routes.*;
import static io.restassured.RestAssured.given;
import static order.constants.Routes.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class OrderClient {
    @Step("Cоздание заказа. Пользователь авторизован")
    public static Response sendCreateOrderRequestAuthorized(Order body, User user) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .header(AUTHORIZATION, user.getAccessToken())
                        .and()
                        .body(body)
                        .when()
                        .post(CREATE_ORDER);
        return response;
    }
    @Step("Cоздание заказа. Пользователь не авторизован")
    public static Response sendCreateOrderRequestUnauthorized(Order body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .and()
                        .body(body)
                        .when()
                        .post(CREATE_ORDER);
        return response;
    }
    @Step("Получение ингредиентов")
    public static Response sendGetIngredientsRequest() {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .when()
                        .get(GET_INGREDIENTS);
        return response;
    }

    @Step("Проверка ответа на ручку создания заказа. Пользователь авторизован")
    public void assertCreateOrderAuthorized(Response response) {
        response.then().assertThat().statusCode(OK)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Проверка ответа на ручку создания заказа. Пользователь авторизован")
    public void assertCreateOrderUnauthorized(Response response) {
        response.then().assertThat().statusCode(UNAUTHORIZED)
                .and()
                .body("success", equalTo(false));
    }
}
