package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import order.body.Order;
import user.body.User;

import static common.constants.BaseParams.*;
import static common.constants.StatusCodes.*;
import static io.restassured.RestAssured.given;
import static order.constants.Messages.NOT_AUTHORIZED;
import static order.constants.Messages.NO_INGREDIENTS;
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

    @Step("Получение заказа. Пользователь не авторизован")
    public static Response sendGetUserOrdersRequestUnauthorized() {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .when()
                        .get(GET_ORDER);
        return response;
    }

    @Step("Получение заказа. Пользователь авторизован")
    public static Response sendGetUserOrdersRequestAuthorized(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .header(AUTHORIZATION, body.getAccessToken())
                        .when()
                        .get(GET_ORDER);
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

    @Step("Проверка ответа на ручку создания заказа. Неверный хэш ингредиентов")
    public void assertCreateOrderWrongIngredients(Response response) {
        response.then().assertThat().statusCode(INTERNAL_SERVER_ERROR);
    }

    @Step("Проверка ответа на ручку создания заказа. Пользователь авторизован")
    public void assertCreateOrderWithoutIngredients(Response response) {
        response.then().assertThat().statusCode(BAD_REQUEST)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo(NO_INGREDIENTS));
    }

    @Step("Проверка ответа на ручку получения заказа пользователя. Пользователь авторизован")
    public void assertGetUsersOrdersAuthorized(Response response) {
        response.then().assertThat().statusCode(OK)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Проверка ответа на ручку получения заказа пользователя. Пользователь авторизован")
    public void assertGetOrdersUnauthorized(Response response) {
        response.then().assertThat().statusCode(UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo(NOT_AUTHORIZED));
    }
}
