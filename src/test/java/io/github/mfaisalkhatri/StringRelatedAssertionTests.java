package io.github.mfaisalkhatri;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Faisal Khatri
 * @since 12/30/2022
 **/
public class StringRelatedAssertionTests {

    private static String URL = "https://reqres.in/api/users?page=2";

    @Test
    public void testStringAssertions() {

        given()
                .get(URL)
                .then()
                .assertThat()
                .body("data[0].first_name", equalTo("Michael"))
                .body("data[0].first_name", equalToIgnoringCase("MICHael"))
                .body("data[0].email", containsString("michael.lawson"))
                .body("data[0].last_name", startsWith("L"))
                .body("data[0].last_name", endsWith("n"))
                .body("data[1].first_name", equalToCompressingWhiteSpace("    Lindsay "));
    }

    @Test
    public void testNotNullAssertions() {
        given()
                .get(URL)
                .then()
                .and()
                .assertThat()
                .body("data[0].first_name", is(Matchers.notNullValue()));
    }

    @Test
    public void testHasKeyAssertion() {
        given()
                .get(URL)
                .then()
                .and()
                .assertThat()
                .body("data[0]", hasKey("email"))
                .body("support", hasKey("url"))
                .body("$", hasKey("page"))
                .body("$", hasKey("total"));
    }

    @Test
    public void testNotAssertions() {
        given()
                .get(URL)
                .then()
                .and()
                .assertThat()
                .body("data", not(emptyArray()))
                .body("data[0].first_name", not(equalTo("George")))
                .body("data.size()", greaterThan(5));
    }

    @Test
    public void testMultipleAssertStatement() {
        given()
                .get(URL)
                .then()
                .and()
                .assertThat()
                .body("page", equalTo(2), "data[0].first_name",
                        equalTo("Michael"), "support.url", is(notNullValue()));
    }

}