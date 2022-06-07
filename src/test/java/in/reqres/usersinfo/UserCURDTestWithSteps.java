package in.reqres.usersinfo;

import in.reqres.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class UserCURDTestWithSteps {

    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static String first_name = "PrimUser" + TestUtils.getRandomValue();
    static String last_name = "PrimeUser" + TestUtils.getRandomValue();
    static String avatar = "Api Testing";
    static int id;

    @Steps
    UsersSteps usersSteps;

    @Title("This will create a new User")
    @Test
    public void test001(){
        ValidatableResponse response = usersSteps.creatingUser(first_name,last_name,email,avatar);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the User was added to the application")
    @Test
    public void test002(){
        HashMap<String, Object> studentMap = usersSteps.getUserInfoByFirstName(first_name);
        Assert.assertThat(studentMap, hasValue(first_name));
        id = (int) studentMap.get("id");
        System.out.println(id);
    }

    @Title("Update the User information and verify the updated information")
    @Test
    public void test003(){
        first_name = first_name + "_updated";

        usersSteps.updateUser(id, first_name, last_name, email, avatar).log().all().statusCode(200);

        HashMap<String, Object> userMap = usersSteps.getUserInfoByFirstName(first_name);
        Assert.assertThat(userMap, hasValue(first_name));

    }

    @Title("Delete the User and verify if the student is deleted")
    @Test
    public void test004(){
        usersSteps.deleteStudent(id).log().all().statusCode(204);
        usersSteps.getUserById(id).statusCode(404);

    }







}
