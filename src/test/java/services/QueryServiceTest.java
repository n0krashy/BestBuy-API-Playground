package services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class QueryServiceTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030/services").build();
    }

    @Test
    public void getAllServicesList() {
        given()
                .spec(requestSpec)
                .when()
                .get()
                .then()
                .statusCode(200)
                // Assert that the response body contains a `total` property
                .and().body("$", hasKey("total"));

        // Assert that the length of the `data` array is 10
        expect().body("data.length", hasSize(10));
    }

    @Test
    public void paginationTest() {
        given()
                .spec(requestSpec)
                .when()
                .get("?$limit=3&$skip=5")
                .then()
                .statusCode(200)
                .and().body("skip", equalTo(5));

        // Assert that the length of the `data` array is 10
        expect().body("data.length", hasSize(3));
    }

    @Test
    public void partialServiceName() {
        given()
                .spec(requestSpec)
                .when()
                .get("?name[$like]=*Kitchen*")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }
}
