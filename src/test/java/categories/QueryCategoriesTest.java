package categories;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class QueryCategoriesTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030/categories").build();
    }

    @Test
    public void getAllCategoriesList() {
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
                .get("?$limit=15&$skip=15")
                .then()
                .statusCode(200)
                .and().body("skip", equalTo(15));

        // Assert that the length of the `data` array is 10
        expect().body("data.length", hasSize(15));
    }

    @Test
    public void partialCategoryName() {
        given()
                .spec(requestSpec)
                .when()
                .get("?name[$like]=*TV*")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }
}
