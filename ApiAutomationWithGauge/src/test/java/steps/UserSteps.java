package steps;

import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import utils.ApiClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {
    private Response response;
    private String lastCreatedUsersId;

    @Step("Send a POST request to create a user with firstName <firstName>, lastName <lastName>, username <username> and password <password>")
    public void createUser(String firstName, String lastName, String username, String password) {
        var requestBody = Map.of("firstName", firstName, "lastName", lastName, "username", username, "password", password);
        response = ApiClient.sendPostRequest("/users", requestBody);
        lastCreatedUsersId = response.jsonPath().getString("userId");
    }

    @Step("Verify the user creation response contains ID")
    public void verifyUserCreationResponse() {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("userId")).isNotEmpty();
        System.out.println("userId:" + lastCreatedUsersId);
    }

}