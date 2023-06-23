package io.github.mfaisalkhatri;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * @author Faisal Khatri
 * @since 12/30/2022
 **/
public class NumberRelatedAssertionTests {

    private static String URL = "https://reqres.in/api/users/";

    @Test
    public void testNumberAssertions() {

        given().when()
                .queryParam("page", 2)
                .get(URL)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("page", equalTo(2))
                .body("per_page", greaterThan(4))
                .body("per_page", greaterThanOrEqualTo(6))
                .body("total", lessThan(14))
                .body("total_pages", lessThanOrEqualTo(3));
    }
}
