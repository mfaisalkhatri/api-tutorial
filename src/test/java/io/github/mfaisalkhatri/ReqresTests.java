package io.github.mfaisalkhatri;

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


}
