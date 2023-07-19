package healthcheck;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthcheckTest {
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030").build();
        responseSpec = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                expectStatusCode(200).
                build();
    }

    @Test
    // GET request to the /healthcheck endpoint and expects a 200 status code
    // body should contain uptime of the application along with the documents
    // numbers of products, stores and categories and printing the results
    public void checkHealth() {
        Response response = given().spec(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .get("healthcheck")
                .then().spec(responseSpec)
                .extract()
                .response();

        String body = response.getBody().asString();

        assert body.contains("uptime");
        assert body.contains("products");
        assert body.contains("stores");
        assert body.contains("categories");

        System.out.println("The uptime is: " + response.path("uptime"));
        System.out.println("The number of products is: " + response.path("documents.products"));
        System.out.println("The number of stores is: " + response.path("documents.stores"));
        System.out.println("The number of categories is: " + response.path("documents.categories"));
    }
}
