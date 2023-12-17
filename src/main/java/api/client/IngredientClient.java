package api.client;

import ingredient.ResponseData;
import io.qameta.allure.Step;

import static common.constants.BaseParams.BASE_CONTENT_TYPE_HEADER;
import static common.constants.BaseParams.BASE_CONTENT_TYPE_VALUE;
import static ingredient.constants.Routes.GET_INGREDIENTS;
import static io.restassured.RestAssured.given;

public class IngredientClient {
    @Step("Получение списка ингредиентов")
    public static ResponseData sendGetIngredientsRequest() {
        ResponseData response =
                given()
                        .header(BASE_CONTENT_TYPE_HEADER, BASE_CONTENT_TYPE_VALUE)
                        .and()
                        .when()
                        .get(GET_INGREDIENTS)
                        .body()
                        .as(ResponseData.class);
        return response;
    }
}
