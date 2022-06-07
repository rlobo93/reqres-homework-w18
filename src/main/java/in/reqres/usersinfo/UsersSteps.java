package in.reqres.usersinfo;

import in.reqres.constants.EndPoints;
import in.reqres.model.UsersPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Title;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;

public class UsersSteps {


    @Step("Creating student with firstName : {0}, lastName: {1}, email: {2}, programme: {3} and courses: {4}")
    public ValidatableResponse creatingUser(String first_name, String last_name,String email , String avatar) {
        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setFirst_name(first_name);
        usersPojo.setLast_name(last_name);
        usersPojo.setEmail(email);
        usersPojo.setAvatar(avatar);
       return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(usersPojo)
                .when()
                .post(EndPoints.CREATE_USERS)
                .then();
    }

    @Step("Getting the user information with firstName: {0}")

    public HashMap<String, Object> getUserInfoByFirstName (String first_name ) {
        String p1 = "data.findAll{it.first_name==' ";
        String p2 = " '}.get(0)";

        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + first_name + p2);
        return userMap;
    }

    @Step("Updating user information with id: {0}, firstName: {1}, lastName: {2}, email: {3}, programme: {4} and courses: {5}")

    public ValidatableResponse updateUser(int id, String first_name, String last_name, String email, String avatar) {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setFirst_name(first_name);
        usersPojo.setLast_name(last_name);
        usersPojo.setEmail(email);
        usersPojo.setAvatar(avatar);
       return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .body(usersPojo)
                .when()
                .put(EndPoints.UPDATE_USERS)
                .then().log().all().statusCode(200);

    }

    @Step("Deleting User information with studentId: {0}")
    public ValidatableResponse deleteStudent(int studentId) {

        return SerenityRest.given().log().all()
                .pathParam("studentID", studentId)
                .when()
                .delete(EndPoints.DELETE_USER)
                .then();
    }

    @Step("Getting User information with studentId: {0}")
    public ValidatableResponse getUserById(int id) {
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .get(EndPoints.GET_SINGLE_STUDENT_BY_ID)
                .then();
    }



}
