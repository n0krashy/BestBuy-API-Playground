package categories;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Category;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.MyUtils.assertValidationErrors;

public class CreateCategoryTest {
    @BeforeClass
    public void createRequestSpec() {
        RestAssured.baseURI = "http://localhost:3030/categories";
    }

    @Test
    public void honorsRequiredFieldsWhenValidating() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/schemas/category.schema.json"));
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
    public void createCategory() {
        Category category;
        category = new Category();
        category.setName("Test Category");
        category.setId("pcsat1234565778821");

        given()
                .contentType(ContentType.JSON)
                .body(category)
                .when()
                .post()
                .then()
                .statusCode(201)
                .and().assertThat().body("id", equalTo(category.getId()));
    }
}
