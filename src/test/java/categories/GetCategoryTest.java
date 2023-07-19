package categories;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import models.Category;
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

public class GetCategoryTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030").build();
    }

    @Test(dataProvider = "dataProvFunc")
    public void getCategoryById(Category category) {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/testdata/get.category.json"));
        given()
                .spec(requestSpec)
                .pathParam("categoryId", category.getId())
                .when()
                .get("/categories/{categoryId}")
                .then()
                .statusCode(200)
                // asserts that bodies are identical
                .body("", equalTo(expectedJson.getMap("")));
    }

    @Test(dataProvider = "dataProvFunc")
    public void canGetCategoryByIdAndSelectOnlyNameAndId(Category category) {
        given()
                .spec(requestSpec)
                .pathParam("categoryId", category.getId())
                .when()
                .get("/categories/{categoryId}?$select[]=name")
                .then()
                .statusCode(200)
                .and().assertThat().body("id", equalTo(category.getId()))
                .and().assertThat().body("name", equalTo(category.getName()));
        // Assert that the response body only contains the category ID and  name
        expect().body("", hasSize(2));
    }

    @Test
    public void returns404ForBadCategoryId() {
        given().spec(requestSpec).get("/categories/123").then().statusCode(404);
    }

    @DataProvider
    public Object[][] dataProvFunc() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/testdata/get.category.json");
        ObjectMapper mapper = new ObjectMapper();
        Category myCategory = mapper.readValue(fileReader, Category.class);
        return new Object[][]{{myCategory}};
    }
}
