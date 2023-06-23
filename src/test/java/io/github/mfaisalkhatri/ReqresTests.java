package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.data.CreateUser;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresTests {

    private static final String BASE_URL = "https://reqres.in";

    @Test
    public void testGetUsers() {
        given().when()
                .log()
                .all()
                .queryParam("page",2)
                .get(BASE_URL + "/api/users")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("page",equalTo(2))
                .body("data[0].last_name",equalTo("Lawson"))
                .body("data[3].last_name",equalTo("Fields"));
    }
    @Test
    public void testCreateUser() {
        given().when()
                .log()
                .all()
                .body("{\n" +
                        "    \"name\": \"steve\",\n" +
                        "    \"job\": \"manager\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .post(BASE_URL + "/api/users")
                .then()
                .log()
                .all()
                .statusCode(201)
                .body("name", equalTo("steve"))
                .body("job", equalTo("manager"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @Test
    public void testUserCreation() {
        CreateUser newUser = new CreateUser("Michael", "Manager");
        given().when()
                .log()
                .all()
                .body(newUser)
                .contentType(ContentType.JSON)
                .post(BASE_URL + "/api/users")
                .then()
                .log()
                .all()
                .statusCode(201)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @Test
    public void updateUser() {
        CreateUser updateUser = new CreateUser("John", "Test Engineer" );
        given().when()
                .log()
                .all()
                .body(updateUser)
                .contentType(ContentType.JSON)
                .put(BASE_URL + "/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", equalTo(updateUser.getName()))
                .body("job", equalTo(updateUser.getJob()))
                .body("updatedAt", is(notNullValue()));

    }

    @Test
    public void updatePatchUser() {
        CreateUser updateUser = new CreateUser("Steve", "Test Engineer" );
        given().when()
                .log()
                .all()
                .body(updateUser)
                .contentType(ContentType.JSON)
                .patch(BASE_URL + "/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", equalTo(updateUser.getName()))
                .body("job", equalTo(updateUser.getJob()))
                .body("updatedAt", is(notNullValue()));

    }

    @Test
    public void deleteUser() {
        given().when()
                .log()
                .all()
                .delete(BASE_URL + "/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

}
