package stores;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Store;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static utils.MyUtils.assertValidationErrors;

public class CreateStoreTest {
    @BeforeClass
    public void createRequestSpec() {
        RestAssured.baseURI = "http://localhost:3030/stores";
    }

    @Test
    public void honorsRequiredFieldsWhenValidating() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/schemas/store.schema.json"));
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
        Store store;
        store = new Store();
        store.setName("Test Store");
        store.setAddress("123 Fake Street");
        store.setCity("Richfield");
        store.setState("MN");
        store.setZip("55437");

        given()
                .contentType(ContentType.JSON)
                .body(store)
                .when()
                .post()
                .then()
                .statusCode(201)
                .and().body("$", hasKey("id"));
    }
}
