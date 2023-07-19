package products;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Product;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static utils.MyUtils.assertValidationErrors;

public class CreateProductTest {
    @BeforeClass
    public void createRequestSpec() {
        RestAssured.baseURI = "http://localhost:3030/products";
    }

    @Test
    public void honorsRequiredFieldsWhenValidating() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/schemas/product.schema.json"));
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post()
                .then().statusCode(400)
                .and().extract().response();

        List<String> requiredFields = expectedJson.getList("required");
        assertValidationErrors(response, requiredFields);
    }

    @Test
    public void testPriceFormat() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "Test Product With Categories",
                          "description": "This is a test product with categories",
                          "upc": "123",
                          "type": "Electronics",
                          "model": "Product354546",
                          "price": 100.111
                        }""")
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("errors[0]", Matchers.equalTo("'price' should be multiple of 0.01"));
    }

    @Test
    public void createProduct() {
        Product product;
        product = new Product();
        product.setName("Test");
        product.setDescription("This");
        product.setUpc("12345");
        product.setType("Electronics");
        product.setModel("Product0123");
        Response response = given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .response();

        String id = response.jsonPath().getString("id");
        assert id != null;
    }
}
