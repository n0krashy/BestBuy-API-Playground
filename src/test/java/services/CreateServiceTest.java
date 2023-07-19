package services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Service;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static utils.MyUtils.assertValidationErrors;

public class CreateServiceTest {
    @BeforeClass
    public void createRequestSpec() {
        RestAssured.baseURI = "http://localhost:3030/services";
    }

    @Test
    public void honorsRequiredFieldsWhenValidating() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/schemas/service.schema.json"));
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
    public void createService() {
        Service service;
        service = new Service();
        service.setName("Test Service");

        given()
                .contentType(ContentType.JSON)
                .body(service)
                .when()
                .post()
                .then()
                .statusCode(201)
                .and().body("$", hasKey("id"));
    }
}
