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

    public static Response sendGetRequest(String endpoint) {
        System.out.println("GET REQUEST:" + BASE_URL + endpoint);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get(BASE_URL + endpoint)
                .thenReturn();
    }

    public static Response sendDeleteRequest(String endpoint) {
        System.out.println("DELETE REQUEST:" + BASE_URL + endpoint);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .delete(BASE_URL + endpoint)
                .thenReturn();
    }

    public static Response sendPatchRequest(String endpoint, Object body) {
        System.out.println("PATCH REQUEST:" + BASE_URL + endpoint);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(BASE_URL + endpoint)
                .thenReturn();
    }

    public static Response sendPutRequest(String endpoint, Object body) {
        System.out.println("PUT REQUEST:" + BASE_URL + endpoint);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put(BASE_URL + endpoint)
                .thenReturn();
    }
}