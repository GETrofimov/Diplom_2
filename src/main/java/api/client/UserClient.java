package api.client;

import user.body.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static user.constants.Messages.*;
import static user.constants.Routes.*;
import static common.constants.BaseParams.*;
import static common.constants.StatusCodes.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserClient {
    @Step("Cоздание пользователя")
    public static Response sendCreateUserRequest(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .and()
                        .body(body)
                        .when()
                        .post(CREATE_USER);
        return response;
    }

    @Step("Авторизация пользователя")
    public static Response sendLoginUserRequest(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .and()
                        .body(body)
                        .when()
                        .post(LOGIN);
        return response;
    }

    @Step("Выход пользователя из системы")
    public static Response sendLogoutUserRequest(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .and()
                        .body(body)
                        .when()
                        .post(LOGOUT);
        return response;
    }

    @Step("Удаление пользователя")
    public static Response sendDeleteUserRequest(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .header(AUTHORIZATION, body.getAccessToken())
                        .and()
                        .when()
                        .delete(DELETE_USER);
        return response;
    }

    @Step("Обновление данных пользователя. С авторизацией")
    public static Response sendPatchUserRequestAuthorized(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .header(AUTHORIZATION, body.getAccessToken())
                        .and()
                        .when()
                        .patch(UPDATE_USER);
        return response;
    }

    @Step("Обновление данных пользователя. Без авторизации")
    public static Response sendPatchUserRequestUnauthorized(User body) {
        Response response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .and()
                        .when()
                        .patch(UPDATE_USER);
        return response;
    }

    @Step("Проверка ответа на ручку создания пользователя. Все поля заполнены")
    public void assertCreateUser(Response response) {
        response.then().assertThat().statusCode(OK)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Проверка ответа на ручку создания пользователя. Одно из полей не заполнено")
    public void assertCreateUserEmptyFields(Response response) {
        response.then().assertThat().statusCode(FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo(EMPTY_FIELD));
    }

    @Step("Проверка ответа на ручку создания пользователя. Пользователь уже существует")
    public void assertCreateUserAlreadyExists(Response response) {
        response.then().assertThat().statusCode(FORBIDDEN)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo(USER_ALREADY_EXIST));
    }

    @Step("Проверка ответа на ручку авторизации пользователя")
    public void assertLoginUser(Response response) {
        response.then().assertThat().statusCode(OK)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Проверка ответа на ручку авторизации пользователя. Логин или пароль неверные или нет одного из полей")
    public void assertLoginUserValidation(Response response) {
        response.then().assertThat().statusCode(UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo(INVALID_CREDENTIALS));
    }

    @Step("Проверка ответа на ручку изменения пользователя. С авторизацией")
    public void asserPatchUser(Response response) {
        response.then().assertThat().statusCode(OK)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Проверка ответа на ручку изменения пользователя. Без авторизации")
    public void asserPatchUserValidation(Response response) {
        response.then().assertThat().statusCode(UNAUTHORIZED)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo(USER_UNAUTHORIZED));
    }
}
