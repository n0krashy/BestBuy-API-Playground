package products;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class QueryProductsTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030/products").build();
    }

    @Test
    public void getAllProductsList() {
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
    public void partialProductName() {
        given()
                .spec(requestSpec)
                .when()
                .get("/?name[$like]=*TV*")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));

        expect().body("total", greaterThan("data.length"));
    }

    @Test
    public void categoryName() {
        given()
                .spec(requestSpec)
                .when()
                .get("?category.name=TVs")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
        // Assert that there are multiple results
        expect().body("total", greaterThan("data.length"));
    }

    @Test
    public void categoryNameAlternative() {
        given()
                .spec(requestSpec)
                .when()
                .get("?category[name]=TVs")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
        // Assert that there are multiple results
        expect().body("total", greaterThan("data.length"));
    }

    @Test
    public void categoryIdAlternative() {
        given()
                .spec(requestSpec)
                .when()
                .get("?category[id]=abcat0101000")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
        // Assert that there are multiple results
        expect().body("total", greaterThan("data.length"));
    }

    @Test
    public void sortByPrice() {
        given()
                .spec(requestSpec)
                .when()
                .get("?$sort[price]=-1&category.name=TVs")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
        // Assert that there are multiple results
        expect().body("data[0].price", greaterThan("body.data[9].price"));
    }

    @Test
    public void selectSubsetOfProperties() {
        given()
                .spec(requestSpec)
                .when()
                .get("?$select[]=name&$select[]=description")
                .then()
                .statusCode(200).extract().response();

        // Assert that the length of the `data` array is more than 0
        expect().body("data[0].length", equalTo(2));
        // Assert it has description
        expect().body("data[0]", hasKey("description"));
        // Assert it has name
        expect().body("data[0]", hasKey("name"));
        // Assert it doesn't have price
        expect().body("data[0]", not(hasKey("price")));
    }


}
