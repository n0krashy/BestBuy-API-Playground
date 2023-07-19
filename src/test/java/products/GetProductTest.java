package products;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetProductTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030").build();
    }

    @Test(dataProvider = "dataProvFunc")
    public void canGetProductByIdAndSelectOnlyNameAndPrice(Product product) {
        Response response = given()
                .spec(requestSpec)
                .pathParam("productId", product.getId())
                .when()
                .get("/products/{productId}?$select[]=price&$select[]=name")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert that the response body contains the product ID, name, and price.
        Assert.assertEquals(response.body().path("id"), (product.getId()));
        Assert.assertEquals(response.body().path("name"), (product.getName()));
        Assert.assertEquals(response.body().path("price"), (product.getPrice()));

        // Assert that the response body only contains the product ID, name, and price.
        expect().body("", hasSize(3));
    }

    @Test
    public void returns404ForBadProductId() {
        given().spec(requestSpec).get("/products/123").then().statusCode(404);
    }

    @Test(dataProvider = "dataProvFunc")
    public void getProductById(Product product) {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/testdata/get.product.json"));
        given()
                .spec(requestSpec)
                .pathParam("productId", product.getId())
                .when()
                .get("/products/{productId}")
                .then()
                .statusCode(200)
                // asserts that bodies are identical
                .body("", equalTo(expectedJson.getMap("")));
    }

    @DataProvider
    public Object[][] dataProvFunc() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/testdata/get.product.json");
        ObjectMapper mapper = new ObjectMapper();
        Product myProduct = mapper.readValue(fileReader, Product.class);
        return new Object[][]{{myProduct}};
    }
}
