package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiClient {
    private static final String BASE_URL = ConfigReader.getProperty("BASE_URL");

    public static Response sendPostRequest(String endpoint, Object body) {
        System.out.println("POST REQUEST:" + BASE_URL + endpoint);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE_URL + endpoint)
                .thenReturn();
    }

}