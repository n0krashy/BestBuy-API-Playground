package stores;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class QueryStoreTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030/stores").build();
    }

    @Test
    public void getAllStoresList() {
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
                .get("?$limit=7&$skip=10")
                .then()
                .statusCode(200)
                .and().body("skip", equalTo(10));

        // Assert that the length of the `data` array is 10
        expect().body("data.length", hasSize(7));
    }

    @Test
    public void partialStoreName() {
        given()
                .spec(requestSpec)
                .when()
                .get("?name[$like]=*Richfield*")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }

    @Test
    public void nearbyStores() {
        given()
                .spec(requestSpec)
                .when()
                .get("?near=90210")
                .then()
                .statusCode(200)
                .and().body("data[0].city", equalTo("Los Angeles"));

        // Assert that the total of the `data` array is less than 20
        expect().body("data.total", lessThan(20));
    }

    @Test
    public void servicesOffered() {
        given()
                .spec(requestSpec)
                .when()
                .get("?service.name=Best Buy Mobile")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }

    @Test
    public void servicesOfferedAlternative() {
        given()
                .spec(requestSpec)
                .when()
                .get("?service[name]=Best Buy Mobile")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }

    @Test
    public void serviceId() {
        given()
                .spec(requestSpec)
                .when()
                .get("?service.id=2")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }

    @Test
    public void serviceIdAlternative() {
        given()
                .spec(requestSpec)
                .when()
                .get("?service[id]=2")
                .then()
                .statusCode(200);

        // Assert that the length of the `data` array is more than 0
        expect().body("data.length", greaterThan(0));
    }

    @Test
    public void selectSubsetOfStoreProperties() {
        given()
                .spec(requestSpec)
                .when()
                .get("?$select[]=name&$select[]=hours")
                .then()
                .statusCode(200).extract().response();

        // Assert that the length of the `data` array is more than 0
        expect().body("data[0].length", equalTo(2));
        // Assert it has hours
        expect().body("data[0]", hasKey("hours"));
        // Assert it has name
        expect().body("data[0]", hasKey("name"));
        // Assert it doesn't have price
        expect().body("data[0]", not(hasKey("price")));
    }
}
