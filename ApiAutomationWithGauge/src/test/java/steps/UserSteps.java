package steps;

import com.thoughtworks.gauge.Step;
import io.restassured.response.Response;
import utils.ApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps {
    private Response response;
    private String lastCreatedUsersId;
    public static Map<String, String> globalVariables = new HashMap<String, String>();

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

    @Step("Verify the user creation error")
    public void verifyUserCreationError() {
        response.then().statusCode(404);
        assertThat(response.jsonPath().getString("userId")).isNullOrEmpty();
    }

    @Step("Send a GET request to getting users")
    public void getUsers() {
        response = ApiClient.sendGetRequest("/users");
    }

    @Step("Verify the getting users response")
    public void verifyGettingUsersResponse() {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getList("$")).isNotEmpty();
        //Are fields exist and not null
        List<Map<String, Object>> usersList = response.jsonPath().getList("$");
        for (Map<String, Object> user : usersList) {
            assertThat(user).containsKey("id");
            assertThat(user.get("id")).isNotNull();
            assertThat(user).containsKey("username");
            assertThat(user.get("username")).isNotNull();
            assertThat(user).containsKey("firstName");
            assertThat(user.get("firstName")).isNotNull();
            assertThat(user).containsKey("lastName");
            assertThat(user.get("lastName")).isNotNull();
            assertThat(user).containsKey("isActive");
            assertThat(user.get("isActive")).isNotNull();
        }
        System.out.println("Users:" + usersList);
        //Is created user exist in list
        Map<String, Object> user = response.jsonPath().getMap("find { it.id == '" + lastCreatedUsersId + "' }");
        assertThat(user.get("firstName")).isEqualTo("jane");
        assertThat(user.get("lastName")).isEqualTo("doe");
        assertThat(user.get("username")).isEqualTo("doejj");
        System.out.println( "Created user exist in users list.");
    }

    @Step("Save userId as <userId>")
    public void saveUserId(String userId) {
        globalVariables.put(userId, lastCreatedUsersId);
        System.out.println("User Id saved as " + userId);
    }

    @Step("Send a GET request to getting user by id <userId>")
    public void getUsers(String userId) {
        String userid = globalVariables.get(userId);
        response = ApiClient.sendGetRequest("/users/" + userid);
    }

    @Step("Verify the getting user by id <userId> response")
    public void verifyGettingUserByIdResponse(String userId) {
        response.then().statusCode(200);
        String userid = globalVariables.get(userId);
        assertThat(response.jsonPath().getString("id")).isEqualTo(userid);
        assertThat(response.jsonPath().getString("username")).isEqualTo("doejj");
        assertThat(response.jsonPath().getString("firstName")).isEqualTo("jane");
        assertThat(response.jsonPath().getString("lastName")).isEqualTo("doe");
        System.out.println("User is exist.");
    }

    @Step("Send a DELETE request to remove user by id <userId>")
    public void deleteUser(String userId) {
        String userid = globalVariables.get(userId);
        response = ApiClient.sendGetRequest("/users/" + userid);
    }

    @Step("Verify the remove user by id <userId> response")
    public void verifyRemoveUserByIdResponse(String userId) {
        String userid = globalVariables.get(userId);
        response.then().statusCode(200);

        //Verifing with get user by id request
        response = ApiClient.sendGetRequest("/users/" + userid);
        response.then().statusCode(404);

        //Verifing with get users request
        response = ApiClient.sendGetRequest("/users");
        response.then().statusCode(200);
        assertThat(response.jsonPath().getMap("find { it.id == '" + lastCreatedUsersId + "' }")).isNull();

        System.out.println("User is not exist.");
    }

    @Step("Send a PATCH request to switch activity <isActive> by id <userId>")
    public void switchActivity(boolean isActive, String userId) {
        String userid = globalVariables.get(userId);
        var requestBody = Map.of("isActive", isActive);

        response = ApiClient.sendPatchRequest("/users/" + userid + "/activity", requestBody);
    }

    @Step("Verify the switch activity <isActive> by id <userId> response")
    public void verifySwitchActivityResponse(boolean isActive, String userId) {
        String userid = globalVariables.get(userId);

        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("userId")).isEqualTo(userid);
        assertThat(response.jsonPath().getBoolean("isActive")).isEqualTo(isActive);

        System.out.println("User activity switched.");
    }

    @Step("Send a PUT request to update user by id <userId> with first name <firstName> and last name <lastName>")
    public void updateUser(String userId, String firstName, String lastName) {
        String userid = globalVariables.get(userId);
        var requestBody = Map.of("firstName", firstName, "lastName", lastName);

        response = ApiClient.sendPutRequest("/users/" + userid, requestBody);
    }

    @Step("Verify the update user by id <userId> with first name <firstName> and last name <lastName> response")
    public void verifyUpdateUserResponse(String userId, String firstName, String lastName) {
        String userid = globalVariables.get(userId);

        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("userId")).isEqualTo(userid);

        //Verifing with get user by id request
        response = ApiClient.sendGetRequest("/users/" + userid);
        assertThat(response.jsonPath().getString("id")).isEqualTo(userid);
        assertThat(response.jsonPath().getString("username")).isEqualTo("doejj");
        assertThat(response.jsonPath().getString("firstName")).isEqualTo(firstName);
        assertThat(response.jsonPath().getString("lastName")).isEqualTo(lastName);

        System.out.println("User updated.");
    }
}